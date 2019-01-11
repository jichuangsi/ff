package cn.com.fintheircing.admin.business.constant;

/**
 * 合约的状态和平仓状态
 */
public enum BusinessStatus {
    CONTRACT_NEW("新建合约",0),CONTRACT_BUSINESS("交易中",1),CONTRACT_END("结束",2),
    BUSINESS_RUDE("强制平仓",3),BUSINESS_NOT_RUDE("非强制平仓",4),BUSINESS_NOT("未平仓",5);

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
    private BusinessStatus(String name, int index) {
        this.name = name;
        this.num = index;
    }

    public static String getName(int index) {
        for (BusinessStatus c : BusinessStatus.values()) {
            if (c.getNum() == index) {
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
