package de.nupoex.hashcode.model;

import java.util.ArrayList;
import java.util.List;

public class Cache {

	private final int index;
	private int freeCapacity;
	private List<Video> videos = new ArrayList<>();
	
	public Cache(int index, int capacity)
	{
		this.index = index;
		this.freeCapacity = capacity;
	}
	
	public boolean hasEnoughSpace(int size)
	{
		return freeCapacity >= size;
	}
	
	public void addVideo(Video video)
	{
		videos.add(video);
		freeCapacity = freeCapacity - video.getSizeInMb();
	}
	
	public List<Video> getVideos()
	{
		return videos;
	}

	public int getIndex() {
		return index;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public boolean contains(Video video) {
		return videos.contains(video);
	}
	
	
}
