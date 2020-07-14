package com.witchey.tyler.videostore;

import androidx.fragment.app.Fragment;

public class VideoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new VideoListFragment();
    }
}
