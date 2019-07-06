package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.*;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ActivityPf extends AppCompatActivity {
    FirebaseFirestore db;

    TextView name_detail, lastname_detail, idcard_detail, phone_detail, address_detail, email_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pf);
db = FirebaseFirestore.getInstance();
        name_detail = findViewById(R.id.name_detail_user);
        lastname_detail = findViewById(R.id.lastname_detail_user);
        idcard_detail = findViewById(R.id.idcard_detail_user);
        phone_detail = findViewById(R.id.phone_detail_user);
        address_detail = findViewById(R.id.address_detail_user);
        email_detail = findViewById(R.id.email_detail_user);


        readdata();
    }

    private void readdata() {

        CollectionReference reference = db.collection("users");
        reference.whereEqualTo("uid", FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        name_detail.setText(document.getString("First_name"));
                        lastname_detail.setText(document.getString("Last_name"));
                        idcard_detail.setText(document.getString("ID_Card"));
                        address_detail.setText(document.getString("address"));
                        email_detail.setText(document.getString("email"));
                        phone_detail.setText(document.getString("Phone_number"));
                    }
                }
            }
        });
    }
}

