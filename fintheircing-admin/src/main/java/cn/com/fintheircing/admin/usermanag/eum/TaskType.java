package cn.com.fintheircing.admin.usermanag.eum;

import cn.com.fintheircing.admin.business.constant.BusinessStatus;

public enum  TaskType {
    ADD_PROMISE("增加保证金",0),EXPEND_MONEY("扩大融资",1),CONTRACT_END("结束",2),
    BUSINESS_RUDE("强制平仓",3),BUSINESS_NOT_RUDE("非强制平仓",4),BUSINESS_NOT("未平仓",5);

    private String name;
    private Integer index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    TaskType(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public static String getName(int index) {
        for (TaskType c : TaskType.values()) {
            if (c.index==index){
                return c.name;
            }
        }
        return null;
    }

    public static BusinessStatus getStatus(String name) {
        for (BusinessStatus c : BusinessStatus.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
