package cn.com.fintheircing.admin;

import com.auth0.jwt.algorithms.Algorithm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("cn.com.fintheircing.admin.*.dao.mapper")//扫描mybatism
public class FintheircingAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintheircingAdminApplication.class, args);
	}

	@Value("${custom.token.secret}")
	private  String secret;
	@Bean
	public Algorithm getTokenAlgorithm() throws IllegalArgumentException, Exception {
		return Algorithm.HMAC512(secret);
	}
}
