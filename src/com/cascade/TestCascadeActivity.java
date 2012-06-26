package com.cascade;

import java.io.File;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TestCascadeActivity extends Activity {
	private static final int more = Menu.FIRST;

	private LinearLayout lvCasecade1;
	private LinearLayout lvCasecade2;
	private LinearLayout lvCasecade3;

	private Display display;
	private String[] imagePaths;
	private int casecadeWidth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		display = this.getWindowManager().getDefaultDisplay();
		casecadeWidth = display.getWidth() / 3;
		findView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, more, 0, getString(R.string.more_action)).setIcon(
				this.getResources().getDrawable(R.drawable.more));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			setImage();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void findView() {
		lvCasecade1 = (LinearLayout) this.findViewById(R.id.casecade1);
		lvCasecade2 = (LinearLayout) this.findViewById(R.id.casecade2);
		lvCasecade3 = (LinearLayout) this.findViewById(R.id.casecade3);
		LayoutParams lp1 = lvCasecade1.getLayoutParams();
		lp1.width = casecadeWidth;
		lvCasecade1.setLayoutParams(lp1);
		LayoutParams lp2 = lvCasecade2.getLayoutParams();
		lp2.width = casecadeWidth;
		lvCasecade2.setLayoutParams(lp2);
		LayoutParams lp3 = lvCasecade3.getLayoutParams();
		lp3.width = casecadeWidth;
		lvCasecade3.setLayoutParams(lp3);
		setImage();
	}

	private void setImage() {
		imagePaths = new File("/sdcard/weittu").list();
		int j = 0;
		for (int i = 0; i < imagePaths.length; i++) {
			if (imagePaths[i].endsWith(".jpg")) {
				addImgToCasecade(imagePaths[i], j);
				j++;
				if (j >= 3)
					j = 0;
			}
		}
	}

	private void addImgToCasecade(String filename, final int j) {
		ImageView iv = (ImageView) LayoutInflater.from(this).inflate(
				R.layout.item, null);
		if (j == 0) {
			lvCasecade1.addView(iv);
		} else if (j == 1) {
			lvCasecade2.addView(iv);
		} else {
			lvCasecade3.addView(iv);
		}
		Drawable imageDrawable = Drawable.createFromPath("/sdcard/weittu/" + filename);
		int oldwidth = imageDrawable.getIntrinsicWidth();
		int oldheight = imageDrawable.getIntrinsicHeight();
		LayoutParams lp = iv.getLayoutParams();
		lp.height = (oldheight * casecadeWidth) / oldwidth;
		iv.setPadding(0, 2, 0, 0);
		iv.setLayoutParams(lp);
		iv.setImageDrawable(imageDrawable);
	}
}