package com.egnize.deviceinfo.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egnize.deviceinfo.R;
import com.egnize.deviceinfo.adapters.AppsListAdapter;
import com.egnize.deviceinfo.utils.AppConst;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserAppsFragment extends BaseFragment {

    private AppsListAdapter adapter;
    @BindView(R.id.rv_apps_list)
    RecyclerView rv_apps_list;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apps, container, false);
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.dark_parrot_green_blue));
            window.setNavigationBarColor(getResources().getColor(R.color.dark_parrot_green_blue));

        }
        initUI();
        return view;
    }


    private void initUI() {
        adapter = new AppsListAdapter(getActivity(), AppConst.userAppsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_apps_list.setLayoutManager(mLayoutManager);
        rv_apps_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}
