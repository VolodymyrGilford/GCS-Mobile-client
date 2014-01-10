package com.example.shadowspy;

import java.io.BufferedInputStream;

import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.net.URL;

import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;

 

public class Download {
	private final String PATH = "/mnt/sdcard/";  
	
	//----------------------------------------------------------
	//Завантажує файл 
    public void DownloadFromUrl(String URL, String fileName) { 
    	try {
    		URL url = new URL("http://norb.16mb.com/"+URL);
    		File file = new File(PATH+fileName);
    		long startTime = System.currentTimeMillis();

    		URLConnection ucon = url.openConnection();
    		InputStream is = ucon.getInputStream();
    		BufferedInputStream bis = new BufferedInputStream(is);
    		ByteArrayBuffer baf = new ByteArrayBuffer(50);

            int current = 0;
            while ((current = bis.read()) != -1) {
            	baf.append((byte) current);
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.close();
    	}catch (IOException e) {
    		//TODO
    	}

    }

}