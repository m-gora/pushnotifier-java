package io.github.mgora.pushnotifier.client.authentication;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;

import io.github.mgora.pushnotifier.client.authentication.model.Login;
import io.github.mgora.pushnotifier.client.authentication.model.LoginResponse;
import reactor.core.publisher.Mono;

public class PushnotifierAuthenticationManager implements Subscriber<LoginResponse>, Runnable {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(PushnotifierAuthenticationManager.class);

	private AuthenticationCommands cmd;
	private String token;
	private OffsetDateTime expiryTime;
	private ScheduledThreadPoolExecutor executor;

	CountDownLatch latch;

	public PushnotifierAuthenticationManager(AuthenticationCommands cmd, String username, String password) {
		this.executor = new ScheduledThreadPoolExecutor(1);
		this.cmd = cmd;

		this.cmd.login(Mono.just(login(username, password))).subscribe(this);

	}

	/*
	 * package private for tests
	 */
	PushnotifierAuthenticationManager(AuthenticationCommands cmd, String username, String password,
			CountDownLatch latch) {
		this(cmd, username, password);
		this.latch = latch;
	}

	public String getAccessToken() {
		return token;
	}

	@Override
	public void onSubscribe(Subscription s) {
		s.request(1);
		this.executor.scheduleAtFixedRate(this, 1, 1, TimeUnit.MINUTES);
	}

	@Override
	public void onNext(LoginResponse t) {
		this.token = t.getAppToken();
		this.expiryTime = t.getExpiresAt();
	}

	@Override
	public void onError(Throwable t) {
		LOG.error("Could not subscribe to Login.", t);
	}

	@Override
	public void onComplete() {
		LOG.info("Subscription completed.");
	}

	@Override
	public void run() {
		// refresh token if expires in less than 2 minutes
		if (expiryTime.minus(2, ChronoUnit.MINUTES).compareTo(Instant.now().atOffset(ZoneOffset.UTC)) <= 0) {
			this.cmd.refresh().subscribe(this);
		}

		// FIXME remove testing code from here
		if (this.latch != null) {
			this.latch.countDown();
		}
	}

	private Login login(String username, String password) {
		Login login = new Login();
		login.setUsername(username);
		login.setPassword(password);
		return login;
	}

}
