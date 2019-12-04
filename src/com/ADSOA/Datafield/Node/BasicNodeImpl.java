package com.ADSOA.Datafield.Node;

import java.net.ServerSocket;
import java.net.Socket;

import com.ADSOA.Shared.Objects.Message;

public interface BasicNodeImpl {
	
	public final static Integer MIN = 5000;
	public final static Integer MAX = 5005;
	
	ServerSocket getServerSocket();
	void connectToExistingNodes();
	void keepListeningForNodes();
	void sendMessage(Message msg, Socket socket);
}