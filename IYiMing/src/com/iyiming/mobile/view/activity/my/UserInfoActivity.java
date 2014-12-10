/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 * @PROGECT IYIMING
 * @AUTHOR dkslbw@gmail.com
 * @TIME 2014年11月23日 上午10:56:20
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 */
package com.iyiming.mobile.view.activity.my;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iyiming.mobile.R;
import com.iyiming.mobile.net.FileUploadUtil;
import com.iyiming.mobile.util.AppInfoUtil;
import com.iyiming.mobile.util.DateUtil;
import com.iyiming.mobile.util.ILog;
import com.iyiming.mobile.util.ImageManager;
import com.iyiming.mobile.util.ImageUtil;
import com.iyiming.mobile.view.activity.BaseActivity;
import com.iyiming.mobile.view.activity.my.album.ShowImageActivity;
import com.iyiming.mobile.view.widget.NavBar;
import com.iyiming.mobile.view.widget.roundedimageview.RoundedImageView;

/**
 * @DESCRIBE ...
 */
public class UserInfoActivity extends BaseActivity {

	private TextView nikeName;
	private TextView phone;
	private TextView name;
	private TextView sex;
	private TextView address;

	private ImageView editNickname;

	private RoundedImageView avatar;

	private NavBar navBar;

	private PopupWindow popWindow;
	
	private final String ua="ua";
	
	private Bitmap photo;

	/**
	 * 头像地址
	 */
	private String tempPicPath = null;

