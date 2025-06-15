package com.shinom.blogging.controller;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinom.blogging.dto.ContactSupportDto;
import com.shinom.blogging.services.ContactSupportService;

@RestController
@RequestMapping("/blogging/support")
public class ContactController {
	
	@Autowired
	private ContactSupportService supportService;
	
	@PostMapping("/contact")
	public ResponseEntity<ContactSupportDto> create(@RequestBody ContactSupportDto dto) {
		return new ResponseEntity<ContactSupportDto>(this.supportService.saveContactDetails(dto), HttpStatus.ACCEPTED);
	}

}
