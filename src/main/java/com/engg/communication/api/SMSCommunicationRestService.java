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

import com.engg.communication.dataobjects.Destination;
import com.engg.communication.dataobjects.IndiaDlt;
import com.engg.communication.dataobjects.Regional;
import com.engg.communication.dataobjects.SMSActivityLog;
import com.engg.communication.dataobjects.SMSMultipleUsersActivityLog;
import com.engg.communication.external.InfobipSMSGateWayBusinessObject;
import com.engg.communication.util.SMSrunCampaignThread;

@Controller
@RestController
@RequestMapping("/send")
public class SMSCommunicationRestService {

	private static final Logger logger = LoggerFactory.getLogger(SMSCommunicationRestService.class);
	final static int THREAD_BASE_SMS_CAMPAIGN_COUNT = 200;
	
	
	@CrossOrigin
	@PostMapping("/sms/campaign/singleUser")
	public String singleUserPushActivities(@RequestBody SMSActivityLog activityLog) {
		
		try {
			logger.info(new StringBuffer("sms.campaign.singleUser requested for ").append(activityLog).toString());
			
			if(activityLog!=null) {
				List<Destination> destinations = activityLog.getDestinations();
				String to = null;
				
				for(Destination d : destinations) {
					to = d.getTo();
				}
				String text = activityLog.getText();
				Regional regional = activityLog.getRegional();
				IndiaDlt indiaDlt = regional.getIndiaDlt();
				
				String dltContentTemplateId = indiaDlt.getDltContentTemplateId();
				String dltPrincipalEntityId = indiaDlt.getDltPrincipalEntityId();
				
				//new InfobipSMSGateWayBusinessObject().sendSMS(to,text,dltContentTemplateId,dltPrincipalEntityId);
				
			}
		    
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	@CrossOrigin
	@PostMapping("/sms/campaign/multipleusers")
	public String multipleUsersSMSActivities(@RequestBody SMSMultipleUsersActivityLog listactivityLog) {
		
		try {
			List<SMSActivityLog> smspayload = listactivityLog.getSmsPayload();
			logger.info(new StringBuffer("sms.campaign.multipleusers requested size for ").append(smspayload.size()).toString());
			
			Integer threadVal = 0;
	    	Integer restVal = 0;
			
			Integer threadNo = smspayload.size()/THREAD_BASE_SMS_CAMPAIGN_COUNT;
			if(threadNo==0){
				threadNo = 1;
			}
			threadVal = threadNo*THREAD_BASE_SMS_CAMPAIGN_COUNT;
			if(smspayload.size()>threadVal){
				restVal = smspayload.size()-threadVal;
			}
			
			final int start = 0;
			final int end = start+THREAD_BASE_SMS_CAMPAIGN_COUNT;
			
			int iterationCount = 0;
			
			if(smspayload!=null && smspayload.size()>0){
				
				iterationCount = smspayload.size();
				
				try {
                      for (int i = 0; i < threadNo; i++){
						
						int start1 =  i * THREAD_BASE_SMS_CAMPAIGN_COUNT;
				    							
						int end1 = 0;
						if(threadNo==1)
							end1 = smspayload.size()-1;
						else
							end1 =((i +1)*THREAD_BASE_SMS_CAMPAIGN_COUNT)-1;
						
				        
				        //logger.info(new StringBuffer("Print start1..").append(start1).toString());
				        //logger.info(new StringBuffer("Print end1..").append(end1).toString());
						
				        
				        List<SMSActivityLog> plist = new ArrayList<SMSActivityLog>();
				        
				        
						for(int j= start1;j<=end1;j++){
							try{
							
							if(smspayload.get(j)!=null)
							   plist.add(smspayload.get(j));
							else
								break;
							}catch(Exception e){
								e.printStackTrace();
							}
						}
				        
				        
						
					if(plist!=null && plist.size()>0) {
						Runnable r = new SMSrunCampaignThread(plist,i);
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
