package com.iyiming.mobile.view.activity.my.album;

import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.my.album.MyImageView.OnMeasureListener;
import com.iyiming.mobile.view.activity.my.album.NativeImageLoader.NativeImageCallBack;

public class ChildAdapter extends BaseAdapter {

	private Point mPoint = new Point(0, 0);//


	private GridView mGridView;
	private List<String> list;

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	protected LayoutInflater mInflater;

	private ShowImageActivity context;

	private GridFragment gridFragment;

	public ChildAdapter(ShowImageActivity context, GridView mGridView,
			GridFragment gridFragment) {
		this.mGridView = mGridView;
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.gridFragment = gridFragment;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		String path = list.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.grid_child_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (MyImageView) convertView
					.findViewById(R.id.child_image);
			viewHolder.mCheckBox = (ImageView) convertView
					.findViewById(R.id.child_checkbox);

			// ��������ImageView�Ŀ�͸�
			viewHolder.mImageView.setOnMeasureListener(new OnMeasureListener() {

				@Override
				public void onMeasureSize(int width, int height) {
					mPoint.set(width, height);
				}
			});

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mImageView
					.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		viewHolder.mImageView.setTag(path);

		viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String path1 = (String) viewHolder.mImageView.getTag();
				addAnimation(viewHolder.mCheckBox);
				boolean is = context.selectedDataList.indexOf(path1) != -1; // �Ѿ���ѡ����
				if (is) {
					viewHolder.mCheckBox
							.setImageResource(R.drawable.friends_sends_pictures_select_icon_unselected);
					context.selectedDataList.remove(path1);
					gridFragment.changeSelectNum();
				} else {
					if (context.selectedDataList.size() == context.num) {
						return;
					} else {
						viewHolder.mCheckBox
								.setImageResource(R.drawable.friends_sends_pictures_select_icon_selected);
						context.selectedDataList.add(path1);
						gridFragment.changeSelectNum();

					}
				}

			}
		});

		// viewHolder.mCheckBox.setChecked(mSelectMap.containsKey(position) ?
		// mSelectMap.get(position) : false);
		// if(context.selectedDataList.indexOf(path)!=-1){
		//
		// }
		boolean is = context.selectedDataList.indexOf(path) != -1; // �Ѿ���ѡ����
		if (is) {
			viewHolder.mCheckBox
					.setImageResource(R.drawable.friends_sends_pictures_select_icon_selected);
		} else {
			viewHolder.mCheckBox
					.setImageResource(R.drawable.friends_sends_pictures_select_icon_unselected);
		}

		// ����NativeImageLoader����ر���ͼƬ
		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path,
				mPoint, new NativeImageCallBack() {

					@Override
					public void onImageLoader(Bitmap bitmap, String path) {
						ImageView mImageView = (ImageView) mGridView
								.findViewWithTag(path);
						if (bitmap != null && mImageView != null) {
							mImageView.setImageBitmap(bitmap);
						}
					}
				});
		if (bitmap != null) {
			viewHolder.mImageView.setImageBitmap(bitmap);
		} else {
			viewHolder.mImageView
					.setImageResource(R.drawable.friends_sends_pictures_no);
		}

		return convertView;
	}

	/**
	 * ��CheckBox�ӵ�����������ÿ�Դ��nineoldandroids���ö���
	 * 
	 * @param view
	 */
	private void addAnimation(View view) {
		float[] vaules = new float[] { 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f,
				1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f };
		AnimatorSet set = new AnimatorSet();
		set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules),
				ObjectAnimator.ofFloat(view, "scaleY", vaules));
		set.setDuration(150);
		set.start();
	}

	/**
	 * // * ��ȡѡ�е�Item��position // * @return //
	 */
	// public List<Integer> getSelectItems(){
	// List<Integer> list = new ArrayList<Integer>();
	// for(Iterator<Map.Entry<Integer, Boolean>> it =
	// mSelectMap.entrySet().iterator(); it.hasNext();){
	// Map.Entry<Integer, Boolean> entry = it.next();
	// if(entry.getValue()){
	// list.add(entry.getKey());
	// }
	// }
	//
	// return list;
	// }

	public static class ViewHolder {
		public MyImageView mImageView;
		public ImageView mCheckBox;
	}

}
