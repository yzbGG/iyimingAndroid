/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月18日 下午11:59:34
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.account;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.AppHelper;
import com.iyiming.mobile.util.MD5Util;
import com.iyiming.mobile.view.activity.BaseActivity;

/**
 * @DESCRIBE ...
 */
public class RegisterActivity extends BaseActivity {

	private EditText phone;
	private TextView getSign;
	private EditText sign;
	private EditText pwd;
	private Button btnRegister;
	private LinearLayout back;
	/** 手机验证码 */
	private final String cvc = "cvc";

	/** 注册 */
	private static final String register = "register";
	private String signedPhoneNumber="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		initData();
		initListener();

	}

	private void initView() {
		phone = (EditText) findViewById(R.id.phone);
		getSign = (TextView) findViewById(R.id.getSign);
		sign = (EditText) findViewById(R.id.sign);
		pwd = (EditText) findViewById(R.id.pwd);
		btnRegister = (Button) findViewById(R.id.register);
		back = (LinearLayout) findViewById(R.id.back);
	}

	private void initData() {

	}

	private void initListener() {
		getSign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getSignCode(phone.getText().toString());

			}
		});

		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				register(signedPhoneNumber,signedPhoneNumber,pwd.getText().toString(), sign.getText().toString());

			}
		});
	}

	/**
	 * 获取手机验证码
	 */
	private void getSignCode(String mobile) {
		if (mobile.length() == 0) {
			showToast("请输入手机号");
			return;
		}
		if (AppHelper.isPhoneNumber(mobile)) {
			post(cvc, addParam(cvc, mobile), false);// 获取手机验证码
			signedPhoneNumber=mobile;
		} else {
			showToast("请输入正确的手机号");
		}
	}

	private void register(String mobile, String username, String password, String validateCode) {
		if(validateCode.length()==0)
		{
			showToast("验证码不能为空");
		}
		if(password.length()>=6)
		{
			post(register, addParam(register, mobile, username, MD5Util.SharedMD5Util().Md5(password),validateCode,"南京"), false);// 用户登录
		}
		else
		{
			showToast("密码长度不能小于6位");
		}
	}

	@Override
	public boolean onResponseOK(Object response, String tag) {
		if (super.onResponseOK(response, tag)) {
			String s = (String) response;
			if (tag.equalsIgnoreCase(cvc))// 获取验证码成功
			{
				showToast("验证码已发送，请注意查收");
			} else if (tag.equalsIgnoreCase(register)) {
				showToast("注册成功");
			}
		}
		return true;
	}

}
