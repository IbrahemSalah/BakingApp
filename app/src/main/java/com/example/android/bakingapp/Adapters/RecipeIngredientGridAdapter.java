package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.bakingapp.Modules.IngredientItem;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Eng_I on 10/6/2017.
 */

public class RecipeIngredientGridAdapter extends BaseAdapter {
    public LayoutInflater inflater;

    public Context context;

    public ArrayList<IngredientItem> ingredintsList;


    public RecipeIngredientGridAdapter(Context context, ArrayList<IngredientItem> ingredintsList) {

        this.context = context;

        this.ingredintsList = ingredintsList;
    }

    @Override
    public int getCount() {
        return ingredintsList.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredintsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridLayout = convertView;

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridLayout = inflater.inflate(R.layout.ingredient_item, null);
        }

        TextView ingredientNumber = (TextView) gridLayout.findViewById(R.id.ingredientNumberTextView);
        TextView ingredientQuantity = (TextView) gridLayout.findViewById(R.id.ingredientQuantity);
        TextView ingredientMeasure = (TextView) gridLayout.findViewById(R.id.ingredientMeasure);
        TextView ingredient = (TextView) gridLayout.findViewById(R.id.ingredient);

        ingredientNumber.setText(String.valueOf(position + 1));
        ingredientQuantity.setText("Ingredient.. " + String.valueOf(ingredintsList.get(position).quantity));
        ingredientMeasure.setText(ingredintsList.get(position).measure);
        ingredient.setText(ingredintsList.get(position).ingredient);


        return gridLayout;
    }
}
