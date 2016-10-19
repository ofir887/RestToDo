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
import com.example.ofirmonis.resttodo.objects.Restaurant;
import com.example.ofirmonis.resttodo.activitys.ReviewsActivity;

import java.util.ArrayList;


public class RestaurantAdapter extends ArrayAdapter<Restaurant>{
    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private FragmentActivity fragmentActivity;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants, FragmentActivity fragmentActivity) {
        super(context, 0, restaurants);
        this.restaurants = restaurants;
        this.fragmentActivity = fragmentActivity;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Restaurant restaurant = getItem(position);
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_row, parent, false);
            restaurant = getItem(position);//// TODO: 27/02/2016 delete
            holder.textView = (TextView)convertView.findViewById(R.id.rest_name_field);
            holder.imageView = (ImageView)convertView.findViewById(R.id.rest_circle_image);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textView.setText(restaurants.get(position).getName());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .withBorder(4)
                .endConfig()
                .buildRound(String.valueOf(restaurant.getName().charAt(0)), color);
        holder.imageView.setImageDrawable(drawable);
        moveToRestaurantsPage(restaurant, holder.imageView, holder.textView);
        notifyDataSetChanged();
        return convertView;
    }


    static class  ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    private void moveToRestaurantsPage(final Restaurant restaurant, ImageView imageView, TextView textView) {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRestaurantsPage(restaurant);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRestaurantsPage(restaurant);
            }
        });

    }

    private void goToRestaurantsPage(Restaurant restaurant) {
        Intent intent = new Intent(fragmentActivity, ReviewsActivity.class);
        intent.putExtra("restName",restaurant.getName());
        intent.putExtra("restUrl", restaurant.getUrl());
        fragmentActivity.startActivity(intent);
    }
}
