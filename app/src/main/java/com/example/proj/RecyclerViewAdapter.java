package com.example.proj;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class RecyclerViewAdapter extends FirestoreRecyclerAdapter<ModelList, RecyclerViewAdapter.NoteHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference Ref = db.collection("fillfuel");


    public RecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<ModelList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final NoteHolder holder, final int position, @NonNull final ModelList model) {

        final String id = getSnapshots().getSnapshot(position).getId();
        final String ida = getSnapshots().getSnapshot(position).getId();


        Ref.whereEqualTo("UserID", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    float sum = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        sum += Float.parseFloat(String.valueOf(document.get("SumValue")));
                    }
                    holder.sum_user.setText(String.valueOf(sum));
                    holder.name_user.setText(model.getFirst_name());
                    holder.lastname_user.setText(model.getLast_name());
                } else {
                    Toast.makeText(holder.itemView.getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        db.collection("fillfuel").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("sdfvgdfg", "Listen failed.", e);
                    return;
                } else {
                    readdata(holder, id, getSnapshots().get(position).getID_Card());
                }
            }
        });


        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), Detail.class);
                intent.putExtra("First_name", getSnapshots().get(position).getFirst_name());
                intent.putExtra("Last_name", getSnapshots().get(position).getLast_name());
                intent.putExtra("ID_Card", getSnapshots().get(position).getID_Card());
                intent.putExtra("address", getSnapshots().get(position).getAddress());
                intent.putExtra("Phone_number", getSnapshots().get(position).getPhone_number());
                intent.putExtra("email", getSnapshots().get(position).getEmail());
                intent.putExtra("uid", getSnapshots().get(position).getUid());
                intent.putExtra("doc_id", getSnapshots().getSnapshot(position).getId());
                holder.itemView.getContext().startActivity(intent);

            }
        });


    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list,
                parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView name_user, lastname_user, sum_user, balance_user;
        Button more;

        public NoteHolder(View itemView) {
            super(itemView);
            name_user = itemView.findViewById(R.id.name_user);
            lastname_user = itemView.findViewById(R.id.lastname_user);
            sum_user = itemView.findViewById(R.id.sum_user);
            more = itemView.findViewById(R.id.list_more);
            balance_user = itemView.findViewById(R.id.balance_user);
        }
    }

    public void readdata(final NoteHolder holder, String id, final String id_card) {

        Ref.whereEqualTo("UserID", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    double sum = 0.0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        sum += Float.parseFloat(String.valueOf(document.get("SumValue")));
                    }
                    holder.sum_user.setText(String.format("%.2f", sum));
                    final double finalSum = sum;

                    db.collection("Ledger").whereEqualTo("ID_Card", id_card).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Double sum_ledger = 0.0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sum_ledger += Double.valueOf(String.valueOf(document.get("Value")));
                            }
                            double total = sum_ledger - finalSum ;
                            holder.balance_user.setText(String.format("%.2f", total));


                        }
                    });

                } else {

                }
            }
        });
    }
}