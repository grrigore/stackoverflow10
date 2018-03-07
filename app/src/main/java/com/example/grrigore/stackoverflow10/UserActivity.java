package com.example.grrigore.stackoverflow10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private ImageView userProfilePictureTextView;
    private TextView userLocationTextView;
    private TextView userBronzeBadgeTextView;
    private TextView userSilverBadgeTextView;
    private TextView userGoldBadgeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        User user = getIntent().getParcelableExtra("userData");
    }
}
