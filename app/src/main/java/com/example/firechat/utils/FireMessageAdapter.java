package com.example.firechat.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firechat.R;
import com.example.firechat.model.FireMessage;

import java.util.List;

public class FireMessageAdapter extends ArrayAdapter<FireMessage> {

    private List<FireMessage> messages;
    private Activity activity;

    public FireMessageAdapter(Activity context, int resource, List<FireMessage> messages) {
        super(context, resource, messages);
        this.messages = messages;
        this.activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        FireMessage fireMessage = getItem(position);
        int layoutResourse = 0;
        int viewType = getItemViewType(position);

        if (viewType == 0) {
            layoutResourse = R.layout.my_message_item;
        } else {
            layoutResourse = R.layout.your_message_item;
        }
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(layoutResourse, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        boolean isText = fireMessage.getImageURL() == null;
        if (isText) {
            viewHolder.messageTextView.setVisibility(View.VISIBLE);
            viewHolder.photoImageView.setVisibility(View.GONE);
            viewHolder.messageTextView.setText(fireMessage.getText());
        } else {
            viewHolder.messageTextView.setVisibility(View.GONE);
            viewHolder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.photoImageView.getContext()).load(fireMessage.getImageURL()).into(viewHolder.photoImageView);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int flag;
        FireMessage fireMessage = messages.get(position);
        if (fireMessage.isMine()) {
            flag = 0;
        } else {
            flag = 1;
        }
        return flag;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolder {
        private TextView messageTextView;
        private ImageView photoImageView;

        public ViewHolder(View view) {
            photoImageView = view.findViewById(R.id.photoImageView);
            messageTextView = view.findViewById(R.id.messageTextView);
        }
    }
}
