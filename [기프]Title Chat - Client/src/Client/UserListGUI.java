package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Addon.MyColor;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Font;

public class UserListGUI extends JFrame { //채팅방 사용자 목록 프레임

	private static UserListGUI lastFrame;
	private JFrame frame;
	private JPanel contentPane;
	private int frameWidth = 250;
	private int frameHeight = 400;
	private JLabel lblConnecting;
	private JScrollPane scrollPane;
	private JLabel lblUserCnt;
	private JButton btnEditRoom;
	private JPanel centerPanel;
	private JPopupMenu popup;
	private JMenuItem menu1;
	private JMenuItem menu2;
	private LinkedHashMap<String, String> users;
	private SelectUserEvent mouseEvent;
	private JLabel selectedUserLabel;
	private String selectedUserId;
	
	///방정보 관련
	private ChatroomGUI callerRoom;
	private String ownerId;
	private int roomId;
	
	public UserListGUI(ChatroomGUI chatroom) {//채팅방 사용자 목록 프레임
		if(lastFrame != null) { //프레임 2개 띄워지는거 방지
			lastFrame.dispose();
		}
		lastFrame = this;
		
		frame = this;
		callerRoom = chatroom;
		ownerId = callerRoom.getRoomOwner();
		roomId = callerRoom.getRoomId();
		users = callerRoom.users;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		
		mouseEvent = new SelectUserEvent(); //마우스 클릭 이벤트
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		//화면 크기 설정 및 위치 화면 가운데 설정
		
		contentPane = new JPanel(); //메인 패널
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(MyColor.midNightBlue);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel topPanel = new JPanel(); //상단 패널
		topPanel.setBounds(0, 0, 244, 50);
		topPanel.setBackground(MyColor.midNightBlue);
		topPanel.setLayout(null);
		contentPane.add(topPanel);
		
		JLabel lblTitle = new JLabel("  현재 참여자 목록"); //상단 제목 라벨
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblTitle.setBounds(0, 0, 160, 50);
		lblTitle.setBackground(MyColor.midNightBlue);
		lblTitle.setForeground(MyColor.lightYellow);
		topPanel.add(lblTitle);
		
		lblUserCnt = new JLabel(users.size()+"/"+chatroom.getMax()); //현재 채팅방 사용자 인원과 최대인원 표시
		lblUserCnt.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblUserCnt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserCnt.setBounds(160, 0, 72, 50);
		lblUserCnt.setBackground(MyColor.midNightBlue);
		lblUserCnt.setForeground(MyColor.lightYellow);
		topPanel.add(lblUserCnt);
		
		if(ownerId.equals(Client.id)) { //이 프레임을 띄운게 채팅방의 방장일때
			JPanel bottomPanel = new JPanel(); //하단 패널 생성
			bottomPanel.setBounds(0, 341, 244, 30);
			bottomPanel.setBackground(MyColor.midNightBlue);
			bottomPanel.setLayout(null);
			contentPane.add(bottomPanel);
			
			btnEditRoom = new JButton("방 설정 변경"); //방 설정 변경 버튼 생성 및 표시
			btnEditRoom.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			btnEditRoom.setBounds(0, 0, 244, 30);
			btnEditRoom.setBackground(MyColor.lightOrange);
			btnEditRoom.setForeground(MyColor.navy);
			btnEditRoom.addActionListener(new ActionListener() {	//버튼 클릭 이벤트 설정	
				@Override
				public void actionPerformed(ActionEvent e) {  //해당 버튼 클릭시
					EditRoomGUI gui = new EditRoomGUI(frame, callerRoom); //방 설정 변경 프레임 표시
				}
			});
			bottomPanel.add(btnEditRoom);
		}
		
		scrollPane = new JScrollPane(); //센터 패널 올릴 스크롤패널
		if(ownerId.equals(Client.id)) {
			scrollPane.setBounds(0, 50, 244, 291);
		} else {
			scrollPane.setBounds(0, 50, 244, 321);
		}	
		scrollPane.getViewport().setBackground(MyColor.midNightBlue);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
		centerPanel = new JPanel(); //사용자 목록 띄울 센터 패널
		centerPanel.setLayout(null);
		centerPanel.setBackground(MyColor.midNightBlue);
		scrollPane.setViewportView(centerPanel);
		
		popup = new JPopupMenu(); //팝업메뉴
		popup.setBackground(MyColor.lightGray);
		
		menu1 = new JMenuItem("방장 위임"); //팝업 메뉴 1번
		menu1.setBackground(MyColor.lightGray);
		menu1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		menu1.setHorizontalAlignment(JLabel.CENTER);
		menu1.addActionListener(new ActionListener() {	//메뉴 클릭 이벤트
			@Override
			public void actionPerformed(ActionEvent e) {//해당 메뉴 클릭시
				if(selectedUserId.equals(Client.id)) { //스스로를 선택했다면 안된다고 알림
					new CheckGUI(frame, "스스로를 지정할 수 없습니다.", false, false);
					return;
				}
				frame.dispose();
				Client.server.sendMessage("ROC§"+roomId+"§"+Client.id+"§"+selectedUserId); //서버로 선택한 대상 방장 위임 요청 전달
			}
		});
		
		menu2 = new JMenuItem("강제 퇴장"); //팝업 메뉴 2번
		menu2.setBackground(MyColor.lightGray);
		menu2.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		menu2.setHorizontalAlignment(JLabel.CENTER);
		menu2.addActionListener(new ActionListener() {		//메뉴 클릭 이벤트
			@Override
			public void actionPerformed(ActionEvent e) {//해당 메뉴 클릭시
				if(selectedUserId.equals(Client.id)) {//스스로를 선택했다면 안된다고 알림
					new CheckGUI(frame, "스스로를 지정할 수 없습니다.", false, false);
					return;
				}
				frame.dispose();
				Client.server.sendMessage("RUK§"+roomId+"§"+Client.id+"§"+selectedUserId); //서버로 선택한 대상 강제퇴장 요청 전달
			}
		});
		
		popup.add(menu1); //팝업 메뉴에 추가
		popup.addSeparator(); //구분선
		popup.add(menu2); 
		
		setUserList(); //센터패널에 사용자 목록 GUI로 띄움
		
		this.setVisible(true);
	}
	
