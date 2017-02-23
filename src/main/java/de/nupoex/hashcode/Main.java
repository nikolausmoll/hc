package de.nupoex.hashcode;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.nupoex.hashcode.model.Model;
import de.nupoex.hashcode.model.Video;
import de.nupoex.hashcode.reader.DataReader;
import de.nupoex.hashcode.writer.DataWriter;

public class Main {

	public static void main(String[] args)
	{
		Model model = new DataReader("sample.txt").getModel();
		
		Map<Integer, Collection<Video>> solution = new HashMap<>();
		solution.put(0, Arrays.asList(model.getVideo(2)));
		solution.put(1, Arrays.asList(model.getVideo(3), model.getVideo(1)));
		solution.put(2, Arrays.asList(model.getVideo(0), model.getVideo(1)));
		new DataWriter("solution1.txt", solution).write();
	}
	
}
