package com.shinom.blogging.servicesImplements;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinom.blogging.dto.ContactSupportDto;
import com.shinom.blogging.entities.ContactSupport;
import com.shinom.blogging.repositories.ContactSupportRepository;
import com.shinom.blogging.services.ContactSupportService;

@Service
public class ContactSupportImplements implements ContactSupportService {

	@Autowired
	private ContactSupportRepository contactSupportRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ContactSupportDto saveContactDetails(ContactSupportDto dto) {
		this.contactSupportRepo.save(this.dtoToContactSupport(dto));
		return dto;
	}

	@Override
	public ContactSupport dtoToContactSupport(ContactSupportDto dto) {
		// TODO Auto-generated method stub
		return this.modelMapper.map(dto, ContactSupport.class);
	}

	@Override
	public ContactSupportDto contactSupportToDto(ContactSupport contact) {
		// TODO Auto-generated method stub
		return this.modelMapper.map(contact, ContactSupportDto.class);
	}

}
