package com.eBid.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.models.AuctionTags;
import com.eBid.models.Auction_Items;
import com.eBid.models.Auctions;
import com.eBid.models.ItemPics;
import com.eBid.models.Item_Pic_List;
import com.eBid.models.Items;
import com.eBid.models.ShowAuctions;
import com.eBid.models.Users;
import com.eBid.repositories.AuctionTagsRepository;
import com.eBid.repositories.AuctionsRepository;
import com.eBid.repositories.BidsRepository;
import com.eBid.repositories.ItemPicsRepository;
import com.eBid.repositories.ItemsRepository;

@RestController
@RequestMapping("/auctions")
public class AuctionsController {

	@Autowired
	private AuctionsRepository auctionRepo;
	
	@Autowired
	private BidsRepository bidRepo;
	
	@Autowired
	private ItemPicsRepository picsRepo;
	
	@Autowired
	private ItemsRepository itemsRepo;

	
	@Autowired AuctionTagsRepository auctionTagsRepo;
	/*
    @RequestMapping(value = "/create_auction/{user_id}/{start_bid}/{starts}/{ends}/{items}/{tags}", method = RequestMethod.POST)
	public String createAuction(@PathVariable("user_id") String user_id,@PathVariable("start_bid") Double start_bid,
			@PathVariable("starts") Timestamp starts,@PathVariable("ends") Timestamp ends,
			@PathVariable("items") List<Integer> items,@PathVariable("tags")List <String>tags) {

		Auctions auction = new Auctions();
		auction.setAuctioneer(user_id);
		auction.setStart_bid(start_bid);
		auction.setCurrent_bid(0.0);
		auction.setStarts(starts);
		auction.setEnds(ends);
		auctionRepo.save(auction);
		for(Integer item:items) {
			AuctionDetails aucDetails = new AuctionDetails();
			aucDetails.setAuction_id(auctionRepo.lastAuctionId());
			aucDetails.setItem_id(item);
			auctionDetRepo.save(aucDetails);
		}
		for(String tag:tags) {
			AuctionTags auctionTag= new AuctionTags();
			auctionTag.setAuction_id(auctionRepo.lastAuctionId());
			auctionTag.setTag(tag);
			auctionTagsRepo.save(auctionTag);
		}
		return "Auction Successfully Created";
	}	*/
	
	@RequestMapping(value = "/start_auction/{user_id}/{auction_id}/{ends}", method = RequestMethod.POST)
    public String start_auction(@PathVariable("user_id") String user_id,@PathVariable("auction_id") Integer auction_id,@PathVariable("ends") Timestamp ends){
		Optional<Auctions> current_auction=auctionRepo.findById(auction_id);
		if((!current_auction.isPresent())){
			return "Auction is not found";
		}
		
		Integer currentauction_id=auctionRepo.check_aunction_ownership(user_id, auction_id);
		Optional<Auctions> current_auction1=auctionRepo.findById(currentauction_id);
		if((!current_auction1.isPresent())){
			 return "Auction is not created by you";
			}
		int check=ends.compareTo(current_auction1.get().getStarts());
		if(check<0){
			return"End date is a timestamp before start date";
		}
		current_auction1.get().setEnds(ends);
		auctionRepo.save(current_auction1.get());
    	return "Dates are defined";
    	
	}
	
	@RequestMapping(value = "/show_activeauctions/{user_id}", method = RequestMethod.GET)
	 public ArrayList<ShowAuctions> findauctions (@PathVariable("user_id") String user_id){
		 boolean ownership;
	    	List<Integer> auctionids=auctionRepo.findactiveauctions();
	    	ArrayList<ArrayList<Double>> bids=new ArrayList<ArrayList<Double>>();
	    	ArrayList<Double> current_bids=new ArrayList<Double>();
	    	ArrayList<ShowAuctions> showauctions=new ArrayList<ShowAuctions> ();
	    	for(Integer auctionid:auctionids){
	    		Optional<Auctions> current_auction=auctionRepo.findById(auctionid);
	    		Integer currentauction_id=auctionRepo.check_aunction_ownership(user_id, auctionid);
	    		Optional<Auctions> current_auction1=auctionRepo.findById(currentauction_id);
	    		if((!current_auction1.isPresent())){
	   			 	ownership=false;
	   			}
	    		else{
	    			ownership=true;
	    		}
	    		current_bids=bidRepo.getaunctionbid(auctionid);
	    		ShowAuctions showauction=new ShowAuctions();
	    		showauction.setAuction(current_auction);
	    		showauction.setOwnership(ownership);
	    		showauction.setBids(current_bids);
	    		showauctions.add(showauction);
	    	}
	    	return showauctions;
	}
	
