package com.example.android.bakingapp.Modules;

/**
 * Created by Eng_I on 10/2/2017.
 */

public class StepItem {

    public int Id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;

    public StepItem() {
        Id = 0;
        shortDescription = "";
        description = "";
        videoURL = "";
        thumbnailURL = "";
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setshortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public void setvideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setthumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
