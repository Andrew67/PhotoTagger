package com.andrew67.phototagger;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final GridView grid = (GridView) findViewById(R.id.gridview);
		final ThumbnailAdapter thumbnails = new ThumbnailAdapter(this);
		grid.setAdapter(thumbnails);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				final String imagePath = thumbnails.getImagePath(position);
				//final Intent i = new Intent(MainActivity.this, TagActivity.class);
				//i.putExtra("imagePath", imagePath);
				Log.d("MainActivity", "path: " + imagePath);
				//startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
