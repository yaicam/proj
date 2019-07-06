package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EmployeeLogined extends AppCompatActivity {
    TextView Status;

    FirebaseUser firebaseUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("users");
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_longined);

        Status = findViewById(R.id.tvstatus);


        findViewById(R.id.add_revenue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddRevenue.class));
            }
        });
        findViewById(R.id.logout_Emp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


        readdata();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        CollectionReference ref = FirebaseFirestore.getInstance().collection("users");
        ref.whereEqualTo("uid", firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    Status.setText(document.getString("status"));
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EmployeeLogined.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("uid",document.getId());
                    editor.apply();

                    MainActivityAcc m= new MainActivityAcc();
                    m.listening(document.getId(),EmployeeLogined.this);


                }
            }
        });

    }

    private void readdata() {
        Query query = notebookRef.orderBy("First_name", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ModelList> options = new FirestoreRecyclerOptions.Builder<ModelList>()
                .setQuery(query, ModelList.class)
                .build();

        adapter = new RecyclerViewAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_forem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

