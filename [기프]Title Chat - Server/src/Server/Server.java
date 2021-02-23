package Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server{
	
	//�ۼ��� 2016244009 �����
	//���α׷� �� : Title Chat - Server
	//����� �ڽ��� ���ϴ� ������ �����Ͽ� ä�ù��� �����ϰų� �����Ͽ� ä���ϴ� ���α׷�
	//���� 1.0.0
	//������ IP���� Ŭ���̾�Ʈ 2�� ������ ���� �� 90~94 ���� �ּ�ó���� ����
	
	ServerSocket ss = null; //���� ����
	HashMap<MySocket, ClientInfo> clients = new HashMap<MySocket, ClientInfo>(); //����� Ŭ���̾�Ʈ ���
	List<Chatroom> chatroomList = new ArrayList<Chatroom>(); //ä�ù� ���
	Server server; //����
	MyDatabase database; //�����ͺ��̽� ��ſ� Ŭ����
	
	////GUI����
	JFrame frame;
	JScrollPane sc;
	JTextArea logArea;
	int frameWidth = 500;
	int frameHeight = 700;
	
	public Server() {
			server = this;
			database = new MyDatabase();
			try {
				server.ss = new ServerSocket(7000);     //���� ���� ����
				//InputCmd inputCmd = new InputCmd(); ������
				//inputCmd.start();
				
				//////////������
				Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
				frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout(5,5));
				frame.setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth/2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
				frame.setTitle("Title Chat Log Manager"); //������ ���� ����
				frame.setResizable(true); //������ ũ�� ���� ����
				frame.setBackground(Color.LIGHT_GRAY); //���� ����
				frame.add(new JPanel(), BorderLayout.NORTH); //����� GUI�� ���� �߰�
				frame.add(new JPanel(), BorderLayout.SOUTH); //����� GUI�� ���� �߰�
				frame.add(new JPanel(), BorderLayout.EAST); //����� GUI�� ���� �߰�
				frame.add(new JPanel(), BorderLayout.WEST); //����� GUI�� ���� �߰�
				//this.setUndecorated(true); ������ Ʋ ���ֱ��, ������
			
				logArea = new JTextArea();
				logArea.setFont(logArea.getFont().deriveFont(15f));
				logArea.setAutoscrolls(true);
				logArea.setEditable(false);
				logArea.setLineWrap(true);   //������ �����ٷ� ���� ����.
				sc = new JScrollPane(logArea);
				sc.setAutoscrolls(true);
				frame.add(sc, BorderLayout.CENTER);
				
				URL src = Server.class.getResource("./resources/baricon.png"); //������ ����
				ImageIcon icon = new ImageIcon(src);
				frame.setIconImage(icon.getImage());
				
				frame.setVisible(true); //������ ǥ��
				
				printLog("Server> ���� ���� �Ϸ�");
				///////////////////
							
				while(true) {   //Ŭ���̾�Ʈ ���� ��û ��� �� ���
					Socket socket = server.ss.accept();       //���ϻ���
					MySocket s = new MySocket(socket, this); //Ŀ���� ��� ���� ����
					boolean exist = false; //�̹� ���������� Ȯ�ο���
					for(MySocket client : clients.keySet()) { //���ӵ� Ŭ���̾�Ʈ ��Ͽ��� Ž��
						//���ӵ� Ŭ���̾�Ʈ ����߿� ���� ������ Ŭ���̾�Ʈ�� IP�� ������ ���� �ִٸ�
						if(client.socket.getInetAddress().toString().equals(socket.getInetAddress().toString())) {
							s.sendMessage("FAL���̹� ������ IP���� ���ӵǾ� �ֽ��ϴ�."); //�ź� �޽��� ����
							exist = true; //����  �źμ���
							break;
						}						
					}
					if(!exist) { //������ �źε��� �ʾҴٸ�
						server.clients.put(s, null); //Ŭ���̾�Ʈ ��Ͽ� �߰�
						s.start(); //�ش�  Ŭ���̾�Ʈ�� ��� ����
					}		
				}
			} catch(SocketException e){
				System.out.println("Server> ���� ���� - ��� ���� �߻�");
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void printLog(String str) { //�α� GUI�� �α����
		SimpleDateFormat format1 = new SimpleDateFormat ( "[yy��MM��dd�� HH:mm:ss] "); //�ð�
		String format_time1 = format1.format (System.currentTimeMillis());
		logArea.append(format_time1+str+"\n"); //���
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}
	
	public static void main(String args[]) {
		Server server = new Server(); //���� ����
	}
	
	public void analysisMessage(MySocket client, String msg) { //��ȣ �м�
		try {//Ȥ�� �𸣴� try��
			String args[] = msg.split("��"); //�޽��� �Ľ�
			String signal = args[0]; //��ȣ

			switch(signal) { //��ȣ�� ���� ó��
			
			case "LGI": //�α���
				server.login(client, args);
				break;
			case "RGS": //ȸ������
				server.register(client, args);
				break;
			case "FPW": //��й�ȣ ã��
				server.findPassword(client, args);
				break;
			case "GCL": //ä�ù� ��� ��û
				server.sendChatroomList(client);
				break;
			case "EUI": //����� ���� ����
				server.editUserInfo(client, args);
				break;
			case "CTR": //ä�ù� ����
				server.createChatroom(client, args);
				break;
			case "JIR": //ä�ù� ����
				server.joinChatroom(client, args);
				break;
			case "QTR": //ä�ù� ����
				server.quitRoom(client);
				break;
			case "SCM": //ä�� �޽��� ����
				server.sendChatmsg(client, args);
				break;
			case "ROC": //ä�ù� ���� ����
				server.roomOwnerChange(client, args);
				break;
			case "RUK": //ä�ù濡�� Ư�� ����� �������� ��û
				server.roomUserKick(client, args);
				break;
			case "EDR": //ä�ù� ���� ����
				server.editRoomInfo(client, args);
				break;
			case "DMP": //�����޽���
				server.sendDumpMsg(client, args);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void login(MySocket client, String args[]) { //�α���
		String id = args[1]; //id
		String pw = args[2]; //pw
		String resPW = database.getPassword(id); //�����ͺ��̽����� id�� �̿��Ͽ� pw�� ������
		if(resPW == null) { //�����ͺ��̽��� �ش� id�� ���ٸ�
			client.sendMessage("FAL���������� �ʴ� ID�Դϴ�."); //���� �޽����� ���� ����
		} else { 
			if(!pw.equals(resPW)) { //�����ͺ��̽��� pw���� ����ڰ� ���� pw���� ��ġ���� �ʴٸ� 
				client.sendMessage("FAL�׺�й�ȣ�� ��ġ���� �ʽ��ϴ�."); //���и޽����� ���� ����
			} else { 
				if(clients.size() > 0) { //������ ������ Ŭ���̾�Ʈ�� 1���� �ִٸ�
					for(MySocket tmpClient : clients.keySet()) { //Ŭ���̾�Ʈ Ž��
						ClientInfo cInfo = clients.get(tmpClient); 
						if(cInfo == null) continue;
						if(cInfo.id.equals(id)) { //���ӵǾ��ִ� Ŭ���̾�Ʈ�� ID���� ����ڰ� ���� ID���� ��ġ�ϴ� ���� �ִٸ�
							client.sendMessage("FAL���̹� ������ ID�� ������   ���ӵǾ��ֽ��ϴ�."); //�ź� �޽����� ���� ����
							return; //�α��� ����
						}
					}
				}	
				String info[] = database.getUserInfo(id); //�����ͺ��̽����� id���� �̿��Ͽ� ����� ���� �ҷ���
				if(info == null) { //����� ������ �����ͺ��̽��� ���ٸ�
					client.sendMessage("FAL�׻���� ������ �ҷ����µ� �����߽��ϴ�."); //�ź� �޽����� ���� ����
				} else {
					ClientInfo cInfo = new ClientInfo(client, id, info[0], info[1], info[2]); //����� ������ ���� ClientInfo Ŭ���� ����
					clients.put(client, cInfo); //���ӵ� Ŭ���̾�Ʈ ��Ͽ� ClientInfo ����
					client.sendMessage("SUC��"+info[0]+"��"+info[1]+"��"+info[2]); //���� ���� �޽��� ����
				}
			}
			
		}
	}
	
	public void register(MySocket client, String args[]) { //ȸ������
		String id = args[1]; //id
		String pw = args[2]; //pw
		String name = args[3]; //�̸�
		String telNum = args[4]; //����ó
		String nickName = args[5]; //�г���
		String resPW = database.getPassword(id); //�������� ID�� ���� pw ������
		if(resPW != null) { //�����ͺ��̽��� ����ڰ� ���� ID���� �̹� �ִٸ�
			client.sendMessage("FAL���̹� �����ϴ� ID�Դϴ�."); //�ź� �޽����� ���� ����
		} else {
			boolean result = database.insertRegisterData(id, pw);  //�����ͺ��̽� �α��� ���� ���̺� ID,PW ����
			boolean result2 = database.insertUserInfo(id, name, telNum, nickName); //�����ͺ��̽� ����� ���� ���̺� ���� ����
			if(result && result2) { //���� ó�� 2���� ���������� ����ƴٸ�
				client.sendMessage("SUC"); //���� �޽��� ����
			} else { //���� ó�� 2���� 1���� �����ߴٸ�
				client.sendMessage("FAL�׼��� ó�� ����"); //���� �޽��� ����
			}
		}
	}
	
	public void findPassword(MySocket client, String args[]) { //�н����� ã��
		String id = args[1]; //id
		String name = args[2]; //�̸�
		String telNum = args[3]; //����ó
		String nickName = args[4]; //�г���
		
		String userInfo[] = database.getUserInfo(id); //�����ͺ��̽����� ID���� �̿��Ͽ� ����� ���� �ҷ���
		if(userInfo == null) { //�����ͺ��̽� ����� ���� ���̺� �ش� ID���� ���ٸ�
			client.sendMessage("FAL���������� �ʴ� ID�Դϴ�."); //�ź� �޽����� ���� ����
		} else if(!(name.equals(userInfo[0]) && telNum.equals(userInfo[1]) && nickName.equals(userInfo[2]))) { 
			//����ڰ� �Է��� ������ �����ͺ��̽��� ����� ������ ��ġ���� �ʴٸ�
			client.sendMessage("FAL�׵�ϵ� ������ ��ġ���� �ʽ��ϴ�.");//�ź� �޽����� ���� ����
		} else { //
			String pw = database.getPassword(id); ////�����ͺ��̽� �α��� ���� ���̺��� ID���� �̿��Ͽ� PW������
			if(pw == null) { //�����ͺ��̽� �α��� ���� ���̺� �ش� ID���� ���ٸ�
				client.sendMessage("FAL���������� �ʴ� ID�Դϴ�.");//�ź� �޽����� ���� ����
			} else {
				client.sendMessage("SUC��"+pw); //���� �޽����� PW�� ����
			}
		}
	}
	
	public void sendChatroomList(MySocket client) { //ä�ù� ���� ����
		String msg = "RLS"; //Ŭ���̾�Ʈ���� ������ ��ȣ
		for(Chatroom room : chatroomList) {  //ä�ù� ��� Ž��
			int roomId = room.roomId; //ä�ù� id
			String roomTitle = room.roomtitle; //ä�ù� ����
			String roomName = room.roomName; //ä�ù� �̸�
			String roomIntro = room.roomIntro; //ä�ù� �Ұ�
			int roomNowPlayer = room.users.size(); //ä�ù� ���� �ο�
			int roomMaxPlayer = room.roomMaxPlayer; //ä�ù� �ִ��ο�
			msg += "��"+roomId+"��"+roomTitle+"��"+roomName+"��"+roomIntro+"��"+roomNowPlayer+"��"+roomMaxPlayer; //�޽����� ��ħ
		}
		client.sendMessage(msg); //ä�ù� ��� �޽��� ����
	}

	public void editUserInfo(MySocket client, String args[]) { //����� ���� ����
		String id = clients.get(client).id; //ID
		String pw = args[1]; //PW
		String name = args[2]; //�̸�
		String telNum = args[3]; //����ó
		String nickName = args[4]; //�г���
		
		boolean result = database.insertRegisterData(id, pw); //�����ͺ��̽� �α������� ���̺� ���ο� PW ���� ����
		boolean result2 = database.insertUserInfo(id, name, telNum, nickName); //�����ͺ��̽� ����� ���� ���̺� ���ο� ����� ���� ���� 
		
		if(!result || !result2) {  //���� ó�� 2���� 1���� ���н�
			client.sendMessage("FAL��ó�� ����");
		} else { //�Ѵ� ������
			String info[] = database.getUserInfo(id); //�����ͺ��̽����� ����� ���� �ҷ��� 
			ClientInfo cInfo = new ClientInfo(client, id, info[0], info[1], info[2]); //ClientInfo �����
			clients.put(client, cInfo); //Ŭ���̾�Ʈ ��Ͽ� ������
			client.sendMessage("SUC��"); //����  �޽��� ����
		}
	}
	
	public void createChatroom(MySocket client, String args[]) { //ä�ù� ����
		String id = clients.get(client).id; //��û�� id
		String roomTitle = args[1]; //ä�ù� ����
		String roomName = args[2]; //ä�ù� �̸�
		String roomIntro = args[3]; //ä�ù� �Ұ�
		String roomMax = args[4]; //ä�ù� �ִ��ο�
		
		Chatroom room = new Chatroom(roomTitle, roomName, roomIntro, Integer.valueOf(roomMax), id); //���޵� ä�ù� ������ ���� ä�ù� ����
		chatroomList.add(room); //ä�ù� ��Ͽ� �߰�
		client.sendMessage("SUC��"+room.roomId); //���� �޽��� ����
		
		for(MySocket tmpClient : clients.keySet()) { //ä�ù��� ���Ӱ� �����Ǿ����Ƿ� Ŭ���̾�Ʈ���� ���θ޴� �����ӿ��� ä�ù��� �߰����־����
			if(tmpClient.equals(client)) continue; //ä�ù� ���� ��û�� �� Ŭ���̾�Ʈ���Դ� �����ʿ����
			if(clients.get(tmpClient) == null) continue; //�α����� ���� ���� ���� Ŭ���̾�Ʈ��� ���۾���
			tmpClient.sendMessage("CAD��"+room.roomId+"��"+room.roomtitle+"��"+room.roomName+"��"+room.roomIntro+"��"+room.users.size()+"��"+room.roomMaxPlayer);	
			 //ä�ù� �߰��� �޽��� ����
		}
	}
	
	public void joinChatroom(MySocket client, String args[]) { //ä�ù� ����
		String id = clients.get(client).id; //��û�� Ŭ���̾�Ʈ id
		int roomId = Integer.valueOf(args[1]); //������ ä�ù� id
		
		Chatroom room = getSpecChatroom(roomId); //ä�ù� id�� �̿��Ͽ� ä�ù� ��ü�� ������
		if(room == null) { //ä�ù� ��ü�� ��µ� �����ߴٸ�
			client.sendMessage("FAL���߸��� ���Դϴ�."); //�źθ޽����� ���� ����
			return;
		}
		int nowPlayer = room.users.size(); //ä�ù��� �����ο� ������ 
		if(nowPlayer >= room.roomMaxPlayer) { //ä�ù��� �����ο��� �ִ��ο��� �����ߴٸ�
			client.sendMessage("FAL�׹� �ο��� �� á���ϴ�."); //�źθ޽����� ���� ����
		} else if(room.kickedUsersId.contains(id)) { //ä�ù��� �߹�� ����� ID��Ͽ� ��û�� ID�� �ִٸ�
			client.sendMessage("FAL���߹���� �濡�� �������� �Ұ����մϴ�."); //�źθ޽����� ���� ����
		} else {
			ClientInfo userInfo = clients.get(client); //Ŭ���̾�Ʈ��Ͽ��� ��û�� ClientInfo ������
			userInfo.joinedRoom = room; //����ڰ� ���� �������� ä�ù� ����
			room.addUser(userInfo); //ä�ù濡 ����� �߰� �޼ҵ� ȣ��
			String msg = "SUC��"+room.owner+"��"+room.roomtitle+"��"+room.roomName+"��"+room.roomIntro+"��"+room.roomMaxPlayer; //ä�ù� ���� �޽�����  ��ħ
			for(ClientInfo user : room.users) { //�޽����� �Բ� ������ ä�ù濡 �������� ����� ��ϵ� ����
				msg += "��"+user.nickName+"��"+user.id;
			}
			client.sendMessage(msg); //�޽��� ����
			for(MySocket tmpClient : clients.keySet()) { //ä�ù� �ο��� �����Ǿ����Ƿ� Ŭ���̾�Ʈ���� ���θ޴� �����ӿ��� �ش� ä�ù��� �������־����
				if(tmpClient.equals(client)) continue; //���� ��û�ڿ��Դ� ������ �ʿ����
				sendSpecRoomInfo(tmpClient, roomId); //������ ä�ù� ���� ����
			}
		}
	}
	
	
	public void quitRoom(MySocket client) { //ä�ù� ����
		ClientInfo cInfo = clients.get(client); //Ŭ���̾�Ʈ��Ͽ��� ��û�� ClientInfo ������
		if(cInfo == null) return; 
		Chatroom room = cInfo.joinedRoom; //������ ä�ù� ������
		if(room == null) return;
		room.removeUser(cInfo); //ä�ù��� ���� ó�� �޼ҵ� ȣ��
		if(room.users.size() <= 0) { //�� ä�ù��� �����ο��� 0���� �ƴٸ�
			delChatroom(room.roomId); //ä�ù� ���� �޼ҵ� ȣ��
		} else {
			for(MySocket tmpClient : clients.keySet()) { //ä�ù� �ο��� �����Ǿ����Ƿ� Ŭ���̾�Ʈ���� ���θ޴� �����ӿ��� �ش� ä�ù��� �������־����
				if(clients.get(tmpClient) == null) continue;
				sendSpecRoomInfo(tmpClient, room.roomId);
			}
		}	
		cInfo.joinedRoom = null;
	}
	
	private void delChatroom(int roomId) { //ä�ù� ����ó��
		
		Chatroom delRoom = getSpecChatroom(roomId); //������ ä�ù� ID�� �̿��Ͽ� ä�ù� ��ü ������
		
		if(delRoom == null) return;
		
		chatroomList.remove(delRoom); //ä�ù� ��Ͽ��� ����
		
		for(MySocket tmpClient : clients.keySet()) { //ä�ù��� �����Ǿ����Ƿ� Ŭ���̾�Ʈ���� ���θ޴� �����ӿ��� �ش� ä�ù��� �������־����
			if(clients.get(tmpClient) == null) continue;
			tmpClient.sendMessage("CDE��"+delRoom.roomId);
		}
	}
	
	public void sendSpecRoomInfo(MySocket client, int roomId) { //client���� ä�ù� ID���� �̿��Ͽ� ä�ù� ���� ����
		Chatroom room = getSpecChatroom(roomId); //ä�ù� ID�� �̿��Ͽ� ä�ù� ��ü ������
		if(room != null) {
			client.sendMessage("CPC��"+room.roomId+"��"+room.roomtitle+"��"+room.roomName+"��"+room.roomIntro+"��"+room.users.size()+"��"+room.roomMaxPlayer);
			//������ �޽����� ���� ����
		}
	}
	
	public void sendChatmsg(MySocket client, String args[]) { //ä�� �޽��� ����
		if(args.length > 1) { 
			ClientInfo userInfo = clients.get(client); //��û�� ����� ���� ������
			String chatmsg = args[1]; //ä�ø޽���
			userInfo.joinedRoom.sendChat(userInfo.nickName, chatmsg); //��û�� ����ڰ� �������� ä�ù��� ä������ �޼ҵ� ȣ��
		}
	}
	
	public void roomOwnerChange(MySocket client, String args[]) { //���� ����
		int roomId = Integer.valueOf(args[1]); //��� ä�ù� ID
		String callerId = args[2]; //��û�� ID
		String target = args[3]; //����� ID
		
		Chatroom room = getSpecChatroom(roomId); //ä�ù� ID�� �̿��Ͽ� ä�ù� ��ü�� ������
		if(room != null) {
			room.changeOwner(callerId, target); //�ش� ä�ù��� ���� ���� �޼ҵ� ȣ��	
		}
	}
	
	public void roomUserKick(MySocket client, String args[]) { //���� ����
		int roomId = Integer.valueOf(args[1]); //ä�ù� ID
		String callerId = args[2]; //��û��
		String target = args[3]; //�����
		
		Chatroom room = getSpecChatroom(roomId); //ä�ù� ID�� �̿��Ͽ� ä�ù� ��ü�� ������
		if(room != null) {
			room.KickUser(callerId, target); //�ش� ä�ù��� �������� �޼ҵ� ȣ��
			if(room.users.size() <= 0) { //��.��. ä�ù� �����ο��� 0���� �ƴٸ�
				delChatroom(room.roomId); //ä�ù� ���� �޼ҵ� ȣ��
			} 	
			for(MySocket tmpClient : clients.keySet()) {  //ä�ù��� �����Ǿ����Ƿ� Ŭ���̾�Ʈ���� ���θ޴� �����ӿ��� �ش� ä�ù��� �������־����
				if(clients.get(tmpClient) == null) continue;
				sendSpecRoomInfo(tmpClient, room.roomId);
			}
		}			
	}
	
	public void editRoomInfo(MySocket client, String args[]) { //�� ���� ����
		int roomId = Integer.valueOf(args[1]); //ä�ù� ID
		String callerId = args[2]; //��û��
		String newTitle = args[3]; //���ο� ������
		String newName = args[4]; //���ο� ����
		String newIntro = args[5]; //���ο� �Ұ���
		
		Chatroom room = getSpecChatroom(roomId); //ä�ù� ID�� �̿��Ͽ� ä�ù� ��ü�� ������
		if(room != null) {
			room.editRoomInfo(callerId, newTitle, newName, newIntro); //�ش� ä�ù��� �������� �޼ҵ� ȣ��
			for(MySocket tmpClient : clients.keySet()) { //ä�ù� ������ �����Ǿ����Ƿ� Ŭ���̾�Ʈ���� ���θ޴� �����ӿ��� �ش� ä�ù��� �������־����
				if(clients.get(tmpClient) == null) continue;
				sendSpecRoomInfo(tmpClient, room.roomId);
			}
		}
	}
	
	public void sendDumpMsg(MySocket client, String args[]) { //���� �޽��� ���޿�
		client.sendMessage("DMP"); //�����޽��� ������ �����޽��� ������
	}
	
	private Chatroom getSpecChatroom(int roomId) { //�� ID�� Ư�� ä�ù� ���
		for(Chatroom room : chatroomList) { //ä�ù� Ž��
			if(room.roomId == roomId) //ä�ù� ID�� ��ûID�� ���ٸ�
				return room; //��ü ��ȯ
		}
		return null;
	}
	
	/////////������
	/*private class InputCmd extends Thread{ 
				
		public void run() {
			while(true) {
				Scanner scanner = new Scanner(System.in);
				String cmd = scanner.next();
				if(cmd.equalsIgnoreCase("test1")) {
					System.out.println("did test1");
					String answer = server.database.getPassword("tester");
					System.out.println(answer);
				} else if(cmd.equalsIgnoreCase("test2")) {
					System.out.println("did test2");
					server.database.insertRegisterData("tester2", "456789");
				}else if(cmd.equalsIgnoreCase("test3")) {
					System.out.println("did test3");
					String args[] = server.database.getUserInfo("tester1");
					System.out.println(args[0]+args[1]+args[2]);
				}else if(cmd.equalsIgnoreCase("test4")) {
					System.out.println("did test4");
					server.database.insertUserInfo("tester1", "�������׽���", "9876543", "�������׽���");
				}else if(cmd.equalsIgnoreCase("test5")) {
					System.out.println("did test5");
					server.database.insertUserInfo("tester2", "�������׽���2", "9876543", "�������׽���2");
				}else if(cmd.equalsIgnoreCase("test6")) {
					System.out.println("did test6");
					List<MySocket> list = new ArrayList<MySocket>(clients.keySet());
					delChatroom(3);
				}else if(cmd.equalsIgnoreCase("test7")) {
					System.out.println("did test7");
					List<MySocket> list = new ArrayList<MySocket>(clients.keySet());
					MySocket client = (MySocket) list.get(0);
					getSpecChatroom(4).roomName = "���扳";
					sendSpecRoomInfo(client, 4);
				}else if(cmd.equalsIgnoreCase("test8")) {
					System.out.println("did test8");
					List<MySocket> list = new ArrayList<MySocket>(clients.keySet());
					delChatroom(1);
				}else if(cmd.equalsIgnoreCase("test9")) {
					System.out.println("did test9");
					List<MySocket> list = new ArrayList<MySocket>(clients.keySet());
					delChatroom(2);
				}
			}
		}
		
	}*/
}

