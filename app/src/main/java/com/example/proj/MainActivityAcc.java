package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proj.Class.User_Model;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivityAcc extends AppCompatActivity {
    TextView userName;
    TextView SumValue;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("fillfuel");
    private UserRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acc);

        SumValue = findViewById(R.id.tSum);
        userName = findViewById(R.id.showname);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        CollectionReference ref = FirebaseFirestore.getInstance().collection("users");
        ref.whereEqualTo("uid", firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    userName.setText(document.getString("First_name"));
                }
            }
        });

        readdata();

    }

    public void listening(final String uid, final Context context){

        db.collection("OTPH")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("debug", "Listen failed.", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    String us = dc.getDocument().getString("UserID");
                                    if (uid.equals(us)){
                                        Notify.show_Notification(String.valueOf(dc.getDocument().getLong("OTP")),context);
                                    }
                                    break;


                            }
                        }

                    }
                });


    }


    public void show_Notification(String otp,Context context){

        String msg = " OTP password : "+otp;

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    private void readdata() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String uidd = null;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        uidd = document.getId();
                    }
                    sum(uidd);
                    listening(uidd,MainActivityAcc.this);

                    Query query = notebookRef.orderBy("Date", Query.Direction.DESCENDING).whereEqualTo("UserID", uidd);

                    FirestoreRecyclerOptions<User_Model> options = new FirestoreRecyclerOptions.Builder<User_Model>()
                            .setQuery(query, User_Model.class)
                            .build();

                    adapter = new UserRecyclerViewAdapter(options);

                    RecyclerView recyclerView = findViewById(R.id.user_recycler_view);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();


                } else {

                    Toast.makeText(getApplicationContext(), "read data fail!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    void sum (String uidd){
        FirebaseFirestore.getInstance().collection("fillfuel").whereEqualTo("UserID", uidd).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    Double sum = 0.0 ;

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        sum += Double.valueOf(String.valueOf(document.get("SumValue")));
                    }

                    String result = String.format("%.2f", sum);

                    SumValue.setText(result);

                }
                else{
                    Toast.makeText(getApplicationContext(), "sum fail!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }



}

