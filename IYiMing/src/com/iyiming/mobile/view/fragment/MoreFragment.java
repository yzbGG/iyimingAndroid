/**   
 * @Title: MoreFragment.java 
 * @Package com.iyiming.mobile.view.fragment 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dkslbw@gmail.com   
 * @date 2014年11月21日 下午2:24:16 
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
import com.iyiming.mobile.view.activity.more.AboutActivity;
import com.iyiming.mobile.view.activity.more.ReportActivity;
import com.iyiming.mobile.view.widget.ShareDialog;

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
		ShareDialog dialog=new ShareDialog(getActivity());
		dialog.showDialog();
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
			Intent intent=new Intent();
			intent.setClass(getActivity(), AboutActivity.class);
			startActivity(intent);
		} else if (v == tabService) {
			Intent intent=new Intent();
			intent.setClass(getActivity(), ReportActivity.class);
			startActivity(intent);
		} else if (v == tabContact) {
			
		} else if (v == tabVersion) {
			
		}
	}
	


}
