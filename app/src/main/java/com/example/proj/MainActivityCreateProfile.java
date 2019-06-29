package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proj.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.Address;

import java.util.HashMap;
import java.util.Map;

public class MainActivityCreateProfile extends AppCompatActivity {

    ImageButton BackButton;
    EditText Firstname;
    EditText Lastname;
    EditText IDcard;
    EditText Address;
    EditText Phone;
    EditText Email;
    EditText Password;
    Button createAcc;

    private ProgressBar progressbar;

    DatabaseReference databaseReference;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    String TAG = "debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_profile);
        Firstname = findViewById(R.id.etFN);
        Lastname = findViewById(R.id.etLN);
        IDcard = findViewById(R.id.etIDcard);
        Address = findViewById(R.id.etAd);
        Phone = findViewById(R.id.etPh);
        Email = findViewById(R.id.etEm);
        Password = findViewById(R.id.etPs);
        createAcc = findViewById(R.id.btCreate);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        db = FirebaseFirestore.getInstance();

        BackButton = findViewById(R.id.btBack);
        progressbar = findViewById(R.id.progressBar);
        progressbar.setVisibility(View.GONE);


        mAuth = FirebaseAuth.getInstance();

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityCreateProfile.this, MainActivity.class));
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String First_name = Firstname.getText().toString();
                final String Last_name = Lastname.getText().toString();
                final String ID_Card = IDcard.getText().toString();
                final String address = Address.getText().toString();
                final String Phone_number = Phone.getText().toString();
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();

                if (TextUtils.isEmpty(First_name)) {
                    Toast.makeText(MainActivityCreateProfile.this, "Please Enter First name.", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Last_name)) {
                    Toast.makeText(MainActivityCreateProfile.this, "Please Enter Last name.", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(ID_Card)) {
                    Toast.makeText(MainActivityCreateProfile.this, "Please Enter IDcard.", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(MainActivityCreateProfile.this, "Please Enter Address.", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Phone_number)) {
                    Toast.makeText(MainActivityCreateProfile.this, "Please Enter Phone nume.", Toast.LENGTH_SHORT).show();
                }
                if (Phone_number.length() != 10) {
                    Phone.setError("Enter a valid phone number.");
                    Phone.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivityCreateProfile.this, "Please Enter Email.", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivityCreateProfile.this, "Please Enter Password.", Toast.LENGTH_SHORT).show();
                }
                if (password.length() < 6) {
                    Password.setError("Password should be atleast 6 characters long.");
                    Password.requestFocus();
                    return;
                }


                progressbar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressbar.setVisibility(View.GONE);
                                    db.collection("users").add(new User(First_name,
                                            Last_name,
                                            ID_Card,
                                            address,
                                            Phone_number,
                                            email, FirebaseAuth.getInstance().getUid(),password)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Log.i("debug", task.getResult().toString());

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            }
                        });

            }
        });
    }
}


/*
        Map<String, Object> newContact =new HashMap<>()  ;
        newContact.put("First name", Firstname);
        newContact.put("Last name", Lastname);
        newContact.put("ID Card", IDcard);
        newContact.put("Address", Address);
        newContact.put("Phone number", Phone);


*/










