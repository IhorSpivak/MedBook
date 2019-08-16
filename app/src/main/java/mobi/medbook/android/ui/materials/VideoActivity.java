package mobi.medbook.android.ui.materials;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Locale;

import mobi.medbook.android.R;
import mobi.medbook.android.utils.LogUtils;


public class VideoActivity extends AppCompatActivity {

    public static final String ARGS_POINTS_VIEW = "args_points_view";
    public static final String BUNDLE_EXTRAS = "bundle_extras";
    public static final String ARGS_POLL_ID = "args_poll_id";
    public static final String ARGS_VIDEO = "args_video";


    private static final String TAG = "AppMedbook/VideoActivity";
    private SimpleExoPlayer player;
    private SimpleExoPlayerView videoPlayerView;
    private String videoUrl;
    private TextView watchedPercent;
    private long startTime = C.TIME_UNSET;
    private long maxWatchedTimeBeforeSearch = startTime;
    private boolean autoplay = true;
    private String pollId;
    private boolean materialWatched;
    private long materialWatchedTime = 0;
    private long materialWatchedTimer = 0;
    private long duration = 0;
    private int pointsView = 0;
    private Handler threadHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.material_test_video_activity);

        Bundle args = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        if(args != null) {
            videoUrl = args.getString(ARGS_VIDEO);
            pollId = args.getString(ARGS_POLL_ID);
            pointsView = args.getInt(ARGS_POINTS_VIEW, 0);
        }
        if(getIntent() != null){
            videoUrl = getIntent().getStringExtra(ARGS_VIDEO);
        }

        watchedPercent = (TextView) findViewById(R.id.watched_percent);
        videoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        ImageButton btnClose = (ImageButton) findViewById(R.id.btn_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //TODO
        //materialWatchedTime = SharedPreferencesManager.getMaterialWatchedTime(pollId);
        materialWatchedTimer = materialWatchedTime;
        //materialWatched = SharedPreferencesManager.isMaterialWatched(pollId);

        if(!materialWatched && materialWatchedTime != 0){
            startTime = materialWatchedTime;
        }

        LogUtils.logI(TAG, String.format("[Video] poll: %s, url: %s, watched to %d, is watched: %s", pollId, videoUrl, materialWatchedTime, String.valueOf(materialWatched)));

        if (videoUrl != null) {
            initPlayer();
        }
    }

    private void initPlayer() {
        if (player != null) {
            return;
        }


        // 1. Create a default TrackSelector
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Medbook")/*, (TransferListener<? super DataSource>) bandwidthMeter*/);

        // 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, new DefaultLoadControl());
        //Set media controller
        videoPlayerView.setUseController(true);
        videoPlayerView.requestFocus();

        // Bind the player to the view.
        videoPlayerView.setPlayer(player);


        final MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), dataSourceFactory, new DefaultExtractorsFactory(), null, null);

        // Prepare the player with the source.
        player.prepare(mediaSource);

        player.addListener(new Player.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {
                LogUtils.logW(TAG, "Listener-onLoadingChanged...");
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                duration = player.getDuration() > 0 ? player.getDuration() : duration;
                LogUtils.logW(TAG, String.format("Listener-onPlayerStateChanged... Duration: %d", duration));
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest) {
//                Log.v(TAG, "Listener-onTimelineChanged...");
//            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                LogUtils.logW(TAG, "Listener-onPlayerError...");
                player.stop();
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                LogUtils.logW(TAG, "Listener-onPlaybackParametersChanged...");
            }

            @Override
            public void onSeekProcessed() {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                LogUtils.logW(TAG, "Listener-onTracksChanged...");
            }
        });

        player.setPlayWhenReady(true);

        //if (!materialWatched) {
        // Disable seek if material not watched (at least MATERIAL_WATCHED_BASE % of time)
        TimeBar timeBar = (TimeBar) findViewById(R.id.exo_progress);
        timeBar.addListener(new TimeBar.OnScrubListener() {

            @Override
            public void onScrubStart(TimeBar timeBar, long position) {
                maxWatchedTimeBeforeSearch = Math.max(position, maxWatchedTimeBeforeSearch);
            }

            @Override
            public void onScrubMove(TimeBar timeBar, long position) {
                if (player != null) {
                    if (position <= Math.max(maxWatchedTimeBeforeSearch, materialWatchedTime) || pointsView == 0) {
                        //player.seekTo(position);
                    }
                    else {
                        player.seekTo(Math.max(maxWatchedTimeBeforeSearch, materialWatchedTime));
                    }
                }
            }

            @Override
            public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
                if (!canceled && player != null) {
                    if (position <= Math.max(maxWatchedTimeBeforeSearch, materialWatchedTime) || pointsView == 0) {
                        //player.seekTo(position);
                    }
                    else {
                        player.seekTo(Math.max(maxWatchedTimeBeforeSearch, materialWatchedTime));
                    }
                }
            }
        });
        //}
    }

    @Override
    protected void onResume() {
        super.onResume();

        initPlayer();

        if (player != null) {
            player.seekTo(startTime);
            player.setPlayWhenReady(autoplay);
        }

        threadHandler.post(updateWatchedPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();

        threadHandler.removeCallbacks(updateWatchedPosition);

        if (player != null) {
            autoplay = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
            startTime = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition()) : C.TIME_UNSET;

            saveWatchedState();
        }
    }

    @Override
    protected void onStop() {
        if (player != null) {
            player.release();
            player = null;
        }

        super.onStop();
    }

    private Runnable updateWatchedPosition = new Runnable() {
        @Override
        public void run() {
            if (player != null && duration != 0) {
                long curPos = player.getCurrentPosition();
                materialWatchedTimer = curPos >= materialWatchedTimer ? curPos : materialWatchedTimer > duration ? duration : materialWatchedTimer;
                int watchedPercentValue = duration != 0 ? (int) (100 * (float) materialWatchedTimer / (float) duration) : 0;

                watchedPercent.setText(String.format(Locale.getDefault(), "(%d%%)", watchedPercentValue));
            }

            threadHandler.postDelayed(updateWatchedPosition, 500);
        }
    };

    private void saveWatchedState() {
        long time = Math.max(player.getCurrentPosition(), maxWatchedTimeBeforeSearch);

        if (player == null || time < materialWatchedTime) {
            return;
        }

        materialWatchedTime = time;
        int watchedPercentValue = duration != 0 ? (int) (100 * (float) materialWatchedTime / (float) duration) : 0;

        //TODO
        //SharedPreferencesManager.setMaterialWatchedTime(pollId, materialWatchedTime);

        //if (watchedPercentValue >= Constants.MATERIAL_WATCHED_BASE) {
        //    materialWatched = MaterialStatus.getInstance().setWatched(pollId);
        //}

        LogUtils.logI(TAG, String.format("saveWatchedState: video watched %d of %d (%d%%), watched: %s",
                materialWatchedTime, player.getDuration(), watchedPercentValue, String.valueOf(materialWatched)));
    }
}