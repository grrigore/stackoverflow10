package com.example.grrigore.stackoverflow10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userAdapter = new UserAdapter(userList, getApplicationContext());

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        prepareUserData();
    }

    private void prepareUserData() {
        User user = new User("https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&d=identicon&r=PG",
                "user1", "Romania", 12, 1, 2);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);
        userList.add(user);

    }
}
