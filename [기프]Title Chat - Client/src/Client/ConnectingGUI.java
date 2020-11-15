package Client;

import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Addon.MyColor;
import Addon.MySocket;

public class ConnectingGUI extends JFrame { //접속중 프레임

	private JFrame frame;
	private JPanel contentPane;
	private int frameWidth = 400;
	private int frameHeight = 300;
	private JLabel lblConnecting;
	
	public ConnectingGUI() {
		frame = this;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //사용자의 화면 크기값을 얻기위한 툴킷 클래스 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(MyColor.midNightBlue);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitleChat = new JLabel("Title Chat"); //제목
		lblTitleChat.setFont(new Font("맑은 고딕", Font.BOLD, 34));
		lblTitleChat.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleChat.setBounds(100, 25, 200, 40);
		lblTitleChat.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitleChat);
		
		ImageIcon mainImg = new ImageIcon(ConnectingGUI.class.getResource("/resources/connect.png"));  //로딩 이미지
		JLabel lblNewLabel = new JLabel(mainImg);
		lblNewLabel.setBounds(80, 105, 120, 120);
		contentPane.add(lblNewLabel);
		
		lblConnecting = new JLabel("접속중"); //메시지
		lblConnecting.setSize(100, 50);
		lblConnecting.setLocation(220, 150);
		lblConnecting.setForeground(MyColor.lightYellow);
		lblConnecting.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblConnecting.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblConnecting);
		
		this.setVisible(true);
		
		new ConnectServer().start(); //서버 접속 시도 시작
		
	}
	
	class ConnectServer extends Thread{ //서버 접속 시도
		
		int cnt = 0;
		boolean stopFlag = false; //쓰레드 종료용
		
		public void run() {
			try {
				sleep(1500); //접속중 프레임 1.5초간은 보여줌
				while(!stopFlag){
					boolean res = connect(); //접속 시도
					sleep(100);
					if(res) { //접속 성공시 
						stopFlag = true; //쓰레드 종료 플래그 true
					}
					cnt++; //접속 시도 횟수 1증가
					if(cnt > 9) { //총 9번 이상 시도했을 시
						CheckGUI cf = new CheckGUI(null, "연결 시간 초과", true, false); //알림 띄우고 프로그램 종료
						stopFlag = true;
					}
					String str = "접속중"; //.이 늘어나는 애니메이션 (ex, 접속중., 접속중.., 접속중...
					for(int i = 0; i < cnt%4; i++) {
						str += ".";
					}
					lblConnecting.setText(str);
				} 
				
			} catch (Exception e) {
			
			}
		}
		
		public boolean connect() { //서버 접속 시도
			try {
				Socket socket = new Socket("127.0.0.1", 7000); //서버 IP, Port
				Client.server = new MySocket(socket); //접속 성공시 서버와의 통신을 위한 MySocket 생성
				String msg = Client.server.receiveMessage(); //접속 성공 또는 실패 메시지 수신
				String args[] = msg.split("§"); //파싱
				if(args[0].equals("FAL")) { //접속 실패시
					CheckGUI cf = new CheckGUI(frame, args[1], true, false); //이유 띄우고 프로그램 종료
				} else if(args[0].equals("ACT")) { //성공시
					frame.dispose(); //접속중 프레임 닫고
					LoginGUI loginFrame = new LoginGUI(); //로그인 프레임 띄움
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	
}
