package de.nupoex.hashcode.reader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import de.nupoex.hashcode.model.Endpoint;
import de.nupoex.hashcode.model.Model;
import de.nupoex.hashcode.model.Request;
import de.nupoex.hashcode.model.Video;

public class DataReader {

	int v;
	int e;
	int r;
	int c;
	int x;

	private Model model;

	public DataReader(String filename) {
		List<String> lines = readFile(filename);
		Iterator<String> iterator = lines.iterator();

		initializeModel(iterator.next());
		readVideos(iterator.next());
		readEndpoints(iterator);
		readRequests(iterator);
	}

	public Model getModel() {
		return model;
	}

	private void readRequests(Iterator<String> iterator) {
		for (int i = 0; i < r; i++) {
			String[] split = iterator.next().split(" ");
			model.setRequest(i,
					new Request(i, model.getVideo(toInt(split[0])), model.getEndpoint(toInt(split[1])), toInt(split[2])));
		}
	}

	private void readEndpoints(Iterator<String> iterator) {
		for (int i = 0; i < e; i++) {
			String[] split = iterator.next().split(" ");
			int cacheCount = toInt(split[1]);
			Endpoint endpoint = new Endpoint(i, toInt(split[0]), cacheCount);
			model.setEndpoint(i, endpoint);
			for (int j = 0; j < cacheCount; j++) {
				String[] cacheLatency = iterator.next().split(" ");
				endpoint.setCache(toInt(cacheLatency[0]), toInt(cacheLatency[1]));
			}
		}
	}

	private void readVideos(String line) {
		int tooBigToCache = 0;
		String[] split = line.split(" ");
		for (int i = 0; i < v; i++) {
			int videoSize = toInt(split[i]);
			if (videoSize > x) {
				tooBigToCache++;
			}
			model.setVideo(i, new Video(i, videoSize));
		}
		System.out.println("Not Cachable videos: " + tooBigToCache + " / " + v);
	}

	private void initializeModel(String line) {
		String[] split = line.split(" ");
		v = toInt(split[0]);
		e = toInt(split[1]);
		r = toInt(split[2]);
		c = toInt(split[3]);
		x = toInt(split[4]);

		model = new Model(v, e, r, c, x);
	}

	private int toInt(String string) {
		return Integer.valueOf(string);
	}

	private List<String> readFile(String filename) {
		try {
			Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
			return Files.readAllLines(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
