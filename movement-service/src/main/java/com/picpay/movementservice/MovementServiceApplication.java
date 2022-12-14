package com.picpay.movementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovementServiceApplication.class, args);
	}

}
