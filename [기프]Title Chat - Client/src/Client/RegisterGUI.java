package Client;

import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Addon.MyColor;

public class RegisterGUI extends JFrame { //회원가입 프레임

	private static RegisterGUI lastFrame;
	private JPanel contentPane;
	private int frameWidth = 300;
	private int frameHeight = 400;
	private JTextField idField;
	private JTextField nameField;
	private JPasswordField pwField;
	private JPasswordField checkPwField;
	private JTextField telNumField;
	private JTextField nickNameField;
	private JButton submitBtn;
	
	public RegisterGUI() { //회원가입 프레임
		if(lastFrame != null) { //프레임 2개 띄워지는거 방지
			lastFrame.dispose();
		}
		lastFrame = this;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png"); //아이콘 설정
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		
		contentPane = new JPanel(); //메인패널
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(MyColor.midNightBlue);
		
		JLabel lblTitle = new JLabel("회원가입"); //상단 제목
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(100, 10, 100, 40);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		JLabel lblId = new JLabel("ID"); //ID 라벨
		lblId.setForeground(new Color(239, 249, 55));
		lblId.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblId.setBounds(40, 70, 60, 25);
		contentPane.add(lblId);
		
		idField = new JTextField(); //ID필드
		idField.setForeground(new Color(0, 0, 66));
		idField.setColumns(10);
		idField.setBorder(new LineBorder(MyColor.white, 2));
		idField.setBackground(new Color(247, 155, 60));
		idField.setBounds(100, 70, 150, 25);
		contentPane.add(idField);
		
		JLabel lblPW = new JLabel("PW"); //PW라벨
		lblPW.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblPW.setBounds(40, 105, 60, 25);
		lblPW.setForeground(MyColor.lightYellow);
		contentPane.add(lblPW);
		
		pwField = new JPasswordField(); //PW필드
		pwField.setEchoChar('*'); //해당 칸에는 입력시 * 로 표시함
		pwField.setColumns(10);
		pwField.setBounds(100, 105, 150, 25);
		pwField.setBackground(MyColor.lightOrange);
		pwField.setForeground(MyColor.navy);
		pwField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(pwField);
		
		JLabel lblCheckPw = new JLabel("PW확인"); //PW찾기 버튼
		lblCheckPw.setForeground(new Color(239, 249, 55));
		lblCheckPw.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblCheckPw.setBounds(40, 140, 60, 25);
		contentPane.add(lblCheckPw);
		
		checkPwField = new JPasswordField(); //PW확인 필드
		checkPwField.setForeground(new Color(0, 0, 66));
		checkPwField.setEchoChar('*'); //해당 칸에는 입력시 * 로 표시함
		checkPwField.setColumns(10);
		checkPwField.setBorder(new LineBorder(MyColor.white, 2));
		checkPwField.setBackground(new Color(247, 155, 60));
		checkPwField.setBounds(100, 140, 150, 25);
		contentPane.add(checkPwField);
		
		JLabel lblName = new JLabel("이름"); //이름 라벨
		lblName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblName.setBounds(40, 175, 60, 25);
		lblName.setForeground(MyColor.lightYellow);
		contentPane.add(lblName);
		
		nameField = new JTextField(); //이름 필드
		nameField.setColumns(10);
		nameField.setBounds(100, 175, 150, 25);
		nameField.setBackground(MyColor.lightOrange);
		nameField.setForeground(MyColor.navy);
		nameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nameField);
		
		JLabel lblTelNum = new JLabel("연락처"); //연락처 라벨
		lblTelNum.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblTelNum.setBounds(40, 210, 60, 25);
		lblTelNum.setForeground(MyColor.lightYellow);
		contentPane.add(lblTelNum);
		
		telNumField = new JTextField(); //연락처 필드
		telNumField.setColumns(10);
		telNumField.setBounds(100, 210, 150, 25);
		telNumField.setBackground(MyColor.lightOrange);
		telNumField.setForeground(MyColor.navy);
		telNumField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(telNumField);
		
		JLabel lblNickName = new JLabel("닉네임"); //닉네임 라벨
		lblNickName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblNickName.setBounds(40, 245, 60, 25);
		lblNickName.setForeground(MyColor.lightYellow);
		contentPane.add(lblNickName);
		
