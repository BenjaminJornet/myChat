package com.comeb.tchat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by côme on 24/09/2015.
 */
public class MyAdapter extends BaseAdapter {
    private final ArrayList list;
    private final Context context;

    public MyAdapter(Context c,ArrayList l) {
       list = l;
        context = c;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater lInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = lInflater.inflate(R.layout.item, null);
        }
        TextView pseudo = (TextView)convertView.findViewById(R.id.pseudo);
        TextView message = (TextView)convertView.findViewById(R.id.message);
        TextView time = (TextView)convertView.findViewById(R.id.time);
        Elem item =(Elem)list.get(position);

        ImageView thumb_image_left = (ImageView) convertView.findViewById(R.id.image_left);
        ImageView thumb_image_right = (ImageView) convertView.findViewById(R.id.image_right);

        if(item.isLeft()) {
            thumb_image_left.setImageResource(item.getResImg());
            thumb_image_right.setVisibility(View.INVISIBLE);
        }else{
            thumb_image_right.setImageResource(item.getResImg());
            thumb_image_left.setVisibility(View.INVISIBLE);
        }

        pseudo.setText(item.getPseudo());
        message.setText(item.getMessage());
        time.setText(item.getTime().toString());
        return convertView;
    }


}
