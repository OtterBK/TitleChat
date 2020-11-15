package Client;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Addon.MyColor;
import javax.swing.JComboBox;

public class CreateRoomGUI extends JFrame { //채팅방 생성 프레임

	private JPanel contentPane;
	private int frameWidth = 300;
	private int frameHeight = 400;
	private JTextField nameField;
	private JTextArea introField;
	private JTextField maxField;
	private JButton submitBtn;
	private static CreateRoomGUI lastFrame;
	private JComboBox comboBox;
	private MainMenuGUI mainMenu; //메인메뉴 프레임

	public CreateRoomGUI(MainMenuGUI mainMenu) { //채팅방 생성 프레임
		if(lastFrame != null) { //프레임 2개 띄워지는거 방지
			lastFrame.dispose();
		}
		lastFrame = this;
		
		this.mainMenu = mainMenu; 
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png"); //아이콘 설정
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		
		contentPane = new JPanel(); //메인 패널
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(MyColor.midNightBlue);
		
		JLabel lblTitle = new JLabel("채팅방 생성"); //제목
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(75, 10, 150, 40);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		JLabel lblId = new JLabel("주제"); //각 필드와 라벨 설정
		lblId.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblId.setBounds(40, 90, 60, 25);
		lblId.setForeground(MyColor.lightYellow);
		contentPane.add(lblId);
		
		comboBox = new JComboBox(Client.titleList); //주제 선택 콤보박스, 콤보박스 목록은 Client 클래스의 titleList 사용
		comboBox.setBounds(100, 90, 150, 25);
		comboBox.setBackground(MyColor.lightOrange);
		comboBox.setForeground(MyColor.navy);
		contentPane.add(comboBox);
		
		JLabel lblName = new JLabel("제목"); //각 필드와 라벨 설정
		lblName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblName.setBounds(40, 125, 60, 25);
		lblName.setForeground(MyColor.lightYellow);
		contentPane.add(lblName);
		
		nameField = new JTextField(); //각 필드와 라벨 설정
		nameField.setColumns(10);
		nameField.setBounds(100, 125, 150, 25);
		nameField.setBackground(MyColor.lightOrange);
		nameField.setForeground(MyColor.navy);
		nameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nameField);
		
		introField = new JTextArea(); //각 필드와 라벨 설정
		introField.setColumns(10);
		introField.setBounds(100, 160, 150, 75);
		introField.setBackground(MyColor.lightOrange);
		introField.setForeground(MyColor.navy); 
		introField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(introField);
		
		JLabel lblTelNum = new JLabel("소개"); //각 필드와 라벨 설정
		lblTelNum.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblTelNum.setBounds(40, 180, 60, 25);
		lblTelNum.setForeground(MyColor.lightYellow);
		contentPane.add(lblTelNum);
		
		maxField = new JTextField();  //각 필드와 라벨 설정
		maxField.setColumns(10);
		maxField.setBounds(100, 245, 150, 25);
		maxField.setBackground(MyColor.lightOrange);
		maxField.setForeground(MyColor.navy);
		maxField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(maxField);
		
		JLabel lblNickName = new JLabel("인원");  //각 필드와 라벨 설정
		lblNickName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblNickName.setBounds(40, 245, 60, 25);
		lblNickName.setForeground(MyColor.lightYellow);
		contentPane.add(lblNickName);
		
		submitBtn = new JButton("제출");  //생성 버튼
		submitBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		submitBtn.setBounds(100, 300, 100, 40);
		submitBtn.setBackground(MyColor.lightOrange);
		submitBtn.setForeground(MyColor.navy);
		submitBtn.setBorder(new LineBorder(MyColor.white, 2));
		submitBtn.setBorderPainted(true);
		submitBtn.addActionListener(new MyEvent(this)); //버튼 클릭 이벤트 설정
		contentPane.add(submitBtn);
		
