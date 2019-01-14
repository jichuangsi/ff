package cn.com.fintheircing.exchange.util;

import org.apache.commons.lang.StringUtils;

public class ColUtils {

	@SuppressWarnings("unchecked")
	public static <T> T colProcess(String col, Class<T> clazz) {
		String temp;
		if (StringUtils.isEmpty(col) && clazz.equals(String.class)) {
			temp = "";
		} else if (StringUtils.isEmpty(col) && clazz.equals(Float.class)) {
			temp = "0.0000";
		} else if (StringUtils.isEmpty(col) && clazz.equals(Integer.class)) {
			temp = "0";
		} else {
			temp = col;
		}
		if (clazz.equals(String.class)) {
			return (T) EncodeUtils.getUTF8StringFromGBKString(temp);
		} else if (clazz.equals(Float.class)) {
			return (T) Float.valueOf(temp);
		} else if (clazz.equals(Integer.class)) {
			return (T) Integer.valueOf(temp);
		} else {
			return null;
		}
	}

}
