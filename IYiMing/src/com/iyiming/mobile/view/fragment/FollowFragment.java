/**   
* @Title: FollowFragment.java 
* @Package com.iyiming.mobile.view.fragment 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dkslbw@gmail.com   
* @date 2014年11月21日 下午2:22:04 
* @version V1.0   
*/
package com.iyiming.mobile.view.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.ImageUtil;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.project.ProjectDetailActivity;
import com.iyiming.mobile.view.widget.XListView;
import com.iyiming.mobile.view.widget.roundedimageview.RoundedImageView;

/** 
 * @ClassName: FollowFragment 
 * @Description: TODO(关注) 
 * @author dkslbw@gmail.com
 * @date 2014年11月21日 下午2:22:04 
 *  
 */
public class FollowFragment extends BaseFragment implements OnItemClickListener{
	
	private XListView listView;
	
	/** 记录所有的资讯 */
	private List<HashMap<String, Object>> list = null;
	
	private FollowAdapter mAdapter;
	
	@Override
	public int getFragmentTitleResourceId() {
		return R.string.follow;
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
		list=new ArrayList<HashMap<String,Object>>();
		mAdapter = new FollowAdapter(getActivity(), list);
		listView.setAdapter(mAdapter);
		listView.setPullLoadEnable(true);
	}

	
	private void initData()
	{
		
	}
	
	private void initListener()
	{
		
		
	}
	
	private class FollowAdapter extends BaseAdapter {

		private Context con;
		private List<HashMap<String, Object>> datas;

		public FollowAdapter(Context con, List<HashMap<String, Object>> datas) {
			this.con=con;
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
				convertView = LayoutInflater.from(con).inflate(
						R.layout.list_item_follow, parent, false);
				holder.itemImage = (RoundedImageView) convertView
						.findViewById(R.id.image);
				holder.itemMoney = (TextView) convertView
						.findViewById(R.id.moneyNow);
				holder.itemTitle = (TextView) convertView
						.findViewById(R.id.title);
				holder.itemMoneyOld = (TextView) convertView
						.findViewById(R.id.moneyOld);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			ImageUtil.getInstance(getActivity()).getImage(holder.itemImage,"http://image.tianjimedia.com/uploadImages/2014/317/17/7XKU0PL1X93S_1000x500.jpg");
			holder.itemMoney.setText("2888￥");
			holder.itemTitle.setText("曼谷游，北京出发");
			holder.itemMoneyOld.setText("5888￥");
			holder.itemMoneyOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

			return convertView;
		}

		class ViewHolder {
			RoundedImageView itemImage;
			TextView itemMoney;
			TextView itemTitle;
			TextView itemMoneyOld;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), ProjectDetailActivity.class);
		startActivity(intent);
	}
	
	

}
