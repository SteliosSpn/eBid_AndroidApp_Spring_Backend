package com.eBid.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="log")
@IdClass(Log.class)
public class Log implements Serializable{

	@Id
	private String user_id;
	@Id
	private Integer auction_id;
	private Integer visits_count;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Integer getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(Integer auction_id) {
		this.auction_id = auction_id;
	}
	public Integer getVisit_count() {
		return visits_count;
	}
	public void setVisit_count(Integer visit_count) {
		this.visits_count = visit_count;
	}
	
}