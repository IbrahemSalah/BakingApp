package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
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

/**
 * Created by Eng_I on 10/17/2017.
 */

public class ExoPlayerFragment extends android.app.Fragment {
    public static final String MY_PREFS = "VideoProgress";
    public static final String LIFECYCLE_CALLBACKS_VIDEO_POSITION = "video_position";
    public static final String LIFECYCLE_CALLBACKS_STEP_NUMBER = "step_number";
    public static int previousVideoPosition = 0;
    public static int previousStepNumber = 0;
    static Context context;
    static SimpleExoPlayerView simpleExoPlayerView;
    static SimpleExoPlayer simpleExoPlayer;
    static Uri recipeStepVideoUri;
    static ImageView videoNotFoundImageView;
    static DefaultHttpDataSourceFactory defaultHttpDataSourceFactory;
    static ExtractorsFactory extractorsFactory;
    static MediaSource mediaSource;
    public static boolean fullScreenMode;
    public View mDecorView;
    View rootView;
    Display display;
    int recipePosition;
    int stepPosition;
    ConstraintLayout videoCardView;

    public ExoPlayerFragment() {

    }

    public static void startTheExoPlayerVideo(int recipePosition, int position) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.exo_player_fragment, container, false);
        context = getContext();
        fullScreenMode = false;

        if (savedInstanceState == null) {
            stepPosition = getArguments().getInt("stepPosition");
            previousVideoPosition = 0;
        }else{
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

        recipePosition = getArguments().getInt("recipePosition");
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exoPlayerView);
        videoNotFoundImageView = (ImageView) rootView.findViewById(R.id.videoNotFoundImageView);
        videoCardView = (ConstraintLayout) rootView.findViewById(R.id.videoCardView);
        try {
            startTheExoPlayerVideo(recipePosition, stepPosition);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error in Exo Player", Toast.LENGTH_SHORT).show();
            Log.i("Exception ", e.toString());
            e.printStackTrace();
        }

        return rootView;
    }

    // This snippet hides the system bars.
    public void hideSystemUI(View view) {
        //simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        view.setSystemUiVisibility(
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
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        videoControlsState(false);
    }

    public void videoControlsState(boolean state) {
        if (state) {
            int high = display.getHeight();
            int width = display.getWidth();
            videoCardView = (ConstraintLayout) rootView.findViewById(R.id.videoCardView);
            View view = (View) rootView.findViewById(R.id.videoCardView);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = high;
            params.width = width;
            view.setLayoutParams(params);

        } else {
            int high = 700;
            int width = display.getWidth();
            //videoCardView = (ConstraintLayout) rootView.findViewById(R.id.videoCardView);
            View view = (View) rootView.findViewById(R.id.videoCardView);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = high;
            params.width = width;
            view.setLayoutParams(params);
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

    public void initializeData(View mDecorView, Display display) {
        this.mDecorView = mDecorView;
        this.display = display;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int stepCurrentNumber = 0;
        stepCurrentNumber = stepPosition;
        int videoPlayerCurrentPosition = 0;
        videoPlayerCurrentPosition = (int) simpleExoPlayer.getCurrentPosition();
        outState.putInt(LIFECYCLE_CALLBACKS_VIDEO_POSITION, videoPlayerCurrentPosition);
        outState.putInt(LIFECYCLE_CALLBACKS_STEP_NUMBER, stepCurrentNumber);
    }


    @Override
    public void onStop() {
        super.onStop();
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
    }


}
