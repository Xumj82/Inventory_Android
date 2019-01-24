package com.adproject.android.inventory.Entity;

import android.util.Log;

import com.adproject.android.inventory.Connection.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Request extends HashMap<String, String> {
    static String host = "inventorywebapi2019.azurewebsites.net";
    static String baseURL = String.format("http://%s/api/Request", host);

    public Request(String requestid, String item, String status,String employee,String dept,String orderid,String date,String qty,String remarks,String userid) {
        put("RequestID", requestid);
        put("Description", item);
        put("Status", status);
        put("Employee",employee);
        put("Department",dept);
        put("OrderID",orderid);
        put("RequestDate",date);
        put("Qty",qty);
        put("Remarks",remarks);
        put("UserID",userid);
    }

    public static List<Request> ReadAllRequest() {
        List<Request> list = new ArrayList<Request>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Request(b.getString("RequestID"),
                        b.getJSONObject("Catalogue").getString("Description"),
                        b.getString("RequestStatus"),
                        b.getJSONObject("AspNetUser").getString("Name"),
                        b.getJSONObject("AspNetUsers").getString("DepartmentID"),
                        b.getString("OrderID"),
                        b.getString("RequestDate"),
                        b.getString("Needed"),
                        b.getString("Remarks"),
                        b.getString("UserID")
                ));
            }
        } catch (Exception e) {
            Log.e("Request", "JSONArray error");
        }


        return(list);
    }

    public static List<Request> ReadOrderByDept(String id) {
        List<Request> list = new ArrayList<Request>();
        List<Request> list1 = new ArrayList<Request>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Request(b.getString("RequestID"),
                        b.getJSONObject("Catalogue").getString("Description"),
                        b.getString("RequestStatus"),
                        b.getJSONObject("AspNetUsers").getString("Name"),
                        b.getJSONObject("AspNetUsers").getString("DepartmentID"),
                        b.getString("OrderID"),
                        b.getString("RequestDate"),
                        b.getString("Needed"),
                        b.getString("Remarks"),
                        b.getString("UserID")
                        ));
            }
        } catch (Exception e) {
            Log.e("Request", "JSONArray error");
        }

        String userdeptid = id;
        for(Request l : list){
            if(l.get("Department").equals(userdeptid)){
                list1.add(l);
            }
        }


        return(list1);
    }

    public static List<Request> ReadRequestByOrderIDUserID(String id,String userid) {
        List<Request> list = new ArrayList<Request>();
        List<Request> list1 = new ArrayList<Request>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+"/"+id);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Request(b.getString("RequestID"),
                        b.getJSONObject("Catalogue").getString("Description"),
                        b.getString("RequestStatus"),
                        b.getJSONObject("AspNetUsers").getString("Name"),
                        b.getJSONObject("AspNetUsers").getString("DepartmentID"),
                        b.getString("OrderID"),
                        b.getString("RequestDate"),
                        b.getString("Needed"),
                        b.getString("Remarks"),
                        b.getString("UserID")
                ));
            }
            for(Request r:list){
                if(r.get("Status").equals("Unapproved")&&r.get("UserID").equals(userid)) {
                    list1.add(r);
                }
            }


        } catch (Exception e) {
            Log.e("Request", "JSONArray error");
            e.printStackTrace();
        }
        return(list1);
    }
}
