package com.iyiming.mobile.view.fragment;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iyiming.mobile.R;
import com.iyiming.mobile.model.project.Project;
import com.iyiming.mobile.util.AppHelper;
import com.iyiming.mobile.util.AppInfoUtil;
import com.iyiming.mobile.util.ImageManager;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.project.ProjectDetailActivity;
import com.iyiming.mobile.view.activity.project.ProjectListActivity;
import com.iyiming.mobile.view.activity.search.SearchActivity;
import com.iyiming.mobile.view.widget.CirclePageIndicator;
import com.iyiming.mobile.view.widget.FixedSpeedScroller;
import com.iyiming.mobile.view.widget.XListView;

public class HomeFragment extends BaseFragment implements OnClickListener, OnItemClickListener {

	private XListView listView;
	private View headView;
	private ViewPager viewpager;
	private CirclePageIndicator indicator;
	private HomeAdapter mAdapter;
	private ViewAdapterTop viewAdapterTop;

	List<Project> picList;
	private List<Project> list = null;

	private LinearLayout homeYiming;
	private LinearLayout homeLiuxue;
	private LinearLayout homeQianzheng;
	private LinearLayout homeShengzi;
	private LinearLayout homeFangchan;
	private LinearLayout homeShuiwu;
	private LinearLayout homeShangye;
	private LinearLayout homeYinhang;

	private LinearLayout homeTouzi;
	private LinearLayout homeJiangzuo;
	private LinearLayout homeYouji;

	private final String gpl = "gpl";
	private final String GPL_REFRESH = "gpl_refresh";
	private final String GPL_LOADMORE = "gpl_loadmore";

	private int page = 1;
	private String pageSize = "5";

	// private int oldPosition = 0;//记录上一次点的位置
	private int currentItem; // 当前页面
	private ScheduledExecutorService scheduledExecutorService;

	@Override
	public int getFragmentTitleResourceId() {
		return R.string.home;
	}

