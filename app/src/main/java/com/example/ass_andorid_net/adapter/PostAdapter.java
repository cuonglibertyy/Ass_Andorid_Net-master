package com.example.ass_andorid_net.adapter;

import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ass_andorid_net.R;
import com.example.ass_andorid_net.adapter.hodel.LateHolder;
import com.example.ass_andorid_net.adapter.interfaceOnClick.OnClickListener;
import com.example.ass_andorid_net.modelLates.Lates;
import com.example.ass_andorid_net.modelpostLater.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    List<Post> dataPost;
    Context context;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PostAdapter(List<Post> dataPost, Context context) {
        this.dataPost = dataPost;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View item = layoutInflater.inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (i == 0 && i == dataPost.size()-1){
            Post post = dataPost.get(i);
            ViewHolder viewHoder = (ViewHolder) viewHolder;
            viewHoder.tvEyse.setTextColor(Color.RED);
            viewHoder.tvTim.setTextColor(Color.RED);
            Picasso.get().load(post.getEmbedded().getWpFeaturedmedia().get(0).getMediaDetails().getSizes().getFull().getSourceUrl()).placeholder(R.drawable.ok).error(R.drawable.ok).into(viewHolder.img_Item);
            viewHoder.img_Item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClickListener(dataPost.get(i));
                }
            });

        }else {
            Post post = dataPost.get(i);
            ViewHolder viewHoder = (ViewHolder) viewHolder;
            viewHoder.tvEyse.setTextColor(Color.BLUE);
            viewHoder.tvTim.setTextColor(Color.BLUE);
            Picasso.get().load(post.getEmbedded().getWpFeaturedmedia().get(0).getMediaDetails().getSizes().getFull().getSourceUrl()).placeholder(R.drawable.ok).error(R.drawable.ok).into(viewHolder.img_Item);
            viewHoder.img_Item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClickListener(dataPost.get(i));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Item;
        TextView tvTim,tvEyse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Item = itemView.findViewById(R.id.imgPost);
            tvEyse = itemView.findViewById(R.id.tvEyse);
            tvTim = itemView.findViewById(R.id.tvTim);
        }


    }

}
