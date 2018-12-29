package cn.com.fintheircing.admin.common.constant;

public enum RoleCode {
    ROLE_MANAGE("M",0),ROLE_PROXY_ONE("A",1),ROLE_PROXY_TWO("S",2)
    ,ROLE_EMP("E",3),ROLE_RISK("R",4),ROLE_FINANCE("F",5),ROLE_USER("U",6);

    private String name;
    private Integer index;

    private RoleCode(String name, Integer index){
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
        for (RoleCode c : RoleCode.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static RoleCode getPosition(String name) {
        for (RoleCode c : RoleCode.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
