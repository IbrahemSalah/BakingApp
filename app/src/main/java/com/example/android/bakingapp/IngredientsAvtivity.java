package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.IngredientRecycleViewAdapter;
import com.example.android.bakingapp.BakingWidget.BakingWidget;

import static com.example.android.bakingapp.BakingWidget.BakingWidget.updateAppWidget;

public class IngredientsAvtivity extends AppCompatActivity {

    int position;
    public static RecyclerView IngredientRV;
    public static IngredientRecycleViewAdapter IngredientRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_avtivity);

        position = getIntent().getIntExtra("Position", 0);

        IngredientRV = (RecyclerView) findViewById(R.id.ingredientRV);
        IngredientRV.setLayoutManager(new LinearLayoutManager(this));
        IngredientRVAdapter = new IngredientRecycleViewAdapter(this, MainActivity.recipes.get(position).getIngredientItems());
        IngredientRV.setAdapter(IngredientRVAdapter);
        IngredientRVAdapter.setItemClickListner(new IngredientClickListner() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(IngredientsAvtivity.this, "Position  :" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void addtoWidget(View view) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplicationContext(), BakingWidget.class));


        updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetIds, position);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredientsWidgetListView);
    }
}
