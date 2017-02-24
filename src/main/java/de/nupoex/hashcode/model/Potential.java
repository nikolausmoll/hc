package de.nupoex.hashcode.model;

import java.util.Comparator;

public class Potential {

	private final int cacheIndex;
	private final long potential;
	private final double priority;
	private final Video video;
	private final Request request;
	private int endpointIndex;

	public Potential(Request request, Video video, Endpoint endpoint, Integer cacheIndex, long potential) {
		this.request = request;
		this.cacheIndex = cacheIndex;
		this.potential = potential;
		this.video = video;
		this.endpointIndex = endpoint.getIndex();
		priority = (double) potential / video.getSizeInMb();
	}

	public int getCacheIndex() {
		return cacheIndex;
	}

	public long getPotential() {
		return potential;
	}

	public double getPriority() {
		return priority;
	}

	public Request getRequest() {
		return request;
	}

	public Video getVideo() {
		return video;
	}
	
	public int getEndpointIndex() {
		return endpointIndex;
	}

	public static class PriorityComparator implements Comparator<Potential> {

		@Override
		public int compare(Potential o1, Potential o2) {
			if (o1.getPriority() < o2.getPriority()) {
				return 1;
			} else if (o1.getPriority() > o2.getPriority()) {
				return -1;
			} else {
				return 0;
			}
		}

	}


}