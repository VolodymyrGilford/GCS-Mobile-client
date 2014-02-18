package com.globalcontrolsystem.gcs.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Waserdx on 09.02.14.
 */
public class ResponseAuthenticate extends Response{

    @SerializedName("access_token")
    public String access_token;
}
