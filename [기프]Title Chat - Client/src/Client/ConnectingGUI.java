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

public class ConnectingGUI extends JFrame { //������ ������

	private JFrame frame;
	private JPanel contentPane;
	private int frameWidth = 400;
	private int frameHeight = 300;
	private JLabel lblConnecting;
	
	public ConnectingGUI() {
		frame = this;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
		
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
		
		JLabel lblTitleChat = new JLabel("Title Chat"); //����
		lblTitleChat.setFont(new Font("���� ���", Font.BOLD, 34));
		lblTitleChat.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleChat.setBounds(100, 25, 200, 40);
		lblTitleChat.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitleChat);
		
		ImageIcon mainImg = new ImageIcon(ConnectingGUI.class.getResource("/resources/connect.png"));  //�ε� �̹���
		JLabel lblNewLabel = new JLabel(mainImg);
		lblNewLabel.setBounds(80, 105, 120, 120);
		contentPane.add(lblNewLabel);
		
		lblConnecting = new JLabel("������"); //�޽���
		lblConnecting.setSize(100, 50);
		lblConnecting.setLocation(220, 150);
		lblConnecting.setForeground(MyColor.lightYellow);
		lblConnecting.setFont(new Font("���� ���", Font.BOLD, 16));
		lblConnecting.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblConnecting);
		
		this.setVisible(true);
		
		new ConnectServer().start(); //���� ���� �õ� ����
		
	}
	
	class ConnectServer extends Thread{ //���� ���� �õ�
		
		int cnt = 0;
		boolean stopFlag = false; //������ �����
		
		public void run() {
			try {
				sleep(1500); //������ ������ 1.5�ʰ��� ������
				while(!stopFlag){
					boolean res = connect(); //���� �õ�
					sleep(100);
					if(res) { //���� ������ 
						stopFlag = true; //������ ���� �÷��� true
					}
					cnt++; //���� �õ� Ƚ�� 1����
					if(cnt > 9) { //�� 9�� �̻� �õ����� ��
						CheckGUI cf = new CheckGUI(null, "���� �ð� �ʰ�", true, false); //�˸� ���� ���α׷� ����
						stopFlag = true;
					}
					String str = "������"; //.�� �þ�� �ִϸ��̼� (ex, ������., ������.., ������...
					for(int i = 0; i < cnt%4; i++) {
						str += ".";
					}
					lblConnecting.setText(str);
				} 
				
			} catch (Exception e) {
			
			}
		}
		
		public boolean connect() { //���� ���� �õ�
			try {
				Socket socket = new Socket("127.0.0.1", 7000); //���� IP, Port
				Client.server = new MySocket(socket); //���� ������ �������� ����� ���� MySocket ����
				String msg = Client.server.receiveMessage(); //���� ���� �Ǵ� ���� �޽��� ����
				String args[] = msg.split("��"); //�Ľ�
				if(args[0].equals("FAL")) { //���� ���н�
					CheckGUI cf = new CheckGUI(frame, args[1], true, false); //���� ���� ���α׷� ����
				} else if(args[0].equals("ACT")) { //������
					frame.dispose(); //������ ������ �ݰ�
					LoginGUI loginFrame = new LoginGUI(); //�α��� ������ ���
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	
}
