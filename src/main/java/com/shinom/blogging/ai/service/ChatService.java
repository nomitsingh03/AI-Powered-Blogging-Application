package com.shinom.blogging.ai.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

public final ChatModel chatModel;
	
	public ChatService(ChatModel model) {
		this.chatModel=model;
	}
	
	public String getResponse(String prompt) {
		return chatModel.call(prompt);
	}
	
	
	public Flux<String> getResponses(String prompt) {
		return chatModel.stream(prompt);
	}
	
	public String getSummary(String content) {
		return chatModel.call("Summarize this content : " + content);
	}
}
