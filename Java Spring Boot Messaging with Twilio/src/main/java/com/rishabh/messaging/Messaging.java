package com.rishabh.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Messaging {
	@Value("${twilio.account.sid}")
	private String sid;
	@Value("${twilio.auth.token}")
	private String token;
	@Value("${twilio.from.phone.number}")
	private String fromPhoneNumber;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFromPhoneNumber() {
		return fromPhoneNumber;
	}
	public void setFromPhoneNumber(String fromPhoneNumber) {
		this.fromPhoneNumber = fromPhoneNumber;
	}
	
}
