package cn.com.fintheircing.admin.common.constant;

public enum  VerifyCode {

    VERIFY_S("成功",0),VERIFY_E("驳回",1),VERIFY_W("待审核",2);

    private String name;
    private int index;

    // 构造方法
    private VerifyCode(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (VerifyCode c : VerifyCode.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static VerifyCode getStatus(String name) {
        for (VerifyCode c : VerifyCode.values()) {
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
