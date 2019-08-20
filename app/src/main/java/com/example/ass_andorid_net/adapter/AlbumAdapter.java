package com.example.ass_andorid_net.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_andorid_net.R;
import com.example.ass_andorid_net.adapter.hodel.CateHolder;
import com.example.ass_andorid_net.adapter.hodel.LateHolder;
import com.example.ass_andorid_net.adapter.interfaceOnClick.OnClickAblum;
import com.example.ass_andorid_net.model.Category;
import com.example.ass_andorid_net.modelLates.Lates;
import com.example.ass_andorid_net.modelPost.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnClickAblum onClickAblum;
    Context context;
    List<Post> postList;

    public void setOnClickAblum(OnClickAblum onClickAblum) {
        this.onClickAblum = onClickAblum;
    }

    public AlbumAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album,parent,false);
        return new AbulmHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Post post = postList.get(position);
        AbulmHodel lateHolder = (AbulmHodel) holder;
         lateHolder.tvAlbum.setText(post.getTitle().getRendered());
         Picasso.get().load(post.getContent().getRendered().substring(post.getContent().getRendered().lastIndexOf("http"),post.getContent().getRendered().lastIndexOf(".jpg")+4))
                .placeholder(R.drawable.backgroundnavi)
                .error(R.drawable.backgroundnavi)
                .into(lateHolder.imgAlbum);
         lateHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 onClickAblum.OnClick(postList.get(position));
             }
         });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
    public class AbulmHodel extends RecyclerView.ViewHolder {
        public ImageView imgAlbum;
        public TextView tvAlbum;
        public AbulmHodel(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.imgAlbum);
            tvAlbum = itemView.findViewById(R.id.tvAlbum);
        }

    }
}
