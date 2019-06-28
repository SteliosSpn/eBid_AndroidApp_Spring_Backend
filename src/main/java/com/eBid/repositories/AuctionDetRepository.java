package com.eBid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eBid.models.AuctionDetails;

public interface AuctionDetRepository extends JpaRepository<AuctionDetails,AuctionDetails> {

}
