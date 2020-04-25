package com.yey.slidingconflict.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragments;
    private Fragment mCurrentFragment;

    public MyFragmentStatePagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> fragments) {
        super(fm, behavior);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getmCurrentFragment() {
        return mCurrentFragment;
    }


}
