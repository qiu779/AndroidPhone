package com.example.myphone.activity;



import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myphone.R;
import com.example.myphone.activity.ContactTabFragment;
import com.example.myphone.activity.PhoneTabFragment;
import com.example.myphone.adapter.TabPagerAdapter;
import com.example.myphone.dao.DBController;
import com.example.myphone.dao.MyDataBaseHelper;
import com.google.android.material.tabs.TabLayout;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private PhoneTabFragment phoneTabFragment;
    private ContactTabFragment contactTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        //tab建立
//        for(int i=0;i<tabs.length;i++){
//            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
//        }

        //拉满
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //fragment实例化加到fragments中
        fragments = new ArrayList<>();
        Instant now = Instant.now();
        phoneTabFragment = new PhoneTabFragment();
        contactTabFragment =new ContactTabFragment();

        fragments.add(phoneTabFragment);
        fragments.add(contactTabFragment);


        //页面Tab适配器
        TabPagerAdapter tabPagerAdapter =
                new TabPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabPagerAdapter);

        //关联viewPager和TabLayout
        tabLayout.setupWithViewPager(viewPager);

        Instant now2 = Instant.now();
        Log.d("时间88",String.valueOf(Duration.between(now, now2).toMillis()));
    }
}