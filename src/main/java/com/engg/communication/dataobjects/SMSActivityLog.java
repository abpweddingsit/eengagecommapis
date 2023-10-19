package com.engg.communication.dataobjects;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SMSActivityLog {
	
	public List<Destination> destinations;
    public String from;
    public String text;
    public Regional regional;
    
	public SMSActivityLog() {
		// TODO Auto-generated constructor stub
	}

	public List<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Regional getRegional() {
		return regional;
	}

	public void setRegional(Regional regional) {
		this.regional = regional;
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
