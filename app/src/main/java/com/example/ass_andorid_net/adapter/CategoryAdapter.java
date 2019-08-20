package com.example.ass_andorid_net.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ass_andorid_net.R;
import com.example.ass_andorid_net.adapter.hodel.CateHolder;
import com.example.ass_andorid_net.adapter.hodel.LoadHolder;
import com.example.ass_andorid_net.adapter.interfaceOnClick.OnclickCategory;
import com.example.ass_andorid_net.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnclickCategory onclickCategory;

    public void setCategory(OnclickCategory onclickCategory) {
        this.onclickCategory = onclickCategory;
    }

    public boolean isOnLoadMore() {
        return onLoadMore;
    }

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    private boolean onLoadMore = true;

    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.category, parent, false);
            return new CateHolder(view);
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.loadmore, parent, false);
            return new LoadHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CateHolder) {
            final Category category = categories.get(position);
            ((CateHolder) holder).tvContent.setText(category.getName());
            ((CateHolder) holder).tvCount.setText("("+category.getCount()+")");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclickCategory.OnClick(categories.get(position));

                }
            });
        } else if (holder instanceof LoadHolder){

        }
    }

    int ITEM = 1;
    int LOAD_MORE = 2;

    @Override
    public int getItemViewType(int position) {

        if (onLoadMore){
            if (position == categories.size() - 1)
                return LOAD_MORE;
            else return ITEM;
        }else return ITEM;
      }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
