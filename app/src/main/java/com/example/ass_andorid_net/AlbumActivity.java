package com.example.ass_andorid_net;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ass_andorid_net.adapter.AlbumAdapter;
import com.example.ass_andorid_net.adapter.CategoryAdapter;
import com.example.ass_andorid_net.adapter.interfaceOnClick.OnClickAblum;
import com.example.ass_andorid_net.model.Category;
import com.example.ass_andorid_net.modelPost.Post;
import com.example.ass_andorid_net.retrofit.PolyRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeAlbum;
    private RecyclerView listAlbum;
    private List<Post> posts;
    private AlbumAdapter albumAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        AnhXa();
        getData();
    }

    private void AnhXa() {
        swipeAlbum = (SwipeRefreshLayout) findViewById(R.id.swipeAlbum);
        listAlbum = (RecyclerView) findViewById(R.id.listAlbum);
        toolbar = findViewById(R.id.toolbarAlbum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        swipeAlbum.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeAlbum.setRefreshing(false);
            }
        });

        posts = new ArrayList<>();
        albumAdapter = new AlbumAdapter(this, posts);
        linearLayoutManager = new GridLayoutManager(this,2);
        listAlbum.setLayoutManager(linearLayoutManager);
        listAlbum.setHasFixedSize(true);
        listAlbum.setAdapter(albumAdapter);

        albumAdapter.setOnClickAblum(new OnClickAblum() {
            @Override
            public void OnClick(Post post) {
                Intent intent = new Intent(AlbumActivity.this,ImageActivity.class);
                intent.putExtra("string",post.getContent().getRendered());
                intent.putExtra("name",post.getTitle().getRendered());
                startActivity(intent);
            }
        });


    }

    private void getData() {
        PolyRetrofit.getInstance().getPostOfCategory(getIntent().getStringExtra("id"),"7","1").enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                posts.addAll(response.body());
                albumAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}
