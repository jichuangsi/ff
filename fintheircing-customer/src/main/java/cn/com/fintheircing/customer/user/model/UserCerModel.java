package cn.com.fintheircing.customer.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserCerModel {
    @NotBlank
    @Pattern(regexp = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d·s]{2,20}$",message = "姓名格式不正确")
    private String realName;
    @NotBlank
    @Pattern(regexp = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",message = "身份证格式不正确")
    private String idCard;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
