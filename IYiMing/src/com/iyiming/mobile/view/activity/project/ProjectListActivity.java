/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月22日 下午10:21:06
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyiming.mobile.R;
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
	private List<HashMap<String, Object>> list = null;

	private Projectdapter mAdapter;

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
		list = new ArrayList<HashMap<String, Object>>();
		mAdapter = new Projectdapter(this, list);
		listView.setAdapter(mAdapter);
		listView.setPullLoadEnable(true);

		navBar = (NavBar) findViewById(R.id.navBar1);
		navBar.setTitle("项目列表");
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
	}

	private void initData() {

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

	}

	private class Projectdapter extends BaseAdapter {

		private Context con;
		private List<HashMap<String, Object>> datas;

		public Projectdapter(Context con, List<HashMap<String, Object>> datas) {
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

			ImageUtil.getInstance(ProjectListActivity.this).getImage(holder.itemImage,
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

}
