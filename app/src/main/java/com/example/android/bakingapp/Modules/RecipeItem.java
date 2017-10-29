package com.example.android.bakingapp.Modules;

import java.util.ArrayList;

/**
 * Created by Eng_I on 10/2/2017.
 */

public class RecipeItem {

    int recipeId;
    String recipeName;
    String recipeImageURL;
    ArrayList<IngredientItem> ingredientItems;
    ArrayList<StepItem> stepItems;

    public RecipeItem() {
        recipeId = 0;
        recipeName = "";
        ingredientItems = new ArrayList<>();
        stepItems = new ArrayList<>();
    }

    public void setStepItems(ArrayList<StepItem> stepItems) {
        this.stepItems = stepItems;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeImageURL(String recipeImageURL) {
        this.recipeImageURL = recipeImageURL;
    }

    public String getRecipeImageURL() {
        return recipeImageURL;
    }

    public ArrayList<IngredientItem> getIngredientItems() {
        return ingredientItems;
    }

    public void setIngredientItems(ArrayList<IngredientItem> ingredientItems) {
        this.ingredientItems = ingredientItems;
    }

    public ArrayList<StepItem> getStepItem() {
        return stepItems;
    }
}
