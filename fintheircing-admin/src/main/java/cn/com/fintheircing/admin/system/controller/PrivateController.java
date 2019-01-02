package cn.com.fintheircing.admin.system.controller;

import cn.com.fintheircing.admin.common.model.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("用于后台添加角色等不暴露操作")
@RequestMapping("private")
public class PrivateController {

    @ApiOperation(value = "添加角色", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "用户token", required = true, dataType = "String")
    })
    @PostMapping("/saveRole")
    public ResponseModel saveRole(){

        return ResponseModel.sucessWithEmptyData("");
    }
}
