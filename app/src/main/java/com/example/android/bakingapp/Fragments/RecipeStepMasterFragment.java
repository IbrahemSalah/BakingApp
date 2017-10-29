package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.StepsRecyclerViewAdapter;
import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepActivity;
import com.example.android.bakingapp.StepsItemClickListner;

/**
 * Created by Eng_I on 10/17/2017.
 */

public class RecipeStepMasterFragment extends android.app.Fragment {

    public RecyclerView StepsRV;
    public StepsRecyclerViewAdapter StepsRVAdapter;

    public RecipeStepMasterFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.master_fragment_step_list, container, false);

        final int recipePosition = getArguments().getInt("recipePosition");
        final int stepPosition = getArguments().getInt("stepPosition");
        final boolean TabletScreen = getArguments().getBoolean("TabletScreen");


        StepsRV = (RecyclerView) rootView.findViewById(R.id.StepsRV);
        StepsRV.setHasFixedSize(true);
        StepsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        StepsRVAdapter = new StepsRecyclerViewAdapter(getContext(), MainActivity.recipes.get(recipePosition).getStepItem());
        StepsRV.setAdapter(StepsRVAdapter);

        StepsRVAdapter.setItemClickListner(new StepsItemClickListner() {
            @Override
            public void onItemClick(int position) {
                if(TabletScreen){
                    ExoPlayerFragment.previousVideoPosition =0;
                    ExoPlayerFragment.startTheExoPlayerVideo(recipePosition, position);
                    StepDescriptionFragment.setStepDescription(recipePosition, position);
                }else {
                    Intent intent = new Intent(getContext(), StepActivity.class);
                    intent.putExtra("recipePosition", recipePosition);
                    intent.putExtra("stepPosition", position);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }
}
