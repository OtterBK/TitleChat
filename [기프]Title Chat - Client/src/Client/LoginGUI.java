package Client;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField idField;
	private JPasswordField pwField; //비밀번호 입력칸
	private JLabel lblId;
	private JLabel lblPw;
	private JButton findPWBtn;
	private JButton registerBtn;
	private JButton loginBtn;
	private int frameWidth = 600;
	private int frameHeight = 400;

	public LoginGUI() {
		Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
		MyEvent event = new MyEvent(this); //버튼 클릭 이벤트
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		
		setTitle("Title Chat"); //제목
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(MyColor.midNightBlue);
		
		JLabel lblTitle = new JLabel("Title Chat"); //상단 제목
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 36));
		lblTitle.setBounds(0, 0, 596, 84);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		idField = new JTextField(); //ID 필드
		idField.setBounds(170, 160, 240, 30);
		idField.setColumns(10);
		idField.setBackground(MyColor.lightOrange);
		idField.setForeground(MyColor.navy);
		idField.setBorder(new LineBorder(MyColor.white, 2));
		idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if(event.getKeyCode() == 10) { // 엔터키 키를 눌렀으면
                	tryLogin();
                }
            }
        });
		contentPane.add(idField);		
		
		pwField = new JPasswordField(); //pw필드
		pwField.setEchoChar('*'); //해당 칸에는 입력시 * 로 표시함
		pwField.setColumns(10);
		pwField.setBounds(170, 210, 240, 30);
		pwField.setBackground(MyColor.lightOrange);
		pwField.setForeground(MyColor.navy);
		pwField.setBorder(new LineBorder(MyColor.white, 2));	
		pwField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if(event.getKeyCode() == 10) { // 엔터키 키를 눌렀으면
                	tryLogin();
                }
            }
        });
		contentPane.add(pwField);
		
		lblId = new JLabel("ID"); //iD필드
		lblId.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblId.setBounds(130, 160, 45, 30);
		lblId.setForeground(MyColor.lightYellow);
		contentPane.add(lblId);
		
		lblPw = new JLabel("PW"); //pw 필드
		lblPw.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblPw.setBounds(130, 210, 45, 30);
		lblPw.setForeground(MyColor.lightYellow);
		contentPane.add(lblPw);
		
		findPWBtn = new JButton("PW 찾기"); //pw찾기 버튼
		findPWBtn.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		findPWBtn.setBounds(160, 320, 120, 30);
		findPWBtn.setOpaque(false);
		findPWBtn.setContentAreaFilled(false);
		findPWBtn.setBorderPainted(false);
		findPWBtn.setForeground(MyColor.lightYellow);
		findPWBtn.addActionListener(event); //버튼 클릭 이벤트
		contentPane.add(findPWBtn);
		
		registerBtn = new JButton("회원가입"); //회원가입 버튼
		registerBtn.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		registerBtn.setBounds(320, 320, 120, 30);
		registerBtn.setOpaque(true);
		registerBtn.setOpaque(false);
		registerBtn.setContentAreaFilled(false);
		registerBtn.setBorderPainted(false);
		registerBtn.setForeground(MyColor.lightYellow);
		registerBtn.addActionListener(event); //버튼 클릭 이벤트
		contentPane.add(registerBtn);
		
		loginBtn = new JButton("Login"); //로그인 버튼
		loginBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		loginBtn.setBounds(416, 160, 70, 80);
		loginBtn.setBackground(MyColor.lightOrange);
		loginBtn.setForeground(MyColor.navy);
		loginBtn.setBorder(new LineBorder(MyColor.white, 2));
		loginBtn.setBorderPainted(true);
		loginBtn.addActionListener(event); //버튼 클릭 이벤트
		contentPane.add(loginBtn);
		
		Client.status = "로그인"; //클라이언트 현재 화면 로그인으로 설정
		
		this.setVisible(true);
	}
	
	private void tryLogin() { //로그인 시도
		if (idField.getText().equals("")) { //ID입력 확인
			CheckGUI cf = new CheckGUI(this, "ID를 입력해주세요.", false, false);
		} else if (pwField.getText().equals("")) { //pW입려 확인
			CheckGUI cf = new CheckGUI(this, "PW를 입력해주세요.", false, false);
		} else {
			String id = idField.getText();
			String pw = ""; // 입력한 pw 저장할 곳
			char[] tmpPw = pwField.getPassword(); // 반환값이 char[] 이기 때문에 string 으로 바꾸기 위한 작업 필요
			for (char tmpCh : tmpPw) {
				Character.toString(tmpCh); // 한글자씩 가져와서 string으로 합침
				pw += tmpCh;
			}
			if (id.contains("§") || pw.contains("§") //문자열 조건  확인
					||id.contains("¿") || pw.contains("¿")) {
				CheckGUI cf = new CheckGUI(this, "포함할 수 없는 문자열이 있습니다.", false, false);
			} else {
				Client.server.sendMessage("LGI§" + id + "§" + pw); //서버로 로그인 시도
				String response = Client.server.receiveMessage(); //결과ㅣ
				String args[] = response.split("§");//파싱
				if(args[0].equals("FAL")) { //실패시
					CheckGUI cf = new CheckGUI(this, args[1], false, false); //이유 띄움
				} else { //성공시
					String name = args[1]; //파싱한 정보로 서버 데이터베이스의 사용자 정보 저장
					String telNum = args[2];
					String nickName = args[3];
					
					Client.id = id; //각 요소 Client(static필드)에 세팅
					Client.pw = pw;
					Client.name = name;
					Client.telNum = telNum;
					Client.nickName = nickName;
					
					this.dispose(); //로그인 프레임 닫기
					try { //혹시 몰라서 try
						MainMenuGUI mainMenu = new MainMenuGUI(); //메인메뉴 프레임 띄우기
					} catch (Exception e1) {
						System.exit(0);
					}
				}
			}			
		}
	}
	
	private class MyEvent implements ActionListener{

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(loginBtn)) {
				tryLogin();
			} else if(src.equals(findPWBtn)) {
				FindPasswordGUI fpwGui = new FindPasswordGUI();
			} else if(src.equals(registerBtn)) {
				RegisterGUI regGui = new RegisterGUI();
			}
		}
	}
}
