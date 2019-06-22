package com.example.proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivityAcc extends AppCompatActivity {
    ImageButton BackButton;
    TextView ShowCode;
    TextView ShowMem;
    TextView ShowSumm;
    String TAG = "MainActivityAcc";
    String Datacode = "datacode";
    String DataDetail = "datadetail";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference doc_ref = db.collection("filluel");
    DocumentReference Docs = db.document("fillfuel/");

    boolean UserID = db.collection("fillfuel").equals("UserID");




    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acc);

        BackButton = findViewById(R.id.btBack);
        ShowCode = findViewById(R.id.tvRow1);
        ShowMem = findViewById(R.id.tvRow2);
        ShowSumm = findViewById(R.id.tvSum);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityAcc.this, MainActivityLogined.class));
            }
        });



        }
    }