		nickNameField = new JTextField(); //닉네임 필드
		nickNameField.setColumns(10);
		nickNameField.setBounds(100, 245, 150, 25);
		nickNameField.setBackground(MyColor.lightOrange);
		nickNameField.setForeground(MyColor.navy);
		nickNameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nickNameField);
				
		submitBtn = new JButton("제출"); //제출 버튼
		submitBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		submitBtn.setBounds(100, 300, 100, 40);
		submitBtn.setBackground(MyColor.lightOrange);
		submitBtn.setForeground(MyColor.navy);
		submitBtn.setBorder(new LineBorder(MyColor.white, 2));
		submitBtn.setBorderPainted(true);
		submitBtn.addActionListener(new MyEvent(this));
		contentPane.add(submitBtn);
		
		this.setVisible(true);
	}
	
	private class MyEvent implements ActionListener{ //회원가입 버튼 클릭 이벤트

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(submitBtn)) { //회원가입 버튼 클릭시
				String id = idField.getText();
				String pw = ""; // 입력한 pw 저장할 곳
				char[] tmpPw = pwField.getPassword(); // 반환값이 char[] 이기 때문에 string 으로 바꾸기 위한 작업 필요
				for (char tmpCh : tmpPw) {
					Character.toString(tmpCh); // 한글자씩 가져와서 string으로 합침
					pw += tmpCh;
				}
				
				String checkpw = ""; // 입력한 pw 저장할 곳
				char[] tmpPw2 = checkPwField.getPassword(); // 반환값이 char[] 이기 때문에 string 으로 바꾸기 위한 작업 필요
				for (char tmpCh : tmpPw2) {
					Character.toString(tmpCh); // 한글자씩 가져와서 string으로 합침
					checkpw += tmpCh;
				}
				
				String name = nameField.getText();
				String telNum = telNumField.getText();
				String nickName = nickNameField.getText();
				
				if (id.equals("")) { //ID입력확인
					CheckGUI cf = new CheckGUI(frame, "ID를 입력해주세요.", false, false);
				} else if (pw.equals("")) { //PW입력확인
					CheckGUI cf = new CheckGUI(frame, "비밀번호를 입력해주세요.", false, false);
				} else if (checkpw.equals("")) { //PW확인칸 입력확인
					CheckGUI cf = new CheckGUI(frame, "비밀번호 확인칸을 입력해주세요.", false, false);
				}  else if (name.equals("")) { //이름 입력확인
					CheckGUI cf = new CheckGUI(frame, "이름을 입력해주세요.", false, false);
				} else if (telNum.equals("")) { //연락처 입력확인
					CheckGUI cf = new CheckGUI(frame, "연락처를 입력해주세요.", false, false);
				} else if (nickName.equals("")) { //닉네임 입력확인
					CheckGUI cf = new CheckGUI(frame, "닉네임을 입력해주세요.", false, false);
				} else {
					
					try { //연락처 조건확인
						Integer.valueOf(telNum);
					} catch (Exception e1) {
						CheckGUI cf = new CheckGUI(frame,"연락처는 숫자만 입력가능합니다.", false, false);
						return;
					}
					
					if(id.length() > 16) { //ID조건확인
						CheckGUI cf = new CheckGUI(frame,"ID는 16글자까지만 가능합니다.", false, false);
						return;
					}
					if(pw.length() > 16) { //pw조건확인
						CheckGUI cf = new CheckGUI(frame,"PW는 16글자까지만 가능합니다.", false, false);
						return;
					}
					if(nickName.length() > 10) { //닉네임 조건확인
						CheckGUI cf = new CheckGUI(frame,"닉네임은 10글자까지만 가능합니다.", false, false);
						return;
					}
					
					if (id.contains("§") || pw.contains("§") || name.contains("§")|| telNum.contains("§")|| nickName.contains("§") //문자열 조건확인
							||id.contains("¿") || pw.contains("¿") || name.contains("¿")|| telNum.contains("¿")|| nickName.contains("¿")) {
						CheckGUI cf = new CheckGUI(frame, "포함할 수 없는 문자열이 있습니다.", false, false);
					} else if (!pw.equals(checkpw)) { //비밀번호 일치 확인
						CheckGUI cf = new CheckGUI(frame, "비밀번호가 일치하지 않습니다.", false, false);
					} else {
						Client.server.sendMessage("RGS§" + id + "§" + pw + "§" + name+ "§" + telNum+ "§" + nickName); //서버에 회원가입 요청
						String response = Client.server.receiveMessage(); //결과값
						String args[] = response.split("§"); //파싱
						if(args[0].equals("SUC")) { //성공시
							CheckGUI cf = new CheckGUI(frame, "회원가입에 성공했습니다.", false, true);
						} else if(args[0].equals("FAL")){ //실패시 
							CheckGUI cf = new CheckGUI(frame, args[1], false, false); //이유 띄움
						}			
					}			
				}
			} 
		}
		
	}
}
