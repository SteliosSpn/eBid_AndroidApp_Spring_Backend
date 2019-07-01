package com.eBid.models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;




import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;
@Entity
@Table(name="users")
public class Users implements Serializable{
	@Id
	private String user_id;
    private String name;
    private String surname;
    private String password;
    private String country;
    private Integer sellerRatingTotal;
    private Integer bidderRatingTotal;
    private Integer sellerVotes;
    private Integer bidderVotes;
    private Integer afm;
    private String email;
    private String city;
    private String telephone;
    private String address;
    private Integer inboxcount;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLES", joinColumns={
			@JoinColumn(name = "USER_ID", referencedColumnName = "user_id") }, inverseJoinColumns = {
					@JoinColumn(name = "NAME", referencedColumnName = "name") })
    private List<Role> roles;
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles2) {
		this.roles = roles2;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getSellerRatingTotal() {
		return sellerRatingTotal;
	}
	public void setSellerRatingTotal(Integer sellerRatingTotal) {
		this.sellerRatingTotal = sellerRatingTotal;
	}
	public Integer getBidderRatingTotal() {
		return bidderRatingTotal;
	}
	public void setBidderRatingTotal(Integer bidderRatingTotal) {
		this.bidderRatingTotal = bidderRatingTotal;
	}
	public Integer getSellerVotes() {
		return sellerVotes;
	}
	public void setSellerVotes(Integer sellerVotes) {
		this.sellerVotes = sellerVotes;
	}
	public Integer getBidderVotes() {
		return bidderVotes;
	}
	public void setBidderVotes(Integer bidderVotes) {
		this.bidderVotes = bidderVotes;
	}
	public Integer getAfm() {
		return afm;
	}
	public void setAfm(Integer afm) {
		this.afm = afm;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getInboxcount() {
		return inboxcount;
	}
	public void setInboxcount(Integer inboxcount) {
		this.inboxcount = inboxcount;
	}
	
	

}
