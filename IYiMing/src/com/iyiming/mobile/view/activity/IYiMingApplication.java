/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年8月10日 下午11:46:18
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity;

import com.iyiming.mobile.model.User;
import com.iyiming.mobile.util.ILog;

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
	public User user=null;
	public static String SESSION_ID="";
	public boolean isLoged=false;
	public String toString()
	{
		if(user!=null)
		{
			return "用户名："+user.getUsername()+"\n"+"session："+user.getSessionId()+"\n"+"avatar："+user.getImageUrl();
		}
		else
		{
			return "获取用户失败";
		}
	}

}
