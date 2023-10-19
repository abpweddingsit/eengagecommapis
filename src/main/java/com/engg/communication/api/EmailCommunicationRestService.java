package com.engg.communication.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engg.communication.dataobjects.EmailActivityLog;
import com.engg.communication.dataobjects.EmailMultipleUsersActivityLog;
import com.engg.communication.dataobjects.Placeholders;
import com.engg.communication.dataobjects.ToEmailData;
import com.engg.communication.external.InfobipEmailGateWayBusinessObject;
import com.engg.communication.util.EmailrunCampaignThread;

@Controller
@RestController
@RequestMapping("/send")
public class EmailCommunicationRestService {
	private static final Logger logger = LoggerFactory.getLogger(EmailCommunicationRestService.class);
	final static int THREAD_BASE_EMAIL_CAMPAIGN_COUNT = 500;
	
	
	/*@CrossOrigin
	@PostMapping("/email/campaign/singleUser")
	public String singleUserEmailActivities(@RequestBody EmailActivityLog activityLog) {
		
		try {
			logger.info(new StringBuffer("email.campaign.singleUser requested for ").append(activityLog).toString());
			ToEmailData toUserEmail = null;
			Placeholders placeholders = null;
			
			String subject = null;
			String htmlBody = null;
			String name = null;
			String to = null;
			
			
			
			if(activityLog != null) {
				 toUserEmail = activityLog.getToplaceholder();
				 to = toUserEmail.getTo();
				 placeholders = activityLog.getToplaceholder().getPlaceholders();
				 name = placeholders.getName();
				 
				 
				 subject = activityLog.getSubject();
				 htmlBody = activityLog.getHtml();
			}
		    
			//new InfobipEmailGateWayBusinessObject().sendEmail(to,subject,htmlBody,name);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}*/
	
	@CrossOrigin
	@PostMapping("/email/campaign/multipleusers")
	public String multipleUsersEmailActivities(@RequestBody EmailMultipleUsersActivityLog listactivityLog) {
		
		try {
			List<EmailActivityLog> emailpayload = listactivityLog.getEmailPayload();
			logger.info(new StringBuffer("push.campaign.multipleusers requested size for ").append(emailpayload.size()).toString());
			
			Integer threadVal = 0;
	    	Integer restVal = 0;
			
			Integer threadNo = emailpayload.size()/THREAD_BASE_EMAIL_CAMPAIGN_COUNT;
			if(threadNo==0){
				threadNo = 1;
			}
			threadVal = threadNo*THREAD_BASE_EMAIL_CAMPAIGN_COUNT;
			if(emailpayload.size()>threadVal){
				restVal = emailpayload.size()-threadVal;
			}
			
			final int start = 0;
			final int end = start+THREAD_BASE_EMAIL_CAMPAIGN_COUNT;
			
			int iterationCount = 0;
			
			if(emailpayload!=null && emailpayload.size()>0){
				
				iterationCount = emailpayload.size();
				
				try {
                      for (int i = 0; i < threadNo; i++){
						
						int start1 =  i * THREAD_BASE_EMAIL_CAMPAIGN_COUNT;
				    	
						int end1 = 0;
						if(threadNo==1)
							end1 = emailpayload.size()-1;
						else
							end1 =((i +1) * THREAD_BASE_EMAIL_CAMPAIGN_COUNT)-1;
				        
				        //logger.info(new StringBuffer("Print start1..").append(start1).toString());
				        //logger.info(new StringBuffer("Print end1..").append(end1).toString());
						
				        
				        List<EmailActivityLog> plist = new ArrayList<EmailActivityLog>();
				        
				        
						for(int j= start1;j<=end1;j++){
							try{
							
							if(emailpayload.get(j)!=null)
							   plist.add(emailpayload.get(j));
							else
								break;
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					
						
						if(plist!=null && plist.size()>0) {
							Runnable r = new EmailrunCampaignThread(plist,i);
							new Thread(r).start();
							
							//Thread.sleep(3000l);
						}
					  }
				}catch(Exception e1){
    				e1.printStackTrace();
    			}finally{
    				//threadPool.shutdown();
    			}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	

}
