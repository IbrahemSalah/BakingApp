package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.example.android.bakingapp.Adapters.RecycleViewAdapter;
import com.example.android.bakingapp.FetchingData.FetchRecipe;
import com.example.android.bakingapp.Modules.RecipeItem;
import com.example.android.bakingapp.Modules.Strings;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String BUNDLE_RECYCLER_LAYOUT = "bakingapp.recycler.layout";
    public static boolean connectionEnabled;
    public static ArrayList<RecipeItem> recipes;
    public static RecyclerView RVMain;
    public static RecycleViewAdapter RVAdapter;
    public LinearLayoutManager linearLayoutManager;
    public static Parcelable savedRecyclerLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipes = new ArrayList<RecipeItem>();
        linearLayoutManager = new LinearLayoutManager(this);
        RVMain = (RecyclerView) findViewById(R.id.RVMain);
        RVMain.setHasFixedSize(true);
        RVMain.setLayoutManager(linearLayoutManager);
        RVAdapter = new RecycleViewAdapter(this, recipes);
        RVMain.setAdapter(RVAdapter);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        if (width > 0 && width < 1081) {
            RVMain.setLayoutManager(new GridLayoutManager(this, 1));
        } else if (width > 1080 && width < 1920) {
            RVMain.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            RVMain.setLayoutManager(new GridLayoutManager(this, 3));
        }

        RVAdapter.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getBaseContext(), IngredientRecipeActivity.class);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });

        if(savedInstanceState != null)
        {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            RVMain.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

        if (isNetworkAvailable()) {
            connectionEnabled = true;
        } else {
            connectionEnabled = false;
        }

        if (connectionEnabled) {
            FetchRecipe downloadTask = new FetchRecipe(getApplicationContext());
            downloadTask.execute(Strings.URL);
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, RVMain.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            RVMain.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }
}
