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
	
	//작성자 2016244009 전재욱
	//프로그램 명 : Title Chat - Server
	//사용자 자신이 원하는 주제를 선택하여 채팅방을 생성하거나 참여하여 채팅하는 프로그램
	//버젼 1.0.0
	//동일한 IP에서 클라이언트 2개 실행을 원할 시 90~94 라인 주석처리시 가능
	
	ServerSocket ss = null; //서버 소켓
	HashMap<MySocket, ClientInfo> clients = new HashMap<MySocket, ClientInfo>(); //연결된 클라이언트 목록
	List<Chatroom> chatroomList = new ArrayList<Chatroom>(); //채팅방 목록
	Server server; //서버
	MyDatabase database; //데이터베이스 통신용 클래스
	
	////GUI관련
	JFrame frame;
	JScrollPane sc;
	JTextArea logArea;
	int frameWidth = 500;
	int frameHeight = 700;
	
	public Server() {
			server = this;
			database = new MyDatabase();
			try {
				server.ss = new ServerSocket(7000);     //서버 소켓 생성
				//InputCmd inputCmd = new InputCmd(); 디버깅용
				//inputCmd.start();
				
				//////////프레임
				Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
				frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout(5,5));
				frame.setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth/2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
				frame.setTitle("Title Chat Log Manager"); //프레임 제목 설정
				frame.setResizable(true); //프레임 크기 조정 가능
				frame.setBackground(Color.LIGHT_GRAY); //배경색 설정
				frame.add(new JPanel(), BorderLayout.NORTH); //깔끔한 GUI를 위해 추가
				frame.add(new JPanel(), BorderLayout.SOUTH); //깔끔한 GUI를 위해 추가
				frame.add(new JPanel(), BorderLayout.EAST); //깔끔한 GUI를 위해 추가
				frame.add(new JPanel(), BorderLayout.WEST); //깔끔한 GUI를 위해 추가
				//this.setUndecorated(true); 윈도우 틀 없애기용, 사용안함
			
				logArea = new JTextArea();
				logArea.setFont(logArea.getFont().deriveFont(15f));
				logArea.setAutoscrolls(true);
				logArea.setEditable(false);
				logArea.setLineWrap(true);   //꽉차면 다음줄로 가게 해줌.
				sc = new JScrollPane(logArea);
				sc.setAutoscrolls(true);
				frame.add(sc, BorderLayout.CENTER);
				
				URL src = Server.class.getResource("./resources/baricon.png"); //아이콘 설정
				ImageIcon icon = new ImageIcon(src);
				frame.setIconImage(icon.getImage());
				
				frame.setVisible(true); //프레임 표시
				
				printLog("Server> 서버 가동 완료");
				///////////////////
							
				while(true) {   //클라이언트 접속 신청 대기 및 허용
					Socket socket = server.ss.accept();       //소켓생성
					MySocket s = new MySocket(socket, this); //커스텀 통신 소켓 생성
					boolean exist = false; //이미 접속중인지 확인여부
					for(MySocket client : clients.keySet()) { //접속된 클라이언트 목록에서 탐색
						//접속된 클라이언트 목록중에 새로 접속한 클라이언트의 IP와 동일한 값이 있다면
						if(client.socket.getInetAddress().toString().equals(socket.getInetAddress().toString())) {
							s.sendMessage("FAL§이미 동일한 IP에서 접속되어 있습니다."); //거부 메시지 전달
							exist = true; //접속  거부설정
							break;
						}						
					}
					if(!exist) { //접속이 거부되지 않았다면
						server.clients.put(s, null); //클라이언트 목록에 추가
						s.start(); //해당  클라이언트와 통신 시작
					}		
				}
			} catch(SocketException e){
				System.out.println("Server> 서버 종료 - 통신 예외 발생");
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void printLog(String str) { //로그 GUI에 로그출력
		SimpleDateFormat format1 = new SimpleDateFormat ( "[yy년MM월dd일 HH:mm:ss] "); //시간
		String format_time1 = format1.format (System.currentTimeMillis());
		logArea.append(format_time1+str+"\n"); //출력
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}
	
	public static void main(String args[]) {
		Server server = new Server(); //서버 실행
	}
	
	public void analysisMessage(MySocket client, String msg) { //신호 분석
		try {//혹시 모르니 try로
			String args[] = msg.split("§"); //메시지 파싱
			String signal = args[0]; //신호

			switch(signal) { //신호에 따라 처리
			
			case "LGI": //로그인
				server.login(client, args);
				break;
			case "RGS": //회원가입
				server.register(client, args);
				break;
			case "FPW": //비밀번호 찾기
				server.findPassword(client, args);
				break;
			case "GCL": //채팅방 목록 요청
				server.sendChatroomList(client);
				break;
			case "EUI": //사용자 정보 수정
				server.editUserInfo(client, args);
				break;
			case "CTR": //채팅방 생성
				server.createChatroom(client, args);
				break;
			case "JIR": //채팅방 입장
				server.joinChatroom(client, args);
				break;
			case "QTR": //채팅방 퇴장
				server.quitRoom(client);
				break;
			case "SCM": //채팅 메시지 전달
				server.sendChatmsg(client, args);
				break;
			case "ROC": //채팅방 방장 변경
				server.roomOwnerChange(client, args);
				break;
			case "RUK": //채팅방에서 특정 사용자 강제퇴장 요청
				server.roomUserKick(client, args);
				break;
			case "EDR": //채팅방 정보 수정
				server.editRoomInfo(client, args);
				break;
			case "DMP": //덤프메시지
				server.sendDumpMsg(client, args);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void login(MySocket client, String args[]) { //로그인
		String id = args[1]; //id
		String pw = args[2]; //pw
		String resPW = database.getPassword(id); //데이터베이스에서 id를 이용하여 pw를 가져옴
		if(resPW == null) { //데이터베이스에 해당 id가 없다면
			client.sendMessage("FAL§존재하지 않는 ID입니다."); //실패 메시지와 이유 전달
		} else { 
			if(!pw.equals(resPW)) { //데이터베이스의 pw값과 사용자가 보낸 pw값이 일치하지 않다면 
				client.sendMessage("FAL§비밀번호가 일치하지 않습니다."); //실패메시지와 이유 전달
			} else { 
				if(clients.size() > 0) { //서버에 접속한 클라이언트가 1개라도 있다면
					for(MySocket tmpClient : clients.keySet()) { //클라이언트 탐색
						ClientInfo cInfo = clients.get(tmpClient); 
						if(cInfo == null) continue;
						if(cInfo.id.equals(id)) { //접속되어있는 클라이언트의 ID값과 사용자가 보낸 ID값이 일치하는 것이 있다면
							client.sendMessage("FAL§이미 동일한 ID가 서버에   접속되어있습니다."); //거부 메시지와 이유 전달
							return; //로그인 실패
						}
					}
				}	
				String info[] = database.getUserInfo(id); //데이터베이스에서 id값을 이용하여 사용자 정보 불러옴
				if(info == null) { //사용자 정보가 데이터베이스에 없다면
					client.sendMessage("FAL§사용자 정보를 불러오는데 실패했습니다."); //거부 메시지와 이유 전달
				} else {
					ClientInfo cInfo = new ClientInfo(client, id, info[0], info[1], info[2]); //사용자 정보를 토대로 ClientInfo 클래스 생성
					clients.put(client, cInfo); //접속된 클라이언트 목록에 ClientInfo 삽입
					client.sendMessage("SUC§"+info[0]+"§"+info[1]+"§"+info[2]); //접속 성공 메시지 전달
				}
			}
			
		}
	}
	
	public void register(MySocket client, String args[]) { //회원가입
		String id = args[1]; //id
		String pw = args[2]; //pw
		String name = args[3]; //이름
		String telNum = args[4]; //연락처
		String nickName = args[5]; //닉네임
		String resPW = database.getPassword(id); //서버에서 ID를 토대로 pw 가져옴
		if(resPW != null) { //데이터베이스에 사용자가 보낸 ID값이 이미 있다면
			client.sendMessage("FAL§이미 존재하는 ID입니다."); //거부 메시지와 이유 전달
		} else {
			boolean result = database.insertRegisterData(id, pw);  //데이터베이스 로그인 정보 테이블에 ID,PW 삽입
			boolean result2 = database.insertUserInfo(id, name, telNum, nickName); //데이터베이스 사용자 정보 테이블에 정보 삽입
			if(result && result2) { //삽입 처리 2개가 정상적으로 실행됐다면
				client.sendMessage("SUC"); //성공 메시지 전달
			} else { //삽입 처리 2개중 1개라도 실패했다면
				client.sendMessage("FAL§서버 처리 실패"); //실패 메시지 전달
			}
		}
	}
	
	public void findPassword(MySocket client, String args[]) { //패스워드 찾기
		String id = args[1]; //id
		String name = args[2]; //이름
		String telNum = args[3]; //연락처
		String nickName = args[4]; //닉네임
		
		String userInfo[] = database.getUserInfo(id); //데이터베이스에서 ID값을 이용하여 사용자 정보 불러옴
		if(userInfo == null) { //데이터베이스 사용자 정보 테이블에 해당 ID값이 없다면
			client.sendMessage("FAL§존재하지 않는 ID입니다."); //거부 메시지와 이유 전달
		} else if(!(name.equals(userInfo[0]) && telNum.equals(userInfo[1]) && nickName.equals(userInfo[2]))) { 
			//사용자가 입력한 정보와 데이터베이스의 사용자 정보가 일치하지 않다면
			client.sendMessage("FAL§등록된 정보와 일치하지 않습니다.");//거부 메시지와 이유 전달
		} else { //
			String pw = database.getPassword(id); ////데이터베이스 로그인 정보 테이블에서 ID값을 이용하여 PW가져옴
			if(pw == null) { //데이터베이스 로그인 정보 테이블에 해당 ID값이 없다면
				client.sendMessage("FAL§존재하지 않는 ID입니다.");//거부 메시지와 이유 전달
			} else {
				client.sendMessage("SUC§"+pw); //성공 메시지와 PW값 전달
			}
		}
	}
	
	public void sendChatroomList(MySocket client) { //채팅방 정보 전달
		String msg = "RLS"; //클라이언트에서 수신할 신호
		for(Chatroom room : chatroomList) {  //채팅방 목록 탐색
			int roomId = room.roomId; //채팅방 id
			String roomTitle = room.roomtitle; //채팅방 주제
			String roomName = room.roomName; //채팅방 이름
			String roomIntro = room.roomIntro; //채팅방 소개
			int roomNowPlayer = room.users.size(); //채팅방 현재 인원
			int roomMaxPlayer = room.roomMaxPlayer; //채팅방 최대인원
			msg += "¿"+roomId+"§"+roomTitle+"§"+roomName+"§"+roomIntro+"§"+roomNowPlayer+"§"+roomMaxPlayer; //메시지로 합침
		}
		client.sendMessage(msg); //채팅방 목록 메시지 전송
	}

	public void editUserInfo(MySocket client, String args[]) { //사용자 정보 수정
		String id = clients.get(client).id; //ID
		String pw = args[1]; //PW
		String name = args[2]; //이름
		String telNum = args[3]; //연락처
		String nickName = args[4]; //닉네임
		
		boolean result = database.insertRegisterData(id, pw); //데이터베이스 로그인정보 테이블에 새로운 PW 정보 저장
		boolean result2 = database.insertUserInfo(id, name, telNum, nickName); //데이터베이스 사용자 정보 테이블에 새로운 사용자 정보 저장 
		
		if(!result || !result2) {  //삽입 처리 2개중 1개라도 실패시
			client.sendMessage("FAL§처리 실패");
		} else { //둘다 성공시
			String info[] = database.getUserInfo(id); //데이터베이스에서 사용자 정보 불러옴 
			ClientInfo cInfo = new ClientInfo(client, id, info[0], info[1], info[2]); //ClientInfo 재생성
			clients.put(client, cInfo); //클라이언트 목록에 재저장
			client.sendMessage("SUC§"); //성공  메시지 전달
		}
	}
	
	public void createChatroom(MySocket client, String args[]) { //채팅방 생성
		String id = clients.get(client).id; //요청자 id
		String roomTitle = args[1]; //채팅방 주제
		String roomName = args[2]; //채팅방 이름
		String roomIntro = args[3]; //채팅방 소개
		String roomMax = args[4]; //채팅방 최대인원
		
		Chatroom room = new Chatroom(roomTitle, roomName, roomIntro, Integer.valueOf(roomMax), id); //전달된 채팅방 정보를 토대로 채팅방 생성
		chatroomList.add(room); //채팅방 목록에 추가
		client.sendMessage("SUC§"+room.roomId); //성공 메시지 전달
		
		for(MySocket tmpClient : clients.keySet()) { //채팅방이 새롭게 생성되었으므로 클라이언트들의 메인메뉴 프레임에서 채팅방을 추가해주어야함
			if(tmpClient.equals(client)) continue; //채팅방 개설 요청을 한 클라이언트에게는 보낼필요없음
			if(clients.get(tmpClient) == null) continue; //로그인을 아직 하지 않은 클라이언트라면 전송안함
			tmpClient.sendMessage("CAD§"+room.roomId+"§"+room.roomtitle+"§"+room.roomName+"§"+room.roomIntro+"§"+room.users.size()+"§"+room.roomMaxPlayer);	
			 //채팅방 추가됨 메시지 전달
		}
	}
	
	public void joinChatroom(MySocket client, String args[]) { //채팅방 입장
		String id = clients.get(client).id; //요청한 클라이언트 id
		int roomId = Integer.valueOf(args[1]); //입장할 채팅방 id
		
		Chatroom room = getSpecChatroom(roomId); //채팅방 id를 이용하여 채팅방 객체를 가져옴
		if(room == null) { //채팅방 객체를 얻는데 실패했다면
			client.sendMessage("FAL§잘못된 방입니다."); //거부메시지와 이유 전달
			return;
		}
		int nowPlayer = room.users.size(); //채팅방의 현재인원 가져옴 
		if(nowPlayer >= room.roomMaxPlayer) { //채팅방의 현재인원이 최대인원에 도달했다면
			client.sendMessage("FAL§방 인원이 다 찼습니다."); //거부메시지와 이유 전달
		} else if(room.kickedUsersId.contains(id)) { //채팅방의 추방된 사용자 ID목록에 요청자 ID가 있다면
			client.sendMessage("FAL§추방당한 방에는 재입장이 불가능합니다."); //거부메시지와 이유 전달
		} else {
			ClientInfo userInfo = clients.get(client); //클라이언트목록에서 요청자 ClientInfo 가져옴
			userInfo.joinedRoom = room; //사용자가 현재 접속중인 채팅방 설정
			room.addUser(userInfo); //채팅방에 사용자 추가 메소드 호출
			String msg = "SUC§"+room.owner+"§"+room.roomtitle+"§"+room.roomName+"§"+room.roomIntro+"§"+room.roomMaxPlayer; //채팅방 정보 메시지로  합침
			for(ClientInfo user : room.users) { //메시지와 함께 입장한 채팅방에 접속중인 사용자 목록도 전달
				msg += "¿"+user.nickName+"§"+user.id;
			}
			client.sendMessage(msg); //메시지 전달
			for(MySocket tmpClient : clients.keySet()) { //채팅방 인원이 수정되었으므로 클라이언트들의 메인메뉴 프레임에서 해당 채팅방을 수정해주어야함
				if(tmpClient.equals(client)) continue; //입장 요청자에게는 전달할 필요없음
				sendSpecRoomInfo(tmpClient, roomId); //수정된 채팅방 정보 전달
			}
		}
	}
	
	
	public void quitRoom(MySocket client) { //채팅방 퇴장
		ClientInfo cInfo = clients.get(client); //클라이언트목록에서 요청자 ClientInfo 가져옴
		if(cInfo == null) return; 
		Chatroom room = cInfo.joinedRoom; //접속한 채팅방 가져옴
		if(room == null) return;
		room.removeUser(cInfo); //채팅방의 퇴장 처리 메소드 호출
		if(room.users.size() <= 0) { //이 채팅방의 접속인원이 0명이 됐다면
			delChatroom(room.roomId); //채팅방 삭제 메소드 호출
		} else {
			for(MySocket tmpClient : clients.keySet()) { //채팅방 인원이 수정되었으므로 클라이언트들의 메인메뉴 프레임에서 해당 채팅방을 수정해주어야함
				if(clients.get(tmpClient) == null) continue;
				sendSpecRoomInfo(tmpClient, room.roomId);
			}
		}	
		cInfo.joinedRoom = null;
	}
	
	private void delChatroom(int roomId) { //채팅방 삭제처리
		
		Chatroom delRoom = getSpecChatroom(roomId); //삭제할 채팅방 ID를 이용하여 채팅방 객체 가져옴
		
		if(delRoom == null) return;
		
		chatroomList.remove(delRoom); //채팅방 목록에서 삭제
		
		for(MySocket tmpClient : clients.keySet()) { //채팅방이 삭제되었으므로 클라이언트들의 메인메뉴 프레임에서 해당 채팅방을 삭제해주어야함
			if(clients.get(tmpClient) == null) continue;
			tmpClient.sendMessage("CDE§"+delRoom.roomId);
		}
	}
	
	public void sendSpecRoomInfo(MySocket client, int roomId) { //client에게 채팅방 ID값을 이용하여 채팅방 정보 전달
		Chatroom room = getSpecChatroom(roomId); //채팅방 ID를 이용하여 채팅방 객체 가져옴
		if(room != null) {
			client.sendMessage("CPC§"+room.roomId+"§"+room.roomtitle+"§"+room.roomName+"§"+room.roomIntro+"§"+room.users.size()+"§"+room.roomMaxPlayer);
			//정보를 메시지로 합쳐 전달
		}
	}
	
	public void sendChatmsg(MySocket client, String args[]) { //채팅 메시지 전달
		if(args.length > 1) { 
			ClientInfo userInfo = clients.get(client); //요청자 사용자 정보 가져옴
			String chatmsg = args[1]; //채팅메시지
			userInfo.joinedRoom.sendChat(userInfo.nickName, chatmsg); //요청한 사용자가 입장중인 채팅방의 채팅전달 메소드 호출
		}
	}
	
	public void roomOwnerChange(MySocket client, String args[]) { //방장 변경
		int roomId = Integer.valueOf(args[1]); //대상 채팅방 ID
		String callerId = args[2]; //요청자 ID
		String target = args[3]; //대상자 ID
		
		Chatroom room = getSpecChatroom(roomId); //채팅방 ID를 이용하여 채팅방 객체를 가져옴
		if(room != null) {
			room.changeOwner(callerId, target); //해당 채팅방의 방장 변경 메소드 호출	
		}
	}
	
	public void roomUserKick(MySocket client, String args[]) { //강제 퇴장
		int roomId = Integer.valueOf(args[1]); //채팅방 ID
		String callerId = args[2]; //요청자
		String target = args[3]; //대상자
		
		Chatroom room = getSpecChatroom(roomId); //채팅방 ID를 이용하여 채팅방 객체를 가져옴
		if(room != null) {
			room.KickUser(callerId, target); //해당 채팅방의 강제퇴장 메소드 호출
			if(room.users.size() <= 0) { //만.약. 채팅방 현재인원이 0명이 됐다면
				delChatroom(room.roomId); //채팅방 삭제 메소드 호출
			} 	
			for(MySocket tmpClient : clients.keySet()) {  //채팅방이 삭제되었으므로 클라이언트들의 메인메뉴 프레임에서 해당 채팅방을 삭제해주어야함
				if(clients.get(tmpClient) == null) continue;
				sendSpecRoomInfo(tmpClient, room.roomId);
			}
		}			
	}
	
	public void editRoomInfo(MySocket client, String args[]) { //방 정보 수정
		int roomId = Integer.valueOf(args[1]); //채팅방 ID
		String callerId = args[2]; //요청자
		String newTitle = args[3]; //새로운 주제값
		String newName = args[4]; //새로운 제목값
		String newIntro = args[5]; //새로운 소개값
		
		Chatroom room = getSpecChatroom(roomId); //채팅방 ID를 이용하여 채팅방 객체를 가져옴
		if(room != null) {
			room.editRoomInfo(callerId, newTitle, newName, newIntro); //해당 채팅방의 정보수정 메소드 호출
			for(MySocket tmpClient : clients.keySet()) { //채팅방 정보가 수정되었으므로 클라이언트들의 메인메뉴 프레임에서 해당 채팅방을 수정해주어야함
				if(clients.get(tmpClient) == null) continue;
				sendSpecRoomInfo(tmpClient, room.roomId);
			}
		}
	}
	
	public void sendDumpMsg(MySocket client, String args[]) { //덤프 메시지 전달용
		client.sendMessage("DMP"); //덤프메시지 받으면 덤프메시지 전달함
	}
	
	private Chatroom getSpecChatroom(int roomId) { //방 ID로 특정 채팅방 얻기
		for(Chatroom room : chatroomList) { //채팅방 탐색
			if(room.roomId == roomId) //채팅방 ID과 요청ID가 같다면
				return room; //객체 반환
		}
		return null;
	}
	
	/////////디버깅용
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
					server.database.insertUserInfo("tester1", "수정된테스터", "9876543", "수정된테스터");
				}else if(cmd.equalsIgnoreCase("test5")) {
					System.out.println("did test5");
					server.database.insertUserInfo("tester2", "수정된테스터2", "9876543", "수정된테스터2");
				}else if(cmd.equalsIgnoreCase("test6")) {
					System.out.println("did test6");
					List<MySocket> list = new ArrayList<MySocket>(clients.keySet());
					delChatroom(3);
				}else if(cmd.equalsIgnoreCase("test7")) {
					System.out.println("did test7");
					List<MySocket> list = new ArrayList<MySocket>(clients.keySet());
					MySocket client = (MySocket) list.get(0);
					getSpecChatroom(4).roomName = "변경돰";
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

