package cn.com.fintheircing.admin.common.constant;


//合约操作
public enum  ControlCode {
    CONTROL_CRETE("创建合约",0),CONTROL_PROMISED("追加保证金",1),CONTROL_SHUT("终止操盘",2)
    ,CONTROL_PAYINTEREST("自动付息",3),CONTROL_OPERATE("追加操盘",4),CONTROL_GAIN("提取利润",5)
    ,CONTROL_BUYSTOCK("买入时收取交易额",6),CONTROL_CLOSEMARKET("收市时收取管理费",7)
    ,CONTROL_BUSINESS("交易时收取交易额",8),CONTROL_GETLEVERMONEY("发放杠杆资金",9);

    private String name;
    private Integer index;

    private ControlCode(String name, Integer index){
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
        for (ControlCode c : ControlCode.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static ControlCode getPosition(String name) {
        for (ControlCode c : ControlCode.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
