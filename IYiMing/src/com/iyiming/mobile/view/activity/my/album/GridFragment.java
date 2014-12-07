package com.iyiming.mobile.view.activity.my.album;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.my.album.NativeImageLoader.NativeImageCallBack;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GridFragment extends Fragment {

	private GridView mGridView;
	private List<String> list;
	public ChildAdapter adapter;

	private Button okButton;
	private TextView preview;

	private List<String> allImages = new ArrayList<String>();

	private static final String ALL_KEYS = "所有图片";

	private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
	private final static int SCAN_OK = 1;
	private ProgressDialog mProgressDialog;
	private GridView mGroupGridView;

	private Context context;

	private PopupWindow popView;

	
	private LinearLayout images;

	private TextView name;
	
	private View view;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCAN_OK:

				mProgressDialog.dismiss();

				list = mGruopMap.get(ALL_KEYS);

				mGridView = (GridView) view.findViewById(R.id.child_grid);
				adapter = new ChildAdapter((ShowImageActivity) getActivity(),
						mGridView, GridFragment.this);
				adapter.setList(list);
				mGridView.setAdapter(adapter);
				initPopMenu();
				break;
			}
		}

	};


	public  void refresh(){
		adapter.notifyDataSetChanged();
		changeSelectNum();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.grid_fragment, null);
		initView();

		mGruopMap.put(ALL_KEYS, allImages);
		getImages();
		return view;
	}

	private void initView() {

		preview = (TextView) view.findViewById(R.id.preview);
		preview.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((ShowImageActivity) getActivity()).changeDetail();

			}
		});
		okButton = (Button) view.findViewById(R.id.ok_button);

		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();

			}
		});
		
		
		ImageView back  = (ImageView)view.findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShowImageActivity activtity = (ShowImageActivity) getActivity();
				activtity.selectedDataList.clear();
				getActivity().finish();
				
			}
		});

		changeSelectNum();

		name = (TextView) view.findViewById(R.id.name);
		images = (LinearLayout) view.findViewById(R.id.images);
		images.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showOrHidePopView(v);

			}
		});
	}

	@Override
	public void onResume() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}

		super.onResume();
	}


	/**
	 * 扫描图片
	 */
	private void getImages() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(getActivity(), "没有存储卡", Toast.LENGTH_SHORT).show();
			return;
		}

		mProgressDialog = ProgressDialog.show(getActivity(), null, "正在扫描...");

		new Thread(new Runnable() {

			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = getActivity()
						.getContentResolver();

				Cursor mCursor = mContentResolver.query(mImageUri,
						new String[] { MediaStore.Images.Media.DATA,
								MediaStore.Images.Media.SIZE },
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_ADDED+" DESC");

				while (mCursor.moveToNext()) {

					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					long size = mCursor.getLong(mCursor
							.getColumnIndex(MediaStore.Images.Media.SIZE));

					if (size < 1) {
						continue;
					}

					String parentName = new File(path).getParentFile()
							.getName();

					if (parentName.contains("drawable")) {
						continue;
					}

					allImages.add(path);
					if (!mGruopMap.containsKey(parentName)) {
						List<String> chileList = new ArrayList<String>();
						chileList.add(path);
						mGruopMap.put(parentName, chileList);
					} else {
						mGruopMap.get(parentName).add(path);
					}
				}

				mCursor.close();

				mHandler.sendEmptyMessage(SCAN_OK);

			}
		}).start();

	}

	private void initPopMenu() {

		ListView listview = new ListView(context);

		// listview.setBackgroundColor(Color.WHITE);
		listview.setDivider(new ColorDrawable(Color.GRAY));  
		listview.setDividerHeight(1);  


		popView = new PopupWindow(listview);

		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();

		popView.setWidth(dm.widthPixels);
		popView.setHeight(dm.heightPixels * 2 / 3);

		popView.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_bound));
		// popView.setOutsideTouchable(true);
		popView.setFocusable(true);
		ArrayList<String> keys = new ArrayList<String>(mGruopMap.keySet());
		// Collections.sort(keys);
		keys.remove(ALL_KEYS);
		keys.add(0, ALL_KEYS);

		MenuAdapter menuadapter = new MenuAdapter(keys);
		listview.setAdapter(menuadapter);
		listview.setOnItemClickListener(menuadapter);

	}

	private void showOrHidePopView(View anchor) {

		if (popView != null) {
			if (popView.isShowing()) {
				popView.dismiss();
			} else {
				popView.showAsDropDown(anchor);
			}
		}
	}

	public void changeSelectNum() {

		ShowImageActivity activtity = (ShowImageActivity) getActivity();
		okButton.setText("完成 " + activtity.selectedDataList.size() + "/"
				+ activtity.num);

	}

	private class MenuAdapter extends BaseAdapter implements
			OnItemClickListener {

		ArrayList<String> keys = null;

		private int last;
		private int now;

		public MenuAdapter(ArrayList<String> keys) {
			super();
			this.keys = keys;
		}

		@Override
		public int getCount() {
			return mGruopMap.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getActivity().getLayoutInflater().inflate(
					R.layout.menu_pic_item, null);
			TextView name = (TextView) view.findViewById(R.id.name); // ���
			TextView count = (TextView) view.findViewById(R.id.count); // ����
			CheckBox check = (CheckBox) view.findViewById(R.id.checcc);
			ImageView imageview = (ImageView)view.findViewById(R.id.imageview);
			
			if (position == now) {
				check.setChecked(true);
			}
			if (position == last) {
				check.setChecked(false);
			}
			String key = keys.get(position);
			name.setText(key);
			count.setText(mGruopMap.get(key).size() + "张");
			
			String path = mGruopMap.get(key).get(0);
			Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path,
					new Point(100, 100), new NativeImageCallBack() {

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
				imageview.setImageBitmap(bitmap);
			} else {
				imageview
						.setImageResource(R.drawable.friends_sends_pictures_no);
			}


			return view;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			String key = keys.get(position);
			last = now;
			now = position;
			name.setText(key);
			adapter.setList(mGruopMap.get(key));
			adapter.notifyDataSetChanged();
			showOrHidePopView(images);
		}
	}

}
