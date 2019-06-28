package com.eBid.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eBid.models.Role;


public interface RoleRepository extends JpaRepository<Role,String>{
	 
}
