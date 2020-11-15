package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MySocket extends Thread { //������ ����� Ŭ���̾�Ʈ���� �����ϱ� ���� MySocket
	Socket socket; //Ŭ���̾�Ʈ ����
	OutputStream outStream; //��ſ�
	DataOutputStream dataOutStream;//��ſ�
	InputStream inStream;//��ſ�
	DataInputStream dataInStream;//��ſ�
	Server server; //���� ��ü
	
	boolean stopFlag; //������ ���� flag
	
	MySocket(Socket _s, Server server) { //���� �� �ʱ�ȭ
		try {
			this.socket = _s;
			this.server = server;
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
		} catch (Exception e) {
			
		}
	}

	public void run() {
		try {
			server.printLog(socket.getInetAddress()+" >> Server : �����"); //������ ����� �޽��� ���
			
			dataOutStream.writeUTF("ACT"); //Ŭ���̾�Ʈ�� ���� ���� �޽��� ����
			while (!stopFlag) { //�ݺ�
				String msg = dataInStream.readUTF(); //Ŭ���̾�Ʈ���� �޽��� ����
				String args[] = msg.split("��"); //�޽��� �Ľ�
				if(args.length > 0) { //�޽����� ���������� �Ѿ�Դٸ� 1�̻���
					if(args[0].equals("SCM")) { //���� ä�� �޽��� ���� ��ȣ���(SCM) ��м� ������ ���Ͽ�  ���� �α�ȭ�鿡 �޽����� ������� ����
						server.printLog(socket.getInetAddress()+" >> Server : SCM");
					}else { //�� ���� ��ȣ��� �����α׿� �״�� ���
						server.printLog(socket.getInetAddress()+" >> Server : "+msg);
					}	
				} 
				server.analysisMessage(this, msg); //������ ��ȣ �м�
			}
		} catch (Exception e) { //��� ���н�
			server.quitRoom(this); //�ش� Ŭ���̾�Ʈ ä�ù� ���� ó��
			server.clients.remove(this); //���� Ŭ���̾�Ʈ ��Ͽ��� ����
			server.printLog(socket.getInetAddress()+" >> Server : "+"��������"); //�α׿� �������� �޽��� ���
		}
	}

	public void sendMessage(String msg) { //Ŭ���̾�Ʈ�� �޽��� ����
		try {
			server.printLog("Server >> "+socket.getInetAddress()+" : "+msg); //�α׿� ���� �޽��� ���
			dataOutStream.writeUTF(msg); //�޽��� ����
		} catch (Exception e) { 
			System.out.println("Server> �޼��� ���� ���� - " + socket.getInetAddress());
		}
	}
}
