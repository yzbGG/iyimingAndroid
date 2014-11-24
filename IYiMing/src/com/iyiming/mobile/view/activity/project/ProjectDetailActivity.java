/**   
* @Title: ProjectDetailActivity.java 
* @Package com.iyiming.mobile.view.activity.project 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dkslbw@gmail.com   
* @date 2014年11月22日 下午2:05:30 
* @version V1.0   
*/
package com.iyiming.mobile.view.activity.project;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.widget.NavBar;

/** 
 * @ClassName: ProjectDetailActivity 
 * @Description: TODO(项目详情) 
 * @author dkslbw@gmail.com
 * @date 2014年11月22日 下午2:05:30 
 *  
 */
public class ProjectDetailActivity extends BaseActivity{
	
	NavBar navBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project);	
		initView();
		setListener();
		}
	
	private void initView()
	{
		navBar=(NavBar)findViewById(R.id.navBar1);
		navBar.setTitle("项目详情");
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
	}
	
	private void setListener()
	{

		navBar.OnLeftClick(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		navBar.OnRightClick(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//保存
			}
		});
	}
	
}
