package cn.com.fintheircing.exchange.constant;

public enum  AccountStatus {
     USEING("使用中",0),FORBID("禁买",1),CUSTOM_ACCOUNT("客户账户", 8), MONEY_ACCOUNT("资金账户", 9);
    private String name;
    private int index;

    // 构造方法
    private AccountStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (AccountStatus c : AccountStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static AccountStatus getStatus(String name) {
        for (AccountStatus c : AccountStatus.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
