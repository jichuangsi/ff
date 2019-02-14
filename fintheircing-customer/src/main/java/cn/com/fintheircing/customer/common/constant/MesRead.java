package cn.com.fintheircing.customer.common.constant;

public enum  MesRead {
   IS_READ("未读",0),IS_NOT_READ("已读",1);
    private String name;
    private Integer index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    MesRead(String name, Integer index) {
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
        for (MesRead c : MesRead.values()) {
            if (c.index==index){
                return c.name;
            }
        }
        return null;
    }

}


