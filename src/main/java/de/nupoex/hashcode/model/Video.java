package de.nupoex.hashcode.model;

import de.nupoex.hashcode.Logger;

public class Video {

	private final int index;
	private final int sizeInMb;
	
	public Video(int index, int sizeInMb)
	{
		Logger.log("Video with " + sizeInMb);
		this.index = index;
		this.sizeInMb = sizeInMb;
	}

	public int getSizeInMb() {
		return sizeInMb;
	}

	public int getIndex() {
		return index;
	}

}
