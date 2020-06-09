package in.thelosergeek.hackathon_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private PostFragment postFragment;
    private ChatsFragment chatsFragment;
    private SearchFragment searchFragment;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        postFragment = new PostFragment();
        chatsFragment = new ChatsFragment();
        searchFragment = new SearchFragment();
        
        tabLayout.setupWithViewPager(viewPager);
        

        ViewPagerAdapter viewPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragments(postFragment,"");
        viewPagerAdapter.addFragments(chatsFragment,"");
        viewPagerAdapter.addFragments(searchFragment,"");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.chat);
        tabLayout.getTabAt(2).setIcon(R.drawable.search);


    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();


        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragments(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}

