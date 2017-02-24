package de.nupoex.hashcode.analyzer;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import de.nupoex.hashcode.model.Endpoint;
import de.nupoex.hashcode.model.Model;
import de.nupoex.hashcode.model.Request;

public class Analyzer {
	
	public static long computeScore(Model model, Map<Integer, Collection<Integer>> solution)
	{
		long score = 0;
		long totalRequestCount = 0;
		for (Request request : model.getRequests())
		{
			long requestCount = request.getCount();
			totalRequestCount += requestCount;
			Endpoint endpoint =  model.getEndpoint(request.getEndpointIndex());
			long originalLatency = endpoint.getLatency();
			
			Integer bestCacheLatency = getBestLatency(request.getVideoIndex(), solution, endpoint.getCacheLatency());
			if (bestCacheLatency == null || bestCacheLatency > originalLatency) {
				continue;
			}
			score += (originalLatency - bestCacheLatency) * requestCount;
		}
		return 1000L * score / totalRequestCount;
	}

	private static Integer getBestLatency(int videoIndex, Map<Integer, Collection<Integer>> solution, Map<Integer, Integer> caches) {
		Integer result = null;
		for (Entry<Integer, Collection<Integer>> entry : solution.entrySet()) {
			int cacheIndex = entry.getKey();
			Collection<Integer> cachedVideos = entry.getValue();
			if (cachedVideos.contains(videoIndex) && caches.containsKey(cacheIndex)) {
				int latency = caches.get(cacheIndex);
				if (result == null || result > latency) {
					result = latency;
				}
			}
		}
		return result;
	}
	
}
