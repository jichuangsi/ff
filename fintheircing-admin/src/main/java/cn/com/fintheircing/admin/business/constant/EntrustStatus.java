package cn.com.fintheircing.admin.business.constant;

public enum  EntrustStatus {

    ENTRUST_SUCCESS("已成",0),ENTRUST_ERR("废单",1),ENTRUST_BACK("撤单",2),
    ENTRUST_TOBACK("已报待扯",3),ENTRUST_REPORT("已报",4),ENTRUST_NOT_REPORT("未报",5)
    ,ENTRUST_WAIT("待处理",6);

    private String name;
    private Integer index;

    private EntrustStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (EntrustStatus c : EntrustStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static EntrustStatus getStatus(String name) {
        for (EntrustStatus c : EntrustStatus.values()) {
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
