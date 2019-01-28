package com.adproject.android.inventory.DeptHeadFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adproject.android.inventory.Adapter.RequestItemAdapter;
import com.adproject.android.inventory.Connection.HttpConnection;
import com.adproject.android.inventory.Entity.Request;
import com.adproject.android.inventory.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsFragment extends Fragment {

    String requestid = "";
    String deptid;
    String userid;
    List<Request> requests;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.depthead_fragment_request_details, container, false);
        getActivity().setTitle("Request Details");
        Bundle arg = getArguments();
        HashMap<String,String> request = (HashMap<String,String>) arg.getSerializable("details");
        userid = request.get("UserName");
        deptid = request.get("OrderID");
        if (arg != null) {

            if (request != null) {
                TextView t1 = (TextView) v.findViewById(R.id.detailtext1);
                t1.setText(request.get("Employee"));
                TextView t2 = v.findViewById(R.id.detailtext2);
                t2.setText(request.get("RequestDate"));
                item(deptid);
            }
        }
        return(v);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button approval = getActivity().findViewById(R.id.buttonApproval);
        Button reject = getActivity().findViewById(R.id.buttonReject);
        approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requests.equals(null)){
                    Toast.makeText(getActivity().getApplicationContext(),"wait for response..",
                            Toast.LENGTH_SHORT).show();
                }else{
                            saveStatus(requests,"","Approved");
                            Toast.makeText(getActivity().getApplicationContext(),"success.",
                            Toast.LENGTH_SHORT).show();
                      }

                }


        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requests.equals(null)){
                    Toast.makeText(getActivity().getApplicationContext(),"wait for response..",
                            Toast.LENGTH_SHORT).show();
                }else{
                    saveStatus(requests,"","Reject");
                    Toast.makeText(getActivity().getApplicationContext(),"success.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void saveStatus(final List<Request> requestlist, final String remarks, final String status) {
        final String url = "https://inventory123.azurewebsites.net/DepManager/SaveRequestStatus";
        new AsyncTask<String, Void,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {

              try {
                List<JSONObject> body = new ArrayList<>();
                for(Request r : requestlist)
                {
                    JSONObject J1 = new JSONObject();
                    J1.put("orderId", r.get("OrderID"));
                    J1.put("requestStatus", status);
                    J1.put("reason",remarks);
                    body.add(J1);
                }
                return HttpConnection.postJSONObject(url,body);
              } catch (JSONException e) {
                e.printStackTrace();return false;
              }
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                //super.onPostExecute(aBoolean);
                try {
                    if (aBoolean == true) {
                    }
                    Toast.makeText(getActivity().getApplicationContext(), aBoolean.toString(),
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute(url);

    }

    void item(String id){
        new AsyncTask<String, Void, List<Request>>() {
            @Override
            protected List<Request> doInBackground(String... params) {
                return  Request.ReadRequestByOrderIDUserID(params[0],userid);
            }
            @Override
            protected void onPostExecute(List<Request> result) {
                try {
                    requests = result;
                    ListView item = getActivity().findViewById(R.id.itemList);
                    RequestItemAdapter adapter = new RequestItemAdapter(getActivity(),result);
                    item.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute(id);
    }


}
