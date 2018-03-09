package com.example.grrigore.stackoverflow10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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
                //TODO implement each case
                if (error instanceof TimeoutError) {
//This indicates that the reuest is time out
                } else if (error instanceof NoConnectionError) {
//This indicates that  there is no connection
                    TextView errorTextView = findViewById(R.id.error_text);
                    ImageView errorImageView = findViewById(R.id.error_image);
                    setContentView(R.layout.error_layout);

                    errorTextView.setText("No internet connection!");
                    Picasso.with(getApplicationContext()).load(R.drawable.icons8_wi_fi_off_100).resize(200,200).into(errorImageView);
                } else if (error instanceof AuthFailureError) {
// Error indicating that there was an Authentication Failure while performing the request
                } else if (error instanceof ServerError) {
//Indicates that the server responded with a error response
                } else if (error instanceof NetworkError) {
//Indicates that there was network error while performing the request
                } else if (error instanceof ParseError) {
// Indicates that the server response could not be parsed
                }
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
