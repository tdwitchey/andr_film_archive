package com.witchey.tyler.videostore;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VideoLab {
    private static VideoLab sVideoLab;

    private List<Video> vVideos;

    public static VideoLab get(Context context){
        if(sVideoLab == null){
            sVideoLab = new VideoLab(context);
        }
        return sVideoLab;
    }

    private VideoLab(Context context){
        vVideos = new ArrayList<>();
    }

    public void addVideo(Video v){
        vVideos.add(v);
    }

    public List<Video> getVideos(){
        return vVideos;
    }

    public Video getVideo(UUID id){
        for(Video video : vVideos){
            if(video.getvID().equals(id)){
                return video;
            }
        }
        return null;
    }

}
