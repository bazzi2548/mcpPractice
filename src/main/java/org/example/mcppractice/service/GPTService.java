package org.example.mcppractice.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class GPTService {

	private final OkHttpClient client = new OkHttpClient.Builder()
		.connectTimeout(20, TimeUnit.SECONDS)
		.writeTimeout(20, TimeUnit.SECONDS)
		.readTimeout(60, TimeUnit.SECONDS) // 가장 중요!
		.build();;
	private final ObjectMapper mapper = new ObjectMapper();
	private static final String URL = "https://api.openai.com/v1/responses";

	@Value("${api.key}")
	private String apiKey;


	public String reviewCode(String code) throws Exception {
		Map<String, Object> payload = Map.of(
			"model", "gpt-5-nano",
			"input", "너는 코드리뷰 전문가야. 아래 코드를 리뷰해줘:\n\n" + code
		);

		String json = mapper.writeValueAsString(payload);

		RequestBody body = RequestBody.create(
			json,
			MediaType.get("application/json")
		);

		Request request = new Request.Builder()
			.url("https://api.openai.com/v1/responses")
			.header("Authorization", "Bearer " + apiKey)
			.post(body)
			.build();

		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
