package cn.com.fintheircing.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.com.fintheircing.admin.*.dao.mapper")//扫描mybatism
public class FintheircingAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintheircingAdminApplication.class, args);
	}


}
