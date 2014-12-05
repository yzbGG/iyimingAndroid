package com.iyiming.mobile.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


public class DateUtil {

	private static String datePattern = "yyyy-MM-dd_HH-mm-ss";
	
	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	


	/**
	 * 格式化日期到时分秒 yyyy-MM-dd_HH-mm-ss
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(datePattern);
		String result = null;
		try {
			result = dateTimeFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public static Date str2Date(String str) {
		return str2Date(str, null);
	}



	public static Calendar str2Calendar(String str) {
		return str2Calendar(str, null);

	}

	public static Calendar str2Calendar(String str, String format) {

		Date date = str2Date(str, format);
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;

	}

	public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
		return date2Str(c, null);
	}

	public static String date2Str(Calendar c, String format) {
		if (c == null) {
			return null;
		}
		return date2Str(c.getTime(), format);
	}

	public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
		return date2Str(d, null);
	}

	public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
		if (d == null) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s = sdf.format(d);
		return s;
	}

	public static String getCurDateStr() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + "-"
				+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
				+ ":" + c.get(Calendar.SECOND);
	}

	/**
	 * 获得当前日期的字符串格式
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurDateStr(String format) {
		Calendar c = Calendar.getInstance();
		return date2Str(c, format);
	}

	// 格式到秒
	public static String getMillon(long time) {

		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);

	}

	// 格式到天
	public static String getDay(long time) {

		return new SimpleDateFormat("yyyy.MM.dd").format(time);

	}
	/**
	 * @方法描述:	格式化得到 小时：分钟
	 * @作者:zhangshuo
	 * @param time
	 * @return
	 */
	public static String getMinute(long time){
		return new SimpleDateFormat("HH-mm").format(time);
	}

	// 格式到毫秒
	public static String getSMillon(long time) {

		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);

	}
	
	/**
	 * 字符串时间转换为固定的格式
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date str2Date(String str, String format) {
		if (str == null || str.length() == 0) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = FORMAT;
		}
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;

	}
	
	/**
	 * 毫秒转换成大致时间
	 * @param milliseconds
	 * @return
	 */
	public static String getTimeFromS(long milliseconds){
		String time = null;//MMddhh
		long tempMilliseconds=milliseconds;
//		Log.e("tempMilliseconds",new Date(tempMilliseconds).toString()+"");
		 milliseconds =  (new Date().getTime()-milliseconds)/1000;
		 if(milliseconds<60){
			 time = "刚刚";
		 }else if(milliseconds<60*60){
			 time = milliseconds/60+"分钟前";
		 }else if(new Date(tempMilliseconds).getDate()==new Date().getDate()){
//			 time = milliseconds/60/60+"小时前";
			 SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			 time="今天 " + format.format(new Date(tempMilliseconds));
		 }else if(new Date(tempMilliseconds).getYear()==new Date().getYear()){
//			 time = milliseconds/60/60/24+"天前";
			 SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
			 time=format.format(new Date(tempMilliseconds));
		 }
		 else
		 {
			 SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日 HH:mm");
			 time=format.format(new Date(tempMilliseconds));
		 }
		return time;
	}
	
//	/**
//	 * @方法描述:	转换时间为 刚刚、分钟前、小时前、天前的样式
//	 * @作者:zhangshuo
//	 * @param con
//	 * @param milliseconds 毫秒
//	 * @return
//	 */
//	public static String converTime(Context con, long milliseconds){  
//        long timeGap = (new Date().getTime() - milliseconds) / 1000;//与现在时间相差秒数  
//        String timeStr = null; 
//        if(timeGap > 24 * 60 * 60 * 28){//一月以上
//        	timeStr = new SimpleDateFormat(
//        			"yyyy" + con.getString(R.string.time_year)
//        			+ "MM" + con.getString(R.string.time_month)
//        			+ "dd" + con.getString(R.string.time_day)).format(milliseconds);
//        }else if(timeGap > 24 * 60 * 60){//1天以上  
//            timeStr = timeGap / (24*60*60) + con.getString(R.string.time_day_ago);  
//        }else if(timeGap > 60 * 60){//1小时-24小时  
//            timeStr = timeGap / (60*60) + con.getString(R.string.time_hour_ago);  
//        }else if(timeGap > 60){//1分钟-59分钟  
//            timeStr = timeGap / 60 + con.getString(R.string.time_minute_ago);  
//        }else{//1秒钟-59秒钟  
//            timeStr = con.getString(R.string.time_just_now);  
//        }  
//        return timeStr;  
//    }  
	/**
	 * 格式化当前时间
	 * @return
	 */
	public static String getNowTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * @方法描述:	日期选择器
	 * @作者:zhangshuo
	 * @param context
	 * @param editText
	 * @return
	 */
	public static Dialog createDataDialog(Context context, final EditText editText) {
		// 用来获取日期和时间的
		Calendar calendar = Calendar.getInstance();
		Dialog dialog = null;
		DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month,
					int dayOfMonth) {
				// Calendar月份是从0开始,所以month要加1
				editText.setText(DateUtil.getDay(
						DateUtil.str2Date(
								year + "-" + (month + 1) + "-" + dayOfMonth
										+ " 00:00:00").getTime()).replace(".",
						"-"));
			}
		};
		dialog = new DatePickerDialog(context, dateListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		return dialog;
	}

	/**
	 * @方法描述:	时间选择器
	 * @作者:zhangshuo
	 * @param context
	 * @param editText
	 * @return
	 */
	public static Dialog createTimerDialog(Context context, final EditText editText) {
		// 用来获取日期和时间的
		Calendar calendar = Calendar.getInstance();
		Dialog dialog = null;
		TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker timerPicker, int hourOfDay,
					int minute) {
				editText.setText(hourOfDay + ":" + minute);
			}
		};
		dialog = new TimePickerDialog(context, timeListener,
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), false); // 是否为二十四制
		return dialog;
	}
	
}
