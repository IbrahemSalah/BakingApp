package com.example.android.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {
    public static final String LIFECYCLE_CALLBACKS_VIDEO_POSITION = "video_position";
    public static final String LIFECYCLE_CALLBACKS_STEP_NUMBER = "step_number";
    public static int previousVideoPosition = 0;
    public static int previousStepNumber = 0;
    int recipePosition;
    int stepPosition;
    @BindView(R.id.exoPlayerView) SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.nextButton) Button nextButton;
    @BindView(R.id.previousButton) Button previousButton;
    @BindView(R.id.stepDesciptionTextView) TextView stepDescription;
    @BindView(R.id.videoCardView) CardView videoCardView;
    @BindView(R.id.stepShortDesciptionTextView) TextView stepShortDescription;
    @BindView(R.id.videoNotFoundImageView) ImageView videoNotFoundImageView;
    SimpleExoPlayer simpleExoPlayer;
    Uri recipeStepVideoUri;
    DefaultHttpDataSourceFactory defaultHttpDataSourceFactory;
    ExtractorsFactory extractorsFactory;
    MediaSource mediaSource;
    boolean fullScreenMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        fullScreenMode = false;
        recipePosition = getIntent().getIntExtra("recipePosition", 0);
        stepPosition = previousStepNumber;

        if (savedInstanceState == null) {
            stepPosition = getIntent().getIntExtra("stepPosition", 0);
        }
        else{
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_VIDEO_POSITION)) {
                int previousVideo = savedInstanceState.getInt(LIFECYCLE_CALLBACKS_VIDEO_POSITION);
                previousVideoPosition = previousVideo;
            }
            if(savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_STEP_NUMBER)){
                int previousStep = savedInstanceState.getInt(LIFECYCLE_CALLBACKS_STEP_NUMBER);
                previousStepNumber = previousStep;
                stepPosition = previousStepNumber;
            }
        }

        stepShortDescription.setText(MainActivity.recipes.get(recipePosition).getStepItem().get(stepPosition).shortDescription);
        stepDescription.setText(MainActivity.recipes.get(recipePosition).getStepItem().get(stepPosition).description);

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            startTheExoPlayerVideo(stepPosition);

        } catch (Exception e) {
            Toast.makeText(this, "Error in Exo Player", Toast.LENGTH_SHORT).show();
            Log.i("Exception ", e.toString());
        }

    }

    public void nextButtonClick(View view) {
        if (simpleExoPlayer != null) {

            if (stepPosition < MainActivity.recipes.get(recipePosition).getStepItem().size() - 1) {
                stepPosition++;
                stepShortDescription.setText(MainActivity.recipes.get(recipePosition).getStepItem().get(stepPosition).shortDescription);
                stepDescription.setText(MainActivity.recipes.get(recipePosition).getStepItem().get(stepPosition).description);
                startTheExoPlayerVideo(stepPosition);
            } else {
                Toast.makeText(this, "It's the Last Step, Well Done!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void previousButtonClick(View view) {
        if (simpleExoPlayer != null){
        if (stepPosition == 0) {
            Toast.makeText(this, "It's the First Step, Keep Going!", Toast.LENGTH_SHORT).show();
        } else {
            stepPosition--;
            stepShortDescription.setText(MainActivity.recipes.get(recipePosition).getStepItem().get(stepPosition).shortDescription);
            stepDescription.setText(MainActivity.recipes.get(recipePosition).getStepItem().get(stepPosition).description);
            startTheExoPlayerVideo(stepPosition);
        }
        }
    }


    public void startTheExoPlayerVideo(int position) {

            String videoURL = MainActivity.recipes.get(recipePosition).getStepItem().get(position).videoURL;
            if (videoURL == null || videoURL.equals("")) {
                videoNotFoundImageView.setVisibility(View.VISIBLE);
            } else {
                recipeStepVideoUri = Uri.parse(videoURL);
                videoNotFoundImageView.setVisibility(View.INVISIBLE);
                defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory("exoPlayerVideo");
                extractorsFactory = new DefaultExtractorsFactory();
                mediaSource = new ExtractorMediaSource(recipeStepVideoUri, defaultHttpDataSourceFactory, extractorsFactory, null, null);
                simpleExoPlayerView.setPlayer(simpleExoPlayer);
                Log.i("Position", String.valueOf(previousVideoPosition));
                if (previousVideoPosition != 0) {
                    simpleExoPlayer.seekTo(previousVideoPosition);
                }
                simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.setPlayWhenReady(true);
            }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (simpleExoPlayer != null){
            int stepCurrentNumber = 0;
            stepCurrentNumber = stepPosition;
            int videoPlayerCurrentPosition = 0;
            videoPlayerCurrentPosition = (int) simpleExoPlayer.getCurrentPosition();
            outState.putInt(LIFECYCLE_CALLBACKS_VIDEO_POSITION, videoPlayerCurrentPosition);
            outState.putInt(LIFECYCLE_CALLBACKS_STEP_NUMBER, stepCurrentNumber);
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }

    }


    public void fullScreenMode(View view) {
        if (!fullScreenMode) {
            hideSystemUI(view);
            fullScreenMode = true;
        } else {
            showSystemUI(view);
            fullScreenMode = false;
        }
    }

    // This snippet hides the system bars.
    public void hideSystemUI(View view) {
        //simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View mDecorView = getWindow().getDecorView();

        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        videoControlsState(true);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    public void showSystemUI(View view) {
        View mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        videoControlsState(false);
    }


    public void videoControlsState(boolean state) {
        if (state) {
            nextButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.GONE);
            stepShortDescription.setVisibility(View.GONE);
            stepDescription.setVisibility(View.GONE);

            Display display = getWindowManager().getDefaultDisplay();
            int high = display.getHeight();
            int width = display.getWidth();
            View view = (View) findViewById(R.id.videoCardView);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = high;
            params.width = width;
            view.setLayoutParams(params);
        } else {
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
            stepShortDescription.setVisibility(View.VISIBLE);
            stepDescription.setVisibility(View.VISIBLE);

            Display display = getWindowManager().getDefaultDisplay();
            int high = 700;
            int width = display.getWidth();
            View view = (View) findViewById(R.id.videoCardView);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = high;
            params.width = width;
            view.setLayoutParams(params);
        }
    }

}
