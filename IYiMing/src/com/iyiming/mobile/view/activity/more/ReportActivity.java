/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年12月7日 上午2:30:29
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.more;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.AppHelper;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.widget.NavBar;

/**
 * @DESCRIBE ...
 */
public class ReportActivity extends BaseActivity{

	private NavBar navBar;
	private final String fb="fb";
	private EditText content;
	private TextView textCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		navBar = (NavBar) findViewById(R.id.navBar1);
		navBar.hideLeft(false);
		navBar.hideRight(false);
		navBar.isNav(true);
		content = (EditText) findViewById(R.id.content);
		textCount = (TextView) findViewById(R.id.text_count);
		
		navBar.OnLeftClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		navBar.OnRightClick(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				sendReport(content.getText().toString());
			}
		});
		
		content.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				textCount.setText(s.toString().length() + "/300字");
			}

		});
		
	}
	
	
	
	@Override
	public boolean onResponseOK(Object response, String tag) {
		if(super.onResponseOK(response, tag))
		{
			if(tag.equals(fb))
			{
				showToast("感谢您的建议！");
				finish();
			}
		}
		return true;
	}



	/**
	 * 发送报告
	 * @param content
	 */
	private void sendReport(String content)
	{
		if(content.length()==0)
		{
			showToast("写点内容吧，亲");
			return;
		}
		String deviceName=android.os.Build.MODEL;
		String osName=android.os.Build.VERSION.RELEASE;
		String appVersionName=AppHelper.getVersion(this);
		post(fb, addParam(fb,content,deviceName,osName,appVersionName), true, fb);

	}
	

}
