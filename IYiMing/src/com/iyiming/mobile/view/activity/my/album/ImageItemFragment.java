package com.iyiming.mobile.view.activity.my.album;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.my.album.NativeImageLoader.NativeImageCallBack;


@SuppressLint("ValidFragment")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ImageItemFragment extends Fragment implements OnPageChangeListener{
	
	private List<String> attachList;

	private FriendCycleViewPager mViewPager;

	private TextView text;
	
	private View view;
	
	
	private int temp = 0;
	
	private ShowImageActivity activity;
	
	
	private CheckBox checkBox;
	
	private Button okButton;
	

	public ImageItemFragment( List<String> attachList,int index) {
		super();
		this.attachList = new ArrayList<String>(attachList);
		this.temp = index;
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = (ShowImageActivity)getActivity();
		view = inflater.inflate(R.layout.widget_photo_viewer_pic, null);
		
		ImageView back = (ImageView)view.findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				(activity).backGrid();
				
			}
		});
		
		 okButton = (Button)view.findViewById(R.id.ok_button);
		okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				(activity).finish();
				
			}
		});
		
		okButton.setText("完成 "+activity.selectedDataList.size()+"/"+activity.num);
		LinearLayout	choose  = (LinearLayout)view.findViewById(R.id.choose);
		choose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkBox.setChecked(!checkBox.isChecked());
				String path = attachList.get(temp);
				if(checkBox.isChecked()){
					activity.selectedDataList.add(path);
				}else{
					activity.selectedDataList.remove(path);
				}
				okButton.setText("完成 "+activity.selectedDataList.size()+"/"+activity.num);
				
			}
		});
		checkBox = (CheckBox)view.findViewById(R.id.check);

		
	
	//	this.attachList = attachList;
	//	count = String.valueOf(attachList.size());

		mViewPager = (FriendCycleViewPager) view.findViewById(R.id.view_pager);
		mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
		mViewPager.setOnPageChangeListener(this);

	//	temp = index;
		text = (TextView) view.findViewById(R.id.text);
		text.setText((temp + 1) + "/" + attachList.size());
		mViewPager.setAdapter(new FriendCyclePagerAdapter());
		mViewPager.setCurrentItem(temp);
		
		
		refreshCheckBox(temp);
	
		return view;
	}



	private  void refreshCheckBox( int tmp){
		String path = attachList.get(tmp);
		if((activity).selectedDataList.contains(path)){
			checkBox.setChecked(true);
		}else{
			checkBox.setChecked(false);
		}
		
		
	}
	
	


	class FriendCyclePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return attachList.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			final 	PhotoView photoView = new PhotoView(container.getContext());
			photoView.setOnViewTapListener(new OnViewTapListener() {
				
				@Override
				public void onViewTap(View view, float x, float y) {
					//FriendCyclePhotoViewer.this.dismiss();
					
				}
			});
			String path = attachList.get(position);
			photoView.setScaleType(ScaleType.CENTER_INSIDE);
//			String string = AppHelper.IAMGE_URL + attachList.get(position).getFilePath();
//			string = string.replaceAll("\\\\", "/");
//			System.out.println(string);
//			ImageManager.from(getContext()).displayImage(photoView, string, R.drawable.ic_more);
			// photoView.setImageResource(sDrawables[position]);

			// Now just add PhotoView to ViewPager and return it
			Point	mPoint = new Point();
			mPoint.set(480, 800);
			Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path,
					mPoint, new NativeImageCallBack() {

						@Override
						public void onImageLoader(Bitmap bitmap, String path) {
							
							if (bitmap != null && photoView != null) {
								photoView.setImageBitmap(bitmap);
							}
						}
					});
			if (bitmap != null) {
				photoView.setImageBitmap(bitmap);
			} else {
				photoView
						.setImageResource(R.drawable.friends_sends_pictures_no);
			}
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			
		

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		if (temp != arg0) {
			text.setText(String.valueOf(arg0 + 1) + "/" + attachList.size());
			temp = arg0;
		}
		refreshCheckBox(temp);
		
	}

	@Override
	public void onPageSelected(int arg0) {

	}

	
//	/**
//	 * ���ô˷�����ʾdialog
//	 */
//	public void showDialog() {
//		// ���ô����Ի�������ĵط�ȡ��Ի���
////		this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		setCanceledOnTouchOutside(true);
//		windowDeploy();
//		if (getContext() != null)
//			show();
//	}
//
//	// ���ô�����ʾ
//	public void windowDeploy() {
//		window = getWindow();
//		WindowManager.LayoutParams lp = window.getAttributes();
//		lp.dimAmount = 0.9f;
//		window.setAttributes(lp);
////		 window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//
//	}


	
}
