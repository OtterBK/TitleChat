package Client;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Addon.MyColor;

public class EditRoomGUI extends JFrame { //채팅방 정보 수정 프레임

	private JPanel contentPane;
	private int frameWidth = 300;
	private int frameHeight = 400;
	private JTextField nameField;
	private JTextArea introField;
	private JButton submitBtn;
	private static EditRoomGUI lastFrame;
	private JComboBox comboBox;
	private JFrame parentFrame;
	
	//방정보
	int roomId;
	private ChatroomGUI callerRoom;

	public EditRoomGUI(JFrame parent, ChatroomGUI room) { //채팅방 정보 수정 프레임
		if(lastFrame != null) { //프레임 2개 띄워지는거 방지
			lastFrame.dispose();
		}
		lastFrame = this;
		this.callerRoom = room;
		this.parentFrame = parent;
		roomId = callerRoom.getRoomId();
		
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
		
		JLabel lblTitle = new JLabel("채팅방 수정"); //제목 
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(75, 10, 150, 40);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		JLabel lblId = new JLabel("주제"); //각 라벨과 텍스트 필드 설정
		lblId.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblId.setBounds(40, 90, 60, 25);
		lblId.setForeground(MyColor.lightYellow);
		contentPane.add(lblId);
		
		comboBox = new JComboBox(Client.titleList);
		comboBox.setBounds(100, 90, 150, 25);
		comboBox.setBackground(MyColor.lightOrange);
		comboBox.setForeground(MyColor.navy);
		contentPane.add(comboBox);
		
		JLabel lblName = new JLabel("제목"); //각 라벨과 텍스트 필드 설정
		lblName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblName.setBounds(40, 125, 60, 25);
		lblName.setForeground(MyColor.lightYellow);
		contentPane.add(lblName);
		
		nameField = new JTextField(); //각 라벨과 텍스트 필드 설정
		nameField.setColumns(10);
		nameField.setBounds(100, 125, 150, 25);
		nameField.setBackground(MyColor.lightOrange);
		nameField.setForeground(MyColor.navy);
		nameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nameField);
		
		introField = new JTextArea(); //각 라벨과 텍스트 필드 설정
		introField.setColumns(10);
		introField.setBounds(100, 160, 150, 75);
		introField.setBackground(MyColor.lightOrange);
		introField.setForeground(MyColor.navy);
		introField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(introField);
		
		JLabel lblTelNum = new JLabel("소개"); //각 라벨과 텍스트 필드 설정
		lblTelNum.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblTelNum.setBounds(40, 180, 60, 25);
		lblTelNum.setForeground(MyColor.lightYellow);
		contentPane.add(lblTelNum);
		
		submitBtn = new JButton("수정"); //채팅방 수정 버튼
		submitBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		submitBtn.setBounds(100, 300, 100, 40);
		submitBtn.setBackground(MyColor.lightOrange);
		submitBtn.setForeground(MyColor.navy);
		submitBtn.setBorder(new LineBorder(MyColor.white, 2));
		submitBtn.setBorderPainted(true);
		submitBtn.addActionListener(new MyEvent(this));
		contentPane.add(submitBtn);
		
		nameField.setText(callerRoom.getRoomName());
		introField.setText(callerRoom.getRoomIntro());
		
		this.setVisible(true);
	}
	
	private class MyEvent implements ActionListener{ //수정 버튼 클릭 이벤트
 
		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(submitBtn)) {  //수정 버튼 클릭시
				if (Client.titleList[comboBox.getSelectedIndex()].equals("")) { //주제 선택 안했을 시
					CheckGUI cf = new CheckGUI(frame, "주제를 선택해주세요.", false, false);
				} else if (nameField.getText().equals("")) { //제목 입력 안했을 시 
					CheckGUI cf = new CheckGUI(frame, "제목을 입력해주세요.", false, false);
				} else if (introField.getText().equals("")) { //소개 입력 안했을 시
					CheckGUI cf = new CheckGUI(frame, "소개를 입력해주세요.", false, false);
				} else {
					String title = Client.titleList[comboBox.getSelectedIndex()]; //정보 저장
					String name = nameField.getText();
					String intro = introField.getText();
					if(intro.length() > 30) { //소개글 조건 확인
						CheckGUI cf = new CheckGUI(frame,"소개글은 30자이내로 작성해주세요.", false, false);
					} else {
						if (name.contains("§")|| intro.contains("§")||name.contains("¿")|| intro.contains("¿")) { //문자열 조건 확인
							CheckGUI cf = new CheckGUI(frame,"포함할 수 없는 문자열이 있습니다.", false, false);
						} else {
							Client.server.sendMessage("EDR§"+ roomId+"§"+ Client.id+"§"+ title + "§" + name+ "§" + intro); //채팅방 수정 메시지 전달
							frame.dispose(); //수정 창 닫음
							parentFrame.dispose(); //사용자 목록 창 닫음
						}
					}
				}
			} 
		}	
	}

}
