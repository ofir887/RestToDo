package com.example.ofirmonis.resttodo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.ofirmonis.resttodo.R;
import com.example.ofirmonis.resttodo.adapters.RestaurantAdapter;
import com.example.ofirmonis.resttodo.objects.Restaurant;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class RestaurantList extends Fragment {
    private String TAG = "RestaurantList: ";
    private ArrayList<Restaurant> restaurantArrayList;
    private RestaurantAdapter adapter;
    private Firebase restRef = new Firebase("https://restmeup.firebaseio.com/restaurants");
    ListView restaurantList ;

    public RestaurantList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_restaurant_list, container, false);
        restaurantList = (ListView) rootView.findViewById(R.id.rest_list);

        //
        adapter = new RestaurantAdapter(getContext(),restaurantArrayList, getActivity());
        restaurantList.setAdapter(adapter);
        //

        restRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setRestaurantRef(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return rootView;
    }



    public void setRestaurantRef(DataSnapshot dataSnapshot){
        restaurantArrayList.clear();
        adapter.notifyDataSetChanged();
        for(DataSnapshot rest: dataSnapshot.getChildren()){
            Restaurant restaurant = rest.getValue(Restaurant.class);
            restaurantArrayList.add(restaurant);
            sortString();
            adapter.notifyDataSetChanged();
        }
    }
    private void sortString(){
        Collections.sort(restaurantArrayList, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return r1.getName().compareToIgnoreCase(r2.getName());
            }
        });
    }


}


