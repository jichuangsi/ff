package cn.com.fintheircing.admin.useritem.common;
/**
 * Demo ProductStatus
 *
 * @author YAOXIONG
 * @date 2018/12/27
 */
public enum Status {
    WHITELIST("白名单", 0), ABSOLUTE_WHITELIST("绝对白名单", 1),
    STATIC_BLACKLIST("静态黑名单", 2),DYNAMIC_BLACKLIST("动态黑名单",3)
    ;
    private String name;
    private int index;

    // 构造方法
    private Status(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (Status c : Status.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static Status getStatus(String name) {
        for (Status c : Status.values()) {
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
