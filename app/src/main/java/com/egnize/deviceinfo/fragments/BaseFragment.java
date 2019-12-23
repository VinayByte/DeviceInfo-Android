package com.egnize.deviceinfo.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.egnize.deviceinfo.activity.HomeActivity;
import com.egnize.deviceinfo.utils.FragmentUtil;
import com.egnize.deviceinfo.utils.Methods;


public class BaseFragment extends Fragment {

    public HomeActivity mActivity;
    public FragmentUtil fragmentUtil;
    Resources mResources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (HomeActivity) getActivity();
        fragmentUtil = new FragmentUtil(mActivity);
        mResources = mActivity.getResources();

        new Methods(mActivity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (HomeActivity) getActivity();
    }


}
