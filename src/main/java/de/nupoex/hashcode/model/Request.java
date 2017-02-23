package de.nupoex.hashcode.model;

import de.nupoex.hashcode.Logger;

public class Request {

	private final int count;
	private final int videoIndex;
	private final int endpointIndex;
	
	public Request(int count, int videoIndex, int endpointIndex) {
		Logger.log(count + " requests for video " + videoIndex + " from endpoint "  + endpointIndex);
		this.count = count;
		this.videoIndex = videoIndex;
		this.endpointIndex = endpointIndex;
	}
	
	
}
