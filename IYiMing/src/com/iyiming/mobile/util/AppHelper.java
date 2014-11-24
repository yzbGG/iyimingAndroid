/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年8月8日 上午1:09:36
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.widget.appmsg.AppMsg;

/**
 * @DESCRIBE 应用程序的帮助类
 */
public class AppHelper {
	
	public static int LOGIN_FAIL_TIMES=1;

	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersion(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return context.getString(R.string.version_unknown);
		}
	}
	
	/**
	 * 检查是否是手机号码
	 * @param str
	 */
	public static boolean isPhoneNumber(String str)
	{
		Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        Matcher m = p.matcher(str);  
        return m.matches();  
	}

	/**
	 * 显示 toast 提示
	 * 
	 * @param context
	 * @param s
	 */
	public static void showToast(Context context, String s) {
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}

	public static void showViewToast(Activity context, String s) {
		AppMsg.makeText(context, s, AppMsg.STYLE_CONFIRM).show();
	}
	
	/**判断手机API版本*/
	public static int getSDKVersionNumber() { 
		 
        int sdkVersion; 
        try { 
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK); 
 
        } catch (NumberFormatException e) { 
 
            sdkVersion = 0; 
        } 
        return sdkVersion; 
    } 

}
