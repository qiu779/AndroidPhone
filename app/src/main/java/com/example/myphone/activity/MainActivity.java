package com.example.myphone.activity;



import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myphone.R;
import com.example.myphone.adapter.TabPagerAdapter;
import com.example.myphone.dao.DBController;
import com.example.myphone.service.PhoneService;
import com.google.android.material.tabs.TabLayout;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private String TAG = "MainActivity";

    private DBController dbController;
    private Intent intent1;
    private PhoneService.MyBinder mBinder;
    private MyConnection myConnection;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private PhoneTabFragment phoneTabFragment;
    private ContactTabFragment contactTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        dbController = new DBController(context);

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
//        intent1 = new Intent(this, PhoneService.class);
//        myConnection = new MyConnection();
//        bindService(intent1, myConnection, Context.BIND_AUTO_CREATE);
    }

    class MyConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, final IBinder service){
            mBinder=(PhoneService.MyBinder)service;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder=null;
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                Toast.makeText(this,"yes",Toast.LENGTH_SHORT);

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Intent intent1 =new Intent(this, PhoneService.class);
//        startService(intent1);
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.CALL_PHONE
                }
        );
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            Toast.makeText(this, "yes", Toast.LENGTH_SHORT);
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.PROCESS_OUTGOING_CALLS,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED
                },
                MY_PERMISSION_REQUEST_CODE
        );
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(myConnection);
        Log.d(TAG, "onDestory");
    }

//    private void getLocalCallLog(){
//        List<Calls> list = new ArrayList<>();
//        list = dbController.getLocalHistoryList();
//        if ( list == null)
//    }
//    private void getLocalContacts(){
//
//    }
}