package com.devinhouse.village;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class DevinhouseVillageApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevinhouseVillageApiApplication.class, args);
	}

}
