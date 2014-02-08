package com.globalcontrolsystem.gcs.Network;

/**
 * Created by admin on 03.02.14.
 */
public class Configuration {
    public  static  final String main_server = "http://waserdxgcs-001-site1.smarterasp.net/";
    public  static final String page_overwiew = "Overview.aspx";

    public  static final String method_test = "SetGeoCoords";

    public static String MakeTestGeoUrl(){
        return main_server + page_overwiew + "/" + method_test;
    }

    public static String MakeAddDevice(){
        return  main_server + page_overwiew + "/addDevice";
    }
}
