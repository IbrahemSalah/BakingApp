package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.ItemClickListner;
import com.example.android.bakingapp.Modules.RecipeItem;
import com.example.android.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eng_I on 10/2/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<RecipeItem> recipeItems;
    ItemClickListner itemClickListner;

    public RecycleViewAdapter(Context context, ArrayList<RecipeItem> recipeItems) {
        this.context = context;
        this.recipeItems = recipeItems;
    }

    public void setItemClickListner(ItemClickListner ItemClickListner) {
        this.itemClickListner = ItemClickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);

        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //Toast.makeText(context, "Fired", Toast.LENGTH_SHORT).show();
        RecipeItem recipeItem = recipeItems.get(position);
        holder.recipeText.setText(recipeItem.getRecipeName());
        //Log.i("image",recipeItems.get(position).getRecipeImageURL());
        if(recipeItems.get(position).getRecipeImageURL().equals(null) || recipeItems.get(position).getRecipeImageURL().equals("")){
            holder.recipeImage.setImageResource(R.drawable.recipeimage);
        }else{
            Picasso.with(context).load(recipeItems.get(position).getRecipeImageURL()).into(holder.recipeImage);
        }
        if(position == 0){
            holder.recipeItemCardView.setTag(null);
        }else{
            holder.recipeItemCardView.setTag(R.string.app_name);
        }

        holder.recipeItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListner != null) {
                    itemClickListner.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeItems.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView recipeImage;
        public TextView recipeText;
        public CardView recipeItemCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipeImageView);
            recipeText = (TextView) itemView.findViewById(R.id.recipeTextView);
            recipeItemCardView = (CardView) itemView.findViewById(R.id.recipeStepCard);
        }
    }
}
