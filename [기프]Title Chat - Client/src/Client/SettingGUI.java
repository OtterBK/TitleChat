package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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

public class SettingGUI extends JFrame { //����� ���� ���� ������

	private static SettingGUI lastFrame;
	private JPanel contentPane;
	private int frameWidth = 300;
	private int frameHeight = 400;
	private JPasswordField pwField;
	private JPasswordField checkPwField;
	private JTextField nameField;
	private JTextField telNumField;
	private JTextField nickNameField;
	private JButton submitBtn;
	private JFrame parentFrame;
	
	public SettingGUI(JFrame parent) { //����� ���� ���� ������
		if(lastFrame != null) { //������ 2�� ������°� ����
			lastFrame.dispose();
		}
		lastFrame = this;
		this.parentFrame = parent;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png"); //������ ����
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		
		contentPane = new JPanel(); //�����г�
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(MyColor.midNightBlue);
		
		JLabel lblTitle = new JLabel("���� ����"); //��� ����
		lblTitle.setFont(new Font("���� ���", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(100, 10, 100, 40);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		JLabel lblPW = new JLabel("PW"); //pw��
		lblPW.setFont(new Font("���� ���", Font.BOLD, 16));
		lblPW.setBounds(40, 105, 60, 25);
		lblPW.setForeground(MyColor.lightYellow);
		contentPane.add(lblPW);
		
		pwField = new JPasswordField(); //pw�ʵ�
		pwField.setEchoChar('*'); //�ش� ĭ���� �Է½� * �� ǥ����
		pwField.setColumns(10);
		pwField.setBounds(100, 105, 150, 25);
		pwField.setBackground(MyColor.lightOrange);
		pwField.setForeground(MyColor.navy);
		pwField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(pwField);
		
		JLabel lblCheckPw = new JLabel("PWȮ��"); //pw��ġ Ȯ�� ��
		lblCheckPw.setForeground(new Color(239, 249, 55));
		lblCheckPw.setFont(new Font("���� ���", Font.BOLD, 14));
		lblCheckPw.setBounds(40, 140, 60, 25);
		contentPane.add(lblCheckPw);
		
		checkPwField = new JPasswordField(); //pw��ġ Ȯ�� �ʵ�
		checkPwField.setForeground(new Color(0, 0, 66));
		checkPwField.setEchoChar('*'); //�ش� ĭ���� �Է½� * �� ǥ����
		checkPwField.setColumns(10);
		checkPwField.setBorder(new LineBorder(MyColor.white, 2));
		checkPwField.setBackground(new Color(247, 155, 60));
		checkPwField.setBounds(100, 140, 150, 25);
		contentPane.add(checkPwField);
		
		JLabel lblName = new JLabel("�̸�"); //�̸� ��
		lblName.setFont(new Font("���� ���", Font.BOLD, 16));
		lblName.setBounds(40, 175, 60, 25);
		lblName.setForeground(MyColor.lightYellow);
		contentPane.add(lblName);
		
		nameField = new JTextField(); //�̸� �ʵ�
		nameField.setColumns(10);
		nameField.setBounds(100, 175, 150, 25);
		nameField.setBackground(MyColor.lightOrange);
		nameField.setForeground(MyColor.navy);
		nameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nameField);
		
		JLabel lblTelNum = new JLabel("����ó"); //����ó ��
		lblTelNum.setFont(new Font("���� ���", Font.BOLD, 16));
		lblTelNum.setBounds(40, 210, 60, 25);
		lblTelNum.setForeground(MyColor.lightYellow);
		contentPane.add(lblTelNum);
		
		telNumField = new JTextField(); //����ó �ʵ�
		telNumField.setColumns(10);
		telNumField.setBounds(100, 210, 150, 25);
		telNumField.setBackground(MyColor.lightOrange);
		telNumField.setForeground(MyColor.navy);
		telNumField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(telNumField);
		
		JLabel lblNickName = new JLabel("�г���"); //�г��� ��
		lblNickName.setFont(new Font("���� ���", Font.BOLD, 16));
		lblNickName.setBounds(40, 245, 60, 25);
		lblNickName.setForeground(MyColor.lightYellow);
		contentPane.add(lblNickName);
		
		nickNameField = new JTextField(); //�г��� �ʵ�
		nickNameField.setColumns(10);
		nickNameField.setBounds(100, 245, 150, 25);
		nickNameField.setBackground(MyColor.lightOrange);
		nickNameField.setForeground(MyColor.navy);
		nickNameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nickNameField);
				
		submitBtn = new JButton("����"); //���� ��ư
		submitBtn.setFont(new Font("���� ���", Font.PLAIN, 16));
		submitBtn.setBounds(100, 300, 100, 40);
		submitBtn.setBackground(MyColor.lightOrange);
		submitBtn.setForeground(MyColor.navy);
		submitBtn.setBorder(new LineBorder(MyColor.white, 2));
		submitBtn.setBorderPainted(true);
		submitBtn.addActionListener(new MyEvent(this));
		contentPane.add(submitBtn);
		
		nameField.setText(Client.name); //���� ����� ���� �����ͼ� ����
		telNumField.setText(Client.telNum);
		nickNameField.setText(Client.nickName);
		
		this.setVisible(true);
	}
	
