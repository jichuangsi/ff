package cn.com.fintheircing.admin.account.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PassWordModel {

    @NotBlank(message = "登录密码不能为空")
    @Pattern(regexp = "^((?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16})|111111$", message = "密码必须为8-16位数字与字母混合")
    private String oldPassWord;
    @NotBlank(message = "登录密码不能为空")
    @Pattern(regexp = "^((?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16})$", message = "密码必须为8-16位数字与字母混合")
    private String newPassWord;


    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }
}
