package cn.com.fintheircing.customer.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtil {


	// 检验手机号码
	public static boolean isPhone(String phone) {
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			return false;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phone);
		boolean isMatch = m.matches();
		return isMatch;
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

	public static Boolean isContainTime(String begin,String end,String format) throws Exception{
		format = "yyyy-MM-dd "+format;
		SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(format);
		long start = 0;
		long finish = 0;
		long nowTime = System.currentTimeMillis();
		String today = getToday();
		begin = today+" "+begin;
		end = today+" "+end;
		try {
			Date date1 = simpleDateFormat.parse(begin);
			start = date1.getTime();
		} catch (ParseException e) {
			throw new Exception("开市时间，转换失败");
		}
		try {
			Date date2 = simpleDateFormat.parse(end);
			finish = date2.getTime();
		} catch (ParseException e) {
			throw new Exception("下市时间，转换失败");
		}
		if (nowTime>start && nowTime<finish){
			return true;
		}
		return false;
	}

	private static String  getToday(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}
}
