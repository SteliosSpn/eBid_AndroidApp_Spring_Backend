package com.eBid.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eBid.models.AuctionTags;

public interface AuctionTagsRepository extends JpaRepository<AuctionTags,AuctionTags>{
@Query("SELECT auction_id FROM AuctionTags  WHERE tag=:tag")
public ArrayList<Integer> getauctionsbytag(@Param("tag") String tag);

@Query("SELECT tag FROM AuctionTags WHERE auction_id=:auction_id")
public ArrayList<String> getTagsOfAuction(@Param("auction_id") Integer auction_id);
}
