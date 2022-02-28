package com.devinhouse.village.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	public RabbitMQConfig(
			@Value("${spring.rabbitmq.host:localhost}") String rabbithost,
			@Value("${spring.rabbitmq.host:guest}") String rabbitUsername,
			@Value("${spring.rabbitmq.host:guest}") String rabbitPassword) {
		
	}
	
	//@Bean
	//public Queue generatePDFQueue() {
	//	return new Queue("devin.generate.pdf", false,true,false,false,false);
	//}
}