	private void setUserList() { //센터패널에 사용자 목록 GUI로 띄우기
		
		int yPos = 0; //올릴 y위치
		
		for(String nickName : users.keySet()) { //사용자 탐색
			JLabel lbl = new JLabel("  "+nickName); //사용자의 닉네임을 라벨로 만듬
			lbl.setName(users.get(nickName)); //라벨 이름은 사용자 id로 설정
			lbl.setBackground(MyColor.navy);
			lbl.setForeground(MyColor.white);
			lbl.setOpaque(true);
			lbl.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			lbl.setBounds(0, yPos, scrollPane.getSize().width-20, 30);
			lbl.setBorder(new LineBorder(MyColor.white,1));
			if(ownerId.equals(Client.id)) { //사용자 목록 프레임을 띄운 대상이 방장이라면 마우스 클릭 이벤트 추가해줌
				lbl.addMouseListener(mouseEvent);
			}
			centerPanel.add(lbl);
			yPos += 30;
		}
		
		centerPanel.revalidate(); //센터패널 새로고침
		centerPanel.repaint();
	}
	
	private class SelectUserEvent implements MouseListener { //사용자 목록 라벨 클릭시
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(selectedUserLabel != null) { //이전에 선택한 사용자 라벨을 원래 배경색으로 설정
				selectedUserLabel.setBackground(MyColor.navy);
			}
			if(e.getButton() == MouseEvent.BUTTON3) { //사용자 라벨을 우클릭 했을 시
				JLabel l = (JLabel)e.getSource(); 
				selectedUserLabel = l; //선택한 라벨 저장
				selectedUserId = l.getName();
				l.setBackground(MyColor.darkBlue); //배경색 변경
				popup.show(l, e.getX(), e.getY()); //팝업메뉴 띄움
			} 
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
}
