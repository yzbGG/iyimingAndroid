/**   
* @Title: SexDialog.java 
* @Package com.iyiming.mobile.view.widget 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dkslbw@gmail.com   
* @date 2014年12月10日 上午10:48:15 
* @version V1.0   
*/
package com.iyiming.mobile.view.widget;

import com.iyiming.mobile.R;

import android.app.Dialog;
import android.content.Context;

/** 
 * @ClassName: SexDialog 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author dkslbw@gmail.com
 * @date 2014年12月10日 上午10:48:15 
 *  
 */
public class SexDialog extends Dialog{

	/**
	 * @param context
	 */
	public SexDialog(Context context) {
		super(context,R.style.dialogSodino);
		setContentView(R.layout.dialog_sex);
		
		
	}
	
	

}
