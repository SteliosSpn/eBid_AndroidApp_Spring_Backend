package com.eBid.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Item_Pic_List {
private Optional<Items> item;
private ArrayList<ItemPics> pictures;
public Optional<Items>  getItem() {
	return item;
}
public void setItem(Optional<Items> item) {
	this.item = item;
}
public ArrayList<ItemPics> getPictures() {
	return pictures;
}
public void setPictures(ArrayList<ItemPics> pictures) {
	this.pictures = pictures;
}
}
