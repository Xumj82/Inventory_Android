package com.adproject.android.inventory.StoreClerkActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.adproject.android.inventory.Entity.Catalogue;
import com.adproject.android.inventory.R;

public class EditCatalogueActivity extends AppCompatActivity {
    Activity activity;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeclerk_edit_catalogue);
        activity=this;
        setTitle("Edit");
        Intent intent = getIntent();
        final TextView txt1 = findViewById(R.id.txtItemID);
        final TextView txt2 = findViewById(R.id.txtDescription);
        final TextView txt3 = findViewById(R.id.txtQuantity);
        final TextView txt4 = findViewById(R.id.txtCategory);
        final TextView txt5 = findViewById(R.id.txtMeasureUnit);
        final TextView txt6 = findViewById(R.id.txtPrice);
        final EditText txt8 = findViewById(R.id.etBinNumber);
        txt1.setText(intent.getStringExtra("ItemID"));
        txt2.setText(intent.getStringExtra("Description"));
        txt3.setText(intent.getStringExtra("Quantity"));
        txt4.setText(intent.getStringExtra("Category"));
        txt5.setText(intent.getStringExtra("MeasureUnit"));
        txt6.setText(intent.getStringExtra("Price"));
        txt8.setText(intent.getStringExtra("BinNumber"));
        btn = findViewById(R.id.btnSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdit();
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCatalogueActivity.this);
//                builder.setMessage("Update successfully").setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).create().show();
            }
        });
    }

    void saveEdit() {
        int[] src = new int[]{R.id.txtItemID, R.id.txtDescription, R.id.txtQuantity, R.id.txtCategory, R.id.txtMeasureUnit, R.id.txtPrice};
        String[] dest = new String[7];
        for (int n = 0; n < src.length; n++) {
            TextView txt = findViewById(src[n]);
            dest[n] = txt.getText().toString();
        }
        EditText txt = findViewById(R.id.etBinNumber);
        dest[6] = txt.getText().toString();
        Catalogue book = new Catalogue(dest[0], dest[1], dest[2], dest[3], dest[4], dest[5], dest[6]);
        new AsyncTask<Catalogue, Integer, Void>() {
            @Override
            protected Void doInBackground(Catalogue... params) {
                publishProgress(View.VISIBLE);
                Catalogue.save(params[0]);
                publishProgress(View.INVISIBLE);
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                ProgressBar progressBar = findViewById(R.id.progressBarEidtCatalo);
                progressBar.setVisibility(values[0]);
                if(values[0].equals(View.VISIBLE)){
                    btn.setEnabled(false);
                }else if(values[0].equals(View.INVISIBLE)){
                    btn.setEnabled(true);
                    Toast.makeText(activity,"success",Toast.LENGTH_LONG).show();
                }
            }
        }.execute(book);
    }
}
