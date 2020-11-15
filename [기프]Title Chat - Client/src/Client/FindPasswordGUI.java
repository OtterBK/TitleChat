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

public class FindPasswordGUI extends JFrame { //��й�ȣ ã�� ������

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
		if(lastFrame != null) { //������ 2�� ������°� ����
			lastFrame.dispose();
		}
		lastFrame = this;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		//ȭ�� ���
		
		contentPane = new JPanel(); //���� �г�
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(MyColor.midNightBlue);
		
		JLabel lblTitle = new JLabel("PW ã��"); //�� �󺧰� �ʵ� ����
		lblTitle.setFont(new Font("���� ���", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(100, 10, 100, 40);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		JLabel lblId = new JLabel("ID"); //�� �󺧰� �ʵ� ����
		lblId.setFont(new Font("���� ���", Font.BOLD, 16));
		lblId.setBounds(40, 140, 60, 25);
		lblId.setForeground(MyColor.lightYellow);
		contentPane.add(lblId);
		
		idField = new JTextField(); //�� �󺧰� �ʵ� ����
		idField.setColumns(10);
		idField.setBounds(100, 140, 150, 25);
		idField.setBackground(MyColor.lightOrange);
		idField.setForeground(MyColor.navy);
		idField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(idField);
		
		JLabel lblName = new JLabel("�̸�"); //�� �󺧰� �ʵ� ����
		lblName.setFont(new Font("���� ���", Font.BOLD, 16));
		lblName.setBounds(40, 175, 60, 25);
		lblName.setForeground(MyColor.lightYellow);
		contentPane.add(lblName);
		
		nameField = new JTextField(); //�� �󺧰� �ʵ� ����
		nameField.setColumns(10);
		nameField.setBounds(100, 175, 150, 25);
		nameField.setBackground(MyColor.lightOrange);
		nameField.setForeground(MyColor.navy);
		nameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nameField);
		
		telNumField = new JTextField(); //�� �󺧰� �ʵ� ����
		telNumField.setColumns(10);
		telNumField.setBounds(100, 210, 150, 25);
		telNumField.setBackground(MyColor.lightOrange);
		telNumField.setForeground(MyColor.navy);
		telNumField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(telNumField);
		
		JLabel lblTelNum = new JLabel("����ó"); //�� �󺧰� �ʵ� ����
		lblTelNum.setFont(new Font("���� ���", Font.BOLD, 16));
		lblTelNum.setBounds(40, 210, 60, 25);
		lblTelNum.setForeground(MyColor.lightYellow);
		contentPane.add(lblTelNum);
		
		nickNameField = new JTextField(); //�� �󺧰� �ʵ� ����
		nickNameField.setColumns(10);
		nickNameField.setBounds(100, 245, 150, 25);
		nickNameField.setBackground(MyColor.lightOrange);
		nickNameField.setForeground(MyColor.navy);
		nickNameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nickNameField);
		
		JLabel lblNickName = new JLabel("�г���"); //�� �󺧰� �ʵ� ����
		lblNickName.setFont(new Font("���� ���", Font.BOLD, 16));
		lblNickName.setBounds(40, 245, 60, 25);
		lblNickName.setForeground(MyColor.lightYellow);
		contentPane.add(lblNickName);
		
		submitBtn = new JButton("����"); //��й�ȣ ã�� ��ư
		submitBtn.setFont(new Font("���� ���", Font.PLAIN, 16));
		submitBtn.setBounds(100, 300, 100, 40);
		submitBtn.setBackground(MyColor.lightOrange);
		submitBtn.setForeground(MyColor.navy);
		submitBtn.setBorder(new LineBorder(MyColor.white, 2));
		submitBtn.setBorderPainted(true);
		submitBtn.addActionListener(new MyEvent(this));
		contentPane.add(submitBtn);
		
		lblPw = new JLabel(""); //ã�� ��й�ȣ�� ����� ��
		lblPw.setFont(new Font("���� ���", Font.PLAIN, 14));
		lblPw.setHorizontalAlignment(SwingConstants.CENTER);
		lblPw.setBounds(25, 90, 250, 30);
		lblPw.setForeground(MyColor.lightOrange);
		contentPane.add(lblPw);
		
		lblAlert = new JLabel(""); //�˸� �޽����� ����� ��
		lblAlert.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlert.setFont(new Font("���� ���", Font.PLAIN, 14));
		lblAlert.setBounds(25, 61, 250, 30);
		lblAlert.setForeground(MyColor.lightOrange);
		contentPane.add(lblAlert);
		
		this.setVisible(true);
	}
	
	private class MyEvent implements ActionListener{ //��й�ȣ ã�� ��ư Ŭ�� �̺�Ʈ

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(submitBtn)) { //��й�ȣ ã�� ��ư Ŭ����
				if (idField.getText().equals("")) { //id�Է� Ȯ��
					CheckGUI cf = new CheckGUI(frame, "ID�� �Է����ּ���.", false, false);
				} else if (nameField.getText().equals("")) { //�̸� �Է� Ȯ��
					CheckGUI cf = new CheckGUI(frame, "�̸��� �Է����ּ���.", false, false);
				} else if (telNumField.getText().equals("")) { //����ó �Է� Ȯ��
					CheckGUI cf = new CheckGUI(frame, "����ó�� �Է����ּ���.", false, false);
				} else if (nickNameField.getText().equals("")) { //�г��� �Է� Ȯ��
					CheckGUI cf = new CheckGUI(frame,"�г����� �Է����ּ���.", false, false);
				} else {
					String id = idField.getText(); //���� ����
					String name = nameField.getText();
					String telNum = telNumField.getText();
					String nickName = nickNameField.getText();
					
					try { //����ó ���� Ȯ��
						Integer.valueOf(telNum);
					} catch (Exception e1) {
						CheckGUI cf = new CheckGUI(frame,"����ó�� ���ڸ� �Է°����մϴ�.", false, false);
						return;
					}
					
					if (id.contains("��") || name.contains("��")|| telNum.contains("��")|| nickName.contains("��") 
							||id.contains("��") || name.contains("��")|| telNum.contains("��")|| nickName.contains("��")) { //���ڿ� ���� Ȯ��
						CheckGUI cf = new CheckGUI(frame,"������ �� ���� ���ڿ��� �ֽ��ϴ�.", false, false);
					} else { 
						Client.server.sendMessage("FPW��" + id + "��" + name+ "��" + telNum+ "��" + nickName); //������ �޽����� ���� ������ ��й�ȣ ã�� ��û
						String response = Client.server.receiveMessage(); //ã�� ���
						String args[] = response.split("��"); //�Ľ�
						if(args[0].equals("SUC")) { //ã�� ������
							lblAlert.setText("���Խ� ����� ��й�ȣ"); //�˸� ����
							lblPw.setText(args[1]); //��й�ȣ ǥ��
						} else if(args[0].equals("FAL")){ //���н�
							lblAlert.setText(args[1]); //�˸�ĭ�� ���� ���
						}			
					}			
				}
			} 
		}
		
	}
}
