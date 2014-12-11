/**   
 * @Title: StartActivity.java 
 * @Package com.iyiming.mobile.view.activity 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dkslbw@gmail.com   
 * @date 2014年12月9日 下午3:58:45 
 * @version V1.0   
 */
package com.iyiming.mobile.view.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.AppHelper;

/**
 * @ClassName: StartActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author dkslbw@gmail.com
 * @date 2014年12月9日 下午3:58:45
 * 
 */
public class StartActivity extends Activity {

	Timer timer = new Timer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppHelper.appVersion=AppHelper.getVersion(this);//获取版本
		setContentView(R.layout.activity_start);
		setTimerTask();
	}

	private void setTimerTask() {

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}

		}, 1000, 2000);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int msgId = msg.what;

			switch (msgId) {
			case 1:

				StartActivity.this.finish();
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, MainActivity.class);
				startActivity(intent);

				break;

			}
		}
	};

	@Override
	public void finish() {
		super.finish();
		timer.cancel();
	}

}
