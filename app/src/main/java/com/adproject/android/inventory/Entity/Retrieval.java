package com.adproject.android.inventory.Entity;

import com.adproject.android.inventory.Connection.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Retrieval extends HashMap<String,String> {

    public Retrieval(String requestId,String itemDescription,String neededQuantity,String availableQuantity,String binNumber,String remarks,String orderid){
        put("requestId",requestId);
        put("itemDescription",itemDescription);
        put("availableQuantity",availableQuantity);
        put("binNumber",binNumber);
        put("remarks",remarks);
        put("orderid",orderid);
    }

    static List<Retrieval> GetRetrievals(){
        List<Retrieval> requests = new ArrayList<>();
        try {
            JSONArray a = new JSONArray();
            a = JSONParser.getJSONFromUrl("https://inventory123.azurewebsites.net/StoreClerk/GetRetrievals").getJSONArray("data");
            for(int i =0;i<a.length();i++){
                requests.add(new Retrieval(
                        a.getJSONObject(i).getString("requestId"),
                        a.getJSONObject(i).getString("itemDescription"),
                        a.getJSONObject(i).getString("neededQuantity"),
                        a.getJSONObject(i).getString("availableQuantity"),
                        a.getJSONObject(i).getString("binNumber"),
                        a.getJSONObject(i).getString("remarks"),
                        a.getJSONObject(i).getString("orderid")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  requests;
    }
}
