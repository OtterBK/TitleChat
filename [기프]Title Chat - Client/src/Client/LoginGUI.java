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
	private JPasswordField pwField; //��й�ȣ �Է�ĭ
	private JLabel lblId;
	private JLabel lblPw;
	private JButton findPWBtn;
	private JButton registerBtn;
	private JButton loginBtn;
	private int frameWidth = 600;
	private int frameHeight = 400;

	public LoginGUI() {
		Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
		MyEvent event = new MyEvent(this); //��ư Ŭ�� �̺�Ʈ
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		
		setTitle("Title Chat"); //����
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(MyColor.midNightBlue);
		
		JLabel lblTitle = new JLabel("Title Chat"); //��� ����
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("���� ���", Font.PLAIN, 36));
		lblTitle.setBounds(0, 0, 596, 84);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		idField = new JTextField(); //ID �ʵ�
		idField.setBounds(170, 160, 240, 30);
		idField.setColumns(10);
		idField.setBackground(MyColor.lightOrange);
		idField.setForeground(MyColor.navy);
		idField.setBorder(new LineBorder(MyColor.white, 2));
		idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if(event.getKeyCode() == 10) { // ����Ű Ű�� ��������
                	tryLogin();
                }
            }
        });
		contentPane.add(idField);		
		
		pwField = new JPasswordField(); //pw�ʵ�
		pwField.setEchoChar('*'); //�ش� ĭ���� �Է½� * �� ǥ����
		pwField.setColumns(10);
		pwField.setBounds(170, 210, 240, 30);
		pwField.setBackground(MyColor.lightOrange);
		pwField.setForeground(MyColor.navy);
		pwField.setBorder(new LineBorder(MyColor.white, 2));	
		pwField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if(event.getKeyCode() == 10) { // ����Ű Ű�� ��������
                	tryLogin();
                }
            }
        });
		contentPane.add(pwField);
		
		lblId = new JLabel("ID"); //iD�ʵ�
		lblId.setFont(new Font("���� ���", Font.BOLD, 20));
		lblId.setBounds(130, 160, 45, 30);
		lblId.setForeground(MyColor.lightYellow);
		contentPane.add(lblId);
		
		lblPw = new JLabel("PW"); //pw �ʵ�
		lblPw.setFont(new Font("���� ���", Font.BOLD, 20));
		lblPw.setBounds(130, 210, 45, 30);
		lblPw.setForeground(MyColor.lightYellow);
		contentPane.add(lblPw);
		
		findPWBtn = new JButton("PW ã��"); //pwã�� ��ư
		findPWBtn.setFont(new Font("���� ���", Font.BOLD, 18));
		findPWBtn.setBounds(160, 320, 120, 30);
		findPWBtn.setOpaque(false);
		findPWBtn.setContentAreaFilled(false);
		findPWBtn.setBorderPainted(false);
		findPWBtn.setForeground(MyColor.lightYellow);
		findPWBtn.addActionListener(event); //��ư Ŭ�� �̺�Ʈ
		contentPane.add(findPWBtn);
		
		registerBtn = new JButton("ȸ������"); //ȸ������ ��ư
		registerBtn.setFont(new Font("���� ���", Font.BOLD, 18));
		registerBtn.setBounds(320, 320, 120, 30);
		registerBtn.setOpaque(true);
		registerBtn.setOpaque(false);
		registerBtn.setContentAreaFilled(false);
		registerBtn.setBorderPainted(false);
		registerBtn.setForeground(MyColor.lightYellow);
		registerBtn.addActionListener(event); //��ư Ŭ�� �̺�Ʈ
		contentPane.add(registerBtn);
		
		loginBtn = new JButton("Login"); //�α��� ��ư
		loginBtn.setFont(new Font("���� ���", Font.BOLD, 12));
		loginBtn.setBounds(416, 160, 70, 80);
		loginBtn.setBackground(MyColor.lightOrange);
		loginBtn.setForeground(MyColor.navy);
		loginBtn.setBorder(new LineBorder(MyColor.white, 2));
		loginBtn.setBorderPainted(true);
		loginBtn.addActionListener(event); //��ư Ŭ�� �̺�Ʈ
		contentPane.add(loginBtn);
		
		Client.status = "�α���"; //Ŭ���̾�Ʈ ���� ȭ�� �α������� ����
		
		this.setVisible(true);
	}
	
	private void tryLogin() { //�α��� �õ�
		if (idField.getText().equals("")) { //ID�Է� Ȯ��
			CheckGUI cf = new CheckGUI(this, "ID�� �Է����ּ���.", false, false);
		} else if (pwField.getText().equals("")) { //pW�Է� Ȯ��
			CheckGUI cf = new CheckGUI(this, "PW�� �Է����ּ���.", false, false);
		} else {
			String id = idField.getText();
			String pw = ""; // �Է��� pw ������ ��
			char[] tmpPw = pwField.getPassword(); // ��ȯ���� char[] �̱� ������ string ���� �ٲٱ� ���� �۾� �ʿ�
			for (char tmpCh : tmpPw) {
				Character.toString(tmpCh); // �ѱ��ھ� �����ͼ� string���� ��ħ
				pw += tmpCh;
			}
			if (id.contains("��") || pw.contains("��") //���ڿ� ����  Ȯ��
					||id.contains("��") || pw.contains("��")) {
				CheckGUI cf = new CheckGUI(this, "������ �� ���� ���ڿ��� �ֽ��ϴ�.", false, false);
			} else {
				Client.server.sendMessage("LGI��" + id + "��" + pw); //������ �α��� �õ�
				String response = Client.server.receiveMessage(); //�����
				String args[] = response.split("��");//�Ľ�
				if(args[0].equals("FAL")) { //���н�
					CheckGUI cf = new CheckGUI(this, args[1], false, false); //���� ���
				} else { //������
					String name = args[1]; //�Ľ��� ������ ���� �����ͺ��̽��� ����� ���� ����
					String telNum = args[2];
					String nickName = args[3];
					
					Client.id = id; //�� ��� Client(static�ʵ�)�� ����
					Client.pw = pw;
					Client.name = name;
					Client.telNum = telNum;
					Client.nickName = nickName;
					
					this.dispose(); //�α��� ������ �ݱ�
					try { //Ȥ�� ���� try
						MainMenuGUI mainMenu = new MainMenuGUI(); //���θ޴� ������ ����
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