		this.setVisible(true);
	}
	
	private class MyEvent implements ActionListener{  //버튼 클릭 이벤트

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(submitBtn)) { //생성 버튼 클릭시
				if (Client.titleList[comboBox.getSelectedIndex()].equals("")) { //선택한 주제 가져와서 선택안했을시에
					CheckGUI cf = new CheckGUI(frame, "주제를 선택해주세요.", false, false);
				} else if (nameField.getText().equals("")) { //제목 비었을 시
					CheckGUI cf = new CheckGUI(frame, "제목을 입력해주세요.", false, false);
				} else if (introField.getText().equals("")) { //소개 비었을 시 
					CheckGUI cf = new CheckGUI(frame, "소개를 입력해주세요.", false, false);
				} else if (maxField.getText().equals("")) { //인원 입력 비었을 시
					CheckGUI cf = new CheckGUI(frame,"최대인원을 입력해주세요.", false, false);
				} else {
					String title = Client.titleList[comboBox.getSelectedIndex()]; //각 정보 저장
					String name = nameField.getText();
					String intro = introField.getText();
					String maxStr = maxField.getText();
					int maxCnt = 0; 
					
					try {
						maxCnt = Integer.valueOf(maxStr);
					}catch (Exception e1) {
						CheckGUI cf = new CheckGUI(frame,"최대인원에는 숫자만 입력이 가능합니다.", false, false);
						return;
					}
					
					if(maxCnt <= 0) { //최대인원 조건 확인
						CheckGUI cf = new CheckGUI(frame,"최대인원은 1명이상 이여야합니다.", false, false);
					} else {
						if(maxCnt >= 100) {
							CheckGUI cf = new CheckGUI(frame,"최대인원은 100명까지만 가능합니다.", false, false);
							return;
						} 
						if(intro.length() > 30) { //소개글 길이 확인
							CheckGUI cf = new CheckGUI(frame,"소개글은 30자이내로 작성해주세요.", false, false);
						} else { 
							if (name.contains("§")|| intro.contains("§")||name.contains("¿")|| intro.contains("¿")) { //문자열 조건 확인
								CheckGUI cf = new CheckGUI(frame,"포함할 수 없는 문자열이 있습니다.", false, false);
							} else {
								Client.server.sendMessage("CTR§" + title + "§" + name+ "§" + intro+ "§" + maxCnt); //서버에 채팅방 생성 신호 전달
								String response = Client.server.receiveMessage();
								try { //꼬임 방지
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String args[] = response.split("§"); //파싱
								if(args[0].equals("SUC")) { //성공시
									Client.server.sendMessage("JIR§"+args[1]); //생성한 채팅방 참가 신호 전달
									String msg = Client.server.receiveMessage();
									String args2[] = msg.split("§"); //파싱
									if(args2[0].equals("FAL")){ //참가 실패시
										CheckGUI cf = new CheckGUI(frame, args2[1], false, false); //이유 띄움
										mainMenu.refreshRoom();
									} else if(args2[0].equals("SUC")) { //참가 성공시
										Client.status = "채팅방";
										Client.server.stopListen();
										args2 = msg.split("¿"); //룸 정보 파싱
										String data[] = args2[0].split("§"); //룸 정보 파싱
										mainMenu.setVisible(false); //메인메뉴 숨김
										ChatroomGUI cr = new ChatroomGUI(Integer.valueOf(args[1]), data[2], data[3], data[4], Integer.valueOf(data[5]),data[1], mainMenu);
										//파싱한 정보로 채팅방 프레임 생성
										frame.dispose();
										for(int i = 1; i < args2.length; i++) { //채팅방 프레임의 사용자 정보 설정 
											String user[] = args2[i].split("§");
											cr.users.put(user[0], user[1]);
										}
									}
								} else if(args[0].equals("FAL")){ //생성 실패시
									CheckGUI cf = new CheckGUI(frame,"생성 실패", false, false); //이유 띄움
									mainMenu.refreshRoom();
								}				
							}	
						}	
					}											
				}
			} 
		}	
	}
}
