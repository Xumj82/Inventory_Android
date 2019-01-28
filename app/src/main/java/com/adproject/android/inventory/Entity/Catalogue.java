package com.adproject.android.inventory.Entity;

import android.text.TextUtils;
import android.util.Log;

import com.adproject.android.inventory.Connection.AccountConnection;
import com.adproject.android.inventory.Connection.HttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Catalogue extends HashMap<String, String> implements Serializable {
    static String host = "inventorywebapi2019.azurewebsites.net";
    static String baseURL = String.format("http://%s/api/Catalogue", host);

    public Catalogue(String itemId, String description, String quantity, String category, String measureUnit, String price, String binNumber) {
        put("ItemID", itemId);
        put("Description", description);
        put("Quantity", quantity);
        put("Category", category);
        put("MeasureUnit", measureUnit);
        put("Price", price);
        put("BinNumber", binNumber);
    }

    public static List<Catalogue> listCatalogues() {
        List<Catalogue> list = new ArrayList<>();
        JSONArray a = HttpConnection.getJSONArrayFromUrl(baseURL);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Catalogue(b.getString("ItemID"),
                        b.getString("Description"),
                        b.getString("Quantity"),
                        b.getString("Category"),
                        b.getString("MeasureUnit"),
                        b.getString("Price"),
                        b.getString("BinNumber")
                ));
            }
        } catch (Exception e) {
            Log.e("User", "JSONArray error");
        }
        return (list);
    }

    public static List<Catalogue> GetDisbursementsByDept(Department department){
        JSONObject jsonObject= new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Catalogue> catalogues = new ArrayList<>();
        String url = "https://inventory123.azurewebsites.net/StoreClerk/GetDisbursementItems";
        try {
            jsonObject.put("deptName", department.get("DepartmentName"));
            jsonArray.put(jsonObject);
            jsonArray = HttpConnection.getJSONArrayByJSONArray(url,jsonArray);
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                catalogues.add(new Catalogue(
                        "",
                        jsonObject.getString("itemDescription"),
                        jsonObject.getString("quantity"),
                        "",
                        jsonObject.getString("uom"),
                        "",
                        ""

                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return catalogues;
    }

    public static boolean save(Catalogue catalogue){
        try {
            String url = "https://inventory123.azurewebsites.net/StoreClerk/UpdateInventoryBinNumber";
            InputStream is = null;
            URL u = new URL(url);
            String urlParameters = "ItemID=" + catalogue.get("ItemID") + "&binNumber=" + catalogue.get("BinNumber");
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            if (AccountConnection.msCookieManager.getCookieStore().getCookies().size() > 0) {
                // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
                conn.setRequestProperty("Cookie",
                        TextUtils.join(";",  AccountConnection.msCookieManager.getCookieStore().getCookies()));
            }

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            conn.connect();
            String m = conn.getResponseMessage();
            return true;
        } catch (Exception e) {
            e.printStackTrace();return false;
        }
    }

}

