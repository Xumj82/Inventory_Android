package com.adproject.android.inventory.Entity;

import android.util.Log;

import com.adproject.android.inventory.Connection.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User extends HashMap<String, String>{
    static String host = "testapi20190121123138.azurewebsites.net";
    static String baseURL = String.format("http://%s/api/Department/", host);

    public User(String name, String username, String dept,String userid,String email,String usertype){
        put("Name", name);
        put("UserName", username);
        put("DepartmentID", dept);
        put("UserID",userid);
        put("Email",email);
        put("UserType",usertype);
    }

    public static List<User> ReadUser(String id) {
        List<User> list = new ArrayList<User>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL+id);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new User(b.getString("Name"),
                        b.getString("UserName"),
                        b.getString("DepartmentID"),
                        b.getString("Id"),
                        b.getString("Email"),
                        b.getString("UserType")
                ));
            }
        } catch (Exception e) {
            Log.e("User", "JSONArray error");
            e.printStackTrace();
        }
        return(list);
    }
}
