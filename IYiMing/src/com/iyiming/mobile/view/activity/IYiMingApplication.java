/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年8月10日 下午11:46:18
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity;

import android.app.Application;
import android.os.Environment;

import com.iyiming.mobile.model.User;

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
	
	
	/** 手机SD卡路径 */
	private final String SDCardPath = Environment.getExternalStorageDirectory()
			.toString();
	/** 本软件缓存照片的目录 ,和系统存储目录相同 */
	private final String SysDefaultImageDir = SDCardPath + "/DCIM/camera/";
	/** 本软件使用的存储目录 */
	private final String BaseSDCardPath = SDCardPath + "/HSMobile/";
	/** 本软件上传图片的缓存目录*/
	private final String UploadImagePath = BaseSDCardPath + "uploadImage/";
	
	/**
	 * 个人头像存储目录
	 */
	public final  String HeadSDCardPath = BaseSDCardPath+"personHeadIcon/";

	

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
