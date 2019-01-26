package cn.com.fintheircing.admin.business.constant;

public enum  EntrustStatus {

    ENTRUST_NOT_DEAL("未处理",0),ENTRUST_WAIT_DEAL("待处理",1),
    ENTRUST_REPORT("已报",2),ENTRUST_FINSISH("已成",3),ENTRUST_BACK("撤单",4)
    ,ENTRUST_BACK_ING("撤单中",5),ENTRUST_BACK_WAIT("待撤单",6),ENTRUST_BADORDER("废单",7);

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
