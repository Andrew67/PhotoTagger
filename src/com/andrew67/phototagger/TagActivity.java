package com.andrew67.phototagger;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TagActivity extends Activity {
	
	private ImageMetadata imgData;
	private TextView imageTitle;
	private TextView imageDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		
		final Intent i = getIntent();
		final String imgPath = i.getExtras().getString("imgPath");
		final File imgFile = new File(imgPath);
		final String imgName = imgFile.getName();
		imgData = new ImageMetadata(this, imgName);
		
		imageTitle = (TextView) findViewById(R.id.imageTitle);
		imageDescription = (TextView) findViewById(R.id.imageDescription);
		final ImageView imageView = (ImageView) findViewById(R.id.imageView);
		
		imageView.setImageBitmap(
				ScaledBitmapFactory.decodeSampledBitmapFromFile(imgPath, imageView.getWidth(), 300));
		
		imageView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.fromFile(new File(imgPath))));
				return false;
			}
		});
		imageTitle.setText(imgData.getTitle());
		imageDescription.setText(imgData.getDescription());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_save:
			imgData.setTitle(imageTitle.getText().toString());
			imgData.setDescription(imageDescription.getText().toString());
			if (imgData.saveChanges()) {
				Toast.makeText(this, R.string.save_ok, Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(this, R.string.save_error, Toast.LENGTH_LONG).show();
			}
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
