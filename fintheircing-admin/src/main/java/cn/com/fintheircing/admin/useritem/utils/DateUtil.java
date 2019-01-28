package cn.com.fintheircing.admin.useritem.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期操作工具类
 * <p/>
 * <pre>
 * 日期类型显示格式
 * ------------------------------
 * 字母 日期或时间元素              表示    示例
 * G    Era 标志符                  Text    AD
 * y    年                         Year    1996; 96
 * M    年中的月份                  Month   July; Jul; 07
 * w    年中的周数                  Number  27
 * W    月份中的周数                Number  2
 * D    年中的天数                  Number  189
 * d    月份中的天数                Number  10
 * F    月份中的星期                Number  2
 * E    星期中的天数                Text    Tuesday; Tue
 * a    Am/pm 标记                  Text    PM
 * H    一天中的小时数（0-23）      Number  0
 * k    一天中的小时数（1-24）      Number  24
 * K    am/pm 中的小时数（0-11）    Number  0
 * h    am/pm 中的小时数（1-12）    Number  12
 * m    小时中的分钟数              Number  30
 * s    分钟中的秒数                Number  55
 * S    毫秒数                     Number  978
 * z    时区                      General time zone  Pacific Standard Time; PST; GMT-08:00
 * Z    时区                      RFC 822 time zone  -0800
 * <p/>
 * 常见的格式如下：
 * "yyyy","年份，如:2009"
 * "M","月份，如:8"
 * "d","日，如:15"
 * "yyyyMMdd","年月日，如:20091001"
 * "yyyy-MM-dd","年月日，如:2009-10-01"
 * "yyyy/MM/dd","年月日，如:2009/10/01"
 * "yyyy.MM.dd","年月日，如:2009.10.01"
 * "yyyy年MM月dd日","年月日，如:2009年10月01日"
 * "yyyy-MM-dd HH:mm","年月日时分，如:2009-10-01 10:01"
 * "yyyy/MM/dd HH:mm","年月日时分，如:2009/10/01 10:01"
 * "yyyy.MM.dd HH:mm","年月日时分，如:2009.10.01 10:01"
 * "yyyy年MM月dd日 HH时mm分","年月日时分，如:2009年10月01日 10时01分"
 * "yyyy-MM-dd HH:mm:ss","年月日时分秒，如:2009-10-01 10:01:01"
 * "yyyy/MM/dd HH:mm:ss","年月日时分秒，如:2009/10/01 10:01:01"
 * "yyyy.MM.dd HH:mm:ss","年月日时分秒，如:2009.10.01 10:01:01"
 * "yyyy年MM月dd日 HH时mm分ss秒","年月日时分秒，如:2009年10月01日 10时01分01秒"
 * "yyyy-MM-dd a","年月日上午/下午，如:2009-10-01 上午"
 * "yyyy/MM/dd a","年月日上午/下午，如:2009/10/01 下午"
 * "yyyy.MM.dd a","年月日上午/下午，如:2009.10.01 上午"
 * </pre>
 */
public class DateUtil {

    /**
     * 日期类型格式：yyyy-MM-dd
     */
    public static String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期类型格式：yyyy-MM-dd HH:mm
     */
    public static String YYYY_MM_DD_HHMM = "yyyy-MM-dd HH:mm";

