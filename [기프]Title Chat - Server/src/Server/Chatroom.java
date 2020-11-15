package Server;

import java.util.ArrayList;
import java.util.List;

public class Chatroom { //ä�ù�
	
	static int nowRoomId = 1; //ä�ù� ID ������
	
	int roomId; //ä�ù� id
	String roomtitle; //ä�ù� ����
	String roomName; //ä�ù� �̸�
	String roomIntro; //ä�ù� �Ұ�
	int roomMaxPlayer; //ä�ù� �ִ��ο�
	String owner; //ä�ù� ����
	List<String> kickedUsersId = new ArrayList<String>(); //�߹�� ����� ID���
	List<ClientInfo> users = new ArrayList<ClientInfo>(); //�ش� ä�ù濡 �ִ� ����� ���
	
	public Chatroom(String title, String roomName, String roomIntro, int roomMax, String owner) { //ä�ù� ���� �� �ʱ�ȭ
		this.roomId = Chatroom.nowRoomId;
		this.roomtitle = title;
		this.roomName = roomName;
		this.roomIntro = roomIntro;
		this.roomMaxPlayer = roomMax;
		this.owner = owner;
		
		Chatroom.nowRoomId += 1; //ä�ù� id����
	}
	
	public void addUser(ClientInfo addUser) { //ä�ù濡 ����� �߰�
		for(int i = 0; i < users.size(); i++) { //�ش� ä�ù濡 ���ӵǾ��ִ� ����ڵ鿡�� ���� �˸� ����϶�� �޽��� ����
			ClientInfo user = users.get(i);
			user.client.sendMessage("RAM��"+addUser.nickName+" ���� �����ϼ̽��ϴ�.");
		}
		users.add(addUser); //����� �߰�
		
		//ä�ù� ����(�ο���) �����Ǿ��ٴ� �޽��� ���޿�
		String msg = "CRI��"+roomId+"��"+roomtitle+"��"+roomName+"��"+roomIntro+"��"+owner+"��";	
		for(ClientInfo user : users) { //�ش� ä�ù濡 �ִ� ����� ��ϵ�
			msg += "��"+user.nickName+"��"+user.id;
		}
		for(int i = 0; i < users.size(); i++) { //���� �޽��� ����
			ClientInfo user = users.get(i);
			if(user == addUser) continue;
			user.client.sendMessage(msg);
		}
	}
	
	public void removeUser(ClientInfo removeUser) { //����� ����
		users.remove(removeUser); //����� ��Ͽ��� ������ ����� ����
		for(int i = 0; i < users.size(); i++) {// �ش� ä�ù濡 ���ӵǾ��ִ� ����ڵ鿡�� ���� �˸� ����϶�� �޽��� ����
			ClientInfo user = users.get(i);
			user.client.sendMessage("RAM��"+removeUser.nickName+" ���� �����ϼ̽��ϴ�.");
		}
		if(users.size() >= 1) { //���� ä�ù濡 1���̶� ����������
			if(removeUser.id.equals(owner)) { //������ ����ڰ� �����̶��
				//�������� 1�� �����Ͽ� ���� ����
				int ri = (int)(Math.random() * ((users.size()-1) + 1));
				ClientInfo cInfo = users.get(ri);
				owner = cInfo.id;
				for(int i = 0; i < users.size(); i++) {
					ClientInfo user = users.get(i);
					user.client.sendMessage("RAM��"+cInfo.nickName+" ���� ������ �Ǿ����ϴ�.");
				}
			}
		}
		sendChangedRoomInfo(); //����� �� ���� ���� �޼ҵ� ȣ��
	}
	
	public void changeOwner(String callerId, String target) { //���� ����
		if(owner.equals(callerId)) { //���� �� ����� ��û�� �����ID�� ������ �ش� ä�ù��� ���� ID�� ��ġ�ϴٸ�
			ClientInfo targetUser = null; //������ ����� ����� ���� Ž��
			for(ClientInfo cInfo : users) {
				if(cInfo.id.equals(target)) {
					targetUser = cInfo;
					break;
				}
			}
			if(targetUser == null) return; //������ ����� �� ä�ù濡 ���ٸ� ���� 
			owner = target; //�ִٸ� �������� ����
			for (int i = 0; i < users.size(); i++) { //�� ä�ù� ����ڵ鿡�� ���� ���� �˸� �޽��� ����
				ClientInfo user = users.get(i);
				user.client.sendMessage("RAM��" + targetUser.nickName + " ���� ������ �Ǿ����ϴ�.");
			}
		}
		sendChangedRoomInfo();//����� �� ���� ���� �޼ҵ� ȣ��
	}
	
	public void KickUser(String callerId, String target) { //���� ����
		if(owner.equals(callerId)) { //���� �� ����� ��û�� �����ID�� ������ �ش� ä�ù��� ���� ID�� ��ġ�ϴٸ�
			ClientInfo kickUser = null; //������ ����� ����� ���� Ž��
			for(ClientInfo cInfo : users) {
				if(cInfo.id.equals(target)) {
					kickUser = cInfo;
					break;
				}
			}
			if(kickUser == null) return; //������ ����� �� ä�ù濡 ���ٸ� ���� 
			
			users.remove(kickUser); //���� ó��
			kickUser.client.sendMessage("CKR��"); //���� ��󿡰� ����� �޽��� ����
			kickedUsersId.add(target); //�߹�� ����� ID��Ͽ� ���� ����� ID�߰�
			for(int i = 0; i < users.size(); i++) { //�� ä�ù� ����ڵ鿡�� ���� �˸� �޽��� ���ä���
				ClientInfo user = users.get(i);
				user.client.sendMessage("RAM��"+kickUser.nickName+" ���� �������� �Ǽ̽��ϴ�.");
			}
		}
		sendChangedRoomInfo();//����� �� ���� ���� �޼ҵ� ȣ��
	}
	
	public void editRoomInfo(String callerId, String newTitle, String newName, String newIntro) { //�� ���� ����
		if(owner.equals(callerId)) {//���� �� ����� ��û�� �����ID�� ������ �ش� ä�ù��� ���� ID�� ��ġ�ϴٸ�
			this.roomtitle = newTitle; //�� ���� ����
			this.roomName = newName;
			this.roomIntro = newIntro;
			sendChangedRoomInfo(); //����� �� ���� ���� �޼ҵ� ȣ��
		}
	}
	
	public void sendChangedRoomInfo() { //����� �� ������ �����ϴ� �޼ҵ���
		String msg = "CRI��"+roomId+"��"+roomtitle+"��"+roomName+"��"+roomIntro+"��"+owner+"��";	//�� ���� �޽����� ����
		for(ClientInfo user : users) {//����� ����� �� ���� �޽����� �߰�
			msg += "��"+user.nickName+"��"+user.id; 
		}
		for(int i = 0; i < users.size(); i++) { //�� ä�ù� ����ڵ鿡�� ����
			ClientInfo user = users.get(i);
			user.client.sendMessage(msg);
		}
	}
	
	public void sendChat(String nickName, String msg) { //ä�� �޽��� ���� �޼ҵ�
		for(int i = 0; i < users.size(); i++) { //�� ä�ù� ����ڵ鿡�� ä�� �޽��� ����
			ClientInfo user = users.get(i);
			user.client.sendMessage("RCM��"+nickName+"��"+msg);
		}
	}
}
