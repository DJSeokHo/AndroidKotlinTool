package com.swein.androidkotlintool.framework.util.fileupload;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploadUtil {

    public interface FileUploadUtilDelegate {
        void onSuccess(String response);
        void onFailed();
        void onError();
    }

    public static void uploadFile(String uploadUrl, String filePath, String fileName, String contentType, FileUploadUtilDelegate fileUploadUtilDelegate) {

        if(!new File(filePath).exists()) {

            return;
        }

        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);

            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setReadTimeout(50000);

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");

            httpURLConnection.setRequestProperty("Content-Type", contentType);
            httpURLConnection.addRequestProperty("File-Path", filePath);
            httpURLConnection.addRequestProperty("File-Name", fileName);

            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());


            FileInputStream fileInputStream = new FileInputStream(filePath);

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int length = -1;

            while ((length = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, length);
            }

            dataOutputStream.flush();
            fileInputStream.close();
            dataOutputStream.close();

            if(httpURLConnection.getResponseCode() == 200) {
                fileUploadUtilDelegate.onSuccess(httpURLConnection.getResponseMessage());
            }
            else {
                fileUploadUtilDelegate.onFailed();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            fileUploadUtilDelegate.onError();
        }
    }

}
