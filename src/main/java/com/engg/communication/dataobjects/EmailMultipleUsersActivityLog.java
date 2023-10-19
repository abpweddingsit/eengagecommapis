package com.engg.communication.dataobjects;

import java.util.List;

public class EmailMultipleUsersActivityLog {
	
	private List<EmailActivityLog> emailPayload;

	public EmailMultipleUsersActivityLog() {
		// TODO Auto-generated constructor stub
	}

	public List<EmailActivityLog> getEmailPayload() {
		return emailPayload;
	}

	public void setEmailPayload(List<EmailActivityLog> emailPayload) {
		this.emailPayload = emailPayload;
	}

	
}
