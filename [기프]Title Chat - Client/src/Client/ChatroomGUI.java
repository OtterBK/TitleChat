package Client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import Addon.MyColor;
import Addon.MyUtility;

public class ChatroomGUI extends JFrame {
	
	public LinkedHashMap<String, String> users = new LinkedHashMap<String, String>(); 
	
	//GUI 객체 및 값
	private JPanel contentPane;
	private JButton settingImg;
	private JButton btnSend;
	private JScrollPane scrollPane;
	private JPanel centerPanel;
	private JLabel titleLabel;
	private JLabel titleImg;
	private JLabel nameLabel;
	private JLabel introLabel;
	private JTextArea textArea;
	private int frameWidth = 500;
	private int frameHeight = 700;
	
	///방 정보
	private int roomId;
	private String title;
	private String name;
	private String intro;
	private String owner = "";
	private int maxPlayer;
	private static ChatroomGUI lastFrame;
	private int centerHeight = 0;
	private String lastEnter = "";
	private MainMenuGUI mainMenu;

	public ChatroomGUI(int roomId, String title, String name, String intro, int maxPlayer, String owner, MainMenuGUI mainMenu) { //채팅방 GUI
		if(lastFrame != null) { //채팅방 프레임은 1개밖에 열릴수 없음 2개 열림 방지
			lastFrame.dispose();
		}
		lastFrame = this;
		
		this.roomId = roomId; //초기화
		this.title = title;
		this.name = name;
		this.intro = intro;
		this.owner = owner;
		this.maxPlayer = maxPlayer;
		this.mainMenu = mainMenu;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png"); //icon이미지
		ImageIcon icon = new ImageIcon(src); //아이콘 설정
		this.setIconImage(icon.getImage()); 
		this.setTitle("Title Chat"); //타이틀 설정
		setResizable(false); //크기 재설정 불가능
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		//프레임 크기 설정
		
		this.addWindowListener(new JFrameWindowClosingEventHandler()); //창 닫기 이벤트
		
		MyEvent event = new MyEvent(this); //버튼 클릭 이벤트
		
		contentPane = new JPanel(); //메인 패널
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(MyColor.midNightBlue);
		contentPane.setLayout(null); //절대 배치
		
		JPanel topPanel = new JPanel(); //위쪽 패널
		topPanel.setBorder(new LineBorder(MyColor.white,1));
		topPanel.setBackground(MyColor.midNightBlue);
		topPanel.setBounds(0, 0, 494, 100);
		topPanel.setLayout(null);
		contentPane.add(topPanel);
		
		ImageIcon img = new ImageIcon(ChatroomGUI.class.getResource("/resources/"+title+".png")); //주제에 따른 이미지 설정 
		titleImg = new JLabel(img); 
		titleImg.setBounds(10, 10, 50, 50);
		topPanel.add(titleImg);
		
		titleLabel = new JLabel(title); //채팅방 주제
		titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(MyColor.lightYellow);
		titleLabel.setBounds(10, 70, 57, 15);
		topPanel.add(titleLabel);
		
		nameLabel = new JLabel(name); //채팅방 이름
		nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		nameLabel.setBounds(75, 10, 300, 30);
		nameLabel.setForeground(MyColor.lightYellow);
		topPanel.add(nameLabel);
		
		introLabel = new JLabel(MyUtility.lineSpacing(intro, 16)); //채팅방 소개, 16글자로 달음
		introLabel.setVerticalAlignment(SwingConstants.TOP);
		introLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		introLabel.setBounds(75, 50, 300, 45);
		introLabel.setForeground(MyColor.lightYellow);
		topPanel.add(introLabel);
		
		ImageIcon setImg = new ImageIcon(ChatroomGUI.class.getResource("/resources/setting.png"));  //세팅 아이콘 설정
		settingImg = new JButton(setImg); //세팅 버튼
		settingImg.setBorderPainted(false);
		settingImg.setContentAreaFilled(false);
		settingImg.setFocusPainted(false);
		settingImg.setBounds(400, 13, 75, 75);
		settingImg.addActionListener(event);
		topPanel.add(settingImg);
		
		JPanel bottomPanel = new JPanel(); //하단 패널
		bottomPanel.setBounds(0, 621, 494, 50);
		contentPane.add(bottomPanel);
		bottomPanel.setLayout(null);
		
		btnSend = new JButton("전송"); //메시지 전송 버튼
		btnSend.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		btnSend.setBounds(428, 0, 66, 50);
		btnSend.setBackground(MyColor.lightOrange);
		btnSend.setForeground(MyColor.navy);
		btnSend.addActionListener(event);
		bottomPanel.add(btnSend);
		
		textArea = new JTextArea(); //메시지 입력 에리어
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		textArea.setAutoscrolls(true);
		textArea.setLineWrap(true); 
		
		JScrollPane textScrollPane = new JScrollPane(); //텍스트 에리어를 올릴 스크롤 패널
		textScrollPane.setBounds(0, 0, 429, 50);
		textScrollPane.getViewport().setBackground(MyColor.midNightBlue);
		textScrollPane.setViewportView(textArea);
		bottomPanel.add(textScrollPane);
		
		centerPanel = new JPanel(); //채팅메시지가 띄워질 프레임
		centerPanel.setBackground(MyColor.midNightBlue);
		centerPanel.setLayout(null);
		
		scrollPane = new JScrollPane(); //채팅 메시지가 생성되는 센터패널을 올릴 스크롤 패널
		scrollPane.setBounds(0, 100, 494, 519);
		scrollPane.getViewport().setBackground(MyColor.midNightBlue);
		scrollPane.setViewportView(centerPanel);
		scrollPane.getViewport().setBackground(MyColor.midNightBlue);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() { //스크롤바 색상설정
            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = MyColor.navy;
            }
        });
		contentPane.add(scrollPane);	
		
		Client.status = "채팅방"; //사용자 현재 화면 상태를 채팅방으로 설정
		
		Client.server.startListen(this); //통신 리스닝 모드 실행하여 서버에서 오는 신호 감지 시작
		
		this.setVisible(true); //프레임 표시
		
		printAlert(Client.nickName+" 님이 입장하셨습니다."); //입장 알림 출력
	}
	
	public void kicked() {  //강제퇴장됨 처리
		mainMenu.setVisible(true); //메인메뉴 표시
		lastFrame.dispose(); //프레임 없앰
		mainMenu.refreshRoom(); //메인메뉴 새로고침
		CheckGUI ch = new CheckGUI(mainMenu, "당신은 강제퇴장 되었습니다.", false, false);
	}
	
	public int getRoomId() { //채팅방 id반환
		return this.roomId;
	}
	
	public int getMax() { //최대인원 반환
		return this.maxPlayer;
	}
	
	public String getRoomName() { //채팅방 이름 반환
		return this.name;
	}
	
	public String getRoomIntro() { //채팅방 소개 반환
		return this.intro;
	}
	
	public String getRoomOwner() { //채팅방 방장 반환
		return this.owner;
	}
	
	public LinkedHashMap<String, String> getUsers() { //채팅방 사용자 목록 반환
		return this.users;
	}
	
	public void changedRoomInfo(String msg) { //채팅방 정보가 변경됨에 따른 처리
		String args[] = msg.split("¿"); //파싱
		
		String roomInfo[] = args[0].split("§"); //파싱
		
		String title = roomInfo[2]; //새로운 주제
		String name = roomInfo[3]; //새로운 제목
		String intro = roomInfo[4]; //새로운 소개
		String owner = roomInfo[5]; //새로운 방장
		
		boolean roomChange = false; //방 정보가 변경되었는가? 아니면 인원만?
		
		if(!this.title.equals(title)) { //기본 주제값과 새 주제값 비교
			this.title = title; //다르면 새로운값으로 설정
			roomChange = true; //방 정보 변경됨 설정
		}
		if(!this.name.equals(name)) {//기본 제목값과 새 제목값 비교
			this.name = name;//다르면 새로운값으로 설정
			roomChange = true;//방 정보 변경됨 설정
		}
		if(!this.intro.equals(intro)) {//기본 소개값과 새 소개값 비교
			this.intro = intro;//다르면 새로운값으로 설정
			roomChange = true;//방 정보 변경됨 설정
		}
		if(!this.owner.equals(owner)) {//기본 방장값과 새 방장값 비교
			this.owner = owner;//다르면 새로운값으로 설정
			roomChange = true;//방 정보 변경됨 설정
		}
		
		if(roomChange) { //방정보 변경되었다면
			ImageIcon img = new ImageIcon(ChatroomGUI.class.getResource("/resources/"+this.title+".png")); //새로운 주제 이미지로 설정
			titleImg.setIcon(img); //방 정보 변경처리
			titleLabel.setText(this.title);
			nameLabel.setText(this.name);
			introLabel.setText(this.intro);
			printAlert("방 정보가 변경되었습니다."); //알림 출력
		}
		
		users.clear(); //인원 목록 초기화
		for(int i = 1; i < args.length; i++) { //새로받은 인원목록값 넣음
			String user[] = args[i].split("§");
			users.put(user[0], user[1]);
		}
		
	}
	
	public void printAlert(String msg) { //알림 출력
		int lineMax = 29; //한줄 최대 값
		int line = (int) (msg.length()/lineMax)+1; //채팅메시지 줄 수, 이 값에 따라  라벨 높이 수정
		int labelHeight = 24; //한줄당 라벨 길이
		int comHeight = line*labelHeight; //전체 길이
		JLabel alertMsg = new JLabel(MyUtility.lineSpacing(msg, lineMax)); //알림 메시지 한줄 최대값으로 나눔
		alertMsg.setBounds(7, centerHeight, scrollPane.getSize().width-35, comHeight); //라벨 크기 , 위치 설정
		alertMsg.setForeground(MyColor.lightOrange);  
		alertMsg.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		centerHeight += comHeight+4;  
		if(centerHeight > scrollPane.getSize().getHeight()) {//센터 패널 길이 수정
			centerPanel.setPreferredSize(new Dimension((int) centerPanel.getSize().getWidth(), centerHeight+10));
		}
		lastEnter = ""; //마지막 입력자 없음으로 설정
		centerPanel.add(alertMsg);
		
		centerPanel.revalidate();
		centerPanel.repaint();
	}
	
	public void printChat(String args[]) { //채팅 메시지 출력
		String nickName = args[1];
		String chat = args[2];
		int lineMax = 31; //한줄 최대 문자길이
		int line = (int) (chat.length()/lineMax)+1; //줄 수, 이거에 따라 라벨 길이 수정 
		int labelHeight = 24; //줄 당 라벨 높이
		int nickNameHeight = 0;
		int comHeight = 0;
		int comWidth = 0;
		if(chat.contains("\n")) { //개행 기호 개수 셈
			int cnt = 0;
		    int fromIndex = -1;
		    while ((fromIndex = chat.indexOf("\n", fromIndex + 1)) >= 0) {
		      cnt++;
		    }
		    line += cnt; //개행기호 만큼 줄 수 증가
		}
		comHeight = line*labelHeight;
		/*if(chat.length() >= lineMax || chat.contains("\n")) { //글자수에 따라 라벨의 가로길이를 조정하려 했으나
			comWidth = scrollPane.getSize().width-35;         //글자에따라 (ex)숫자, 영어) 라벨의 가로길이가 달라져
		} else {												//최대치로 통일하는 것이 더 깔끔함
			comWidth = (int)(chat.length() * 14.5)+5;
		}*/
		comWidth = scrollPane.getSize().width-35;
		
		if(!lastEnter.equalsIgnoreCase(nickName)) { //마지막 채팅을 친 사용자의 닉네임과 현재 친 사용자의 닉네임이 같지 않으면 닉네임 표시
			JLabel nickNameLbl = new JLabel(nickName);
			nickNameLbl.setBounds(7, centerHeight, scrollPane.getSize().width-35, labelHeight);
			nickNameLbl.setFont(new Font("맑은 고딕", Font.BOLD, 17));
			nickNameLbl.setForeground(MyColor.white);
			nickNameHeight = 25;
			centerPanel.add(nickNameLbl);
			lastEnter = nickName;
		}
		//같으면 닉네임 표시 x
		
		//채팅 메시지 라벨
		JLabel chatLbl = new JLabel(MyUtility.lineSpacing(chat, lineMax));
		chatLbl.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		chatLbl.setBounds(7, centerHeight+nickNameHeight, comWidth, comHeight); //라벨 위치 및 크기 설정
		chatLbl.setForeground(MyColor.navy);
		chatLbl.setBackground(MyColor.lightOrange);
		chatLbl.setBorder(new LineBorder(MyColor.white,1));
		chatLbl.setOpaque(true);
			
		centerHeight += comHeight+nickNameHeight+4; //센터 패널 길이 수정
		if(centerHeight > scrollPane.getSize().getHeight()) {
			centerPanel.setPreferredSize(new Dimension((int) centerPanel.getSize().getWidth(), centerHeight+10));
		}
		
		centerPanel.add(chatLbl);
		
		centerPanel.revalidate();
		centerPanel.repaint();
		
		//채팅이 입력된 후 스크롤을 가장 아래로 내려야하는데 바로 내리면 가장 아래로 내려가지지 않음
		//쓰레드를 생성하여 0.05초 후에 스크롤바를 가장 아래로 내리도록 사용
		scrollDown sd = new scrollDown(); 
		sd.start();
	
	}
	
	private void sendChat() { //서버에 채팅 메시지 입력 전달
		String text = textArea.getText();
		if(text != "") {
			Client.server.sendMessage("SCM§"+text);
			textArea.requestFocus();
			textArea.setText("");
		}
	}
	
	private class JFrameWindowClosingEventHandler extends WindowAdapter { //창 닫으면 채팅방 퇴장 처리 및 메인메뉴 프레임 표시
		public void windowClosing(WindowEvent e) {
			Client.server.sendMessage("QTR§"); //퇴장 처리 메시지 전달
			mainMenu.setVisible(true); //메인메뉴 프레임 표시
			lastFrame.dispose(); //채팅창 닫음
			mainMenu.refreshRoom(); //메인메뉴 새로고침
		}
	}
	
	private class MyEvent implements ActionListener{ //버튼 클릭이벤트

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(settingImg)) { //세팅 이미지 클릭시
				UserListGUI ug = new UserListGUI((ChatroomGUI)frame); //채팅방 목록창 띄움
			} else if(src.equals(btnSend)) { //전송 버튼 클릭시
				sendChat(); //메시지 전송
			}
		}
	}
	
	private class scrollDown extends Thread{ //0.5초후 스크롤 가장 아래로
		public void run() {
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		}
		
	}
}
