package com.example.ass_andorid_net.adapter.hodel;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ass_andorid_net.R;

public class CateHolder extends RecyclerView.ViewHolder {
    public RelativeLayout imgBack;
    public TextView tvContent,tvCount;
    ImageView  imgCate;
    public CateHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tvContent);
        tvCount = itemView.findViewById(R.id.tvCount);
        imgBack = itemView.findViewById(R.id.imgBack);
        imgCate =itemView.findViewById(R.id.imgCate);

        Glide.with(itemView.getContext()).load(R.drawable.tramanh).into(imgCate);
    }
}
