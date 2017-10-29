package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.IngredientClickListner;
import com.example.android.bakingapp.Modules.IngredientItem;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Eng_I on 10/25/2017.
 */

public class IngredientRecycleViewAdapter extends  RecyclerView.Adapter<IngredientRecycleViewAdapter.IngredientViewHolder>{

    Context context;
    ArrayList<IngredientItem> ingredintsList;
    IngredientClickListner ingredientClickListner;

    public IngredientRecycleViewAdapter(Context context, ArrayList<IngredientItem> ingredintsList) {
        this.context = context;
        this.ingredintsList = ingredintsList;
    }

    public void setItemClickListner(IngredientClickListner ingredientClickListner) {
        this.ingredientClickListner = ingredientClickListner;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);

        return new IngredientViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder,final int position) {
        IngredientItem ingredientItem = ingredintsList.get(position);
        holder.ingredientNumber.setText(String.valueOf(position + 1));
        holder.ingredientQuantity.setText("Ingredient.. " + String.valueOf(ingredientItem.quantity));
        holder.ingredientMeasure.setText(ingredientItem.measure);
        holder.ingredient.setText(ingredientItem.ingredient);

        if(position == 0){
            holder.ingredientCardView.setTag(null);
        }else{
            holder.ingredientCardView.setTag(R.string.app_name);
        }

        holder.ingredientCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ingredientClickListner != null) {
                    ingredientClickListner.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredintsList.size();
    }


    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientNumber ;
        TextView ingredientQuantity ;
        TextView ingredientMeasure ;
        TextView ingredient ;
        CardView ingredientCardView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientNumber = (TextView) itemView.findViewById(R.id.ingredientNumberTextView);
            ingredientQuantity = (TextView) itemView.findViewById(R.id.ingredientQuantity);
            ingredientMeasure = (TextView) itemView.findViewById(R.id.ingredientMeasure);
            ingredient = (TextView) itemView.findViewById(R.id.ingredient);
            ingredientCardView =(CardView) itemView.findViewById(R.id.recipeStepCard);
        }
    }
}
