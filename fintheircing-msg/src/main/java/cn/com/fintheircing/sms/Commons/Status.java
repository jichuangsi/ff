package cn.com.fintheircing.sms.Commons;

public enum Status {
    SUCCESS("成功", 0), FAIL("失败", 1);
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
