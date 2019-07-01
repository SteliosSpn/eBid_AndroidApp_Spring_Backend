package com.eBid.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eBid.models.AuctionTags;
import com.eBid.models.Auctions;
import com.eBid.models.ItemPics;
import com.eBid.models.ItemTags;
import com.eBid.models.Items;
import com.eBid.models.Users;
import com.eBid.repositories.ItemPicsRepository;
import com.eBid.repositories.ItemTagsRepository;
import com.eBid.repositories.ItemsRepository;

@RestController
@RequestMapping("/items")
public class ItemsController {

	@Autowired
	private ItemsRepository itemsRepo;
	
	@Autowired
	private ItemPicsRepository picsRepo;
	
	@Autowired
	private ItemTagsRepository itemTagsRepo;
	
	/*@RequestMapping(value = "/new_item/{name}/{description}/{country}/{latitude}/{longitude}", method = RequestMethod.POST)
	public String createNewItem(@PathVariable("name") String name,@PathVariable("description") String description,
			@PathVariable("country") String country,@PathVariable("latitude") Double latitude
			,@PathVariable("longitude") Double longitude,@RequestParam(value="file")MultipartFile[]files) {

		Items item=new Items();
		ItemPics picture=new ItemPics();
		item.setName(name);
		item.setCountry(country);
		item.setDescription(description);
		item.setLatitude(latitude);
		item.setLongitude(longitude);
		itemsRepo.save(item);
		picture.setItem_id(itemsRepo.lastItemId());
		
		int i=1;
		for(MultipartFile file:files)
		  try {
                picture.setItem_id_num(i);
	            byte[] bytes = file.getBytes();
	            picture.setPicture(bytes);
	    		picsRepo.save(picture);
                i++;

	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Error with images";
	        }
		
		
		
		return "Item Created";
	}	*/
	/*
	@RequestMapping(value = "/create_item", method = RequestMethod.POST)
	public String createNewItem(@RequestBody Items item,@RequestParam(value="file")MultipartFile[]files) {

		ItemPics picture=new ItemPics();
		itemsRepo.save(item);
		picture.setItem_id(itemsRepo.lastItemId());
		
		int i=1;
		for(MultipartFile file:files)
		  try {
                picture.setItem_id_num(i);
	            byte[] bytes = file.getBytes();
	            picture.setPicture(bytes);
	    		picsRepo.save(picture);
                i++;

	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Error with images";
	        }
		
		
		
		return "Item Created";
	}*/
	
	@RequestMapping(value = "/create_item", headers = {
    "content-type=application/json" },consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Items createNewItem(@RequestBody Items item) {

		
		itemsRepo.save(item);
		Items returnItem = new Items();
		returnItem.setItem_id(itemsRepo.lastItemId());
		if(!item.getTags().isEmpty()){
			for(String tag:item.getTags()) {
				ItemTags tags = new ItemTags();
				tags.setItem_id(itemsRepo.lastItemId());
				tags.setTag(tag);
				System.out.println(tags.getTag());
				itemTagsRepo.save(tags);
			}
		
		}
		return returnItem;
	}
	
	@RequestMapping(value = "/add_pictures", method = RequestMethod.POST)
	public Auctions createNewItem(@RequestParam(value="file")MultipartFile[]files) {

		ItemPics picture=new ItemPics();
		picture.setItem_id(itemsRepo.lastItemId());
		
		int i=1;
		for(MultipartFile file:files)
		  try {
                picture.setItem_id_num(i);
	            byte[] bytes = file.getBytes();
	            picture.setPicture(bytes);
	    		picsRepo.save(picture);
                i++;

	        } catch (IOException e) {
	            e.printStackTrace();
	        
	        }
		Auctions auction = new Auctions();
		auction.setName("success");;
		
		return auction;
	}
	
	@RequestMapping(value = "/get_item_pics/{item_id}/{pic_num}", method = RequestMethod.GET,produces =MediaType.IMAGE_PNG_VALUE)
	public  byte[] getItemPics(@PathVariable("item_id") Integer item_id,@PathVariable("pic_num") Integer pic_num) {
    List <byte[]> pictureslist = picsRepo.findByItemId(item_id);
		return pictureslist.get(pic_num-1);
	}	
	/*
	@RequestMapping(value = "/get_item_details/{item_id}", method = RequestMethod.GET)
	public  Items getItem(@PathVariable("item_id") Integer item_id) {
		Optional <Items> opt = itemsRepo.findById(item_id);
		Items item_details = new Items();
		if (opt.isPresent())item_details=opt.get();
		return item_details;
	}	*/
	
	@RequestMapping(value = "/get_item_pic_count/{item_id}", method = RequestMethod.GET)
	public  Integer getItemPicCount(@PathVariable("item_id") Integer item_id) {
		
		Integer picture_count=picsRepo.findPictureCount(item_id);
		
		return picture_count;
	}	
	
	@RequestMapping(value = "/get_auction_items/{auction_id}", method = RequestMethod.GET)
	public  ArrayList <Items> getItem(@PathVariable("auction_id") Integer auction_id) {
		ArrayList <Items> items = new ArrayList<>();
		ArrayList <Integer> items_id = itemsRepo.findItemsOfAuction(auction_id);
		for(Integer item:items_id) {
			Optional <Items> opt = itemsRepo.findById(item);
			if(opt.isPresent())items.add(opt.get());
		}
		return items;
	}
	
	
}
