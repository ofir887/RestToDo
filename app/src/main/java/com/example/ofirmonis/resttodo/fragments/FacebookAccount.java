package com.example.ofirmonis.resttodo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ofirmonis.resttodo.R;
import com.example.ofirmonis.resttodo.activitys.LogInActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;





public class FacebookAccount extends Fragment{

    SimpleFacebook mSimpleFacebook=null;
    View rootView;
    Permission[] permissions;
    AccessToken accessToken;
    TextView facebookUserName;

    String TAG = "ThreeFragment: ";

    Profile.Properties pr=new Profile.Properties.Builder()
            .add(Profile.Properties.ID)
            .add(Profile.Properties.FIRST_NAME)
            .add(Profile.Properties.COVER)
            .add(Profile.Properties.WORK)
            .add(Profile.Properties.EDUCATION)
            .add(Profile.Properties.PICTURE).add(Profile.Properties.INSTALLED)
            .build();


    Profile.Properties properties = new Profile.Properties.Builder()
            .add(Profile.Properties.ID)
            .add(Profile.Properties.FIRST_NAME)
            .add(Profile.Properties.COVER)
            .add(Profile.Properties.WORK)
            .add(Profile.Properties.EDUCATION)
            .add(Profile.Properties.PICTURE).add(Profile.Properties.INSTALLED)
            .build();


    public FacebookAccount() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getActivity().getApplicationContext());
        accessToken = AccessToken.getCurrentAccessToken();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.facebook_account, container, false);

        facebookUserName = (TextView)rootView.findViewById(R.id.FaceBookUserName);
        facebookUserName.setText(com.facebook.Profile.getCurrentProfile().getName());
        SimpleFacebook.initialize(this.getActivity());
        mSimpleFacebook.initialize(this.getActivity());
        Button facebookButton = (Button)rootView.findViewById(R.id.login_button2);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
                LoginManager.getInstance().logOut();
                getActivity().finish();


            }
        });
        ProfilePictureView f = (ProfilePictureView)rootView.findViewById(R.id.image45);
        f.setProfileId(accessToken.getUserId());



        permissions = new Permission[] {
                Permission.EMAIL,
                Permission.PUBLISH_ACTION,
                Permission.USER_ABOUT_ME,
                Permission.USER_BIRTHDAY,
                Permission.USER_RELATIONSHIP_DETAILS,
                Permission.USER_FRIENDS,
                Permission.USER_RELATIONSHIPS,
                Permission.USER_LOCATION,
                Permission.USER_STATUS,
                Permission.USER_LIKES,
                Permission.USER_FRIENDS,
                Permission.READ_CUSTOM_FRIENDLISTS,
                Permission.USER_PHOTOS
        };





        // Inflate the layout for this fragment



        return rootView;
    }





    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


    }
}
