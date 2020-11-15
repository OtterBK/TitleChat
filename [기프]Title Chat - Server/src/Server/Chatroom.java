package Server;

import java.util.ArrayList;
import java.util.List;

public class Chatroom { //채팅방
	
	static int nowRoomId = 1; //채팅방 ID 설정용
	
	int roomId; //채팅방 id
	String roomtitle; //채팅방 주제
	String roomName; //채팅방 이름
	String roomIntro; //채팅방 소개
	int roomMaxPlayer; //채팅방 최대인원
	String owner; //채팅방 방장
	List<String> kickedUsersId = new ArrayList<String>(); //추방된 사용자 ID목록
	List<ClientInfo> users = new ArrayList<ClientInfo>(); //해당 채팅방에 있는 사용자 목록
	
	public Chatroom(String title, String roomName, String roomIntro, int roomMax, String owner) { //채팅방 생성 및 초기화
		this.roomId = Chatroom.nowRoomId;
		this.roomtitle = title;
		this.roomName = roomName;
		this.roomIntro = roomIntro;
		this.roomMaxPlayer = roomMax;
		this.owner = owner;
		
		Chatroom.nowRoomId += 1; //채팅방 id설정
	}
	
	public void addUser(ClientInfo addUser) { //채팅방에 사용자 추가
		for(int i = 0; i < users.size(); i++) { //해당 채팅방에 접속되어있는 사용자들에게 입장 알림 출력하라는 메시지 전달
			ClientInfo user = users.get(i);
			user.client.sendMessage("RAM§"+addUser.nickName+" 님이 입장하셨습니다.");
		}
		users.add(addUser); //사용자 추가
		
		//채팅방 정보(인원이) 수정되었다는 메시지 전달용
		String msg = "CRI§"+roomId+"§"+roomtitle+"§"+roomName+"§"+roomIntro+"§"+owner+"§";	
		for(ClientInfo user : users) { //해당 채팅방에 있는 사용자 목록들
			msg += "¿"+user.nickName+"§"+user.id;
		}
		for(int i = 0; i < users.size(); i++) { //수정 메시지 전달
			ClientInfo user = users.get(i);
			if(user == addUser) continue;
			user.client.sendMessage(msg);
		}
	}
	
	public void removeUser(ClientInfo removeUser) { //사용자 퇴장
		users.remove(removeUser); //사용자 목록에서 퇴장한 사용자 삭제
		for(int i = 0; i < users.size(); i++) {// 해당 채팅방에 접속되어있는 사용자들에게 퇴장 알림 출력하라는 메시지 전달
			ClientInfo user = users.get(i);
			user.client.sendMessage("RAM§"+removeUser.nickName+" 님이 퇴장하셨습니다.");
		}
		if(users.size() >= 1) { //만약 채팅방에 1명이라도 남아있으면
			if(removeUser.id.equals(owner)) { //퇴장한 사용자가 방장이라면
				//랜덤으로 1명 지정하여 방장 위임
				int ri = (int)(Math.random() * ((users.size()-1) + 1));
				ClientInfo cInfo = users.get(ri);
				owner = cInfo.id;
				for(int i = 0; i < users.size(); i++) {
					ClientInfo user = users.get(i);
					user.client.sendMessage("RAM§"+cInfo.nickName+" 님이 방장이 되었습니다.");
				}
			}
		}
		sendChangedRoomInfo(); //변경된 방 정보 전달 메소드 호출
	}
	
	public void changeOwner(String callerId, String target) { //방장 변경
		if(owner.equals(callerId)) { //만약 이 기능을 요청한 사용자ID가 서버의 해당 채팅방의 방장 ID와 일치하다면
			ClientInfo targetUser = null; //위임할 대상의 사용자 정보 탐색
			for(ClientInfo cInfo : users) {
				if(cInfo.id.equals(target)) {
					targetUser = cInfo;
					break;
				}
			}
			if(targetUser == null) return; //위임할 대상이 이 채팅방에 없다면 리턴 
			owner = target; //있다면 방장으로 설정
			for (int i = 0; i < users.size(); i++) { //이 채팅방 사용자들에게 방장 변경 알림 메시지 전달
				ClientInfo user = users.get(i);
				user.client.sendMessage("RAM§" + targetUser.nickName + " 님이 방장이 되었습니다.");
			}
		}
		sendChangedRoomInfo();//변경된 방 정보 전달 메소드 호출
	}
	
	public void KickUser(String callerId, String target) { //강제 퇴장
		if(owner.equals(callerId)) { //만약 이 기능을 요청한 사용자ID가 서버의 해당 채팅방의 방장 ID와 일치하다면
			ClientInfo kickUser = null; //강퇴할 대상의 사용자 정보 탐색
			for(ClientInfo cInfo : users) {
				if(cInfo.id.equals(target)) {
					kickUser = cInfo;
					break;
				}
			}
			if(kickUser == null) return; //강퇴할 대상이 이 채팅방에 없다면 리턴 
			
			users.remove(kickUser); //강퇴 처리
			kickUser.client.sendMessage("CKR§"); //강퇴 대상에게 강퇴됨 메시지 전달
			kickedUsersId.add(target); //추방된 사용자 ID목록에 강퇴 대상의 ID추가
			for(int i = 0; i < users.size(); i++) { //이 채팅방 사용자들에게 강퇴 알림 메시지 저ㅓㄴ달
				ClientInfo user = users.get(i);
				user.client.sendMessage("RAM§"+kickUser.nickName+" 님이 강제퇴장 되셨습니다.");
			}
		}
		sendChangedRoomInfo();//변경된 방 정보 전달 메소드 호출
	}
	
	public void editRoomInfo(String callerId, String newTitle, String newName, String newIntro) { //방 정보 수정
		if(owner.equals(callerId)) {//만약 이 기능을 요청한 사용자ID가 서버의 해당 채팅방의 방장 ID와 일치하다면
			this.roomtitle = newTitle; //방 정보 수정
			this.roomName = newName;
			this.roomIntro = newIntro;
			sendChangedRoomInfo(); //변경된 방 정보 전달 메소드 호출
		}
	}
	
	public void sendChangedRoomInfo() { //변경된 방 정보를 전달하는 메소드임
		String msg = "CRI§"+roomId+"§"+roomtitle+"§"+roomName+"§"+roomIntro+"§"+owner+"§";	//방 정보 메시지로 만듬
		for(ClientInfo user : users) {//사용자 목록을 방 정보 메시지에 추가
			msg += "¿"+user.nickName+"§"+user.id; 
		}
		for(int i = 0; i < users.size(); i++) { //이 채팅방 사용자들에게 전달
			ClientInfo user = users.get(i);
			user.client.sendMessage(msg);
		}
	}
	
	public void sendChat(String nickName, String msg) { //채팅 메시지 전달 메소드
		for(int i = 0; i < users.size(); i++) { //이 채팅방 사용자들에게 채팅 메시지 전달
			ClientInfo user = users.get(i);
			user.client.sendMessage("RCM§"+nickName+"§"+msg);
		}
	}
}
