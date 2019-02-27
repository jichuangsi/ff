package cn.com.fintheircing.admin.business.constant;

public enum  ApplyStatus {

    APPLY_INSERT("添加股票",0),APPLY_REMOVE("删减股票",1);

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
    private ApplyStatus(String name, int index) {
        this.name = name;
        this.num = index;
    }

    public static String getName(int index) {
        for (ApplyStatus c : ApplyStatus.values()) {
            if (c.getNum() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static ApplyStatus getStatus(String name) {
        for (ApplyStatus c : ApplyStatus.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
