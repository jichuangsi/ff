package cn.com.fintheircing.admin.systemdetect.common;
/**
 * Demo ProductStatus
 *
 * @author YAOXIONG
 * @date 2018/12/27
 */
public enum ProductStatus {
    DAYS("日配", 0), MONTHS("月配", 1),
    SPECIAL("特殊", 2);
    private String name;
    private int index;

    // 构造方法
    private ProductStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ProductStatus c : ProductStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static ProductStatus getStatus(String name) {
        for (ProductStatus c : ProductStatus.values()) {
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
