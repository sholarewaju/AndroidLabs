package com.example.assignment3;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Message msg = null;
    int i=0;


    public CustomAdapter(Activity a, ArrayList d, Resources res) {
        activity = a;
        data = d;
        this.res = res;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        if(data.size()>0){
            Message msg = (Message)data.get(i);
            return msg.getId();
        }
        return -1;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        ViewHolder holder;

        if (view == null){
            vi = inflater.inflate(R.layout.activity_listview, null);

            holder = new ViewHolder();
            holder.textView =(TextView)vi.findViewById(R.id.msg);
            holder.img = (ImageView)vi.findViewById(R.id.icon);
            vi.setTag(holder);
        }
        else
            holder = (ViewHolder)vi.getTag();

        if (data.size()<=0){
            holder.textView.setText("No data");
        }
        else{
            msg = null;
            msg = (Message)data.get(i);

            holder.textView.setText(msg.getMsg());

            if (msg.getType())
                holder.img.setImageResource(R.drawable.icons8_male);
//              holder.img.setImageResource(res.getIdentifier("com.example.assignment3:drawable/icons8_male.png", null, null));
            else
                holder.img.setImageResource(R.drawable.icons8_female);
//              holder.img.setImageResource(res.getIdentifier("com.example.assignment3:drawable/icons8_female.png", null, null));
        }
        return vi;
    }

    public static class ViewHolder{
        public ImageView img;
        public TextView textView;
    }
}
