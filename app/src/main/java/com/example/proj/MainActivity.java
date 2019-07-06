package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button Login;


    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPass);
        Login = findViewById(R.id.btLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (uid.equals("PxinOPJfVhbjoBP6MJck8BM9vGF3"))
                startActivity(new Intent(MainActivity.this, AdminBoard.class));
            else
                startActivity(new Intent(MainActivity.this, MainActivityLogined.class));
        } else {
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                            password.getText().toString())
                            .addOnCompleteListener((new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        if (uid.equals("PxinOPJfVhbjoBP6MJck8BM9vGF3")){
                                            startActivity(new Intent(MainActivity.this, AdminBoard.class));}
                                        else if (uid.equals("E9vr1mUghZW5gqMMayC7XI3Hhoz2")){
                                            startActivity(new Intent(MainActivity.this,AdminBoard.class));}
                                        else {
                                            startActivity(new Intent(MainActivity.this, MainActivityLogined.class));
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();

                                    }
                                }
                            }));
                }
            });
        }


    }

}

