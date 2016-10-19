package com.example.ofirmonis.resttodo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ofirmonis.resttodo.R;
import com.example.ofirmonis.resttodo.activitys.EventActivity;
import com.example.ofirmonis.resttodo.objects.Event;

import java.util.ArrayList;


public class EventAdapter extends ArrayAdapter<Event> {
    private ArrayList<Event> events = new ArrayList<>();
    private FragmentActivity fragmentActivity;

    public EventAdapter(Context context, ArrayList<Event> events, FragmentActivity fragmentActivity) {
        super(context, 0, events);
        this.events = events;
        this.fragmentActivity = fragmentActivity;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        Event event = getItem(position);
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_row, parent, false);
            event = getItem(position);//// TODO: 27/02/2016 delete
            holder.textTitle = (TextView)convertView.findViewById(R.id.text_event);
            holder.textDate = (TextView)convertView.findViewById(R.id.text_date);
            holder.textRestName = (TextView)convertView.findViewById(R.id.text_rest_name);
            holder.imageView = (ImageView)convertView.findViewById(R.id.image_view_event);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }


        holder.textTitle.setText(event.getTitle());
        holder.textDate.setText(event.getDate());
        holder.textRestName.setText(event.getRestaurant());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .withBorder(4)
                .endConfig()
                .buildRound(String.valueOf(event.getTitle().charAt(0)), color);
        holder.imageView.setImageDrawable(drawable);

        // TODO: 14/03/2016
        moveToEventPage(event, holder);

        notifyDataSetChanged();
        return convertView;
    }

    static class  ViewHolder{
        ImageView imageView;
        TextView textTitle;
        TextView textDate;
        TextView textRestName;
    }

    private void moveToEventPage(Event event, ViewHolder holder) {
        onclick(event,holder.imageView);
        onclick(event,holder.textDate);
        onclick(event,holder.textRestName);
        onclick(event, holder.textTitle);
    }

    private void onclick(final Event event, View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEventPage(event);
            }
        });
    }


    private void goToEventPage(Event event) {
        //// TODO: 14/03/2016         Intent intent = new Intent(fragmentActivity, ReviewsActivity.class) to activiti
        Intent intent = new Intent(fragmentActivity, EventActivity.class);
        // TODO: 27/02/2016 to see if i can put string!
        intent.putExtra("eventDate",event.getDate());
        intent.putExtra("eventName", event.getRestaurant());
        intent.putExtra("eventTitle", event.getTitle());
        intent.putExtra("restUrl",event.getUrl());
        fragmentActivity.startActivity(intent);
    }


}
