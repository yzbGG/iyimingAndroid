/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年8月17日 下午6:36:12
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyiming.mobile.R;

/**
 * @DESCRIBE 自定义导航
 */
public class NavBar extends LinearLayout{

	private LinearLayout left;
	private LinearLayout right;
	private TextView title;
	private Context context;
	private ImageView leftImage;
	private ImageView rightImage;
	private RelativeLayout 	navContainer;
	private LinearLayout 	searchContainer;

	/**
	 * @param context
	 */
	public NavBar(Context context) {
		super(context);
		initView(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public NavBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public NavBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}

	/**
	 * 初始化状态
	 * 
	 * @param context
	 * @param attrs
	 */
	private void initView(Context context, AttributeSet attrs) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.widget_navbar, this);
		this.context = context;
		left = (LinearLayout) findViewById(R.id.left);
		right = (LinearLayout) findViewById(R.id.right);
		leftImage = (ImageView) findViewById(R.id.leftImage);
		rightImage = (ImageView) findViewById(R.id.rightImage);
		title = (TextView) findViewById(R.id.title);
		navContainer=(RelativeLayout)findViewById(R.id.navContainer);
		searchContainer=(LinearLayout)findViewById(R.id.searchContainer);
		String mText = null;
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.NavBar);
			mText = a.getString(R.styleable.NavBar_mtext);
		}
		if (mText == null) {
			mText = "";
		}

		title.setText(mText);
	}

	public void setLeftImage(int resId) {
		leftImage.setImageResource(resId);
	}

	public void setRightImage(int resId) {
		rightImage.setImageResource(resId);
	}

	public void OnLeftClick(OnClickListener listener) {
		left.setOnClickListener(listener);
	}

	public void OnRightClick(OnClickListener listener) {
		right.setOnClickListener(listener);
	}

	public void hideLeft(boolean isHide) {
		if (isHide) {
			left.setVisibility(View.GONE);
		} else {
			left.setVisibility(View.VISIBLE);
		}
	}

	public void hideRight(boolean isHide) {
		if (isHide) {
			right.setVisibility(View.GONE);
		} else {
			right.setVisibility(View.VISIBLE);
		}
	}
	
	public void setTitle(String mtitle)
	{
		title.setText(mtitle);
	}
	
	public void setTitle(int resId)
	{
		title.setText(resId);
	}
	
	public void isNav(boolean isNav)
	{
		if (isNav) {
			navContainer.setVisibility(View.VISIBLE);
			searchContainer.setVisibility(View.GONE);
		} else {
			navContainer.setVisibility(View.GONE);
			searchContainer.setVisibility(View.VISIBLE);
		}
	}


}
