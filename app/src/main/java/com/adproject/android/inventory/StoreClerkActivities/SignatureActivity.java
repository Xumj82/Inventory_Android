package com.adproject.android.inventory.StoreClerkActivities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adproject.android.inventory.Connection.HttpConnection;
import com.adproject.android.inventory.Entity.Catalogue;
import com.adproject.android.inventory.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.util.List;

public class SignatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        setTitle("Signature Pad");
        final List<String> orderid = getIntent().getExtras().getStringArrayList("orderids");
        final SignaturePad signaturePad = findViewById(R.id.signaturePad);
        try {
            getSignature(orderid.get(0), signaturePad);
        }catch (Exception e){
            e.printStackTrace();
        }
        Button btnClear = findViewById(R.id.buttonClear);
        Button btnSave = findViewById(R.id.buttonSignSave);
        final Activity activity = this;
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addsignatureToDB(signaturePad.getSignatureBitmap(),orderid,activity);

            }
        });



    }

    void getSignature(final String orderid, final SignaturePad signaturePad){
        new AsyncTask<String ,Void,Bitmap>(){

            @Override
            protected Bitmap doInBackground(String... strings) {
                String url = "https://lusis.azurewebsites.net/StoreClerk/GetImage?orderid="+orderid;
                Bitmap s = HttpConnection.getBitmap(url);
                return s;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                try {
                    signaturePad.setSignatureBitmap(bitmap);
                    super.onPostExecute(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute(orderid);
    }

    void addsignatureToDB(final Bitmap sign, final List<String> ids, final Activity activity){
        new AsyncTask<Bitmap, Void, String>() {
            @Override
            protected String doInBackground(Bitmap... bitmaps) {
                String idlist = "id0="+ids.get(0);
                for(int i =1;i<ids.size();i++){
                    idlist = idlist+"&id"+i+"="+ids.get(i);
                }
                String url = "https://lusis.azurewebsites.net/StoreClerk/SaveImage?"+idlist;
                return HttpConnection.postStream(url, bitmaps[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                String A = s;
                if(s.equals("OK")) {
                    Toast.makeText(activity,"Saved",Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    Toast.makeText(activity, "Fail", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(sign);


    }
}
