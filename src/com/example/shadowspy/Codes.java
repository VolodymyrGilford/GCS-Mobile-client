package com.example.shadowspy;

public class Codes {
	//коди з'єднання
	public static String NO_CONNECT = "#0";
	
	//коди реєстрації
	public static String SUCCESS_REGISTRATION = "#20";
	public static String FAIL_REGISTRATION = "#21";	
	
	//коди авторизації
	public static String SUCCESS_AUTHORIZATION = "#25";
	public static String BAD_LOGIN = "#27";		
	public static String BAD_PASSWORD = "#26";	
	
	//коди виходу із системи
	public static String FAIL_LOGOUT = "#23";
	
	//коди доставки повідомлень
	public static String FAIL_SEND_MESSAGE = "#28";
	
	//коди телефонії
	public static String NEW_CALL = "#100";
	public static String START_INCOMING_CALL = "#101";
	public static String START_OUTCOMING_CALL = "#102";	
	public static String STOP_CALL = "#103";
	public static String NEW_SMS = "#110";
	public static String INCOME_SMS = "#111";
	public static String GET_CONTACT_LIST = "#112";
	
	//коди GPS
	public static String MY_LOCATION = "#120";
	
	//коди текстових повідомлень
	public static String NEW_MSG = "#150";
	
	//ключі програми для збереження налаштувань
	public static String file_pref = "preference";
	public static String saved_login = "login";
	public static String saved_pass = "pass";
	public static String saved_isPCOnline = "isPCOnline";
	public static String saved_isPDAOnline = "isPCOnline";
	public static String saved_isStartScreenShow = "isStartScreenShow";
	public static String saved_IMEI = "IMEI";
	public static String saved_time_to_refresh = "time_refresh";
	
	//коди повідомлень StatusBar
	public static int CONNECTION_NOTIFY = 0x0001;
	public static int NEW_MESSAGE_NOTIFY = 0x6521;
}
