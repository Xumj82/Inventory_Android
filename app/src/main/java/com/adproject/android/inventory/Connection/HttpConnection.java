package com.adproject.android.inventory.Connection;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpConnection {

    public static String getStream(String url) {
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        String message;

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            //set cookie for authentication
            if (AccountConnection.msCookieManager.getCookieStore().getCookies().size() > 0) {
                // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
                conn.setRequestProperty("Cookie",
                        TextUtils.join(";",  AccountConnection.msCookieManager.getCookieStore().getCookies()));
            }

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.connect();

            Map<String, List<String>> headerFields = conn.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(AccountConnection.COOKIES_HEADER);
            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    AccountConnection.msCookieManager.getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
                }
            }

            is = conn.getInputStream();
            //this message is for test
            message = conn.getResponseMessage();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return  sb.toString();
    }



    public static String postStream(String url, Bitmap data) {
        InputStream is = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");
            //conn.setFixedLengthStreamingMode(data.getNinePatchChunk().length);
            conn.connect();
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            data.compress(Bitmap.CompressFormat.PNG, 100, baos);
            os.write(baos.toByteArray());
            os.flush();
            is = conn.getInputStream();
            conn.disconnect();
        } catch (UnsupportedEncodingException e) {
            Log.e("postStream Exception", e.toString());
        } catch (Exception e) {
            Log.e("postStream Exception", e.toString());
        }
        return  readStream(is);

    }

    public static Boolean postJSONObject(String url,List<JSONObject> jsonObject){
        try {
            URL Url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
            //set_cookie
            if (AccountConnection.msCookieManager.getCookieStore().getCookies().size() > 0) {
                // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
                conn.setRequestProperty("Cookie",
                        TextUtils.join(";",  AccountConnection.msCookieManager.getCookieStore().getCookies()));
            }

            conn.setConnectTimeout(5000);
            // 设置允许输出
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 设置contentType
            conn.setRequestProperty("Content-Type", "application/json");
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            String content = String.valueOf(jsonObject);
            os.writeBytes(content);
            os.flush();
            os.close();
            if(conn.getResponseMessage().equals("OK")){
                return true;
            }
            conn.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();return false;
        } catch (IOException e) {
            e.printStackTrace();return false;
        }
        return false;
    }

    public static String postJSONArray(String url,JSONArray jsonArray){
        InputStream is;
        StringBuilder sb = new StringBuilder();
        try {
            URL Url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
            //set_cookie
            if (AccountConnection.msCookieManager.getCookieStore().getCookies().size() > 0) {
                // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
                conn.setRequestProperty("Cookie",
                        TextUtils.join(";",  AccountConnection.msCookieManager.getCookieStore().getCookies()));
            }

            conn.setConnectTimeout(5000);
            // 设置允许输出
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 设置contentType
            conn.setRequestProperty("Content-Type", "application/json");
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            String content = String.valueOf(jsonArray);
            os.writeBytes(content);
            os.flush();
            os.close();
            is = conn.getInputStream();
            //this message is for test
            String message = conn.getResponseMessage();
        } catch (ProtocolException e) {
            e.printStackTrace();return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();return null;
        } catch (IOException e) {
            e.printStackTrace();return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return  sb.toString();
    }




    public static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return sb.toString();
    }

    public static JSONObject getJSONFromUrl(String url) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(getStream(url));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public static JSONArray getJSONArrayFromUrl(String url) {
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(getStream(url));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing array " + e.toString());
            e.printStackTrace();
        }
        return jArray;
    }

    public static JSONArray getJSONArrayByJSONArray(String url,JSONArray jsonArray){
       JSONArray jArray = null;
       try{
           jArray = new JSONArray(postJSONArray(url,jsonArray));
       } catch (JSONException e) {
           e.printStackTrace();
       }
       return jArray;
    }
}
