package com.engg.communication.dataobjects;

import com.fasterxml.jackson.databind.JsonNode;

public class ToEmailData {
	
    private String to;
    private JsonNode placeholders;
    
	public ToEmailData() {
		// TODO Auto-generated constructor stub
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public JsonNode getPlaceholders() {
		return placeholders;
	}

	public void setPlaceholders(JsonNode placeholders) {
		this.placeholders = placeholders;
	}

	

	

	

	

	
	
	

}
