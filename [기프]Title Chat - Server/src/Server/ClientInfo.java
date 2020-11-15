package Server;

public class ClientInfo { //사용자 정보를 저장하기 위한 클래스

	String id; //사용자 ID
	String name; //사용자 이름
	String telNum; //사용자 연락처 
	String nickName; //사용자 닉네임
	MySocket client; //클라이언트의 소켓
	Chatroom joinedRoom; //접속중인 채팅방
	
	public ClientInfo(MySocket client, String id, String name, String telNum, String nickName) { //생성 및 초기화
		this.client = client;
		this.id = id;
		this.name = name;
		this.telNum = telNum;
		this.nickName = nickName;
	}
	
}
