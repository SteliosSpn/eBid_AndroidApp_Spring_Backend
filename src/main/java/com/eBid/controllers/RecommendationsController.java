package com.eBid.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.models.Auctions;
import com.eBid.models.ItemPics;
import com.eBid.models.Items;
import com.eBid.models.Log;
import com.eBid.models.MyAuctions;
import com.eBid.repositories.AuctionTagsRepository;
import com.eBid.repositories.AuctionsRepository;
import com.eBid.repositories.BidsRepository;
import com.eBid.repositories.ItemPicsRepository;
import com.eBid.repositories.ItemTagsRepository;
import com.eBid.repositories.ItemsRepository;
import com.eBid.repositories.LogRepository;

@RestController
@RequestMapping("/rec")
public class RecommendationsController {
	
	@Autowired
	private BidsRepository bidsRepo;
	
	@Autowired
	private ItemsRepository itemsRepo;
	
	@Autowired
	private AuctionsRepository auctionRepo;
	
	@Autowired
	private AuctionTagsRepository auctionTagsRepo;
	
	@Autowired
	private ItemTagsRepository itemTagsRepo;
	
	@Autowired
	private LogRepository logRepo;
	
	@Autowired
	private ItemPicsRepository picsRepo;

	@RequestMapping(value = "/get_rec/{user_id}", method = RequestMethod.GET)
		public List<Items> getItemRec(@PathVariable("user_id") String user_id){
		List<Items> items = new ArrayList<>();
		List<Items> sortedFiveItemsList = new ArrayList<>();
		ArrayList <Integer> my_auction_ids=new ArrayList<>();
		my_auction_ids=bidsRepo.getAuctionsOfBidder(user_id);
		HashMap<String,Integer> tagsmap=new HashMap<String,Integer>();
		if(!my_auction_ids.isEmpty()) {
			for(Integer auction:my_auction_ids) {
			
				ArrayList<String> auctiontags=new ArrayList<>();
				auctiontags=auctionTagsRepo.getTagsOfAuction(auction);
				for(String tag:auctiontags) {
				
					
					if(tagsmap.get(tag)==null)tagsmap.put(tag, 1);
					else {
						int count=tagsmap.get(tag);
						tagsmap.put(tag, count+1);
					}
				}
			}
			items=itemsRepo.findAll();
			for(Items item:items) {
				List<byte[]> pictures=picsRepo.findByItemId(item.getItem_id());
				item.setPictures_str(pictures);
				item.setPictures(picsRepo.findByItemId(item.getItem_id()));
				item.setTags(itemTagsRepo.getTagsOfItem(item.getItem_id()));
				item.setRec_score(0);
				for(String tag:item.getTags()) {
					if(tagsmap.containsKey(tag)){
					item.setRec_score(item.getRec_score()+tagsmap.get(tag));
					}
				}
			}
			items.sort(Comparator.comparing(Items::getRec_score).reversed());
			for(int i=0;i<5&&i<items.size();i++) {
				sortedFiveItemsList.add(items.get(i));
			}
		}
		
		else {
			ArrayList<Log> visitedList=new ArrayList<>();
			visitedList=logRepo.findVisited(user_id);
			if(visitedList.isEmpty()) return null;
			else {
				for(Log visited_auction:visitedList) {
					ArrayList<String> auctiontags=new ArrayList<>();
					auctiontags=auctionTagsRepo.getTagsOfAuction(visited_auction.getAuction_id());
					for(String tag:auctiontags) {
						
						
						if(tagsmap.get(tag)==null)tagsmap.put(tag, 1);
						else {
							int count=tagsmap.get(tag);
							tagsmap.put(tag, count+1);
						}
						int count=tagsmap.get(tag);
						tagsmap.put(tag,log(count*visited_auction.getVisit_count(),2));
					}
				}
				items=itemsRepo.findAll();
				for(Items item:items) {
					List<byte[]> pictures=picsRepo.findByItemId(item.getItem_id());
					item.setPictures_str(pictures);
					item.setPictures(picsRepo.findByItemId(item.getItem_id()));
					item.setTags(itemTagsRepo.getTagsOfItem(item.getItem_id()));
					item.setRec_score(0);
					for(String tag:item.getTags()) {
						if(tagsmap.containsKey(tag)){
						item.setRec_score(item.getRec_score()+tagsmap.get(tag));
						}
					}
				}
				items.sort(Comparator.comparing(Items::getRec_score).reversed());
				for(int i=0;i<5&&i<items.size();i++) {
					sortedFiveItemsList.add(items.get(i));
				}
			}
		}
		
		return sortedFiveItemsList;
	}
	
	public static int log(int x, int b)
	{
	    return (int) (Math.log(x) / Math.log(b));
	}
	
	@RequestMapping(value = "/get_auction/{auction_id}", method = RequestMethod.GET)
	public ArrayList<MyAuctions> getMyAuctions(@PathVariable("auction_id") Integer auction_id){
		ArrayList<Auctions> auctions=new ArrayList<>();

		
			Optional <Auctions> opt=auctionRepo.findById(auction_id);
			if(opt.isPresent()) {
				auctions.add(opt.get());
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
				Optional <Items> opt1 = itemsRepo.findById(item);
				if(opt.isPresent()) items.add(opt1.get());
				
			}
			for(Items item:items) {
				List<byte[]> pictures=picsRepo.findByItemId(item.getItem_id());
				item.setPictures_str(pictures);
				item.setPictures(picsRepo.findByItemId(item.getItem_id()));
				item.setTags(itemTagsRepo.getTagsOfItem(item.getItem_id()));
			}
			oneAuction.setTags(auctionTagsRepo.getTagsOfAuction(auction.getAuction_id()));
			oneAuction.setItems(items);
			myauctions.add(oneAuction);
		}
		return myauctions;
	}
	
}