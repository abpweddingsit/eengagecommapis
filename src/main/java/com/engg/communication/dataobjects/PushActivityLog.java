package com.engg.communication.dataobjects;

import java.util.List;

public class PushActivityLog {

	private List<String> registration_ids;
	private Notification notification;
	private Data data;
	private String priority;
	private boolean content_available;
	private boolean contentAvailable;
	private String matrimoneyAwCode;
	
    public PushActivityLog(){
		
	}

	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean isContent_available() {
		return content_available;
	}

	public void setContent_available(boolean content_available) {
		this.content_available = content_available;
	}

	public boolean isContentAvailable() {
		return contentAvailable;
	}

	public void setContentAvailable(boolean contentAvailable) {
		this.contentAvailable = contentAvailable;
	}

	public String getMatrimoneyAwCode() {
		return matrimoneyAwCode;
	}

	public void setMatrimoneyAwCode(String matrimoneyAwCode) {
		this.matrimoneyAwCode = matrimoneyAwCode;
	}
    
    
}
