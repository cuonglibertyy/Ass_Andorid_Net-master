package com.example.ass_andorid_net;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ass_andorid_net.adapter.ContentAdapter;
import com.example.ass_andorid_net.adapter.interfaceOnClick.OnClickImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> linkList;
    private ContentAdapter contentAdapter;
    private String content,title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        content =  intent.getStringExtra("string");
        title = intent.getStringExtra("name");
        Log.d("ddd",content);
        linkList = new ArrayList<>();
        contentAdapter = new ContentAdapter(this,linkList);
        recyclerView = findViewById(R.id.rvPost);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        toolbar = findViewById(R.id.toolbarImage);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getLink();
    }
    private void getLink() {
        Pattern pattern = Pattern.compile("/uploads(?<url>.*?)jpg.*?");
        Matcher matcher = pattern.matcher(content.trim());

        String s = "";

        while (matcher.find()) {
            int lengthm = matcher.group(1).length();
            int lengths = s.length();

            if(!matcher.group(1).substring(0, getLength(lengths, lengthm))
                    .equalsIgnoreCase(s.substring(0, getLength(lengths, lengthm)))
                    || s.length() == 0
            ){
                linkList.add("http://asian.dotplays.com/wp-content/uploads" + matcher.group(1) + "jpg");
                s = matcher.group(1);
            }else{
                s = matcher.group(1);
            }
        }
        contentAdapter.setOnClickImage(new OnClickImage() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ImageDetailActivity.class);
                intent.putExtra("imageContent", linkList.get(position));
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(contentAdapter);
    }

    int getLength(int i, int s){
        if (i > 10 && i < 14 && s > 10 && s < 14) return 9;
        else if (i < 37 && i >= 14 && s < 37 && s > 14) return 14;
        else if (i >= 37 && i < 42 && s >= 37 && s < 42)return 37;
        else if (i >= 42 && s >= 42) return 42;
        else return 0;
    }
}
