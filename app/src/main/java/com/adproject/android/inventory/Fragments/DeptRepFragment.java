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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adproject.android.inventory.Adapter.UseraAdpter;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_deptrep, container, false);
        userid = (String)getArguments().getSerializable("userid");
        ReadUser(userid);
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
                    TextView name = getActivity().findViewById(R.id.textDepRepName);
                    TextView email = getActivity().findViewById(R.id.textDeptRepEmail);
                    TextView phone = getActivity().findViewById(R.id.textDeprRepPhone);
                    u = (User) parent.getItemAtPosition(position);
                    name.setText(u.get("Name"));
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

     void ReadUser(String id){
        new AsyncTask<String,Void,List<User>>() {
            @Override
            protected List<User> doInBackground(String... voids) {
               List<User> users1 = User.ReadUser(voids[0]);
               List<User> users = new ArrayList<>();
               for(User u : users1){
                   if(!u.get("UserType").equals("Rep")){
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
        String url = "https://inventoryaandroid.azurewebsites.net/DepManager/saveNewRep?dropdown1="+id;
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
                //super.onPostExecute(aBoolean);
                Toast.makeText(getActivity().getApplicationContext(), aBoolean.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }.execute(url);
    }


}
