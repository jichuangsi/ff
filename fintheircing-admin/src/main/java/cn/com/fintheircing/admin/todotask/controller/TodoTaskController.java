package cn.com.fintheircing.admin.todotask.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import cn.com.fintheircing.admin.todotask.model.CreateRegTodoTaskModel;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/todoTask")
public class TodoTaskController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ApiOperation(value = "创建待办任务", notes = "")
	@ApiImplicitParams({
			})
	@PostMapping("/createRegTodoTask")
	public ResponseModel<String> createRegTodoTask(@Validated @RequestBody CreateRegTodoTaskModel model){
		logger.debug("recevice createRegTodoTask info:"+model.toString());
		return ResponseModel.sucessWithEmptyData("");
	}

}
