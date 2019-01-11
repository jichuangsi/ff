package cn.com.fintheircing.admin.todotask.controller;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.common.utils.EntityToModel;
import cn.com.fintheircing.admin.common.utils.ModelToEntity;
import cn.com.fintheircing.admin.todotask.entity.TodoTaskInfo;
import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;
import cn.com.fintheircing.admin.todotask.model.FeedbackModel;
import cn.com.fintheircing.admin.usermanag.dao.repsitory.ITodoTaskInfoRepository;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todoTask")
public class TodoTaskController {

	@Autowired
	private ITodoTaskInfoRepository todoTaskInfoRepository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ApiOperation(value = "创建待办任务并且返回结果", notes = "")
	@ApiImplicitParams({
			})
	@PostMapping("/createRegTodoTask")
	public ResponseModel<FeedbackModel> createRegTodoTask(@Validated @RequestBody CreateRegTodoTaskModel model){
		TodoTaskInfo taskInfo = EntityToModel.CoverTodoTaskInfoModel(model);
		TodoTaskInfo save = todoTaskInfoRepository.save(taskInfo);
		FeedbackModel feedbackModel = ModelToEntity.coverTodoTaskInfo(save);
		logger.debug("recevice createRegTodoTask info:"+model.toString());
		return ResponseModel.sucess("",feedbackModel);
	}
}
