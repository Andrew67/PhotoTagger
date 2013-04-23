package com.andrew67.phototagger;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TagActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		
		final Intent i = getIntent();
		final String imgPath = i.getExtras().getString("imgPath");
		final ImageView image = (ImageView) findViewById(R.id.imageView);
		image.setImageBitmap(
				ScaledBitmapFactory.decodeSampledBitmapFromFile(imgPath, image.getWidth(), 300));
		
		image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.fromFile(new File(imgPath))));
			}
		});
	}
	
}
