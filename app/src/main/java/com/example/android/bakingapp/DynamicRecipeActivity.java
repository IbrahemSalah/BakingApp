package com.example.android.bakingapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.bakingapp.Fragments.ExoPlayerFragment;
import com.example.android.bakingapp.Fragments.RecipeStepMasterFragment;
import com.example.android.bakingapp.Fragments.StepDescriptionFragment;

public class DynamicRecipeActivity extends AppCompatActivity {
    public static String FULL_SCREEN_STATE= "fullScreenMode";
    public boolean fullScreenMode = false;
    ExoPlayerFragment exoPlayerFragment;
    StepDescriptionFragment stepDescriptionFragment;
    RecipeStepMasterFragment recipeStepMasterFragment;
    FragmentManager fragmentManager;
    android.app.FragmentManager fragmentManagerApp;
    boolean TabletScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_recipe);
        fragmentManager = getSupportFragmentManager();
        fragmentManagerApp = getFragmentManager();
        if (findViewById(R.id.stepVideoFragmentContainer)!= null){
            // Tablet UI Master Panel
            TabletScreen = true;
            int recipePosition = getIntent().getIntExtra("recipePosition", 0);
            int stepPosition = getIntent().getIntExtra("stepPosition", 0);

            Bundle bundle = new Bundle();
            bundle.putInt("recipePosition", recipePosition);
            bundle.putInt("stepPosition", stepPosition);
            bundle.putBoolean("TabletScreen", TabletScreen);

            if (savedInstanceState != null){
                if (savedInstanceState.containsKey(FULL_SCREEN_STATE)) {
                    fullScreenMode = savedInstanceState.getBoolean(FULL_SCREEN_STATE);
                }
            }

            if (fragmentManagerApp.findFragmentById(R.id.stepListFragmentContainer) == null) {

                recipeStepMasterFragment = new RecipeStepMasterFragment();
                recipeStepMasterFragment.setArguments(bundle);

                fragmentManager = getSupportFragmentManager();
                fragmentManagerApp.beginTransaction()
                        .add(R.id.stepListFragmentContainer, recipeStepMasterFragment)
                        .commit();


                exoPlayerFragment = new ExoPlayerFragment();
                exoPlayerFragment.setArguments(bundle);
                View mDecorView = getWindow().getDecorView();
                Display display = getWindowManager().getDefaultDisplay();
                exoPlayerFragment.initializeData(mDecorView, display);

                fragmentManagerApp.beginTransaction()
                        .add(R.id.stepVideoFragmentContainer, exoPlayerFragment)
                        .commit();


                stepDescriptionFragment = new StepDescriptionFragment();
                stepDescriptionFragment.setArguments(bundle);

                fragmentManagerApp.beginTransaction()
                        .add(R.id.stepShortDescriptionFragmentContainer, stepDescriptionFragment)
                        .commit();
            }
            else{
                recipeStepMasterFragment = (RecipeStepMasterFragment)fragmentManagerApp.findFragmentById(R.id.stepListFragmentContainer);
                exoPlayerFragment = (ExoPlayerFragment)fragmentManagerApp.findFragmentById(R.id.stepVideoFragmentContainer);
                stepDescriptionFragment = (StepDescriptionFragment)fragmentManagerApp.findFragmentById(R.id.stepShortDescriptionFragmentContainer);
            }
            if (fullScreenMode){
                ExoPlayerFragment.fullScreenMode = false;
                View view = new View(this);
                fullScreenVideo(view);
            }
        }else {
            //Phone UI
            TabletScreen = false;
            int recipePosition = getIntent().getIntExtra("recipePosition", 0);
            int stepPosition = getIntent().getIntExtra("stepPosition", 0);

            Bundle bundle = new Bundle();
            bundle.putInt("recipePosition", recipePosition);
            bundle.putBoolean("TabletScreen", TabletScreen);

            if (savedInstanceState == null) {

                recipeStepMasterFragment = new RecipeStepMasterFragment();
                recipeStepMasterFragment.setArguments(bundle);

                fragmentManager = getSupportFragmentManager();
                fragmentManagerApp.beginTransaction()
                        .add(R.id.stepListFragmentContainer, recipeStepMasterFragment)
                        .commit();
            }
        }

    }

    public void fullScreenVideo(View view){
        if (fragmentManagerApp == null){
            fragmentManagerApp = getFragmentManager();
        }
        if(exoPlayerFragment == null){
            exoPlayerFragment = new ExoPlayerFragment();
        }

        if (!ExoPlayerFragment.fullScreenMode){
            fragmentManagerApp.beginTransaction()
                    .remove(recipeStepMasterFragment)
                    .commit();
            FrameLayout listFrameLayoutContainer = (FrameLayout)findViewById(R.id.stepListFragmentContainer);
            listFrameLayoutContainer.setVisibility(View.GONE);
            fragmentManagerApp.beginTransaction()
                    .remove(stepDescriptionFragment)
                    .commit();
            FrameLayout descFrameLayoutContainer = (FrameLayout)findViewById(R.id.stepShortDescriptionFragmentContainer);
            descFrameLayoutContainer.setVisibility(View.GONE);

            Display display = getWindowManager().getDefaultDisplay();
            int high = display.getHeight();
            int width = display.getWidth();

            View videoView = findViewById(R.id.stepVideoFragmentContainer);
            ViewGroup.LayoutParams params = videoView.getLayoutParams();
            params.height = high;
            params.width = width;
            videoView.setLayoutParams(params);
        }else{
            FrameLayout listFrameLayoutContainer = (FrameLayout)findViewById(R.id.stepListFragmentContainer);
            listFrameLayoutContainer.setVisibility(View.VISIBLE);
            fragmentManagerApp.beginTransaction()
                    .add(R.id.stepListFragmentContainer, recipeStepMasterFragment)
                    .commit();

            FrameLayout descFrameLayoutContainer = (FrameLayout)findViewById(R.id.stepShortDescriptionFragmentContainer);
            descFrameLayoutContainer.setVisibility(View.VISIBLE);
            fragmentManagerApp.beginTransaction()
                    .add(R.id.stepShortDescriptionFragmentContainer, stepDescriptionFragment)
                    .commit();

        }
        exoPlayerFragment.initializeData(getWindow().getDecorView(),getWindowManager().getDefaultDisplay());
        exoPlayerFragment.fullScreenMode(view);
    }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(FULL_SCREEN_STATE,ExoPlayerFragment.fullScreenMode);
    }
}
