package com.example.grrigore.stackoverflow10;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
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

    private boolean serverError = false;

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String URL = "https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow&filter=!LnOMtAecZnTWD8_9-F83ja";
    private List<User> userList = new ArrayList<>();
    private ListView listView;
    private UserAdapter userAdapter;

    private RequestQueue queue;


    private TextView errorTextView;
    private ImageView errorImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainActivity(savedInstanceState);
    }

    private void setMainActivity(Bundle savedInstanceState) {
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
                if (error instanceof NoConnectionError) {
//This indicates that  there is no connection
                    setErrorLayout(R.string.no_internet_text, R.drawable.nointernet);
                } else if (error instanceof ServerError) {
//Indicates that the server responded with a error response
                    setErrorLayout(R.string.server_text, R.drawable.server_error);
                    serverError = true;
                }
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void setErrorLayout(int errorText, int errorImage) {
        setContentView(R.layout.error_layout);

        errorTextView = findViewById(R.id.error_text);
        errorImageView = findViewById(R.id.error_image);


        errorTextView.setText(errorText);
        Picasso.with(getApplicationContext()).load(errorImage).into(errorImageView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_ITEMS, new ArrayList<>(userList));
    }

    public void reloadActivity(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo data = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null & data != null) && (wifi.isConnected() || data.isConnected())) {
            setMainActivity(null);
        } else {
            Toast.makeText(getApplicationContext(), "Connect to internet!", Toast.LENGTH_SHORT).show();
        }

        if(serverError){
            Toast.makeText(getApplicationContext(),"Try again later!",Toast.LENGTH_SHORT).show();
        }
    }
}
