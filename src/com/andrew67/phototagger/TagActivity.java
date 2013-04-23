package com.andrew67.phototagger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class TagActivity extends Activity {
	
	Bitmap bm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		
		final Intent i = getIntent();
		final String imgPath = i.getExtras().getString("imgPath");
		final ImageView image = (ImageView) findViewById(R.id.imageView);
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		bm = BitmapFactory.decodeFile(imgPath, options);
		image.setImageBitmap(bm);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		bm.recycle();
	}
	
}
