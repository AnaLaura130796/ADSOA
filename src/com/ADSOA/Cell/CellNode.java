package com.ADSOA.Cell;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

import com.ADSOA.Shared.Objects.ContentCodes;
import com.ADSOA.Shared.Objects.Message;
import com.google.gson.Gson;

public class CellNode implements CellNodeImpl, ContentCodes{

	private Gson gson;
	private UUID uuid;
	private Socket socket;
	private DataInputStream in;
	
	public CellNode() {
		gson = new Gson();
		uuid = UUID.randomUUID();	
	}
	
	@Override
	public void connectToDataField() {
		Boolean isConnected = false;
		Random r = new Random();
		while(!isConnected) {
			int result = r.nextInt(MAX-MIN) + MIN;
			try {
				socket = new Socket("127.0.0.1", result);
				isConnected = true;
			}catch(Exception e) {}
		}
	}

	@Override
	public void reportAliveStatus() {
		System.out.println("About to report ");
		Message msg = new Message();
		msg.setContentCode(REPORT_ALIVE_STATUS_FROM_CELL);
		msg.setUuidSender(String.valueOf(uuid));
		sendMessage(msg);
	}

	@Override
	public void sendMessage(Message msg) {
		String messageParsedFromMessage = gson.toJson(msg);
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(messageParsedFromMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void listenToMessages() {
		try {
			in = new DataInputStream(socket.getInputStream());
			reportAliveStatus();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			String receivedJsonMessage;
			try {
				receivedJsonMessage = in.readUTF();
				System.out.println("Received the message " + receivedJsonMessage);
				Message receivedParsedMessage = gson.fromJson(receivedJsonMessage, Message.class);
				processNewMessages(receivedParsedMessage);
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("Lost connection");
			}
		}

	}

	private void processNewMessages(Message receivedParsedMessage) {
		switch(receivedParsedMessage.getContentCode())	{
			case SUM_OPERATION_FROM_INTERFACE:{
				Message responseMessage = new Message();
				responseMessage.setDigitOne(receivedParsedMessage.getDigitOne() + receivedParsedMessage.getDigitTwo());
				responseMessage.setContentCode(SUM_OPERATION_FROM_CELL);
				responseMessage.setUuidSender(receivedParsedMessage.getUuidSender());
				sendMessage(responseMessage);
			}
			case SUB_OPERATION_FROM_INTERFACE:{
				Message responseMessage = new Message();
				responseMessage.setDigitOne(receivedParsedMessage.getDigitOne() - receivedParsedMessage.getDigitTwo());
				responseMessage.setContentCode(SUB_OPERATION_FROM_CELL);
				responseMessage.setUuidSender(receivedParsedMessage.getUuidSender());
				sendMessage(responseMessage);				
			}
			case MUL_OPERATION_FROM_INTERFACE:{
				Message responseMessage = new Message();
				responseMessage.setDigitOne(receivedParsedMessage.getDigitOne() * receivedParsedMessage.getDigitTwo());
				responseMessage.setContentCode(MUL_OPERATION_FROM_CELL);
				responseMessage.setUuidSender(receivedParsedMessage.getUuidSender());
				sendMessage(responseMessage);				
			}
			case DIV_OPERATION_FROM_INTERFACE:{
				Message responseMessage = new Message();
				responseMessage.setDigitOne(receivedParsedMessage.getDigitOne() / receivedParsedMessage.getDigitTwo());
				responseMessage.setContentCode(DIV_OPERATION_FROM_CELL);
				responseMessage.setUuidSender(receivedParsedMessage.getUuidSender());
				sendMessage(responseMessage);				
			}
		}
	}
}
