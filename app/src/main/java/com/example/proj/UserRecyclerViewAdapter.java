package com.example.proj;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proj.Class.User_Model;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UserRecyclerViewAdapter extends FirestoreRecyclerAdapter<User_Model, UserRecyclerViewAdapter.NoteHolder> {



    public UserRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<User_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final NoteHolder holder, final int position, @NonNull final User_Model model) {
        Date date = model.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String strDate = dateFormat.format(date);
        holder.time.setText(strDate);
        String result = String.format("%.2f", model.getSumValue());
        holder.sumaa.setText(result);

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,
                parent, false);
        return new NoteHolder(v);
    }



    class NoteHolder extends RecyclerView.ViewHolder {
        TextView time ,sumaa;


        public NoteHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            sumaa = itemView.findViewById(R.id.sumaa);

        }
    }

}