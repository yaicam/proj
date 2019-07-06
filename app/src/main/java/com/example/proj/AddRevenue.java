package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.renderscript.Sampler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddRevenue extends AppCompatActivity {

EditText IDcard,Detail,Value;
Button Adddata;
TextView Date;

private DatePickerDialog.OnDateSetListener mDateSetListeber;





    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_revenue);

        IDcard = findViewById(R.id.ID_card);
        Detail = findViewById(R.id.etDetail);
        Date = findViewById(R.id.tvDate);
        Value = findViewById(R.id.etValue);

        Adddata = findViewById(R.id.btAddRe);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddRevenue.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListeber,
                        day, month, year
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListeber = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                month = month + 1;
                Log.d("debug", "onDateSet: date:" + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                Date.setText(date);
            }
        };

        Adddata.setOnClickListener(new View.OnClickListener() {

            private Map<String, Object> docdata;

            @Override
            public void onClick(View view) {
                 String ID_Card = IDcard.getText().toString();
                String Detail_Add = Detail.getText().toString();
                String DateAdd =Date.getText().toString();
                Double Value_Add = Double.valueOf(Value.getText().toString());

                if (TextUtils.isEmpty(ID_Card)) {
                    Toast.makeText(AddRevenue.this, "Please Enter ID Card.", Toast.LENGTH_SHORT).show();
                }
                if (ID_Card.length() != 13) {
                    IDcard.setError("Enter a valid ID card.");
                    IDcard.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(Detail_Add)) {
                    Toast.makeText(AddRevenue.this, "Please Enter Detail.", Toast.LENGTH_SHORT).show();
                }

                if (Value.length() <=0) {
                    Toast.makeText(AddRevenue.this, "Please Enter Value.", Toast.LENGTH_SHORT).show();
                }



                docdata = new HashMap<>();
                docdata.put("ID_Card", ID_Card);
                docdata.put("Detail", Detail_Add);
                docdata.put("Date", DateAdd);
                docdata.put("Value", Value_Add);

                db.collection("Ledger")
                        .add(docdata)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddRevenue.this,"Success",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddRevenue.this,EmployeeLogined.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(AddRevenue.this,"Error : "+ error,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }


}








