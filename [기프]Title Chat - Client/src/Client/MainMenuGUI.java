package Client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import Addon.MyColor;
import Addon.MyUtility;

public class MainMenuGUI extends JFrame { //���θ޴� ȭ��
	
	private int frameWidth = 400;
	private int frameHeight = 600;
	private JLabel lblNickName;
	private JPanel topLeftPanel;
	private JPanel topRightPanel;
	private JPanel bottomPanel;
	private JButton settingBtn;
	private JButton enterBtn;
	private JButton createRoomBtn;
	private JPanel centerPanel;
	private JComboBox comboBox; 
	private JScrollPane sc;
	private LinkedHashMap<Room, JPanel> rooms = new LinkedHashMap<Room, JPanel>();
	private SelectRoomEvent selectEvent;
	private String selectedRoomId;
	private JPanel selectedRoomPane; 
	private MainMenuGUI mainMenu;
	
	public MainMenuGUI() { //���θ޴� ������
		
		Toolkit tk = Toolkit.getDefaultToolkit(); //������� ȭ�� ũ�Ⱚ�� ������� ��Ŷ Ŭ���� 
		
		URL src = ConnectingGUI.class.getResource("/resources/baricon.png");
		ImageIcon icon = new ImageIcon(src);
		this.setIconImage(icon.getImage());
		this.setTitle("Title Chat");
		setResizable(false);
		setBounds((int) tk.getScreenSize().getWidth() / 2 - frameWidth /2, (int) tk.getScreenSize().getHeight() / 2 - frameHeight/2, frameWidth, frameHeight);
		getContentPane().setLayout(null);
		getContentPane().setBackground(MyColor.midNightBlue);
		
		this.addWindowListener(new JFrameWindowClosingEventHandler()); //â �ݱ� �̺�Ʈ
		mainMenu = this; //���θ޴� ����
		
		MyEvent event = new MyEvent(this);
		selectEvent = new SelectRoomEvent();
		selectedRoomId = null;
		selectedRoomPane = null;
		
		topLeftPanel = new JPanel(); //��� ���� �г�
		topLeftPanel.setBounds(0, 0, 250, 50);
		topLeftPanel.setBackground(MyColor.midNightBlue);
		topLeftPanel.setBorder(new LineBorder(MyColor.darkBlue, 1));
		topLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		getContentPane().add(topLeftPanel);
		
		lblNickName = new JLabel(Client.nickName); //�г��� ��
		lblNickName.setFont(new Font("���� ���", Font.BOLD, 20));
		lblNickName.setHorizontalAlignment(SwingConstants.LEFT);
		lblNickName.setPreferredSize(new Dimension(245, 50));
		lblNickName.setForeground(MyColor.lightYellow);
		topLeftPanel.add(lblNickName);
		
		topRightPanel = new JPanel(); //��� ���� �г�
		topRightPanel.setBounds(250, 0, 144, 50);
		topRightPanel.setBackground(MyColor.midNightBlue);
		topRightPanel.setBorder(new LineBorder(MyColor.darkBlue, 1));
		topRightPanel.setLayout(new GridLayout(2, 1, 0, 0));
		getContentPane().add(topRightPanel);
		
		JLabel lblSelectTitle = new JLabel("��������"); //�������� ��
		lblSelectTitle.setForeground(MyColor.lightYellow);
		lblSelectTitle.setFont(new Font("���� ���", Font.PLAIN, 12));
		topRightPanel.add(lblSelectTitle);
		
		comboBox = new JComboBox(Client.titleList); //�޺��ڽ�, ����� Client Ŭ������ titleList ���
		comboBox.setFont(new Font("���� ���", Font.PLAIN, 12));
		comboBox.setBackground(MyColor.lightOrange);
		comboBox.setForeground(MyColor.navy);
		comboBox.addActionListener(new ActionListener() { //�޺��ڽ� ���� �̺�Ʈ		
			public void actionPerformed(ActionEvent e) {
				clearCenterPanel(); //ä�ù� ��� GUI �ʱ�ȭ
				setRoomGui();	 //ä�ù� ��� ����
			}
		});
		topRightPanel.add(comboBox);
		
		bottomPanel = new JPanel(); //�ϴ� �г�
		bottomPanel.setBounds(0, 541, 394, 30);
		bottomPanel.setBackground(MyColor.lightOrange);
		bottomPanel.setLayout(new GridLayout(0, 3, 0, 0));
		getContentPane().add(bottomPanel);
		
		settingBtn = new JButton("����"); //����� ���� ���� ��ư
		settingBtn.setFont(new Font("���� ���", Font.PLAIN, 12));
		settingBtn.setBackground(MyColor.lightOrange);
		settingBtn.setForeground(MyColor.navy);
		settingBtn.setBorderPainted(true);
		settingBtn.addActionListener(event);
		bottomPanel.add(settingBtn);
		
		enterBtn = new JButton("����"); //ä�ù� ���� ��ư
		enterBtn.setFont(new Font("���� ���", Font.PLAIN, 12));
		enterBtn.setBackground(MyColor.lightOrange);
		enterBtn.setForeground(MyColor.navy);
		enterBtn.setBorderPainted(true);
		enterBtn.addActionListener(event);
		bottomPanel.add(enterBtn);
		
		createRoomBtn = new JButton("�� ����"); //ä�ù� ���� ��ư
		createRoomBtn.setFont(new Font("���� ���", Font.PLAIN, 12));
		createRoomBtn.setBackground(MyColor.lightOrange);
		createRoomBtn.setForeground(MyColor.navy);
		createRoomBtn.setBorderPainted(true);
		createRoomBtn.addActionListener(event);
		bottomPanel.add(createRoomBtn);
		
		centerPanel = new JPanel(); //���� �г�
		centerPanel.setBackground(MyColor.midNightBlue);
		centerPanel.setLayout(null);
		
		sc = new JScrollPane(centerPanel); //���� �г� �ø� ��ũ�� �г�
		centerPanel.setLayout(null);
		sc.getViewport().setBackground(MyColor.midNightBlue);
		sc.setBounds(0, 50, 394, 492);
		sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sc.getVerticalScrollBar().setUI(new BasicScrollBarUI() { //��ũ�ѹ� �� ����
            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = MyColor.navy;
            }
        });
		getContentPane().add(sc);
		centerPanel.setPreferredSize(new Dimension(sc.getSize().width,sc.getSize().height));
		
