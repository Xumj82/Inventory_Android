package com.adproject.android.inventory.StoreClerkActivities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adproject.android.inventory.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        final SignaturePad signaturePad = findViewById(R.id.signaturePad);
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
                Toast.makeText(activity,"update",Toast.LENGTH_LONG);
                onBackPressed();
            }
        });

    }
}
