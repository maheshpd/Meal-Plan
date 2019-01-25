package com.arfeenkhan.mealplan;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arfeenkhan.mealplan.Adapter.DrawerItemCustomAdapter;
import com.arfeenkhan.mealplan.Adapter.FoodAdapter;
import com.arfeenkhan.mealplan.Model.DataModel;
import com.arfeenkhan.mealplan.Model.FoodItem;
import com.arfeenkhan.mealplan.fragment.Home;

import java.util.List;

import static android.support.v4.view.GravityCompat.START;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private List<FoodItem> foodlist;
    private FoodAdapter adapter;

    //Toolbar
    Toolbar mtoolbar;

    DrawerLayout drawer;

    private String[] mNavigationDrawerItemtitles;
    private ListView mDrawerList;
    private CharSequence mDrawertitles;
    private CharSequence mTitle;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Toolbar
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custome_toolbar, null);
        actionBar.setCustomView(actionBarView);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
        }


        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, mtoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(START);
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.nav_menu);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTitle = mDrawertitles = getTitle();
        mNavigationDrawerItemtitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerList = findViewById(R.id.left_drawer);

        DataModel[] drawerItem = new DataModel[3];

        drawerItem[0] = new DataModel(R.drawable.search, "Search");
        drawerItem[1] = new DataModel(R.drawable.basket, "Shopping List");
        drawerItem[2] = new DataModel(R.drawable.promo, "Weekly Planning");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawer.setDrawerListener(toggle);
//        setupDrawerToggle();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        switch (position) {
            case 0:
                Toast.makeText(this, "Search Click", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "Shopping List", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "Weekly Planning", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

//        if (fragment != null) {
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
//
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
//            setTitle(mNavigationDrawerItemtitles[position]);
//            drawer.closeDrawer(mDrawerList);
//        } else {
//            Log.e("MainActivity", "Error in creating fragment ");
//        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


}
