package com.arfeenkhan.mealplan.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arfeenkhan.mealplan.Adapter.FoodAdapter;
import com.arfeenkhan.mealplan.Model.FoodItem;
import com.arfeenkhan.mealplan.R;
import com.arfeenkhan.mealplan.Utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private RecyclerView recyclerView;
    private List<FoodItem> foodlist;
    private FoodAdapter adapter;
    private Context context;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

        recyclerView = view.findViewById(R.id.food_recycler);
        foodlist = new ArrayList<>();
        adapter = new FoodAdapter(context, foodlist);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        return view;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.vegetarian_img,
                R.drawable.italian,
                R.drawable.vegan,
                R.drawable.gluten_free,
                R.drawable.vegetarian_img,
                R.drawable.asian,
                R.drawable.japanese,
                R.drawable.american,
                R.drawable.spanish,
                R.drawable.mexican,
                R.drawable.healthy,
                R.drawable.mexican,
                R.drawable.healthy};

        FoodItem a = new FoodItem("Vegetarian", covers[0]);
        foodlist.add(a);

        FoodItem b = new FoodItem("Italian", covers[1]);
        foodlist.add(b);

        FoodItem c = new FoodItem("Vegan", covers[2]);
        foodlist.add(c);

        FoodItem d = new FoodItem("Gluten Free", covers[3]);
        foodlist.add(d);

        FoodItem e = new FoodItem("Asian", covers[4]);
        foodlist.add(e);

        FoodItem f = new FoodItem("Japanese", covers[5]);
        foodlist.add(f);

        FoodItem g = new FoodItem("American", covers[6]);
        foodlist.add(g);

        FoodItem h = new FoodItem("Spanish", covers[7]);
        foodlist.add(h);

        FoodItem i = new FoodItem("Mexican", covers[8]);
        foodlist.add(i);

        FoodItem j = new FoodItem("Healthy", covers[9]);
        foodlist.add(j);

        FoodItem k = new FoodItem("Healthy", covers[9]);
        foodlist.add(k);

        FoodItem l = new FoodItem("Healthy", covers[9]);
        foodlist.add(l);

        FoodItem m = new FoodItem("Healthy", covers[9]);
        foodlist.add(m);

        FoodItem n = new FoodItem("Healthy", covers[9]);
        foodlist.add(n);

        adapter.notifyDataSetChanged();
    }



}
