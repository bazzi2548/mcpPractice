package org.example.mcppractice.controller;

import java.util.Map;

import org.example.mcppractice.service.GPTService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class McpController {

	private final GPTService gptService;

	@PostMapping("/review")
	public Map<String, Object> review(@RequestBody Map<String, String> body) throws Exception {
		String code = body.get("code");
		String result = gptService.reviewCode(code);
		return Map.of("review", result);
	}

}



