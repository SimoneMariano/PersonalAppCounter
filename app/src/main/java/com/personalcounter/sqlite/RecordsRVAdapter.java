package com.personalcounter.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class RecordsRVAdapter extends RecyclerView.Adapter<RecordsRVAdapter.ViewHolder> {
    private ArrayList<RecordModel> recordModelArrayList;
    private Context context;

    public RecordsRVAdapter(ArrayList<RecordModel> recordModelArrayList, Context context) {
        this.recordModelArrayList = recordModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecordModel modal = recordModelArrayList.get(position);
        holder.dateTV.append(modal.getdate());
        holder.player1TV.append(modal.getplayer1());
        holder.player2TV.append(modal.getplayer2());
        holder.resultTV.append(modal.getresult());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return recordModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView dateTV, player1TV, player2TV, resultTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            dateTV = itemView.findViewById(R.id.idTVdate);
            player1TV = itemView.findViewById(R.id.idTVplayer1);
            player2TV = itemView.findViewById(R.id.idTVplayer2);
            resultTV = itemView.findViewById(R.id.idTVresult);
        }
    }
}
