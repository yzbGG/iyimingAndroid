/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月23日 上午11:11:01
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.my;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.widget.NavBar;

/**
 * @DESCRIBE ...
 */
public class EditPasswordActivity extends BaseActivity{
	
	NavBar navBar;
	private EditText oldPwd;
	private EditText newPwd;
	private EditText newPwd2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_password);
		initView();
		setListener();
	}
	
	
	private void initView()
	{
		navBar=(NavBar)findViewById(R.id.navBar1);
		navBar.setTitle("编辑密码");
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
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
			}
		});
	}
	
	

}
