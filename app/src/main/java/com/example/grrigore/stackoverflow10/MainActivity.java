package com.example.grrigore.stackoverflow10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

    //TODO internet connection check
    //TODO 2 level caching (RAM & disk)
    //TODO process dying restore? smth like this
    //TODO comments
    //TODO everything in res/
    //TODO no hardcode
    //TODO UI polish
    //TODO comments
    //TODO files structure - packages
    //TODO run findBugs
    //TODO run Lint

    private static final String STATE_ITEMS = "items";

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String URL = "https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow&filter=!LnOMtAecZnTWD8_9-F83ja";
    private List<User> userList = new ArrayList<>();
    private ListView listView;
    private UserAdapter userAdapter;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        if (savedInstanceState == null || !savedInstanceState.containsKey(STATE_ITEMS))
            parseJson();
        else
            userList = savedInstanceState.getParcelableArrayList(STATE_ITEMS);

        userAdapter = new UserAdapter(userList, getApplicationContext());

        listView.setAdapter(userAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("userData", userList.get(i));
                startActivity(intent);
            }
        });
    }

    private void parseJson() {
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_ITEMS, new ArrayList<>(userList));
    }
}
