package com.engg.communication.external;


import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.engg.communication.dataobjects.EmailActivityLog;
import com.engg.communication.dataobjects.ToEmailData;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import okio.Buffer;









public class InfobipEmailGateWayBusinessObject {
	private static final Logger logger = LoggerFactory.getLogger(InfobipEmailGateWayBusinessObject.class);
	
	final String EmailAPI = "https://4mvrkp.api.infobip.com/email/3/send";
	final String userName = "abp_wed1";
	final String password = "Abpweddings123!";
	final String senderId = "ABPWED";
	
	/*private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int connectTimeout = 10000;
	    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	    clientHttpRequestFactory.setConnectTimeout(connectTimeout);
	    clientHttpRequestFactory.setReadTimeout(connectTimeout);
	    return clientHttpRequestFactory;
	}*/
	
	/*public EmailResponse sendCampaign(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        
        EmailResponse emailResponse = null;
        
        String auth = userName + ":" + password;
		byte[] bytesEncoded = Base64.encodeBase64(auth.getBytes());
		
		
        interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic "+ new String(bytesEncoded)));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "multipart/form-data"));
        restTemplate.setInterceptors(interceptors);
        
        
        restTemplate.postForObject(EmailAPI, entity, EmailResponse.class);
        
        
        return emailResponse;
    }*/
	
	public String sendEmail( List<EmailActivityLog> emailpayload, int cnt) {
		String responseMsg = null;
		try {
			//OkHttpClient client = new OkHttpClient().newBuilder().build();
			OkHttpClient client = new OkHttpClient();
			
			client.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			client.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			//MediaType mediaType = MediaType.parse("text/plain");
			
			String auth = userName + ":" + password;
			byte[] bytesEncoded = Base64.encodeBase64(auth.getBytes());
			logger.info("bytesEncoded is-->"+bytesEncoded);
			
			MultipartBuilder buildernew = null;
					//.addFormDataPart("html",htmlBody);
			RequestBody body = null;
			Request request = null;
			
			
			for(EmailActivityLog activityLog: emailpayload){
				if(activityLog != null) {
					
					buildernew = new MultipartBuilder().type(MultipartBuilder.FORM)
		                    .addFormDataPart("from","ABPweddings <noreply@info.abpweddings.com>");
		                    //.addFormDataPart("subject",subject);
					
										
					JSONObject todtls = new JSONObject();
					
					
					
					ToEmailData toUserEmail = activityLog.getToplaceholder();
					
					JsonNode placeholders = activityLog.getToplaceholder().getPlaceholders();
					
					/*Iterator<String> iterator = placeholders.fieldNames();
					JSONObject jplaceholder = new JSONObject();
					
					while(iterator.hasNext()) {
						 
						
						String currentDynamicKey = (String)iterator.next();
						String value = placeholders.get(currentDynamicKey).textValue();
						//logger.info(new StringBuffer("currentDynamicKey..").append(currentDynamicKey).toString());
						//logger.info(new StringBuffer("value..").append(value).toString());
						
						jplaceholder.put(currentDynamicKey, value);
						
					}*/
				    
					String jplace = placeholders.toPrettyString();
					logger.info(new StringBuffer("jplace..").append(jplace).toString());
					 
					 
					todtls.put("to",toUserEmail.getTo());
						
					 // JSONObject jplaceholder = new JSONObject();
					  
					  //jplaceholder.put("name", name);
					  todtls.put("placeholders", new JSONObject(jplace));
					  
					  //logger.info("todtls are.."+todtls.toString());
					  
					  String htmlBody = activityLog.getHtml();
					  String subject = activityLog.getSubject();
					  
					 // logger.info(new StringBuffer("Thread Email mail id... ").append(toUserEmail.getTo()).append(".. with bucket no..").append(cnt).toString());
					 // logger.info(new StringBuffer("Thread Email html body... ").append(htmlBody).toString());
					 // logger.info("todtls is-->"+todtls.toString()); 
					  
					 
					  
					  //payLoad  =   todtls.toString() ;
					  buildernew.addFormDataPart("html",htmlBody);
					  buildernew.addFormDataPart("subject",subject);
					  buildernew.addFormDataPart("to",todtls.toString());
					  
					  					  
					  body = buildernew.build();
					  
					  request = new Request.Builder()
							  .url("https://4mvrkp.api.infobip.com/email/3/send")
							  .post( body)
							  .addHeader("Authorization", "Basic "+ new String(bytesEncoded))
							  .build();
					  
					  Response response = client.newCall(request).execute();
						
					  logger.info(response.toString());
						
					  responseMsg = response.toString();
					  logger.info(new StringBuffer("Show responseMsg of Email is..").append(responseMsg).toString());
					  
				}
			}
                   
			
			
			/*RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					  .addFormDataPart("from","noreply@info.abpweddings.com")
					  
					  .addFormDataPart("subject",subject)
					  .addFormDataPart("html",htmlBody)
					  .build();*/
			
			
			
			
			
			
			
			
			//String reqMessage = getMessage(body);
			//logger.info(new StringBuffer("Show body of Email is..").append(reqMessage).toString());
			
			
			
			//logger.info("body is-->"+body.toString());
			
			
			
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return responseMsg;
	}
	
	private String getMessage(RequestBody body) {
	   String message = null;
		try {
	    RequestBody requestBody = body;
	    

	
	
	    logger.info(" (" + requestBody.contentLength() + "-byte body)");

	    MediaType contentType = requestBody.contentType();
	    
	    contentType.charset(Charset.forName("UTF-8"));
	    
	            Buffer buffer = new Buffer();
	            requestBody.writeTo(buffer);
	            
	            
	            
	            
	            
	            
	            message = buffer.readByteString().toString();
	            
	            //logger.info(buffer.readByteString().toString());

		}catch(Exception e) {
			e.printStackTrace();
		}
	           
	       return  message;
	    
	}
	
	/*public String sendEmail(String toUserEmail, String subject, String htmlBody, String name) {
		String responseMsg = null;
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			
			
			
			MediaType mediaType = MediaType.parse("text/plain");
			
			JSONObject todtls = new JSONObject();
			todtls.put("to",toUserEmail);
			
			JSONObject jplaceholder = new JSONObject();
			jplaceholder.put("name", name);
			todtls.put("placeholders", jplaceholder);
			
			
			logger.info(todtls.toString());
			
			
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					  .addFormDataPart("from","noreply@info.abpweddings.com")
					  .addFormDataPart("to",todtls.toString())
					  .addFormDataPart("subject",subject)
					  .addFormDataPart("html",htmlBody)
					  .build();
			
			String auth = userName + ":" + password;
			byte[] bytesEncoded = Base64.encodeBase64(auth.getBytes());
			
			Request request = new Request.Builder()
					  .url("https://4mvrkp.api.infobip.com/email/3/send")
					  .method("POST", body)
					  .addHeader("Authorization", "Basic "+ new String(bytesEncoded))
					  .build();
			
			
			
			Response response = client.newCall(request).execute();
			
			logger.info(response.toString());
			
			responseMsg = response.toString();
			logger.info(new StringBuffer("Show responseMsg of Email is..").append(responseMsg).toString());
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return responseMsg;
	}*/

}
