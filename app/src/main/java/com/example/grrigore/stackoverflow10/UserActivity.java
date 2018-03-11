package com.example.grrigore.stackoverflow10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grrigore.stackoverflow10.model.User;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {


    private static final String INTENT_KEY = "userData";

    private TextView userNameTextView;
    private ImageView userProfilePictureImageView;
    private TextView userLocationTextView;
    private TextView userBronzeBadgeTextView;
    private TextView userSilverBadgeTextView;
    private TextView userGoldBadgeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userNameTextView = findViewById(R.id.user_name);
        userProfilePictureImageView = findViewById(R.id.user_profile_picture);
        userLocationTextView = findViewById(R.id.user_location);
        userBronzeBadgeTextView = findViewById(R.id.bronze_badges);
        userSilverBadgeTextView = findViewById(R.id.silver_badges);
        userGoldBadgeTextView = findViewById(R.id.gold_badges);

        User user = getIntent().getParcelableExtra(INTENT_KEY);

        userNameTextView.setText(user.getUserName());
        Picasso.with(this).load(user.getUserProfilePicture()).resize(240, 240).into(userProfilePictureImageView);
        userLocationTextView.setText(user.getUserLocation());
        userBronzeBadgeTextView.setText(String.valueOf(user.getUserBronzeBadge()));
        userSilverBadgeTextView.setText(String.valueOf(user.getUserSilverBadge()));
        userGoldBadgeTextView.setText(String.valueOf(user.getUserGoldBadge()));
    }
}
