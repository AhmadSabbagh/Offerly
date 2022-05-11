package com.example.ahmad.hakimi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmad.hakimi.tab.Fav_news;
import com.example.ahmad.hakimi.tab.Fav_offer;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class NewFavoriteAvtivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_favorite_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bnve) ;
        // BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.id1 ://firstPage
                        startActivity(new Intent(NewFavoriteAvtivity.this,First_Page.class));
                        finish();
                        break;
                    case R.id.navigation_home : //homePage
                        startActivity(new Intent(NewFavoriteAvtivity.this,HomePage.class));
                        finish();
                        break;
                    case R.id.navigation_notifications ://NearBy
                        startActivity(new Intent(NewFavoriteAvtivity.this,hot_event_activity.class));
                        finish();
                        break;
                    case R.id.navigation_dashboard : //account
                        startActivity(new Intent(NewFavoriteAvtivity.this,product.class));
                        finish();
                        break;
                    case R.id.id2 ://favorite
                        break;

                }
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fav_news, container, false);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0 : Fragment newss =getNews();
                    return newss;
                case 1 : Fragment newss1 =getOffer();
                    return newss1;
             /*   case 2 : Fragment offer = getOffer();
                    return  offer;*/

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
    public Fragment getNews ()
    {
        Fav_news fav_news =new Fav_news(this);
        return  fav_news;
    }
    public Fragment getOffer ()
    {
        Fav_offer fav_offer = new Fav_offer (this);
        return fav_offer;
    }
}
