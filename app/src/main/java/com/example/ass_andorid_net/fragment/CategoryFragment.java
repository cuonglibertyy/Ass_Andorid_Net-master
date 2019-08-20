package com.example.ass_andorid_net.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ass_andorid_net.AlbumActivity;
import com.example.ass_andorid_net.R;
import com.example.ass_andorid_net.adapter.CategoryAdapter;
import com.example.ass_andorid_net.adapter.interfaceOnClick.OnclickCategory;
import com.example.ass_andorid_net.model.Category;
import com.example.ass_andorid_net.retrofit.EndlessRecyclerViewScrollListener;
import com.example.ass_andorid_net.retrofit.PolyRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private RecyclerView lvList;

    private SwipeRefreshLayout swipe;


    private int page = 1;
    private int per_page = 5;
    private List<Category> categories;
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment,container,false);
        lvList = view.findViewById(R.id.recy);
        swipe = view.findViewById(R.id.swipe1);


        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categories);
        linearLayoutManager = new GridLayoutManager(getContext(),2,RecyclerView.HORIZONTAL,false);
        lvList.setLayoutManager(linearLayoutManager);
        lvList.setHasFixedSize(true);
        lvList.setAdapter(categoryAdapter);
        getData(page, per_page);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
//                categoryAdapter.setOnLoadMore(true);
//                lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//                    @Override
//                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                        getData(page+1, per_page);
//                        Log.d("okokokokok22",""+(page+1));
//                    }
//                });
            }
        });
        categoryAdapter.setCategory(new OnclickCategory() {
            @Override
            public void OnClick(Category category) {
                Intent intent = new Intent(getContext(), AlbumActivity.class);
                intent.putExtra("id",category.getId());
                startActivity(intent);
            }
        });
        lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getData(page + 1, per_page);
            }
        });
        return view;
    }

    public void getData(int page, int per_page) {

        PolyRetrofit.getInstance().getCategories(page, per_page).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        swipe.setRefreshing(false);
                        if (response.body().size() == 0){
                            lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                                @Override
                                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                                }
                            });

                            categoryAdapter.setOnLoadMore(false);
                        }

                        categories.addAll(response.body());
                        categoryAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        swipe.setRefreshing(false);
                    }
                });

    }
}
