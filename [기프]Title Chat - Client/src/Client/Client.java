package Client;

import java.net.Socket;

import Addon.MySocket;

public class Client {
	
	//�ۼ��� 2016244009 �����
	//���α׷� �� : Title Chat - Client
	//����� �ڽ��� ���ϴ� ������ �����Ͽ� ä�ù��� �����ϰų� �����Ͽ� ä���ϴ� ���α׷�
	//���� 1.0.0

	public static MySocket server; //�������� ��ſ� ����
	public static String id = null; //����� id
	public static String pw = null; //����� pw
	public static String name = null; //����� �̸�
	public static String telNum = null; //����� ����ó
	public static String nickName = null; //����� �г���
	public static String status = "������"; //����� ���� ȭ��
	final public static String[] titleList = {"" ,"����", "�丮", "����", "��Ȱ","�н�"}; //���� ���
	
	public static void main(String args[]) {
		ConnectingGUI connect = new ConnectingGUI(); //������ ������ ����
	}
	
}
