package com.example.ass_andorid_net.retrofit;

import com.example.ass_andorid_net.model.Category;
import com.example.ass_andorid_net.modelLates.Lates;
import com.example.ass_andorid_net.modelPost.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PolyService {

    @GET("wp-json/wp/v2/posts?_embed")
    Call<List<com.example.ass_andorid_net.modelpostLater.Post>> getPostOfhome();

    @GET("wp-json/wp/v2/categories")
    Call<List<Category>> getCategories(@Query("page") int page,
                                       @Query("per_page") int per_page);

    @GET("/wp-json/wp/v2/media")
    Call<List<Lates>> getLates();

    @GET("wp-json/wp/v2/posts")
    Call<List<Post>> getPostOfCategory(@Query("category") String categoryID,
                                       @Query("per_page") String per_page,
                                       @Query("paging") String paging
    );
}
