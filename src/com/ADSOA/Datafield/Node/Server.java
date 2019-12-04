package com.ADSOA.Datafield.Node;

public class Server {

	public static void main(String[] args) {
		BasicNode server = new BasicNode();
		server.getServerSocket();
		server.connectToExistingNodes();
		server.keepListeningForNodes();
	}
}
