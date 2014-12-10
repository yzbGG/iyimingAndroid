/**   
* @Title: SexDialog.java 
* @Package com.iyiming.mobile.view.widget 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dkslbw@gmail.com   
* @date 2014年12月10日 上午10:48:15 
* @version V1.0   
*/
package com.iyiming.mobile.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.iyiming.mobile.R;

/** 
 * @ClassName: SexDialog 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author dkslbw@gmail.com
 * @date 2014年12月10日 上午10:48:15 
 *  
 */
public class SexDialog extends Dialog{
	
	private LinearLayout man;
	private LinearLayout women;

	/**
	 * @param context
	 */
	public SexDialog(Context context) {
		super(context,R.style.dialogSodino);
		setContentView(R.layout.dialog_sex);
		man=(LinearLayout)findViewById(R.id.man);
		women=(LinearLayout)findViewById(R.id.women);
	}
	
	public void setOnManClickListener(View.OnClickListener listener)
	{
		man.setOnClickListener(listener);
	}
	
	public void setOnWomenClickListener(View.OnClickListener listener)
	{
		women.setOnClickListener(listener);
	}
}
