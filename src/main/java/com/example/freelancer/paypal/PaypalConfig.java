package com.example.freelancer.paypal;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfig {

	@Value("ARJfHFMvP2U1EZqo6mUxc-NJwOzIq-yoRPcKCfke3zl31snic6RPg8coma_kSqGdqzHLAec0fbKIKjBE")
	private String clientId;
	@Value("ELfB8AP9RKI0XVBeyIcvQP5kT0I6Ht4LOxbt10T9KB9eeAksLtYWqiTUuJgG7hG1LxBOP78wZZPJqiQb")
	private String clientSecret;
	@Value("Sandbox")
	private String mode;

	@Bean
	public Map<String, String> paypalSdkConfig() {
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		return configMap;
	}

	@Bean
	public OAuthTokenCredential oAuthTokenCredential() {
		return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
	}

	@Bean
	public APIContext apiContext() throws PayPalRESTException {
		APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
		context.setConfigurationMap(paypalSdkConfig());
		return context;
	}

}
