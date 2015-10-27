/**   
 * @Title: ProjectDetailActivity.java 
 * @Package com.iyiming.mobile.view.activity.project 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dkslbw@gmail.com   
 * @date 2014年11月22日 下午2:05:30 
 * @version V1.0   
 */
package com.iyiming.mobile.view.activity.project;

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
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iyiming.mobile.R;
import com.iyiming.mobile.model.project.ImageUrl;
import com.iyiming.mobile.model.project.Project;
import com.iyiming.mobile.util.AppHelper;
import com.iyiming.mobile.util.AppInfoUtil;
import com.iyiming.mobile.util.ImageManager;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.account.LoginActivty;
import com.iyiming.mobile.view.widget.CirclePageIndicator;
import com.iyiming.mobile.view.widget.FixedSpeedScroller;
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
	// private ImageView itemImage;
	private Button btnAttention;
	private ViewPager viewpager;
	private CirclePageIndicator indicator;
	private ViewAdapterTop viewAdapterTop;
	private List<ImageUrl> imageList;
	private final int LOGIN = 10001;
	private ScheduledExecutorService scheduledExecutorService;
	private int currentItem; // 当前页面

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
		// itemImage = (ImageView) findViewById(R.id.item_image);
		viewpager = (ViewPager) findViewById(R.id.viewpager_home_page);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator_home_page);
		itemTitle = (TextView) findViewById(R.id.item_title);
		itemMoney = (TextView) findViewById(R.id.item_money);
		itemInfo = (TextView) findViewById(R.id.item_info);
		itemAttentionCount = (TextView) findViewById(R.id.item_attentioncount);
		btnAttention = (Button) findViewById(R.id.button1);
		imageList = new ArrayList<ImageUrl>();
		viewAdapterTop = new ViewAdapterTop(imageList, this);
		viewpager.setAdapter(viewAdapterTop);
		indicator.setViewPager(viewpager);
		indicator.setRadius(10f);
		indicator.setFillColor(getResources().getColor(R.color.whiteT));
		indicator.setPageColor(getResources().getColor(R.color.grayT));
		indicator.setStrokeColor(getResources().getColor(R.color.grayT));
		indicator.setStrokeWidth(0f);

	}

	private void initData() {
		Intent intent = this.getIntent();
		id = intent.getIntExtra("id", 0);
		getProjectDetial(id);

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

	/**
	 * 获取项目详情
	 * 
	 * @param id
	 */
	private void getProjectDetial(int id) {
		post(gpd, addParam(gpd, String.valueOf(id)), false, gpd, true);// 添加了离线浏览模式
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
		// return true;
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
		// ImageManager.getInstance(this).getImage(itemImage,
		// AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() +
		// projectDetail.getImageUrl());
		imageList.clear();
		imageList.addAll(projectDetail.getImages());
		viewAdapterTop.notifyDataSetChanged();
		itemTitle.setText(projectDetail.getName());
		itemInfo.setText(projectDetail.getIntro());
		itemMoney.setText(AppHelper.getAmt(projectDetail));
		itemAttentionCount.setText("已经有" + projectDetail.getAttentionCount() + "人关注");
		setBtnState(projectDetail);
	}

	private void setBtnState(Project projectDetail) {
		if (projectDetail.getAttentionFlag().equalsIgnoreCase("Y")) {
			btnAttention.setText("已关注");
			btnAttention.setBackgroundResource(R.drawable.followed);
		} else {
			btnAttention.setText("+ 关注");
			btnAttention.setBackgroundResource(R.drawable.follow);
		}
	}

	private class ViewAdapterTop extends PagerAdapter {

		private List<ImageUrl> datas;

		private HashMap<Integer, View> views = new HashMap<Integer, View>();

		private LayoutInflater inflater;

		public ViewAdapterTop(List<ImageUrl> infos, Context con) {
			if (null == infos) {
				this.datas = new ArrayList<ImageUrl>();
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
				views.put(position, view);
			}
			container.addView(view);

			ImageView image = (ImageView) view.findViewById(R.id.iv_home_page_viewpager_item);
			String imgUrl = "";
			imgUrl = AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + datas.get(position).getUrl();

			// 给 ImageView 设置一个 tag
			// holder.itemImage.setTag(imgUrl);
			ImageManager.getInstance(ProjectDetailActivity.this).getImage(image, imgUrl);
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
