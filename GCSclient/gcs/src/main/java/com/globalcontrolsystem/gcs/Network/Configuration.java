package com.globalcontrolsystem.gcs.Network;

/**
 * Created by admin on 03.02.14.
 */
public class Configuration {
    public static  final String main_server = "http://waserdxgcs-001-site1.smarterasp.net/";
    public static final String maps_api = "Api/Maps.aspx/";
    public static final String device_api = "Api/Devices.aspx/";
    public static final String user_api = "Api/Users.aspx/";

    public static final String method_set_geo = "SetGeoCoords";
    public static final String method_add_device = "addDevice";
    public static final String method_send_msg = "ReceiveMessage";

    public static String MakeSetGeoUrl(){
        return main_server + maps_api + "/" + method_set_geo;
    }

    public static String MakeAddDeviceUrl(){
        return main_server + device_api + method_add_device;
    }

    public static String MakeSendMsgUrl(){
        return  main_server + user_api + method_send_msg;
    }
}
