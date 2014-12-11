/**@文件:UpdateDialog.java
 * @作者:dkslbw@gmail.com
 * @日期:2014年7月30日 下午4:05:58*/
package com.iyiming.mobile.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iyiming.mobile.R;


/**
 * 
* @ClassName: UpdateDialog 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author dkslbw@gmail.com
* @date 2014年12月11日 上午10:59:50 
*
 */

public class UpdateDialog extends Dialog implements android.view.View.OnClickListener{

	/** 确定按钮 */
	private Button certain;
	/** 取消按钮 */
	private Button cancel;
	/** 新版本更新内容 */
	private TextView updateInfo;
	/** 新版本 */
	private TextView tv;

	private OnUpdateClickListener listener;

	public UpdateDialog(Context context) {
		super(context, R.style.dialogFilterSodino);
		setContentView(R.layout.widget_update);
		certain = (Button) findViewById(R.id.certain);
		cancel = (Button) findViewById(R.id.cancel);
		tv=(TextView) findViewById(R.id.textView1);
		updateInfo = (TextView) findViewById(R.id.whats_new);
		certain.setFocusable(true);
		certain.requestFocus();

		certain.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if (listener != null) {
			switch (v.getId()) {
			case R.id.certain:
				listener.OnCertainClick();
				break;
			case R.id.cancel:
				listener.OnCancelClickListener();
				break;
			}
		}
		dismiss();
	}

	public interface OnUpdateClickListener {
		public void OnCertainClick();

		public void OnCancelClickListener();
	}

	public void setOnUpdateClickListener(OnUpdateClickListener listener) {
		this.listener = listener;
	}

	public void setText(String s,String version) {
		tv.setText("爱移民 "+version);
		updateInfo.setText(s.replace("|","\n"));
	}

	@Override
	public void show() {
	//	 this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);  
		super.show();
	}

}
