package com.eBid.models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="auctions")
public class Auctions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer auction_id;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private Timestamp starts;
	private Timestamp ends;
	private String auctioneer;
	private Double start_bid;
	private Double current_bid;
	@Transient
	private List<String> tags;
	public Integer getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(Integer auction_id) {
		this.auction_id = auction_id;
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
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}
