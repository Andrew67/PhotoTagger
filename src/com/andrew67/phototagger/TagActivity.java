package com.andrew67.phototagger;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TagActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		
		final Intent i = getIntent();
		final String imgPath = i.getExtras().getString("imgPath");
		final File imgFile = new File(imgPath);
		final String imgName = imgFile.getName();
		final ImageMetadata imgData = new ImageMetadata(this, imgName);
		
		final TextView imageTitle = (TextView) findViewById(R.id.imageTitle);
		final TextView imageDescription = (TextView) findViewById(R.id.imageDescription);
		final ImageView imageView = (ImageView) findViewById(R.id.imageView);
		
		imageView.setImageBitmap(
				ScaledBitmapFactory.decodeSampledBitmapFromFile(imgPath, imageView.getWidth(), 300));
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.fromFile(new File(imgPath))));
			}
		});
		imageTitle.setText(imgData.getTitle());
		imageDescription.setText(imgData.getDescription());
	}
	
}
