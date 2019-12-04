package com.ADSOA.Cell;

import com.ADSOA.Shared.Objects.Message;

public interface CellNodeImpl {
	
	public final static Integer MIN = 5000;
	public final static Integer MAX = 5005;

	void connectToDataField();
	void reportAliveStatus();
	void sendMessage(Message msg);
	void listenToMessages();
}
