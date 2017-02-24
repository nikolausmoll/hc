package de.nupoex.hashcode.model;

import de.nupoex.hashcode.Logger;

public class Model {

	private Video[] videos;
	private Endpoint[] endpoints;
	private Request[] requests;
	private Cache[] caches;
	
	private final int v;
	private final int e;
	private final int r;
	private final int c;
	private final int x;

	public Model(int v, int e, int r, int c, int x) {
		Logger.log("v: " + v + " e: " + e + " r: " + r + " c: " + c + " x: " + x);
		this.v = v;
		this.e = e;
		this.r = r;
		this.c = c;
		this.x = x;

		videos = new Video[v];
		endpoints = new Endpoint[e];
		requests = new Request[r];
		caches = new Cache[c];
		for (int i = 0; i < c; i ++) {
			caches[i] = new Cache(i, x);
		}
	}

	public void setVideo(int index, Video video) {
		videos[index] = video;
	}

	public void setEndpoint(int index, Endpoint endpoint) {
		endpoints[index] = endpoint;
	}

	public void setRequest(int index, Request request) {
		requests[index] = request;
	}

	public Video getVideo(int i) {
		return videos[i];
	}

	public Request[] getRequests() {
		return requests;
	}

	public Endpoint getEndpoint(int endpointIndex) {
		return endpoints[endpointIndex];
	}
	
	public Cache[] getCaches()
	{
		return caches;
	}

	public Cache getCache(int index)
	{
		return caches[index];
	}

}
