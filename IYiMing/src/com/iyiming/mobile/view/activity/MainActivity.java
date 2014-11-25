package com.iyiming.mobile.view.activity;

import java.util.HashMap;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.AppHelper;
import com.iyiming.mobile.view.fragment.BaseFragment;
import com.iyiming.mobile.view.fragment.FollowFragment;
import com.iyiming.mobile.view.fragment.HomeFragment;
import com.iyiming.mobile.view.fragment.MineFragment;
import com.iyiming.mobile.view.fragment.MoreFragment;
import com.iyiming.mobile.view.widget.NavBar;

public class MainActivity extends BaseActivity implements OnClickListener {

	private LinearLayout tabHome;
	private LinearLayout tabFollow;
	private LinearLayout tabMine;
	private LinearLayout tabMore;

	private ImageView imageHome;
	private ImageView imageFollow;
	private ImageView imageMine;
	private ImageView imageMore;

	private TextView textHome;
	private TextView textFollow;
	private TextView textMine;
	private TextView textMore;

	/**
	 * 上一个显示的fragment，需要隐藏的
	 */
	private BaseFragment lastFragment;

	/**
	 * 当前的fragment
	 */
	private BaseFragment currentFragment;

	private int tabIndex = 0;
	
	private final String gp="gp";

	/**
	 * 存放不需要重新创建的fragment
	 */
	private HashMap<Integer, BaseFragment> maps = new HashMap<Integer, BaseFragment>();

	private NavBar navBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initLinstener();
	}

	private void initView() {
		navBar = (NavBar) findViewById(R.id.navBar);
		tabHome = (LinearLayout) findViewById(R.id.tab_home);
		tabFollow = (LinearLayout) findViewById(R.id.tab_follow);
		tabMine = (LinearLayout) findViewById(R.id.tab_mine);
		tabMore = (LinearLayout) findViewById(R.id.tab_more);

		imageHome = (ImageView) findViewById(R.id.home_image);
		imageFollow = (ImageView) findViewById(R.id.follow_image);
		imageMine = (ImageView) findViewById(R.id.mine_image);
		imageMore = (ImageView) findViewById(R.id.more_image);

		textHome = (TextView) findViewById(R.id.home_text);
		textFollow = (TextView) findViewById(R.id.follow_text);
		textMine = (TextView) findViewById(R.id.mine_text);
		textMore = (TextView) findViewById(R.id.more_text);

	}

	private void initData() {
		setTabIndex(0);
		getUserProfile();
	}

	private void initLinstener() {
		tabHome.setOnClickListener(this);
		tabFollow.setOnClickListener(this);
		tabMine.setOnClickListener(this);
		tabMore.setOnClickListener(this);

	}
	
	
	private void getUserProfile(){
		post(gp, addParam(gp),true);//获取个人资料
	}
	
	
	
	
	
	

	@Override
	public boolean onResponseOK(Object response, String tag) {
		if(super.onResponseOK(response, tag))
		{
			
		}
		return true;
	}

	/**
	 * 显示fragment
	 */
	public BaseFragment showFragment(int checkId) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		if(AppHelper.getSDKVersionNumber()>=18)
		{
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}
		BaseFragment fragment = maps.get(checkId);
		if (fragment == null) {
			fragment = generateFragment(checkId);
			if (fragment == null) {
				return null;
			}
			maps.put(checkId, fragment);
			if (lastFragment != null) {
				if (lastFragment.isNeedRemove()) {
					maps.remove(lastFragment.getBindIngd());

					transaction.remove(lastFragment);
				} else {
					transaction.hide(lastFragment);
				}
			}
			transaction.add(R.id.container, fragment);
			lastFragment = fragment;

		} else {
			if (lastFragment != null) {
				if (lastFragment == fragment) {
					return fragment;
				}
				if (lastFragment.isNeedRemove()) {
					maps.remove(lastFragment.getBindIngd());
					transaction.remove(lastFragment);
					lastFragment = null;
				} else {
					transaction.hide(lastFragment);
				}
			}

			transaction.show(fragment);
			lastFragment = fragment;
		}

		// 更换标题
		setTitleName(fragment);

		// 更换右边图片
		changeFragmentTitle(fragment);

		transaction.commit();

		return fragment;
	}

	private void setTitleName(BaseFragment fragment) {
		navBar.setTitle(fragment.getFragmentTitleResourceId());
	}

	private void changeFragmentTitle(final BaseFragment fragment) {

		navBar.isNav(!fragment.isNavBarHide());
		navBar.hideLeft(fragment.isLeftTitleHide());
		navBar.hideRight(fragment.isRightTitleHide());
		if (fragment.getLeftTitle() != 0) {
			navBar.setLeftImage(fragment.getLeftTitle());
		}

		if (fragment.getRightTitle() != 0) {
			navBar.setRightImage(fragment.getRightTitle());
		}

		navBar.OnLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment.leftTitleClick(v, MainActivity.this);
			}
		});

		navBar.OnRightClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment.rightTitleClick(v, MainActivity.this);
			}
		});
	}

	/**
	 * 新增的fragment 在此跟按钮id绑定
	 * 
	 * @param checkId
	 * @return
	 */
	private BaseFragment generateFragment(int checkId) {
		BaseFragment fragment = null;

		switch (checkId) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new FollowFragment();
			break;
		case 2:
			fragment = new MineFragment();
			break;
		case 3:
			fragment = new MoreFragment();
			break;
		default:
			fragment = new HomeFragment();
			break;
		}
		if (fragment != null) {
			fragment.setBindIngd(checkId);
		}
		return fragment;
	}

	@Override
	public void onClick(View v) {

		if (v == tabHome) {
			setTabIndex(0);
		} else if (v == tabFollow) {
			setTabIndex(1);
		} else if (v == tabMine) {
			setTabIndex(2);
		} else if (v == tabMore) {
			setTabIndex(3);
		}

	}

	private int textNomal = 0xff444444;
	private int textSelected = 0xffed4135;

	/**
	 * 设置选项卡
	 * 
	 * @param index
	 */
	private void setTabIndex(int index) {
		imageHome.setImageResource(R.drawable.bottom_home);
		imageFollow.setImageResource(R.drawable.bottom_follow);
		imageMine.setImageResource(R.drawable.bottom_mine);
		imageMore.setImageResource(R.drawable.bottom_more);
		textHome.setTextColor(textNomal);
		textFollow.setTextColor(textNomal);
		textMine.setTextColor(textNomal);
		textMore.setTextColor(textNomal);
		switch (index) {
		case 0:
			imageHome.setImageResource(R.drawable.bottom_home_down);
			textHome.setTextColor(textSelected);
			break;
		case 1:
			imageFollow.setImageResource(R.drawable.bottom_follow_down);
			textFollow.setTextColor(textSelected);
			break;
		case 2:
			imageMine.setImageResource(R.drawable.bottom_mine_down);
			textMine.setTextColor(textSelected);
			break;
		case 3:
			imageMore.setImageResource(R.drawable.bottom_more_down);
			textMore.setTextColor(textSelected);
			break;

		default:
			imageHome.setImageResource(R.drawable.bottom_home_down);
			textHome.setTextColor(textSelected);
			break;
		}
		tabIndex = index;
		showFragment(tabIndex);
	}

}
