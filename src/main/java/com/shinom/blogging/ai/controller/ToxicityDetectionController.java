package com.shinom.blogging.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinom.blogging.ai.service.PerspectiveApiService;
import com.shinom.blogging.dto.PostDto;

@RestController
public class ToxicityDetectionController {
	
	@Autowired
	private PerspectiveApiService perspectiveApiService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public Boolean detectToxicity(String text) {
		Double toxicityScore = perspectiveApiService.getToxicityScore(text);
		if (toxicityScore > 0.4) {
			return true; // Toxic
		}
		return false;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/detect")
	private void test() {
		String text = """
		        Tum logon ki soch dekh ke hasi aati hai. Har kisi ko lagta hai woh hi sabse intelligent hai.
		        Bhai tu chutiya hai kya? Teri baat sun ke toh dimaag ka dahi ho gaya.
		        Bevakoof logon ka toh bharmaar hai yahan. Gaali dene ke alawa kuch aata bhi hai kya?
		        Log itne gande hain, kuch toh tameez seekho. Saale harami, pehle khud sudhro fir dusron ko gyaan do.
		        """;
		detectToxicity(text);
	}


	
	
//	public Boolean detectContentToxicity(String data) throws Exception {
//		PostDto dto = this.objectMapper.readValue(data,PostDto.class);
//		Double toxicityScore = perspectiveApiService.getToxicityScore(dto.getContent());
//		return toxicityScore > 0.4; // Toxic;
//	}

}
