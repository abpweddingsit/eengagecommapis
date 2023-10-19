package com.engg.communication.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engg.communication.dataobjects.Data;
import com.engg.communication.dataobjects.Notification;
import com.engg.communication.dataobjects.PushActivityLog;
import com.engg.communication.dataobjects.PushMultipleUsersActivityLog;
import com.engg.communication.external.FCMPushGatewayBusinessObject;
import com.engg.communication.util.PushrunCampaignThread;



@Controller
@RestController
//@RequestMapping("/api/dynamicprice")
@RequestMapping("/send")
//@PropertySource("classpath:application.properties")
public class PushCommunicationRestService {
	/*
	 * @Value("${max.com.alloowed}") private Integer pageSize;
	 */
	//@Autowired Environment env;
	
	/*
	 * public String getProperty(String keyName) { return env.getProperty(keyName);
	 * }
	 */ 
	
	@Value("${firebase.server.key}")
	private String FIREBASE_SERVER_KEY;
	
	@Value("${firebase.cm.rest.api}")
	private String FIREBASE_CM_REST_URI;
	
	private static final Logger logger = LoggerFactory.getLogger(PushCommunicationRestService.class);
	final static int THREAD_BASE_PUSH_CAMPAIGN_COUNT = 200;
	
	
		
	@CrossOrigin
	@GetMapping("/getUserdetails/{userId}")
	public String getpppercentage(@PathVariable long userId) {
	   System.out.println("Show userid-->"+userId); 
	   /*TestpropertyVal test = new TestpropertyVal();
	   
	   String pageSize = test.foo();
	   logger.info("name-->"+pageSize);*/
	   logger.info(FIREBASE_SERVER_KEY);
	   logger.info(FIREBASE_CM_REST_URI);
	   
	   return ""; 
	}
	
	
	@CrossOrigin
	@PostMapping("/push/campaign/singleUser")
	public String singleUserPushActivities(@RequestBody PushActivityLog activityLog) {
		
		try {
			logger.info(new StringBuffer("push.campaign.singleUser requested for ").append(activityLog).toString());
			List<String> registration_ids = activityLog.getRegistration_ids();
			Notification notification = activityLog.getNotification();
			Data data = activityLog.getData();
			String priority = activityLog.getPriority();
			boolean content_available = activityLog.isContent_available();
			boolean contentAvailable = activityLog.isContentAvailable();
			
			new FCMPushGatewayBusinessObject().sendAndroidCampaignPushMessage(registration_ids,notification,data,priority,content_available,contentAvailable,activityLog.getMatrimoneyAwCode());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	
	
	
	
	@CrossOrigin
	@PostMapping("/push/campaign/multipleusers")
	public String multipleUsersPushActivities(@RequestBody PushMultipleUsersActivityLog listactivityLog) {
		try{
		
		List<PushActivityLog> pushpayload = listactivityLog.getPushpayload();
		logger.info(new StringBuffer("push.campaign.multipleusers requested size for ").append(pushpayload.size()).toString());
		//LOG.info(new StringBuffer("push.campaign.multipleusers requested payload for ").append(pushpayload));
		Integer threadVal = 0;
    	Integer restVal = 0;
		
		Integer threadNo = pushpayload.size()/THREAD_BASE_PUSH_CAMPAIGN_COUNT;
		if(threadNo==0){
			threadNo = 1;
		}
		threadVal = threadNo*THREAD_BASE_PUSH_CAMPAIGN_COUNT;
		if(pushpayload.size()>threadVal){
			restVal = pushpayload.size()-threadVal;
		}
		
		//LOG.info(new StringBuffer("threadNo size for multiple users push campaign activity is..").append(threadNo));
		//LOG.info(new StringBuffer("threadVal size for multiple users push campaign activity is..").append(threadVal));
		//LOG.info(new StringBuffer("restVal size for multiple users push campaign activity is..").append(restVal));
		
		final int start = 0;
		final int end = start+THREAD_BASE_PUSH_CAMPAIGN_COUNT;
		
		int iterationCount = 0;
		
		if(pushpayload!=null && pushpayload.size()>0){
			
			iterationCount = pushpayload.size();
				
			
			try{
					for (int i = 0; i < threadNo; i++){
						
						int start1 =  i * THREAD_BASE_PUSH_CAMPAIGN_COUNT;
				    	
						int end1 = 0;
						if(threadNo==1)
							end1 = pushpayload.size()-1;
						else
							end1 =((i +1) * THREAD_BASE_PUSH_CAMPAIGN_COUNT)-1;
				        
				        //logger.info(new StringBuffer("Print start1..").append(start1).toString());
				        //logger.info(new StringBuffer("Print end1..").append(end1).toString());
						
				        
				        List<PushActivityLog> plist = new ArrayList<PushActivityLog>();
				        
				        
						for(int j= start1;j<=end1;j++){
							try{
							
							if(pushpayload.get(j)!=null)
							   plist.add(pushpayload.get(j));
							else
								break;
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					
						
						
						Runnable r = new PushrunCampaignThread(plist,i);
						new Thread(r).start();
						
						Thread.sleep(3000l);
						
					  }
					}catch(Exception e1){
        				e1.printStackTrace();
        			}finally{
        				//threadPool.shutdown();
        			}
				
		}
		
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return "success";
  }
}
