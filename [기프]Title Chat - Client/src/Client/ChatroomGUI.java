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
	
	//GUI ��ü �� ��
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
	
	///�� ����
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

	public ChatroomGUI(int roomId, String title, String name, String intro, int maxPlayer, String owner, MainMenuGUI mainMenu) { //ä�ù� GUI
		if(lastFrame != null) { //ä�ù� �������� 1���ۿ� ������ ���� 2�� ���� ����
			lastFrame.dispose();
		}
		lastFrame = this;
		
		this.roomId = roomId; //�ʱ�ȭ
		this.title = title;
		this.name = name;
		this.intro = intro;
		this.owner = owner;
		this.maxPlayer = maxPlayer;
		this.mainMenu = mainMenu;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png"); //icon�̹���
		ImageIcon icon = new ImageIcon(src); //������ ����
		this.setIconImage(icon.getImage()); 
		this.setTitle("Title Chat"); //Ÿ��Ʋ ����
		setResizable(false); //ũ�� �缳�� �Ұ���
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		//������ ũ�� ����
		
		this.addWindowListener(new JFrameWindowClosingEventHandler()); //â �ݱ� �̺�Ʈ
		
		MyEvent event = new MyEvent(this); //��ư Ŭ�� �̺�Ʈ
		
		contentPane = new JPanel(); //���� �г�
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(MyColor.midNightBlue);
		contentPane.setLayout(null); //���� ��ġ
		
		JPanel topPanel = new JPanel(); //���� �г�
		topPanel.setBorder(new LineBorder(MyColor.white,1));
		topPanel.setBackground(MyColor.midNightBlue);
		topPanel.setBounds(0, 0, 494, 100);
		topPanel.setLayout(null);
		contentPane.add(topPanel);
		
		ImageIcon img = new ImageIcon(ChatroomGUI.class.getResource("/resources/"+title+".png")); //������ ���� �̹��� ���� 
		titleImg = new JLabel(img); 
		titleImg.setBounds(10, 10, 50, 50);
		topPanel.add(titleImg);
		
		titleLabel = new JLabel(title); //ä�ù� ����
		titleLabel.setFont(new Font("���� ���", Font.PLAIN, 14));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(MyColor.lightYellow);
		titleLabel.setBounds(10, 70, 57, 15);
		topPanel.add(titleLabel);
		
		nameLabel = new JLabel(name); //ä�ù� �̸�
		nameLabel.setFont(new Font("���� ���", Font.BOLD, 16));
		nameLabel.setBounds(75, 10, 300, 30);
		nameLabel.setForeground(MyColor.lightYellow);
		topPanel.add(nameLabel);
		
		introLabel = new JLabel(MyUtility.lineSpacing(intro, 16)); //ä�ù� �Ұ�, 16���ڷ� ����
		introLabel.setVerticalAlignment(SwingConstants.TOP);
		introLabel.setFont(new Font("���� ���", Font.PLAIN, 12));
		introLabel.setBounds(75, 50, 300, 45);
		introLabel.setForeground(MyColor.lightYellow);
		topPanel.add(introLabel);
		
		ImageIcon setImg = new ImageIcon(ChatroomGUI.class.getResource("/resources/setting.png"));  //���� ������ ����
		settingImg = new JButton(setImg); //���� ��ư
		settingImg.setBorderPainted(false);
		settingImg.setContentAreaFilled(false);
		settingImg.setFocusPainted(false);
		settingImg.setBounds(400, 13, 75, 75);
		settingImg.addActionListener(event);
		topPanel.add(settingImg);
		
		JPanel bottomPanel = new JPanel(); //�ϴ� �г�
		bottomPanel.setBounds(0, 621, 494, 50);
		contentPane.add(bottomPanel);
		bottomPanel.setLayout(null);
		
		btnSend = new JButton("����"); //�޽��� ���� ��ư
		btnSend.setFont(new Font("���� ���", Font.BOLD, 16));
		btnSend.setBounds(428, 0, 66, 50);
		btnSend.setBackground(MyColor.lightOrange);
		btnSend.setForeground(MyColor.navy);
		btnSend.addActionListener(event);
		bottomPanel.add(btnSend);
		
		textArea = new JTextArea(); //�޽��� �Է� ������
		textArea.setFont(new Font("���� ���", Font.PLAIN, 16));
		textArea.setAutoscrolls(true);
		textArea.setLineWrap(true); 
		
		JScrollPane textScrollPane = new JScrollPane(); //�ؽ�Ʈ ����� �ø� ��ũ�� �г�
		textScrollPane.setBounds(0, 0, 429, 50);
		textScrollPane.getViewport().setBackground(MyColor.midNightBlue);
		textScrollPane.setViewportView(textArea);
		bottomPanel.add(textScrollPane);
		
		centerPanel = new JPanel(); //ä�ø޽����� ����� ������
		centerPanel.setBackground(MyColor.midNightBlue);
		centerPanel.setLayout(null);
		
		scrollPane = new JScrollPane(); //ä�� �޽����� �����Ǵ� �����г��� �ø� ��ũ�� �г�
		scrollPane.setBounds(0, 100, 494, 519);
		scrollPane.getViewport().setBackground(MyColor.midNightBlue);
		scrollPane.setViewportView(centerPanel);
		scrollPane.getViewport().setBackground(MyColor.midNightBlue);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() { //��ũ�ѹ� ������
            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = MyColor.navy;
            }
        });
		contentPane.add(scrollPane);	
		
		Client.status = "ä�ù�"; //����� ���� ȭ�� ���¸� ä�ù����� ����
		
		Client.server.startListen(this); //��� ������ ��� �����Ͽ� �������� ���� ��ȣ ���� ����
		
		this.setVisible(true); //������ ǥ��
		
		printAlert(Client.nickName+" ���� �����ϼ̽��ϴ�."); //���� �˸� ���
	}
	
	public void kicked() {  //��������� ó��
		mainMenu.setVisible(true); //���θ޴� ǥ��
		lastFrame.dispose(); //������ ����
		mainMenu.refreshRoom(); //���θ޴� ���ΰ�ħ
		CheckGUI ch = new CheckGUI(mainMenu, "����� �������� �Ǿ����ϴ�.", false, false);
	}
	
	public int getRoomId() { //ä�ù� id��ȯ
		return this.roomId;
	}
	
	public int getMax() { //�ִ��ο� ��ȯ
		return this.maxPlayer;
	}
	
	public String getRoomName() { //ä�ù� �̸� ��ȯ
		return this.name;
	}
	
	public String getRoomIntro() { //ä�ù� �Ұ� ��ȯ
		return this.intro;
	}
	
	public String getRoomOwner() { //ä�ù� ���� ��ȯ
		return this.owner;
	}
	
	public LinkedHashMap<String, String> getUsers() { //ä�ù� ����� ��� ��ȯ
		return this.users;
	}
	
	public void changedRoomInfo(String msg) { //ä�ù� ������ ����ʿ� ���� ó��
		String args[] = msg.split("��"); //�Ľ�
		
		String roomInfo[] = args[0].split("��"); //�Ľ�
		
		String title = roomInfo[2]; //���ο� ����
		String name = roomInfo[3]; //���ο� ����
		String intro = roomInfo[4]; //���ο� �Ұ�
		String owner = roomInfo[5]; //���ο� ����
		
		boolean roomChange = false; //�� ������ ����Ǿ��°�? �ƴϸ� �ο���?
		
		if(!this.title.equals(title)) { //�⺻ �������� �� ������ ��
			this.title = title; //�ٸ��� ���ο���� ����
			roomChange = true; //�� ���� ����� ����
		}
		if(!this.name.equals(name)) {//�⺻ ���񰪰� �� ���� ��
			this.name = name;//�ٸ��� ���ο���� ����
			roomChange = true;//�� ���� ����� ����
		}
		if(!this.intro.equals(intro)) {//�⺻ �Ұ����� �� �Ұ��� ��
			this.intro = intro;//�ٸ��� ���ο���� ����
			roomChange = true;//�� ���� ����� ����
		}
		if(!this.owner.equals(owner)) {//�⺻ ���尪�� �� ���尪 ��
			this.owner = owner;//�ٸ��� ���ο���� ����
			roomChange = true;//�� ���� ����� ����
		}
		
		if(roomChange) { //������ ����Ǿ��ٸ�
			ImageIcon img = new ImageIcon(ChatroomGUI.class.getResource("/resources/"+this.title+".png")); //���ο� ���� �̹����� ����
			titleImg.setIcon(img); //�� ���� ����ó��
			titleLabel.setText(this.title);
			nameLabel.setText(this.name);
			introLabel.setText(this.intro);
			printAlert("�� ������ ����Ǿ����ϴ�."); //�˸� ���
		}
		
		users.clear(); //�ο� ��� �ʱ�ȭ
		for(int i = 1; i < args.length; i++) { //���ι��� �ο���ϰ� ����
			String user[] = args[i].split("��");
			users.put(user[0], user[1]);
		}
		
	}
	
	public void printAlert(String msg) { //�˸� ���
		int lineMax = 29; //���� �ִ� ��
		int line = (int) (msg.length()/lineMax)+1; //ä�ø޽��� �� ��, �� ���� ����  �� ���� ����
		int labelHeight = 24; //���ٴ� �� ����
		int comHeight = line*labelHeight; //��ü ����
		JLabel alertMsg = new JLabel(MyUtility.lineSpacing(msg, lineMax)); //�˸� �޽��� ���� �ִ밪���� ����
		alertMsg.setBounds(7, centerHeight, scrollPane.getSize().width-35, comHeight); //�� ũ�� , ��ġ ����
		alertMsg.setForeground(MyColor.lightOrange);  
		alertMsg.setFont(new Font("���� ���", Font.BOLD, 16));
		centerHeight += comHeight+4;  
		if(centerHeight > scrollPane.getSize().getHeight()) {//���� �г� ���� ����
			centerPanel.setPreferredSize(new Dimension((int) centerPanel.getSize().getWidth(), centerHeight+10));
		}
		lastEnter = ""; //������ �Է��� �������� ����
		centerPanel.add(alertMsg);
		
		centerPanel.revalidate();
		centerPanel.repaint();
	}
	
	public void printChat(String args[]) { //ä�� �޽��� ���
		String nickName = args[1];
		String chat = args[2];
		int lineMax = 31; //���� �ִ� ���ڱ���
		int line = (int) (chat.length()/lineMax)+1; //�� ��, �̰ſ� ���� �� ���� ���� 
		int labelHeight = 24; //�� �� �� ����
		int nickNameHeight = 0;
		int comHeight = 0;
		int comWidth = 0;
		if(chat.contains("\n")) { //���� ��ȣ ���� ��
			int cnt = 0;
		    int fromIndex = -1;
		    while ((fromIndex = chat.indexOf("\n", fromIndex + 1)) >= 0) {
		      cnt++;
		    }
		    line += cnt; //�����ȣ ��ŭ �� �� ����
		}
		comHeight = line*labelHeight;
		/*if(chat.length() >= lineMax || chat.contains("\n")) { //���ڼ��� ���� ���� ���α��̸� �����Ϸ� ������
			comWidth = scrollPane.getSize().width-35;         //���ڿ����� (ex)����, ����) ���� ���α��̰� �޶���
		} else {												//�ִ�ġ�� �����ϴ� ���� �� �����
			comWidth = (int)(chat.length() * 14.5)+5;
		}*/
		comWidth = scrollPane.getSize().width-35;
		
		if(!lastEnter.equalsIgnoreCase(nickName)) { //������ ä���� ģ ������� �г��Ӱ� ���� ģ ������� �г����� ���� ������ �г��� ǥ��
			JLabel nickNameLbl = new JLabel(nickName);
			nickNameLbl.setBounds(7, centerHeight, scrollPane.getSize().width-35, labelHeight);
			nickNameLbl.setFont(new Font("���� ���", Font.BOLD, 17));
			nickNameLbl.setForeground(MyColor.white);
			nickNameHeight = 25;
			centerPanel.add(nickNameLbl);
			lastEnter = nickName;
		}
		//������ �г��� ǥ�� x
		
		//ä�� �޽��� ��
		JLabel chatLbl = new JLabel(MyUtility.lineSpacing(chat, lineMax));
		chatLbl.setFont(new Font("���� ���", Font.PLAIN, 14));
		chatLbl.setBounds(7, centerHeight+nickNameHeight, comWidth, comHeight); //�� ��ġ �� ũ�� ����
		chatLbl.setForeground(MyColor.navy);
		chatLbl.setBackground(MyColor.lightOrange);
		chatLbl.setBorder(new LineBorder(MyColor.white,1));
		chatLbl.setOpaque(true);
			
		centerHeight += comHeight+nickNameHeight+4; //���� �г� ���� ����
		if(centerHeight > scrollPane.getSize().getHeight()) {
			centerPanel.setPreferredSize(new Dimension((int) centerPanel.getSize().getWidth(), centerHeight+10));
		}
		
		centerPanel.add(chatLbl);
		
		centerPanel.revalidate();
		centerPanel.repaint();
		
		//ä���� �Էµ� �� ��ũ���� ���� �Ʒ��� �������ϴµ� �ٷ� ������ ���� �Ʒ��� ���������� ����
		//�����带 �����Ͽ� 0.05�� �Ŀ� ��ũ�ѹٸ� ���� �Ʒ��� �������� ���
		scrollDown sd = new scrollDown(); 
		sd.start();
	
	}
	
	private void sendChat() { //������ ä�� �޽��� �Է� ����
		String text = textArea.getText();
		if(text != "") {
			Client.server.sendMessage("SCM��"+text);
			textArea.requestFocus();
			textArea.setText("");
		}
	}
	
	private class JFrameWindowClosingEventHandler extends WindowAdapter { //â ������ ä�ù� ���� ó�� �� ���θ޴� ������ ǥ��
		public void windowClosing(WindowEvent e) {
			Client.server.sendMessage("QTR��"); //���� ó�� �޽��� ����
			mainMenu.setVisible(true); //���θ޴� ������ ǥ��
			lastFrame.dispose(); //ä��â ����
			mainMenu.refreshRoom(); //���θ޴� ���ΰ�ħ
		}
	}
	
	private class MyEvent implements ActionListener{ //��ư Ŭ���̺�Ʈ

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(settingImg)) { //���� �̹��� Ŭ����
				UserListGUI ug = new UserListGUI((ChatroomGUI)frame); //ä�ù� ���â ���
			} else if(src.equals(btnSend)) { //���� ��ư Ŭ����
				sendChat(); //�޽��� ����
			}
		}
	}
	
	private class scrollDown extends Thread{ //0.5���� ��ũ�� ���� �Ʒ���
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
