/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年12月7日 下午2:47:27
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.search;

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
import android.widget.ImageView;
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

/**
 * @DESCRIBE ...
 */
public class SearchActivity extends BaseActivity implements OnItemClickListener{
	
	private XListView listView;

	NavBar navBar;

	/** 记录所有的资讯 */
	private List<Project> list = null;

	private SearchResultdapter mAdapter;

	private String keyword = "";

	private final String gpl = "gpl";
	private final String GPL_REFRESH = "gpl_refresh";
	private final String GPL_LOADMORE = "gpl_loadmore";

	private RelativeLayout tabCountry;

	private RelativeLayout tabMoney;

	private RelativeLayout tabTime;

	private int page = 1;
	private String pageSize = "20";

	private String itemsCountry = null;
	private String itemsMoney = null;
	private String itemsDate = null;

	private TextView textCountry;
	private TextView textMoney;
	private TextView textDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initData();
		initListener();
		
	}
	
	private void initView() {
		listView = (XListView) findViewById(R.id.xlistview_home);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		list = new ArrayList<Project>();
		mAdapter = new SearchResultdapter(this, list);
		listView.setAdapter(mAdapter);
		listView.setPullLoadEnable(true);
		listView.setOnItemClickListener(this);
		navBar = (NavBar) findViewById(R.id.navBar1);

		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);

		tabCountry = (RelativeLayout) findViewById(R.id.tabCountry);
		tabMoney = (RelativeLayout) findViewById(R.id.tabMoney);
		tabTime = (RelativeLayout) findViewById(R.id.tabTime);

		textCountry = (TextView) findViewById(R.id.textcountry);
		textMoney = (TextView) findViewById(R.id.textmoney);
		textDate = (TextView) findViewById(R.id.textdate);

	}

	private void initData() {
		Intent intent = this.getIntent();
		keyword = intent.getStringExtra("keyword");
		String subKeyWord=keyword.length()>10?keyword.substring(0, 10)+"...":keyword;
		navBar.setTitle("\""+subKeyWord+"\"" +"的搜索结果");
		getList(1, keyword, GPL_REFRESH);
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
				// post(gpl, addParam(gpl, pageSize, String.valueOf(1), null,
				// null, null, null, null, null, type), false, GPL_REFRESH);
				getList(1, keyword, GPL_REFRESH);
			}

			@Override
			public void onLoadMore() {
				// post(gpl, addParam(gpl, pageSize, String.valueOf(page + 1),
				// null, null, null, null, null, null, type), false,
				// GPL_LOADMORE);
				getList(page + 1, keyword, GPL_LOADMORE);
			}
		});

		tabCountry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {

					PopSelector.getInstance(SearchActivity.this).setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							if (arg2 == 0) {
								itemsCountry = null;
							} else {
								itemsCountry = Constants.COUNTRY_LIST[arg2];
							}
							textCountry.setText(Constants.COUNTRY_LIST[arg2]);
							PopSelector.getInstance(SearchActivity.this).hidePopWindow();
							getList(1, keyword, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(SearchActivity.this).ShowSelector(Constants.COUNTRY_LIST, arg0);
				} else {
					PopSelector.getInstance(SearchActivity.this).hidePopWindow();
				}
			}
		});

		tabMoney.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {

					PopSelector.getInstance(SearchActivity.this).setOnItemClickListener(new OnItemClickListener() {
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
							PopSelector.getInstance(SearchActivity.this).hidePopWindow();
							getList(1, keyword, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(SearchActivity.this).ShowSelector(Constants.MONEY_LIST, arg0);
				} else {
					PopSelector.getInstance(SearchActivity.this).hidePopWindow();
				}
			}
		});

		tabTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {

					PopSelector.getInstance(SearchActivity.this).setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							if (arg2 == 0) {
								itemsDate = null;
							} else if (arg2 == 1) {
								itemsDate = AppHelper.getDateString(0);
							} else if (arg2 == 2) {
								itemsDate = AppHelper.getDateString(-3);
							} else if (arg2 == 3) {
								itemsDate = AppHelper.getDateString(-7);
							}
							textDate.setText(Constants.DATE_LIST[arg2]);
							PopSelector.getInstance(SearchActivity.this).hidePopWindow();
							getList(1, keyword, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(SearchActivity.this).ShowSelector(Constants.DATE_LIST, arg0);
				} else {
					PopSelector.getInstance(SearchActivity.this).hidePopWindow();
				}
			}
		});

	}

	private void getList(int num, String keyword, String tag) {
		post(gpl, addParam(gpl, pageSize, String.valueOf(num), itemsCountry, null, null, null, itemsDate, null, null, itemsMoney,null, keyword), false, tag);
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

	private class SearchResultdapter extends BaseAdapter {

		private Context con;
		private List<Project> datas;

		public SearchResultdapter(Context con, List<Project> datas) {
			this.con = con;
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
			ImageManager.getInstance(SearchActivity.this).getImage(holder.itemImage, imgUrl);
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
