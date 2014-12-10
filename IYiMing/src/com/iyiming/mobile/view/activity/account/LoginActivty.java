/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014��11��15�� ����10:43:37
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.account;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iyiming.mobile.R;
import com.iyiming.mobile.model.User;
import com.iyiming.mobile.util.AppHelper;
import com.iyiming.mobile.util.Bitmap2Base64Util;
import com.iyiming.mobile.util.MD5Util;
import com.iyiming.mobile.util.SerializationUtil;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.IYiMingApplication;
import com.iyiming.mobile.view.activity.MainActivity;

/**
 * @DESCRIBE ...
 */
public class LoginActivty extends BaseActivity {

	private static final String LOGIN_TAG = "login";

	/** 验证吗 */
	private static final String Cvc = "cvc";

	private final String gp = "gp";

	public LinearLayout back;
	private Button btnLogin;
	private Button btnRegister;
	private EditText username;
	private EditText password;

	private ImageView signImage;
	private EditText signText;
	private LinearLayout signContainer;

	private boolean isSignImageOn = false;
	private String signImageCode = "";

	private String rightUserName = "";
	private String rightPassword = "";

	private final int CODE_REGISTER = 10001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		Log.e("ayiming", "进入程序");
		initView();
		initData();
		initListener();

	}

	private void initView() {
		btnLogin = (Button) findViewById(R.id.login);
		btnRegister = (Button) findViewById(R.id.register);
		username = (EditText) findViewById(R.id.uname);
		password = (EditText) findViewById(R.id.pwd);
		signImage = (ImageView) findViewById(R.id.sign_image);
		signText = (EditText) findViewById(R.id.sign_text);
		signContainer = (LinearLayout) findViewById(R.id.sign_container);
		back = (LinearLayout) findViewById(R.id.back);

	}

	private void initData() {

	}

	private void initListener() {
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				login(username.getText().toString(), password.getText().toString());
				btnLogin.setEnabled(false);
			}
		});

		btnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(LoginActivty.this, RegisterActivity.class);
				startActivityForResult(intent, CODE_REGISTER);
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 */
	private void login(String username, String password) {

		if (username.length() == 0) {
			showToast("手机号码不能为空");
			return;
		}
		if (!AppHelper.isPhoneNumber(username)) {
			showToast("手机号码格式不正确");
			// return;
		}
		if (password.length() < 6 || password.length() > 16) {
			showToast("密码长度应该是6-16位之间");
		}
		if (isSignImageOn) {
			if (!signImageCode.equals(signText.getText().toString())) {
				showToast("验证码错误");
				return;
			}
		}
		post(LOGIN_TAG, addParam(LOGIN_TAG, username, MD5Util.SharedMD5Util().Md5(password)), false, LOGIN_TAG);// 用户登录
		rightUserName = username;
	}

	@Override
	public boolean onResponseOK(Object response, String tag) {
		btnLogin.setEnabled(true);
		if (super.onResponseOK(response, tag)) {
			String s = (String) response;
			if (tag.equalsIgnoreCase(LOGIN_TAG))// 登录成功
			{
				showToast("登录成功");
				AppHelper.LOGIN_FAIL_TIMES = 1;// 重置次数
				// User user=new User();
				// user.setSessionId(IYiMingApplication.SESSION_ID);
				// user.setUsername(rightUserName);
				// application.user=user;
				// saveUser(user);//保存用户到持久化数据
				// Intent intent=new Intent();
				// intent.setClass(LoginActivty.this, MainActivity.class);
				// startActivity(intent);
				getUserProfile();
			} else if (tag.equalsIgnoreCase("cci")) {
				try {
					JSONObject json = new JSONObject(s);
					signImageCode = json.getString("memo");// 获取验证码文字
					Bitmap bitmap = Bitmap2Base64Util.base64ToBitmap(json.getString("image"));
					signImage.setImageBitmap(bitmap);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (tag.equalsIgnoreCase(gp)) {
				try {
					JSONObject json = new JSONObject((String) response);
					json.remove("memo");
					json.remove("status");
					String data = json.toString();
					Type type = new TypeToken<User>() {
					}.getType();
					User user = new Gson().fromJson(data, type);
					user.setSessionId(IYiMingApplication.SESSION_ID);
					application.user = user;
					application.isLoged = true;
					application.saveUser();// 保存用户到持久化数据
					// Intent intent=new Intent();
					// intent.setClass(LoginActivty.this, MainActivity.class);
					// startActivity(intent);

					setResult(RESULT_OK);
					finish();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else// 失败的处理情况
		{
			if (tag.equalsIgnoreCase(LOGIN_TAG))// 登录成功
			{
				// if(AppHelper.LOGIN_FAIL_TIMES<3)//低于三次则不显示验证码
				// {
				// AppHelper.LOGIN_FAIL_TIMES++;
				// }
				// else
				// {
				// // signContainer.setVisibility(View.VISIBLE);
				// // isSignImageOn=true;
				// //请求验证码
				// // cci();
				// }
				//
			}
		}

		return true;
	}

	@Override
	public void onResponseError(VolleyError arg0, String tag) {
		super.onResponseError(arg0, tag);
		btnLogin.setEnabled(true);
	}

	private void getUserProfile() {
		post(gp, addParam(gp), true, gp);// 获取个人资料
	}

	/** 获取图片验证码 */
	private void cci() {
		post("cci", addParam("cci"), false, "cci");// 用户登录
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) { //
		case Activity.RESULT_OK:
			if (requestCode == CODE_REGISTER) {

				Bundle b = data.getExtras();
				String type = b.getString("state");
				if (type.equalsIgnoreCase("OK")) {
					String name = b.getString("username");
					String pwd = b.getString("password");
					username.setText(name);
					password.setText(pwd);
					login(username.getText().toString(), password.getText().toString());
				}
				

			}
		}
	}

}
