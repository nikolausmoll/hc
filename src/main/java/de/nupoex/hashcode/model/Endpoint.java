package de.nupoex.hashcode.model;

import java.util.HashMap;
import java.util.Map;

import de.nupoex.hashcode.Logger;

public class Endpoint {

	private final int index;
	private final int latency;
	private final int caches;
	private final Map<Integer, Integer> cacheLatency;

	public Endpoint(int index, int latency, int caches) {
		Logger.log("Endpoint with latency " + latency + " and " + caches + " caches");
		this.index = index;
		this.latency = latency;
		this.caches = caches;
		cacheLatency = new HashMap<>();
	}

	public int getLatency() {
		return latency;
	}

	public int getCaches() {
		return caches;
	}

	public void setCache(int cacheIndex, int latency) {
		Logger.log("cache " + cacheIndex + " has latency " + latency);
		cacheLatency.put(cacheIndex, latency);
	}

	public Map<Integer, Integer> getCacheLatency() {
		return cacheLatency;
	}

	public int getIndex() {
		return index;
	}

}
