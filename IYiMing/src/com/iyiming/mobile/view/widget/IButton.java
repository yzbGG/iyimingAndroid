/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @COMPANY IFXME.COM
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年8月8日 下午11:16:19
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.util.ButtonTouch;

/**
 * @DESCRIBE ...
 */
public class IButton extends LinearLayout implements
		android.view.View.OnClickListener {

	private ImageButton button;
	private TextView text;
	private String mText;
	private int mTextColor = 0;
	private float mTextSize;
	private boolean isScale = false;// false表示不进行缩放 true表示缩放
	private Drawable mSrc;
	private OnIButtonClickListener listener;

	/**
	 * @param context
	 * @param attrs
	 */
	public IButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.widget_i_button, this);
		button = (ImageButton) findViewById(R.id.button);
		text = (TextView) findViewById(R.id.text);
		if (!isInEditMode()) {
			button.setOnTouchListener(ButtonTouch.TouchDark);
			button.setOnClickListener(this);
		}

		try {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.IButton);
			mText = a.getString(R.styleable.IButton_text);
			mTextColor = a.getColor(R.styleable.IButton_textColor, 0xff000000);
			mTextSize = a.getDimension(R.styleable.IButton_textSize, 20);
			mSrc = a.getDrawable(R.styleable.IButton_src);
			isScale = a.getBoolean(R.styleable.IButton_isScale, false);
			if (isScale) {
				ViewGroup.LayoutParams lp = button.getLayoutParams();
				lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
				lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
				button.setLayoutParams(lp);
			}
			if (mText != null) {
				text.setText(mText);
			}
			text.setTextColor(mTextColor);
			text.setTextSize(mTextSize);
			if (mSrc != null) {
				button.setBackgroundDrawable(mSrc);
			}
			a.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setText(String mtext) {
		text.setText(mtext);
	}

	public interface OnIButtonClickListener {
		public void onVClick(View v);
	}

	public void setOnIClickListener(OnIButtonClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {

		if (this.listener != null) {
			listener.onVClick(this);
		}
	}

}
