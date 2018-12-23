package cn.com.fintheircing.admin.common.constant;

public enum PositionCode {
    POSITION_MANAGE("M",0),POSITION_PROXY_ONE("A",1),POSITION_PROXY_TWO("S",2)
    ,POSITION_EMP("E",3);

    private String name;
    private Integer index;

    private  PositionCode(String name,Integer index){
        this.name = name;
        this.index = index;}

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
    public static String getName(Integer index) {
        for (PositionCode c : PositionCode.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static PositionCode getPosition(String name) {
        for (PositionCode c : PositionCode.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
