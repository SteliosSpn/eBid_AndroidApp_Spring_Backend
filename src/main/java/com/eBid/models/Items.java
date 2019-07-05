package com.eBid.models;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="items")
public class Items {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer item_id;
	private Integer auction_id;
	public Integer getAuction_id() {
		return auction_id;
	}
	public void setAuction_id(Integer auction_id) {
		this.auction_id = auction_id;
	}
	private String name;
	private String description;
	private String country;
	private Double latitude;
	private Double longitude;
	@Transient 
	private List<byte[]>pictures;
	@Transient
	private List<String> tags;
	@Transient
	private Integer rec_score;
	@Transient
	private List<String> pictures_str;
	
	public List<String> getPictures_str() {
		return pictures_str;
	}
	public void setPictures_str(List<byte[]> pictures) {
		String pic_str;
		this.pictures_str=new ArrayList<String>();
		for(byte[] picture:pictures){
		pic_str = Base64.getEncoder().encodeToString(picture);
		this.pictures_str.add(pic_str);
		}}
	public Integer getItem_id() {
		return item_id;
	}
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	public List<byte[]> getPictures() {
		return pictures;
	}
	public void setPictures(List<byte[]> pictures) {
		this.pictures = pictures;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Integer getRec_score() {
		return rec_score;
	}
	public void setRec_score(Integer rec_score) {
		this.rec_score = rec_score;
	}
	
	
}