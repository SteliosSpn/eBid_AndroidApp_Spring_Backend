package com.eBid.models;

import java.util.ArrayList;
import java.util.Optional;

public class ShowAuctions {
private Optional<Auctions> auction;
private boolean ownership;
private ArrayList<Double> bids;
public Optional<Auctions> getAuction() {
	return auction;
}
public void setAuction(Optional<Auctions> current_auction) {
	this.auction = current_auction;
}
public boolean isOwnership() {
	return ownership;
}
public void setOwnership(boolean ownership) {
	this.ownership = ownership;
}
public ArrayList<Double> getBids() {
	return bids;
}
public void setBids(ArrayList<Double> bids) {
	this.bids = bids;
}
}
