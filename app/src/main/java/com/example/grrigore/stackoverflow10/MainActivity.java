package com.example.grrigore.stackoverflow10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String URL = "https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow&filter=!LnOMtAecZnTWD8_9-F83ja";
    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userAdapter = new UserAdapter(userList, getApplicationContext());

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject userObject = jsonArray.getJSONObject(i);

                        User user = new User();
                        user.setUserName(userObject.getString("display_name"));
                        user.setUserProfilePicture(userObject.getString("profile_image"));
                        user.setUserLocation(userObject.getString("location"));

                        JSONObject userBadges = userObject.getJSONObject("badge_counts");

                        user.setUserGoldBadge(userBadges.getInt("gold"));
                        user.setUserSilverBadge(userBadges.getInt("silver"));
                        user.setUserBronzeBadge(userBadges.getInt("bronze"));

                        userList.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                userAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        queue.add(jsonObjectRequest);
    }
}
