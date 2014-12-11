/**
 * 
 */
package com.iyiming.mobile.view.activity;

import android.app.Application;
import android.os.Environment;

import com.iyiming.mobile.model.User;
import com.iyiming.mobile.util.ILog;
import com.iyiming.mobile.util.SerializationUtil;

/**
 * 
* @ClassName: IYiMingApplication 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author dkslbw@gmail.com
* @date 2014年12月11日 下午1:55:57 
*
 */
public class IYiMingApplication extends Application {

	/**
	 * 用户信息
	 */
	public User user = null;
	/**
	 * 存储session中的id
	 */
	public static String SESSION_ID = "";
	public boolean isLoged = false;
	/**
	 * 是否有更新
	 */
	public boolean hasUpdate=false;
	/**
	 * 是否处于离线模式
	 */
	public static boolean isInOfflineState=false;

	/** 手机SD卡路径 */
	private final String SDCardPath = Environment.getExternalStorageDirectory().toString();
	/** 本软件缓存照片的目录 ,和系统存储目录相同 */
	private final String SysDefaultImageDir = SDCardPath + "/DCIM/camera/";
	/** 本软件使用的存储目录 */
	private final String BaseSDCardPath = SDCardPath + "/IYIMING/";
	/** 本软件上传图片的缓存目录 */
	private final String UploadImagePath = BaseSDCardPath + "uploadImage/";

	/**
	 * 个人头像存储目录
	 */
	public final String HeadSDCardPath = BaseSDCardPath + "personHeadIcon/";

	public String toString() {
		if (user != null) {
			return "用户名：" + user.getUsername() + "\n" + "session：" + user.getSessionId() + "\n" + "avatar：" + user.getImageUrl();
		} else {
			return "获取用户失败";
		}
	}

	/**
	 * 保存user
	 * 
	 * @param user
	 */
	public void saveUser() {
		ILog.d("保存用户信息");
		SerializationUtil.sharedSerializationUtil().serialize(this, user);
	}

	public User getUser() {
		ILog.d("读取用户信息");
		return (User) SerializationUtil.sharedSerializationUtil().unSerialize(this);
	}

	public void deleteUser() {
		ILog.d("删除用户信息");
		SerializationUtil.sharedSerializationUtil().delete(this);
	}

}
