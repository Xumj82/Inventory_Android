package com.adproject.android.inventory.Connection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.adproject.android.inventory.LoginActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Logout {
    public void Logout(){
        final String url = "https://inventoryaandroid.azurewebsites.net/Account/LogOff";
        new AsyncTask<Void,Void,String>(){

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
