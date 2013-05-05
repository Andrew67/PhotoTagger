package com.andrew67.phototagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import android.content.Context;

public class ImageMetadata {

	private final File dataFile;
	private String title;
	private String description;
	private boolean hasChanged;
	
	public ImageMetadata(final Context c, final String imageName) {
		dataFile = new File(
				c.getExternalFilesDir(null).toString() +
				File.separator +
				imageName +
				".dat");
		
		if (!dataFile.exists()) {
			title = imageName.replace(".jpg", "");
			description = "";
		}
		else {
			try {
				final Scanner fileIn = new Scanner(new FileInputStream(dataFile));
				fileIn.useDelimiter("\n");
				if (fileIn.hasNext()) {
					title = fileIn.next();
				}
				if (fileIn.hasNext()) {
					description = fileIn.next();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		hasChanged = false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (!this.title.equals(title)) {
			this.title = title;
			hasChanged = true;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (!this.description.equals(description)) {
			this.description = description;
			hasChanged = true;
		}
	}
	
	public boolean saveChanges() {
		if (!hasChanged) return true;
		
		try {
			final OutputStream fileOut = new FileOutputStream(dataFile);
			fileOut.write(title.getBytes());
			fileOut.write('\n');
			fileOut.write(description.getBytes());
			fileOut.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
