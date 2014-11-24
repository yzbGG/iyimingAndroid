/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月18日 下午11:59:34
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.account;

import android.os.Bundle;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.BaseActivity;

/**
 * @DESCRIBE ...
 */
public class RegisterActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onResponseOK(Object response, String tag) {
		return super.onResponseOK(response, tag);
		
	}
	
	

}
