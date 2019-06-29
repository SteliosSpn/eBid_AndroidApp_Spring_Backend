package com.eBid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Configuration
@EnableScheduling
public class TaskSchedulerService {
	
	@Autowired
	private AsyncCompletedAuctionService asyncService;

	@Scheduled(fixedDelay=60000)
	public void scheduleCompletedAuctionsScan() {
		    asyncService.checkDB();
		    
	}
}
