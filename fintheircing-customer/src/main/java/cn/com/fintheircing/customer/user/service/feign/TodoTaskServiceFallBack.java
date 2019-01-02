package cn.com.fintheircing.customer.user.service.feign;

import cn.com.fintheircing.customer.common.constant.ResultCode;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import cn.com.fintheircing.customer.common.model.RoleModel;
import cn.com.fintheircing.customer.user.service.feign.model.CreateTodoTaskModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TodoTaskServiceFallBack implements ITodoTaskService {

	Logger logger  = LoggerFactory.getLogger(getClass());

	@Override
	public ResponseModel<Object> createRegTodoTask(CreateTodoTaskModel model) {
		return new ResponseModel<Object>("", ResultCode.SYS_BUSY, ResultCode.SYS_BUSY_MSG, null);
	}

	@Override
	public Boolean isExistBlackList(String ip) {
		logger.error(ResultCode.SYS_BUSY_BLACK);
		return true;
	}

	@Override
	public List<RoleModel> getRoles() {
		return new ArrayList<>();
	}
}
