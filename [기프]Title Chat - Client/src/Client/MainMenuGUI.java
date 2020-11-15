package Client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import Addon.MyColor;
import Addon.MyUtility;

public class MainMenuGUI extends JFrame { //메인메뉴 화면
	
	private int frameWidth = 400;
	private int frameHeight = 600;
	private JLabel lblNickName;
	private JPanel topLeftPanel;
	private JPanel topRightPanel;
	private JPanel bottomPanel;
	private JButton settingBtn;
	private JButton enterBtn;
	private JButton createRoomBtn;
	private JPanel centerPanel;
	private JComboBox comboBox; 
	private JScrollPane sc;
	private LinkedHashMap<Room, JPanel> rooms = new LinkedHashMap<Room, JPanel>();
	private SelectRoomEvent selectEvent;
	private String selectedRoomId;
	private JPanel selectedRoomPane; 
	private MainMenuGUI mainMenu;
	
	public MainMenuGUI() { //메인메뉴 프레임
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		setResizable(false);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		getContentPane().setLayout(null);
		getContentPane().setBackground(MyColor.midNightBlue);
		
		this.addWindowListener(new JFrameWindowClosingEventHandler()); //창 닫기 이벤트
		mainMenu = this; //메인메뉴 설정
		
		MyEvent event = new MyEvent(this);
		selectEvent = new SelectRoomEvent();
		selectedRoomId = null;
		selectedRoomPane = null;
		
		topLeftPanel = new JPanel(); //상단 좌측 패널
		topLeftPanel.setBounds(0, 0, 250, 50);
		topLeftPanel.setBackground(MyColor.midNightBlue);
		topLeftPanel.setBorder(new LineBorder(MyColor.darkBlue, 1));
		topLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		getContentPane().add(topLeftPanel);
		
		lblNickName = new JLabel(Client.nickName); //닉네임 라벨
		lblNickName.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNickName.setHorizontalAlignment(SwingConstants.LEFT);
		lblNickName.setPreferredSize(new Dimension(245, 50));
		lblNickName.setForeground(MyColor.lightYellow);
		topLeftPanel.add(lblNickName);
		
		topRightPanel = new JPanel(); //상단 우측 패널
		topRightPanel.setBounds(250, 0, 144, 50);
		topRightPanel.setBackground(MyColor.midNightBlue);
		topRightPanel.setBorder(new LineBorder(MyColor.darkBlue, 1));
		topRightPanel.setLayout(new GridLayout(2, 1, 0, 0));
		getContentPane().add(topRightPanel);
		
		JLabel lblSelectTitle = new JLabel("주제선택"); //주제선택 라벨
		lblSelectTitle.setForeground(MyColor.lightYellow);
		lblSelectTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		topRightPanel.add(lblSelectTitle);
		
		comboBox = new JComboBox(Client.titleList); //콤보박스, 목록은 Client 클래스의 titleList 사용
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		comboBox.setBackground(MyColor.lightOrange);
		comboBox.setForeground(MyColor.navy);
		comboBox.addActionListener(new ActionListener() { //콤보박스 선택 이벤트		
			public void actionPerformed(ActionEvent e) {
				clearCenterPanel(); //채팅방 목록 GUI 초기화
				setRoomGui();	 //채팅방 목록 설정
			}
		});
		topRightPanel.add(comboBox);
		
		bottomPanel = new JPanel(); //하단 패널
		bottomPanel.setBounds(0, 541, 394, 30);
		bottomPanel.setBackground(MyColor.lightOrange);
		bottomPanel.setLayout(new GridLayout(0, 3, 0, 0));
		getContentPane().add(bottomPanel);
		
		settingBtn = new JButton("설정"); //사용자 정보 설정 버튼
		settingBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		settingBtn.setBackground(MyColor.lightOrange);
		settingBtn.setForeground(MyColor.navy);
		settingBtn.setBorderPainted(true);
		settingBtn.addActionListener(event);
		bottomPanel.add(settingBtn);
		
		enterBtn = new JButton("입장"); //채팅방 입장 버튼
		enterBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		enterBtn.setBackground(MyColor.lightOrange);
		enterBtn.setForeground(MyColor.navy);
		enterBtn.setBorderPainted(true);
		enterBtn.addActionListener(event);
		bottomPanel.add(enterBtn);
		
		createRoomBtn = new JButton("방 생성"); //채팅방 생성 버튼
		createRoomBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		createRoomBtn.setBackground(MyColor.lightOrange);
		createRoomBtn.setForeground(MyColor.navy);
		createRoomBtn.setBorderPainted(true);
		createRoomBtn.addActionListener(event);
		bottomPanel.add(createRoomBtn);
		
		centerPanel = new JPanel(); //센터 패널
		centerPanel.setBackground(MyColor.midNightBlue);
		centerPanel.setLayout(null);
		
		sc = new JScrollPane(centerPanel); //센터 패널 올릴 스크롤 패널
		centerPanel.setLayout(null);
		sc.getViewport().setBackground(MyColor.midNightBlue);
		sc.setBounds(0, 50, 394, 492);
		sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sc.getVerticalScrollBar().setUI(new BasicScrollBarUI() { //스크롤바 색 설정
            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = MyColor.navy;
            }
        });
		getContentPane().add(sc);
		centerPanel.setPreferredSize(new Dimension(sc.getSize().width,sc.getSize().height));
		
