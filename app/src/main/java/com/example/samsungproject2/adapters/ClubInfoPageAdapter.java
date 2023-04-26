package com.example.samsungproject2.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.samsungproject2.view.clubs.clubinfo.ClubCommentsFragment;
import com.example.samsungproject2.view.clubs.clubinfo.ClubEventsFragment;
import com.example.samsungproject2.view.clubs.clubinfo.ClubMainInformationFragment;
import com.example.samsungproject2.view.clubs.clubinfo.ClubPhotosFragment;

public class ClubInfoPageAdapter extends FragmentStateAdapter {

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] {"Основное", "События", "Отзывы", "Фото"};

    public ClubInfoPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public String[] getTabTitles() {
        return tabTitles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new ClubMainInformationFragment();
                break;
            case 1:
                fragment = new ClubEventsFragment();
                break;
            case 2:
                fragment = new ClubCommentsFragment();
                break;
            case 3:
                fragment = new ClubPhotosFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}
