package com.engg.communication.dataobjects;

import java.util.List;

public class SMSMultipleUsersActivityLog {

	private List<SMSActivityLog> smsPayload;
	private String campaignId;
	public SMSMultipleUsersActivityLog() {
		// TODO Auto-generated constructor stub
	}
	public List<SMSActivityLog> getSmsPayload() {
		return smsPayload;
	}
	public void setSmsPayload(List<SMSActivityLog> smsPayload) {
		this.smsPayload = smsPayload;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	
	

}
