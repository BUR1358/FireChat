package com.example.firechat.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firechat.R;
import com.example.firechat.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> users;
    private OnUserClcikListener listener;

    public interface OnUserClcikListener {
        void onUserClcik(int position);
    }

    public void setOnUserClcikListener(OnUserClcikListener listener) {
        this.listener = listener;
    }

    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        UserViewHolder viewHolder = new UserViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        User currentUser = users.get(i);
        userViewHolder.avatarImageView.setImageResource(currentUser.getAvatarMockUpResourse());
        userViewHolder.userNameTextView.setText(currentUser.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarImageView;
        public TextView userNameTextView;

        public UserViewHolder(@NonNull View itemView, OnUserClcikListener listener) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUserClcik(position);
                        }
                    }
                }
            });
        }

    }
}
