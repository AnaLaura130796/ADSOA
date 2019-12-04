package com.ADSOA.Datafield.Node;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import com.ADSOA.Shared.Objects.Connection;
import com.ADSOA.Shared.Objects.ContentCodes;
import com.ADSOA.Shared.Objects.Message;
import com.ADSOA.Shared.Objects.Types;
import com.google.gson.Gson;

public class NodeHandler extends Thread implements  NodeHandlerImpl, ContentCodes, Types{

	private Socket socket;
	private BasicNode basicNode;
	private DataInputStream in;
	private Gson gson;
	private UUID uuid;
	
	public NodeHandler(Socket tempSocket, BasicNode basicNode, UUID uuid) {
		this.basicNode = basicNode;
		this.socket = tempSocket;
		this.gson = new Gson();
		this.uuid = uuid;
		System.out.println("New thread instanced " + uuid);
		try {
			this.in = new DataInputStream(socket.getInputStream());
			report();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		System.out.println("Running ");

		while(true) {
			listenForNewMessages();
		}
	}

	@Override
	public void listenForNewMessages() {
		String receivedJsonMessage;
		try {
			receivedJsonMessage = in.readUTF();
			System.out.println("Received the message " + receivedJsonMessage);
			Message receivedParsedMessage = gson.fromJson(receivedJsonMessage, Message.class);
			processNewMessages(receivedParsedMessage);
		} catch (IOException e) {
			//e.printStackTrace();
			//System.out.println("Lost connection");
		}
	}

	@Override
	public void processNewMessages(Message receivedParsedMessage) {
		switch (receivedParsedMessage.getContentCode()){
			case REPORT_ALIVE_STATUS_FROM_NODE:{
				Connection con = new Connection();
				con.setConnectedSocket(socket);
				con.setConnectedType(NODE);
				this.basicNode.addToTable(con);
				break;
			}
			case REPORT_ALIVE_STATUS_FROM_CELL:{
				Connection con = new Connection();
				con.setConnectedSocket(socket);
				con.setConnectedType(CELL);
				this.basicNode.addToTable(con);
				break;
				
			}
			case REPORT_ALIVE_STATUS_FROM_INTERFACE:{
				Connection con = new Connection();
				con.setConnectedSocket(socket);
				con.setConnectedType(INTERFACE);
				this.basicNode.addToTable(con);
				break;
			}
			case SUM_OPERATION_FROM_INTERFACE:
			case SUB_OPERATION_FROM_INTERFACE:
			case MUL_OPERATION_FROM_INTERFACE:
			case DIV_OPERATION_FROM_INTERFACE:{
				receivedParsedMessage.setBroadcasted(true);
				basicNode.broadcast(receivedParsedMessage);
				break;
			}
			case SUM_OPERATION_FROM_CELL:
			case SUB_OPERATION_FROM_CELL:
			case MUL_OPERATION_FROM_CELL:
			case DIV_OPERATION_FROM_CELL:{
			
			}
			default:{
				
			}
		}
	}

	@Override
	public void report() {
		System.out.println("About to report ");
		Message msg = new Message();
		msg.setContentCode(REPORT_ALIVE_STATUS_FROM_NODE);
		msg.setUuidSender(String.valueOf(uuid));
		this.basicNode.sendMessage(msg, socket);
	}


}