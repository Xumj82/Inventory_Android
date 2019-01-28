package com.adproject.android.inventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.adproject.android.inventory.Entity.Catalogue;

public class EditCatalogueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_catalogue);
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
        Button btn = findViewById(R.id.btnSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdit();
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCatalogueActivity.this);
                builder.setMessage("Update successfully").setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
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
        new AsyncTask<Catalogue, Void, Void>() {
            @Override
            protected Void doInBackground(Catalogue... params) {
                Catalogue.save(params[0]);
                return null;
            }
        }.execute(book);
    }
}
