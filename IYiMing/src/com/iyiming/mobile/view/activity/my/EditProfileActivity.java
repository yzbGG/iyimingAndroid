/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月23日 上午11:31:17
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.widget.NavBar;

/**
 * @DESCRIBE ...
 */
public class EditProfileActivity extends BaseActivity {

	NavBar navBar;
	private final String cp = "cp";
	private EditText tvNickName;
	private String currentProfile = "";
	private String type = "";
	private TextView textInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_nickname);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		navBar = (NavBar) findViewById(R.id.navBar1);
		
		navBar.hideLeft(false);
		navBar.hideRight(false);
		navBar.isNav(true);
		tvNickName = (EditText) findViewById(R.id.nickName);
		textInfo=(TextView)findViewById(R.id.textInfo);
	}

	private void initData() {
		Intent intent = this.getIntent();
		type = intent.getStringExtra("type");
		if (type.equals("nickName")) {
			navBar.setTitle("修改昵称");
			tvNickName.getText().insert(0, application.user.getNickName() == null ? "" : application.user.getNickName());
			textInfo.setText("昵称必须是4个以上英文字符或2-5个中文字符");
		} else if (type.equals("name")) {
			navBar.setTitle("修改真实姓名");
			tvNickName.getText().insert(0,  application.user.getRealName() == null ? "" : application.user.getRealName());
			textInfo.setText("姓名必须是4个以上英文字符或2-5个中文字符");
		} else if (type.equals("sex")) {
			navBar.setTitle("修改性别");
			tvNickName.getText().insert(0, application.user.getSex() == null ? "" : application.user.getSex());
			textInfo.setText("");
		} else if (type.equals("address")) {
			navBar.setTitle("修改地址");
			tvNickName.getText().insert(0, application.user.getAddress() == null ? "" : application.user.getAddress());
			textInfo.setText("");
		}
	}

	private void saveProfile(String profile) {
		currentProfile = profile;
		String nickName = application.user.getNickName() == null ? "" : application.user.getNickName();
		String city = application.user.getCity() == null ? "" : application.user.getCity();
		String sex = application.user.getSex() == null ? "" : application.user.getSex();
		String realname = application.user.getRealName() == null ? "" : application.user.getRealName();
		String address = application.user.getAddress() == null ? "" : application.user.getAddress();
		if (type.equals("nickName")) {
			nickName = profile;
		} else if (type.equals("name")) {
			realname = profile;
		} else if (type.equals("sex")) {
			sex = profile;
		} else if (type.equals("address")) {
			address = profile;
		}
		post(cp, addParam(cp, city, sex, realname, address, nickName), true, cp);
	}

	private void setListener() {

		navBar.OnLeftClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		navBar.OnRightClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 保存
				saveProfile(tvNickName.getText().toString());


			
			}
		});
	}

	@Override
	public boolean onResponseOK(Object response, String tag) {
		if(super.onResponseOK(response, tag))
		{
			if(tag.equalsIgnoreCase(cp))
			{
				showToast("修改成功");

				if (type.equals("nickName")) {
					application.user.setNickName(currentProfile);
				} else if (type.equals("name")) {
					application.user.setRealName(currentProfile);
				} else if (type.equals("sex")) {
					application.user.setSex(currentProfile);
				} else if (type.equals("address")) {
					application.user.setAddress(currentProfile);
				}
				
				finish();
			}
		}
		return true;
	}
	
	
	

}
