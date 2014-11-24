/**   
* @Title: MineFragment.java 
* @Package com.iyiming.mobile.view.fragment 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dkslbw@gmail.com   
* @date 2014年11月21日 下午2:20:03 
* @version V1.0   
*/
package com.iyiming.mobile.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.my.EditPasswordActivity;
import com.iyiming.mobile.view.activity.my.UserInfoActivity;
import com.iyiming.mobile.view.activity.project.ProjectDetailActivity;

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
	}
	
	private void initData()
	{
		
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

}
