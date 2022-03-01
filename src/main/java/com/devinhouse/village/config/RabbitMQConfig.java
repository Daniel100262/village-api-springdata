package com.devinhouse.village.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	public RabbitMQConfig(
			@Value("${spring.rabbitmq.host}") String rabbithost,
			@Value("${spring.rabbitmq.host}") String rabbitUsername,
			@Value("${spring.rabbitmq.host}") String rabbitPassword) {	
	}
}
