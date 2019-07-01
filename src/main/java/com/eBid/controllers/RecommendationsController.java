package com.eBid.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.models.Items;
import com.eBid.repositories.AuctionTagsRepository;
import com.eBid.repositories.BidsRepository;
import com.eBid.repositories.ItemTagsRepository;
import com.eBid.repositories.ItemsRepository;

@RestController
@RequestMapping("/rec")
public class RecommendationsController {
	
	@Autowired
	private BidsRepository bidsRepo;
	
	@Autowired
	private ItemsRepository itemsRepo;
	
	@Autowired
	private AuctionTagsRepository auctionTagsRepo;
	
	@Autowired
	private ItemTagsRepository itemTagsRepo;

	@RequestMapping(value = "/get_rec/{user_id}", method = RequestMethod.GET)
		public List<Items> getItemRec(@PathVariable("user_id") String user_id){
		List<Items> items = new ArrayList<>();
		List<Items> sortedFiveItemsList = new ArrayList<>();
		ArrayList <Integer> my_auction_ids=new ArrayList<>();
		my_auction_ids=bidsRepo.getAuctionsOfBidder(user_id);
		HashMap<String,Integer> tagsmap=new HashMap<String,Integer>();
		if(!my_auction_ids.isEmpty()) {
			for(Integer auction:my_auction_ids) {
				//System.out.println(auction);
				ArrayList<String> auctiontags=new ArrayList<>();
				auctiontags=auctionTagsRepo.getTagsOfAuction(auction);
				for(String tag:auctiontags) {
					//System.out.println(tag);
					
					if(tagsmap.get(tag)==null)tagsmap.put(tag, 1);
					else {
						int count=tagsmap.get(tag);
						tagsmap.put(tag, count+1);
					}
				}
			}
			items=itemsRepo.findAll();
			for(Items item:items) {
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
			
		}
		
		return sortedFiveItemsList;
	}
}
