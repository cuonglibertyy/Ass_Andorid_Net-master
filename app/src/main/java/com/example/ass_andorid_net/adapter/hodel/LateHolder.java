package com.example.ass_andorid_net.adapter.hodel;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_andorid_net.R;

public class LateHolder extends RecyclerView.ViewHolder {
    public ImageView imgLate;
    public TextView tvYeu,tvXem;
    public LateHolder(@NonNull View itemView) {
        super(itemView);
        tvXem = itemView.findViewById(R.id.tvXem);
        tvYeu = itemView.findViewById(R.id.tvYeu);
        imgLate = itemView.findViewById(R.id.imgLatest);
    }
}
