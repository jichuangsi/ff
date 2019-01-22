package cn.com.fintheircing.admin;

import com.auth0.jwt.algorithms.Algorithm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient//服务发现
@EnableScheduling
@EnableAsync
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


	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(5);
		// 设置最大线程数
		executor.setMaxPoolSize(10);
		// 设置队列容量
		executor.setQueueCapacity(20);
		// 设置线程活跃时间（秒）
		executor.setKeepAliveSeconds(60);
		// 设置默认线程名称
		executor.setThreadNamePrefix("hello-");
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}


	@Bean
	public Object testBean(PlatformTransactionManager platformTransactionManager){
		System.out.println(">>>>>>>>>>>"+platformTransactionManager.getClass().getName());
		return new Object();
	}

}