		getRooms();
		
		Client.status = "���θ޴�"; //����� ȭ����� ���θ޴��� ����
		
		this.setVisible(true);
		Client.server.startListen(this); //��� ������ ��� �����Ͽ� �������� ���� ��ȣ ���� ����
	}
	
	public void refreshNickName() { //����� �г��� ���ΰ�ħ
		lblNickName.setText(Client.nickName);
	}
	
	public void refreshRoom() { //ä�ù� ��� ���ΰ�ħ
		clearCenterPanel(); //ä�ù� ��� GUI �ʱ�ȭ
		getRooms(); //�������� ä�ù� �޾ƿ��� �޼ҵ� ȣ��
	}
	
	private void clearCenterPanel() { //ä�ù� ��� GUI �ʱ�ȭ
		for(Component c : centerPanel.getComponents()) { //�����гο� �ִ� ������Ʈ ��� ����
			centerPanel.remove(c);
		}	
	}
	
	private void getRooms() { //�������� ä�ù� ��� �޾ƿ�
		Client.server.sendMessage("GCL��"); //������ ��� ��û
		String args[] = null;
		do {
			String msg = Client.server.receiveMessage(); //��� ��
			rooms.clear(); //�� ��� �ʱ�ȭ
			args = msg.split("��"); //�Ľ�
		}while(!args[0].equalsIgnoreCase("RLS")); //���� ���䰪�� RLS��(�븮��Ʈ ����) �ƴϸ� �ٽ� ����, �߸��� ���� �޴°� ����
		
		for(int i = 1; i < args.length; i++) { //�Ľ��� �� ���� ����
			String data[] = args[i].split("��");
			int id = Integer.valueOf(data[0]);
			String title = data[1];
			String name = data[2];
			String intro = data[3];
			int now = Integer.valueOf(data[4]);
			int max = Integer.valueOf(data[5]);
				
			Room room = new Room(id, title, name, intro, now, max);
			rooms.put(room, null);
		}
		setRoomGui(); //�� ������ ���� ä�ù� ��� GUI����

	}
	
	private void setRoomGui() { //ä�ù� ��� GUI ����
		int yPos = 0; //y��ġ��
		int showCnt = 0; //�������� �� ����
		String selectTitle = Client.titleList[comboBox.getSelectedIndex()]; //�޺��ڽ����� ������ ���� ������
		for(Room room : rooms.keySet()) {//���� ������ ""��� ��ü ǥ����
			if(!selectTitle.equals("")) { //������ ���õ� ���¶��
				if(!room.title.equals(selectTitle)) continue; //ä�ù��� ������ ���� ������ �������� ������ ���� ä�ù����� 
			}
			JPanel gui = room.getGUI(); //GUIȭ �ؼ� ǥ��
			gui.setBounds(0, yPos, sc.getSize().width-20, 100);
			rooms.put(room, gui);
			centerPanel.add(gui);
			yPos += 100;
			showCnt += 1;
		}
		
		if(showCnt >= 5) { //�������� GUI�� �� ������ 5�� �̻��̸� ���� �г��� ���� �ø�
			centerPanel.setPreferredSize(new Dimension(sc.getSize().width,sc.getSize().height+((showCnt-4)*100)));
		} else {
			centerPanel.setPreferredSize(new Dimension(sc.getSize().width,sc.getSize().height-20));
		}
		centerPanel.revalidate(); //�����г� ������Ʈ
		centerPanel.repaint();
	}
	
	public void editRooms_add(String args[]) {//���ο� �� ���� �߰� 
		int id = Integer.valueOf(args[1]); //�� ���� ����
		String title = args[2];
		String name = args[3];
		String intro = args[4];
		int now = Integer.valueOf(args[5]);
		int max = Integer.valueOf(args[6]);
		
		Room room = new Room(id, title, name, intro, now, max); //ä�ù� ���� ����
		JPanel gui = room.getGUI();
		gui.setBounds(0, rooms.size()*100, sc.getSize().width-20, 100); //ä�ù� ��ġ �� ũ�� ����
		rooms.put(room, gui); //ä�ù� ���� ����
		centerPanel.add(gui); //�ϴܿ� �ش� ä�ù� ����
		if(rooms.size() >= 5) { //���� �г� ũ�� ����
			centerPanel.setPreferredSize(new Dimension(sc.getSize().width,sc.getSize().height+((rooms.size()-4)*100)));
		}
		centerPanel.revalidate(); //�����г� ������Ʈ
		centerPanel.repaint();
	}
	
	public void editRooms_del(String args[]) { //ä�ù� ����
		int id = Integer.valueOf(args[1]); //������ ä�ù� ID
		
		Room delRoom = null;

		for(Room room : rooms.keySet()) { //���� ä�ù� ���� Ž��
			if(room.id == id) { //������ ä�ù� ID�� ��ġ�ϴٸ�
				delRoom = room; //������ �� ����
				break;
			}
		}
		if(delRoom == null) return;
		
		rooms.remove(delRoom); //�� ����

		refreshRoom(); //�� ���ΰ�ħ
	}
	
	public void editRooms_roominfo(String args[]) { //ä�ù� ����� �� ���� ����
		int id = Integer.valueOf(args[1]); //������ ä�ù� ID
		String title = args[2]; //���ο� ���� ���� 
		String name = args[3];
		String intro = args[4];
		int now = Integer.valueOf(args[5]);
		int max = Integer.valueOf(args[6]);
		
		Room editRoom = null;
		
		for(Room room : rooms.keySet()) { //���� ä�ù� ���� Ž��
			if(room.id == id) { //������ ä�ù� ID�� ��ġ�ϸ�
				editRoom = room;
				break;
			}
		}
		
		editRoom.title = title; //���ο� ä�ù� ������ ����
		editRoom.name = name;
		editRoom.intro = intro;
		editRoom.now = now;
		editRoom.max = max;

		JPanel gui = rooms.get(editRoom); //������ ������ ä�ù��� ������ ������ �ִ� �г� ������
		if(gui == null) return; 
		for(Component c : gui.getComponents()) { //�ش� �г��� ������Ʈ ����
			gui.remove(c);
		}
		
		editRoom.setGuiComponents(gui); //������Ʈ �缳��

		centerPanel.revalidate(); //������Ʈ
		centerPanel.repaint();
	}
	
	private class MyEvent implements ActionListener{ //��ư Ŭ�� �̺�Ʈ

		private JFrame frame;
		
		public MyEvent(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) { 
			JButton src = (JButton) e.getSource();
			if (src.equals(settingBtn)) { //����� ���� ���� ��ư Ŭ����
				SettingGUI settingGUI = new SettingGUI(frame); //����� ���� ���� ������ ��� 
			} else if(src.equals(createRoomBtn)) { //�� ���� ��ư Ŭ����
				CreateRoomGUI createRoomGUI = new CreateRoomGUI(mainMenu); //�� ���� ������ ���
			} else if(src.equals(enterBtn)) { //���� ��ư Ŭ����
				if(selectedRoomId != null) { //���� ������ �����϶���
					Client.server.sendMessage("JIR��"+selectedRoomId); //������ �ش� �� ID�� ���� ��û
					String msg = Client.server.receiveMessage(); //�������
					String args[] = msg.split("��"); //�Ľ�
					if(args[0].equals("FAL")){ //���н�
						CheckGUI cf = new CheckGUI(frame, args[1], false, false); //���� ���
						selectedRoomId = null;	
						refreshRoom();	//ä�ù� ��� ���ΰ�ħ
					} else if(args[0].equals("SUC")) { //������
						args = msg.split("��"); //�Ľ�
						String data[] = args[0].split("��"); //�Ľ�
						mainMenu.dispose();
						ChatroomGUI cr = new ChatroomGUI(Integer.valueOf(selectedRoomId), data[2], data[3], data[4], Integer.valueOf(data[5]),data[1], mainMenu);
						//�Ľ��� ä�ù� ������ ���� ä�ù� ������ ���
						for(int i = 1; i < args.length; i++) { //ä�ù濡 �ִ� ����ڵ� ����
							String user[] = args[i].split("��");
							cr.users.put(user[0], user[1]);
						}
					}
				}
			}
		}
	}
	
	private class JFrameWindowClosingEventHandler extends WindowAdapter { //â �ݱ��
		public void windowClosing(WindowEvent e) {
			if(e.getWindow() instanceof MainMenuGUI) {
				Client.server.stopListen(); //������  ���� 
				System.exit(0); //���α׷� ����
			}	
		}
	}
	
	private class SelectRoomEvent implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) { //ä�ù� �г� Ŭ����
			JPanel p = (JPanel)e.getSource();
			if(selectedRoomPane != null) { //������ ������ �г��� ���� ��������
				selectedRoomPane.setBackground(MyColor.midNightBlue);
			}
			selectedRoomPane = p; //������ �г��� ����
			selectedRoomId = p.getName(); //������ ä�ù� ID����
			p.setBackground(MyColor.darkBlue); //������ �г��� ���� ����
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

	private class Room{ //�� ����
		
		int id; //ä�ù� ID
		String title; //ä�ù� ����
		String name; //ä�ù� ����
		String intro; //ä�ù�  �Ұ�
		int now; //�����ο�
		int max; //�ִ��ο�
		
		public Room(int id, String title, String name, String intro, int now, int max) { //ä�ù� ���� ����
			this.id = id; //�ʱ�ȭ
			this.title = title;
			this.name = name;
			this.intro = intro;
			this.now = now;
			this.max = max;
		}
		
		public JPanel getGUI() { //�ش� ä�ù��� ������ ���� gui�г��� �������
				
			JPanel pane = new JPanel(); //��ȯ�� �г�
			pane.setLayout(null); //���� ��ġ
			
			setGuiComponents(pane); //������Ʈ ����
			
			pane.setName(id+""); //�г��̸��� ä�ù� id�� ����
			
			pane.setBorder(new LineBorder(MyColor.white, 1));
			pane.setBackground(MyColor.midNightBlue);
			
			pane.addMouseListener(selectEvent); //�г� Ŭ���̺�Ʈ �߰�
			
			return pane;
		}
		
		public void setGuiComponents(JPanel pane) { //ä�ù� ��� �г��� ������Ʈ ����
			
			ImageIcon img = new ImageIcon(MainMenuGUI.class.getResource("/resources/"+title+".png"));  //������ ���� �̹��� ����
			JLabel titleImg = new JLabel(img);
			titleImg.setBounds(5, 10, 50, 50);
			pane.add(titleImg);
			
			JLabel titleLabel = new JLabel(title); //ä�ù� ���� ��
			titleLabel.setForeground(MyColor.white);
			titleLabel.setHorizontalAlignment(JLabel.CENTER);
			titleLabel.setBounds(5, 65, 50, 25);
			titleLabel.setFont(new Font("���� ���", Font.PLAIN, 12));
			pane.add(titleLabel);
			
			JLabel nameLabel = new JLabel(name); //ä�ù� ���� ��
			nameLabel.setForeground(MyColor.white);
			nameLabel.setBounds(70, 20, 200, 25);
			nameLabel.setFont(new Font("���� ���", Font.BOLD, 14));
			nameLabel.setVerticalAlignment(JLabel.CENTER);
			pane.add(nameLabel);
			
			JLabel introLabel = new JLabel(MyUtility.lineSpacing(intro, 16)); //ä�ù� �Ұ� ��
			introLabel.setForeground(MyColor.white);
			introLabel.setBounds(70, 50, 200, 45);
			introLabel.setVerticalAlignment(JLabel.TOP);
			introLabel.setFont(new Font("���� ���", Font.PLAIN, 12));
			pane.add(introLabel);
			
			JLabel playerLabel = new JLabel(now + "/" + max); //ä�ù� �ο� ��, �����ο� / �ִ��ο�
			playerLabel.setForeground(MyColor.white);
			playerLabel.setHorizontalAlignment(JLabel.RIGHT);
			playerLabel.setBounds(290, 15, 70, 70);
			playerLabel.setFont(new Font("���� ���", Font.BOLD, 17));
			pane.add(playerLabel);
			
		}
		
	}
}
