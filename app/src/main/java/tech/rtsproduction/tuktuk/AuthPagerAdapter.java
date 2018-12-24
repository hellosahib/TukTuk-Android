package tech.rtsproduction.tuktuk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AuthPagerAdapter extends FragmentPagerAdapter {

    public AuthPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    final private int TOTAL_FRAGMENTS = 2;

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: return new LoginFragment();
            case 1: return new RegisterFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_FRAGMENTS;
    }

}
