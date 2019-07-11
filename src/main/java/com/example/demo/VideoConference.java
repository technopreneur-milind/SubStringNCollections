package com.example.demo;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

class Result2 {

	/*
	 * Complete the 'solve' function below.
	 *
	 * The function is expected to return a STRING_ARRAY. The function accepts
	 * STRING_ARRAY names as parameter.
	 */

	private static void addUsedSubStrings(Map<String, String> usedStrings, String string) {
		for (int i = 1; i <= string.length(); i++) {
			String subStr = string.substring(0, i);
			usedStrings.put(subStr, "I am available here");
		}
	}

	private static void addUsedMainString(Map<String, Integer> usedMainStrings, String string) {
		usedMainStrings.put(string, getOccurence(usedMainStrings, string));
	}

	private static int getOccurence(Map<String, Integer> stringMap, String string) {
		return stringMap.get(string) == null ? 1 : stringMap.get(string) + 1;
	}

	public static List<String> solve(List<String> names) {
		List<String> result = new ArrayList<String>();
		Map<String, Integer> usedMainStrings = new HashMap<String, Integer>();
		Map<String, String> usedSubStrings = new HashMap<String, String>();
		result.add(names.get(0).substring(0, 1));
		addUsedMainString(usedMainStrings, names.get(0));
		addUsedSubStrings(usedSubStrings, names.get(0));
		for (int i = 1; i < names.size(); i++) {
			String next = names.get(i);
			Integer occurence = usedMainStrings.get(next);
			if (occurence != null && occurence > 0) {
				occurence++;
				result.add(next + " " + occurence);
			} else {
				boolean subStringAdded = false;
				for (int j = 1; j <= next.length(); j++) {
					String nextSubString = next.substring(0, j);
					if (!usedSubStrings.containsKey(nextSubString)) {
						subStringAdded = true;
						result.add(nextSubString);
						break;
					}
				}
				//This String has not been used nor we were successful in finding its subString..so add it directly
				if (!subStringAdded)
					result.add(next);
			}
			addUsedMainString(usedMainStrings, next);
			addUsedSubStrings(usedSubStrings, next);
		}
		return result;
	}

}

public class VideoConference {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter("/Users/milindtechnopreneur/Technopreneur/VideoConference_out.txt"));

		int n = Integer.parseInt(bufferedReader.readLine().trim());

		List<String> names = IntStream.range(0, n).mapToObj(i -> {
			try {
				return bufferedReader.readLine();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}).collect(toList());

		List<String> res = Result2.solve(names);

		bufferedWriter.write(res.stream().collect(joining("\n")) + "\n");

		bufferedReader.close();
		bufferedWriter.close();
	}
}




