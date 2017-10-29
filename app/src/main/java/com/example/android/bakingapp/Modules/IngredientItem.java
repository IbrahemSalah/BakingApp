package com.example.android.bakingapp.Modules;

/**
 * Created by Eng_I on 10/2/2017.
 */

public class IngredientItem {

    public int quantity;
    public String measure;
    public String ingredient;

    public IngredientItem() {
        quantity = 0;
        measure = "";
        ingredient = "";
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
