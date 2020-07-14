package com.witchey.tyler.videostore;

import java.util.Date;
import java.util.UUID;

public class Video {

    private UUID vID;
    private String vTitle;
    private String vDesc;
    private Date vDateReleased;
    private Date vDateAdded;
    private Boolean vWatched;
    private Boolean vLike;
    private Boolean vDislike;
    private float vRating;

    public Video(){
        vID = UUID.randomUUID();
        vDateAdded = new Date();
        vDateReleased = new Date();
        vWatched = false;
        vLike = false;
        vDislike = false;
        vRating = 0;
    }

    public String getvDesc() {
        return vDesc;
    }

    public void setvDesc(String vDesc) {
        this.vDesc = vDesc;
    }

    public Date getvDateAdded() {
        return vDateAdded;
    }

    public void setvDateAdded(Date vDateAdded) {
        this.vDateAdded = vDateAdded;
    }

    public Boolean getvDislike() {
        return vDislike;
    }

    public void setvDislike(Boolean vDislike) {
        this.vDislike = vDislike;
    }

    public Boolean getvLike() {
        return vLike;
    }

    public void setvLike(Boolean vLike) {
        this.vLike = vLike;
    }

    public float getvRating() {
        return vRating;
    }

    public void setvRating(float vRating) {
        this.vRating = vRating;
    }

    public UUID getvID() {
        return vID;
    }

    public String getvTitle() {
        return vTitle;
    }

    public void setvTitle(String vTitle) {
        this.vTitle = vTitle;
    }

    public Date getvDateReleased() {
        return vDateReleased;
    }

    public void setvDateReleased(Date vDateReleased) {
        this.vDateReleased = vDateReleased;
    }

    public Boolean getvWatched() {
        return vWatched;
    }

    public void setvWatched(Boolean vWatched) {
        this.vWatched = vWatched;
    }
}
