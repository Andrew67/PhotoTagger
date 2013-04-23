package com.andrew67.phototagger;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

// See http://stackoverflow.com/questions/3680357/how-to-query-android-mediastore-content-provider-avoiding-orphaned-images
public class ThumbnailAdapter extends BaseAdapter {
	
	// Context required for performing queries
	private final Context mContext;
	
	// Cursor for thumbnails
	private final Cursor cursor;
	private final int imgId;
	private final int imgData;
	private final int count;
	
	public ThumbnailAdapter(Context c) {
		this.mContext = c;
		
		// Get list of all images, sorted by last taken first
		final String[] projection = {
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA
		};
		cursor = mContext.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				projection,
				null,
				null,
				MediaStore.Images.Media.DATE_TAKEN + " DESC"
		);
		
		// Set constants (column indices and image count)
		imgId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
		imgData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		count = cursor.getCount();
		Log.d("ThumbnailAdapter", count + " images found");
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(96, 96));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        
        // Move cursor to image position, fetch id, and generate/view thumbnail
        cursor.moveToPosition(position);
        final Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(
				mContext.getContentResolver(),
				cursor.getInt(imgId), 
				MediaStore.Images.Thumbnails.MICRO_KIND,
				null
		);
     	imageView.setImageBitmap(thumbnail);
    	Log.d("ThumbnailAdapter", "render: " + cursor.getString(imgData));
        
        return imageView;
	}
	
	/**
	 * Get the image path from the given position
	 * @param position
	 * @return
	 */
	public String getImagePath(int position) {
		cursor.moveToPosition(position);
		return cursor.getString(imgData);
	}

}
