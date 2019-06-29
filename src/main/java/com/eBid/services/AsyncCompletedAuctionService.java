package com.eBid.services;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.eBid.models.Auctions;
import com.eBid.models.Messages;
import com.eBid.repositories.AuctionsRepository;
import com.eBid.repositories.MessagesRepository;

@Service
@Configuration
@EnableAsync
public class AsyncCompletedAuctionService {
    
	@Autowired
	private AuctionsRepository auctionsRepo;
	
	@Autowired
	private MessagesRepository messagesRepo;
	
	@Async
	public void checkDB() {
		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ArrayList <Auctions> completed_auctions = new ArrayList<>();
		completed_auctions=auctionsRepo.findFinishedAuctions(timestamp);
		if(!completed_auctions.isEmpty()) {
			for(Auctions auction:completed_auctions) {
				ArrayList <Messages> checkifalreadyfriends = new ArrayList<>();;
			    checkifalreadyfriends=messagesRepo.isMember(auction.getAuctioneer(), auction.getHighest_bidder());
				if(checkifalreadyfriends.isEmpty()) {
				Messages message=new Messages();
				message.setIs_read(false);
				message.setDeleted(false);
				message.setReceiver(auction.getAuctioneer());
				message.setSender(auction.getHighest_bidder());
				message.setMessage("You became friends with "+auction.getHighest_bidder());
				//System.out.println(message.toString());
				messagesRepo.save(message);
				
				Messages message2=new Messages();
				message2.setIs_read(false);
				message2.setDeleted(false);
				message2.setReceiver(auction.getHighest_bidder());
				message2.setSender(auction.getAuctioneer());
				message2.setMessage("You became friends with "+auction.getAuctioneer());
				//System.out.println(message2.toString());
				messagesRepo.save(message2);
				
				}
				auction.setChecked(true);
				auctionsRepo.save(auction);
			}
		}
	}
}
