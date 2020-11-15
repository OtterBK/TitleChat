package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Addon.MyColor;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FindPasswordGUI extends JFrame { //비밀번호 찾기 프레임

	private JPanel contentPane;
	private int frameWidth = 300;
	private int frameHeight = 400;
	private JTextField nameField;
	private JTextField idField;
	private JTextField telNumField;
	private JTextField nickNameField;
	private JButton submitBtn;
	private JLabel lblPw;
	private JLabel lblAlert;
	private static FindPasswordGUI lastFrame;
	
	public FindPasswordGUI() {
		if(lastFrame != null) { //프레임 2개 띄워지는거 방지
			lastFrame.dispose();
		}
		lastFrame = this;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		//화면 가운데
		
		contentPane = new JPanel(); //메인 패널
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(MyColor.midNightBlue);
		
		JLabel lblTitle = new JLabel("PW 찾기"); //각 라벨과 필드 설정
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(100, 10, 100, 40);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		JLabel lblId = new JLabel("ID"); //각 라벨과 필드 설정
		lblId.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblId.setBounds(40, 140, 60, 25);
		lblId.setForeground(MyColor.lightYellow);
		contentPane.add(lblId);
		
		idField = new JTextField(); //각 라벨과 필드 설정
		idField.setColumns(10);
		idField.setBounds(100, 140, 150, 25);
		idField.setBackground(MyColor.lightOrange);
		idField.setForeground(MyColor.navy);
		idField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(idField);
		
		JLabel lblName = new JLabel("이름"); //각 라벨과 필드 설정
		lblName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblName.setBounds(40, 175, 60, 25);
		lblName.setForeground(MyColor.lightYellow);
		contentPane.add(lblName);
		
		nameField = new JTextField(); //각 라벨과 필드 설정
		nameField.setColumns(10);
		nameField.setBounds(100, 175, 150, 25);
		nameField.setBackground(MyColor.lightOrange);
		nameField.setForeground(MyColor.navy);
		nameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nameField);
		
		telNumField = new JTextField(); //각 라벨과 필드 설정
		telNumField.setColumns(10);
		telNumField.setBounds(100, 210, 150, 25);
		telNumField.setBackground(MyColor.lightOrange);
		telNumField.setForeground(MyColor.navy);
		telNumField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(telNumField);
		
		JLabel lblTelNum = new JLabel("연락처"); //각 라벨과 필드 설정
		lblTelNum.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblTelNum.setBounds(40, 210, 60, 25);
		lblTelNum.setForeground(MyColor.lightYellow);
		contentPane.add(lblTelNum);
		
		nickNameField = new JTextField(); //각 라벨과 필드 설정
		nickNameField.setColumns(10);
		nickNameField.setBounds(100, 245, 150, 25);
		nickNameField.setBackground(MyColor.lightOrange);
		nickNameField.setForeground(MyColor.navy);
		nickNameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nickNameField);
		
		JLabel lblNickName = new JLabel("닉네임"); //각 라벨과 필드 설정
		lblNickName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblNickName.setBounds(40, 245, 60, 25);
		lblNickName.setForeground(MyColor.lightYellow);
		contentPane.add(lblNickName);
		
		submitBtn = new JButton("제출"); //비밀번호 찾기 버튼
		submitBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		submitBtn.setBounds(100, 300, 100, 40);
		submitBtn.setBackground(MyColor.lightOrange);
		submitBtn.setForeground(MyColor.navy);
		submitBtn.setBorder(new LineBorder(MyColor.white, 2));
		submitBtn.setBorderPainted(true);
		submitBtn.addActionListener(new MyEvent(this));
		contentPane.add(submitBtn);
		
		lblPw = new JLabel(""); //찾은 비밀번호가 띄워질 라벨
		lblPw.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lblPw.setHorizontalAlignment(SwingConstants.CENTER);
		lblPw.setBounds(25, 90, 250, 30);
		lblPw.setForeground(MyColor.lightOrange);
		contentPane.add(lblPw);
		
		lblAlert = new JLabel(""); //알림 메시지가 띄워질 라벨
		lblAlert.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlert.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		lblAlert.setBounds(25, 61, 250, 30);
		lblAlert.setForeground(MyColor.lightOrange);
		contentPane.add(lblAlert);
		
		this.setVisible(true);
	}
	
	private class MyEvent implements ActionListener{ //비밀번호 찾기 버튼 클릭 이벤트

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(submitBtn)) { //비밀번호 찾기 버튼 클릭시
				if (idField.getText().equals("")) { //id입력 확인
					CheckGUI cf = new CheckGUI(frame, "ID를 입력해주세요.", false, false);
				} else if (nameField.getText().equals("")) { //이름 입력 확인
					CheckGUI cf = new CheckGUI(frame, "이름을 입력해주세요.", false, false);
				} else if (telNumField.getText().equals("")) { //연락처 입력 확인
					CheckGUI cf = new CheckGUI(frame, "연락처를 입력해주세요.", false, false);
				} else if (nickNameField.getText().equals("")) { //닉네임 입력 확인
					CheckGUI cf = new CheckGUI(frame,"닉네임을 입력해주세요.", false, false);
				} else {
					String id = idField.getText(); //정보 저장
					String name = nameField.getText();
					String telNum = telNumField.getText();
					String nickName = nickNameField.getText();
					
					try { //연락처 조건 확인
						Integer.valueOf(telNum);
					} catch (Exception e1) {
						CheckGUI cf = new CheckGUI(frame,"연락처는 숫자만 입력가능합니다.", false, false);
						return;
					}
					
					if (id.contains("§") || name.contains("§")|| telNum.contains("§")|| nickName.contains("§") 
							||id.contains("¿") || name.contains("¿")|| telNum.contains("¿")|| nickName.contains("¿")) { //문자열 조건 확인
						CheckGUI cf = new CheckGUI(frame,"포함할 수 없는 문자열이 있습니다.", false, false);
					} else { 
						Client.server.sendMessage("FPW§" + id + "§" + name+ "§" + telNum+ "§" + nickName); //정보를 메시지로 합쳐 서버에 비밀번호 찾기 요청
						String response = Client.server.receiveMessage(); //찾기 결과
						String args[] = response.split("§"); //파싱
						if(args[0].equals("SUC")) { //찾기 성공시
							lblAlert.setText("가입시 사용한 비밀번호"); //알림 띄우고
							lblPw.setText(args[1]); //비밀번호 표시
						} else if(args[0].equals("FAL")){ //실패시
							lblAlert.setText(args[1]); //알림칸에 이유 띄움
						}			
					}			
				}
			} 
		}
		
	}
}
