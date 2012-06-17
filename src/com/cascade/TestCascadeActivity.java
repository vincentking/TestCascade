package com.cascade;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cascade.DrawableManager.ImageCallback;

public class TestCascadeActivity extends Activity {
	private static final String TAG = "TestCascadeActivity";
	private static final int more = Menu.FIRST;
//	private static long lane1 = 0;
//	private static long lane2 = 0;
//	private static long lane3 = 0;
	
	private LinearLayout llCcasecade;
	private LinearLayout lvCasecade1;
	private LinearLayout lvCasecade2;
	private LinearLayout lvCasecade3;
	
	private Display display;
	private AssetManager assetManager;
	private List<String> iamgePaths;
	private static final String imgspath = "imgs";
	private int casecadeWidth;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        display = this.getWindowManager().getDefaultDisplay();
        casecadeWidth = display.getWidth()/3;
        assetManager = this.getAssets();
        findView();
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, more, 0, getString(R.string.more_action)).setIcon(this.getResources().getDrawable(R.drawable.more));
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			findView();
			break;
		}
		return super.onOptionsItemSelected(item);
    }
    private static int more_loop = 0;
    private void findView(){
    	more_loop++;
    	llCcasecade = (LinearLayout)this.findViewById(R.id.llCcasecade);
    	lvCasecade1 = (LinearLayout)this.findViewById(R.id.casecade1);
    	lvCasecade2 = (LinearLayout)this.findViewById(R.id.casecade2);
    	lvCasecade3 = (LinearLayout)this.findViewById(R.id.casecade3);
    	LayoutParams lp1 = lvCasecade1.getLayoutParams();
    	lp1.width = casecadeWidth;
    	lvCasecade1.setLayoutParams(lp1);
    	
    	LayoutParams lp2 = lvCasecade2.getLayoutParams();
    	lp2.width = casecadeWidth;
    	lvCasecade2.setLayoutParams(lp2);
    	
    	LayoutParams lp3 = lvCasecade3.getLayoutParams();
    	lp3.width = casecadeWidth;
    	lvCasecade3.setLayoutParams(lp3);
    	
    	try {
			iamgePaths = Arrays.asList(assetManager.list("imgs"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int j = 0;
		for (int i = 0; i < iamgePaths.size(); i++) {
//			long lane = Math.min(Math.min(lane1, lane2), Math.min(lane2, lane3));
//			if(lane==lane2)
//				j = 1;
//			else if(lane==lane3)
//				j = 2;
			addImgToCasecade(iamgePaths.get(i), j);
			j++;
			if(j>=3) j = 0;
		}
    }
    
    private void addImgToCasecade(String filename,final int j){
    	ImageView iv = (ImageView)LayoutInflater.from(this).inflate(R.layout.item, null);
    	if(j==0){
    		lvCasecade1.addView(iv);
    	}else if(j==1){
    		lvCasecade2.addView(iv);
    	}else{
    		lvCasecade3.addView(iv);
    	}
		String imgPath = imgspath+"/"+filename;
		iv.setTag(imgPath+more_loop);
		Drawable drawable = DrawableManager.getInstance().fetchDrawableOnThread(imgPath, assetManager, new ImageCallback() {
			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				ImageView iv = (ImageView)llCcasecade.findViewWithTag(imageUrl+more_loop);
				if(iv!=null && imageDrawable!=null){
					int oldwidth = imageDrawable.getIntrinsicWidth();
					int oldheight = imageDrawable.getIntrinsicHeight();
					LayoutParams lp = iv.getLayoutParams();
					lp.height = (oldheight * casecadeWidth)/oldwidth;
					iv.setPadding(0, 2, 0, 0);
					iv.setLayoutParams(lp);
					iv.setImageDrawable(imageDrawable);
//					switch(j)
//					{
//						case 0:
//							lane1 += lp.height;
//							break;
//						case 1:
//							lane2 += lp.height;
//							break;
//						case 2:
//							lane3 += lp.height;
//							break;
//					}
				}
			}
		});
//		if(drawable!=null){
//			int oldwidth = drawable.getIntrinsicWidth();
//			int oldheight = drawable.getIntrinsicHeight();
//			LayoutParams lp = iv.getLayoutParams();
//			lp.height = (oldheight * casecadeWidth)/oldwidth;
//			iv.setPadding(0, 2, 0, 0);
//			iv.setLayoutParams(lp);
//			iv.setImageDrawable(drawable);
//		}
    }
}