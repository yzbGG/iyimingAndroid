/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年8月10日 下午11:46:18
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity;

import com.iyiming.mobile.model.User;

import android.app.Application;

/**
 * @DESCRIBE ifxme 应用程序上下文
 */
public class IYiMingApplication extends Application{
	
	/**
	 * 用户信息
	 */
	/**
	 * 存储session中的id
	 */
	public static User user=null;
	public static String SESSION_ID="";

}
