package com.github.ka4ok85.wca.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.github.ka4ok85.wca.Engage;
import com.github.ka4ok85.wca.command.WaitForJobCommand;

@Configuration
@ComponentScan("com.github.ka4ok85.wca")
public class SpringConfig {

	@Bean
	@Scope("prototype")
	public Engage engage(@Value("${podNumber}") int podNumber, @Value("${clientId}") String clientId,
			@Value("${clientSecret}") String clientSecret, @Value("${refreshToken}") String refreshToken) {
		return new Engage(podNumber, clientId, clientSecret, refreshToken);
	}

	@Bean
	@Scope("prototype")
	public WaitForJobCommand waitForJobCommand() {
		return new WaitForJobCommand();
	}
}
