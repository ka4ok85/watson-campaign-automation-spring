package com.github.ka4ok85.wca.pod;

import java.util.Arrays;
import java.util.List;

public class Pod {
	private static String ACCESS_URL = "https://apiPOD.silverpop.com/oauth/token";
	private static List<Integer> podList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

	public static String getOAuthEndpoint(int podNumber) {
		podList.contains(0);
		if (false == isValidPodNumber(podNumber)) {
			throw new RuntimeException("Unsupported Pod Number");
		}

		return ACCESS_URL.replaceAll("POD", String.valueOf(podNumber));
	}

	private static boolean isValidPodNumber(int podNumber) {
		return podList.contains(podNumber);
	}
}