    /**
     * 日期类型格式：yyyy-MM-dd HH:mm:ss
     */
    public static String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳类型格式：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String YYYY_MM_DD_HHMMSS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 判断字符串是否是有效的日期， 以下格式被认为是有效的(字符串长度大于或等于8)：
     * <p>yyyy-MM-dd</p>
     * <p>yyyy-MM-d</p>
     * <p>yyyy-M-dd</p>
     * <p>yyyy-M-d</p>
     * <p>yyyy/MM/dd</p>
     * <p>yyyy/MM/d</p>
     * <p>yyyy/M/dd</p>
     * <p>yyyy/M/d</p>
     * <p>yyyyMMdd</p>
     *
     * @param date 日期字符串
     * @return 如果是有效的日期则返回true，否则返回false
     */
    public static boolean isValidDate(String date) {
        if ((date == null) || (date.length() < 8)) {
            return false;
        }
        try {
            boolean result = false;
            SimpleDateFormat formatter;
            char dateSpace = date.charAt(4);
            String format[];
            if ((dateSpace == '-') || (dateSpace == '/')) {
                format = new String[4];
                String strDateSpace = Character.toString(dateSpace);
                format[0] = "yyyy" + strDateSpace + "MM" + strDateSpace + "dd";
                format[1] = "yyyy" + strDateSpace + "MM" + strDateSpace + "d";
                format[2] = "yyyy" + strDateSpace + "M" + strDateSpace + "dd";
                format[3] = "yyyy" + strDateSpace + "M" + strDateSpace + "d";
            } else {
                format = new String[1];
                format[0] = "yyyyMMdd";
            }
            for (String aFormat : format) {
                formatter = new SimpleDateFormat(aFormat);
                formatter.setLenient(false);
                String tmp = formatter.format(formatter.parse(date));
                if (date.equals(tmp)) {
                    result = true;
                    break;
                }
            }
            return result;
        } catch (ParseException e) {
            return false;
        }
    }
    /**
     * 判断字符串是否是有效的日期
     *
     * @param date    日期字符串
     * @param pattern 日期格式,如：yyyy-MM-dd
     * @return 是则返回true，否则返回false
     */
    public static boolean isValidTime(String date, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            formatter.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 解析字符串为日期类型
     * @param date_str 字符串类型日期值，要求日期数据格式为：
     * 					yyyy-M-d，yyyy/M/d，yyyyMMdd， yyyy-MM-dd，yyyy/MM/dd
     *  				yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss
     *  				yyyy-MM-dd HH:mm:ss
     *   				yyyyMMdd 或 yyyy-MM-dd 或 yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static Date parseDate(String date_str) throws Exception {
        if (date_str == null || date_str.equals("")) {
            return null;
        }
        Date date = null;
        SimpleDateFormat format;
        try {
            if (date_str.length() == 8) {
                if (date_str.contains("-")) {
                    format = new SimpleDateFormat("yyyy-M-d");
                } else if (date_str.contains("/")) {
                    format = new SimpleDateFormat("yyyy/M/d");
                } else {
                    format = new SimpleDateFormat("yyyyMMdd");
                }
            } else if (date_str.length() == 10) {
                if (date_str.contains("-")) {
                    format = new SimpleDateFormat("yyyy-MM-dd");
                } else if (date_str.contains("/")) {
                    format = new SimpleDateFormat("yyyy/MM/dd");
                } else {
                    format = new SimpleDateFormat("yyyy-MM-dd");
                }
            } else if (date_str.length() == 16) {
                if (date_str.contains("-")) {
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                } else if (date_str.contains("/")) {
                    format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                } else {
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                }
            } else if (date_str.length() == 19) {
                if (date_str.contains("-")) {
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                } else if (date_str.contains("/")) {
                    format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                } else {
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
            } else {
                format = new SimpleDateFormat("yyyy-MM-dd");
            }
            format.setLenient(false);
            date = format.parse(date_str);

            return date;
        } catch (ParseException e) {
            String msg = "日期数据[" + date_str + "]类型转换异常！";
            throw new Exception(msg,e);
        }
    }
    /**
     * 用默认的格式将日期字符串转换成日期对象<br/>
     * 默认格式：yyyy-MM-dd
     * @param date    日期字符串
     * @return 返回格式化的日期
     */
    public static Date parse(String date){
        return parseDate(date,YYYY_MM_DD);
    }

    /**
     * 将指定格式的日期字符串转换成日期对象
     *
     * @param date    日期字符串
     * @param pattern 日期格式,如：yyyy-MM-dd
     * @return 返回格式化的日期
     */
    public static Date parseDate(String date, String pattern){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            return formatter.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将指定格式的日期字符串转换成日期对象
     *
     * @param date    日期字符串
     * @param pattern 日期格式,如：yyyy-MM-dd
     * @return 返回格式化的日期
     * @throws ParseException 解析异常
     */
    public static Date parseDate(String date, String pattern, Date dft) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            return formatter.parse(date);
        } catch (Exception e) {
            return dft;
        }
    }

    /**
     * 将日期对象转换成指定格式的字符串
     *
     * @param date    日期对象
     * @param pattern 日期格式,如：yyyy-MM-dd
     * @return 返回格式化的日期字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将当前日期转换成指定格式的字符串
     *
     * @param pattern 日期格式
     * @return 返回格式化的日期字符串
     */
    public static String getNow(String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date now = new Date();
            return formatter.format(now);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得年份，格式"yyyy"
     *
     * @return 返回当前年份
     */
    public static int getYear() {
        Date now = new Date();
        return getYear(now);
    }

    /**
     * 取得日期的年份，格式"yyyy"
     *
     * @param date 日期
     * @return 日期的年份
     */
    public static int getYear(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return Integer.parseInt(formatter.format(date));
    }

    /**
     * 取得月份
     *
     * @return 返回当前月份
     */
    public static int getMonth() {
        Date now = new Date();
        return getMonth(now);
    }

    /**
     * 取得日期的月份
     *
     * @param date 日期
     * @return 日期的月份
     */
    public static int getMonth(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("M");
        return Integer.parseInt(formatter.format(date));
    }

    /**
     * 取得今天的日期数
     *
     * @return 返回今天的日期数
     */
    public static int getDay() {
        Date now = new Date();
        return getDay(now);
    }

    /**
     * 取得日期的天数
     *
     * @param date 日期
     * @return 日期的天数
     */
    public static int getDay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("d");
        return Integer.parseInt(formatter.format(date));
    }

    /**
     * 获得与某日期相隔几天的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的天数,可以是负数，表示日期前几天
     * @return 返回的日期
     */
    public static Date addDay(Date date, int addCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addCount);
        return calendar.getTime();
    }

