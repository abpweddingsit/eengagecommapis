package com.engg.communication.external;

import java.util.Collections;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import infobip.api.client.SendMultipleTextualSmsAdvanced;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.model.Destination;
import infobip.api.model.sms.mt.send.IndiaDltOptions;
import infobip.api.model.sms.mt.send.Message;
import infobip.api.model.sms.mt.send.RegionalOptions;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;
import infobip.api.model.sms.mt.send.textual.SMSAdvancedTextualRequest;


public class InfobipSMSGateWayBusinessObject {
	private static final Logger logger = LoggerFactory.getLogger(InfobipSMSGateWayBusinessObject.class);
	
	final String userName = "abp_wed1";
	final String password = "Abpweddings123!";
	final String senderId = "ABPWED";
	
	public String sendSMS(String campaignData) {
		String responseMsg = null;
		try {
			
			//logger.info(new StringBuffer("Show campaignData of SMS is..").append(campaignData).toString());
			
			
			
			RestTemplate rt = new RestTemplate();
			
			/*String auth = userName + ":" + password;
			byte[] bytesEncoded = Base64.encodeBase64(auth.getBytes());
			String authHeader = "Basic " + new String( bytesEncoded );*/
			
			String plainCreds = userName + ":" + password;
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
			String base64Creds = new String(base64CredsBytes);
			
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Basic " + base64Creds);
			HttpEntity<String> request = new HttpEntity<>(campaignData.toString(),headers);
			
			ResponseEntity<String> result = rt.exchange("https://4mvrkp.api.infobip.com/sms/2/text/advanced", HttpMethod.POST, request, String.class);
			responseMsg = result.getBody();
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info(new StringBuffer("Show responseMsg of SMS is..").append(responseMsg).toString());
		return responseMsg;
		
	}
	
	/*public String sendSMS(String toUserMobile, String text, String dltContentTemplateId, String dltPrincipalEntityId) {
		String responseMsg = null;
		try {
			
			SendMultipleTextualSmsAdvanced client = new SendMultipleTextualSmsAdvanced(new BasicAuthConfiguration(userName, password));
			
			Destination destination = new Destination();
	        destination.setTo(toUserMobile);

	        IndiaDltOptions indiaDlt = new IndiaDltOptions();
	        indiaDlt.setContentTemplateId(dltContentTemplateId);
	        indiaDlt.setPrincipalEntityId(dltPrincipalEntityId);

	        RegionalOptions regional = new RegionalOptions();
	        regional.setIndiaDlt(indiaDlt);

	        Message message = new Message();
	        message.setFrom(senderId);
	        message.setDestinations(Collections.singletonList(destination));
	        message.setText(text);
	        message.setTransliteration("ENGLISH");
	        message.setRegional(regional);
	        
	        SMSAdvancedTextualRequest requestBody = new SMSAdvancedTextualRequest();
	        requestBody.setMessages(Collections.singletonList(message));
			
	        SMSResponse response = client.execute(requestBody);
	        
	        if ( response != null ) {
				final SMSResponseDetails sentMessageInfo = (SMSResponseDetails) response.getMessages().get(0);
				responseMsg = sentMessageInfo.getMessageId();
			}
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info(new StringBuffer("Show responseMsg of SMS is..").append(responseMsg).toString());
		return responseMsg;
		
	}*/
}
