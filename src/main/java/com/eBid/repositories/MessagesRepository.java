package com.eBid.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eBid.models.Auctions;
import com.eBid.models.Messages;

public interface MessagesRepository extends JpaRepository<Messages,Integer>{

	@Query("SELECT m FROM  Messages m WHERE sender=:user1 AND receiver=:user2 OR sender=:user2 AND receiver=:user1")
	   public ArrayList<Messages> isMember(@Param("user1") String user1,@Param("user2") String user2);
	//changes
	@Query("SELECT message_id from Messages WHERE receiver=:receiver and deleted=false ORDER BY message_id ASC")
	public ArrayList<Integer> getinbox(@Param("receiver") String receiver);
	@Query("SELECT message_id from Messages WHERE sender=:sender ORDER BY message_id ASC")
	public ArrayList<Integer> getoutbox(@Param("sender") String sender);
	@Query("SELECT message_id from Messages WHERE receiver=:receiver AND message_id=:message_id")
	public Integer findmessage(@Param("receiver") String receiver,@Param("message_id") Integer message_id);
	@Query("SELECT count(*) from Messages WHERE receiver=:receiver")
	public Integer countmessage(@Param("receiver") String receiver);
	@Query("SELECT count(*) from Messages WHERE receiver=:receiver AND sender=:sender")
	public Integer countusersmessage(@Param("sender") String sender,@Param("receiver") String receiver);
	@Query("SELECT message_id from Messages WHERE receiver=:receiver AND message_id=:message_id AND sender=:sender") 
	public Integer existexchangesmessage(@Param("receiver") String receiver,@Param("message_id") Integer message_id,@Param("sender") String sender);
	@Query("SELECT count(*) FROM Messages where receiver=:receiver AND  is_read=false")
	public Integer findunreadmessages(@Param("receiver") String receiver);
	
	   
}
