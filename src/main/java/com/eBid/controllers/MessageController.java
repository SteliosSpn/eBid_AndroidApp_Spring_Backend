package com.eBid.controllers;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.models.Auctions;
import com.eBid.models.Messages;
import com.eBid.models.Users;
import com.eBid.repositories.AuctionsRepository;
import com.eBid.repositories.MessagesRepository;
import com.eBid.repositories.UserRepository;
@RestController
@RequestMapping("/chat")
public class MessageController {
	@Autowired
    private MessagesRepository message_repo;
	
	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping(value = "/showincomes/{receiver}", method = RequestMethod.GET)
	public ArrayList<Messages> showincomes(@PathVariable("receiver") String receiver){
		ArrayList<Integer> message_ids=message_repo.getinbox(receiver);
		ArrayList<Messages> messages=new ArrayList<Messages>();
		boolean delete;
		for(Integer message_id:message_ids){
			Optional<Messages> message=message_repo.findById(message_id);
			if(message.isPresent()){
				delete=message.get().isDeleted();
				if(delete==false){
				messages.add(message.get());}
			}
		}
		return messages;
	}
	
	@RequestMapping(value = "/showoutcomes/{sender}", method = RequestMethod.GET)
	public ArrayList<Messages> showoutcomes(@PathVariable("sender") String sender){
		ArrayList<Integer> message_ids=message_repo.getoutbox(sender);
		ArrayList<Messages> messages=new ArrayList<Messages>();
		for(Integer message_id:message_ids){
			Optional<Messages> message=message_repo.findById(message_id);
			if(message.isPresent()){
				messages.add(message.get());
			}
		}
		return messages;
	}
	@RequestMapping(value = "/deletemsg/{receiver}/{message_id}", method = RequestMethod.GET)
	public String deletemessage(@PathVariable("receiver") String receiver,@PathVariable("message_id") Integer message_id){
			Integer message_idx=message_repo.findmessage(receiver, message_id);
			Integer inbox_count;
			Optional<Messages> message=message_repo.findById(message_idx);
			if(!message.isPresent()){
				return "Message not found";
			}
			Integer count =message_repo.countmessage(receiver);
			if(count!=1){
				message.get().setDeleted(true);
				message.get().setIs_read(true);
				message_repo.save(message.get());
				//message_repo.deleteById(message_idx);
				Optional<Users> user=userRepo.findById(receiver);
				if(user.isPresent()){
					inbox_count=user.get().getInboxcount();
					inbox_count=inbox_count-1;
					user.get().setInboxcount(inbox_count);
					userRepo.save(user.get());
				}
				
				
				
				return "Message deleted";
			}
			
			return"Delete of this message terminate communication between users";
			
	
	
	}
	@RequestMapping(value = "/readmsg/{sender}/{receiver}/{message_id}", method = RequestMethod.GET)
	public String readmessage(@PathVariable("sender") String sender,@PathVariable("receiver") String receiver,@PathVariable("message_id") Integer message_id){
		Integer count=message_repo.existexchangesmessage(receiver, message_id, sender);
		Integer inbox_count;
		if(count==message_id){
			Optional<Messages> message=message_repo.findById(message_id);
			if(message.isPresent()){
				boolean read=message.get().isIs_read();
				if(read==false){
					message.get().setIs_read(true);
					message_repo.save(message.get());
					Optional<Users> user=userRepo.findById(receiver);
					if(user.isPresent()){
						inbox_count=user.get().getInboxcount();
						inbox_count=inbox_count-1;
						user.get().setInboxcount(inbox_count);
					}
					
					return "Message is read";
				}
				return "Message have been read";
			}
		}
		return"Message not exist";
	}
	
	@RequestMapping(value = "/sendmsg/{sender}/{receiver}/{message}", method = RequestMethod.GET)
	public String sendmessage(@PathVariable("sender") String sender,@PathVariable("receiver") String receiver,@PathVariable("message") String message){
		Integer count =message_repo.countusersmessage(sender, receiver);
		Integer inbox_count;
		if(count>=1){
			Messages new_message=new Messages();
			new_message.setSender(sender);
			new_message.setReceiver(receiver);
			new_message.setMessage(message);
			new_message.setDeleted(false);
			new_message.setIs_read(false);
			message_repo.save(new_message);
			Optional<Users> user=userRepo.findById(receiver);
			if(user.isPresent()){
				inbox_count=user.get().getInboxcount();
				inbox_count=inbox_count+1;
				user.get().setInboxcount(inbox_count);
			}
			
			
			
			
			return"Message is sent";
		}
		return"Unavailable communication";
	}
	
}