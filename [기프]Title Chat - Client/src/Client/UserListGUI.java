package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Addon.MyColor;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Font;

public class UserListGUI extends JFrame { //ä�ù� ����� ��� ������

	private static UserListGUI lastFrame;
	private JFrame frame;
	private JPanel contentPane;
	private int frameWidth = 250;
	private int frameHeight = 400;
	private JLabel lblConnecting;
	private JScrollPane scrollPane;
	private JLabel lblUserCnt;
	private JButton btnEditRoom;
	private JPanel centerPanel;
	private JPopupMenu popup;
	private JMenuItem menu1;
	private JMenuItem menu2;
	private LinkedHashMap<String, String> users;
	private SelectUserEvent mouseEvent;
	private JLabel selectedUserLabel;
	private String selectedUserId;
	
	///������ ����
	private ChatroomGUI callerRoom;
	private String ownerId;
	private int roomId;
	
	public UserListGUI(ChatroomGUI chatroom) {//ä�ù� ����� ��� ������
		if(lastFrame != null) { //������ 2�� ������°� ����
			lastFrame.dispose();
		}
		lastFrame = this;
		
		frame = this;
		callerRoom = chatroom;
		ownerId = callerRoom.getRoomOwner();
		roomId = callerRoom.getRoomId();
		users = callerRoom.users;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		
		mouseEvent = new SelectUserEvent(); //���콺 Ŭ�� �̺�Ʈ
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		//ȭ�� ũ�� ���� �� ��ġ ȭ�� ��� ����
		
		contentPane = new JPanel(); //���� �г�
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(MyColor.midNightBlue);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel topPanel = new JPanel(); //��� �г�
		topPanel.setBounds(0, 0, 244, 50);
		topPanel.setBackground(MyColor.midNightBlue);
		topPanel.setLayout(null);
		contentPane.add(topPanel);
		
		JLabel lblTitle = new JLabel("  ���� ������ ���"); //��� ���� ��
		lblTitle.setFont(new Font("���� ���", Font.BOLD, 14));
		lblTitle.setBounds(0, 0, 160, 50);
		lblTitle.setBackground(MyColor.midNightBlue);
		lblTitle.setForeground(MyColor.lightYellow);
		topPanel.add(lblTitle);
		
		lblUserCnt = new JLabel(users.size()+"/"+chatroom.getMax()); //���� ä�ù� ����� �ο��� �ִ��ο� ǥ��
		lblUserCnt.setFont(new Font("���� ���", Font.BOLD, 14));
		lblUserCnt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserCnt.setBounds(160, 0, 72, 50);
		lblUserCnt.setBackground(MyColor.midNightBlue);
		lblUserCnt.setForeground(MyColor.lightYellow);
		topPanel.add(lblUserCnt);
		
		if(ownerId.equals(Client.id)) { //�� �������� ���� ä�ù��� �����϶�
			JPanel bottomPanel = new JPanel(); //�ϴ� �г� ����
			bottomPanel.setBounds(0, 341, 244, 30);
			bottomPanel.setBackground(MyColor.midNightBlue);
			bottomPanel.setLayout(null);
			contentPane.add(bottomPanel);
			
			btnEditRoom = new JButton("�� ���� ����"); //�� ���� ���� ��ư ���� �� ǥ��
			btnEditRoom.setFont(new Font("���� ���", Font.BOLD, 14));
			btnEditRoom.setBounds(0, 0, 244, 30);
			btnEditRoom.setBackground(MyColor.lightOrange);
			btnEditRoom.setForeground(MyColor.navy);
			btnEditRoom.addActionListener(new ActionListener() {	//��ư Ŭ�� �̺�Ʈ ����	
				@Override
				public void actionPerformed(ActionEvent e) {  //�ش� ��ư Ŭ����
					EditRoomGUI gui = new EditRoomGUI(frame, callerRoom); //�� ���� ���� ������ ǥ��
				}
			});
			bottomPanel.add(btnEditRoom);
		}
		
		scrollPane = new JScrollPane(); //���� �г� �ø� ��ũ���г�
		if(ownerId.equals(Client.id)) {
			scrollPane.setBounds(0, 50, 244, 291);
		} else {
			scrollPane.setBounds(0, 50, 244, 321);
		}	
		scrollPane.getViewport().setBackground(MyColor.midNightBlue);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
		centerPanel = new JPanel(); //����� ��� ��� ���� �г�
		centerPanel.setLayout(null);
		centerPanel.setBackground(MyColor.midNightBlue);
		scrollPane.setViewportView(centerPanel);
		
		popup = new JPopupMenu(); //�˾��޴�
		popup.setBackground(MyColor.lightGray);
		
		menu1 = new JMenuItem("���� ����"); //�˾� �޴� 1��
		menu1.setBackground(MyColor.lightGray);
		menu1.setFont(new Font("���� ���", Font.PLAIN, 13));
		menu1.setHorizontalAlignment(JLabel.CENTER);
		menu1.addActionListener(new ActionListener() {	//�޴� Ŭ�� �̺�Ʈ
			@Override
			public void actionPerformed(ActionEvent e) {//�ش� �޴� Ŭ����
				if(selectedUserId.equals(Client.id)) { //�����θ� �����ߴٸ� �ȵȴٰ� �˸�
					new CheckGUI(frame, "�����θ� ������ �� �����ϴ�.", false, false);
					return;
				}
				frame.dispose();
				Client.server.sendMessage("ROC��"+roomId+"��"+Client.id+"��"+selectedUserId); //������ ������ ��� ���� ���� ��û ����
			}
		});
		
		menu2 = new JMenuItem("���� ����"); //�˾� �޴� 2��
		menu2.setBackground(MyColor.lightGray);
		menu2.setFont(new Font("���� ���", Font.PLAIN, 13));
		menu2.setHorizontalAlignment(JLabel.CENTER);
		menu2.addActionListener(new ActionListener() {		//�޴� Ŭ�� �̺�Ʈ
			@Override
			public void actionPerformed(ActionEvent e) {//�ش� �޴� Ŭ����
				if(selectedUserId.equals(Client.id)) {//�����θ� �����ߴٸ� �ȵȴٰ� �˸�
					new CheckGUI(frame, "�����θ� ������ �� �����ϴ�.", false, false);
					return;
				}
				frame.dispose();
				Client.server.sendMessage("RUK��"+roomId+"��"+Client.id+"��"+selectedUserId); //������ ������ ��� �������� ��û ����
			}
		});
		
		popup.add(menu1); //�˾� �޴��� �߰�
		popup.addSeparator(); //���м�
		popup.add(menu2); 
		
		setUserList(); //�����гο� ����� ��� GUI�� ���
		
		this.setVisible(true);
	}
	
