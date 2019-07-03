package com.eBid.repositories;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eBid.models.Log;

public interface LogRepository extends JpaRepository<Log,Log> {

	 @Query("SELECT l FROM Log l WHERE user_id=:user_id")
	   public ArrayList<Log> findVisited(@Param("user_id") String user_id);
	 
	 @Query("SELECT visits_count FROM Log WHERE user_id=:user_id AND auction_id=:auction_id")
	   public ArrayList<Integer> findLog(@Param("user_id") String user_id,@Param("auction_id") Integer auction_id);
	 
	 @Modifying
	 @Query("DELETE FROM Log WHERE user_id=:user_id AND auction_id=:auction_id")
	 @Transactional
	 void customdelete(@Param("user_id") String user_id,@Param("auction_id") Integer auction_id);
}