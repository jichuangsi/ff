package cn.com.fintheircing.customer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableHystrix//使用Hystrix
@EnableCircuitBreaker//断路器
@EnableDiscoveryClient//服务发现
@EnableFeignClients//使用feing
@MapperScan("cn.com.fintheircing.customer.user.dao.mapper")//扫描mybatismapper
public class FintheircingCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintheircingCustomerApplication.class, args);
	}
}
