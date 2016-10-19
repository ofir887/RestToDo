package com.example.ofirmonis.resttodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ofirmonis.resttodo.R;
import com.example.ofirmonis.resttodo.objects.Review;
import com.facebook.login.widget.ProfilePictureView;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;

import java.util.ArrayList;

public class ReviewsAdapter extends ArrayAdapter<Review> {

    private String TAG = "ReviewsAdapter ";
    private ArrayList<Review> reviews = new ArrayList<>();
    private ArrayList<Profile> friends = new ArrayList<>();

    public ReviewsAdapter(Context context, ArrayList<Review> reviews, ArrayList<Profile> friends) {
        super(context,0, reviews);
        this.reviews = reviews;
        this.friends = friends;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){

            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_row, parent, false);
            holder.reviewText = (TextView)convertView.findViewById(R.id.text_review);
            holder.userName = (TextView)convertView.findViewById(R.id.text_rest_name);
            holder.facebookProfileImage = (ProfilePictureView)convertView.findViewById(R.id.image_view_event);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.reviewText.setText(reviews.get(position).getReview());
        holder.userName.setText(getText(reviews.get(position), friends));
        holder.facebookProfileImage.setProfileId(reviews.get(position).getUser_id());
        return convertView;
    }

    private String getText(Review review, ArrayList<Profile> friends) {
        String id = review.getUser_id();
        for(int i=0;i<friends.size();i++){
            if(friends.get(i).getId().equals(id)){
                return friends.get(i).getName();
            }
        }

        if(SimpleFacebook.getInstance().getAccessToken().getUserId().equals(id))
        {
            com.facebook.Profile p = com.facebook.Profile.getCurrentProfile();
            return p.getName();
        }
        return null;
    }

    static class  ViewHolder{
        ProfilePictureView facebookProfileImage;
        TextView reviewText;
        TextView userName;
    }

}
