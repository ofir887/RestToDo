package com.example.ofirmonis.resttodo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ofirmonis.resttodo.R;
import com.example.ofirmonis.resttodo.adapters.EventAdapter;
import com.example.ofirmonis.resttodo.objects.Event;
import com.facebook.AccessToken;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class EventList extends Fragment{

    private ArrayList<Event> eventsArrayList;
    private EventAdapter adapter;
    private Firebase eventRef = new Firebase("https://restmeup.firebaseio.com/users");



    public EventList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.todo_fragment, container, false);
        final ListView eventListView = (ListView)rootView.findViewById(R.id.todo_list);
        adapter = new EventAdapter(getContext(), eventsArrayList, getActivity());
        eventListView.setAdapter(adapter);
        eventRef.child(AccessToken.getCurrentAccessToken().getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventsArrayList.clear();
                adapter.notifyDataSetChanged();



                for (DataSnapshot eventData : dataSnapshot.getChildren()) {
                    Event e = eventData.getValue(Event.class);

                    eventsArrayList.add(e);
                }
                sortByDate();
                adapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return rootView;

    }

    private void sortByDate() {
        Collections.sort(eventsArrayList, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = new Date();
                Date date2 = new Date();
                try {
                    date1 = df.parse(o1.getDate());
                    date2 = df.parse(o2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
    }
}
