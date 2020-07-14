package com.witchey.tyler.videostore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class VideoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_VIDEO_ID = "com.witchey.tyler.videostore.video_id";

    private ViewPager mViewPager;
    private List<Video> mVideos;

    public static Intent newIntent(Context packageContext, UUID videoID){
        Intent intent = new Intent(packageContext, VideoPagerActivity.class);
        intent.putExtra(EXTRA_VIDEO_ID, videoID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_pager);

        UUID videoID = (UUID) getIntent()
                .getSerializableExtra(EXTRA_VIDEO_ID);

        mViewPager = (ViewPager) findViewById(R.id.video_view_pager);

        mVideos = VideoLab.get(this).getVideos();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Video video = mVideos.get(position);
                return VideoFragment.newInstance(video.getvID());
            }

            @Override
            public int getCount() {
                return mVideos.size();
            }
        });

        for(int i = 0; i < mVideos.size(); i++)
        {
            if(mVideos.get(i).getvID().equals(videoID))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
