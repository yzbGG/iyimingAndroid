/**   
* @Title: MineFragment.java 
* @Package com.iyiming.mobile.view.fragment 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dkslbw@gmail.com   
* @date 2014年11月21日 下午2:20:03 
* @version V1.0   
*/
package com.iyiming.mobile.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.AppInfoUtil;
import com.iyiming.mobile.util.ILog;
import com.iyiming.mobile.util.ImageUtil;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.account.LoginActivty;
import com.iyiming.mobile.view.activity.my.EditPasswordActivity;
import com.iyiming.mobile.view.activity.my.UserInfoActivity;
import com.iyiming.mobile.view.widget.roundedimageview.RoundedImageView;

/** 
 * @ClassName: MineFragment 
 * @Description: TODO(我的) 
 * @author dkslbw@gmail.com
 * @date 2014年11月21日 下午2:20:03 
 *  
 */
public class MineFragment extends BaseFragment{
	
	private RelativeLayout tabInfo;
	private RelativeLayout tabPwd;
	private Button logout;
	
	private LinearLayout avatarContainer;
	private RoundedImageView avatar;
	private TextView name;
	
	private LinearLayout loginContainer;
	private Button login;
	
	private final int LOGIN=10001;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_mine, null);
		initView(inflater,view);
		initData();
		initListener();
		return view;
	}
	
	private void initView(LayoutInflater inflater,View view)
	{
		tabInfo=(RelativeLayout)view.findViewById(R.id.tab_info);
		tabPwd=(RelativeLayout)view.findViewById(R.id.tab_pwd);
		logout=(Button)view.findViewById(R.id.logout);
		avatarContainer=(LinearLayout)view.findViewById(R.id.avatarContainer);
		
		
		loginContainer=(LinearLayout)view.findViewById(R.id.loginContainer);
		login=(Button)view.findViewById(R.id.login);
		
		avatarContainer=(LinearLayout)view.findViewById(R.id.avatarContainer);
		avatar=(RoundedImageView)view.findViewById(R.id.avatar);
		name=(TextView)view.findViewById(R.id.name);
	}
	
	private void initData()
	{
		if(application.isLoged)
		{
			showLogedView();
		}
		else
		{
			showLogoutView();
		}
	}
	
	private void initListener()
	{
		tabInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), UserInfoActivity.class);
				startActivity(intent);
			}
		});
		
		tabPwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), EditPasswordActivity.class);
				startActivity(intent);
			}
		});
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(getActivity(), LoginActivty.class);
				startActivityForResult(intent, LOGIN);
			}
		});
	}
	
	/**
	 * 
	 */
	private void showLogedView()
	{

			avatarContainer.setVisibility(View.VISIBLE);
			loginContainer.setVisibility(View.GONE);
			logout.setVisibility(View.VISIBLE);
			ImageUtil.getInstance(getActivity()).getImage(avatar, AppInfoUtil.sharedAppInfoUtil().getImageServerUrl()+application.user.getImageUrl(),true);
			name.setText(application.user.getUsername());
			ILog.e(AppInfoUtil.sharedAppInfoUtil().getImageServerUrl()+application.user.getImageUrl());

	}
	
	private void showLogoutView()
	{
		avatarContainer.setVisibility(View.GONE);
		loginContainer.setVisibility(View.VISIBLE);
		logout.setVisibility(View.GONE);
	}

	@Override
	public int getFragmentTitleResourceId() {
		return R.string.mine;
	}

	@Override
	public boolean isNeedRemove() {
		return false;
	}


	@Override
	public void rightTitleClick(View v, BaseActivity context) {
	}


	@Override
	public void leftTitleClick(View v, BaseActivity context) {
	}


	@Override
	public int getRightTitle() {
		return R.drawable.ic_config;
	}


	@Override
	public int getLeftTitle() {
		return R.drawable.ic_back_white;
	}


	@Override
	public boolean isLeftTitleHide() {
		return false;
	}


	@Override
	public boolean isRightTitleHide() {
		return false;
	}


	@Override
	public boolean isNavBarHide() {
		return false;
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode) { //
		case Activity.RESULT_OK:
			if (requestCode == LOGIN) {

				initData();

			}

		}
		
	}
	
	

}
