package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;

/**
 * Created by Eng_I on 10/17/2017.
 */

public class StepDescriptionFragment extends android.app.Fragment {
    static TextView recipeDescriptionTextView;

    public StepDescriptionFragment() {

    }

    public static void setStepDescription(int recipePosition, int stepPosition) {
        recipeDescriptionTextView.setText(MainActivity.recipes.get(recipePosition).getStepItem().get(stepPosition).description);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_step_description_fragment, container, false);

        recipeDescriptionTextView = (TextView) rootView.findViewById(R.id.recipeStepDescription);
        int recipePosition = getArguments().getInt("recipePosition");
        int stepPosition = getArguments().getInt("stepPosition");

        recipeDescriptionTextView.setText(MainActivity.recipes.get(recipePosition).getStepItem().get(stepPosition).description);

        return rootView;
    }
}
