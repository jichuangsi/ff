package cn.com.fintheircing.customer.user.service.feign;

import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.service.feign.model.CreateTodoTaskModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ffadmin", fallback = TodoTaskServiceFallBack.class)
public interface ITodoTaskService {
	
	@RequestMapping(value = "/todoTask/createRegTodoTask")
	ResponseModel<Object> createRegTodoTask(CreateTodoTaskModel model);

	@RequestMapping(value = "/adminF/isExistBlackList")
	Boolean isExistBlackList(@RequestParam("ip") String ip);
}
