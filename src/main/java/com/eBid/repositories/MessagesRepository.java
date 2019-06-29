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
}
