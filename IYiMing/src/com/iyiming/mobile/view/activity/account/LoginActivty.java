/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014��11��15�� ����10:43:37
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.account;

import org.json.JSONException;
import org.json.JSONObject;

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
	private static final String EP_TAG = "ep";
	/**验证吗*/
	private static final String Cvc="cvc";

	
	private Button btnLogin;
	private Button btnRegister;
	private EditText username;
	private EditText password;
	
	private ImageView signImage;
	private EditText signText;
	private LinearLayout signContainer;
	
	private boolean isSignImageOn=false;
	private String signImageCode="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

//		login("slbw2012", "123456");
//		resetPassword("123456","1234567","1234567");
//		cvc("13966761823");
		//762225
//		register("13966761823","slbw2012","123456","901897");
		Log.e("ayiming","进入程序");
		initView();
		initData();
		initListener();
		
		
	}
	
	private void initView()
	{
		btnLogin=(Button)findViewById(R.id.login);
		btnRegister=(Button)findViewById(R.id.register);
		username=(EditText)findViewById(R.id.uname);
		password=(EditText)findViewById(R.id.pwd);
		signImage=(ImageView)findViewById(R.id.sign_image);
		signText=(EditText)findViewById(R.id.sign_text);
		signContainer=(LinearLayout)findViewById(R.id.sign_container);
	}
	
	private void initData()
	{
		
	}
	
	private void initListener()
	{
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				login(username.getText().toString(),password.getText().toString());
			}
		});
		
		btnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent();
				intent.setClass(LoginActivty.this, RegisterActivity.class);
				startActivity(intent);
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
		
		if(username.length()==0)
		{
			showToast("手机号码不能为空");
			return;
		}
		if(!AppHelper.isPhoneNumber(username))
		{
			showToast("手机号码格式不正确");
			//return;
		}
		if(password.length()<6||password.length()>16)
		{
			showToast("密码长度应该是6-16位之间");
		}
		if(isSignImageOn)
		{
			if(!signImageCode.equals(signText.getText().toString()))
			{
				showToast("验证码错误");
				return;
			}
		}
		post(LOGIN_TAG, addParam(LOGIN_TAG, username, MD5Util.SharedMD5Util().Md5(password)),false);// 用户登录
	}
	

	@Override
	public boolean onResponseOK(Object response, String tag) {
		if( super.onResponseOK(response, tag))
		{
			String s=(String) response;
			if(tag.equalsIgnoreCase(LOGIN_TAG))//登录成功
			{
				showToast("登录成功");
				AppHelper.LOGIN_FAIL_TIMES=1;//重置次数
				User user=new User();
				user.setSessionId(IYiMingApplication.SESSION_ID);
				application.user=user;
				saveUser(user);//保存用户到持久化数据
				Intent intent=new Intent();
				intent.setClass(LoginActivty.this, MainActivity.class);
				startActivity(intent);
			}
			else if(tag.equalsIgnoreCase("cci"))
			{
				try {
					JSONObject json=new JSONObject(s);
					signImageCode=json.getString("memo");//获取验证码文字
					Bitmap bitmap = Bitmap2Base64Util.base64ToBitmap(json.getString("image"));
					signImage.setImageBitmap(bitmap);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		else//失败的处理情况
		{
			if(tag.equalsIgnoreCase(LOGIN_TAG))//登录成功
			{
				if(AppHelper.LOGIN_FAIL_TIMES<3)//低于三次则不显示验证码
				{
					AppHelper.LOGIN_FAIL_TIMES++;
				}
				else
				{
					signContainer.setVisibility(View.VISIBLE);
					isSignImageOn=true;
					//请求验证码
					cci();
				}
				
			}
		}
		
			return true;
	}
	

	private void resetPassword(String oldPwd, String newPwd, String newPwd2) {

		post(EP_TAG,
				addParam(EP_TAG, MD5Util.SharedMD5Util().Md5(oldPwd), MD5Util.SharedMD5Util().Md5(newPwd), MD5Util.SharedMD5Util().Md5(newPwd2)),true);// 用户登录
	}
	
//	private void cvc(String phone) {
//		post(Cvc,
//				addParam(Cvc,phone),true);// 用户登录
//	}

	
	/**获取图片验证码*/
	private void cci() {
		post("cci",
				addParam("cci"),false);// 用户登录
	}
	
	
	/**
	 * 保存user
	 * @param user
	 */
	private void saveUser(User user)
	{
		SerializationUtil.sharedSerializationUtil().serialize(this,user);
	}

}
