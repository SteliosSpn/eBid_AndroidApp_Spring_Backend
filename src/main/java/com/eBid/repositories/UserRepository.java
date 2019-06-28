package com.eBid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eBid.models.Users;


public interface UserRepository extends JpaRepository<Users,String> {
	
	
}
