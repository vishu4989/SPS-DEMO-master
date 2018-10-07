package com.capgemini.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.capgemini.validation.FriendManagementValidation;
import com.capgemini.exceptionhandling.ResourceNotFoundException;
import com.capgemini.model.ResponseUser;
import com.capgemini.model.Subscriber;

@Repository
public class FriendMangmtDAO {

	@Autowired
	FriendManagementValidation fmError;

	@Autowired
	JdbcTemplate jdbcTemplate;

	//	public ResponseUser findFriendById(String id) {
	//		 return jdbcTemplate.queryForObject("select * from friendmanagement where id=?", new Object[] {
	//		            id
	//		        },
	//		        new BeanPropertyRowMapper < ResponseUser > (ResponseUser.class));
	//	}
	//	
	//	public int subscribeTargetFriend(String requestor, String target) {
	//		 return jdbcTemplate.update("update friendmanagement " + " set subscription = ? " + " where email = ?",
	//			        new Object[] {
	//			        		target, requestor
	//		 });
	//		 
	//	}


	public boolean addNewFriendConnection(com.capgemini.model.UserRequest userReq) {
		List<String> friends = userReq.getFriends();
		String requestor = friends.get(1);
		String target = friends.get(2);
		boolean isFriendAvailable = false;

		String sql = "SELECT * FROM friendmanagement";

		List<ResponseUser> responseUser  = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(ResponseUser.class));

		for(ResponseUser ru:responseUser) {
			if(ru.getEmail().equals(target)) {
				jdbcTemplate.update("update friendmanagement " + " set friend_list = ? " + " where email = ?",
						new Object[] {
								target, requestor
				});
				isFriendAvailable = true;
				break;
			}
		}

		if(isFriendAvailable) {
			return true;
		}else {
			return false;
		}
	}

	public FriendManagementValidation subscribeTargetFriend(com.capgemini.model.Subscriber subscriber)throws ResourceNotFoundException {
		String requestor = subscriber.getRequestor();
		String target = subscriber.getTarget();

		 
		String query = "SELECT email FROM friendmanagement";

		List<String> emails =jdbcTemplate.queryForList(query,String.class);

		if(emails.contains(target)) {
			String sql = "SELECT subscription FROM friendmanagement WHERE email=?";

			String subscribers = (String) jdbcTemplate.queryForObject(
					sql, new Object[] { requestor }, String.class);
			//	Syso
			System.out.println("subscribers "+subscribers);

			int result;
			if(subscribers.isEmpty()) {
				result = jdbcTemplate.update("update friendmanagement " + " set subscription = ? " + " where email = ?",
						new Object[] {
								target, requestor
				});
			}else {

				String[] subs = subscribers.split(",");
				ArrayList al = new ArrayList(Arrays.asList(subs));
				System.out.println("al "+al);
				if(!al.contains(target)) {
					target= subscribers +", "+ target;
					result = jdbcTemplate.update("update friendmanagement " + " set subscription = ? " + " where email = ?",
							new Object[] {
									target, requestor
					});
				}else {
					fmError.setStatus(false);
					fmError.setErrorDescription("Target already subscribed");
					return fmError;     
				}
			}
			//		String[] subscriberList = subscribers.split(",");






			if(result==1) {
				fmError.setStatus(true);
				fmError.setErrorDescription("Subscribed successfully");
				return fmError;
			}else {
				fmError.setStatus(false);
				fmError.setErrorDescription("");
				return fmError;
			}
		}else {
			fmError.setStatus(false);
			fmError.setErrorDescription("Target Doesn't exist");
			return fmError;
		}
	}


}
