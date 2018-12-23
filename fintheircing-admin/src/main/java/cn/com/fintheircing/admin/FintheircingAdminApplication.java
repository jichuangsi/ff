package cn.com.fintheircing.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("cn.com.fintheircing.admin.UserManag.mapper.userMapper")
@EnableFeignClients
public class FintheircingAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintheircingAdminApplication.class, args);
	}


}
