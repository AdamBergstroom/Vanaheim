package se.vanaheim.vanaheim.viewmodels;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import se.vanaheim.vanaheim.R;
import se.vanaheim.vanaheim.adapters.CustomizedFragmentPagerAdapter;

public class AreaTableAndViewPagerLayout extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomizedFragmentPagerAdapter(getSupportFragmentManager(),
                AreaTableAndViewPagerLayout.this));

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}