package com.ADSOA.Datafield.Node;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import com.ADSOA.Shared.Objects.Connection;
import com.ADSOA.Shared.Objects.Message;
import com.ADSOA.Shared.Objects.Types;
import com.google.gson.Gson;

public class BasicNode implements BasicNodeImpl, Types{
	private Integer port;
	private ServerSocket serverSocket;
	private Gson gson;
	private UUID uuid;
	private ArrayList<Connection> connectedElements = new ArrayList<Connection>();
	
	@Override
	public ServerSocket getServerSocket() {
		gson = new Gson();
		uuid = UUID.randomUUID();
		for (int i = MIN; i < MAX; i++) {
			try {
				serverSocket = new ServerSocket(i);
				port = i;
				System.out.println("Found Connection at port: " + i);
				return serverSocket;
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public void connectToExistingNodes() {
		for (int i = MIN; i < MAX; i++) {
			if(i != port) {
				try {
					Socket tempSocket = new Socket("127.0.0.1", i);
					NodeHandler nodeHandler = new NodeHandler(tempSocket, this, uuid);
					new Thread(nodeHandler).start();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void keepListeningForNodes() {
		while(true) {
			try {
				Socket tempSocket = serverSocket.accept();
				new Thread(new NodeHandler(tempSocket, this, uuid)).start();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	@Override
	public synchronized void sendMessage(Message msg, Socket socket) {
		String messageParsedFromMessage = gson.toJson(msg);
		try {
			//ou.writeUTF(messageParsedFromMessage);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(messageParsedFromMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void addToTable(Connection con) {
		connectedElements.add(con);
	}
	public synchronized void broadcast(Message msg) {
		for(Connection con: connectedElements) {
			if(msg.isBroadcasted()) {
				if(con.getConnectedType() != NODE) {
					sendMessage(msg, con.getConnectedSocket());
				}
			}else {
				sendMessage(msg, con.getConnectedSocket());
			}
		}
	}
}