	@Override
	public boolean isNeedRemove() {
		return false;
	}

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
		return false;
	}

	@Override
	public boolean isRightTitleHide() {
		return false;
	}

	@Override
	public boolean isNavBarHide() {
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, null);
		initView(inflater, view);
		initData();
		initListener();
		return view;
	}

	private void initView(LayoutInflater inflater, View view) {

		listView = (XListView) view.findViewById(R.id.xlistview_home);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		listView.setOnItemClickListener(this);
		this.initHeadView(inflater);
		listView.addHeaderView(this.headView);
		list = new ArrayList<Project>();
		mAdapter = new HomeAdapter(getActivity(), list);
		listView.setAdapter(mAdapter);
		listView.setPullLoadEnable(true);

		homeYiming = (LinearLayout) view.findViewById(R.id.home_yiming);
		homeLiuxue = (LinearLayout) view.findViewById(R.id.home_liuxue);
		homeQianzheng = (LinearLayout) view.findViewById(R.id.home_qianzheng);
		homeShengzi = (LinearLayout) view.findViewById(R.id.home_shengzi);
		homeFangchan = (LinearLayout) view.findViewById(R.id.home_fangchan);
		homeShuiwu = (LinearLayout) view.findViewById(R.id.home_shuiwu);
		homeShangye = (LinearLayout) view.findViewById(R.id.home_shangye);
		homeYinhang = (LinearLayout) view.findViewById(R.id.home_yinhang);

		homeTouzi = (LinearLayout) view.findViewById(R.id.home_touzi);
		homeJiangzuo = (LinearLayout) view.findViewById(R.id.home_jiangzuo);
		homeYouji = (LinearLayout) view.findViewById(R.id.home_youji);

	}

	private void initData() {

		post(gpl, addParam(gpl, pageSize, String.valueOf(1), null, null, null, null, null, null, null, null, null, null), false, GPL_REFRESH, true);

		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(viewpager.getContext(), new AccelerateInterpolator());
			// scroller.setFixedDuration(5000);
			mScroller.set(viewpager, scroller);
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}

	}

	private void initListener() {
		homeYiming.setOnClickListener(this);
		homeLiuxue.setOnClickListener(this);
		homeQianzheng.setOnClickListener(this);
		homeShengzi.setOnClickListener(this);
		homeFangchan.setOnClickListener(this);
		homeShuiwu.setOnClickListener(this);
		homeShangye.setOnClickListener(this);
		homeYinhang.setOnClickListener(this);

		homeTouzi.setOnClickListener(this);
		homeJiangzuo.setOnClickListener(this);
		homeYouji.setOnClickListener(this);

		listView.setXListViewListener(new XListView.IXListViewListener() {
			@Override
			public void onRefresh() {
				post(gpl, addParam(gpl, pageSize, String.valueOf(1), null, null, null, null, null, null, null, null, null, null), false, GPL_REFRESH,
						true);
			}

			@Override
			public void onLoadMore() {
				post(gpl, addParam(gpl, pageSize, String.valueOf(page + 1), null, null, null, null, null, null, null, null, null, null), false,
						GPL_LOADMORE, true);
			}
		});

		searchText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				switch (arg1) {
				case EditorInfo.IME_NULL:

					break;
				case EditorInfo.IME_ACTION_SEND:

					break;
				case EditorInfo.IME_ACTION_DONE:

					break;
				case EditorInfo.IME_ACTION_SEARCH:
					// showToast("正在搜索");
					if (searchText.getText().toString().length() != 0) {
						Intent intent = new Intent();
						intent.putExtra("keyword", searchText.getText().toString());
						intent.setClass(getActivity(), SearchActivity.class);
						startActivity(intent);
					}
					break;
				}
				return true;
			}

		});
	}

	private void initHeadView(LayoutInflater inflater) {
		headView = inflater.inflate(R.layout.fragment_home_header, null);
		viewpager = (ViewPager) headView.findViewById(R.id.viewpager_home_page);
		indicator = (CirclePageIndicator) headView.findViewById(R.id.indicator_home_page);
		picList = new ArrayList<Project>();
		viewAdapterTop = new ViewAdapterTop(picList, getActivity());
		viewpager.setAdapter(viewAdapterTop);
		indicator.setViewPager(viewpager);
		indicator.setRadius(10f);
		indicator.setFillColor(getResources().getColor(R.color.whiteT));
		indicator.setPageColor(getResources().getColor(R.color.grayT));
		indicator.setStrokeColor(getResources().getColor(R.color.grayT));
		indicator.setStrokeWidth(0f);
	}

	private void refreshHeadView() {
		// viewpager.setAdapter(new ViewAdapterTop(listTop, getActivity()));
		// indicator.setViewPager(viewpager);
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

						picList.clear();
						if (projectDetails.size() > 4) {
							for (int i = 0; i < 4; i++) {
								picList.add(projectDetails.get(i));
							}
						} else {
							for (int i = 0; i < projectDetails.size(); i++) {
								picList.add(projectDetails.get(i));
							}
						}
						viewAdapterTop.notifyDataSetChanged();
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

	@Override
	public void onResponseError(VolleyError arg0, String tag) {
		super.onResponseError(arg0, tag);
		listView.stopRefresh();
		listView.stopLoadMore();
	}

	private class HomeAdapter extends BaseAdapter {

		private Context con;
		private List<Project> datas;

		public HomeAdapter(Context con, List<Project> datas) {
			this.con = con;
			this.datas = datas;
		}

		// /**
		// * 刷新数据
		// *
		// * @param news
		// */
		// public void refreshData(List<HashMap<String, Object>> datas) {
		// if (null == datas) {
		// this.datas = new ArrayList<HashMap<String, Object>>();
		// } else {
		// this.datas = datas;
		// }
		// notifyDataSetChanged();
		// }

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
			// holder.itemImage.setTag(imgUrl);
			ImageManager.getInstance(getActivity()).getImage(holder.itemImage, imgUrl);
			holder.itemMoney.setText(String.valueOf(AppHelper.getAmt(datas.get(position))));
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

	private class ViewAdapterTop extends PagerAdapter {

		private List<Project> datas;

		private HashMap<Integer, View> views = new HashMap<Integer, View>();

		private LayoutInflater inflater;

		public ViewAdapterTop(List<Project> infos, Context con) {
			if (null == infos) {
				this.datas = new ArrayList<Project>();
			} else {
				this.datas = infos;
			}
			this.inflater = LayoutInflater.from(con);
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return super.getPageTitle(position);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {

			View view = views.get(position);
			if (view == null) {
				view = inflater.inflate(R.layout.layout_home_viewpager_item, null);
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.putExtra("id", list.get(position).getId());
						intent.setClass(getActivity(), ProjectDetailActivity.class);
						startActivity(intent);
					}
				});
				views.put(position, view);
			}
			container.addView(view);

			ImageView image = (ImageView) view.findViewById(R.id.iv_home_page_viewpager_item);
			final String imgUrl = AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + datas.get(position).getImageUrl();
			// 给 ImageView 设置一个 tag
			// holder.itemImage.setTag(imgUrl);
			ImageManager.getInstance(getActivity()).getImage(image, imgUrl);
			return view;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			super.registerDataSetObserver(observer);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			super.restoreState(state, loader);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	@Override
	public void onClick(View v) {
		String type = "1";
		if (v == homeYiming) {
			type = "1";
		} else if (v == homeLiuxue) {
			type = "2";
		} else if (v == homeQianzheng) {
			type = "3";
		} else if (v == homeShengzi) {
			type = "4";
		} else if (v == homeFangchan) {
			type = "5";
		} else if (v == homeShuiwu) {
			type = "6";
		} else if (v == homeShangye) {
			type = "7";
		} else if (v == homeYinhang) {
			type = "8";
		} else if (v == homeTouzi) {
			type = "9";
		} else if (v == homeJiangzuo) {
			type = "10";
		} else if (v == homeYouji) {
			type = "11";
		}
		Intent intent = new Intent();
		intent.putExtra("type", type);
		intent.setClass(getActivity(), ProjectListActivity.class);
		startActivity(intent);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (position > 1 && position < mAdapter.getCount() + 2) {
			Intent intent = new Intent();
			intent.putExtra("id", list.get(position - 2).getId());
			intent.setClass(getActivity(), ProjectDetailActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 5, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewpager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewpager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % 4;
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

}
