package Server;

public class ClientInfo { //����� ������ �����ϱ� ���� Ŭ����

	String id; //����� ID
	String name; //����� �̸�
	String telNum; //����� ����ó 
	String nickName; //����� �г���
	MySocket client; //Ŭ���̾�Ʈ�� ����
	Chatroom joinedRoom; //�������� ä�ù�
	
	public ClientInfo(MySocket client, String id, String name, String telNum, String nickName) { //���� �� �ʱ�ȭ
		this.client = client;
		this.id = id;
		this.name = name;
		this.telNum = telNum;
		this.nickName = nickName;
	}
	
}
