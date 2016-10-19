package com.example.ofirmonis.resttodo.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ofirmonis.resttodo.R;
import com.example.ofirmonis.resttodo.objects.Event;
import com.example.ofirmonis.resttodo.objects.Review;
import com.facebook.AccessToken;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Firebase eventsRef = new Firebase("https://restmeup.firebaseio.com/users/");
    private Firebase reviewRef = new Firebase("https://restmeup.firebaseio.com/reviews/");
    private EditText dateTextView;
    private Button doneButton;
    private Calendar calendar;

    private Bundle bundle;
    private String reviewText = "";

    private String facebookUID;
    private String key;
    private Event event;

    private AlertDialog alertDialog;

    private static ArrayList<Activity> activities=new ArrayList<Activity>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);

        setContentView(R.layout.activity_event);



        bundle = getIntent().getExtras();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);

        }

        facebookUID = AccessToken.getCurrentAccessToken().getUserId();

        String eventDate = bundle.getString("eventDate");
        String eventName = bundle.getString("eventName");
        String eventTitle = bundle.getString("eventTitle");
        String restUrl = bundle.getString("restUrl");
        setTitle(eventTitle);
        event = new Event(eventDate,eventName,eventTitle,restUrl);


        Button dateButton = (Button) findViewById(R.id.change_date_button);
        dateTextView = (EditText)findViewById(R.id.date);
        dateTextView.setText(bundle.getString("eventDate"));
        calendar = Calendar.getInstance();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EventActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                end.add(Calendar.YEAR, 1);

                dpd.setMinDate(Calendar.getInstance());
                dpd.setMaxDate(end);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        getURL();

        doneButton = (Button)findViewById(R.id.done_button);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog = new AlertDialog.Builder(EventActivity.this).create();

                alertDialog.setTitle("Do you want to add review?");

                final EditText input = new EditText(EventActivity.this);

                alertDialog.setView(input);

//                alertDialog.setMessage("This is a three-button dialog!");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        reviewText = input.getText().toString();

                        String date;
                        date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);

                        String fbID = AccessToken.getCurrentAccessToken().getUserId();
                        String restID = bundle.getString("restUrl");
                        if (reviewText.length()>=4) {
                            Review review = new Review(fbID, restID, reviewText, date);
                            reviewRef.push().setValue(review);
                            eventsRef.child(facebookUID).child(key).removeValue();
                            finish();
                        }else {
                            Context context = getApplicationContext();


//                            CharSequence text = "text length need to be at least 5";
                            CharSequence text = "minimum 4 characters";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    } });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "no", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        eventsRef.child(facebookUID).child(key).removeValue();
                        System.out.println("ACTIVITYs: " + activities.size());
                        for (Activity activity : activities) {
                            System.out.println("ACTIVITY: " +activity.getClass().getName());
                            activity.finish();
                        }
                        finish();
                    }});

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.dismiss();
                    }});

                alertDialog.show();

            }
        });
    }


    private void getURL() {
        eventsRef.child(facebookUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Event e = d.getValue(Event.class);
                    if (e.equals(event)) {
                        key = d.getKey();
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.YEAR, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateTextView.setText(date);
        Map<String,Object> m = new HashMap<>();
        m.put("date",date);
        eventsRef.child(facebookUID).child(key).updateChildren(m);

    }
}
