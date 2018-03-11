package com.example.grrigore.stackoverflow10.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by grrigore on 07.03.2018.
 */

public class User implements Parcelable {
    private String userProfilePicture;
    private String userName;
    private String userLocation;
    private int userGoldBadge;
    private int userSilverBadge;
    private int userBronzeBadge;


    /**
     * empty constructor
     */
    public User() {
    }

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

    @Override
    public String toString() {
        return "User{" +
                "userProfilePicture='" + userProfilePicture + '\'' +
                ", userName='" + userName + '\'' +
                ", userLocation='" + userLocation + '\'' +
                ", userGoldBadge=" + userGoldBadge +
                ", userSilverBadge=" + userSilverBadge +
                ", userBronzeBadge=" + userBronzeBadge +
                '}';
    }

    private User(Parcel in) {
        userProfilePicture = in.readString();
        userName = in.readString();
        userLocation = in.readString();
        userGoldBadge = in.readInt();
        userSilverBadge = in.readInt();
        userBronzeBadge = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userProfilePicture);
        parcel.writeString(userName);
        parcel.writeString(userLocation);
        parcel.writeInt(userGoldBadge);
        parcel.writeInt(userSilverBadge);
        parcel.writeInt(userBronzeBadge);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
