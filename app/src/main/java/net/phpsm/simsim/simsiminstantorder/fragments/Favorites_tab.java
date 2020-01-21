package net.phpsm.simsim.simsiminstantorder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;

import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.ArrayList;
import java.util.List;



public class Favorites_tab extends Fragment {
    private View view;
    AppSharedPreferences appSharedPreferences;
    String token;
    String refreshToken;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static Favorites_tab newInstance() {
        Favorites_tab fragment = new Favorites_tab();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorites_tab, container, false);
       // sharedPreferencesGet = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
       // editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        appSharedPreferences = new AppSharedPreferences(getContext());
        token = appSharedPreferences.readString("access_token");
        refreshToken = appSharedPreferences.readString("refresh_token");
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout view2  = (LinearLayout) tab.getCustomView();
                TextView title=view2.findViewById(R.id.title);
                title.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                /*ImageView img=view2.findViewById(R.id.img);
                img.setSelected(true);*/
                //  = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_header, null);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout view  = (LinearLayout) tab.getCustomView();
                TextView title=view.findViewById(R.id.title);
                title.setTextColor(getActivity().getResources().getColor(R.color.bold_name));
                /*ImageView img=view.findViewById(R.id.img);
                img.setSelected(false);*/
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }



    private void setupTabIcons() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_header, null);
        TextView title=tabOne.findViewById(R.id.title);
        title.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        /*ImageView img=tabOne.findViewById(R.id.img);
        img.setSelected(true);*/

        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_header2, null);
        tabLayout.getTabAt(1).setCustomView(tabTwo);



    }

    private void setupViewPager(ViewPager viewPager1) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new FavoriteProvider(), getString(R.string.providers));
        adapter.addFrag(new Favorite_orders(), getString(R.string.myfriendrecommended));
        viewPager1.setAdapter(adapter);
        viewPager1.invalidate();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




}
