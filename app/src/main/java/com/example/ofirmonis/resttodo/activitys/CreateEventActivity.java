package com.example.ofirmonis.resttodo.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ofirmonis.resttodo.R;
import com.example.ofirmonis.resttodo.objects.Event;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.firebase.client.Firebase;
import com.sromku.simple.fb.SimpleFacebook;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText dateTextView;
    private EditText titleTextView;
    private Calendar calendar;
    private Button createEvent;
    private Firebase ref;
    private String restName;
    private static ArrayList<Activity> activities=new ArrayList<Activity>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
        setContentView(R.layout.activity_create_event);
        FacebookSdk.sdkInitialize(getApplicationContext());
        SimpleFacebook.initialize(this);

        Bundle b = getIntent().getExtras();
        Bundle bundle = getIntent().getExtras();

        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);

        }

        dateTextView = (EditText)findViewById(R.id.editTextDate);
        Button dateButton = (Button) findViewById(R.id.date);

        titleTextView = (EditText)findViewById(R.id.eventTitleText);

        createEvent = (Button)findViewById(R.id.createEvent);

        createEvent.setEnabled(false);

        calendar = Calendar.getInstance();
        restName = b.getString("ResName");
        setTitle(b.getString("ResName"));
        final String url = b.getString("restUrl");
        String facebookUesrName;



        AccessToken accessToken = AccessToken.getCurrentAccessToken();


        final String facebookUID = accessToken.getUserId();
//        System.out.println("my UID:" + accessToken.getUserId());
        Profile profile = Profile.getCurrentProfile();
        final String name = profile.getName();
//        System.out.println("my name is: "+name);
        ref = new Firebase("https://restmeup.firebaseio.com/users/"+facebookUID);//// TODO: 26/02/2016

//        SimpleFacebook.getInstance().getProfile(new OnProfileListener() {
//            @Override
//            public void onComplete(Profile response) {
//                super.onComplete(response);
//                //facebookUesrName = response.getName();
//                System.out.println("my UID-Name:" + response.getName());
//
//            }
//        });


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateEventActivity.this,
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


        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateTextView.getText().length() > 0 && titleTextView.length() > 0) {
                    ref.child(facebookUID);
                    Event event = new Event(dateTextView.getText().toString(), restName, titleTextView.getText().toString(), url);
                    ref.push().setValue(event);

                    ReviewsActivity.CloseActivity();	//                    Intent intent = new Intent(CreateEventActivity.this, MainActivity.class);

//                    System.out.println(activities.size());
                    for (Activity activity : activities) {
//                            System.out.println(activity.toString());
                        activity.finish();
                    }
                    finish();
//                    startActivity(intent);
                }
            }
        });



        titleTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(titleTextView.getText().length()>0 && dateTextView.getText().length()>0){
                    createEvent.setEnabled(true);
                }else {
                    createEvent.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.YEAR, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        dateTextView.setText(date);

        if(dateTextView.getText().length()>0 && titleTextView.length()>0){
            createEvent.setEnabled(true);
        }
    }

}
