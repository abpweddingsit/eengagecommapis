package com.engg.communication.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engg.communication.dataobjects.Destination;
import com.engg.communication.dataobjects.IndiaDlt;
import com.engg.communication.dataobjects.Regional;
import com.engg.communication.dataobjects.SMSActivityLog;
import com.engg.communication.external.InfobipSMSGateWayBusinessObject;
import com.engg.communication.service.SmsTrackService;





public class SMSrunCampaignThread implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(SMSrunCampaignThread.class);

	List<SMSActivityLog> smsList = null;
	int x = 0;
	SmsTrackService smsTrackServicebk = null;
	String campaignIdbk = null;
	
	public SMSrunCampaignThread(List<SMSActivityLog>  plist,int i,SmsTrackService smsTrackService,String campaignId) {
		// TODO Auto-generated constructor stub
		this.smsList = plist;
		this.x = i;
		this.smsTrackServicebk = smsTrackService;
		this.campaignIdbk = campaignId;
	}
	
	@Override
	public void run() {
		executeJobForSmsCampaignThreadSafe(smsList,x,smsTrackServicebk,campaignIdbk);
	}
	
	public void executeJobForSmsCampaignThreadSafe(List<SMSActivityLog> smspayload,int cnt,SmsTrackService trackService,String campId){
		
		try{
			//logger.info(new StringBuffer("Thread bucket SMS ... ").append(cnt).toString());
			//logger.info(new StringBuffer("SMS campaign: start processing with... ").append(smspayload.size()).toString());
			
			
			JSONArray messages1 = new JSONArray();
			JSONObject messages = new JSONObject();
			
			
			//String text = "";
			
			
			
			for(SMSActivityLog activityLog : smspayload) {	
				
				
				JSONArray destinations = new JSONArray();
				JSONObject data = new JSONObject();	
				
				JSONObject to = new JSONObject();
				
				JSONObject regional = new JSONObject();
				JSONObject indiaDlt = new JSONObject();
				
				String contactNo = null;
				
				Regional regional1 = activityLog.getRegional();
				IndiaDlt indiaDlt1 = regional1.getIndiaDlt();
				
				String dltContentTemplateId = indiaDlt1.getDltContentTemplateId();
				String dltPrincipalEntityId = indiaDlt1.getDltPrincipalEntityId();
				
				
				
				List<Destination> destinations1 = activityLog.getDestinations();
				//String to = null;
				
				//MessageFormat.format(text, activityLog.getKey());
				String text = activityLog.getText();
				
				
				for(Destination d : destinations1) {
					//to = d.getTo();
					to.put("to", d.getTo());
					contactNo = d.getTo();
				}
				
				logger.info(new StringBuffer("Thread SMS mobile no... ").append(contactNo).append(".. with bucket no..").append(cnt).toString());
				
				destinations.put(to);
				
				
								
				data.put("destinations", destinations);
				data.put("text", text);
				
				data.put("from", "ABPWED");
				
				
				indiaDlt.put("contentTemplateId", dltContentTemplateId);
				indiaDlt.put("principalEntityId", dltPrincipalEntityId);
				
				regional.put("indiaDlt", indiaDlt);
				
				data.put("regional", regional);
				
				messages1.put(data);
				
				messages.put("messages", messages1);
				
			}
			
			new InfobipSMSGateWayBusinessObject().sendSMS(messages.toString(),trackService,campId);
			logger.info(new StringBuffer(" SMS bulk data json... ").append(messages.toString()).toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
   /*public void executeJobForSmsCampaignThreadSafe(List<SMSActivityLog> smspayload,int cnt){
		
		try{
			logger.info(new StringBuffer("Thread bucket SMS ... ").append(cnt).toString());
			logger.info(new StringBuffer("SMS campaign: start processing with... ").append(smspayload.size()).toString());
				
			for(SMSActivityLog activityLog : smspayload) {	
				
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
				
				new InfobipSMSGateWayBusinessObject().sendSMS(to,text,dltContentTemplateId,dltPrincipalEntityId);
			  }
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
}
