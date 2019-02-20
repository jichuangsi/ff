package cn.com.fintheircing.admin.business.constant;

public enum  RudeStatus {
    BUSINESS_RUDE("强制平仓",3),BUSINESS_NOT_RUDE("非强制平仓",4),BUSINESS_NOT("未平仓",5),BUSINESS_CONTROL("监控中",6),
    CONTRACT_NOT_WARN("安全",0),CONTRACT_WARN("触发",1);;

    private String name;
    private Integer num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    // 构造方法
    private RudeStatus(String name, int index) {
        this.name = name;
        this.num = index;
    }

    public static String getName(int index) {
        for (RudeStatus c : RudeStatus.values()) {
            if (c.getNum() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static RudeStatus getStatus(String name) {
        for (RudeStatus c : RudeStatus.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