	private void setUserList() { //�����гο� ����� ��� GUI�� ����
		
		int yPos = 0; //�ø� y��ġ
		
		for(String nickName : users.keySet()) { //����� Ž��
			JLabel lbl = new JLabel("  "+nickName); //������� �г����� �󺧷� ����
			lbl.setName(users.get(nickName)); //�� �̸��� ����� id�� ����
			lbl.setBackground(MyColor.navy);
			lbl.setForeground(MyColor.white);
			lbl.setOpaque(true);
			lbl.setFont(new Font("���� ���", Font.BOLD, 12));
			lbl.setBounds(0, yPos, scrollPane.getSize().width-20, 30);
			lbl.setBorder(new LineBorder(MyColor.white,1));
			if(ownerId.equals(Client.id)) { //����� ��� �������� ��� ����� �����̶�� ���콺 Ŭ�� �̺�Ʈ �߰�����
				lbl.addMouseListener(mouseEvent);
			}
			centerPanel.add(lbl);
			yPos += 30;
		}
		
		centerPanel.revalidate(); //�����г� ���ΰ�ħ
		centerPanel.repaint();
	}
	
	private class SelectUserEvent implements MouseListener { //����� ��� �� Ŭ����
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(selectedUserLabel != null) { //������ ������ ����� ���� ���� �������� ����
				selectedUserLabel.setBackground(MyColor.navy);
			}
			if(e.getButton() == MouseEvent.BUTTON3) { //����� ���� ��Ŭ�� ���� ��
				JLabel l = (JLabel)e.getSource(); 
				selectedUserLabel = l; //������ �� ����
				selectedUserId = l.getName();
				l.setBackground(MyColor.darkBlue); //���� ����
				popup.show(l, e.getX(), e.getY()); //�˾��޴� ���
			} 
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

	}
}
