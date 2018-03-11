package set08114.marcos.yourorganizer;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate : Starting");

        //Set up the VIewPager with the sections adapter
        mViewPager = findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //Set tab icons
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_book);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tv);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_film);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPageAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPageAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position){
            return mFragmentList.get(position);
        }
        @Override
        public int getCount(){
            return mFragmentList.size();
        }
    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new BookFragment(),"BOOKS");
        adapter.addFragment(new SeriesFragment(),"SERIES");
        adapter.addFragment(new FilmFragment(),"FILMS");
        viewPager.setAdapter(adapter);
    }
}
