package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Modules.StepItem;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepsItemClickListner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eng_I on 10/23/2017.
 */

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.StepsViewHolder>{

    Context context;

    public ArrayList<StepItem> stepsList;
    StepsItemClickListner stepsItemClickListner;


    public StepsRecyclerViewAdapter(Context context,  ArrayList<StepItem> stepsList) {
        this.context = context;
        this.stepsList = stepsList;
    }

    public void setItemClickListner(StepsItemClickListner stepsItemClickListner) {
        this.stepsItemClickListner = stepsItemClickListner;
    }

    @Override
    public StepsRecyclerViewAdapter.StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);

        return new StepsRecyclerViewAdapter.StepsViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(StepsRecyclerViewAdapter.StepsViewHolder holder, final int position) {
        StepItem stepItem = stepsList.get(position);
        holder.stepNumberText.setText(String.valueOf(stepItem.Id));
        holder.stepNameText.setText(stepItem.shortDescription);
        if(stepItem.thumbnailURL.equals(null) || stepItem.thumbnailURL.equals("")){
            holder.recipeImage.setVisibility(View.GONE);
        }else{
            Picasso.with(context).load(stepItem.thumbnailURL).into(holder.recipeImage);
        }
        if(position == 0){
            holder.recipeItemCardView.setTag(null);
        }else{
            holder.recipeItemCardView.setTag(R.string.app_name);
        }

        holder.recipeItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepsItemClickListner != null) {
                    stepsItemClickListner.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }


    public class StepsViewHolder extends RecyclerView.ViewHolder {

        public ImageView recipeImage;
        public TextView stepNumberText;
        public TextView stepNameText;
        public CardView recipeItemCardView;

        public StepsViewHolder(View itemView) {
            super(itemView);
            recipeImage = (ImageView) itemView.findViewById(R.id.stepImageView);
            stepNumberText = (TextView) itemView.findViewById(R.id.stepNumber);
            stepNameText = (TextView) itemView.findViewById(R.id.stepName);
            recipeItemCardView = (CardView) itemView.findViewById(R.id.recipeStepCard);
        }
    }
}
