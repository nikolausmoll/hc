package de.nupoex.hashcode.writer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.nupoex.hashcode.model.Video;

public class DataWriter {

	private String filename;
	private Map<Integer, Collection<Video>> solution;

	public DataWriter(String filename, Map<Integer, Collection<Video>> solution) {
		this.filename = filename;
		this.solution = solution;
	}

	public void write() {
		try {
			Path path = Paths.get(filename);

			java.nio.file.Files.write(path, createSolutionText().getBytes("utf-8"), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String createSolutionText() {
		StringBuilder b = new StringBuilder();
		b.append(solution.size());
		b.append("\n");
		
		List<Integer> caches = new ArrayList<>(solution.keySet());
		Collections.sort(caches);
		for (Integer cache : caches) {
			b.append(cache);
			Collection<Video> videos = solution.get(cache);
			for (Video video : videos) {
				b.append(' ');
				b.append(video.getIndex());
			}
			b.append("\n");
		}
		
		return b.toString();
	}
}
