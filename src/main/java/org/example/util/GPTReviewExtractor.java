package org.example.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GPTReviewExtractor {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static String extractReview(String rawJson) {
		try {
			JsonNode root = mapper.readTree(rawJson);

			// output 배열 접근
			JsonNode outputArray = root.path("output");
			if (!outputArray.isArray()) return "";

			StringBuilder review = new StringBuilder();

			for (JsonNode outputItem : outputArray) {
				JsonNode contentArray = outputItem.path("content");
				if (!contentArray.isArray()) continue;

				for (JsonNode contentItem : contentArray) {
					if ("output_text".equals(contentItem.path("type").asText())) {
						String text = contentItem.path("text").asText();
						review.append(text).append("\n");
					}
				}
			}

			return review.toString().trim();
		} catch (Exception e) {
			throw new RuntimeException("Failed to extract review content", e);
		}
	}
}
