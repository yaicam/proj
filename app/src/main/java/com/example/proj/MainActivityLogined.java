package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proj.Class.Revenue_Model;

import com.example.proj.Class.User_Model;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivityLogined extends AppCompatActivity {
    TextView userName, Rest, Total;
    Button Logout;
    Button Profile;
    Button Account;
    String doc_id;
    String idc = null;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Ledger");
    private RevenueRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logined);


        userName = findViewById(R.id.showname);
        Rest = findViewById(R.id.tvRests);
        Total = findViewById(R.id.tvTotalaa);

        Logout = findViewById(R.id.btLogout);

        Profile = findViewById(R.id.btPf);
        Account = findViewById(R.id.btAcc);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("debug", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d("debug", token);
                        Toast.makeText(MainActivityLogined.this, token, Toast.LENGTH_SHORT).show();
                    }
                });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        CollectionReference ref = FirebaseFirestore.getInstance().collection("users");
        ref.whereEqualTo("uid", firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    userName.setText(document.getString("First_name"));
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivityLogined.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("uid", document.getId());
                    editor.apply();

                    MainActivityAcc m = new MainActivityAcc();
                    m.listening(document.getId(), MainActivityLogined.this);

                }
            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivityLogined.this, MainActivity.class));
            }
        });

        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityLogined.this, MainActivityAcc.class));
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityLogined.this, ActivityPf.class));
            }
        });

        getidcard();

    }

    void getidcard() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        idc = String.valueOf(document.get("ID_Card"));
                        doc_id = document.getId();
                    }
                    sum(idc);
                    Toast.makeText(getApplicationContext(), idc, Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "get uid fail!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void get_sum_fillfuel(String doc_id, final Double sum_leadger) {
        FirebaseFirestore.getInstance().collection("fillfuel").whereEqualTo("UserID", doc_id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Double sum = 0.0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        sum += Double.valueOf(String.valueOf(document.get("SumValue")));
                    }
                    Double total =  sum_leadger - sum;
                    String result = String.format("%.2f", total);
                    Rest.setText(result);
                    readdata();

                } else {
                    Toast.makeText(getApplicationContext(), "get sum fillfuel fail!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    void sum(String IDC) {
        FirebaseFirestore.getInstance().collection("Ledger").whereEqualTo("ID_Card", IDC).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Double sum = 0.0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        sum += Double.valueOf(String.valueOf(document.get("Value")));
                    }

                    String result = String.format("%.2f", sum);
                    Total.setText(result);

                    Toast.makeText(getApplicationContext(),sum.toString() , Toast.LENGTH_LONG).show();

                    get_sum_fillfuel(doc_id,sum);

                } else {
                    Toast.makeText(getApplicationContext(), "sum fail!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void readdata() {
        Query query = notebookRef.orderBy("ID_Card", Query.Direction.DESCENDING).whereEqualTo("ID_Card",idc);
        FirestoreRecyclerOptions<Revenue_Model> options = new FirestoreRecyclerOptions.Builder<Revenue_Model>()
                .setQuery(query, Revenue_Model.class)
                .build();

        adapter = new RevenueRecycleViewAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.revenue_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

}