	private class MyEvent implements ActionListener{ //���� ��ư Ŭ�� �̺�Ʈ

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(submitBtn)) { //�����ư Ŭ����
				String pw = ""; // �Է��� pw ������ ��
				char[] tmpPw = pwField.getPassword(); // ��ȯ���� char[] �̱� ������ string ���� �ٲٱ� ���� �۾� �ʿ�
				for (char tmpCh : tmpPw) {
					Character.toString(tmpCh); // �ѱ��ھ� �����ͼ� string���� ��ħ
					pw += tmpCh;
				}
				
				String checkpw = ""; // �Է��� pw ������ ��
				char[] tmpPw2 = checkPwField.getPassword(); // ��ȯ���� char[] �̱� ������ string ���� �ٲٱ� ���� �۾� �ʿ�
				for (char tmpCh : tmpPw2) {
					Character.toString(tmpCh); // �ѱ��ھ� �����ͼ� string���� ��ħ
					checkpw += tmpCh;
				}
				
				String name = nameField.getText(); //�Է� ���� ����
				String telNum = telNumField.getText();
				String nickName = nickNameField.getText();
				
				if (pw.equals("")) { //pw�Է�Ȯ��
					CheckGUI cf = new CheckGUI(frame, "��й�ȣ�� �Է����ּ���.", false, false);
				} else if (checkpw.equals("")) { //pwȮ��ĭ �Է� Ȯ��
					CheckGUI cf = new CheckGUI(frame, "��й�ȣ Ȯ��ĭ�� �Է����ּ���.", false, false);
				}  else if (name.equals("")) { //�̸� �Է�Ȯ��
					CheckGUI cf = new CheckGUI(frame, "�̸��� �Է����ּ���.", false, false);
				} else if (telNum.equals("")) { //����ó �Է�Ȯ��
					CheckGUI cf = new CheckGUI(frame, "����ó�� �Է����ּ���.", false, false);
				} else if (nickName.equals("")) { //�г��� �Է�Ȯ��
					CheckGUI cf = new CheckGUI(frame, "�г����� �Է����ּ���.", false, false);
				} else {
					
					try {//����ó ����Ȯ��
						Integer.valueOf(telNum);
					} catch (Exception e1) {
						CheckGUI cf = new CheckGUI(frame,"����ó�� ���ڸ� �Է°����մϴ�.", false, false);
						return;
					}
					
					if(pw.length() > 16) { //pw����Ȯ��
						CheckGUI cf = new CheckGUI(frame,"PW�� 16���ڱ����� �����մϴ�.", false, false);
						return;
					}
					
					if(nickName.length() > 10) { //�г��� ����Ȯ��
						CheckGUI cf = new CheckGUI(frame,"�г����� 10���ڱ����� �����մϴ�.", false, false);
						return;
					}
					
					if (pw.contains("��") || name.contains("��")|| telNum.contains("��")|| nickName.contains("��") //���ڿ� ���� Ȯ��
							||pw.contains("��") || name.contains("��")|| telNum.contains("��")|| nickName.contains("��")) {
						CheckGUI cf = new CheckGUI(frame, "������ �� ���� ���ڿ��� �ֽ��ϴ�.", false, false);
					} else if (!pw.equals(checkpw)) { //��й�ȣ ��ġ Ȯ��
						CheckGUI cf = new CheckGUI(frame, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", false, false);
					} else {
						Client.server.sendMessage("EUI��" + pw + "��" + name+ "��" + telNum+ "��" + nickName); //������ ����� ���� ���� ��û
						String response = Client.server.receiveMessage(); //�����
						String args[] = response.split("��"); //�Ľ�
						if(args[0].equals("FAL")) { //���н�
							CheckGUI cf = new CheckGUI(frame, args[1], false, false); //���� ���
						} else { //������
							Client.pw = pw; //�����Է��� ������ Ŭ���̾�Ʈ�� ����� ���� ����
							Client.name = name;
							Client.telNum = telNum;
							Client.nickName = nickName;
							CheckGUI cf = new CheckGUI(frame, "���������� �����߽��ϴ�.", false, true); //Ȯ��â�� �Բ� ����� ���� ����â ����
							if(parentFrame instanceof MainMenuGUI) { //���θ޴� ���ΰ�ħ
								MainMenuGUI mainMenu = (MainMenuGUI) parentFrame;
								mainMenu.refreshNickName(); //�г��� ���ΰ�ħ
								mainMenu.refreshRoom(); //ä�ù� ��� GUI ���ΰ�ħ
							}
						}
					}			
				}
			} 
		}
		
	}
}
