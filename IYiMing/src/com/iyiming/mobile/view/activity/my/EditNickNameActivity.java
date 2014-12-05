/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月23日 上午11:31:17
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.my;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.widget.NavBar;

/**
 * @DESCRIBE ...
 */
public class EditNickNameActivity extends BaseActivity{
	
	NavBar navBar;
	private final String cp="cp";
	private TextView tvNickName;
	private String currentNickName="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_nickname);
		initView();
		setListener();
	}
	
	private void initView()
	{
		navBar=(NavBar)findViewById(R.id.navBar1);
		navBar.setTitle("修改昵称");
		navBar.hideLeft(false);
		navBar.hideRight(false);
		navBar.isNav(true);
		tvNickName=(TextView)findViewById(R.id.nickName);
	}
	
	
	private void saveNickName(String nickName)
	{
		currentNickName=nickName;
		String city=application.user.getCity()==null?"":application.user.getCity();
		String sex=application.user.getSex()==null?"":application.user.getSex();
		String realname=application.user.getRealName()==null?"":application.user.getRealName();
		String address=application.user.getAddress()==null?"":application.user.getAddress();
		post(cp, addParam(cp,city,sex,realname,address,nickName), true, cp);
	}
	
	private void setListener()
	{

		navBar.OnLeftClick(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		navBar.OnRightClick(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//保存
				saveNickName(tvNickName.getText().toString());
				showToast("修改成功");
				application.user.setNickName(currentNickName);
			}
		});
	}
	
	

}
