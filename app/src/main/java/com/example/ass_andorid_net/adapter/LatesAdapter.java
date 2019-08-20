package com.example.ass_andorid_net.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_andorid_net.R;
import com.example.ass_andorid_net.adapter.hodel.LateHolder;
import com.example.ass_andorid_net.modelLates.Lates;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Lates> latesList;
    private Context context;

    public LatesAdapter(List<Lates> latesList, Context context) {
        this.latesList = latesList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.latest,parent,false);
        return new LateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Lates lates = latesList.get(position);
        LateHolder lateHolder = (LateHolder) holder;
        lateHolder.tvXem.setText(""+lates.getPost());
        lateHolder.tvYeu.setText(""+lates.getPost());
        Picasso.get().load(lates.getMediaDetails().getSizes().getThumbnail().getSourceUrl())
                .placeholder(R.drawable.backgroundnavi)
                .error(R.drawable.backgroundnavi)
                .into(lateHolder.imgLate);
    }

    @Override
    public int getItemCount() {
        return latesList.size();
    }
}
