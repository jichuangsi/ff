package cn.com.fintheircing.admin.usermanag.uilts;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

public class SortForListUtils {
    //对List

    /**
     * @param t    按照数组里面的排序
     * @param list 这里的list一定要是java.util.ArrayList的下的包 因为只有它才实现了remove,add和其他方法
     * @param <T>
     * @return
     */
    public static <T> List<T> sortByArray(T[] t, List<T> list) {
        List sortList = new ArrayList<T>();
        //去重
        Object[] exp = deletRepeat(t);

        for (Object t1 : exp) {
            for (int i = 0; i < list.size(); i++) {
                if (t1.equals(list.get(i))) {
                    sortList.add(t1);
                    list.remove(list.get(i));
                    i--;
                }
            }
        }
        if (list.size() > 0) {
            for (T t1 : list) {
                sortList.add(t1);
            }
        }
        return sortList;

    }

    /**
     * 数组去重
     *
     * @param t
     * @return
     */
    public static Object[] deletRepeat(Object[] t) {
        List list = new ArrayList();
        for (Object t1 : t) {
            if (!list.contains(t1)) {
                list.add(t1);
            }
        }
        Object[] exp = list.toArray();
        return exp;
    }

    //不去重
    public static <T> List<T> notdeletRepeat(T[] t, List<T> list) {
        List sortList = new ArrayList<>(list.size());
        for (T t1 : t) {
            for (int i = 0; i < list.size(); i++) {
                if (t1.equals(list.get(i))) {
                    sortList.add(list.get(i));
                    list.remove(list.get(i));
                    i--;
                }
            }
        }
        if (list.size() > 0) {
            for (T t1 : list) {
                sortList.add(t1);
            }
        }
        return sortList;
    }
}