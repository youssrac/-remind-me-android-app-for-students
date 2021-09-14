package com.example.pavilion.remind2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by asus on 18/04/2018.
 */

class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : Notifications_Fragment notificationsfragment=new Notifications_Fragment();
                return notificationsfragment;
            case 1 : UsersFragment usersFragment=new UsersFragment();
                return usersFragment;
            case 2 : OhersFragment ohersFragment=new OhersFragment();
                return ohersFragment;
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
