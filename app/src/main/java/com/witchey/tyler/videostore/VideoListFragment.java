package com.witchey.tyler.videostore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class VideoListFragment extends Fragment {

    private RecyclerView vVideoRecyclerView;
    private VideoAdapter vAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);

        vVideoRecyclerView = (RecyclerView) view.findViewById(R.id.video_recycler_view);
        vVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_video_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.new_video:
                Video video = new Video();
                VideoLab.get(getActivity()).addVideo(video);
                Intent intent = VideoPagerActivity
                        .newIntent(getActivity(), video.getvID());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        VideoLab videoLab = VideoLab.get(getActivity());
        List<Video> videos = videoLab.getVideos();

        if (vAdapter == null)
        {
            vAdapter = new VideoAdapter(videos);
            vVideoRecyclerView.setAdapter(vAdapter);
        }
        else
        {
            vAdapter.notifyDataSetChanged();
        }
    }

    private class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView vTitleTextView;
        private TextView vDateTextView;
        private Video vVideo;
        private ImageView vWatchedImageView;
        private ImageView vLikedImageView;
        private ImageView vDislikedImageView;
        private RatingBar vVideoRatingBar;

        public VideoHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_video, parent, false));
            itemView.setOnClickListener(this);
            vTitleTextView = (TextView) itemView.findViewById(R.id.video_title);
            vDateTextView = (TextView) itemView.findViewById(R.id.video_date);
            vWatchedImageView = (ImageView) itemView.findViewById(R.id.video_watched);
            vLikedImageView = (ImageView) itemView.findViewById(R.id.video_liked);
            vDislikedImageView = (ImageView) itemView.findViewById(R.id.video_disliked);
            vVideoRatingBar = (RatingBar) itemView.findViewById(R.id.video_rating);
        }

        public void bind(Video video){
            vVideo = video;
            vTitleTextView.setText(vVideo.getvTitle());
                //format date
                String addList = getResources().getString(R.string.video_added_to_list);
                addList += " " + new SimpleDateFormat("MMMM dd, yyyy").format(vVideo.getvDateAdded());
            vDateTextView.setText(addList);
            vWatchedImageView.setVisibility(video.getvWatched() ? View.VISIBLE : View.GONE);
            vLikedImageView.setVisibility(video.getvLike() ? View.VISIBLE : View.GONE);
            vDislikedImageView.setVisibility(video.getvDislike() ? View.VISIBLE : View.GONE);
            vVideoRatingBar.setRating(video.getvRating());
        }

        @Override
        public void onClick(View view){
            Intent intent = VideoPagerActivity.newIntent(getActivity(), vVideo.getvID());
            startActivity(intent);
        }
    }

    private class VideoAdapter extends RecyclerView.Adapter<VideoHolder>{
        private List<Video> vVideos;

        public VideoAdapter(List<Video> videos){
            vVideos = videos;
        }

        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new VideoHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(VideoHolder holder, int position){
            Video video = vVideos.get(position);
            holder.bind(video);
        }
        @Override
        public int getItemCount(){
            return vVideos.size();
        }
    }
}
