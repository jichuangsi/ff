package cn.com.fintheircing.customer.common.constant;

public enum TaskStatus {
    DAYS("扩大融资", 0), MONTHS("减少融资", 1),
    SPECIAL("提盈", 2);
    private String name;
    private int index;

    TaskStatus(String name, int index) {
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
        for (TaskStatus c : TaskStatus.values()) {
            if (c.index == index) {
                return c.name;
            }
        }
        return null;
    }
}
