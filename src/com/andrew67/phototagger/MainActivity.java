package com.andrew67.phototagger;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends Activity {
	
	private static final int ACTIVITY_SELECT_IMAGE = 0;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final GridView grid = (GridView) findViewById(R.id.gridview);
		final ThumbnailAdapter thumbnails = new ThumbnailAdapter(this);
		grid.setAdapter(thumbnails);
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				final String imgPath = thumbnails.getImagePath(position);
				final Intent i = new Intent(MainActivity.this, TagActivity.class);
				i.putExtra("imgPath", imgPath);
				Log.d("MainActivity", "path: " + imgPath);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("MainActivity", "menu: " + item.getItemId());
		switch (item.getItemId()) {
		// Menu item for selecting an image using a gallery application
		// See http://stackoverflow.com/a/2507973/400663
		case R.id.action_gallery:
			final Intent i = new Intent(Intent.ACTION_PICK,
		               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, 
		       Intent returnedIntent) {
		    super.onActivityResult(requestCode, resultCode, returnedIntent); 

		    switch(requestCode) { 
		    // Handle image selected from gallery application instead of our grid
		    // See http://stackoverflow.com/a/2508138/400663
		    case ACTIVITY_SELECT_IMAGE:
		        if(resultCode == RESULT_OK){  
		            final Uri selectedImage = returnedIntent.getData();
		            final String[] filePathColumn = {MediaStore.Images.Media.DATA};

		            final Cursor cursor = getContentResolver().query(
		                               selectedImage, filePathColumn, null, null, null);
		            cursor.moveToFirst();

		            final int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		            final String imgPath = cursor.getString(columnIndex);
		            cursor.close();

					final Intent i = new Intent(MainActivity.this, TagActivity.class);
					i.putExtra("imgPath", imgPath);
					Log.d("MainActivity", "path: " + imgPath);
					startActivity(i);
		        }
		    }
		}

}
