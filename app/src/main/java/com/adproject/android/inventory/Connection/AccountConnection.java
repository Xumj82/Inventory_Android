package com.adproject.android.inventory.Connection;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class AccountConnection {


     public void Logout(){
        new AsyncTask<Void,Void,String>(){

            String url = "https://inventoryaandroid.azurewebsites.net/Account/LogOff";
            @Override
            protected String doInBackground(Void... voids){
                try{
                    URL u = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setUseCaches(false);
                    conn.connect();
                } catch (ProtocolException e) {
                    e.printStackTrace(); return "0";
                } catch (MalformedURLException e) {
                    e.printStackTrace(); return "0";
                } catch (IOException e) {
                    e.printStackTrace(); return "0";
                }

                return "1";
            }

            @Override
            protected void onPostExecute(String s) {

            }
        };

    }
}
