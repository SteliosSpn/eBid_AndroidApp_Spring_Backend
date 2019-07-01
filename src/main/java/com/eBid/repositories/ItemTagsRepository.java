package com.eBid.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eBid.models.ItemTags;

public interface ItemTagsRepository extends JpaRepository<ItemTags,ItemTags> {

	@Query("SELECT tag FROM ItemTags WHERE item_id=:item_id")
	public ArrayList<String> getTagsOfItem(@Param("item_id") Integer item_id);
	
}
