package com.ADSOA.Shared.Objects;

public class Message {
	
	private int contentCode;
	private boolean isBroadcasted;
	private String uuidSender;
	private int digitOne;
	private int digitTwo;
	
	public Message() {
		this.setBroadcasted(false);
	}
	
	public int getContentCode() {
		return contentCode;
	}

	public void setContentCode(int contentCode) {
		this.contentCode = contentCode;
	}

	public boolean isBroadcasted() {
		return isBroadcasted;
	}

	public void setBroadcasted(boolean isBroadcasted) {
		this.isBroadcasted = isBroadcasted;
	}

	public String getUuidSender() {
		return uuidSender;
	}

	public void setUuidSender(String uuidSender) {
		this.uuidSender = uuidSender;
	}

	public int getDigitOne() {
		return digitOne;
	}

	public void setDigitOne(int digitOne) {
		this.digitOne = digitOne;
	}

	public int getDigitTwo() {
		return digitTwo;
	}

	public void setDigitTwo(int digitTwo) {
		this.digitTwo = digitTwo;
	}
}
