package com.eBid.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="item_pics")
@IdClass(ItemPics.class)
public class ItemPics implements Serializable{

	@Id
	private Integer item_id;
	@Id
	private Integer item_id_num;
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] picture;
	
	public Integer getItem_id() {
		return item_id;
	}
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public Integer getItem_id_num() {
		return item_id_num;
	}
	public void setItem_id_num(Integer item_id_num) {
		this.item_id_num = item_id_num;
	}
}
