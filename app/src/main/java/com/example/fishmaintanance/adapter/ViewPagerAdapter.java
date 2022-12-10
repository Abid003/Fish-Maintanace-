package com.example.fishmaintanance.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fishmaintanance.fragments.Feeding;
import com.example.fishmaintanance.fragments.HomeFragment;
import com.example.fishmaintanance.fragments.PHFragment;
import com.example.fishmaintanance.fragments.more;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new Feeding();
            case 2:
                return new PHFragment();
            case 3:
                return  new more();
            default:
              return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
