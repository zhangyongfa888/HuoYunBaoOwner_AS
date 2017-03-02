package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 字符串工具类
 * 
 * @author zby
 * @date 2014-11-17
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空?
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0||str.equals("")||str.equals("null"));
	}

	public static String refFormatNowDate() {
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate;
	}

	/**获取当前的时间
	 * @return
	 */
	public static String getTime() {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattime = format1.format(new Date());
		return formattime;
	}

	/**
	 * 判断字符串是否为空?
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && str.length() != 0);
	}

	public final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	public final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM");
		}
	};

	public final static ThreadLocal<SimpleDateFormat> dateFormater4 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM-dd");
		}
	};

	/**
	 * 在字符串中截取数字
	 * 
	 * @param args
	 * @return
	 */
	public static String Stringtoint(String args) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(args);
		return m.replaceAll("").trim();
	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static double toDouble(String str, double defValue) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 距离处理 2014-11-24
	 * 
	 * @param distance
	 * @return
	 */
	public static String toDistance(double distance) {
		if (distance < 1) {
			return "0 km";
		} else if (distance > 1 && distance < 100) {
			return round(distance / 1000, 1) + " km";
		} else if (distance >= 100 && distance < 1000) {
			return round(distance / 1000, 2) + " km";
		} else if (distance > 1000) {
			double km = round(distance / 1000, 1);
			return km + " km";
		}
		return null;
	}

	/**
	 * 提供精确的小数位四舍五入处理�?
	 * 
	 * @param v
	 *            �?��四舍五入的数�?
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理�?
	 * 
	 * @param v
	 *            �?��四舍五入的数�?
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static float roundFloat(float v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Float.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 年龄处理 2014-11-26
	 * 
	 * @param birthDay
	 * @return
	 */
	public static int age(String birthDay) {
		int a = 0;

		if (isEmpty(birthDay)) {
			a = 0;
			return a;
		}

		try {
			Date birDate = dateFormater2.get().parse(birthDay);
			Date nowDate = dateFormater2.get().parse(dateFormater2.get().format(new Date()));

			long days = (nowDate.getTime() - birDate.getTime()) / (24 * 60 * 60 * 1000);
			a = (int) (days / 365);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * 登录时间 2014-11-26
	 * 
	 * @param loginDate
	 * @return
	 */
	public static String loginTime(String loginDate) {

		try {
			Date lastDate = dateFormater.get().parse(loginDate);
			Date nowDate = new Date();
			// Log.i("", nowDate.getTime()-lastDate.getTime()+"");
			long mins = (nowDate.getTime() - lastDate.getTime()) / (60 * 1000);
			long hons = (nowDate.getTime() - lastDate.getTime()) / (60 * 1000 * 60);
			long days = (nowDate.getTime() - lastDate.getTime()) / (60 * 1000 * 60 * 24);
			// Log.i("", mins + "***" + hons + "***" + days);
			if (mins < 60) {
				return mins + "分钟前";
			} else if (mins >= 60 && hons < 24) {
				return hons + "小时前";
			} else if (days >= 1) {
				return days + "天前";
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 判断是否是手机号
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (StringUtil.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	/**
	 * 格式化时间,判断时间是昨天还是今天
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDateTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (time == null || "".equals(time)) {
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar current = Calendar.getInstance();

		Calendar today = Calendar.getInstance(); // 今天

		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
		// Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		Calendar yesterday = Calendar.getInstance(); // 昨天

		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);

		current.setTime(date);

		if (current.after(today)) {
			return getDateStringFromStringTime(time);
		} else if (current.before(today) && current.after(yesterday)) {
			return "昨天";
		} else {
			// int index = time.indexOf("-")+1;
			// return time.substring(index, time.length());
			return getDateStringFromStringMonth(time);
		}
	}

	/**
	 * @描述 yyyy-MM-dd
	 * @return
	 */
	public static String getDateStringFromStringMonth(String dateStr) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * @描述 yyyy-MM-dd
	 * @return
	 */
	public static String getDateStringFromStringMonth2(String dateStr) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * 将时间戳转为年月日
	 * 
	 */
	public static String getStrDayTime3(String dateStr) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

		String data = sdf.format(date);
		String m = data.substring(0, 2);
		String d = data.substring(3, 5);
		String h = data.substring(5, data.length());
		data = m + "月" + d + "日" + h;
		return data;
	}

	/**
	 * @描述 HH:mm
	 * @return
	 */
	public static String getDateStringFromStringTime(String dateStr) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

	/**
	 * 获取当前时间距离指定时间多少毫秒
	 * 
	 * @param timeStr
	 * @return
	 */
	public static long getLeftTime(String timeStr) {
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		String nowMonthStr = format.format(new Date());

		Date begin = new Date();
		Date end = null;
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			end = dfs.parse(nowMonthStr + " " + timeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60 / 60;

		System.out.println(hour1 + "小时" + minute1 + "分" + second1 + "秒");
		return (hour1 * 24 * 3600 + minute1 * 60 + second1) * 1000;
	}

	/**
	 * 处理时钟显示方式
	 * 
	 * @param paramLong
	 * @return
	 */
	public static String changeTimerFormat(long paramLong) {
		long l1 = paramLong / 86400000L;
		long l2 = paramLong % 86400000L / 3600000L;
		long l3 = paramLong % 3600000L / 60000L;
		String str1 = String.valueOf(l1);
		;
		if ((l1 == 0L) && (l2 > 0L)) {
			// return l2 + "小时";
			if (l1 < 10L) {
				str1 = "0" + l1;
			}
		}
		if (str1.equals("0")) {
			str1 = "00";
		}
		long l4 = paramLong % 60000L / 1000L;
		String str2 = String.valueOf(l3);
		String str3 = String.valueOf(l4);
		if (l3 < 10L) {
			str2 = "0" + l3;
		}
		if (l4 < 10L) {
			str3 = "0" + l4;
		}
		return str1 + ":" + str2 + ":" + str3;
	}

	/**
	 * 获取设备号
	 * @param
	 *
	 * @return
	 */
	public static String getDeviceId(Context mContext) {
		final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 将时间戳转为字符串
	 */
	public static String getStrDayTime2(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time));
		return re_StrTime;
	}

	/**
	 * 将时间戳转为字符串
	 */
	public static String getStrDayTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time));
		return re_StrTime;
	}

	/**
	 * 将时间戳转为字符串
	 */
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time));
		return re_StrTime;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static boolean isEnglish(String str) {
		Pattern p = Pattern.compile("[a-zA-Z]*");
		Matcher m = p.matcher(str);
		m = p.matcher(str);
		return m.matches();
	}

	public static boolean isNumericOrEnglish(String str) {
		Pattern p = Pattern.compile("[0-9a-zA-Z]*");
		Matcher m = p.matcher(str);
		m = p.matcher(str);
		return m.matches();
	}

	public static boolean isChineseOrNumeric(String str) {
		Pattern p = Pattern.compile("[0-9\u4e00-\u9fa5]*");
		Matcher m = p.matcher(str);
		m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断是否为中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChineseWord(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

	/**
	 * 友好的显示资产格式 2014-7-22
	 * 
	 * @param coin
	 *            资产数
	 * @return
	 */
	public static String toCoinFormat(long coin) {

		if (coin <= 10000) {
			return String.valueOf(round(coin, 1));
		} else if (coin > 10000 && coin <= 100000000) {
			if (coin % 10000 == 0) {
				return round((coin / 10000), 1) + "万";
			} else {
				return (int) (coin / 10000) + "万" + round(coin % 10000, 1);
			}
		} else {
			double w = coin % 100000000;
			if (w > 10000 && w < 100000000) {
				return (int) (coin / 100000000) + "亿" + (int) (w / 10000) + "万" + round(w % 10000, 1);
			} else {
				return (int) (coin / 100000000) + "亿" + round(w, 1);
			}
		}
	}

	/**
	 * 
	 * @param bankcard
	 *            - 银行卡号 例 884566213448 --> **** **** **** 3448
	 * @return
	 */
	public static String bankCardToAsterisk(String bankcard) {
		String card = null;

		card = bankcard.substring(bankcard.length() - 4, bankcard.length());

		card = "**** **** **** " + card;

		return card;
	}

	/**
	 * 判断两字符串是否相等
	 *
	 * @param a 待校验字符串a
	 * @param b 待校验字符串b
	 * @return {@code true}: 相等<br>{@code false}: 不相等
	 */
	public static boolean equals(CharSequence a, CharSequence b) {
		if (a == b) return true;
		int length;
		if (a != null && b != null && (length = a.length()) == b.length()) {
			if (a instanceof String && b instanceof String) {
				return a.equals(b);
			} else {
				for (int i = 0; i < length; i++) {
					if (a.charAt(i) != b.charAt(i)) return false;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断两字符串忽略大小写是否相等
	 *
	 * @param a 待校验字符串a
	 * @param b 待校验字符串b
	 * @return {@code true}: 相等<br>{@code false}: 不相等
	 */
	public static boolean equalsIgnoreCase(String a, String b) {
		return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
	}

	/**
	 * null转为长度为0的字符串
	 *
	 * @param s 待转字符串
	 * @return s为null转为长度为0字符串，否则不改变
	 */
	public static String null2Length0(String s) {
		return s == null ? "" : s;
	}

	/**
	 * 返回字符串长度
	 *
	 * @param s 字符串
	 * @return null返回0，其他返回自身长度
	 */
	public static int length(CharSequence s) {
		return s == null ? 0 : s.length();
	}

	/**
	 * 首字母大写
	 *
	 * @param s 待转字符串
	 * @return 首字母大写字符串
	 */
	public static String upperFirstLetter(String s) {
		if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
		return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
	}

	/**
	 * 首字母小写
	 *
	 * @param s 待转字符串
	 * @return 首字母小写字符串
	 */
	public static String lowerFirstLetter(String s) {
		if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
		return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
	}

	/**
	 * 反转字符串
	 *
	 * @param s 待反转字符串
	 * @return 反转字符串
	 */
	public static String reverse(String s) {
		int len = length(s);
		if (len <= 1) return s;
		int mid = len >> 1;
		char[] chars = s.toCharArray();
		char c;
		for (int i = 0; i < mid; ++i) {
			c = chars[i];
			chars[i] = chars[len - i - 1];
			chars[len - i - 1] = c;
		}
		return new String(chars);
	}
}
