package com.example.grrigore.stackoverflow10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by grrigore on 07.03.2018.
 */

public class UserAdapter extends BaseAdapter {

    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        if (userList == null)
            return null;
        else
            return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = null;
        final int currentPosition = position;
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            int layoutId = R.layout.list_item;
            view = layoutInflater.inflate(layoutId, viewGroup, false);
            viewHolder.userName = view.findViewById(R.id.name_list_item);
            viewHolder.userProfileImage = view.findViewById(R.id.profileImage_list_item);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        User user = (User) getItem(currentPosition);
        viewHolder.userName.setText(user.getUserName());
        Picasso.with(context).load(userList.get(position).getUserProfilePicture()).into(viewHolder.userProfileImage);
        return view;
    }

    static class ViewHolder {
        TextView userName;
        ImageView userProfileImage;
    }
}
