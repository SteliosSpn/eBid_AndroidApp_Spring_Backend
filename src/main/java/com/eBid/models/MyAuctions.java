package com.eBid.models;

import java.sql.Timestamp;
import java.util.List;

public class MyAuctions {

	private Integer auction_id;
	private String name;
	private Timestamp starts;
	private Timestamp ends;
	private String auctioneer;
	private Double start_bid;
	private Double current_bid;
	private String highest_bidder;
	private boolean checked;
	private List<String>tags;
	private List<Items>items;
	
	public Integer getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(Integer auction_id) {
		this.auction_id = auction_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getStarts() {
		return starts;
	}
	public void setStarts(Timestamp starts) {
		this.starts = starts;
	}
	public Timestamp getEnds() {
		return ends;
	}
	public void setEnds(Timestamp ends) {
		this.ends = ends;
	}
	public String getAuctioneer() {
		return auctioneer;
	}
	public void setAuctioneer(String auctioneer) {
		this.auctioneer = auctioneer;
	}
	public Double getStart_bid() {
		return start_bid;
	}
	public void setStart_bid(Double start_bid) {
		this.start_bid = start_bid;
	}
	public Double getCurrent_bid() {
		return current_bid;
	}
	public void setCurrent_bid(Double current_bid) {
		this.current_bid = current_bid;
	}
	public String getHighest_bidder() {
		return highest_bidder;
	}
	public void setHighest_bidder(String highest_bidder) {
		this.highest_bidder = highest_bidder;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}

	
	
}
