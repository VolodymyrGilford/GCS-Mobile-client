package com.globalcontrolsystem.gcs.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Waserdx on 09.02.14.
 */
public class ResponseAddDevice extends Response{

    @SerializedName("deviceID")
    public int deviceID;

    @SerializedName("deviceKey")
    public String deviceKey;

    @SerializedName("cid")
    public int cid;

    @SerializedName("token")
    public String token;
}