		getRooms();
		
		Client.status = "메인메뉴"; //사용자 화면상태 메인메뉴로 설정
		
		this.setVisible(true);
		Client.server.startListen(this); //통신 리스닝 모드 실행하여 서버에서 오는 신호 감지 시작
	}
	
	public void refreshNickName() { //사용자 닉네임 새로고침
		lblNickName.setText(Client.nickName);
	}
	
	public void refreshRoom() { //채팅방 목록 새로고침
		clearCenterPanel(); //채팅방 목록 GUI 초기화
		getRooms(); //서버에서 채팅방 받아오는 메소드 호출
	}
	
	private void clearCenterPanel() { //채팅방 목록 GUI 초기화
		for(Component c : centerPanel.getComponents()) { //센터패널에 있는 컴포넌트 모두 삭제
			centerPanel.remove(c);
		}	
	}
	
	private void getRooms() { //서버에서 채팅방 목록 받아옴
		Client.server.sendMessage("GCL§"); //서버에 목록 요청
		String args[] = null;
		do {
			String msg = Client.server.receiveMessage(); //결과 값
			rooms.clear(); //방 목록 초기화
			args = msg.split("¿"); //파싱
		}while(!args[0].equalsIgnoreCase("RLS")); //만약 응답값이 RLS가(룸리스트 정보) 아니면 다시 들음, 잘못된 정보 받는거 방지
		
		for(int i = 1; i < args.length; i++) { //파싱한 방 정보 저장
			String data[] = args[i].split("§");
			int id = Integer.valueOf(data[0]);
			String title = data[1];
			String name = data[2];
			String intro = data[3];
			int now = Integer.valueOf(data[4]);
			int max = Integer.valueOf(data[5]);
				
			Room room = new Room(id, title, name, intro, now, max);
			rooms.put(room, null);
		}
		setRoomGui(); //방 정보를 토대로 채팅방 목록 GUI설정

	}
	
	private void setRoomGui() { //채팅방 목록 GUI 설정
		int yPos = 0; //y위치값
		int showCnt = 0; //보여지는 총 개수
		String selectTitle = Client.titleList[comboBox.getSelectedIndex()]; //콤보박스에서 선택한 주제 가져옴
		for(Room room : rooms.keySet()) {//선택 주제가 ""라면 전체 표시임
			if(!selectTitle.equals("")) { //주제가 선택된 상태라면
				if(!room.title.equals(selectTitle)) continue; //채팅방의 주제가 선택 주제와 동일하지 않으면 다음 채팅방으로 
			}
			JPanel gui = room.getGUI(); //GUI화 해서 표시
			gui.setBounds(0, yPos, sc.getSize().width-20, 100);
			rooms.put(room, gui);
			centerPanel.add(gui);
			yPos += 100;
			showCnt += 1;
		}
		
		if(showCnt >= 5) { //보여지는 GUI의 총 개수가 5개 이상이면 센터 패널의 높이 늘림
			centerPanel.setPreferredSize(new Dimension(sc.getSize().width,sc.getSize().height+((showCnt-4)*100)));
		} else {
			centerPanel.setPreferredSize(new Dimension(sc.getSize().width,sc.getSize().height-20));
		}
		centerPanel.revalidate(); //센터패널 업데이트
		centerPanel.repaint();
	}
	
	public void editRooms_add(String args[]) {//새로운 방 정보 추가 
		int id = Integer.valueOf(args[1]); //방 정보 세팅
		String title = args[2];
		String name = args[3];
		String intro = args[4];
		int now = Integer.valueOf(args[5]);
		int max = Integer.valueOf(args[6]);
		
		Room room = new Room(id, title, name, intro, now, max); //채팅방 정보 생성
		JPanel gui = room.getGUI();
		gui.setBounds(0, rooms.size()*100, sc.getSize().width-20, 100); //채팅방 위치 및 크기 설정
		rooms.put(room, gui); //채팅방 정보 저장
		centerPanel.add(gui); //하단에 해당 채팅방 생성
		if(rooms.size() >= 5) { //센터 패널 크기 조절
			centerPanel.setPreferredSize(new Dimension(sc.getSize().width,sc.getSize().height+((rooms.size()-4)*100)));
		}
		centerPanel.revalidate(); //센터패널 업데이트
		centerPanel.repaint();
	}
	
	public void editRooms_del(String args[]) { //채팅방 삭제
		int id = Integer.valueOf(args[1]); //수정할 채팅방 ID
		
		Room delRoom = null;

		for(Room room : rooms.keySet()) { //가진 채팅방 정보 탐색
			if(room.id == id) { //삭제할 채팅방 ID와 일치하다면
				delRoom = room; //삭제할 방 저장
				break;
			}
		}
		if(delRoom == null) return;
		
		rooms.remove(delRoom); //방 삭제

		refreshRoom(); //방 새로고침
	}
	
	public void editRooms_roominfo(String args[]) { //채팅방 목록의 방 정보 수정
		int id = Integer.valueOf(args[1]); //수정할 채팅방 ID
		String title = args[2]; //새로운 정보 저장 
		String name = args[3];
		String intro = args[4];
		int now = Integer.valueOf(args[5]);
		int max = Integer.valueOf(args[6]);
		
		Room editRoom = null;
		
		for(Room room : rooms.keySet()) { //가진 채팅방 정보 탐색
			if(room.id == id) { //수정할 채팅방 ID와 일치하면
				editRoom = room;
				break;
			}
		}
		
		editRoom.title = title; //새로운 채팅방 정보로 저장
		editRoom.name = name;
		editRoom.intro = intro;
		editRoom.now = now;
		editRoom.max = max;

		JPanel gui = rooms.get(editRoom); //정보가 수정된 채팅방이 기존에 가지고 있던 패널 가져옴
		if(gui == null) return; 
		for(Component c : gui.getComponents()) { //해당 패널의 컴포넌트 삭제
			gui.remove(c);
		}
		
		editRoom.setGuiComponents(gui); //컴포넌트 재설정

		centerPanel.revalidate(); //업데이트
		centerPanel.repaint();
	}
	
	private class MyEvent implements ActionListener{ //버튼 클릭 이벤트

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) { 
			JButton src = (JButton) e.getSource();
			if (src.equals(settingBtn)) { //사용자 정보 수정 버튼 클릭시
				SettingGUI settingGUI = new SettingGUI(frame); //사용자 정보 수정 프레임 띄움 
			} else if(src.equals(createRoomBtn)) { //방 생성 버튼 클릭시
				CreateRoomGUI createRoomGUI = new CreateRoomGUI(mainMenu); //방 생성 프레임 띄움
			} else if(src.equals(enterBtn)) { //입장 버튼 클릭시
				if(selectedRoomId != null) { //방을 선택한 상태일때만
					Client.server.sendMessage("JIR§"+selectedRoomId); //서버에 해당 방 ID의 입장 요청
					String msg = Client.server.receiveMessage(); //결과받음
					String args[] = msg.split("§"); //파싱
					if(args[0].equals("FAL")){ //실패시
						CheckGUI cf = new CheckGUI(frame, args[1], false, false); //이유 띄움
						selectedRoomId = null;	
						refreshRoom();	//채팅방 목록 새로고침
					} else if(args[0].equals("SUC")) { //성공시
						args = msg.split("¿"); //파싱
						String data[] = args[0].split("§"); //파싱
						mainMenu.dispose();
						ChatroomGUI cr = new ChatroomGUI(Integer.valueOf(selectedRoomId), data[2], data[3], data[4], Integer.valueOf(data[5]),data[1], mainMenu);
						//파싱한 채팅방 정보를 토대로 채팅방 프레임 띄움
						for(int i = 1; i < args.length; i++) { //채팅방에 있는 사용자들 설정
							String user[] = args[i].split("§");
							cr.users.put(user[0], user[1]);
						}
					}
				}
			}
		}
	}
	
	private class JFrameWindowClosingEventHandler extends WindowAdapter { //창 닫기시
		public void windowClosing(WindowEvent e) {
			if(e.getWindow() instanceof MainMenuGUI) {
				Client.server.stopListen(); //리스너  중지 
				System.exit(0); //프로그램 종료
			}	
		}
	}
	
	private class SelectRoomEvent implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) { //채팅방 패널 클릭시
			JPanel p = (JPanel)e.getSource();
			if(selectedRoomPane != null) { //이전에 선택한 패널은 원래 배경색으로
				selectedRoomPane.setBackground(MyColor.midNightBlue);
			}
			selectedRoomPane = p; //선택한 패널을 저장
			selectedRoomId = p.getName(); //선택한 채팅방 ID저장
			p.setBackground(MyColor.darkBlue); //선택한 패널의 배경색 변경
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

	}

	private class Room{ //방 정보
		
		int id; //채팅방 ID
		String title; //채팅방 주제
		String name; //채팅방 제목
		String intro; //채팅방  소개
		int now; //현재인원
		int max; //최대인원
		
		public Room(int id, String title, String name, String intro, int now, int max) { //채팅방 정보 생성
			this.id = id; //초기화
			this.title = title;
			this.name = name;
			this.intro = intro;
			this.now = now;
			this.max = max;
		}
		
		public JPanel getGUI() { //해당 채팅방의 정보를 토대로 gui패널을 만들어줌
				
			JPanel pane = new JPanel(); //반환할 패널
			pane.setLayout(null); //절대 배치
			
			setGuiComponents(pane); //컴포넌트 설정
			
			pane.setName(id+""); //패널이름을 채팅방 id로 설정
			
			pane.setBorder(new LineBorder(MyColor.white, 1));
			pane.setBackground(MyColor.midNightBlue);
			
			pane.addMouseListener(selectEvent); //패널 클릭이벤트 추가
			
			return pane;
		}
		
		public void setGuiComponents(JPanel pane) { //채팅방 목록 패널의 컴포넌트 설정
			
			ImageIcon img = new ImageIcon(MainMenuGUI.class.getResource("/resources/"+title+".png"));  //주제에 맞춰 이미지 설정
			JLabel titleImg = new JLabel(img);
			titleImg.setBounds(5, 10, 50, 50);
			pane.add(titleImg);
			
			JLabel titleLabel = new JLabel(title); //채팅방 주제 라벨
			titleLabel.setForeground(MyColor.white);
			titleLabel.setHorizontalAlignment(JLabel.CENTER);
			titleLabel.setBounds(5, 65, 50, 25);
			titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			pane.add(titleLabel);
			
			JLabel nameLabel = new JLabel(name); //채팅방 제목 라벨
			nameLabel.setForeground(MyColor.white);
			nameLabel.setBounds(70, 20, 200, 25);
			nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			nameLabel.setVerticalAlignment(JLabel.CENTER);
			pane.add(nameLabel);
			
			JLabel introLabel = new JLabel(MyUtility.lineSpacing(intro, 16)); //채팅방 소개 라벨
			introLabel.setForeground(MyColor.white);
			introLabel.setBounds(70, 50, 200, 45);
			introLabel.setVerticalAlignment(JLabel.TOP);
			introLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			pane.add(introLabel);
			
			JLabel playerLabel = new JLabel(now + "/" + max); //채팅방 인원 라벨, 현재인원 / 최대인원
			playerLabel.setForeground(MyColor.white);
			playerLabel.setHorizontalAlignment(JLabel.RIGHT);
			playerLabel.setBounds(290, 15, 70, 70);
			playerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
			pane.add(playerLabel);
			
		}
		
	}
}