    /**
     * 获得与某日期相隔几月的日期
     *
     * @param date     指定的日期
     * @param addCount 相隔的月数,可以是负数，表示日期前几月
     * @return 返回的日期
     */
    public static Date addMonth(Date date, int addCount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addCount);
        return calendar.getTime();
    }

    /**
     * 得到某天是周几
     *
     * @param strDay 2010-01-02
     * @return 周几
     */
    public static int getWeekDay(String strDay){
        Date day = DateUtil.addDay(parseDate(strDay, "yyyy-MM-dd"), -1);
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(day);
        return strDate.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到某天是周几
     * @param date 日期类型
     * @return 周几
     * @throws ParseException 解析异常
     */
    public static int getWeekDay(Date date){
        Date day = DateUtil.addDay(date, -1);
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(day);
        return strDate.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 取得两个日期段的间隔天数
     *
     * @param t1 时间1
     * @param t2 时间2
     * @return t2 与t1的间隔天数
     * @throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getBetweenDays(String t1, String t2) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        betweenDays = getBetweenDays(d1, d2);
        return betweenDays;
    }

    /**
     * 取得两个日期段的日期间隔
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return t2 与t1的间隔天数
     */
    public static int getBetweenDays(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return -1;
        }
        int betweenDays;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c2.setTime(d1);
            c1.setTime(d2);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears
                ;
             i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    public static String getWeek(Date date){
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        String week = format.format(date);
        return week;
    }

    /**
     * 判断今天是否为星期六
     * @return
     */
    public static boolean isSaturday(){
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return week == Calendar.SATURDAY;
    }

    /**
     * 判断是否为星期六
     * @param date:年月日，格式：yyyy-MM-dd
     * @return
     */
    public static boolean isSaturday(String date){
        try {
            Date d = DateUtil.parseDate(date,YYYY_MM_DD);
            Calendar calendar =Calendar.getInstance();
            calendar.setTime(d);
            int week =  calendar.get(Calendar.DAY_OF_WEEK);
            return week == Calendar.SATURDAY;
        } catch (Exception e) {
            return false;
        }
    }



}
