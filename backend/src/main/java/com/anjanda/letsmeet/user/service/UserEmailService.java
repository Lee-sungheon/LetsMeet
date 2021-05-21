package com.anjanda.letsmeet.user.service;

import com.anjanda.letsmeet.repository.dto.Emailconfirm;

public interface UserEmailService {

	void sendSimpleMessage(String to) throws Exception;

	Emailconfirm selectEmailconfirm(Emailconfirm emailconfirm);
	

}