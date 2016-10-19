package com.example.ofirmonis.resttodo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ofirmonis.resttodo.fragments.EventList;
import com.example.ofirmonis.resttodo.fragments.FacebookAccount;
import com.example.ofirmonis.resttodo.fragments.RestaurantList;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private int tabNumber;

    public PagerAdapter(FragmentManager fm, int tabNumber) {
        super(fm);
        this.tabNumber = tabNumber;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                EventList tab1 = new EventList();
                return tab1;
            case 1:
                RestaurantList tab2 = new RestaurantList();
                return tab2;
            case 2:
                FacebookAccount tab3 = new FacebookAccount();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }

}