	@RequestMapping(value = "/create_auction",headers = {
    "content-type=application/json" },consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Auctions createAuction(@RequestBody Auctions auction) {
		auction.setCurrent_bid(0.0);
		auctionRepo.save(auction);
		for(String tag:auction.getTags()) {
			AuctionTags tags = new AuctionTags();
			tags.setAuction_id(auctionRepo.lastAuctionId());
			tags.setTag(tag);
			System.out.println(tags.getTag());
			auctionTagsRepo.save(tags);
		}
	    Auctions returnAuction = new Auctions();
	    returnAuction.setAuction_id(auctionRepo.lastAuctionId());
		return returnAuction;
	}
	
	@RequestMapping(value = "/create_auction_tags",headers = {
    "content-type=application/json" },consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public String createAuctionTags(@RequestBody List<AuctionTags> tags) {
		AuctionTags auctiontags=new AuctionTags();
		auctiontags.setAuction_id(auctionRepo.lastAuctionId());
		for(AuctionTags tag:tags) {
			auctiontags.setTag(tag.getTag());
			System.out.println(auctiontags.getTag());
			//auctionTagsRepo.save(auctiontags);
		}
		
		return "Success";
	}
	
	@RequestMapping(value = "/my_auctions/{user_id}", method = RequestMethod.GET)
	public ArrayList<Auctions> getMyAuctions(@PathVariable("user_id") String user_id){
		ArrayList<Auctions> auctions=new ArrayList<>();
		ArrayList <Integer> auctions_ids=auctionRepo.findMyAuctions(user_id);
		for(Integer auction:auctions_ids) {
			Optional <Auctions> opt=auctionRepo.findById(auction);
			if(opt.isPresent()) {
				auctions.add(opt.get());
			}
		}
		//auctions.addAll(auctionRepo.findMyAuctions(user_id));
		return auctions;
	}
	
	@RequestMapping(value = "/deleteauction/{user_id}/{auction_id}", method = RequestMethod.GET)
	public String delete_aunction(@PathVariable("user_id") String user_id,@PathVariable("auction_id") Integer auction_id){
		Integer currentauction_id=auctionRepo.check_aunction_ownership(user_id, auction_id);
		Optional<Auctions> current_auction1=auctionRepo.findById(currentauction_id);
		if((!current_auction1.isPresent())){
			 return "Auction is not created by you";
			}
		Double current_bid=current_auction1.get().getCurrent_bid();
		
		if(current_bid!=0){
			return"Bids have already submitted- Auction cannot be deleted";
		}
		
		auctionRepo.deleteById(auction_id);
		//not needed cascade --change comment this area
		/*ArrayList<Integer> item_ids=itemsRepo.findauctionitems(auction_id);
		List<ItemPics> pics=picsRepo.findAll();
		
		for(Integer id:item_ids){
			Optional<Items> item=itemsRepo.findById(id);
			if((item.isPresent())){
				itemsRepo.deleteById(id);
				//this area not exist  previously
				for(ItemPics pic:pics){
					if(pic.getItem_id()==id){
						picsRepo.delete(pic);
					}
				}
				 /*ArrayList<Integer> pic_ids=picsRepo.findrecordByItemId(id);
				 for(Integer pic_id:pic_ids){
					 picsRepo.deleteById(id,pic_id);
				// }
			}
			
		}*/
		return"Record delete";
	}
	
	@RequestMapping(value = "/visitor_view/", method = RequestMethod.GET)
	public ArrayList<Auction_Items> show_all_auctions(){
		List<Auctions> auctions=auctionRepo.findAll();
		List<ItemPics> pics=picsRepo.findAll();
		ArrayList<Item_Pic_List> item_pic_lists = null;
		ArrayList<Auction_Items> auction_items=new ArrayList<Auction_Items> ();
		for(Auctions auction:auctions){
			Auction_Items auction_item=new Auction_Items();
			auction_item.setAuction(auction);
			ArrayList<Integer> itemids=itemsRepo.findItemsOfAuction(auction.getAuction_id());
			for(Integer id:itemids){
				Optional<Items> item=itemsRepo.findById(id);
				item_pic_lists=new ArrayList<Item_Pic_List>();
				ArrayList<ItemPics> item_pics=new ArrayList<ItemPics>();
				for(ItemPics pic:pics){
					if(pic.getItem_id()==id){
						item_pics.add(pic);
					}
					}
				Item_Pic_List item_pic_list=new Item_Pic_List();
				item_pic_list.setItem(item);
				item_pic_list.setPictures(item_pics);
				item_pic_lists.add(item_pic_list);
			}
			auction_item.setItems(item_pic_lists);
			auction_items.add(auction_item);
		}
		return auction_items;
	}
	
}
