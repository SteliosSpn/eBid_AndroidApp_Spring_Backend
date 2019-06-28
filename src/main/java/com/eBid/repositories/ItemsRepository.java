package com.eBid.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eBid.models.Items;

public interface ItemsRepository extends JpaRepository<Items,Integer> {
	
	@Query("select max(item_id) from Items")
    Integer lastItemId();
	
	@Query("SELECT item_id FROM Items WHERE auction_id=:auction_id")
	   public ArrayList<Integer> findItemsOfAuction(@Param("auction_id") Integer auction_id);
}
