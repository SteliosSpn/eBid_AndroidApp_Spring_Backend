package com.eBid.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="auction_details")
@IdClass(AuctionDetails.class)
public class AuctionDetails implements Serializable {

	@Id
	private Integer item_id;
	@Id
	private Integer auction_id;
	public Integer getItem_id() {
		return item_id;
	}
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	public Integer getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(Integer auction_id) {
		this.auction_id = auction_id;
	}
	
}
