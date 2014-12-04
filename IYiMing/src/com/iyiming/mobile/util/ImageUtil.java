package com.iyiming.mobile.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

/**
 * 图片处理工具类
 * 
 */
public class ImageUtil {

	/** 图片质量压缩后的最大值 */
	public static int maxSize = 150;

	/**
	 * 压缩指定路径图片，并返回新的存储路径
	 * 
	 * @param oldPath
	 *            图片路径
	 * @param newDir
	 *            图片压缩后的存储目录（名称默认使用原名）
	 * @param displayWidth
	 *            屏幕宽
	 * @param displayHeight
	 *            屏幕高
	 * @return
	 */
	public static String getCompressBitmapPath(String oldPath, String newDir,
			int displayWidth, int displayHeight) {
		if(null == oldPath) return null;
		String fileName = oldPath.substring(oldPath.lastIndexOf("/") + 1);
		int inSampleSize = ImageUtil.calculateInSampleSize(oldPath,
				displayWidth, displayHeight);
		try {
			/*大小等比例压缩*/
			Bitmap bitmap = ImageUtil.compressBitmapBounds(oldPath,
					inSampleSize);
			/*旋转角度*/
			bitmap = ImageUtil.rotateBitmap(bitmap, ImageUtil.readPictureDegree(oldPath));
			/*质量压缩*/
			bitmap = ImageUtil.compressBitmapQuality(bitmap);
			return ImageUtil.saveFile(bitmap, newDir, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 图片质量压缩，旨在图片大小和图片失真度之间找到比较好的平衡点,压缩后图片大小在100KB一下
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressBitmapQuality(Bitmap image) {
		if(null == image) return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		int size  = (baos.toByteArray().length / 1024);
		if(size<200){
			return image;
		}
		options  = (maxSize * 100) / (size);// 根据当前图片大小整除压缩最大值限制得到压缩比
		baos.reset();// 重置baos即清空baos
		image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 根据路径获得图片并根据宽高压缩比压缩图片
	 * 
	 * @param filePath
	 *            路径
	 * @param inSampleSize
	 *            高宽压缩比
	 * @return
	 */
	public static Bitmap compressBitmapBounds(String filePath, int inSampleSize) {
		if(null == filePath) return null;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据手机屏幕的宽度和高度，计算图片的宽高缩放值
	 * 
	 * @param filePath
	 *            路径
	 * @param displayWidth
	 *            屏幕宽度
	 * @param displayHeight
	 *            屏幕高度
	 * @return
	 */
	public static int calculateInSampleSize(String filePath, int displayWidth,
			int displayHeight) {
		if(null == filePath) return 1;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > (displayHeight / 2.5) || width > (displayWidth / 2.5)) {
			final int heightRatio = Math.round((float) height
					/ (float) (displayHeight / 2.5));
			final int widthRatio = Math.round((float) width
					/ (float) (displayWidth / 2.5));
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 读取Bitmap的宽度和高度
	 * 
	 * @param filePath
	 * @param width
	 * @param height
	 */
	public static void calculateBitmapBounds(String filePath, int width,
			int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 可以不把图片读到内存中,但依然可以计算出图片的大小
		BitmapFactory.decodeFile(filePath, options);
		height = options.outHeight;
		width = options.outWidth;
	}

	/**
	 * 保存图片
	 * 
	 * @param bm
	 * @param dir
	 *            指定保存目录
	 * @param fileName
	 *            指定图片名称
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(Bitmap bm, String dir, String fileName)
			throws IOException {
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		String path = dir + fileName;
		File myCaptureFile = new File(path);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		bos.flush();
		bos.close();
		return path;
	}

	/**
	 * @方法描述:	将Bitmap转换成Byte[]
	 * @作者:zhangshuo
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
	
	/**
	 * Byte[]转为bitmap
	 * @param base64Data
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] bytes) {
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
	
	/**
	 * 把bitmap转换成Base64 String
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}
	
	/**
	 * base64转为bitmap
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * @方法描述:	读取图片的角度
	 * @作者:zhangshuo
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int rotation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (rotation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * @方法描述:	根据角度，旋转图片
	 * @作者:zhangshuo
	 * @param bitmap
	 * @param degress
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
		if(degress == 0){
			return bitmap;
		}
		if (bitmap != null) {
			Matrix m = new Matrix();
			m.postRotate(degress);
			try {
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), m, true);
			} catch (OutOfMemoryError e) {
				ILog.e("rotateBitmap--旋转图片--内存溢出");
			    e.printStackTrace();
			}
			
			return bitmap;
		}
		return bitmap;
	}
}
