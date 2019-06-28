package com.eBid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eBid.models.Role;
import com.eBid.models.Users;
import com.eBid.repositories.RoleRepository;
import com.eBid.repositories.UserRepository;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createUser(Users user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole=new Role("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
	}
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Users user = new Users();
        Optional<Users> opt = userRepository.findById(userid);
        if(opt.isPresent())user=opt.get();
        if (user != null) {
            List<Role> userRoles = user.getRoles();
            Set<GrantedAuthority> roles = new HashSet<>();
            for (Role r : userRoles) {
                roles.add(new SimpleGrantedAuthority(r.getName()));
            }
            List<GrantedAuthority> authorityList = new ArrayList<>(roles);
            return new org.springframework.security.core.userdetails.User(user.getUser_id(), user.getPassword(), authorityList);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }
    
    public boolean isUserPresent(String user_id) {
		Optional<Users> opt=userRepository.findById(user_id);
		
		if(opt.isPresent())
			return true;
		
		return false;
	}

}