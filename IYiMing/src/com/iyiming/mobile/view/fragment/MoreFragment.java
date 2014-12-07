/**   
 * @Title: MoreFragment.java 
 * @Package com.iyiming.mobile.view.fragment 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dkslbw@gmail.com   
 * @date 2014年11月21日 下午2:24:16 
 * @version V1.0   
 */
package com.iyiming.mobile.view.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.more.AboutActivity;
import com.iyiming.mobile.view.activity.more.ReportActivity;
import com.iyiming.mobile.view.widget.ShareDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * @ClassName: MoreFragment
 * @Description: TODO(更多)
 * @author dkslbw@gmail.com
 * @date 2014年11月21日 下午2:24:16
 * 
 */
public class MoreFragment extends BaseFragment implements OnClickListener {

	@Override
	public int getFragmentTitleResourceId() {
		return R.string.more;
	}

	@Override
	public boolean isNeedRemove() {
		return false;
	}

	@Override
	public void rightTitleClick(View v, BaseActivity context) {
		ShareDialog dialog = new ShareDialog(getActivity());
		// dialog.showDialog();

		// 设置分享内容
		mController.setShareContent("爱移民，https://115.29.248.18/login");
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(getActivity(), "https://115.29.248.18/static/images/logo.png"));
		mController.openShare(getActivity(), false);

		// showCustomUI(true);
	}

	@Override
	public void leftTitleClick(View v, BaseActivity context) {
	}

	@Override
	public int getRightTitle() {
		return R.drawable.ic_share;
	}

	@Override
	public int getLeftTitle() {
		return R.drawable.ic_back_white;
	}

	@Override
	public boolean isLeftTitleHide() {
		return true;
	}

	@Override
	public boolean isRightTitleHide() {
		return false;
	}

	@Override
	public boolean isNavBarHide() {
		return false;
	}

	private RelativeLayout tabAbout;
	private RelativeLayout tabService;
	private RelativeLayout tabContact;
	private RelativeLayout tabVersion;

	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_more, null);
		initView(inflater, view);
		initData();
		initListener();
		return view;
	}

	private void initView(LayoutInflater inflater, View view) {
		tabAbout = (RelativeLayout) view.findViewById(R.id.about);
		tabService = (RelativeLayout) view.findViewById(R.id.service);
		tabContact = (RelativeLayout) view.findViewById(R.id.contact);
		tabVersion = (RelativeLayout) view.findViewById(R.id.version);
	}

	private void initData() {

		
		String appID = "wx967daebe835fbeac";
		String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(getActivity(),appID,appSecret);
		wxHandler.addToSocialSDK();
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(),appID,appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), "100424468",
		                "c7394704798a158208a74ab60104f0ba");
		qqSsoHandler.addToSocialSDK();  
		
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(), "100424468",
		                "c7394704798a158208a74ab60104f0ba");
		qZoneSsoHandler.addToSocialSDK();
		
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		
	}

	private void initListener() {
		tabAbout.setOnClickListener(this);
		tabService.setOnClickListener(this);
		tabContact.setOnClickListener(this);
		tabVersion.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == tabAbout) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), AboutActivity.class);
			startActivity(intent);
		} else if (v == tabService) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), ReportActivity.class);
			startActivity(intent);
		} else if (v == tabContact) {

		} else if (v == tabVersion) {

		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    /**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}

}
