package com.eBid.repositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eBid.models.Auctions;

public interface AuctionsRepository extends JpaRepository<Auctions,Integer> {

	@Query("select max(auction_id) from Auctions")
    Integer lastAuctionId();
	
	 @Query("SELECT count(*) FROM Auctions WHERE auctioneer=:auctioneer")
	   public Integer findauctionuser(@Param("auctioneer") String auctioneer);
	 
	 @Query("SELECT auction_id FROM Auctions WHERE auctioneer=:auctioneer")
	   public ArrayList<Integer> findMyAuctions(@Param("auctioneer") String auctioneer);
	 
	 @Query("SELECT auction_id  FROM Auctions WHERE auctioneer=:auctioneer AND auction_id=:auction_id")
	  public Integer check_aunction_ownership(@Param("auctioneer") String auctioneer,@Param("auction_id") Integer auction_id );
	 
	   @Query("SELECT auction_id FROM Auctions WHERE ends>now() or starts is NULL")
	   public  List<Integer> findactiveauctions();
	   
	   @Query("SELECT a FROM Auctions a WHERE ends<:time AND checked=FALSE")
	   public ArrayList<Auctions> findFinishedAuctions(@Param("time") Timestamp time);
	   
	   @Query("SELECT DISTINCT a FROM Auctions a WHERE name LIKE %:name%")
	   public ArrayList<Auctions> findAuctionsByName(@Param("name") String name);
	 
}
