package cn.com.fintheircing.customer;

import com.auth0.jwt.algorithms.Algorithm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableHystrix//使用Hystrix
@EnableCircuitBreaker//断路器
@EnableDiscoveryClient//服务发现
@EnableFeignClients//使用feign
@MapperScan("cn.com.fintheircing.customer.user.dao.mapper")//扫描mybatismapper
public class FintheircingCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintheircingCustomerApplication.class, args);
	}


	@Value("${custom.token.secret}")
	private  String secret;
	@Bean
	public Algorithm getTokenAlgorithm() throws IllegalArgumentException, Exception {
		return Algorithm.HMAC512(secret);
	}

}
