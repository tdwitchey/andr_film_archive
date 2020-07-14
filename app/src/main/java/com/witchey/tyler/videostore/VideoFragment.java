package com.witchey.tyler.videostore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class VideoFragment extends Fragment {

    private static final String ARG_VIDEO_ID = "video_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Video vVideo;
    private EditText vTitleField;
    private EditText vDescField;
    private Button vDateButton;
    private CheckBox vVideoWatched;
    private CheckBox vVideoLiked;
    private CheckBox vVideoDisliked;
    private RatingBar vVideoRating;

    public static VideoFragment newInstance(UUID videoID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_VIDEO_ID, videoID);

        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        UUID videoID = (UUID) getArguments().getSerializable(ARG_VIDEO_ID);
        vVideo = VideoLab.get(getActivity()).getVideo(videoID);
    }

    @Override
    public void onResume(){
        super.onResume();

        if(vVideo.getvLike() == true)
        {
            vVideoDisliked.setVisibility(false ? View.VISIBLE : View.GONE);
        }
        else if(vVideo.getvDislike() == true)
        {
            vVideoLiked.setVisibility(false ? View.VISIBLE : View.GONE);
        }
        else if(vVideo.getvWatched() == false)
        {
            vVideo.setvLike(false);
            vVideo.setvDislike(false);
            vVideo.setvRating(0);
            vVideoRating.setRating(0);
            vVideoLiked.setChecked(false);
            vVideoDisliked.setChecked(false);
            vVideoLiked.setVisibility(false ? View.VISIBLE : View.GONE);
            vVideoDisliked.setVisibility(false ? View.VISIBLE : View.GONE);
            vVideoRating.setVisibility(false ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_video, container, false);

        vTitleField = (EditText) v.findViewById(R.id.video_title);
        vTitleField.setText(vVideo.getvTitle());
        vTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vVideo.setvTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }
        });

        vDescField = (EditText) v.findViewById(R.id.video_description);
        vDescField.setText(vVideo.getvDesc());
        vDescField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vVideo.setvDesc(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally blank
            }
        });

        vDateButton = (Button) v.findViewById(R.id.video_date);
        updateReleaseDate();
        vDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(vVideo.getvDateReleased());
                dialog.setTargetFragment(VideoFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        vVideoWatched = (CheckBox) v.findViewById(R.id.video_watched);
        vVideoWatched.setChecked(vVideo.getvWatched());
        vVideoWatched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                vVideo.setvWatched(isChecked);
                vVideoLiked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                vVideoDisliked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                vVideoRating.setVisibility(isChecked ? View.VISIBLE : View.GONE);

                if(vVideo.getvWatched() == false)
                {
                    vVideo.setvLike(false);
                    vVideo.setvDislike(false);
                    vVideo.setvRating(0);
                    vVideoRating.setRating(0);
                    vVideoLiked.setChecked(false);
                    vVideoDisliked.setChecked(false);
                    vVideoLiked.setVisibility(false ? View.VISIBLE : View.GONE);
                    vVideoDisliked.setVisibility(false ? View.VISIBLE : View.GONE);
                    //vVideoRating.setVisibility(false ? View.VISIBLE : View.GONE);
                }
            }
        });

        vVideoLiked = (CheckBox) v.findViewById(R.id.video_liked);
        vVideoLiked.setChecked(vVideo.getvLike());
        vVideoLiked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vVideo.setvLike(isChecked);
                vVideoDisliked.setClickable(! isChecked);
                vVideoDisliked.setVisibility(! isChecked ? View.VISIBLE : View.GONE);
            }
        });

        vVideoDisliked = (CheckBox) v.findViewById(R.id.video_disliked);
        vVideoDisliked.setChecked(vVideo.getvDislike());
        vVideoDisliked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vVideo.setvDislike(isChecked);
                vVideoLiked.setClickable(! isChecked);
                vVideoLiked.setVisibility(! isChecked ? View.VISIBLE : View.GONE);
            }
        });

        vVideoRating = (RatingBar) v.findViewById(R.id.video_rating);
        vVideoRating.setRating(vVideo.getvRating());
        vVideoRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vVideo.setvRating(rating);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }

        if(requestCode == REQUEST_DATE)
        {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            vVideo.setvDateReleased(date);
            updateReleaseDate();
        }
    }

    private void updateReleaseDate()
    {
        String releaseYear = new SimpleDateFormat("yyyy").format(vVideo.getvDateReleased());
        vDateButton.setText("Year Released: " + releaseYear);
    }

}
