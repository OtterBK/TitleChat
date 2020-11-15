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

public class EditRoomGUI extends JFrame { //ä�ù� ���� ���� ������

	private JPanel contentPane;
	private int frameWidth = 300;
	private int frameHeight = 400;
	private JTextField nameField;
	private JTextArea introField;
	private JButton submitBtn;
	private static EditRoomGUI lastFrame;
	private JComboBox comboBox;
	private JFrame parentFrame;
	
	//������
	int roomId;
	private ChatroomGUI callerRoom;

	public EditRoomGUI(JFrame parent, ChatroomGUI room) { //ä�ù� ���� ���� ������
		if(lastFrame != null) { //������ 2�� ������°� ����
			lastFrame.dispose();
		}
		lastFrame = this;
		this.callerRoom = room;
		this.parentFrame = parent;
		roomId = callerRoom.getRoomId();
		
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
		
		JLabel lblId = new JLabel("����"); //�� �󺧰� �ؽ�Ʈ �ʵ� ����
		lblId.setFont(new Font("���� ���", Font.BOLD, 16));
		lblId.setBounds(40, 90, 60, 25);
		lblId.setForeground(MyColor.lightYellow);
		contentPane.add(lblId);
		
		comboBox = new JComboBox(Client.titleList);
		comboBox.setBounds(100, 90, 150, 25);
		comboBox.setBackground(MyColor.lightOrange);
		comboBox.setForeground(MyColor.navy);
		contentPane.add(comboBox);
		
		JLabel lblName = new JLabel("����"); //�� �󺧰� �ؽ�Ʈ �ʵ� ����
		lblName.setFont(new Font("���� ���", Font.BOLD, 16));
		lblName.setBounds(40, 125, 60, 25);
		lblName.setForeground(MyColor.lightYellow);
		contentPane.add(lblName);
		
		nameField = new JTextField(); //�� �󺧰� �ؽ�Ʈ �ʵ� ����
		nameField.setColumns(10);
		nameField.setBounds(100, 125, 150, 25);
		nameField.setBackground(MyColor.lightOrange);
		nameField.setForeground(MyColor.navy);
		nameField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(nameField);
		
		introField = new JTextArea(); //�� �󺧰� �ؽ�Ʈ �ʵ� ����
		introField.setColumns(10);
		introField.setBounds(100, 160, 150, 75);
		introField.setBackground(MyColor.lightOrange);
		introField.setForeground(MyColor.navy);
		introField.setBorder(new LineBorder(MyColor.white, 2));
		contentPane.add(introField);
		
		JLabel lblTelNum = new JLabel("�Ұ�"); //�� �󺧰� �ؽ�Ʈ �ʵ� ����
		lblTelNum.setFont(new Font("���� ���", Font.BOLD, 16));
		lblTelNum.setBounds(40, 180, 60, 25);
		lblTelNum.setForeground(MyColor.lightYellow);
		contentPane.add(lblTelNum);
		
		submitBtn = new JButton("����"); //ä�ù� ���� ��ư
		submitBtn.setFont(new Font("���� ���", Font.PLAIN, 16));
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
	
	private class MyEvent implements ActionListener{ //���� ��ư Ŭ�� �̺�Ʈ
 
		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton) e.getSource();
			if (src.equals(submitBtn)) {  //���� ��ư Ŭ����
				if (Client.titleList[comboBox.getSelectedIndex()].equals("")) { //���� ���� ������ ��
					CheckGUI cf = new CheckGUI(frame, "������ �������ּ���.", false, false);
				} else if (nameField.getText().equals("")) { //���� �Է� ������ �� 
					CheckGUI cf = new CheckGUI(frame, "������ �Է����ּ���.", false, false);
				} else if (introField.getText().equals("")) { //�Ұ� �Է� ������ ��
					CheckGUI cf = new CheckGUI(frame, "�Ұ��� �Է����ּ���.", false, false);
				} else {
					String title = Client.titleList[comboBox.getSelectedIndex()]; //���� ����
					String name = nameField.getText();
					String intro = introField.getText();
					if(intro.length() > 30) { //�Ұ��� ���� Ȯ��
						CheckGUI cf = new CheckGUI(frame,"�Ұ����� 30���̳��� �ۼ����ּ���.", false, false);
					} else {
						if (name.contains("��")|| intro.contains("��")||name.contains("��")|| intro.contains("��")) { //���ڿ� ���� Ȯ��
							CheckGUI cf = new CheckGUI(frame,"������ �� ���� ���ڿ��� �ֽ��ϴ�.", false, false);
						} else {
							Client.server.sendMessage("EDR��"+ roomId+"��"+ Client.id+"��"+ title + "��" + name+ "��" + intro); //ä�ù� ���� �޽��� ����
							frame.dispose(); //���� â ����
							parentFrame.dispose(); //����� ��� â ����
						}
					}
				}
			} 
		}	
	}

}
