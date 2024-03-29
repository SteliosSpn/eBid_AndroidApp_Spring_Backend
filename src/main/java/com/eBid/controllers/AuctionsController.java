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
import com.eBid.models.MyAuctions;
import com.eBid.models.ShowAuctions;
import com.eBid.models.Users;
import com.eBid.repositories.AuctionTagsRepository;
import com.eBid.repositories.AuctionsRepository;
import com.eBid.repositories.BidsRepository;
import com.eBid.repositories.ItemPicsRepository;
import com.eBid.repositories.ItemTagsRepository;
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
	
    @Autowired 
	private AuctionTagsRepository auctionTagsRepo;
    
    @Autowired
    private ItemTagsRepository itemTagsRepo;
    

	
    @RequestMapping(value = "/start_auction/{user_id}/{auction_id}", method = RequestMethod.GET)
    public String start_auction(@PathVariable("user_id") String user_id,@PathVariable("auction_id") Integer auction_id){
    Optional<Auctions> current_auction=auctionRepo.findById(auction_id);
    if((!current_auction.isPresent())){
    return "Auction is not found";
    }

    Integer currentauction_id=auctionRepo.check_aunction_ownership(user_id, auction_id);
    Optional<Auctions> current_auction1=auctionRepo.findById(currentauction_id);
    if((!current_auction1.isPresent())){
    return "This auction was not created by you";
    }

    Timestamp start_date=current_auction1.get().getStarts();
    start_date.setHours(start_date.getHours()-3);



    boolean check= start_date.before(new Timestamp(System.currentTimeMillis()));
    if(check==true){
    return"Auction has already started";
    }

    Timestamp end_date=current_auction1.get().getEnds();
    check= start_date.after(end_date);
    if(check==true){
    return"The start date you have entered is  after the end date";
    }

    current_auction1.get().setStarts(new Timestamp(System.currentTimeMillis()));
    auctionRepo.save(current_auction1.get());
    return "Auction started successfully!";

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
		auction.setChecked(false);
		auctionRepo.save(auction);
		if(auction.getTags().isEmpty()){}
		else {
		for(String tag:auction.getTags()) {
			AuctionTags tags = new AuctionTags();
			tags.setAuction_id(auctionRepo.lastAuctionId());
			tags.setTag(tag);
			System.out.println(tags.getTag());
			auctionTagsRepo.save(tags);
		}}
	    Auctions returnAuction = new Auctions();
	    returnAuction.setAuction_id(auctionRepo.lastAuctionId());
		return returnAuction;
	}

	
	@RequestMapping(value = "/my_auctions/{user_id}", method = RequestMethod.GET)
	public ArrayList<MyAuctions> getMyAuctions(@PathVariable("user_id") String user_id){
		ArrayList<Auctions> auctions=new ArrayList<>();
		ArrayList <Integer> auctions_ids=auctionRepo.findMyAuctions(user_id);
		for(Integer auction:auctions_ids) {
			Optional <Auctions> opt=auctionRepo.findById(auction);
			if(opt.isPresent()) {
				auctions.add(opt.get());
			}
		}
		ArrayList<MyAuctions> myauctions=new ArrayList<>();
		//auctions.addAll(auctionRepo.findMyAuctions(user_id));
		for(Auctions auction:auctions) {
            MyAuctions oneAuction=new MyAuctions();
            oneAuction.setAuction_id(auction.getAuction_id());
            oneAuction.setName(auction.getName());
            oneAuction.setStarts(auction.getStarts());
            oneAuction.setEnds(auction.getEnds());
            oneAuction.setAuctioneer(user_id);
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
			oneAuction.setTags(auctionTagsRepo.getTagsOfAuction(auction.getAuction_id()));
			oneAuction.setItems(items);
			myauctions.add(oneAuction);
		}
		return myauctions;
	}
	
	@RequestMapping(value = "/deleteauction/{user_id}/{auction_id}", method = RequestMethod.GET)
	public String delete_aunction(@PathVariable("user_id") String user_id,@PathVariable("auction_id") Integer auction_id){
		Integer currentauction_id=auctionRepo.check_aunction_ownership(user_id, auction_id);
		Optional<Auctions> current_auction1=auctionRepo.findById(currentauction_id);
		if((!current_auction1.isPresent())){
			 return "This auction is not created by you";
			}
		Double current_bid=current_auction1.get().getCurrent_bid();
		
		if(current_bid!=0){
			return"Bids have already been submitted - Auction cannot be deleted";
		}
		
		auctionRepo.deleteById(auction_id);
		System.out.println(auction_id);
		System.out.println(currentauction_id);
	
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
