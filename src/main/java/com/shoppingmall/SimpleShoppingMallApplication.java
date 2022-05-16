package com.shoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories("com.shoppingmall")
@EntityScan("com.shoppingmall")
@EnableScheduling
public class SimpleShoppingMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleShoppingMallApplication.class, args);
	}

}
