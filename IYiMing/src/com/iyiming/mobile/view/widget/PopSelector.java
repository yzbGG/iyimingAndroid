/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年12月6日 下午2:51:52
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.widget;

import java.util.List;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.iyiming.mobile.R;

/**
 * @DESCRIBE ...
 */
public class PopSelector {

	private static PopSelector popSelector;
	public static boolean isPopUp = false;

	private Context context;
	private PopupWindow popWindow;
	private ListView listView;
	private OnItemClickListener listener;

	private PopSelector(Context context) {
		this.context = context;

	}

	public static PopSelector getInstance(Context context) {
		if (popSelector == null) {
			popSelector = new PopSelector(context);
		}
		return popSelector;
	}
	
	public void setOnItemClickListener(OnItemClickListener listener)
	{
		this.listener=listener;
	}

	public void ShowSelector(String[] list, View view) {
		
		PopSelector.isPopUp = true;
		View layoutView = initPopView(list);
		popWindow = new PopupWindow(layoutView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		/* 设置背景为空，解决点击PopupWindow中的RadioButton是RadioButton的背景变为透明的问题 */
		popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.color.no_color));
		/* 设置PopupWindow外部区域是否可触摸 */
		popWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popWindow.setTouchable(true); // 设置PopupWindow可触摸
		popWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		popWindow.setAnimationStyle(R.style.pop_anim_in_up_to_down);
		// popWindow.showAtLocation(view, Gravity.TOP, 0,
		// DensityUtil.dip2px(context, 0));/
		popWindow.showAsDropDown(view);
		

		popWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				isPopUp = false;
			}
		});
	}

	private View initPopView(String[] list) {
		View popView = View.inflate(context, R.layout.pop_selector, null);

		// LinearLayout black=(LinearLayout)popView.findViewById(R.id.black);
		listView = (ListView) popView.findViewById(R.id.list);
		listView.setAdapter(new PopSelectorAdapter(context, list));
		if(listener!=null)
		{
			listView.setOnItemClickListener(listener);
		}
		// black.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// hidePopWindow(); // 拍照
		//
		//
		// }
		// });

		popView.setFocusableInTouchMode(true);// 设置此属性是为了让popView的返回键监听器生效，否则无法监听
		popView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (popWindow != null && popWindow.isShowing()) {
						hidePopWindow();
						return true;
					}
				}
				return false;
			}
		});
		return popView;
	}

	/**
	 * 隐藏PopWindow
	 * 
	 * @author zhangshuo
	 * @date 2013-4-23 下午1:57:23
	 * @version
	 */
	public void hidePopWindow() {

		if (null != popWindow && popWindow.isShowing()) {
			popWindow.dismiss();
			popWindow = null;
		}
	}

	private class PopSelectorAdapter extends BaseAdapter {

		private Context con;
		private String[] datas;

		public PopSelectorAdapter(Context con, String[] datas) {
			this.con = con;
			this.datas = datas;
		}

		@Override
		public int getCount() {
			return datas.length;
		}

		@Override
		public Object getItem(int arg0) {
			return datas[arg0];
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
				convertView = LayoutInflater.from(con).inflate(R.layout.list_item_pop_selector, parent, false);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.name.setText(datas[position]);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

		class ViewHolder {
			TextView name;

		}

	}

}
