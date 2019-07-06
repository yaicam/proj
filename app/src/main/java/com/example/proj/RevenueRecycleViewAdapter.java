package com.example.proj;
import com.example.proj.Class.Revenue_Model;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class RevenueRecycleViewAdapter extends FirestoreRecyclerAdapter<Revenue_Model,  RevenueRecycleViewAdapter.NoteHolder>  {

    public RevenueRecycleViewAdapter(@NonNull FirestoreRecyclerOptions<Revenue_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final  NoteHolder holder,final int i, @NonNull Revenue_Model revenue_model) {
        String strDate = revenue_model.getDate();
        holder.timeadd.setText(strDate);
        String result = String.format("%.2f", revenue_model.getValue());
        holder.valueadd.setText(result);
        holder.detailaa.setText(revenue_model.getDetail());
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.revenue_list,
                parent, false);
        return new RevenueRecycleViewAdapter.NoteHolder(v);
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        TextView timeadd ,valueadd,detailaa;

        public NoteHolder(View itemView) {
            super(itemView);
            timeadd = itemView.findViewById(R.id.timeadd);
            valueadd = itemView.findViewById(R.id.valueadd);
            detailaa =itemView.findViewById(R.id.detailaa);

        }
    }
}
