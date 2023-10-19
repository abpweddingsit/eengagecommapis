package com.engg.communication.external;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import org.springframework.web.client.RestTemplate;

import com.engg.communication.dataobjects.Data;
import com.engg.communication.dataobjects.FirebaseResponse;
import com.engg.communication.dataobjects.HeaderRequestInterceptor;
import com.engg.communication.dataobjects.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FCMPushGatewayBusinessObject {
	
	private static final Logger logger = LoggerFactory.getLogger(FCMPushGatewayBusinessObject.class);

	public FCMPushGatewayBusinessObject() {
		// TODO Auto-generated constructor stub
	}
	
	final String FIREBASE_SERVER_KEY = "AAAALUVwLfE:APA91bExpRsHrbJmuzLrpHu5AT_v9m7K4CubOcZ2XaxY5HYQhz5XDnOLgnGNX_hDL6zTLphMT7naClCOce0xlKUkSu2D_UoaVqhszzzmP-XRV6zwmb0FIFOfUdbhCV8lOAq7vumGMwER";
	final String FIREBASE_CM_REST_URI = "https://fcm.googleapis.com/fcm/send";
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int connectTimeout = 10000;
	    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	    clientHttpRequestFactory.setConnectTimeout(connectTimeout);
	    clientHttpRequestFactory.setReadTimeout(connectTimeout);
	    return clientHttpRequestFactory;
	}
	
	public FirebaseResponse sendCampaign(HttpEntity<String> entity,String matrimoneyuserCode) {

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);
        
        
        FirebaseResponse firebaseResponse = restTemplate.postForObject(FIREBASE_CM_REST_URI, entity, FirebaseResponse.class);
        if(matrimoneyuserCode.equals("AW71148319") || matrimoneyuserCode.equals("AW67331594") || matrimoneyuserCode.equals("AW88079912") || matrimoneyuserCode.equals("AW45958472") || matrimoneyuserCode.equals("AW71148319") || matrimoneyuserCode.equals("AW42824190") || matrimoneyuserCode.equals("AW97176497") || matrimoneyuserCode.equals("AW88079912")){
            logger.info(new StringBuffer("fcm.push.android: response... ").append("for MatrimoneyUserCode ..").append(matrimoneyuserCode).append("  ").append(firebaseResponse).toString());
        }
        
        return firebaseResponse;
    }
	
	
	// used in production
		public void sendAndroidCampaignPushMessage(final List<String> registration_ids, final Notification ntObject, final Data fcmdata, final String priority, final boolean content_available, final boolean contentAvailable,String matrimoneyuserCode) { 
	        
			try {
				JSONArray device_ids = new JSONArray();
				for (String s : registration_ids) device_ids.put(s);
				
				JSONObject body = new JSONObject();
				body.put("registration_ids", device_ids);
				// body.put("to", "e-h-6Y9LVJs:APA91bGXP_Le7aWt7TVDVDWThAcmhUTX6fZIm2zvNwoXz3aWSt8WKT20Fq7KzG-7gjqWnRd2ymomrh90yWiyhC49-Cwdnk10r5IHTOtCFvpbx20J3Rybea6Zg8wJwoJI6Dabm9kmDzIx");
				body.put("priority", "high");
				body.put("content_available", true);
				body.put("contentAvailable", true);

				JSONObject notification = new JSONObject();
				if(ntObject!=null){
				 notification.put("body", ntObject.getBody());
				 notification.put("title", ntObject.getTitle());
				 notification.put("image", ntObject.getImage());
				}
				

				JSONObject data = new JSONObject();
				data.put("source", fcmdata.getSource());
				data.put("body", fcmdata.getBody());
				data.put("title", fcmdata.getTitle());
				data.put("clickAction", fcmdata.getClickAction());
				data.put("image", fcmdata.getImage());
				data.put("actions", fcmdata.getActions());
				
				/*JSONArray action_ids = new JSONArray();
				List<Action> list = fcmdata.getActions();
				
				for(Action a : list){
					Action ac = new Action();
					
					ac.setId(a.getId());
					ac.setLink(a.getLink());
					ac.setTitle(a.getTitle());
					
					action_ids.put(ac);
				}
				data.put("actions", action_ids);*/
				

				body.put("notification", notification);
				body.put("data", data);
				
				String pushMessage = body.toString();                    // JSONUtility.toJson(pushMessageBody);
				if(matrimoneyuserCode.equals("AW71148319") || matrimoneyuserCode.equals("AW67331594") || matrimoneyuserCode.equals("AW88079912") || matrimoneyuserCode.equals("AW45958472") || matrimoneyuserCode.equals("AW71148319") || matrimoneyuserCode.equals("AW42824190") || matrimoneyuserCode.equals("AW97176497") || matrimoneyuserCode.equals("AW88079912")){
					logger.info(new StringBuffer().append("fcm.push.android: payload... ").append(pushMessage).toString());
				}
				
				HttpEntity<String> request = new HttpEntity<>(pushMessage);
				sendCampaign(request,matrimoneyuserCode);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

}
