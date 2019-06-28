package com.eBid.repositories;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eBid.models.Bids;


public interface BidsRepository extends JpaRepository<Bids,Bids> {
	@Query("SELECT count(*) FROM Bids WHERE bidder_id=:bidder_id")
	   public Integer findbiduser(@Param("bidder_id") String bidder_id);
	
	@Query("SELECT count(*) FROM Bids WHERE bidder_id=:bidder_id AND auction_id=:auction_id AND bid>:bid" )
	   public Integer findbetterbids(@Param("bidder_id") String bidder_id,@Param("auction_id") Integer auction_id,@Param("bid") Double bid);

	@Query("SELECT  bid from Bids where auction_id=:auction_id")
	public ArrayList<Double> getaunctionbid(@Param("auction_id") Integer auction_id);
}