	/** 拍照 */
	public static final int ACTIVITY_RESULT_CAMERA = 101;
	/** 图库 */
	public static final int ACTIVITY_RESULT_GALARY = 102;
	/** 图片裁剪 */
	private static final int ACTIVITY_RESULT_CROP = 103;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		editNickname = (ImageView) findViewById(R.id.edit_nickname);
		avatar = (RoundedImageView) findViewById(R.id.avatar);
		nikeName = (TextView) findViewById(R.id.nikename);
		phone = (TextView) findViewById(R.id.phone);
		name = (TextView) findViewById(R.id.name);
		sex = (TextView) findViewById(R.id.sex);
		address = (TextView) findViewById(R.id.address);
		navBar = (NavBar) findViewById(R.id.navBar1);
		navBar.setTitle("个人资料");
		navBar.hideLeft(false);
		navBar.hideRight(true);
		navBar.isNav(true);
	}

	private void initData() {

		nikeName.setText(application.user.getNickName() == null ? application.user.getUsername() : application.user.getNickName());
		phone.setText(application.user.getMobile() == null ? "" : application.user.getMobile());
		name.setText(application.user.getRealName() == null ? "" : application.user.getRealName());
		sex.setText(application.user.getSex() == null ? "" : application.user.getSex());
		address.setText(application.user.getAddress() == null ? "" : application.user.getAddress());
		ImageManager.getInstance(this).getImage(avatar, AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + application.user.getImageUrl(),R.drawable.avatar_default, true);
	}

	private void initListener() {
		editNickname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("type", "nickName");
				intent.setClass(UserInfoActivity.this, EditProfileActivity.class);
				startActivity(intent);
			}
		});
		
		name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("type", "name");
				intent.setClass(UserInfoActivity.this, EditProfileActivity.class);
				startActivity(intent);
			}
		});
		
		sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("type", "sex");
				intent.setClass(UserInfoActivity.this, EditProfileActivity.class);
				startActivity(intent);
			}
		});
		
		address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("type", "address");
				intent.setClass(UserInfoActivity.this, EditProfileActivity.class);
				startActivity(intent);
			}
		});


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
		avatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopWindow(v);
			}
		});

	}

	// ===================点击更换头像=========================
	private View initPopView() {
		View popView = View.inflate(this, R.layout.layout_pop_select_head_icon, null);
		// 拍照
		TextView camera = (TextView) popView.findViewById(R.id.tv_pop_select_head_icon_camera);
		// 选择
		TextView select = (TextView) popView.findViewById(R.id.tv_pop_select_head_icon_select);
		// 取消
		TextView tvCancel = (TextView) popView.findViewById(R.id.tv_pop_select_head_icon_cancel);

		camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hidePopWindow(); // 拍照
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				tempPicPath = application.HeadSDCardPath + DateUtil.formatDateTime(new Date()) + ".png";

				new File(application.HeadSDCardPath).mkdirs();

				File headFile = new File(tempPicPath);

				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(headFile));
				startActivityForResult(intent, ACTIVITY_RESULT_CAMERA);

			}
		});
		select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/* 使用自定义的图库 */
				hidePopWindow();
				Intent it = new Intent();
				it.setClass(UserInfoActivity.this, ShowImageActivity.class);
				it.putExtra("num", 1);
				startActivityForResult(it, ACTIVITY_RESULT_GALARY);
			}
		});
		tvCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hidePopWindow();
			}
		});

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
	 * 将PopWindow初始化并显示
	 * 
	 * @author zhangshuo
	 * @date 2013-4-23 上午11:26:22
	 * @version
	 * @param referView
	 *            作为锚点的View
	 * @param layoutView
	 *            作为PopWindow内部的布局样式
	 */
	private void showPopWindow(View referView) {

		View layoutView = initPopView();
		popWindow = new PopupWindow(layoutView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		/* 设置背景为空，解决点击PopupWindow中的RadioButton是RadioButton的背景变为透明的问题 */
		popWindow.setBackgroundDrawable(null);
		/* 设置PopupWindow外部区域是否可触摸 */
		popWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		popWindow.setTouchable(true); // 设置PopupWindow可触摸
		popWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		popWindow.setAnimationStyle(R.style.pop_anim_in_down_to_up);
		popWindow.showAtLocation(referView, Gravity.NO_GRAVITY, 0, 0);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK && requestCode == ACTIVITY_RESULT_CAMERA) {
			ILog.d("ResumeActivity=相机");
			if (tempPicPath == null) {
				showToast("获取图片失败");
				return;
			}
			Uri uri = Uri.fromFile(new File(tempPicPath));
			ILog.e("ResumeActivity=相机uri=" + uri.getPath());

			final Intent intent1 = new Intent("com.android.camera.action.CROP");
			intent1.setDataAndType(uri, "image/*");
			intent1.putExtra("crop", "true");
			intent1.putExtra("aspectX", 1);
			intent1.putExtra("aspectY", 1);
			intent1.putExtra("outputX", 300);
			intent1.putExtra("outputY", 300);
			intent1.putExtra("return-data", true);
			startActivityForResult(intent1, ACTIVITY_RESULT_CROP);

		} else if (resultCode == RESULT_OK && requestCode == ACTIVITY_RESULT_GALARY) {
			ILog.d("ResumeActivity=图库");
			try {
				Bundle bundle = data.getExtras();
				ArrayList<String> paths = bundle.getStringArrayList("dataList");
				if (null != paths && paths.size() > 0) {
					if (paths.get(0) == null) {
						showToast("加载失败");
						return;
					}
					Uri uri = Uri.fromFile(new File(paths.get(0)));
					ILog.e("ResumeActivity=图库uri=" + uri.getPath());

					final Intent intent1 = new Intent("com.android.camera.action.CROP");
					intent1.setDataAndType(uri, "image/*");
					intent1.putExtra("crop", "true");
					intent1.putExtra("aspectX", 1);
					intent1.putExtra("aspectY", 1);
					intent1.putExtra("outputX", 300);
					intent1.putExtra("outputY", 300);
					intent1.putExtra("return-data", true);
					startActivityForResult(intent1, ACTIVITY_RESULT_CROP);
				} else {
					showToast("加载失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				showToast("加载失败");
				return;
			}
		} else if (resultCode == RESULT_OK && requestCode == ACTIVITY_RESULT_CROP) {
			ILog.d("ResumeActivity=剪裁");
			Bundle bundle = data.getExtras();
			photo = (Bitmap) bundle.getParcelable("data");
			String dir = application.HeadSDCardPath;
			String fileName = DateUtil.formatDateTime(new Date()) + ".png";
			try {
				ImageUtil.saveFile(photo, dir, fileName);
			} catch (IOException e) {
				showToast("裁剪图片失败");
				return;
			}
			
			////上传图片
			uploadHeadIcon(dir + fileName);

		} else if (resultCode == RESULT_CANCELED) {
			ILog.w("ResumeActivity--resultCode-->取消");
		} else {
			ILog.e("ResumeActivity--未知ResultCode" + resultCode);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case 1:
				showToast("修改成功");
				avatar.setImageBitmap(photo);
				ImageManager.getInstance(UserInfoActivity.this).mMemoryCache.put(AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + application.user.getImageUrl(), photo);
				ImageManager.getInstance(UserInfoActivity.this).mDiskCache.put(AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + application.user.getImageUrl(), photo);
				break;
				
			case 2:
				showToast("修改失败");
				break;

			default:
				showToast("修改失败，请重试");
				break;
			}
			
		}
		
	};
	
	private void uploadHeadIcon(final String path)
	{
//		:;
//	: form-data; name=""
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					FileUploadUtil.post(path, ua, addParam(ua),handler);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		
		
	
		
//		File file=new File(path);
//		Map<String,File> fileMap=new HashMap<String,File>();
//		fileMap.put("avatar", file);
//		Map<String,String> paramMap=new HashMap<String,String>();
//		paramMap.put("Content-Type","multipart/form-data");
//		paramMap.put("Content-Disposition","form-data; name=\"avatar\"");
//		multiUpload(ua, fileMap, paramMap, ua);
	}

	@Override
	protected void onResume() {
		super.onResume();
		nikeName.setText(application.user.getNickName() == null ? application.user.getUsername() : application.user.getNickName());
		phone.setText(application.user.getMobile() == null ? "" : application.user.getMobile());
		name.setText(application.user.getRealName() == null ? "" : application.user.getRealName());
		sex.setText(application.user.getSex() == null ? "" : application.user.getSex());
		address.setText(application.user.getAddress() == null ? "" : application.user.getAddress());
		ImageManager.getInstance(this).getImage(avatar, AppInfoUtil.sharedAppInfoUtil().getImageServerUrl() + application.user.getImageUrl(),R.drawable.avatar_default, true);
	}

}
