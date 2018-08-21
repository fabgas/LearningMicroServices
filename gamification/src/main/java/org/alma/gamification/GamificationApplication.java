package org.alma.gamification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * docker run -p 5672:5672 -p 15672:15672 -e RABBITMQ_USERNAME=fg -e RABBITMQ_PASSWORD=fg bitnami/rabbitmq
 */
@SpringBootApplication
public class GamificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamificationApplication.class, args);
	}
}
