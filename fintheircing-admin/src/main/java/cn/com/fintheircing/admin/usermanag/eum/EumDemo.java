package cn.com.fintheircing.admin.usermanag.eum;

public enum EumDemo {
    RED(30){
        @Override
        public EumDemo nextLamp() {
            return null;
        }
    };
    public abstract EumDemo nextLamp();
    // 枚举也可以象一般的类一样添加方法和属性
    private int time;
    private EumDemo(int time){this.time = time;}
}
