package com.amressam.bakingapp.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Steps")
public class Steps implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String recipe_name;
    private String shortDescription;
    private String description;
    private String videoURL;

    @Ignore
    public Steps(String recipe_name, String shortDescription, String description, String videoURL) {
        this.recipe_name = recipe_name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    public Steps(int id, String recipe_name, String shortDescription, String description, String videoURL) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    public int getId() {
        return id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
