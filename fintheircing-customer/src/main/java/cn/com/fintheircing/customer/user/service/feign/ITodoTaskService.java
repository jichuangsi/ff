package cn.com.fintheircing.customer.user.service.feign;

import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;
import cn.com.fintheircing.admin.todotask.model.TaskModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(value = "ffadmin", fallback = TodoTaskServiceFallBack.class)
public interface ITodoTaskService {
	
	@RequestMapping(value = "/todoTask/createRegTodoTask")
	ResponseModel<CreateRegTodoTaskModel> createRegTodoTask(TaskModel model);

}
