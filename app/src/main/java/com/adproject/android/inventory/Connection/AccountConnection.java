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

    public static final String COOKIES_HEADER = "Set-Cookie";
    public static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    static public String login(String username,String password){
        String s = "";
        try {
            String url = "https://inventory123.azurewebsites.net/Account/MobileLogin";
            InputStream is = null;
            URL u = new URL(url);
            String urlParameters = "username=" + username + "&password=" + password;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            conn.connect();

            //set-cookie
            Map<String, List<String>> headerFields = conn.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
                }
            }

            String a = conn.getResponseMessage();
            is = conn.getInputStream();
            s = HttpConnection.readStream(is);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

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
