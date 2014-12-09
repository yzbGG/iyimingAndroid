/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月18日 下午11:59:34
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.account;

import android.app.Activity;
import android.content.Intent;
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
import com.iyiming.mobile.view.widget.searchlist.CitySelector;
import com.iyiming.mobile.view.widget.searchlist.SortModel;

/**
 * @DESCRIBE ...
 */
public class RegisterActivity extends BaseActivity {

	private EditText phone;
	private TextView getSign;
	private EditText sign;
	private EditText pwd;
	private Button btnRegister;
	private TextView city;
	private LinearLayout back;
	/** 手机验证码 */
	private final String cvc = "cvc";

	private final int RESULT_CITY = 10001;
	/** 注册 */
	private static final String register = "register";
	private String signedPhoneNumber = "";
	private String signedPassword = "";

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
		city = (TextView) findViewById(R.id.city);
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

				register(signedPhoneNumber, signedPhoneNumber, pwd.getText().toString(), sign.getText().toString(), city.getText().toString());

			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, CitySelector.class);
				startActivityForResult(intent, RESULT_CITY);
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
			post(cvc, addParam(cvc, mobile), false, cvc);// 获取手机验证码
			signedPhoneNumber = mobile;
		} else {
			showToast("请输入正确的手机号");
		}
	}

	private void register(String mobile, String username, String password, String validateCode, String citys) {
		if (validateCode.length() == 0) {
			showToast("验证码不能为空");
		}
		if (password.length() >= 6) {
			signedPhoneNumber=mobile;
			signedPassword=password;
			post(register, addParam(register, mobile, username, MD5Util.SharedMD5Util().Md5(password), validateCode, citys), false, register);// 用户登录
		} else {
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
				
				Intent intent = new Intent();
				intent.putExtra("type","OK");
				intent.putExtra("username",signedPhoneNumber);
				intent.putExtra("password",signedPassword);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) { //
		case Activity.RESULT_OK:
			if (requestCode == RESULT_CITY) {

				Bundle b = data.getExtras();
				String name = b.getString("city_name");
				city.setText(name);
			}
		}
	}

}
