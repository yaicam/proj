package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePrice extends AppCompatActivity {
    TextView NameOil, BeforePrice;
    EditText NewPrice;
    Button UpdatePrice;
    private static final String PRICE = "price";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Float Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_price);
        //NewPrice=findViewById(R.id.etNewPrice);
        UpdatePrice = findViewById(R.id.Update);
        NewPrice = findViewById(R.id.etNewPrice);
        NameOil = findViewById(R.id.tvNameOil);
        BeforePrice = findViewById(R.id.tvBeforePrice);

        NameOil.setText("Diesel");
        db.collection("oiltype").document("Diesel").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                BeforePrice.setText(String.valueOf(task.getResult().get("price")));
            }
        });


    }

    public void updateprice(View v) {
        final Double Price = Double.valueOf(NewPrice.getText().toString());

        db.collection("oiltype").document("Diesel")
                .update("price", Price).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String result = String.format("%.2f", (400 / Price));
                //double aa = Double.valueOf(result);
                float aa = Math.round(Float.parseFloat(result));
                db.collection("oiltype").document("Diesel")
                        .update("sum", aa).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });


    }
};

