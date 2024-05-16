package com.rishabh.messaging;

import java.time.LocalDateTime;

public class ComposeMessage {
	private String name;
	private String otp;
	private String localDateTime;
	
	public ComposeMessage() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOtp() {
		return otp;
	}
	@Override
	public String toString() {
		return "ComposeMessage [name=" + name + ", otp=" + otp + ", localDateTime=" + localDateTime + "]";
	}

	public ComposeMessage(String name, String otp, String localDateTime) {
		this.name = name;
		this.otp = otp;
		this.localDateTime = localDateTime;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(String localDateTime) {
		this.localDateTime = localDateTime;
	}

}
