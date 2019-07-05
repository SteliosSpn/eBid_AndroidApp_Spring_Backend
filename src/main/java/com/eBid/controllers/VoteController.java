package com.eBid.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eBid.models.BidderRating;
import com.eBid.models.SellerRating;
import com.eBid.models.Users;
import com.eBid.repositories.AuctionsRepository;
import com.eBid.repositories.BidderRatingRepository;
import com.eBid.repositories.BidsRepository;
import com.eBid.repositories.SellerRatingRepository;
import com.eBid.repositories.UserRepository;
@RestController
@RequestMapping("/vote")
public class VoteController {
	@Autowired
	private SellerRatingRepository Seller_ratingRepo;
	
	@Autowired private BidderRatingRepository Bidder_ratingRepo;
	@Autowired
    private AuctionsRepository auction_repo;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private BidsRepository bid_repo;
    
	
@RequestMapping(value = "/voteseller/{voter_id}/{candidate_id}/{type_vote}", method = RequestMethod.POST)
public String upvote_seller(@PathVariable("voter_id") String voter_id,@PathVariable("candidate_id") String candidate_id ,@PathVariable("type_vote") Boolean type_vote ){
	Optional<Users> candidate_user=userRepository.findById(candidate_id);
	Optional<Users> voter_user=userRepository.findById(voter_id);

	if((!candidate_user.isPresent())||(!voter_user.isPresent())){
		return "User(s) not found";
	}
	if(voter_id.equals(candidate_id)){
		return "You cannot upvote yourself!";
	}
	
	Integer count=auction_repo.findauctionuser(candidate_id);
	if(count==0){
		return "This user is not an auctioneer";
	}
	
	
	SellerRating rating=new SellerRating();
	rating.setVoter_id(voter_id);
	rating.setCandidate_id(candidate_id);
boolean exist=Seller_ratingRepo.existsById(rating);
	if(exist==true){
		return "You have already rated this user";
	}
	Integer no_votes=candidate_user.get().getSellerVotes();
	if(no_votes==null){
		no_votes=0;
		}
	if(type_vote==true){
		no_votes=no_votes+1;
		
	}
	else{
		no_votes=no_votes-1;
	}
	candidate_user.get().setSellerVotes(no_votes);
	userRepository.save(candidate_user.get());
	
	Seller_ratingRepo.save(rating);
return "0";
}

@RequestMapping(value = "/votebidder/{voter_id}/{candidate_id}/{type_vote}", method = RequestMethod.POST)
public String upvote_bidder(@PathVariable("voter_id") String voter_id,@PathVariable("candidate_id") String candidate_id,@PathVariable("type_vote") Boolean type_vote){
	Optional<Users> candidate_user=userRepository.findById(candidate_id);
	Optional<Users> voter_user=userRepository.findById(voter_id);


	if((!candidate_user.isPresent())||(!voter_user.isPresent())){
		return "User(s) not found";
	}
	if(voter_id.equals(candidate_id)){
		return "You cannot upvote yourself!";
	}
	
	Integer count=bid_repo.findbiduser(candidate_id);
	if(count==0){
		return "This user is not a bidder";
	}
	
	
	BidderRating rating=new BidderRating();
	rating.setVoter_id(voter_id);
	rating.setCandidate_id(candidate_id);
boolean exist=Bidder_ratingRepo.existsById(rating);
	if(exist==true){
		return "You have already rated this user";
	}
	Integer no_votes=candidate_user.get().getBidderVotes();
	if(no_votes==null){
		no_votes=0;
		}
	if(type_vote==true){
		no_votes=no_votes+1;
		
	}
	else{
		no_votes=no_votes-1;
	}
	candidate_user.get().setBidderVotes(no_votes);
	userRepository.save(candidate_user.get());
	
	
	
	
	
	
	
	Bidder_ratingRepo.save(rating);
return "0";
	
	
	
}
	
	
}

