package com.engg.communication.dataobjects;

import java.util.List;

public class SMSMultipleUsersActivityLog {

	private List<SMSActivityLog> smsPayload;
	public SMSMultipleUsersActivityLog() {
		// TODO Auto-generated constructor stub
	}
	public List<SMSActivityLog> getSmsPayload() {
		return smsPayload;
	}
	public void setSmsPayload(List<SMSActivityLog> smsPayload) {
		this.smsPayload = smsPayload;
	}
	
	

}
