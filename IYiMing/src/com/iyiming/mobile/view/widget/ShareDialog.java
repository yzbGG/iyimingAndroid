/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月23日 上午10:03:16
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.iyiming.mobile.R;

/**
 * @DESCRIBE ...
 */
public class ShareDialog extends Dialog{
	

    private Window window = null;

    private Context mContext;

    public ShareDialog(Context context) { 
	super(context, R.style.dialogFilterSodino);
	setContentView(R.layout.widget_share_dialog);
	
	
	mContext = context;
	
    }

    // 设置窗口显示
    public void windowDeploy() {
	window = getWindow();
	WindowManager.LayoutParams lp = window.getAttributes();
	lp.dimAmount = 0.5f;
	lp.width=WindowManager.LayoutParams.MATCH_PARENT;
//	lp.x = lp.x + DensityUtil.dip2px(getContext(), 5);
//	lp.y = lp.y + DensityUtil.dip2px(getContext(), 50);
	window.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
	window.setAttributes(lp);
//	 window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

    /**
     * 调用此方法显示dialog
     */
    public void showDialog() {
	// 设置触摸对话框意外的地方取消对话框
	setCanceledOnTouchOutside(true);
	windowDeploy();
	show();
    }




}
