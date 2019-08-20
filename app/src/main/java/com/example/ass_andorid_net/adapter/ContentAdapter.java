package com.example.ass_andorid_net.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ass_andorid_net.R;
import com.example.ass_andorid_net.adapter.interfaceOnClick.OnClickImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    private OnClickImage onClickImage;
    private Context context;
    private List<String> listLink;

    public ContentAdapter(Context context, List<String> listLink) {
        this.context = context;
        this.listLink = listLink;
    }

    public void setOnClickImage(OnClickImage onClickImage) {
        this.onClickImage = onClickImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_content, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Picasso.get().load(listLink.get(i))
                .placeholder(R.drawable.backgroundnavi)
                .error(R.drawable.backgroundnavi)
                .into(viewHolder.imgPost);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickImage.onItemClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listLink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPost = itemView.findViewById(R.id.img_avtPost);
        }
    }
}
