package com.eBid.models;

import java.util.ArrayList;
import java.util.List;

public class Auction_Items {
private Auctions auction;
private ArrayList<Item_Pic_List> items;
public Auctions getAuction() {
	return auction;
}
public void setAuction(Auctions auction) {
	this.auction = auction;
}
public ArrayList<Item_Pic_List> getItems() {
	return items;
}
public void setItems(ArrayList<Item_Pic_List> items) {
	this.items = items;
}

}
