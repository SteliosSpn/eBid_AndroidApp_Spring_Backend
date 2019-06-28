package com.eBid.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;



@Entity
@Table(name="bidder_rating")
@IdClass(BidderRating.class)
public class BidderRating implements Serializable {
	@Id
  private String voter_id;
	@Id
  private String candidate_id;
	public String getVoter_id() {
		return voter_id;
	}
	public void setVoter_id(String voter_id) {
		this.voter_id = voter_id;
	}
	public String getCandidate_id() {
		return candidate_id;
	}
	public void setCandidate_id(String candidate_id) {
		this.candidate_id = candidate_id;
	}

  
  
}
