package de.nupoex.hashcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.nupoex.hashcode.analyzer.Analyzer;
import de.nupoex.hashcode.model.Cache;
import de.nupoex.hashcode.model.Model;
import de.nupoex.hashcode.model.Potential;
import de.nupoex.hashcode.model.Request;
import de.nupoex.hashcode.model.Video;
import de.nupoex.hashcode.reader.DataReader;
import de.nupoex.hashcode.writer.DataWriter;

public class Main {

	public static void main(String[] args) {
        //Get the jvm heap size.
        long heapSize = Runtime.getRuntime().totalMemory();

        //Print the jvm heap size.
        System.out.println("Heap Size = " + heapSize);

		calculateSolution("sample.txt", "sample-solution.txt");
		//calculateSolution("me_at_the_zoo.in", "me_at_the_zoo.out");
		calculateSolution("kittens.in", "kittens.out");
		// calculateSolution("videos_worth_spreading.in", "videos_worth_spreading.out");
		//calculateSolution("trending_today.in", "trending_today.out");
	}

	private static void calculateSolution(String input, String output) {
		Model model = new DataReader(input).getModel();

		List<Potential> rankings = new ArrayList<>();
		for (Request r : model.getRequests()) {
			rankings.addAll(r.calculateRankings());
		}

		int bestScore = 0;
		Map<Integer, Collection<Integer>> bestSolution = null;

		Collections.sort(rankings, new Potential.PriorityComparator());
		Map<Integer, Collection<Integer>> sortedSolution = calculateSolution(model, rankings, true);
		int sortedScore = Analyzer.computeScore(model, sortedSolution);
		Logger.log("Sorted Score: " + sortedScore);

//		for (int i = 0; i < 0; i++) {
//			Collections.shuffle(rankings);
//			Map<Integer, Collection<Integer>> solution = calculateSolution(model, rankings, false);
//
//			int score = Analyzer.computeScore(model, solution);
//			if (score > bestScore) {
//				bestScore = score;
//				bestSolution = solution;
//			}
//		}

		Logger.log("Best-Score: " + Math.max(bestScore, sortedScore));

//		if (bestScore > sortedScore) {
//			Logger.log("Best Solution is not sorted");
//		} else {
			bestSolution = sortedSolution;
//		}
		new DataWriter(output, bestSolution).write();
	}

	private static Map<Integer, Collection<Integer>> calculateSolution(Model model, List<Potential> rankings,
			boolean debug) {
		Map<Integer, Collection<Integer>> solution = new HashMap<>();

		Set<Integer> handledRequests = new HashSet<>();
		Iterator<Potential> iterator = rankings.iterator();
		while (iterator.hasNext()) {
			Potential potential = iterator.next();
			int cacheIndex = potential.getCacheIndex();
			double poriority = potential.getPriority();
			long potentialValue = potential.getPotential();
			Video video = potential.getVideo();
			if (handledRequests.contains(potential.getRequest().getIndex())) {
				if (debug) {
					Logger.log("Skip handled Request: Cache " + cacheIndex + ": " + poriority + " (" + potentialValue
							+ ") for Video " + video.getIndex() + " on " + potential.getEndpointIndex());
				}
				continue;
			}

			Cache cache = model.getCache(cacheIndex);
			if (cache.contains(video)) {
				continue;
			}

			boolean fitsInCache = cache.hasEnoughSpace(video.getSizeInMb());
			if (!fitsInCache) {
				if (debug) {
					Logger.log(
							"Skip unsatisfyable Request: Cache " + cacheIndex + ": " + poriority + " (" + potentialValue
									+ ") for Video " + video.getIndex() + " on " + potential.getEndpointIndex());
				}
				continue;
			}

			if (debug) {
				Logger.log("Caching Request: Cache " + cacheIndex + ": " + poriority + " (" + potentialValue
						+ ") for Video " + video.getIndex() + " on " + potential.getEndpointIndex());
			}

			handledRequests.add(potential.getRequest().getIndex());
			cache.addVideo(video);

			Collection<Integer> videos = solution.get(cacheIndex);
			if (videos == null) {
				videos = new ArrayList<>();
				solution.put(cacheIndex, videos);
			}
			videos.add(video.getIndex());
		}
		return solution;
	}

}
