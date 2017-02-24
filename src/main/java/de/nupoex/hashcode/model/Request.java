package de.nupoex.hashcode.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import de.nupoex.hashcode.Logger;

public class Request {

	private final int index;
	private final int count;
	private final Video video;
	private final Endpoint endpoint;

	public Request(int index, Video video, Endpoint endpoint, int count) {
		Logger.log(count + " requests for video " + video.getIndex() + " from endpoint " + endpoint.getIndex());
		this.index = index;
		this.count = count;
		this.video = video;
		this.endpoint = endpoint;
	}

	public int getCount() {
		return count;
	}

	public int getVideoIndex() {
		return video.getIndex();
	}

	public int getEndpointIndex() {
		return endpoint.getIndex();
	}
	
	public Collection<Potential> calculateRankings() {
		Collection<Potential> ranking = new ArrayList<>();
		for (Entry<Integer, Integer> cacheLatency : endpoint.getCacheLatency().entrySet()) {
			int cacheIndex = cacheLatency.getKey();
			int latency = cacheLatency.getValue();
			if (latency < endpoint.getLatency()) {
				long potential = calculatePotential(cacheIndex);
				ranking.add(new Potential(this, video, endpoint, cacheIndex, potential));
			}
		}
		return ranking;
	}

	private long calculatePotential(int cacheIndex) {
		return count * (endpoint.getLatency() - endpoint.getCacheLatency().get(cacheIndex));
	}

	public int getIndex() {
		return index;
	}
}
