package com.shinom.blogging.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinom.blogging.ai.service.ChatService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/blogging/chat")
public class AiController {

	private final ChatService chatService;

	@Autowired
	public AiController(ChatService chatService) {
		this.chatService = chatService;
	}
	
	 @GetMapping("/ai/generate")
	    public ResponseEntity<String> generate(@RequestParam(value = "prompt") String message) {
	        return new ResponseEntity<String>(chatService.getResponse(message),HttpStatus.OK);
	    }
	 
	 @GetMapping("/ai/generates")
	    public ResponseEntity<Flux<String>> generateResponses(@RequestParam(value = "prompt") String message) {
	        return ResponseEntity.ok(chatService.getResponses(message));
	    }
	 
		@GetMapping("/ai/summarize")
		public ResponseEntity<String> summarize(@RequestBody String content) {
			return new ResponseEntity<String>(chatService.getSummary(content), HttpStatus.OK);
		}
}
