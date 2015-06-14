package com.example.binyamin.fakebook;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ChannelArrayAdapter extends ArrayAdapter<Channel> {

    TextView channelName;
    Context context;
    private List<Channel> channelList = new ArrayList();
    RelativeLayout singleMessageContainer;

    public ChannelArrayAdapter(Context context, int resourceId,
                               List<Channel> items) {
        super(context, resourceId);
        this.context = context;
        channelList = items;
    }

    /*private view holder class
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }
    */

    public int getCount() {
        return this.channelList.size();
    }

    public Channel getItem(int index) {
        return this.channelList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
        }
        singleMessageContainer = (RelativeLayout) row.findViewById(R.id.list_item_relative);
        Channel ChannelOnj = getItem(position);
        channelName = (TextView) row.findViewById(R.id.title);
        channelName.setText(ChannelOnj.getName() + "\n" + ChannelOnj.getId());
        return row;
    }
}