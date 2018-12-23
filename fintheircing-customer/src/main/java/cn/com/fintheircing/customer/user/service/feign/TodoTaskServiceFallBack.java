package cn.com.fintheircing.customer.user.service.feign;

import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;
import cn.com.fintheircing.admin.todotask.model.TaskModel;
import cn.com.fintheircing.customer.common.model.ResponseModel;
import org.springframework.stereotype.Component;

@Component
public class TodoTaskServiceFallBack implements ITodoTaskService {

	@Override
	public ResponseModel<CreateRegTodoTaskModel> createRegTodoTask(TaskModel model) {
		CreateRegTodoTaskModel Regmodel =new CreateRegTodoTaskModel();
		Regmodel.setPhoneNo(model.getPhoneNo());
		Regmodel.setRegisterUserId(model.getTaskId());
		Regmodel.setTaskType(model.getTaskType());
		return ResponseModel.sucess("",Regmodel);
	}
}
