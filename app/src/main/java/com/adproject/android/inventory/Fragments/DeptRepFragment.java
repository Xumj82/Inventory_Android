package com.adproject.android.inventory.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adproject.android.inventory.Adapter.UseraAdpter;
import com.adproject.android.inventory.Entity.Department;
import com.adproject.android.inventory.Entity.User;
import com.adproject.android.inventory.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DeptRepFragment extends Fragment {
    Spinner spinner;
    User u;
    String userid;
    String selcetid;
    String deptid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dept_rep, container, false);
        deptid = getActivity().getIntent().getExtras().getString("dept");
        GetRep(deptid);
        ReadUser(deptid);
        return(v);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Dept Rep");
        spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        if(spinner!=null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView email = getActivity().findViewById(R.id.textDeptRepEmail);
                    TextView phone = getActivity().findViewById(R.id.textDeprRepPhone);
                    u = (User) parent.getItemAtPosition(position);
                    email.setText(u.get("Email"));
                    phone.setText(u.get("PhoneNumber"));
                    selcetid = u.get("UserID");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
        };
        Button save = getActivity().findViewById(R.id.buttonSaveRep);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(u!=null){
                 Save(selcetid);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "wait...",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void GetRep(String id){
        new AsyncTask<String,Void,User>() {
            @Override
            protected User doInBackground(String... voids) {
                List<User> users1 = Department.ReadUserByDeptID(voids[0]);
                User user = new User("","","","","","");
                for(User u : users1){
                    String type = u.get("UserType");
                    if(type.equals("DeptRep")){
                        user = u;
                    }
                }
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                try {
                  TextView deptrep = getActivity().findViewById(R.id.textDepRepName);
                  deptrep.setText(user.get("Name"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute(id);
    }

     void ReadUser(String id){
        new AsyncTask<String,Void,List<User>>() {
            @Override
            protected List<User> doInBackground(String... voids) {
               List<User> users1 = Department.ReadUserByDeptID(voids[0]);
               List<User> users = new ArrayList<>();
               for(User u : users1){
                   String type = u.get("UserType");
                   if(!(type.equals("DeptHead"))){
                       users.add(u);
                   }
               }
               return users;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                try {
                    UseraAdpter adapter = new UseraAdpter(getActivity(), users);
                    spinner.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute(id);
    }

    void Save(String id){
        String url = "https://inventory123.azurewebsites.net/DepManager/saveNewRep?dropdown1="+id;
        new AsyncTask<String, Void,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                try {
                    URL u = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    String response = conn.getResponseMessage();
                    conn.disconnect();
                    if(response.equals("OK")) {

                        return true;
                    }
                    else {
                        return false;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();return false;
                } catch (IOException e) {
                    e.printStackTrace();return false;
                }

            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                Toast.makeText(getActivity().getApplicationContext(), aBoolean.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }.execute(url);
    }


}
