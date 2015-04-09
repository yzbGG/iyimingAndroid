/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年12月6日 上午11:03:23
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.my;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iyiming.mobile.R;
import com.iyiming.mobile.model.project.Project;
import com.iyiming.mobile.util.AppHelper;
import com.iyiming.mobile.util.AppInfoUtil;
import com.iyiming.mobile.util.Constants;
import com.iyiming.mobile.util.ImageManager;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.project.ProjectDetailActivity;
import com.iyiming.mobile.view.widget.NavBar;
import com.iyiming.mobile.view.widget.PopSelector;
import com.iyiming.mobile.view.widget.XListView;
import com.iyiming.mobile.view.widget.roundedimageview.RoundedImageView;

/**
 * @DESCRIBE ...
 */
public class FollowActivity extends BaseActivity implements OnItemClickListener{
	
private XListView listView;

private final String gpl = "gpl";
private final String GPL_REFRESH = "gpl_refresh";
private final String GPL_LOADMORE = "gpl_loadmore";

private int page = 1;
private String pageSize = "20";
NavBar navBar;

private RelativeLayout tabCountry;

private RelativeLayout tabMoney;

private RelativeLayout tabTime;

private String itemsCountry = null;
private String itemsMoney = null;
private String itemsDate = null;

private TextView textCountry;
private TextView textMoney;
private TextView textDate;
	
	/** 记录所有的资讯 */
	private List<Project> list = null;
	
	private FollowAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_follow);
		initView();
		initData();
		initListener();
	}
	
	
	private void initView()
	{
		navBar = (NavBar) findViewById(R.id.navBar1);
		navBar.setTitle("我的关注");
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
		listView = (XListView) findViewById(R.id.xlistview_home);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		listView.setOnItemClickListener(this);
		list=new ArrayList<Project>();
		mAdapter = new FollowAdapter(this, list);
		listView.setAdapter(mAdapter);
		listView.setPullLoadEnable(true);
		
		tabCountry = (RelativeLayout) findViewById(R.id.tabCountry);
		tabMoney = (RelativeLayout) findViewById(R.id.tabMoney);
		tabTime = (RelativeLayout) findViewById(R.id.tabTime);
		
		textCountry=(TextView)findViewById(R.id.textcountry);
		textMoney=(TextView)findViewById(R.id.textmoney);
		textDate=(TextView)findViewById(R.id.textdate);
	}

	
	private void initData()
	{
		getMyAttentionList(1,GPL_REFRESH);
	}
	
	private void initListener()
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
				// 保存
			}
		});
		listView.setXListViewListener(new XListView.IXListViewListener() {
			@Override
			public void onRefresh() {
//				post(gpl, addParam(gpl, pageSize, String.valueOf(1), null, null, null, null, null, null, type), false, GPL_REFRESH);
				getMyAttentionList(1,GPL_REFRESH);
			}

			@Override
			public void onLoadMore() {
//				post(gpl, addParam(gpl, pageSize, String.valueOf(page + 1), null, null, null, null, null, null, type), false, GPL_LOADMORE);
				getMyAttentionList(page+1,GPL_LOADMORE);
			}
		});
		
		
		tabCountry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {
					
					PopSelector.getInstance(FollowActivity.this).setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							if (arg2 == 0) {
								itemsCountry = null;
							} else {
								itemsCountry = Constants.COUNTRY_LIST[arg2];
							}
							textCountry.setText(Constants.COUNTRY_LIST[arg2]);
							PopSelector.getInstance(FollowActivity.this).hidePopWindow();
							getMyAttentionList(1, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(FollowActivity.this).ShowSelector(Constants.COUNTRY_LIST, arg0);
				} else {
					PopSelector.getInstance(FollowActivity.this).hidePopWindow();
				}
			}
		});
		
		tabMoney.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {
					
					PopSelector.getInstance(FollowActivity.this).setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							if (arg2 == 0) {
								itemsMoney = null;
							} else if (arg2 == 1) {
								itemsMoney = "A";
							} else if (arg2 == 2) {
								itemsMoney = "B";
							}
							textMoney.setText(Constants.MONEY_LIST[arg2]);
							PopSelector.getInstance(FollowActivity.this).hidePopWindow();
							getMyAttentionList(1, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(FollowActivity.this).ShowSelector(Constants.MONEY_LIST, arg0);
				} else {
					PopSelector.getInstance(FollowActivity.this).hidePopWindow();
				}
			}
		});
	
		tabTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {
				
					PopSelector.getInstance(FollowActivity.this).setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							if (arg2 == 0) {
								itemsDate = null;
							} else if (arg2 == 1) {
								itemsDate = "1";
							} else if (arg2 == 2) {
								itemsDate = "2";
							} else if (arg2 == 3) {
								itemsDate = "3";
							}
							textDate.setText(Constants.DATE_LIST[arg2]);
							PopSelector.getInstance(FollowActivity.this).hidePopWindow();
							getMyAttentionList(1, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(FollowActivity.this).ShowSelector(Constants.DATE_LIST, arg0);
				} else {
					PopSelector.getInstance(FollowActivity.this).hidePopWindow();
				}
			}
		});
		
		
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
	
	
//	private void getMyAttentionList(String pageSize,int page,String tag)
//	{
//		post(gpl, addParam(gpl, pageSize, String.valueOf(page), null, null, null, null, null, "Y", null,null), false, tag);
//	}
	
	private void getMyAttentionList(int num, String tag) {
		post(gpl, addParam(gpl, pageSize, String.valueOf(num), itemsCountry, null, null, null, null, "Y", null, itemsMoney,itemsDate, null), false, tag);
	}
	
	
	
	private class FollowAdapter extends BaseAdapter {

		private Context con;
		private List<Project> datas;

		public FollowAdapter(Context con, List<Project> datas) {
			this.con=con;
			this.datas = datas;
		}


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
				convertView = LayoutInflater.from(con).inflate(
						R.layout.list_item_follow, parent, false);
				holder.itemImage = (RoundedImageView) convertView
						.findViewById(R.id.image);
				holder.itemMoney = (TextView) convertView
						.findViewById(R.id.moneyNow);
				holder.itemTitle = (TextView) convertView
						.findViewById(R.id.title);
				holder.itemFlow = (TextView) convertView
						.findViewById(R.id.flow);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String imgUrl = "";
			if (datas.get(position).getImages().size() > 0) {
				imgUrl = AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + datas.get(position).getImages().get(0).getUrl();

			} else {
				imgUrl = AppInfoUtil.sharedAppInfoUtil().getImageServerUrl();
			}
			ImageManager.getInstance(FollowActivity.this).getImage(holder.itemImage,imgUrl);
			holder.itemMoney.setText(String.valueOf(AppHelper.getAmt(datas.get(position))));
				holder.itemTitle.setText(datas.get(position).getName());
			if(datas.get(position).getFlowId().equals("下架"))
			{
				holder.itemFlow.setText("已下架");
			}
			else
			{
				holder.itemFlow.setText("");
			}
//			holder.itemMoneyOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

			return convertView;
		}

		class ViewHolder {
			RoundedImageView itemImage;
			TextView itemMoney;
			TextView itemTitle;
			TextView itemFlow;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (position > 0 && position < mAdapter.getCount() + 1) {
			Intent intent = new Intent();
			intent.putExtra("id", list.get(position - 1).getId());
			intent.setClass(this, ProjectDetailActivity.class);
			startActivity(intent);
		}
	}
	
	
	
	
	

}
