package com.engg.communication.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engg.communication.dataobjects.Data;
import com.engg.communication.dataobjects.Notification;
import com.engg.communication.dataobjects.PushActivityLog;
import com.engg.communication.external.FCMPushGatewayBusinessObject;



public class PushrunCampaignThread implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(PushrunCampaignThread.class);
	
	List<PushActivityLog> pushList = null;
	int x = 0;
	
	public PushrunCampaignThread(List<PushActivityLog>  plist,int i) {
		// TODO Auto-generated constructor stub
		this.pushList = plist;
		this.x = i;
	}
	
	@Override
	public void run() {
		executeJobForPushCampaignThreadSafe(pushList,x);
    }
	
    public void executeJobForPushCampaignThreadSafe(List<PushActivityLog> pushpayload,int cnt){
		
		try{
			if (pushpayload != null && pushpayload.size() > 0) {
				
				
				logger.info(new StringBuffer("Push campaign: start processing with... ").append(pushpayload.size()).toString());
				
				for(PushActivityLog pactLog: pushpayload){
					
					List<String> registration_ids = pactLog.getRegistration_ids();
					Notification notification = pactLog.getNotification();
					Data data = pactLog.getData();
					String priority = pactLog.getPriority();
					boolean content_available = pactLog.isContent_available();
					boolean contentAvailable = pactLog.isContentAvailable();
					
					logger.info(new StringBuffer(" ").append(pactLog.getMatrimoneyAwCode()).append(" bucket no ..").append(cnt).toString());
					
					new FCMPushGatewayBusinessObject().sendAndroidCampaignPushMessage(registration_ids,notification,data,priority,content_available,contentAvailable,pactLog.getMatrimoneyAwCode());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
