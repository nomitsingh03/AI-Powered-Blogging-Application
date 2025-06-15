package com.shinom.blogging.ai.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PerspectiveApiService {
	
	private final WebClient webClient;

	@Value("${perspective.api.key}")
	private String apiKey;

	public PerspectiveApiService(WebClient.Builder webClientBuilder) {
	    this.webClient = webClientBuilder.baseUrl("https://commentanalyzer.googleapis.com").build();
	}

	public double getToxicityScore(String commentText) {
	    Map<String, Object> body = Map.of(
	        "comment", Map.of("text", commentText),
	        "languages", List.of("en"),
	        "requestedAttributes", Map.of("TOXICITY", new HashMap<>())
	    );

	    Map<String, Object> response = webClient.post()
	        .uri(uriBuilder -> uriBuilder
	            .path("/v1alpha1/comments:analyze")
	            .queryParam("key", apiKey)
	            .build())
	        .bodyValue(body)
	        .retrieve()
	        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
	        .block();

	    Map<String, Object> attrScores = (Map<String, Object>) response.get("attributeScores");
	    Map<String, Object> toxicity = (Map<String, Object>) attrScores.get("TOXICITY");
	    Map<String, Object> score = (Map<String, Object>) toxicity.get("summaryScore");

	    return (Double) score.get("value");
	}


}
