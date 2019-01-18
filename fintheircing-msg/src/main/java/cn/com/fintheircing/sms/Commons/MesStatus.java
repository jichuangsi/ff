package cn.com.fintheircing.sms.Commons;

public enum MesStatus {
    SUCCESS("成功", 0), FAIL("失败", 1);
    private String name;
    private int index;

    // 构造方法
    private MesStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (MesStatus c : MesStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static MesStatus getStatus(String name) {
        for (MesStatus c : MesStatus.values()) {
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
