package com.iyiming.mobile.view.activity.my.album;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.iyiming.mobile.R;




@SuppressLint("NewApi")
public class ShowImageActivity extends Activity {
	
	
	private GridFragment gridFragment;
	
	private ImageItemFragment imageFragment;
	
	public ArrayList<String> selectedDataList = new ArrayList<String>();
	
	public  int num = 0;
	
	/**
	 * 返回键的监听
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(imageFragment!=null){
				if(imageFragment.isAdded()){
					backGrid();
				}else{
					selectedDataList.clear();
					finish();
				}
			}else{
				selectedDataList.clear();
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}



	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_image_activity);
		
		num = getIntent().getIntExtra("num", 0);
		
		FragmentManager manager = getFragmentManager();
		FragmentTransaction t = manager.beginTransaction();
		gridFragment = new GridFragment();
		t.add(R.id.frame, gridFragment);
		t.commit();
		
	
		
	}

	@Override
	public void finish() {
		if (selectedDataList.size() > 0) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("dataList", selectedDataList);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
		
		}
		super.finish();
	}
	
	public void changeDetail(){
		
		if(selectedDataList.size()==0){
			return;
		}
		FragmentManager manager = getFragmentManager();
		FragmentTransaction fragmentTransaction = manager.beginTransaction();
		fragmentTransaction.hide(gridFragment);
		
			imageFragment = new ImageItemFragment(selectedDataList,0);
		fragmentTransaction.add(R.id.frame, imageFragment);
		//fragmentTransaction.replace(R.id.frame, imageFragment);
	//	fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
	public void backGrid(){
		gridFragment.refresh();
		
		FragmentManager manager = getFragmentManager();
		FragmentTransaction fragmentTransaction = manager.beginTransaction();
		fragmentTransaction.remove(imageFragment);
		fragmentTransaction.show(gridFragment);
		//fragmentTransaction.replace(R.id.frame, imageFragment);
	//	fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		
		
		
	}
	
	
	
	
	

	
}
