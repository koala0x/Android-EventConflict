package com.yey.slidingconflict;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yey.slidingconflict.adapter.MyRecyclerViewAdapter;

public class FirstFragment extends Fragment {

    private static final String TAG = FirstFragment.class.getName();
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 第1个Fragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 第1个Fragment");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_first, null);
        recyclerView = (RecyclerView) baseView.findViewById(R.id.rv);
        return baseView;
    }

    boolean isFirstLoad = true;

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 第1个Fragment");
        if (isFirstLoad) {
            isFirstLoad = !isFirstLoad;
            Log.d(TAG, "第1个Fragment懒加载");
            loadData();
        }
    }

    // 加载数据
    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MyRecyclerViewAdapter());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 第1个Fragment");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 第1个Fragment");
    }
}
