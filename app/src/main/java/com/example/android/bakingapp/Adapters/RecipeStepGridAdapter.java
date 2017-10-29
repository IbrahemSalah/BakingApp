package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Modules.StepItem;
import com.example.android.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eng_I on 10/6/2017.
 */

public class RecipeStepGridAdapter extends BaseAdapter {

    public LayoutInflater inflater;

    public Context context;

    public ArrayList<StepItem> stepsList;


    public RecipeStepGridAdapter(Context context, ArrayList<StepItem> stepsList) {

        this.context = context;

        this.stepsList = stepsList;
    }

    @Override
    public int getCount() {
        return stepsList.size();
    }

    @Override
    public Object getItem(int position) {
        return stepsList.get(position);
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
            gridLayout = inflater.inflate(R.layout.step_item, null);
        }

        TextView recipeNumber = (TextView) gridLayout.findViewById(R.id.stepNumber);
        TextView recipeName = (TextView) gridLayout.findViewById(R.id.stepName);
        ImageView recipeImage = (ImageView) gridLayout.findViewById(R.id.stepImageView);

        recipeNumber.setText("Step.. " + String.valueOf((stepsList.get(position).Id) + 1));
        recipeName.setText(stepsList.get(position).shortDescription);
        if (stepsList.get(position).thumbnailURL != null ||stepsList.get(position).thumbnailURL != ""){
            recipeImage.setVisibility(View.GONE);
        }else{
            Picasso.with(context).load(stepsList.get(position).thumbnailURL).into(recipeImage);
        }


        return gridLayout;
    }
}
