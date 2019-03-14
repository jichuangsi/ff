package cn.com.fintheircing.customer.common.constant;
/**
 * 支付的所有状态
 *
 * @author yaoxiong
 * @date 2019/1/15
 */
public enum  PayStatus {
    UN_DEFRAY("未支付",0),DEFRAY("已支付",1),FAILEPay("支付失败",2)
    ;

    private String name;
    private int index;

    PayStatus(String name, int index) {
        this.name = name;
        this.index = index;
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

    public static String getName(int index) {
        for (PayStatus c : PayStatus.values()) {
            if (c.index == index) {
                return c.name;
            }
        }
        return null;
    }
}

