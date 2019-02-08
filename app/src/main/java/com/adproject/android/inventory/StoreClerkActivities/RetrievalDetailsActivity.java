package com.adproject.android.inventory.StoreClerkActivities;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adproject.android.inventory.Connection.HttpConnection;
import com.adproject.android.inventory.Entity.Retrieval;
import com.adproject.android.inventory.R;
import com.adproject.android.inventory.StoreClerkActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RetrievalDetailsActivity extends AppCompatActivity {

    Activity activity = this;
    Retrieval retrieval;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeclerk_activity_retrieval_details);
        setTitle("Retrieval Details");
        retrieval = new Retrieval(
                getIntent().getExtras().getString("requestId"),
                getIntent().getExtras().getString("itemDescription"),
                getIntent().getExtras().getString("neededQuantity"),
                getIntent().getExtras().getString("availableQuantity"),
                getIntent().getExtras().getString("binNumber"),
                getIntent().getExtras().getString("remarks"),
                getIntent().getExtras().getString("orderid")
        );
        TextView textView1 = findViewById(R.id.textRequestID);
        TextView textView2 = findViewById(R.id.textItemName);
        TextView textView3 = findViewById(R.id.textRequestedQuantity);
        final TextView textView4 = findViewById(R.id.textAvailableQuantity);
        TextView textView5 = findViewById(R.id.textBin);
        final EditText editText1 = findViewById(R.id.editQuantityPicked);
        final EditText editText2 = findViewById(R.id.editRemarks);
        textView1.setText(retrieval.get("requestId"));
        textView2.setText(retrieval.get("itemDescription"));
        textView3.setText(retrieval.get("neededQuantity"));
        textView4.setText(retrieval.get("availableQuantity"));
        textView5.setText(retrieval.get("binNumber"));

        btnUpdate = findViewById(R.id.buttonUpdateInventory);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText1.getText().toString().equals("")) {
                    String pickQty = editText1.getText().toString();
                    String remarks = editText2.getText().toString();
                    int availableQty = Integer.parseInt(textView4.getText().toString());
                    if (Integer.parseInt(pickQty) < 0) {
                        editText1.setError("Cannot less than 0");
                    } else if (Integer.parseInt(pickQty)> availableQty) {
                        editText1.setError("Cannot more than available quantity");
                    } else {
                        UpdateInventory(retrieval,pickQty,remarks);
                    }
                }else {
                    editText1.setError("Please enter pick up quantity");
                }
            }
        });


    }

    void UpdateInventory(Retrieval retrieval, final String pickup, final String remarks){
        new AsyncTask<Retrieval, Integer, String>() {
            @Override
            protected String doInBackground(Retrieval... retrievals) {
                publishProgress(View.VISIBLE);
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("itemDescription", retrievals[0].get("itemDescription"));
                    jsonObject.put("quantityPicked",pickup);
                    jsonObject.put("remarks",remarks);
                    jsonArray.put(jsonObject);
                    String url = "https://lusis.azurewebsites.net/StoreClerk/UpdateInventory";
                    HttpConnection.postJSONArray(url, jsonArray);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
                publishProgress(View.INVISIBLE);
                return HttpConnection.message;
            }

            @Override
            protected void onPostExecute(String s) {
                if(s.equals("OK")){
                    onBackPressed();
                }else {
                    Toast.makeText(activity,"Sever error",Toast.LENGTH_LONG).show();
                }
                super.onPostExecute(s);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                ProgressBar progressBar = findViewById(R.id.progressBarRetrievalDetail);
                progressBar.setVisibility(values[0]);
                if(values[0].equals(View.VISIBLE)){
                    btnUpdate.setEnabled(false);
                }else if(values[0].equals(View.INVISIBLE)){
                    btnUpdate.setEnabled(true);
                }
            }
        }.execute(retrieval);
    }
}
