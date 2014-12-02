/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月22日 下午10:21:06
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.project;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iyiming.mobile.R;
import com.iyiming.mobile.model.project.Project;
import com.iyiming.mobile.util.AppInfoUtil;
import com.iyiming.mobile.util.Constants;
import com.iyiming.mobile.util.ImageManager;
import com.iyiming.mobile.util.ImageUtil;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.widget.NavBar;
import com.iyiming.mobile.view.widget.XListView;

/**
 * @DESCRIBE ...
 */
public class ProjectListActivity extends BaseActivity {

	private XListView listView;

	NavBar navBar;

	/** 记录所有的资讯 */
	private List<Project> list = null;

	private Projectdapter mAdapter;
	
	private String type="1";
	
	private final String gpl = "gpl";
	private final String GPL_REFRESH = "gpl_refresh";
	private final String GPL_LOADMORE = "gpl_loadmore";

	private int page = 1;
	private String pageSize = "20";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		listView = (XListView) findViewById(R.id.xlistview_home);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		list = new ArrayList<Project>();
		mAdapter = new Projectdapter(this,list);
		listView.setAdapter(mAdapter);
		listView.setPullLoadEnable(true);

		navBar = (NavBar) findViewById(R.id.navBar1);
		navBar.setTitle("项目列表");
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
	}

	private void initData() {
		Intent intent=this.getIntent();
		type=intent.getStringExtra("type");
		getList(type);
	}

	private void initListener() {
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
		listView.setXListViewListener(new XListView.IXListViewListener() {
			@Override
			public void onRefresh() {
				post(gpl, addParam(gpl, pageSize, String.valueOf(1), null, null, null, null, null, null, null), false, GPL_REFRESH);
			}

			@Override
			public void onLoadMore() {
				post(gpl, addParam(gpl, pageSize, String.valueOf(page + 1), null, null, null, null, null, null, null), false, GPL_LOADMORE);
			}
		});
	}
	
	
	private void getList(String type)
	{
		String mtype=Constants.PROJECT_TYPE_LIST[Integer.valueOf(type)];
		post(gpl, addParam(gpl, pageSize, String.valueOf(1), null, null, null, null, null, null, mtype), false, GPL_REFRESH);
	}
	
	
	
	

	@Override
	public boolean onResponseOK(Object response, String tag) {
		listView.stopRefresh();
		listView.stopLoadMore();
		if (super.onResponseOK(response, tag)) {
			try {
				JSONObject json = new JSONObject((String) response);
				if (tag.equalsIgnoreCase(GPL_REFRESH)) {
					List<Project> projectDetails;
					String items = json.getString("projectDetails");
					if (null != items) {
						Type type = new TypeToken<List<Project>>() {
						}.getType();
						projectDetails = new Gson().fromJson(items, type);
						list.clear();
						list.addAll(projectDetails);
						mAdapter.notifyDataSetChanged();
						page = 1;
					}
				} else if (tag.equalsIgnoreCase(GPL_LOADMORE)) {
					List<Project> projectDetails1;
					String items1 = json.getString("projectDetails");
					if (null != items1) {
						Type type = new TypeToken<List<Project>>() {
						}.getType();
						projectDetails1 = new Gson().fromJson(items1, type);

						if (projectDetails1 != null && projectDetails1.size() > 0) {
							page++;
							list.addAll(projectDetails1);
							mAdapter.notifyDataSetChanged();
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return true;
	}





	private class Projectdapter extends BaseAdapter {

		private Context con;
		private List<Project> datas;

		public Projectdapter(Context con, List<Project> datas) {
			this.con = con;
			this.datas = datas;
		}

//		/**
//		 * 刷新数据
//		 * 
//		 * @param news
//		 */
//		public void refreshData(List<HashMap<String, Object>> datas) {
//			if (null == datas) {
//				this.datas = new ArrayList<HashMap<String, Object>>();
//			} else {
//				this.datas = datas;
//			}
//			notifyDataSetChanged();
//		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int arg0) {
			return datas.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(con).inflate(R.layout.list_item_project, parent, false);
				holder.itemImage = (ImageView) convertView.findViewById(R.id.item_image);
				holder.itemMoney = (TextView) convertView.findViewById(R.id.item_money);
				holder.itemTitle = (TextView) convertView.findViewById(R.id.item_title);
				holder.itemInfo = (TextView) convertView.findViewById(R.id.item_info);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final String imgUrl = AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + datas.get(position).getImageUrl();
			// 给 ImageView 设置一个 tag
			holder.itemImage.setTag(imgUrl);
			ImageManager.getInstance(ProjectListActivity.this).getImage(holder.itemImage, imgUrl);
			holder.itemMoney.setText(datas.get(position).getAmt() + "￥");
			holder.itemTitle.setText(datas.get(position).getName());
			holder.itemInfo.setText(datas.get(position).getIntro());

			return convertView;
		}

		class ViewHolder {
			ImageView itemImage;
			TextView itemMoney;
			TextView itemTitle;
			TextView itemInfo;

		}

	}

}
