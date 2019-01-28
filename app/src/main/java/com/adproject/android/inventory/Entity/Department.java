package com.adproject.android.inventory.Entity;

import android.util.Log;

import com.adproject.android.inventory.Connection.HttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Department extends HashMap<String,String> {
    static String host = "inventorywebapi2019.azurewebsites.net";
    static String baseURL = String.format("http://%s/api/Department", host);
    List<User> userList = new ArrayList<>();


    public Department(String id, String rep, String head,String name,String point,String start,String end){
        put("DepartmentID", id);
        put("DepartmentRep", rep);
        put("DepartmentHead",head);
        put("DepartmentName",name);
        put("CollectionPoint",point);
        put("DepartmentHeadStartDate",start);
        put("DepartmentHeadEndDate",end);
    }

    public static List<Department> ReadDepts() {
        List<Department> list = new ArrayList<>();
        JSONArray a = HttpConnection.getJSONArrayFromUrl(baseURL);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                //JSONArray  c = b.getJSONArray("AspNetUsers2");
                list.add(new Department(b.getString("DepartmentID"),
                        b.getString("DepartmentRep"),
                        b.getString("DepartmentHead"),
                        b.getString("DepartmentName"),
                        b.getString("CollectionPoint"),
                        b.getString("DepartmentHeadStartDate"),
                        b.getString("DepartmentHeadEndDate")
                ));
            }
        } catch (Exception e) {
            Log.e("User", "JSONArray error");
        }
        return(list);
    }

    public static List<User> ReadUserByDeptID(String id) {
        List<User> list = new ArrayList<>();
        JSONArray a = HttpConnection.getJSONArrayFromUrl(baseURL);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                if(b.getString("DepartmentID").equals(id)) {
                   JSONArray  c = b.getJSONArray("AspNetUsers2");
                   for (int j=0;j<c.length();j++){
                       JSONObject d = c.getJSONObject(j);
                       list.add(new User(
                               d.getString("Name"),
                               d.getString("UserName"),
                               d.getString("DepartmentID"),
                               d.getString("Id"),
                               d.getString("Email"),
                               d.getString("UserType")
                       ));
                   }
                }
            }
        } catch (Exception e) {
            Log.e("User", "JSONArray error");
            e.printStackTrace();
        }
        return(list);
    }

    public static List<Department> GetDisbursementList(){
        List<Department> departments = new ArrayList<>();
        JSONArray a = HttpConnection.getJSONArrayFromUrl("https://inventory123.azurewebsites.net/StoreClerk/GetDisbursementList");
        try{
            for(int i=0;i<a.length();i++){
                JSONObject b = a.getJSONObject(i);
                departments.add(new Department("",
                        b.getString("representative"),
                        "",
                        b.getString("departmentName"),
                        b.getString("collectionPoint"),
                        "",
                        ""

                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return departments;

    }
}
