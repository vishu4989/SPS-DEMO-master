package com.capgemini.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.capgemini.validation.FriendManagementValidation;
import com.capgemini.exceptionhandling.ResourceNotFoundException;
import com.capgemini.model.BaseResponse;
import com.capgemini.model.UserRequest;
import com.capgemini.service.FrientMangmtService;

@RestController
@RequestMapping(value = "/test")
public class FriendManagementController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final String sharedKey = "SHARED_KEY";
	 private static final String SUCCESS_STATUS = "success";
	 private static final String ERROR_STATUS = "error";
	 private static final int CODE_SUCCESS = 100;
	 //private static final int CODE_SUCCESS = true;
	 //private static final String SUCCESS_MESSAGE = "success:";
	 private static final int AUTH_FAILURE = 102;
	
	 @Autowired
	 public FrientMangmtService frndMngtServc;
	 
	// @Autowired
	 //private BaseResponse response;
	//http://localhost:8102/user/create
		@RequestMapping(value = "/create", method = RequestMethod.POST)
		public BaseResponse newFriendConnection(@RequestBody com.capgemini.model.UserRequest userReq) {
		//public ResponseEntity<BaseResponse> newFriendConnection(@RequestBody com.capgemini.model.UserRequest userReq) {	
			
		BaseResponse response = new BaseResponse();
		List <String > emails = (List<String>) userReq.getFriends();
		//System.out.println("Saving user." +emails.get(1));
		//System.out.println("Saving user." +emails.get(2));
			System.out.println("-----Saving user." +userReq.getFriends().get(1));
			//System.out.println("Saving user." +userReq.getFriends().get(2)());
			//LOG.info("Saving user." +userReq.getEmailFirst());
			//LOG.info("Saving user." +userReq.getEmailSecond());
			
			
			boolean isNewfrndMangmReqSuccess = frndMngtServc.addNewFriendConnection(userReq);
			
			/*{
				"status": "success",
				"code": 100
				}*/
			/*{
				"success": "true"
				
				}*/
			
			
			if(true){
			response.setStatus(SUCCESS_STATUS);
			   //response.setCode(CODE_SUCCESS);
			  } else {
			   response.setStatus(ERROR_STATUS);
			   //response.setCode(AUTH_FAILURE);
			  }
			  return  response;
					  
					  //return new ResponseEntity<>(response, "success");
						 /*HttpHeaders headers = new HttpHeaders();
				        headers.add("Responded", "MyController");
				        
				        return ResponseEntity.accepted().headers(headers).body(c);;*/
			
		}
		
		@RequestMapping(value="/subscribe", method = RequestMethod.PUT)
		public FriendManagementValidation subscribeFriend(@Valid @RequestBody com.capgemini.model.Subscriber subscriber)throws ResourceNotFoundException {
			try {
			   return frndMngtServc.subscribeTargetFriend(subscriber);
			}catch(ResourceNotFoundException rnfe) {
				throw new ResourceNotFoundException("Resourece not found");
			}
		}
	
}
