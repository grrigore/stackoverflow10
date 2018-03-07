package com.example.grrigore.stackoverflow10;

/**
 * Created by grrigore on 07.03.2018.
 */

public class User {
    private String userProfilePicture;
    private String userName;
    private String userLocation;
    private int userGoldBadge;
    private int userSilverBadge;
    private int userBronzeBadge;

    public User(String userProfilePicture, String userName, String userLocation, int userGoldBadge, int userSilverBadge, int userBronzeBadge) {
        this.userProfilePicture = userProfilePicture;
        this.userName = userName;
        this.userLocation = userLocation;
        this.userGoldBadge = userGoldBadge;
        this.userSilverBadge = userSilverBadge;
        this.userBronzeBadge = userBronzeBadge;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public int getUserGoldBadge() {
        return userGoldBadge;
    }

    public void setUserGoldBadge(int userGoldBadge) {
        this.userGoldBadge = userGoldBadge;
    }

    public int getUserSilverBadge() {
        return userSilverBadge;
    }

    public void setUserSilverBadge(int userSilverBadge) {
        this.userSilverBadge = userSilverBadge;
    }

    public int getUserBronzeBadge() {
        return userBronzeBadge;
    }

    public void setUserBronzeBadge(int userBronzeBadge) {
        this.userBronzeBadge = userBronzeBadge;
    }
}
