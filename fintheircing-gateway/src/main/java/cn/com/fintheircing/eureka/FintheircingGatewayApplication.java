package cn.com.fintheircing.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class FintheircingGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintheircingGatewayApplication.class, args);
	}
}
