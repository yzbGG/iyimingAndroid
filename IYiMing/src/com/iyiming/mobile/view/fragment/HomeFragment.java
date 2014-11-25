package com.iyiming.mobile.view.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.ImageUtil;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.project.ProjectDetailActivity;
import com.iyiming.mobile.view.activity.project.ProjectListActivity;
import com.iyiming.mobile.view.widget.CirclePageIndicator;
import com.iyiming.mobile.view.widget.XListView;

public class HomeFragment extends BaseFragment implements OnClickListener, OnItemClickListener {

	private XListView listView;
	private View headView;
	private ViewPager viewpager;
	private CirclePageIndicator indicator;
	private HomeAdapter mAdapter;

	private List<HashMap<String, Object>> list = null;

	private List<HashMap<String, Object>> listBottom = null;

	private List<HashMap<String, Object>> listTop = null;

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
	
	private final String gpl="gpl";

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

		mAdapter = new HomeAdapter(getActivity(), listBottom);
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
		
		post(gpl, addParam(gpl,"20","1",null,null,null,null,null,null,null), false);
		
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
	}

	private void initHeadView(LayoutInflater inflater) {
		headView = inflater.inflate(R.layout.fragment_home_header, null);
		viewpager = (ViewPager) headView.findViewById(R.id.viewpager_home_page);
		indicator = (CirclePageIndicator) headView.findViewById(R.id.indicator_home_page);
		viewpager.setAdapter(new ViewAdapterTop(listTop, getActivity()));
		indicator.setViewPager(viewpager);
		indicator.setRadius(5f);
		indicator.setFillColor(getResources().getColor(R.color.white));
		indicator.setPageColor(getResources().getColor(R.color.gray));
		indicator.setStrokeColor(getResources().getColor(R.color.gray));
		indicator.setStrokeWidth(0f);
	}

	private void refreshHeadView() {
		viewpager.setAdapter(new ViewAdapterTop(listTop, getActivity()));
		indicator.setViewPager(viewpager);
	}

	private class HomeAdapter extends BaseAdapter {

		private Context con;
		private List<HashMap<String, Object>> datas;

		public HomeAdapter(Context con, List<HashMap<String, Object>> datas) {
			this.con = con;
			this.datas = datas;
		}

		/**
		 * 刷新数据
		 * 
		 * @param news
		 */
		public void refreshData(List<HashMap<String, Object>> datas) {
			if (null == datas) {
				this.datas = new ArrayList<HashMap<String, Object>>();
			} else {
				this.datas = datas;
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
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

			ImageUtil.getInstance(getActivity()).getImage(holder.itemImage,
					"http://image.tianjimedia.com/uploadImages/2014/317/17/7XKU0PL1X93S_1000x500.jpg");
			holder.itemMoney.setText("5888￥");
			holder.itemTitle.setText("曼谷游，北京出发");
			holder.itemInfo.setText("天津航空包机直飞，人侍洞宜比思酒店入住，赠送接机服务和一日游服务");

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

		private List<HashMap<String, Object>> datas;

		private HashMap<Integer, View> views = new HashMap<Integer, View>();

		private LayoutInflater inflater;

		public ViewAdapterTop(List<HashMap<String, Object>> infos, Context con) {
			if (null == infos) {
				this.datas = new ArrayList<HashMap<String, Object>>();
			} else {
				this.datas = infos;
			}
			this.inflater = LayoutInflater.from(con);
		}

		@Override
		public int getCount() {
			// return datas.size();
			return 5;
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
						intent.setClass(getActivity(), ProjectDetailActivity.class);
						startActivity(intent);
					}
				});
				views.put(position, view);
			}
			container.addView(view);

			ImageView image = (ImageView) view.findViewById(R.id.iv_home_page_viewpager_item);
			ImageUtil.getInstance(getActivity()).getImage(image, "http://p1.image.hiapk.com/uploads/allimg/141118/7730-14111Q13047.jpg");
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
	public void onClick(View arg0) {

		Intent intent = new Intent();
		intent.setClass(getActivity(), ProjectListActivity.class);
		startActivity(intent);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), ProjectDetailActivity.class);
		startActivity(intent);
	}

}
