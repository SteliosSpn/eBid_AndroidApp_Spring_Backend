package com.eBid.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eBid.models.ItemPics;

public interface ItemPicsRepository extends JpaRepository<ItemPics,ItemPics>{

	@Query("SELECT picture FROM ItemPics WHERE item_id=:item_id")
	public List<byte[]> findByItemId(@Param("item_id") Integer item_id);
	
	@Query("SELECT max(item_id_num) FROM ItemPics WHERE item_id=:item_id")
	public Integer findPictureCount(@Param("item_id")Integer item_id);
}
