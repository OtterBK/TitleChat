package Addon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import Client.ChatroomGUI;
import Client.CheckGUI;
import Client.Client;
import Client.MainMenuGUI;

public class MySocket { //������ ����� ���� MySocket
	Socket socket; //��� ����
	OutputStream outStream; //����� ���� ��Ʈ��
	DataOutputStream dataOutStream;//����� ���� ��Ʈ��
	InputStream inStream;//����� ���� ��Ʈ��
	DataInputStream dataInStream;//����� ���� ��Ʈ��
	JFrame caller; //������ ���(�������� ��ȣ���°� ��� ����)�� ȣ����  GUI ������
	String lastMsg = null; //���������� ������ �޽���
	Listener listener; //�����׸��� 
	
	public MySocket(Socket _s) { //���� �ʱ�ȭ
		try {
			this.socket = _s;
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startListen(JFrame caller) { //������ ��� ����
		this.caller = caller; //ȣ���� ������ ����
		if(listener != null) {
			listener.stopFlag = false;
			return;
		}
		
		listener = new Listener(); //������ ����
		listener.start(); //����
	}
	
	public void stopListen() { //������ ��� ����
		if(listener != null) { //������ �ִٸ�
			listener.stopFlag = true; //stopFlag Ȱ��ȭ
		}
	}

	public void sendMessage(String msg) { //������ �޽��� ����
		try {
			dataOutStream.writeUTF(msg);
		} catch (IOException e) { //���� ���н� �˸��� ���� ���α׷� ����
			//e.printStackTrace();
			CheckGUI cf = new CheckGUI(null, "�������� ������ ������ϴ�.", true, false);	
		}
	}
	
	public String receiveMessage() { //�������� �޽��� ����
		String msg = null; //������ �޽���
		if(listener != null && listener.listening) { //���� ������ ��尡 Ȱ��ȭ �Ǿ��ִٸ�
			try {
				stopListen(); //�����ʸ� ��� ���߰�
				listener.join(); //�����ʰ� ���������� ���(�����ʰ� ��� �޽����� ������)
				msg = lastMsg; //�����ʰ� ������ �޽����� ������ �޽����� ����
				startListen(caller); //�ٽ� ������ ����
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { //������ ��尡 Ȱ��ȭ �Ǿ����� �ʴٸ�
			try {
				msg = dataInStream.readUTF(); //�������� �޽��� ����
				lastMsg = msg; //������ �޽����� ������ �޽����� ����
			} catch (IOException e) { //���� ���н� �˸��� ���� ���α׷� ����
				//e.printStackTrace();
				CheckGUI cf = new CheckGUI(null, "�������� ������ ������ϴ�.", true, false);
			}
		}
		return msg; //�޽��� ����
	}
	
	public void analysisMessage(String msg) { //�����ʸ�忡�� ������ �޽��� �м�
		String args[] = msg.split("��"); //���ڿ� �Ľ�
		String signal = args[0]; //�� �� ���ڿ��� ��ȣ��
		
		switch(signal) { //��ȣ�� ����
		
		case "CPC": //ä�ù� ��Ͽ��� Ư�� ä�ù��� ���� �����
			if(Client.status.equalsIgnoreCase("���θ޴�")) { //���� Ŭ���̾�Ʈ�� ���θ޴� ȭ���̶��
				if(caller instanceof MainMenuGUI) {
					MainMenuGUI mainMenu = (MainMenuGUI) caller;
					mainMenu.editRooms_roominfo(args); //���θ޴��� Ư�� ä�ù� ���� �޼ҵ� ȣ��
				}							
			} 
			break;
		case "CAD": //ä�ù� ��Ͽ��� ���ο� ä�ù� �߰���
			if(Client.status.equalsIgnoreCase("���θ޴�")) { //Ŭ���̾�Ʈ�� ���θ޴� ȭ���϶�
				if(caller instanceof MainMenuGUI) {
					MainMenuGUI mainMenu = (MainMenuGUI) caller;
					mainMenu.editRooms_add(args); //��Ͽ� �ش� ä�ù� �߰� �޼ҵ� ȣ��
				}							
			}
			break;
		case "CDE": //ä�ù� ��Ͽ��� ���� ä�ù��� ������
			if(Client.status.equalsIgnoreCase("���θ޴�")) { //Ŭ���̾�Ʈ ���θ޴� ȭ���϶�
				if(caller instanceof MainMenuGUI) {
					MainMenuGUI mainMenu = (MainMenuGUI) caller; 
					mainMenu.editRooms_del(args); //��Ͽ��� �ش� ä�ù� ���� �޼ҵ� ȣ��
				}							
			}
			break;
		case "RCM": //ä�ù濡�� ä�� �޽��� ��� ��ȣ ����
			if(Client.status.equalsIgnoreCase("ä�ù�")) { //Ŭ���̾�Ʈ�� ä�ù� ȭ���̶��
				if(caller instanceof ChatroomGUI) {
					ChatroomGUI chatroom = (ChatroomGUI) caller;
					chatroom.printChat(args); //ä�ù��� ä�� ��� �޼ҵ� ����
				}							
			}
			break;
		case "RAM": //ä�ù濡�� �˸� �޽��� ��� ��ȣ ����
			if(Client.status.equalsIgnoreCase("ä�ù�")) { //Ŭ���̾�Ʈ�� ä�ù� ȭ���̶��
				if(caller instanceof ChatroomGUI) {
					ChatroomGUI chatroom = (ChatroomGUI) caller;
					chatroom.printAlert(args[1]); //ä�ù��� �˸� ��� �޼ҵ� ����
				}							
			}
			break;
		case "CRI": //ä�ù濡�� ä�ù� ���� ���� ��ȣ ����
			if(Client.status.equalsIgnoreCase("ä�ù�")) { //Ŭ���̾�Ʈ�� ä�ù� ȭ���̶��
				if(caller instanceof ChatroomGUI) {
					ChatroomGUI room = (ChatroomGUI) caller;
					room.changedRoomInfo(msg); //ä�ù� ���� ���ΰ�ħ �޼ҵ� ����
				}							
			}
			break;	
		case "CKR": //ä�ù濡�� ��������� �޽��� ��ȣ ����
			if(Client.status.equalsIgnoreCase("ä�ù�")) {//Ŭ���̾�Ʈ�� ä�ù� ȭ���̶��
				if(caller instanceof ChatroomGUI) {
					ChatroomGUI room = (ChatroomGUI) caller;
					room.kicked(); //��������Ǵ� �޼ҵ� ����
				}							
			}
			break;	
		case "DMP": //���� �޽�����
			System.out.println("dump");
			break;
		}
	}
	
	private class Listener extends Thread{
		boolean stopFlag = false;
		boolean listening = true;
		
		public void run() { //������ ���
			try {
				while (!stopFlag) { //stopFlag�� true�̸� ������ �����
					listening = true; //�������� �޽����� �Ѿ���� ������̴�
					String msg = dataInStream.readUTF();
					listening = false; //���� �������� �޽����� �޾� ó���� �����̴�
					lastMsg = msg;//������ �޽��� ����
					analysisMessage(msg); //�޽��� �м�
				}
				listener = null;
			} catch (IOException e) { //������ ��� ����� ���α׷� ����
				//e.printStackTrace();
				CheckGUI cf = new CheckGUI(null, "�������� ������ ������ϴ�.", true, false);		
			} 
		}
	}
}
