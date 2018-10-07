package com.capgemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.SynthesizedAnnotation;
import org.springframework.stereotype.Service;

import com.capgemini.dao.FriendMangmtDAO;
import com.capgemini.exceptionhandling.ResourceNotFoundException;
import com.capgemini.validation.FriendManagementValidation;

@Service
public class FrientMangmtService {

	@Autowired
	FriendMangmtDAO friendMangmtDAO;
	
	public boolean addNewFriendConnection(com.capgemini.model.UserRequest userReq) {
		friendMangmtDAO.addNewFriendConnection(userReq);
		return true;
	}

	public FriendManagementValidation subscribeTargetFriend(com.capgemini.model.Subscriber subscriber)throws ResourceNotFoundException {
		return friendMangmtDAO.subscribeTargetFriend(subscriber);
		
	}
	
	
	
}
