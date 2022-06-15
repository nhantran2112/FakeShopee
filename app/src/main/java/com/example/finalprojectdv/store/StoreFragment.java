package com.example.finalprojectdv.store;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.VPAdapter;
import com.google.android.material.tabs.TabLayout;


public class StoreFragment extends Fragment {

    private MyProductFragment myProductFragment;
    private AddProductFragment addProductFragment;

    private String account;

    public StoreFragment(String account) {
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        myProductFragment = new MyProductFragment(account);
        addProductFragment = new AddProductFragment(account);
        setuptab(view);

        return view;
    }

    private void setuptab(View v){
        TabLayout tabLayout;
        ViewPager viewPager;
        VPAdapter vpAdapter = new VPAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(myProductFragment, "");
        vpAdapter.addFragment(addProductFragment, "");

        tabLayout = v.findViewById(R.id.idtablayoutstore);
        viewPager = v.findViewById(R.id.idviewpagerstore);
        viewPager.setAdapter(vpAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_outline_store_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_outline_add_business_24);
    }
}