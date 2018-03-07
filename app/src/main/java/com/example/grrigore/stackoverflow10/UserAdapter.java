package com.example.grrigore.stackoverflow10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by grrigore on 07.03.2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.userName.setText(userList.get(position).getUserName());
        Picasso.with(context).load(userList.get(position).getUserProfilePicture()).into(holder.userProfileImage);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView userProfileImage;

        public MyViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.name_list_item);
            userProfileImage = view.findViewById(R.id.profileImage_list_item);
        }
    }
}
