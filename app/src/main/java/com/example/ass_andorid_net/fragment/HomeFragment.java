package com.example.ass_andorid_net.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ass_andorid_net.AlbumActivity;
import com.example.ass_andorid_net.ImageActivity;
import com.example.ass_andorid_net.ImageDetailActivity;
import com.example.ass_andorid_net.R;
import com.example.ass_andorid_net.adapter.AlbumAdapter;
import com.example.ass_andorid_net.adapter.PostAdapter;
import com.example.ass_andorid_net.adapter.interfaceOnClick.OnClickListener;
import com.example.ass_andorid_net.modelpostLater.Post;
import com.example.ass_andorid_net.retrofit.PolyRetrofit;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private List<Post> posts;
    private PostAdapter postAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us_fragment,container,false);
        swipe = view.findViewById(R.id.swipeMedia);
        recyclerView = view.findViewById(R.id.lvLatest);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
            }
        });

        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts, getContext());
        linearLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(postAdapter);
        getData();

       postAdapter.setOnClickListener(new OnClickListener() {
           @Override
           public void onClickListener(Post post) {
               Intent intent = new Intent(getActivity(), ImageActivity.class);
               intent.putExtra("string",post.getContent().getRendered());
               intent.putExtra("name"," List Image ");
               startActivity(intent);
           }
       });
        return view;
    }
    private void getData() {
        PolyRetrofit.getInstance().getPostOfhome().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                posts.addAll(response.body());
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}

