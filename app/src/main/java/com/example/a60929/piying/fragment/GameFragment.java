package com.example.a60929.piying.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a60929.piying.R;

public class GameFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savdInstanceState){
        View view = inflater.inflate(R.layout.fragment_game,null);
        return view;
    }
}
