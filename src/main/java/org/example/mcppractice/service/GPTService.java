package org.example.mcppractice.service;

import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class GPTService {

	private final OkHttpClient client = new OkHttpClient();
	private static final String URL = "https://api.openai.com/v1/responses";
	private final String apiKey = System.getenv("API_KEY");

	public String reviewCode(String code) throws Exception {

		String prompt = """
            너는 코드리뷰 전문가야.
            코드리뷰 후 문제점/개선점을 알려줘.
            
            코드:
            """ + code;

		String json = """
        {
          "model": "gpt-5.1-mini",
          "input": "%s"
        }
        """.formatted(prompt.replace("\"","\\\""));

		RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
		Request request = new Request.Builder()
			.url(URL)
			.header("Authorization", "Bearer " + apiKey)
			.post(body)
			.build();

		Response response = client.newCall(request).execute();

		return response.body().string();
	}

}
