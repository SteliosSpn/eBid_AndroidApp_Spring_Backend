package com.eBid.controllers;

import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.models.Auctions;
import com.eBid.models.Items;
import com.eBid.models.Log;
import com.eBid.models.MyAuctions;
import com.eBid.repositories.AuctionTagsRepository;
import com.eBid.repositories.AuctionsRepository;
import com.eBid.repositories.ItemPicsRepository;
import com.eBid.repositories.ItemTagsRepository;
import com.eBid.repositories.ItemsRepository;
import com.eBid.repositories.LogRepository;
@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
    private AuctionsRepository auction_repo;
	@Autowired
	private AuctionTagsRepository auctiontagsrepo;
	
	@Autowired
	private LogRepository logRepo;
	
	@Autowired
	private ItemsRepository itemsRepo;
	
	@Autowired
	private ItemPicsRepository picsRepo;
	@Autowired
	private ItemTagsRepository itemTagsRepo;
	

	
	@RequestMapping(value = "/searchauctions/{word}", method = RequestMethod.GET)
	public ArrayList<MyAuctions> search(@PathVariable("word") String word){
		ArrayList<Auctions> auctions=new ArrayList<Auctions>();
		ArrayList <Integer> auctions_ids=auctiontagsrepo.getauctionsbytag(word);

		for(Integer auction:auctions_ids) {
			Optional <Auctions> opt=auction_repo.findById(auction);
			if(opt.isPresent()) {
				auctions.add(opt.get());
			}
		}
		
		ArrayList<Auctions> auctions1=new ArrayList<Auctions>();
		auctions1=auction_repo.findAuctionsByName(word);
		auctions.addAll(auctions1);
		ArrayList <Integer> auctions_ids1=new ArrayList<Integer>();
		for(Auctions auction:auctions) {
			auctions_ids1.add(auction.getAuction_id());
		}
		
		ArrayList <Integer> unique_auctions_ids=SearchController.removeDuplicates(auctions_ids1);
		auctions=new ArrayList<Auctions>();
		for(Integer auctionid:unique_auctions_ids) {
			Optional <Auctions> opt=auction_repo.findById(auctionid);
			if(opt.isPresent()) {
				auctions.add(opt.get());
			}
		}
		ArrayList<MyAuctions> myauctions=new ArrayList<>();
		for(Auctions auction:auctions) {
            MyAuctions oneAuction=new MyAuctions();
            oneAuction.setAuction_id(auction.getAuction_id());
            oneAuction.setName(auction.getName());
            oneAuction.setStarts(auction.getStarts());
            oneAuction.setEnds(auction.getEnds());
            oneAuction.setAuctioneer(auction.getAuctioneer());
            oneAuction.setStart_bid(auction.getStart_bid());
            oneAuction.setCurrent_bid(auction.getCurrent_bid());
            oneAuction.setHighest_bidder(auction.getHighest_bidder());
			ArrayList <Items> items = new ArrayList<>();
			ArrayList <Integer> items_id = itemsRepo.findItemsOfAuction(auction.getAuction_id());
			for(Integer item:items_id) {
				Optional <Items> opt = itemsRepo.findById(item);
				if(opt.isPresent())items.add(opt.get());
			}
			for(Items item:items) {
				List<byte[]> pictures=picsRepo.findByItemId(item.getItem_id());
				item.setPictures_str(pictures);
				item.setPictures(picsRepo.findByItemId(item.getItem_id()));
				item.setTags(itemTagsRepo.getTagsOfItem(item.getItem_id()));
			}
			oneAuction.setTags(auctiontagsrepo.getTagsOfAuction(auction.getAuction_id()));
			oneAuction.setItems(items);
			myauctions.add(oneAuction);
		}
		return myauctions;
	}
	
	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) 
    { 
  
        // Create a new ArrayList 
        ArrayList<T> newList = new ArrayList<T>(); 
  
        // Traverse through the first list 
        for (T element : list) { 
  
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) { 
  
                newList.add(element); 
            } 
        } 
  
        // return the new list 
        return newList; 
    } 
	
	
	@RequestMapping(value = "/log",headers = {
    "content-type=application/json" },consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Log addLog(@RequestBody Log log) {
		ArrayList<Integer> loglist = new ArrayList<>();
		loglist=logRepo.findLog(log.getUser_id(), log.getAuction_id());
		if(!loglist.isEmpty()) {
			for(Integer visit_count:loglist) {
				log.setVisit_count(visit_count+1);
				logRepo.customdelete(log.getUser_id(),log.getAuction_id());
				logRepo.save(log);
			}
			
		}
		else {
			log.setVisit_count(1);
			logRepo.save(log);
		}
      return log;
	}

}