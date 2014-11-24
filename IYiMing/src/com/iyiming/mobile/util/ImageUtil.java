package com.iyiming.mobile.util;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.iyiming.mobile.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

/**
 * 图片显示帮助类(网络，本地 L1 L2 缓存)
 * @author F
 *
 */
public class ImageUtil {

	private static ImageUtil imageUtil;
	//容器的上下文
	private Context context;
	
	private RequestQueue mQueue;
	
	ImageLoader imageLoader;
	
	private ImageUtil(Context context)
	{
		this.context=context;
		mQueue = Volley.newRequestQueue(this.context);
		imageLoader = new ImageLoader(mQueue,new BitmapCache());
	}
	
	public static ImageUtil getInstance(Context context)
	{
		if(imageUtil==null)
		{
			imageUtil=new ImageUtil(context);
		}
		return imageUtil;
	}
	
	/**
	 * 从网络或者硬盘获取图片
	 * @param imageView
	 */
	public void getImage(ImageView imageView,String url)
	{
		ImageListener listener=ImageLoader.getImageListener(imageView,R.drawable.default_bg,R.drawable.default_bg); 
		imageLoader.get(url,listener);
	}
	
	
	//图片L1缓存
	public class BitmapCache implements ImageCache{
		
		private LruCache<String, Bitmap> mCache;  
		  
	    public BitmapCache() {  
	        int maxSize = 10 * 1024 * 1024;  
	        mCache = new LruCache<String, Bitmap>(maxSize) {  
	            @Override  
	            protected int sizeOf(String key, Bitmap bitmap) {  
	                return bitmap.getRowBytes() * bitmap.getHeight();  
	            }  
	        };  
	    }  
	  
	    @Override  
	    public Bitmap getBitmap(String url) {  
	        return mCache.get(url);  
	    }  
	  
	    @Override  
	    public void putBitmap(String url, Bitmap bitmap) {  
	        mCache.put(url, bitmap);  
	    }  

	}
	
}
