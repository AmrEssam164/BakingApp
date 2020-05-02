package com.amressam.bakingapp.fragments;

import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.amressam.bakingapp.IngredientsStepsActivity;
import com.amressam.bakingapp.MainActivity;
import com.amressam.bakingapp.R;
import com.amressam.bakingapp.classes.Steps;
import com.amressam.bakingapp.databinding.StepDetailsFragmentBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.Serializable;
import java.util.ArrayList;


public class StepDetailsFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = "StepDetailsFragment";
    private ArrayList<Steps> mStepsArrayList;
    private int stepIndex;
    StepDetailsFragmentBinding mStepDetailsFragmentBinding;
    private SimpleExoPlayer mExoPlayer;
    private boolean isVideoReady = true;
    private long videoLastPosition = 0;
    public static final String VIDEO_READY = "video_ready";
    public static final String VIDEO_POSITION = "video_position";
    MediaSession mMediaSession;
    PlaybackState.Builder mPlaybackState;

    public StepDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStepDetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.step_details_fragment, container, false);
        View view = mStepDetailsFragmentBinding.getRoot();

        if (savedInstanceState != null) {
            mStepsArrayList = (ArrayList<Steps>) savedInstanceState.getSerializable(MainActivity.STEPS_STATE);
            stepIndex = savedInstanceState.getInt(IngredientsStepsActivity.STEP_POSITION);
            videoLastPosition = savedInstanceState.getLong(VIDEO_POSITION);
            isVideoReady = savedInstanceState.getBoolean(VIDEO_READY);
        }

        if (IngredientsStepsActivity.mTwoPane == true) {
            if (view.findViewById(R.id.land_layout) == null) {
                mStepDetailsFragmentBinding.previous.setVisibility(View.GONE);
                mStepDetailsFragmentBinding.next.setVisibility(View.GONE);
            }
        } else {
            if (view.findViewById(R.id.land_layout) == null) {
                if (stepIndex == 0) {
                    mStepDetailsFragmentBinding.previous.setVisibility(View.GONE);
                } else if (stepIndex == mStepsArrayList.size() - 1) {
                    mStepDetailsFragmentBinding.next.setVisibility(View.GONE);
                }
            }
        }

        if (!mStepsArrayList.get(stepIndex).getVideoURL().equals("")) {
            mStepDetailsFragmentBinding.stepVideo.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(mStepsArrayList.get(stepIndex).getVideoURL()));
        }

        mStepDetailsFragmentBinding.stepDesc.setText(mStepsArrayList.get(stepIndex).getDescription());

        if (view.findViewById(R.id.land_layout) == null) {
            mStepDetailsFragmentBinding.next.setOnClickListener(mOnClickNext);
            mStepDetailsFragmentBinding.previous.setOnClickListener(mOnClickPrevious);
        }


        return view;
    }

    View.OnClickListener mOnClickNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stepIndex++;
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStepsArrayList(mStepsArrayList);
            stepDetailsFragment.setStepIndex(stepIndex);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }
    };

    View.OnClickListener mOnClickPrevious = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stepIndex--;
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStepsArrayList(mStepsArrayList);
            stepDetailsFragment.setStepIndex(stepIndex);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(MainActivity.STEPS_STATE, (Serializable) mStepsArrayList);
        outState.putInt(IngredientsStepsActivity.STEP_POSITION, stepIndex);
        if (mExoPlayer != null) {
            videoLastPosition = mExoPlayer.getCurrentPosition();
            outState.putLong(VIDEO_POSITION, videoLastPosition);
            outState.putBoolean(VIDEO_READY, isVideoReady);
        }
    }

    public void setStepsArrayList(ArrayList<Steps> stepsArrayList) {
        mStepsArrayList = stepsArrayList;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }


    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mStepDetailsFragmentBinding.stepVideo.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(
                            getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isVideoReady);
            mExoPlayer.seekTo(videoLastPosition);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSession(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSession.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mPlaybackState = new PlaybackState.Builder()
                .setActions(
                        PlaybackState.ACTION_PLAY |
                                PlaybackState.ACTION_PAUSE |
                                PlaybackState.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackState.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mPlaybackState.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class MySessionCallback extends MediaSession.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }


    private void resumePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(isVideoReady);
        }
    }

    private void pausePlayer() {
        if (mExoPlayer != null) {
            videoLastPosition = mExoPlayer.getCurrentPosition();
            isVideoReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resumePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mPlaybackState.setState(PlaybackState.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mPlaybackState.setState(PlaybackState.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mPlaybackState.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
