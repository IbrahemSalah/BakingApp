package com.example.android.bakingapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.StepsRecyclerViewAdapter;

public class IngredientRecipeActivity extends AppCompatActivity {

    public static String LIFECYCLE_CALLBACKS_STEP_NUMBER ="recipeNumberCallBack";
    public static RecyclerView StepsRV;
    public static StepsRecyclerViewAdapter StepsRVAdapter;
    int recipePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredient);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        if(savedInstanceState == null){
        recipePosition = getIntent().getIntExtra("Position", 0);
        }else{
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_STEP_NUMBER)) {
                int previousStepNumber = savedInstanceState.getInt(LIFECYCLE_CALLBACKS_STEP_NUMBER);
                recipePosition = previousStepNumber;
            }
        }

        StepsRV = (RecyclerView) findViewById(R.id.StepsRV);
        StepsRV.setHasFixedSize(true);
        StepsRV.setLayoutManager(new LinearLayoutManager(this));
        StepsRVAdapter = new StepsRecyclerViewAdapter(this, MainActivity.recipes.get(recipePosition).getStepItem());
        StepsRV.setAdapter(StepsRVAdapter);

        StepsRVAdapter.setItemClickListner(new StepsItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getBaseContext(), DynamicRecipeActivity.class);
                intent.putExtra("recipePosition", recipePosition);
                intent.putExtra("stepPosition", position);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ingredientView(View view) {
        Intent intent = new Intent(getBaseContext(), IngredientsAvtivity.class);
        intent.putExtra("Position", recipePosition);
        startActivity(intent);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try{
            int stepCurrentNumber = 0;
            stepCurrentNumber = recipePosition;
            outState.putInt(LIFECYCLE_CALLBACKS_STEP_NUMBER, stepCurrentNumber);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error In Player", Toast.LENGTH_SHORT).show();
        }
    }
}
