package com.eBid.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="auction_tags")
@IdClass(AuctionTags.class)
public class AuctionTags implements Serializable {

	@Id
	private Integer auction_id;
	@Id
	private String tag;
	@Transient
	private List<String>tags;
	
	public Integer getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(Integer auction_id) {
		this.auction_id = auction_id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
