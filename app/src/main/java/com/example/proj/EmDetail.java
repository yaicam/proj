package com.example.proj;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class EmDetail  extends AppCompatActivity {
    TextView name_detail,lastname_detail,idcard_detail,phone_detail,address_detail,email_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_detail);

        name_detail= findViewById(R.id.name_detail);
        lastname_detail= findViewById(R.id.lastname_detail);
        idcard_detail= findViewById(R.id.idcard_detail);
        phone_detail= findViewById(R.id.phone_detail);
        address_detail= findViewById(R.id.address_detail);
        email_detail= findViewById(R.id.email_detail);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            showdetail();

        else {}
    }

    private void showdetail() {

        String n = getIntent().getExtras().getString("First_name");
        String l = getIntent().getExtras().getString("Last_name");
        String id = getIntent().getExtras().getString("ID_Card");
        String p = getIntent().getExtras().getString("Phone_number");
        String a = getIntent().getExtras().getString("address");
        String e = getIntent().getExtras().getString("email");


        name_detail.setText(n);
        lastname_detail.setText(l);
        idcard_detail.setText(id);
        phone_detail.setText(p);
        address_detail.setText(a);
        email_detail.setText(e);
    }
}
