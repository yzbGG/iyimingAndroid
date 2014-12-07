/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月23日 上午11:11:01
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.MD5Util;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.widget.NavBar;

/**
 * @DESCRIBE ...
 */
public class EditPasswordActivity extends BaseActivity implements TextWatcher{

	NavBar navBar;
	private EditText oldPwd;
	private EditText newPwd;
	private EditText newPwd2;
	private Button btnOk;
	
	private static final String EP_TAG = "ep";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_password);
		initView();
		setListener();
	}

	private void initView() {
		navBar = (NavBar) findViewById(R.id.navBar1);
		navBar.setTitle("编辑密码");
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
		oldPwd = (EditText) findViewById(R.id.oldpwd);
		newPwd = (EditText) findViewById(R.id.newpwd);
		newPwd2 = (EditText) findViewById(R.id.newpwd2);
		btnOk = (Button) findViewById(R.id.button1);
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
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 提交
				resetPassword(oldPwd.getText().toString(),newPwd.getText().toString(),newPwd2.getText().toString());
			}
		});
		
		oldPwd.addTextChangedListener(this);
		
		newPwd.addTextChangedListener(this);
		
		newPwd2.addTextChangedListener(this);
		
		
	}
	
	
	

	@Override
	public boolean onResponseOK(Object response, String tag) {
		if(super.onResponseOK(response, tag))
		{
			if(tag.equalsIgnoreCase(EP_TAG))
			{
				showToast("修改成功");
				finish();
			}
		}
		return true;
	}

	private void resetPassword(String oldPwd, String newPwd, String newPwd2) {
		

		if(!newPwd.equals(newPwd2))
		{
			showToast("两次密码输入不相符，请重新输入");
			return;
		}
		post(EP_TAG,
				addParam(EP_TAG, MD5Util.SharedMD5Util().Md5(oldPwd), MD5Util.SharedMD5Util().Md5(newPwd), MD5Util.SharedMD5Util().Md5(newPwd2)),
				true, EP_TAG);// 用户修改面膜
	}

	@Override
	public void afterTextChanged(Editable arg0) {
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		
		if(oldPwd.length()>=6&&newPwd.length()>=6&&newPwd2.length()>=6)
		{
			btnOk.setEnabled(true);
				btnOk.setBackgroundResource(R.drawable.btn_login);
		}
		else
		{
			btnOk.setEnabled(false);
			btnOk.setBackgroundResource(R.drawable.btn_pwd_default);
		}
		
	}

}
