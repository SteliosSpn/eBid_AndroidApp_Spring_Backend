package com.eBid.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.models.Auctions;
import com.eBid.repositories.AuctionTagsRepository;
import com.eBid.repositories.AuctionsRepository;
@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
    private AuctionsRepository auction_repo;
	@Autowired
	private AuctionTagsRepository auctiontagsrepo;
	@RequestMapping(value = "/searchbytag/{tag}", method = RequestMethod.POST)
	public ArrayList<Auctions> searchauctionsbytag(@PathVariable("tag") String tag){
		ArrayList<Auctions> auctions=new ArrayList<Auctions>();
		ArrayList<Integer> auction_ids=auctiontagsrepo.getauctionsbytag(tag);
		for(Integer auction_id:auction_ids){
			Optional<Auctions> auction=auction_repo.findById(auction_id);
			if(auction.isPresent()){
				auctions.add(auction.get());
			}
		}
		return auctions;
	}
	
	
	@RequestMapping(value = "/searchbytagdescription/{tag}/{name}", method = RequestMethod.POST)
	public ArrayList<Auctions> searchauctionsbytagdescription(@PathVariable("tag") String tag,@PathVariable("name") String name){
		ArrayList<Auctions> auctions=new ArrayList();
		ArrayList<Integer> auction_ids=auctiontagsrepo.getauctionsbytag(tag);
		String description,lower_name;
		for(Integer auction_id:auction_ids){
			Optional<Auctions> auction=auction_repo.findById(auction_id);
			
			
			
			if(auction.isPresent()){
				description=(auction.get().getName()).toLowerCase();
				lower_name=name.toLowerCase();
				if(description.contains(lower_name)){
					auctions.add(auction.get());
				}
				
				
			}
		}
		return auctions;
	}
	
	
	
	
	
}
