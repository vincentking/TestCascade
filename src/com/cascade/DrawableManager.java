package com.cascade;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DrawableManager {

//	private static Map<String, Drawable> drawableMap = new LinkedHashMap<String, Drawable>();
	private static DrawableManager instance;

	private DrawableManager() {
	}

	public static DrawableManager getInstance() {
		if (instance == null) {
			instance = new DrawableManager();
		}
		return instance;
	}

	public void clear() {
//		if (drawableMap != null) {
//			drawableMap.clear();
//			drawableMap = null;
//		}
//		Log.d(this.getClass().getSimpleName(), "clear image hashmap");
	}


	public Drawable fetchDrawableOnThread(final String urlString,final AssetManager assetManager,
			final ImageCallback imageCallback) {
//		if (drawableMap == null) {
//			drawableMap = new HashMap<String, Drawable>();
//		}
//		if (drawableMap.containsKey(urlString)) {
//			return drawableMap.get(urlString);
//		}
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, urlString);
			}
		};

		Thread thread = new Thread() {
			@Override
			public void run() {
				Drawable drawable = fetchDrawable(urlString,assetManager);
				Message message = handler.obtainMessage(1, drawable);
				handler.sendMessage(message);
			}
		};
		thread.start();
		return null;
	}
	

	private Drawable fetchDrawable(String urlString,AssetManager assetManager) {
//		if (drawableMap.containsKey(urlString)) {
//			return drawableMap.get(urlString);
//		}

		Log.d(this.getClass().getSimpleName(), "image url:" + urlString);
		InputStream is = null;
		try {
			is = assetManager.open(urlString);
			Drawable drawable = Drawable.createFromStream(is, "src");
//			if (drawableMap == null) {
//				drawableMap = new HashMap<String, Drawable>();
//			}
//			drawableMap.put(urlString, drawable);
			return drawable;
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
		} finally{//关闭流
			try {
				if(is!=null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
//	public Drawable getDrawable(String urlkey) {
//		return drawableMap.get(urlkey);
//	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}
	
//	 public void remove(String key){
//		 if(drawableMap.containsKey(key))
//			 drawableMap.remove(key);
//	 }
}