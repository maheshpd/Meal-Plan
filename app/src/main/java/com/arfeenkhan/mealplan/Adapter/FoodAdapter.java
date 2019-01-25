package com.arfeenkhan.mealplan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arfeenkhan.mealplan.Model.FoodItem;
import com.arfeenkhan.mealplan.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyFoodItemView> {
    private Context mContext;
    private List<FoodItem> foodlist;

    public FoodAdapter(Context mContext, List<FoodItem> albumList) {
        this.mContext = mContext;
        this.foodlist = albumList;
    }

    @NonNull
    @Override
    public MyFoodItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_item, viewGroup, false);
        return new MyFoodItemView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFoodItemView holder, int i) {
        FoodItem fi = foodlist.get(i);
        holder.name.setText(fi.getName());
        Glide.with(mContext).load(fi.getThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public class MyFoodItemView extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView thumbnail;

        public MyFoodItemView(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.food_name);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }

}
