package com.adproject.android.inventory.StoreClerkActivities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adproject.android.inventory.Entity.Retrieval;
import com.adproject.android.inventory.R;

public class RetrievalDetailsActivity extends AppCompatActivity {

    Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeclerk_activity_retrieval_details);
        Retrieval retrieval = new Retrieval(
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

        Button btnUpdate = findViewById(R.id.buttonUpdateInventory);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText1.getText().toString().equals("")) {
                    int pickQty = Integer.parseInt(editText1.getText().toString());
                    String remarks = editText2.getText().toString();
                    int availableQty = Integer.parseInt(textView4.getText().toString());
                    if (pickQty < 0) {
                        editText1.setError("cannot less than 0");
                    } else if (pickQty > availableQty) {
                        editText1.setError("cannot more than available quantity");
                    } else {
                        UpdateInventory();
                        onBackPressed();
                    }
                }else {
                    editText1.setError("Please enter pick up number");
                }
            }
        });


    }

    void UpdateInventory(){
        Toast.makeText(this.getApplicationContext(), "Update",
                Toast.LENGTH_SHORT).show();
    }
}
