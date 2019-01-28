package com.adproject.android.inventory.Entity;

import android.util.Log;

import com.adproject.android.inventory.Connection.HttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

}

