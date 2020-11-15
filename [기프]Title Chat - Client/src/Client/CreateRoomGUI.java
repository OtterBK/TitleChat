package Client;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Addon.MyColor;
import javax.swing.JComboBox;

public class CreateRoomGUI extends JFrame { //ä�ù� ���� ������

	private JPanel contentPane;
	private int frameWidth = 300;
	private int frameHeight = 400;
	private JTextField nameField;
	private JTextArea introField;
	private JTextField maxField;
	private JButton submitBtn;
	private static CreateRoomGUI lastFrame;
	private JComboBox comboBox;
	private MainMenuGUI mainMenu; //���θ޴� ������

	public CreateRoomGUI(MainMenuGUI mainMenu) { //ä�ù� ���� ������
		if(lastFrame != null) { //������ 2�� ������°� ����
			lastFrame.dispose();
		}
		lastFrame = this;
		
		this.mainMenu = mainMenu; 
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png"); //������ ����
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		
		contentPane = new JPanel(); //���� �г�
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(MyColor.midNightBlue);
		
		JLabel lblTitle = new JLabel("ä�ù� ����"); //����
		lblTitle.setFont(new Font("���� ���", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(75, 10, 150, 40);
		lblTitle.setForeground(MyColor.lightYellow);
		contentPane.add(lblTitle);
		
		JLabel lblId = new JLabel("����"); //�� �ʵ�� �� ����
		lblId.setFont(new Font("���� ���", Font.BOLD, 16));
		lblId.setBounds(40, 90, 60, 25);
		lblId.setForeground(MyColor.lightYellow);
		contentPane.add(lblId);
		
		comboBox = new JComboBox(Client.titleList); //���� ���� �޺��ڽ�, �޺��ڽ� ����� Client Ŭ������ titleList ���
		comboBox.setBounds(100, 90, 150, 25);
		comboBox.setBackground(MyColor.lightOrange);
		comboBox.setForeground(MyColor.navy);
		contentPane.add(comboBox);
		
		JLabel lblName = new JLabel("����"); //�� �ʵ�� �� ����
		lblName.setFont(new Font("���� ���", Font.BOLD, 16));
		lblName.setBounds(40, 125, 60, 25);
		lblName.setForeground(MyColor.lightYellow);
		contentPane.add(lblName);
		
		nameField = new JTextField(); //�� �ʵ�� �� ����
		nameField.setColumns(10);
		nameField.setBounds(100, 125, 150, 25);
		nameField.setBackground(MyColor.lightOrange);
		nameField.setForeground(MyColor.navy);
		nameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nameField);
		
		introField = new JTextArea(); //�� �ʵ�� �� ����
		introField.setColumns(10);
		introField.setBounds(100, 160, 150, 75);
		introField.setBackground(MyColor.lightOrange);
		introField.setForeground(MyColor.navy); 
		introField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(introField);
		
		JLabel lblTelNum = new JLabel("�Ұ�"); //�� �ʵ�� �� ����
		lblTelNum.setFont(new Font("���� ���", Font.BOLD, 16));
		lblTelNum.setBounds(40, 180, 60, 25);
		lblTelNum.setForeground(MyColor.lightYellow);
		contentPane.add(lblTelNum);
		
		maxField = new JTextField();  //�� �ʵ�� �� ����
		maxField.setColumns(10);
		maxField.setBounds(100, 245, 150, 25);
		maxField.setBackground(MyColor.lightOrange);
		maxField.setForeground(MyColor.navy);
		maxField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(maxField);
		
		JLabel lblNickName = new JLabel("�ο�");  //�� �ʵ�� �� ����
		lblNickName.setFont(new Font("���� ���", Font.BOLD, 16));
		lblNickName.setBounds(40, 245, 60, 25);
		lblNickName.setForeground(MyColor.lightYellow);
		contentPane.add(lblNickName);
		
		submitBtn = new JButton("����");  //���� ��ư
		submitBtn.setFont(new Font("���� ���", Font.PLAIN, 16));
		submitBtn.setBounds(100, 300, 100, 40);
		submitBtn.setBackground(MyColor.lightOrange);
		submitBtn.setForeground(MyColor.navy);
		submitBtn.setBorder(new LineBorder(MyColor.white, 2));
		submitBtn.setBorderPainted(true);
		submitBtn.addActionListener(new MyEvent(this)); //��ư Ŭ�� �̺�Ʈ ����
		contentPane.add(submitBtn);
		
		this.setVisible(true);
	}
	
	private class MyEvent implements ActionListener{  //��ư Ŭ�� �̺�Ʈ

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(submitBtn)) { //���� ��ư Ŭ����
				if (Client.titleList[comboBox.getSelectedIndex()].equals("")) { //������ ���� �����ͼ� ���þ������ÿ�
					CheckGUI cf = new CheckGUI(frame, "������ �������ּ���.", false, false);
				} else if (nameField.getText().equals("")) { //���� ����� ��
					CheckGUI cf = new CheckGUI(frame, "������ �Է����ּ���.", false, false);
				} else if (introField.getText().equals("")) { //�Ұ� ����� �� 
					CheckGUI cf = new CheckGUI(frame, "�Ұ��� �Է����ּ���.", false, false);
				} else if (maxField.getText().equals("")) { //�ο� �Է� ����� ��
					CheckGUI cf = new CheckGUI(frame,"�ִ��ο��� �Է����ּ���.", false, false);
				} else {
					String title = Client.titleList[comboBox.getSelectedIndex()]; //�� ���� ����
					String name = nameField.getText();
					String intro = introField.getText();
					String maxStr = maxField.getText();
					int maxCnt = 0; 
					
					try {
						maxCnt = Integer.valueOf(maxStr);
					}catch (Exception e1) {
						CheckGUI cf = new CheckGUI(frame,"�ִ��ο����� ���ڸ� �Է��� �����մϴ�.", false, false);
						return;
					}
					
					if(maxCnt <= 0) { //�ִ��ο� ���� Ȯ��
						CheckGUI cf = new CheckGUI(frame,"�ִ��ο��� 1���̻� �̿����մϴ�.", false, false);
					} else {
						if(maxCnt >= 100) {
							CheckGUI cf = new CheckGUI(frame,"�ִ��ο��� 100������� �����մϴ�.", false, false);
							return;
						} 
						if(intro.length() > 30) { //�Ұ��� ���� Ȯ��
							CheckGUI cf = new CheckGUI(frame,"�Ұ����� 30���̳��� �ۼ����ּ���.", false, false);
						} else { 
							if (name.contains("��")|| intro.contains("��")||name.contains("��")|| intro.contains("��")) { //���ڿ� ���� Ȯ��
								CheckGUI cf = new CheckGUI(frame,"������ �� ���� ���ڿ��� �ֽ��ϴ�.", false, false);
							} else {
								Client.server.sendMessage("CTR��" + title + "��" + name+ "��" + intro+ "��" + maxCnt); //������ ä�ù� ���� ��ȣ ����
								String response = Client.server.receiveMessage();
								try { //���� ����
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String args[] = response.split("��"); //�Ľ�
								if(args[0].equals("SUC")) { //������
									Client.server.sendMessage("JIR��"+args[1]); //������ ä�ù� ���� ��ȣ ����
									String msg = Client.server.receiveMessage();
									String args2[] = msg.split("��"); //�Ľ�
									if(args2[0].equals("FAL")){ //���� ���н�
										CheckGUI cf = new CheckGUI(frame, args2[1], false, false); //���� ���
										mainMenu.refreshRoom();
									} else if(args2[0].equals("SUC")) { //���� ������
										Client.status = "ä�ù�";
										Client.server.stopListen();
										args2 = msg.split("��"); //�� ���� �Ľ�
										String data[] = args2[0].split("��"); //�� ���� �Ľ�
										mainMenu.setVisible(false); //���θ޴� ����
										ChatroomGUI cr = new ChatroomGUI(Integer.valueOf(args[1]), data[2], data[3], data[4], Integer.valueOf(data[5]),data[1], mainMenu);
										//�Ľ��� ������ ä�ù� ������ ����
										frame.dispose();
										for(int i = 1; i < args2.length; i++) { //ä�ù� �������� ����� ���� ���� 
											String user[] = args2[i].split("��");
											cr.users.put(user[0], user[1]);
										}
									}
								} else if(args[0].equals("FAL")){ //���� ���н�
									CheckGUI cf = new CheckGUI(frame,"���� ����", false, false); //���� ���
									mainMenu.refreshRoom();
								}				
							}	
						}	
					}											
				}
			} 
		}	
	}
}
