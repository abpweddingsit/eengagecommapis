package com.engg.communication.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engg.communication.dataobjects.EmailActivityLog;
import com.engg.communication.dataobjects.Placeholders;
import com.engg.communication.dataobjects.ToEmailData;
import com.engg.communication.external.InfobipEmailGateWayBusinessObject;

public class EmailrunCampaignThread implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(EmailrunCampaignThread.class);
	
	List<EmailActivityLog> emailList = null;
	int x = 0;
	
	public EmailrunCampaignThread(List<EmailActivityLog>  plist,int i) {
		// TODO Auto-generated constructor stub
		this.emailList = plist;
		this.x = i;
	}
	
	@Override
	public void run() {
		executeJobForEmailCampaignThreadSafe(emailList,x);
    }
	
    public void executeJobForEmailCampaignThreadSafe(List<EmailActivityLog> emailpayload,int cnt){
		
		try{
			//if (emailpayload != null && emailpayload.size() > 0) {
				
			    
				//logger.info(new StringBuffer("Email campaign: start processing with... ").append(emailpayload.size()).toString());
				
				
				//String subject = emailpayload.get(0).getSubject();
				//String htmlBody = emailpayload.get(0).getHtml();
				
				
				
				
				
				
				new InfobipEmailGateWayBusinessObject().sendEmail(emailpayload, cnt);
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
    
  /*public void executeJobForEmailCampaignThreadSafe(List<EmailActivityLog> emailpayload,int cnt){
		
		try{
			if (emailpayload != null && emailpayload.size() > 0) {
				
				
				logger.info(new StringBuffer("Email campaign: start processing with... ").append(emailpayload.size()).toString());
				//ToEmailData toUserEmail = null;
				//Placeholders placeholders = null;
				
				//String subject = null;
				//String htmlBody = null;
				//String name = null;
				//String to = null;
				
				for(EmailActivityLog activityLog: emailpayload){
					if(activityLog != null) {
						 ToEmailData toUserEmail = activityLog.getTo();
						 String to = toUserEmail.getTo();
						 Placeholders placeholders = activityLog.getTo().getPlaceholders();
						 String name = placeholders.getName();
						 
						 
						 String subject = activityLog.getSubject();
						 String htmlBody = activityLog.getHtml();
						 
						 new InfobipEmailGateWayBusinessObject().sendEmail(to,subject,htmlBody,name);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/

}
