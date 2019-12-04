package com.ADSOA.Datafield.Node;

import com.ADSOA.Shared.Objects.Message;

public interface NodeHandlerImpl {
	
	void listenForNewMessages();
	void report();
	void processNewMessages(Message receivedParsedMessage);
	
}
