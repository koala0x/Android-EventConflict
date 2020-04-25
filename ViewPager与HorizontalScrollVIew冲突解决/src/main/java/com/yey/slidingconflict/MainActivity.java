package com.yey.slidingconflict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.yey.slidingconflict.adapter.MyFragmentStatePagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager mVp;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVp = (ViewPager) findViewById(R.id.vp);
        addFragments();
        MyFragmentStatePagerAdapter myFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                mFragments);
        mVp.setAdapter(myFragmentStatePagerAdapter);
    }

    private void addFragments() {
        mFragments = new ArrayList();
        mFragments.add(new FirstFragment());
        mFragments.add(new SecondFragment());
    }
}
