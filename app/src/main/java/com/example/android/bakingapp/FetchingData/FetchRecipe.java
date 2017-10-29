package com.example.android.bakingapp.FetchingData;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.Modules.IngredientItem;
import com.example.android.bakingapp.Modules.RecipeItem;
import com.example.android.bakingapp.Modules.StepItem;
import com.example.android.bakingapp.Modules.Strings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Eng_I on 10/3/2017.
 */

public class FetchRecipe extends AsyncTask<String, Void, String> {

    public Context currentConext;

    public FetchRecipe(Context context) {
        currentConext = context;
    }


    @Override
    protected String doInBackground(String... URLS) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(URLS[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //update UI, processing Data
        super.onPostExecute(result);

        if (result == null || result.isEmpty())
            return;

        if (MainActivity.connectionEnabled) {
            try {
                //JSONObject recipesObject = new JSONObject(result);
                JSONArray recipeArray = new JSONArray(result);
                final int numberOfRecipes = recipeArray.length();

                for (int i = 0; i < numberOfRecipes; i++) {

                    JSONObject recipe = recipeArray.getJSONObject(i);
                    RecipeItem recipeItem = new RecipeItem();
                    recipeItem.setRecipeId(recipe.getInt("id"));
                    recipeItem.setRecipeName(recipe.getString("name"));
                    recipeItem.setRecipeImageURL(recipe.getString("image"));

                    ArrayList<IngredientItem> ingredientItemArrayList = new ArrayList<IngredientItem>();
                    JSONArray ingredientsArray = recipe.getJSONArray("ingredients");
                    for (int j = 0; j < ingredientsArray.length(); j++) {

                        JSONObject ingredientsObject = ingredientsArray.getJSONObject(j);
                        IngredientItem ingredientItem = new IngredientItem();
                        ingredientItem.setQuantity(ingredientsObject.getInt("quantity"));
                        ingredientItem.setMeasure(ingredientsObject.getString("measure"));
                        ingredientItem.setIngredient(ingredientsObject.getString("ingredient"));
                        ingredientItemArrayList.add(ingredientItem);
                    }
                    recipeItem.setIngredientItems(ingredientItemArrayList);

                    ArrayList<StepItem> stepItemArrayList = new ArrayList<StepItem>();
                    JSONArray stepsArray = recipe.getJSONArray("steps");
                    for (int k = 0; k < stepsArray.length(); k++) {

                        JSONObject stepsObject = stepsArray.getJSONObject(k);
                        StepItem stepItem = new StepItem();
                        stepItem.setId(stepsObject.getInt("id"));
                        stepItem.setshortDescription(stepsObject.getString("shortDescription"));
                        stepItem.setdescription(stepsObject.getString("description"));
                        if (stepsObject.getString("videoURL") == "" || stepsObject.getString("videoURL") == null) {
                            stepItem.setvideoURL(Strings.Null);
                        } else {
                            stepItem.setvideoURL(stepsObject.getString("videoURL"));
                        }
                        if (stepsObject.getString("thumbnailURL") == "" || stepsObject.getString("thumbnailURL") == null) {
                            stepItem.setthumbnailURL("");
                        } else {
                            stepItem.setthumbnailURL(stepsObject.getString("thumbnailURL"));
                        }
                        stepItemArrayList.add(stepItem);
                    }
                    recipeItem.setStepItems(stepItemArrayList);

                    MainActivity.recipes.add(recipeItem);
                }

                Log.i("recipes : ", String.valueOf(MainActivity.recipes.size()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainActivity.RVAdapter.notifyDataSetChanged();
            if(MainActivity.savedRecyclerLayoutState!= null)
            MainActivity.RVMain.getLayoutManager().onRestoreInstanceState(MainActivity.savedRecyclerLayoutState);
        } else {
            //network is not working
            Toast.makeText(currentConext, "Network Is Not Available", Toast.LENGTH_LONG).show();
        }
    }
}
