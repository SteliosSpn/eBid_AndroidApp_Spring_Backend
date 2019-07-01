package com.eBid.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.eBid.models.Users;
import com.eBid.repositories.MessagesRepository;
import com.eBid.repositories.UserRepository;



@Service
@Configuration
@EnableAsync
public class AsyncMessageNotificationService {
	@Autowired
	private MessagesRepository messagesRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Async
	public void checkInbox() {
		List<Users> users=userRepo.findAll();
		String id;
		Integer count;
		for(Users user:users){
			id=user.getUser_id();
			count=messagesRepo.findunreadmessages(id);
			user.setInboxcount(count);
			userRepo.save(user);
		}
	}
	
}
