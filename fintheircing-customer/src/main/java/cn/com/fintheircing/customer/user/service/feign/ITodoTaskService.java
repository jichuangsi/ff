package cn.com.fintheircing.customer.user.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.service.feign.model.CreateTodoTaskModel;


@FeignClient(value = "ffadmin", fallback = TodoTaskServiceFallBack.class)
public interface ITodoTaskService {
	
	@RequestMapping(value = "/todoTask/createRegTodoTask")
	ResponseModel<Object> createRegTodoTask(CreateTodoTaskModel model);

}
