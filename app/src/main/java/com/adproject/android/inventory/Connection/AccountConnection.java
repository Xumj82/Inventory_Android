package com.adproject.android.inventory.Connection;

import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountConnection {



    static public String login(String username,String password){
        String url = "https://lusis.azurewebsites.net/Account/MobileLogin";
        String param = "username="+username+"&password="+password;
        String s = HttpConnection.paramConnect(url,param,"login");
        return s;
    }

     public void Logout(){
        new AsyncTask<Void,Void,String>(){

            String url = "https://lusis.azurewebsites.net/Account/LogOff";
            @Override
            protected String doInBackground(Void... voids){
                try{
                    URL u = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    if (HttpConnection.msCookieManager.getCookieStore().getCookies().size() > 0) {
                        // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
                        conn.setRequestProperty("Cookie",
                                TextUtils.join(";",  HttpConnection.msCookieManager.getCookieStore().getCookies()));
                    }
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setUseCaches(false);
                    conn.connect();
                    String m = conn.getResponseMessage();
                } catch (ProtocolException e) {
                    e.printStackTrace(); return "0";
                } catch (MalformedURLException e) {
                    e.printStackTrace(); return "0";
                } catch (IOException e) {
                    e.printStackTrace(); return "0";
                }

                return "1";
            }

        };

    }
}
