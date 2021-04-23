package com.example.survey.ui.home;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.PropertyName;
import java.io.Serializable;

public class SurveyModel implements Serializable {
    public String id;
    public String comments;
    public String Address;
    public long securityRating;
    public GeoPoint location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @PropertyName("Address")
    public String getAddress() {
        return Address;
    }

    @PropertyName("Address")
    public void setAddress(String address) {
        this.Address = address;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public long getSecurityRating() {
        return securityRating;
    }

    public void setSecurityRating(long securityRating) {
        this.securityRating = securityRating;
    }
}
