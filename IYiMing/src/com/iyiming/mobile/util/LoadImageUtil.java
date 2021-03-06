package com.iyiming.mobile.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.iyiming.mobile.R;
import com.iyiming.mobile.net.HeaderImageLoader;
import com.iyiming.mobile.net.HeaderImageLoader.ImageCache;
import com.iyiming.mobile.net.HeaderImageLoader.ImageListener;
import com.iyiming.mobile.view.activity.IYiMingApplication;

/**
 * 图片显示帮助类(网络，本地 L1 L2 缓存)
 * @author F
 *
 */
public class LoadImageUtil {

	private static LoadImageUtil imageUtil;
	//容器的上下文
	private Context context;
	
	private RequestQueue mQueue;
	
	HeaderImageLoader imageLoader;
	
	private LoadImageUtil(Context context)
	{
		this.context=context;
		mQueue = Volley.newRequestQueue(this.context);
		imageLoader = new HeaderImageLoader(mQueue,new BitmapCache());
		
	}
	
	public static LoadImageUtil getInstance(Context context)
	{
		if(imageUtil==null)
		{
			imageUtil=new LoadImageUtil(context);
		}
		return imageUtil;
	}
	
	/**
	 * 从网络或者硬盘获取图片
	 * @param imageView
	 */
	public void getImage(ImageView imageView,String url)
	{
		ImageListener listener=HeaderImageLoader.getImageListener(imageView,R.color.cornsilk,R.color.cornsilk);
		imageLoader.get(url,listener);
	}
	
	/**
	 * 从网络或者硬盘获取图片
	 * @param imageView
	 */
	public void getImage(ImageView imageView,String url,boolean isLogin)
	{
		
		ImageListener listener=HeaderImageLoader.getImageListener(imageView,R.color.cornsilk,R.color.cornsilk); 
		if(isLogin)
		{
			Map<String, String> headers=new HashMap<String, String>();
			if(IYiMingApplication.SESSION_ID!=null&&IYiMingApplication.SESSION_ID.length()!=0)
			{
				headers.put("Cookie", "JSESSIONID="+IYiMingApplication.SESSION_ID);
			}
			ILog.e("[图片header]"+headers.toString());  
			imageLoader.get(url,listener,headers);
		}
		else
		{
			imageLoader.get(url,listener);
		}
	}
	
	
	//图片L1缓存
	public class BitmapCache implements ImageCache{
		
		private LruCache<String, Bitmap> mCache;  
		  
	    public BitmapCache() {  
	        int maxSize = 50 * 1024 * 1024;
	        mCache = new LruCache<String, Bitmap>(maxSize) {
	            @Override  
	            protected int sizeOf(String key, Bitmap bitmap) {  
	                return bitmap.getRowBytes() * bitmap.getHeight();  
	            }  
	        };  
	    }  
	  
	    @Override  
	    public Bitmap getBitmap(String url){  
	        return mCache.get(url);
	    }  
	  
	    @Override  
	    public void putBitmap(String url, Bitmap bitmap) {  
	        mCache.put(url, bitmap);  
	    }  

	}
	

	
}
