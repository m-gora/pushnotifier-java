package io.github.mgora.pushnotifier.client.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.github.mgora.pushnotifier.client.authentication.model.LoginResponse;
import reactor.core.publisher.Mono;

public class PushnotifierAuthenticationManagerTest {
	
	@Test
	public void testLogin() {
		LoginResponse resp = new LoginResponse();
		resp.setAppToken("accessToken1");
		AuthenticationCommands cmd = Mockito.mock(AuthenticationCommands.class);
		when(cmd.login(Mockito.any())).thenReturn(Mono.just(resp));
		
		PushnotifierAuthenticationManager mgr = new PushnotifierAuthenticationManager(cmd, "user", "pass");
		
		assertThat(mgr.getAccessToken()).isEqualTo("accessToken1");
	}
	
	@Test
	public void testRefresh() throws InterruptedException {
		LoginResponse resp = new LoginResponse();
		resp.setAppToken("accessToken1");
		resp.setExpiresAt(Instant.now().atOffset(ZoneOffset.UTC).plus(30, ChronoUnit.SECONDS));
		
		LoginResponse refreshResp = new LoginResponse();
		OffsetDateTime later = Instant.now().plus(1, ChronoUnit.HOURS).atOffset(ZoneOffset.UTC);
		refreshResp.setExpiresAt(later);
		refreshResp.setAppToken("refreshed");
		AuthenticationCommands cmd = Mockito.mock(AuthenticationCommands.class);
		when(cmd.login(Mockito.any())).thenReturn(Mono.just(resp));
		when(cmd.refresh()).thenReturn(Mono.just(refreshResp));
		
		// count down on refresh
		CountDownLatch latch = new CountDownLatch(1);
		PushnotifierAuthenticationManager mgr = new PushnotifierAuthenticationManager(cmd, "user", "pass", latch);
		
		
		assertThat(latch.await(2, TimeUnit.MINUTES)).isTrue();
		Mockito.verify(cmd).refresh();
		assertThat(mgr.getAccessToken()).isEqualTo("refreshed");
	}
	
}
