/**   
* @Title: ProjectFragment.java 
* @Package com.iyiming.mobile.view.fragment 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dkslbw@gmail.com   
* @date 2014年12月2日 下午5:02:37 
* @version V1.0   
*/
package com.iyiming.mobile.view.fragment;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
import com.iyiming.mobile.view.activity.project.ProjectListActivity;
import com.iyiming.mobile.view.widget.PopSelector;
import com.iyiming.mobile.view.widget.XListView;

/** 
 * @ClassName: ProjectFragment 
 * @Description: TODO(项目) 
 * @author dkslbw@gmail.com
 * @date 2014年12月2日 下午5:02:37 
 *  
 */
public class ProjectFragment extends BaseFragment implements OnItemClickListener{
	
	private XListView listView;
	
	/** 记录所有的资讯 */
	private List<Project> list = null;
	
	private ProjectListAdapter mAdapter;
	
	private String type="1";
	
	private final String gpl = "gpl";
	private final String GPL_REFRESH = "gpl_refresh";
	private final String GPL_LOADMORE = "gpl_loadmore";

	private int page = 1;
	private String pageSize = "20";
	
	
	private RelativeLayout tabCountry;

	private RelativeLayout tabMoney;

	private RelativeLayout tabTime;

	private String itemsCountry = null;
	private String itemsMoney = null;
	private String itemsDate = null;
	
	private TextView textCountry;
	private TextView textMoney;
	private TextView textDate;

	@Override
	public void rightTitleClick(View v, BaseActivity context) {
	}

	@Override
	public void leftTitleClick(View v, BaseActivity context) {
	}

	@Override
	public int getRightTitle() {
		return 0;
	}

	@Override
	public int getLeftTitle() {
		return 0;
	}

	@Override
	public boolean isLeftTitleHide() {
		return true;
	}

	@Override
	public boolean isRightTitleHide() {
		return true;
	}

	@Override
	public boolean isNavBarHide() {
		return false;
	}

	@Override
	public int getFragmentTitleResourceId() {
		return R.string.project;
	}

	@Override
	public boolean isNeedRemove() {
		return false;
	}
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_follow, null);
		initView(inflater, view);
		initData();
		initListener();
		return view;
	}
	
	
	private void initView(LayoutInflater inflater,View view)
	{
		listView = (XListView) view.findViewById(R.id.xlistview_home);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		listView.setOnItemClickListener(this);
		list=new ArrayList<Project>();
		mAdapter = new ProjectListAdapter(getActivity(), list);
		listView.setAdapter(mAdapter);
		listView.setPullLoadEnable(true);
		
		tabCountry = (RelativeLayout) view.findViewById(R.id.tabCountry);
		tabMoney = (RelativeLayout) view.findViewById(R.id.tabMoney);
		tabTime = (RelativeLayout) view.findViewById(R.id.tabTime);
		
		textCountry=(TextView)view.findViewById(R.id.textcountry);
		textMoney=(TextView)view.findViewById(R.id.textmoney);
		textDate=(TextView)view.findViewById(R.id.textdate);
	}

	
	private void initData()
	{
//		post(gpl, addParam(gpl, pageSize, String.valueOf(1), null, null, null, null, null, null, null,null), false, GPL_REFRESH);
		getList( 1,GPL_REFRESH);

	}
	
	private void initListener()
	{
		listView.setXListViewListener(new XListView.IXListViewListener() {
			@Override
			public void onRefresh() {
//				post(gpl, addParam(gpl, pageSize, String.valueOf(1), null, null, null, null, null, null, null,null), false, GPL_REFRESH);
				getList( 1,GPL_REFRESH);
			}

			@Override
			public void onLoadMore() {
//				post(gpl, addParam(gpl, pageSize, String.valueOf(page + 1), null, null, null, null, null, null, null,null), false, GPL_LOADMORE);
				
				getList(page + 1,GPL_LOADMORE);
			}
		});
		
		
		tabCountry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {
					
					PopSelector.getInstance(getActivity()).setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

							if (arg2 == 0) {
								itemsCountry = null;
							} else {
								itemsCountry = Constants.COUNTRY_LIST[arg2];
							}
							textCountry.setText(Constants.COUNTRY_LIST[arg2]);
							PopSelector.getInstance(getActivity()).hidePopWindow();
							getList(1, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(getActivity()).ShowSelector(Constants.COUNTRY_LIST, arg0);
				} else {
					PopSelector.getInstance(getActivity()).hidePopWindow();
				}
			}
		});
		
		tabMoney.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {
					
					PopSelector.getInstance(getActivity()).setOnItemClickListener(new OnItemClickListener() {
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
							PopSelector.getInstance(getActivity()).hidePopWindow();
							getList(1, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(getActivity()).ShowSelector(Constants.MONEY_LIST, arg0);
				} else {
					PopSelector.getInstance(getActivity()).hidePopWindow();
				}
			}
		});
	
		tabTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!PopSelector.isPopUp) {
				
					PopSelector.getInstance(getActivity()).setOnItemClickListener(new OnItemClickListener() {
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
							PopSelector.getInstance(getActivity()).hidePopWindow();
							getList(1, GPL_REFRESH);
						}
					});
					PopSelector.getInstance(getActivity()).ShowSelector(Constants.DATE_LIST, arg0);
				} else {
					PopSelector.getInstance(getActivity()).hidePopWindow();
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
	
	
	private void getList(int pagenum,String tag) {
		post(gpl, addParam(gpl, pageSize, String.valueOf(pagenum), itemsCountry, null, null, null, itemsDate, null, null, itemsMoney,null, null), false, tag);
	}


	
	
	private class ProjectListAdapter extends BaseAdapter {

		private Context con;
		private List<Project> datas;

		public ProjectListAdapter(Context con, List<Project> datas) {
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
			ImageManager.getInstance(getActivity()).getImage(holder.itemImage, imgUrl);
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
			intent.setClass(getActivity(), ProjectDetailActivity.class);
			startActivity(intent);
		}
	}
	
	

}
	
	

