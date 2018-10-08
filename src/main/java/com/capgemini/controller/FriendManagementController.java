package com.capgemini.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptionhandling.ResourceNotFoundException;
import com.capgemini.model.BaseResponse;
import com.capgemini.service.FrientMangmtService;
import com.capgemini.validation.FriendManagementValidation;

/**
 * @author vishwman
 *
 */
@RestController
@RequestMapping(value = "/test")
public class FriendManagementController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final String sharedKey = "SHARED_KEY";
	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";
	private static final int CODE_SUCCESS = 100;
	private static final int AUTH_FAILURE = 102;

	@Autowired
	public FrientMangmtService frndMngtServc;

	@Autowired
	FriendManagementValidation fmError;


	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public BaseResponse newFriendConnection(@RequestBody com.capgemini.model.UserRequest userReq) {
		//public ResponseEntity<BaseResponse> newFriendConnection(@RequestBody com.capgemini.model.UserRequest userReq) {	

		BaseResponse response = new BaseResponse();
		List <String > emails = (List<String>) userReq.getFriends();
		//System.out.println("-----Saving user." +userReq.getFriends().get(1));
		//System.out.println("Saving user." +userReq.getFriends().get(2)());
		//LOG.info("Saving user." +userReq.getEmailFirst());
		//LOG.info("Saving user." +userReq.getEmailSecond());


		boolean isNewfrndMangmReqSuccess = frndMngtServc.addNewFriendConnection(userReq);

		if(true){
			response.setStatus(SUCCESS_STATUS);
			//response.setCode(CODE_SUCCESS);
		} else {
			response.setStatus(ERROR_STATUS);
			//response.setCode(AUTH_FAILURE);
		}
		return  response;


	}

	/**
	 * @param subscriber
	 * @param result
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/subscribe", method = RequestMethod.PUT)
	public ResponseEntity<FriendManagementValidation> subscribeFriend(@Valid @RequestBody com.capgemini.model.Subscriber subscriber, BindingResult result)throws ResourceNotFoundException {

		//Validation
		if(result.hasErrors()) {
			return handleValidation(result);
		}

		ResponseEntity<FriendManagementValidation> responseEntity = null;

		try {
			FriendManagementValidation fmv =frndMngtServc.subscribeTargetFriend(subscriber);
			if(fmv.getStatus().equalsIgnoreCase("success")) {
				responseEntity = new ResponseEntity<FriendManagementValidation>(fmv, HttpStatus.OK);
			}else {
				responseEntity = new ResponseEntity<FriendManagementValidation>(fmv, HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {

		}

		return responseEntity;

	}


	/**
	 * This method is used for client validation
	 * @param result
	 * @return
	 */
	private ResponseEntity<FriendManagementValidation> handleValidation(BindingResult result) {
		fmError.setStatus("Failed");
		if(result.getFieldError("requestor") != null && result.getFieldError("target") != null) {
			fmError.setErrorDescription(result.getFieldError("requestor").getDefaultMessage()+" "+result.getFieldError("target").getDefaultMessage());
		}else if(result.getFieldError("target") != null) {
			fmError.setErrorDescription(result.getFieldError("target").getDefaultMessage());
		}else{
			fmError.setErrorDescription(result.getFieldError("requestor").getDefaultMessage());

		}
		return new ResponseEntity<FriendManagementValidation>(fmError, HttpStatus.BAD_REQUEST);

	}

}
