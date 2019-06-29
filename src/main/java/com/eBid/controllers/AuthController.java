package com.eBid.controllers;



import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.security.AuthenticationFacade;
import com.eBid.services.UserService;
import com.eBid.models.Users;
import com.eBid.repositories.UserRepository;
import com.eBid.security.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

  

     @Autowired
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticationFacade authenticationFacade;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userDetailsServiceImpl;


	@GetMapping("/b")
    public Users findIncidents() {
        System.out.println("Alex");
        Users user=new Users();
        user.setUser_id("KOSTAMALAKA");
        return user;
    }

	
    @RequestMapping(value = "/register",headers = {
    "content-type=application/json" },consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public String register(@RequestBody Users user){
		
    	
    	
		
		if (userDetailsServiceImpl.isUserPresent(user.getUser_id())) {
            //user.setUserId(null);
            return "Unsuccessful Registration.The username you entered is already used.";
        }
		
		else {
            userDetailsServiceImpl.createUser(user);
            return "Registration Successful";
        }
		
		
		
		
		
		
	}
	
    public void authWithAuthManager(HttpServletRequest request, String user_id, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user_id, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        //System.out.println(authToken.getPrincipal().toString());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
    }
    
    @RequestMapping(value = "/login/{user_id}/{password}",method = RequestMethod.GET)
    public Users login(HttpServletRequest request,@PathVariable("user_id") String user_id,@PathVariable("password")String password) {
    	authWithAuthManager(request,user_id, password);
		//user.setRoles("USER")45;
    	System.out.println("success");
    	Users user= new Users();
    	user.setUser_id(user_id);
    	return user;
        
    }
    
    @RequestMapping(value="/index")
	public String home(){
		return "success";
	}
	
}