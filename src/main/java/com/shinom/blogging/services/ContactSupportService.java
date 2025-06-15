package com.shinom.blogging.services;

import com.shinom.blogging.dto.ContactSupportDto;
import com.shinom.blogging.entities.ContactSupport;

public interface ContactSupportService {
	
	ContactSupportDto saveContactDetails(ContactSupportDto dto);
	
	ContactSupport dtoToContactSupport(ContactSupportDto dto);
	
	ContactSupportDto contactSupportToDto(ContactSupport contact);

}
