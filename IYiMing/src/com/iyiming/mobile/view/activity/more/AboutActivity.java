/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年12月7日 上午2:01:45
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.more;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.widget.NavBar;

/**
 * @DESCRIBE ...
 */
public class AboutActivity extends BaseActivity{
	private NavBar navBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		navBar = (NavBar) findViewById(R.id.navBar1);
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
		
		navBar.OnLeftClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
	

}
