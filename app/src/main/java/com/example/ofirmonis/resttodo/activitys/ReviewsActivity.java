package com.example.ofirmonis.resttodo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ofirmonis.resttodo.R;
import com.example.ofirmonis.resttodo.adapters.ReviewsAdapter;
import com.example.ofirmonis.resttodo.objects.Review;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnFriendsListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private String TAG = "ReviewsActivity ";
    private ArrayList<Review> reviewsArrayList;
    private ArrayList<Profile> facebookFriends;
    private ReviewsAdapter adapter;
    private Firebase reviewRef = new Firebase("https://restmeup.firebaseio.com/reviews/");
    private String restName;
    private static Activity CurrentActivity;
    private ListView listView;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resturant_page);
        CurrentActivity = this;
        Bundle b = getIntent().getExtras();
        FacebookSdk.sdkInitialize(getApplicationContext());
        SimpleFacebook.initialize(this);


        /*---UI---*/

        Button web = (Button) findViewById(R.id.web);
        listView = (ListView)findViewById(R.id.ReviewsList);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        facebookFriends = new ArrayList<>();
        reviewsArrayList = new ArrayList<>();


        /**---WebView---**/
        url = b.getString("restUrl");

        restName = b.getString("restName");
        setTitle(restName);


        reviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setReviewsRef(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewsActivity.this, CreateEventActivity.class);
                intent.putExtra("ResName", restName);
                intent.putExtra("restUrl", url);
                startActivity(intent);
            }
        });


        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void myUpdateOperation() {

    }

    private void setReviewsRef(final DataSnapshot dataSnapshot) {
        reviewsArrayList.clear();
        adapter = new ReviewsAdapter(getBaseContext(),reviewsArrayList, facebookFriends);
        adapter.notifyDataSetChanged();

        SimpleFacebook.getInstance().getFriends(new OnFriendsListener() {
            @Override
            public void onComplete(List<Profile> response) {


                for (int i = 0; i < response.size();i++){
                    facebookFriends.add(response.get(i));
                }
                for(DataSnapshot reviewItem: dataSnapshot.getChildren()) {
                    //    mSwipeRefreshLayout.setRefreshing(true);
                    Review review = reviewItem.getValue(Review.class);
                    if (review.getRest_id().equals(url)) {
                        if (review.getUser_id().equals(AccessToken.getCurrentAccessToken().getUserId()))
                            reviewsArrayList.add(review);
                        else
                            for (int j = 0; j < facebookFriends.size(); j++) {
                                if (facebookFriends.get(j).getId().equals(review.getUser_id())) {
                                    reviewsArrayList.add(review);
                                    break;
                                }


                        }
                    }
                }
                setTitle(restName+" - "+reviewsArrayList.size()+" reviews");

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }
    public static void CloseActivity(){
        CurrentActivity.finish();
    }
}
