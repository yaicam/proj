package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Detail extends AppCompatActivity {

    TextView txtString;
    private WebView myWebView;


    TextView name_detail, lastname_detail, idcard_detail, phone_detail, address_detail, email_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name_detail = findViewById(R.id.name_detail);
        lastname_detail = findViewById(R.id.lastname_detail);
        idcard_detail = findViewById(R.id.idcard_detail);
        phone_detail = findViewById(R.id.phone_detail);
        address_detail = findViewById(R.id.address_detail);
        email_detail = findViewById(R.id.email_detail);
        myWebView =  findViewById(R.id.webView);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("E9vr1mUghZW5gqMMayC7XI3Hhoz2")){
            findViewById(R.id.Deleteuser).setVisibility(View.GONE);
        }

        findViewById(R.id.Deleteuser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String doc_id = getIntent().getExtras().getString("doc_id");
                FirebaseFirestore.getInstance().collection("users").document(doc_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        final String uid = String.valueOf(task.getResult().get("uid"));
                        FirebaseFirestore.getInstance().collection("users").document(doc_id).update("password", "").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"กำลังลบผู้ใช้",Toast.LENGTH_SHORT).show();
                                String url = "https://us-central1-proj-4a40b.cloudfunctions.net/deleteuser?text="+uid;
                                myWebView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        super.onPageFinished(view, url);
                                        Toast.makeText(getApplicationContext(),"ลบ(ู้ใช้สำเร็จ",Toast.LENGTH_LONG).show();
                                        finish();
                                    }

                                    @Override
                                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                        super.onReceivedError(view, request, error);
                                        Toast.makeText(getApplicationContext(),"ลบ(ู้ใช้ไม่สำเร็จ",Toast.LENGTH_LONG).show();

                                    }
                                });
                                myWebView.loadUrl(url);
                            }
                        });

                    }
                });

            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            showdetail();

        else {
        }
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
