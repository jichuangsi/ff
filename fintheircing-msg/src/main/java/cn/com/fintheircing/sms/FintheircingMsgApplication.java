package cn.com.fintheircing.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableHystrix//使用Hystrix
@EnableCircuitBreaker//断路器
@EnableDiscoveryClient//服务发现
@EnableFeignClients//使用feign
@MapperScan("cn.com.fintheircing.sms.dao.mapper")
public class FintheircingMsgApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintheircingMsgApplication.class, args);
	}
}
