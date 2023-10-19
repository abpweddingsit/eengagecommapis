package com.engg.communication.dataobjects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailActivityLog {

	private String from;
    private ToEmailData toplaceholder;
    private String subject;
    private String html;
    
	public EmailActivityLog() {
		// TODO Auto-generated constructor stub
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public ToEmailData getToplaceholder() {
		return toplaceholder;
	}

	public void setToplaceholder(ToEmailData toplaceholder) {
		this.toplaceholder = toplaceholder;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
