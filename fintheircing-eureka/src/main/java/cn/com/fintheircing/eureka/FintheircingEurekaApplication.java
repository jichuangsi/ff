package cn.com.fintheircing.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FintheircingEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintheircingEurekaApplication.class, args);
	}
}
