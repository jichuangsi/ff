package cn.com.fintheircing.admin.common.utils;

import cn.com.fintheircing.admin.common.constant.ResultCode;
import cn.com.fintheircing.admin.system.exception.SystemException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	// 检验手机号码
	public static boolean isPhone(String phone) {
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			return isMatch;
		}
	}

	//对字符串做SHA256摘要
	public static String toSha256(String text) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(text.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("toSha256 error.");
		}
		return encodeStr;
	}

	/**
	 * 将byte转为16进制 @param bytes @return
	 */
	private static String byte2Hex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		String temp = null;
		for (int i = 0; i < bytes.length; i++) {
			temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1) {
				// 1得到一位的进行补0操作
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}

	public static long getlongTime(String date,String sdformat) throws SystemException{
		SimpleDateFormat sdf = new SimpleDateFormat(sdformat);
		long longTime = 0;
		try {
			Date time = sdf.parse(date);
			longTime = time.getTime();
		} catch (ParseException e) {
			throw new SystemException(ResultCode.DATE_FORMATE_MSG);
		}
		return longTime;
	}

	public static long getEndLongTime(String date,String sdformat) throws SystemException{
		long time = getlongTime(date,sdformat);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime().getTime();
	}

	public static Boolean regexString(String regex,String str){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
}
