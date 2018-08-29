package org.alma.mircoservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MircoservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MircoservicesApplication.class, args);
	}
}
