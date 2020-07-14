package com.witchey.tyler.videostore;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class VideoActivity extends SingleFragmentActivity {

    private static final String EXTRA_VIDEO_ID = "com.witchey.tyler.videostore.video_id";

    public static Intent newIntent(Context packageContext, UUID videoID){
        Intent intent = new Intent(packageContext, VideoActivity.class);
        intent.putExtra(EXTRA_VIDEO_ID, videoID);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID videoID = (UUID) getIntent().getSerializableExtra(EXTRA_VIDEO_ID);
        return VideoFragment.newInstance(videoID);
    }
}
