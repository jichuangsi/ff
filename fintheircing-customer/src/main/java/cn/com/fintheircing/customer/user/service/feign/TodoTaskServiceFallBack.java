package cn.com.fintheircing.customer.user.service.feign;

import org.springframework.stereotype.Component;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.user.service.feign.model.CreateTodoTaskModel;

@Component
public class TodoTaskServiceFallBack implements ITodoTaskService {

	@Override
	public ResponseModel<Object> createRegTodoTask(CreateTodoTaskModel model) {
		return new ResponseModel<Object>("", ResultCode.SYS_BUSY, ResultCode.SYS_BUSY_MSG, null);
	}

}
