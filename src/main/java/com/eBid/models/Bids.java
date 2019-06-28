package com.eBid.models;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name="bids")
@IdClass(Bids.class)
public class Bids implements Serializable {
	@Id
	  private Integer auction_id;
		@Id
	  private String bidder_id;
		@Id
		private Double bid;//?
		public Integer getAuction_id() {
			return auction_id;
		}
		public void setAuction_id(Integer auction_id) {
			this.auction_id = auction_id;
		}
		public String getBidder_id() {
			return bidder_id;
		}
		public void setBidder_id(String bidder_id) {
			this.bidder_id = bidder_id;
		}
		public Double getBid() {
			return bid;
		}
		public void setBid(Double bid) {
			this.bid = bid;
		}
		
}
