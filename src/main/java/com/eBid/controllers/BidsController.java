package com.eBid.controllers;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.models.Auctions;
import com.eBid.models.Bids;
import com.eBid.repositories.AuctionsRepository;
import com.eBid.repositories.BidsRepository;




@RestController
@RequestMapping("/bids")
public class BidsController {
	//? transaction
	@Autowired
   private BidsRepository bidRepo;
	@Autowired
    private AuctionsRepository auctionRepo;
	@RequestMapping(value = "/make_bid/{auction_id}/{bidder_id}/{bid}", method = RequestMethod.GET)
    public String createAuction(@PathVariable("auction_id") Integer auction_id,@PathVariable("bidder_id") String bidder_id,
            @PathVariable("bid") Double bid) {
		//hold biggest
		Optional<Auctions> auction=auctionRepo.findById(auction_id);
		if((!auction.isPresent())){
			return "Auction is not found";
		}
		//date?
		Timestamp start_date=auction.get().getStarts();
		//start_date.setHours(start_date.getHours()-3);
		boolean check=(start_date).after(new Timestamp(System.currentTimeMillis()-3));
		System.out.println(new Timestamp(System.currentTimeMillis()));
		System.out.println(check);	
		System.out.println(start_date);	
				 
		
		if(check==true){
			return"This aunction will not start";
		}
		Timestamp end_date=auction.get().getEnds();
		//end_date.setHours(end_date.getHours()-3);
		System.out.println(end_date);
		 check=end_date.before(start_date);
		 System.out.println(check);
		if (check==true){
			return"Auction has been  completed";
		}
		
		
		String auctioneer=auction.get().getAuctioneer();
		if(auctioneer.equals(bidder_id)){
			return "Auctioneer cannot make bid in your auction";
		}
		Integer count=bidRepo.findbetterbids(bidder_id,auction_id,bid);
		if(count>0){
			return "You have already done better offers";
		}
		
		Double start_bid=auction.get().getStart_bid();
		Double current_bid=auction.get().getCurrent_bid();
		if(bid<start_bid || bid<=current_bid){
			return "Your bid is not acceptable";
		}
		
		Bids  new_bid=new Bids();
		new_bid.setAuction_id(auction_id);
		new_bid.setBid(bid);
		new_bid.setBidder_id(bidder_id);
		bidRepo.save(new_bid);
		auction.get().setCurrent_bid(bid);
		auction.get().setHighest_bidder(bidder_id);	//change
		auctionRepo.save(auction.get());
		return "Auction bid up";
		

	}
}