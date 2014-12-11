/**@文件:UpdateService.java
 * @作者:dkslbw@gmail.com
 * @日期:2014年7月30日 下午7:07:12*/
package com.iyiming.mobile.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.iyiming.mobile.R;
import com.iyiming.mobile.view.activity.MainActivity;

/**
 * @公司: 南京红松信息技术有限公司
 * @CLASS:UpdateService
 * @描述:
 * @作者:dkslbw@gmail.com
 * @版本:v1.0
 * @日期:2014年7月30日 下午7:07:12
 */

public class UpdateService extends Service {
	private static final String TAG = "AppUpdateService";

	// 标题
	private String titleId;
	private String appName;
	private String appId;
	private final static int DOWNLOAD_COMPLETE = 0;
	private final static int DOWNLOAD_FAIL = 1;
	// 文件存储
	private File updateDir = null;
	private File updateFile = null;
	// 通知栏
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	// 通知栏跳转Intent
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 获取传值
		titleId = intent.getStringExtra("titleId");
		appName = intent.getStringExtra("appname");
		appId = intent.getStringExtra("downurl");
		// 创建文件
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			updateDir = new File(Environment.getExternalStorageDirectory(), "app/download/");
			updateFile = new File(updateDir.getPath(), titleId);
		} else {
			Toast.makeText(UpdateService.this, "未检测到SD卡，更新失败", Toast.LENGTH_SHORT).show();
			return super.onStartCommand(intent, flags, startId);
		}
		updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		updateNotification = new Notification(R.drawable.ic_launcher, "开始下载" + appName, System.currentTimeMillis());
		updateNotification.contentView = new RemoteViews(getPackageName(), R.layout.notification);
		updateNotification.contentView.setProgressBar(R.id.capacity_progressbar, 100, 0, false);
		updateNotification.contentView.setTextViewText(R.id.down_tv, "正在下载" + appName + "0%");
		// 设置下载过程中，点击通知栏，回到主界面
		updateIntent = new Intent(this, MainActivity.class);
		updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
		// 设置通知栏显示内容
		// 发出通知
		updateNotificationManager.notify(0, updateNotification);
		// 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
		new Thread(new updateRunnable()).start();// 这个是下载的重点，是下载的过程
		return super.onStartCommand(intent, flags, startId);
	}

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_COMPLETE:
				updateNotification.flags |= updateNotification.FLAG_AUTO_CANCEL;
				// 点击安装PendingIntent
				Uri uri = Uri.fromFile(updateFile);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
				updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, installIntent, 0);
				updateNotification.defaults = Notification.DEFAULT_SOUND;// 铃声提醒
				// updateNotification.setLatestEventInfo(UpdateService.this,
				// appName, "下载完成,点击安装", updatePendingIntent);
				updateNotification.contentView.setProgressBar(R.id.capacity_progressbar, 100, 100, false);
				// Toast.makeText(UpdateService.this, "下载完成",
				// Toast.LENGTH_SHORT).show();
				// updateNotification.contentView.setTextViewText(R.id.down_tv,
				// "下载完成，点击安装");
				updateNotification.icon = R.drawable.ic_launcher;
				updateNotification.setLatestEventInfo(UpdateService.this, appName, "下载完成，点击安装", updatePendingIntent);
				updateNotificationManager.notify(0, updateNotification);
				// 停止服务
				startActivity(installIntent);
				stopService(updateIntent);
				break;
			case DOWNLOAD_FAIL:
				// 下载失败
				updateNotification.icon = R.drawable.ic_launcher;
				updateNotification.setLatestEventInfo(UpdateService.this, appName, "下载失败", null);
				updateNotificationManager.notify(0, updateNotification);
				break;
			default:
				stopService(updateIntent);
				break;
			}
		}
	};

	class updateRunnable implements Runnable {
		Message message = updateHandler.obtainMessage();

		public void run() {
			message.what = DOWNLOAD_COMPLETE;
			try {
				if (!updateDir.exists()) {
					updateDir.mkdirs();
				}
				if (!updateFile.exists()) {
					updateFile.createNewFile();
				}
				long downloadSize = downloadUpdateFile(appId, updateFile);
				if (downloadSize > 0) {
					// 下载成功
					updateHandler.sendMessage(message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				message.what = DOWNLOAD_FAIL;
				// 下载失败
				updateHandler.sendMessage(message);
			}
		}
	}

	public long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {

		int downloadCount = 0;
		int currentSize = 0;
		long totalSize = 0;
		int updateTotalSize = 0;

		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("User-Agent", "HongSong");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
			}
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);
			byte buffer[] = new byte[4096];
			int readsize = 0;
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
				if ((downloadCount == 0) || (int) (totalSize * 100 / updateTotalSize) - 3 > downloadCount) {
					downloadCount += 3;

					updateNotification.contentView.setTextViewText(R.id.down_tv, "正在下载" + appName + (totalSize * 100 / updateTotalSize) + "%");
					updateNotification.contentView.setProgressBar(R.id.capacity_progressbar, 100, (int) ((totalSize * 100 / updateTotalSize) + 0.5),
							false);
					updateNotificationManager.notify(0, updateNotification);
				}
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return totalSize;
	}

}
