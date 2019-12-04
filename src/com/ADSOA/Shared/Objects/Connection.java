package com.ADSOA.Shared.Objects;

import java.net.Socket;

public class Connection {
	Socket connectedSocket;
	int connectedType;
	
	public Socket getConnectedSocket() {
		return connectedSocket;
	}
	public void setConnectedSocket(Socket connectedSocket) {
		this.connectedSocket = connectedSocket;
	}
	public int getConnectedType() {
		return connectedType;
	}
	public void setConnectedType(int connectedType) {
		this.connectedType = connectedType;
	}
}
