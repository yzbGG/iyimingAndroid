/**   
 * @Title: ProjectDetailActivity.java 
 * @Package com.iyiming.mobile.view.activity.project 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dkslbw@gmail.com   
 * @date 2014年11月22日 下午2:05:30 
 * @version V1.0   
 */
package com.iyiming.mobile.view.activity.project;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iyiming.mobile.R;
import com.iyiming.mobile.model.project.Project;
import com.iyiming.mobile.util.AppInfoUtil;
import com.iyiming.mobile.util.ImageManager;
import com.iyiming.mobile.util.LoadImageUtil;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.account.LoginActivty;
import com.iyiming.mobile.view.widget.NavBar;

/**
 * @ClassName: ProjectDetailActivity
 * @Description: TODO(项目详情)
 * @author dkslbw@gmail.com
 * @date 2014年11月22日 下午2:05:30
 * 
 */
public class ProjectDetailActivity extends BaseActivity {

	NavBar navBar;
	private int id = 0;
	private final String gpd = "gpd";
	private final String aoc = "aoc";
	// 关注
	private final String doaoc = "doaoc";
	// 取消关注
	private final String unaoc = "unaoc";
	private Project projectDetail;
	private TextView itemTitle;
	private TextView itemMoney;
	private TextView itemInfo;
	private TextView itemAttentionCount;
	private ImageView itemImage;
	private Button btnAttention;

	private final int LOGIN = 10001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		navBar = (NavBar) findViewById(R.id.navBar1);
		navBar.setTitle("项目详情");
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
		itemImage = (ImageView) findViewById(R.id.item_image);
		itemTitle = (TextView) findViewById(R.id.item_title);
		itemMoney = (TextView) findViewById(R.id.item_money);
		itemInfo = (TextView) findViewById(R.id.item_info);
		itemAttentionCount = (TextView) findViewById(R.id.item_attentioncount);
		btnAttention = (Button) findViewById(R.id.button1);

	}

	private void initData() {
		Intent intent = this.getIntent();
		id = intent.getIntExtra("id", 0);
		getProjectDetial(id);
	}

	/**
	 * 获取项目详情
	 * 
	 * @param id
	 */
	private void getProjectDetial(int id) {
		post(gpd, addParam(gpd, String.valueOf(id)), false, gpd);
	}

	/**
	 * 关注/取消关注
	 * 
	 * @param id
	 * @param type
	 *            A 关注 C取消
	 */
	private void attention(int id, String type) {
		if (type.equalsIgnoreCase("A")) {
			post(aoc, addParam(aoc, String.valueOf(id), type), false, doaoc);
		} else if (type.equalsIgnoreCase("C")) {
			post(aoc, addParam(aoc, String.valueOf(id), type), false, unaoc);
		}
	}

	/**
	 * 登录检查
	 * 
	 * @return
	 */
	private boolean loginCheck() {
		return application.isLoged;
//		return true;
	}

	private void setListener() {

		navBar.OnLeftClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		navBar.OnRightClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 保存
			}
		});
		btnAttention.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (projectDetail != null && projectDetail.getAttentionFlag().equalsIgnoreCase("Y")) {
					attention(id, "C");
				} else if (projectDetail != null && projectDetail.getAttentionFlag().equalsIgnoreCase("N")) {
					if (loginCheck()) {
						attention(id, "A");
					} else {
						showToast("您还没登录，请先登录");
						Intent intent = new Intent();
						intent.setClass(ProjectDetailActivity.this, LoginActivty.class);
						startActivityForResult(intent, LOGIN);
					}
				}
			}
		});
	}

	@Override
	public boolean onResponseOK(Object response, String tag) {
		if (super.onResponseOK(response, tag)) {
			if (tag.equalsIgnoreCase(gpd)) {
				JSONObject json;
				try {
					json = new JSONObject((String) response);
					Type type = new TypeToken<Project>() {
					}.getType();
					String data = json.getString("projectDetail");
					projectDetail = new Gson().fromJson(data, type);
					setData(projectDetail);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (tag.equalsIgnoreCase(doaoc)) {
				showToast("关注成功");
				projectDetail.setAttentionFlag("Y");
				setBtnState(projectDetail);
			} else if (tag.equalsIgnoreCase(unaoc)) {
				showToast("已取消关注");
				projectDetail.setAttentionFlag("N");
				setBtnState(projectDetail);
			}
		}

		return true;
	}

	private void setData(Project projectDetail) {
		ImageManager.getInstance(this).getImage(itemImage, AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + projectDetail.getImageUrl());
		itemTitle.setText(projectDetail.getName());
		itemInfo.setText(projectDetail.getIntro());
		itemMoney.setText(projectDetail.getAmt() + "￥");
		itemAttentionCount.setText("已经有" + projectDetail.getAttentionCount() + "人关注");
		setBtnState(projectDetail);
	}
	
	private void setBtnState(Project projectDetail)
	{
		if (projectDetail.getAttentionFlag().equalsIgnoreCase("Y")) {
			btnAttention.setText("已关注");
			btnAttention.setBackgroundResource(R.drawable.followed);
		} else {
			btnAttention.setText("+ 关注");
			btnAttention.setBackgroundResource(R.drawable.follow);
		}
	}

}
