package com.example.myphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphone.R;
import com.example.myphone.adapter.LogsAdapter;
import com.example.myphone.dao.DBController;
import com.example.myphone.mode.Calls;
import com.example.myphone.myView.EditTextWithDel;

import java.util.List;

public class AllLogsActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "AllLogsActivity";

    private ImageView back, choose;
    private RecyclerView logsrecycle;
    private List<Calls> datas;
    private int cId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        DBController dbController =new DBController(this);
        Intent intent = this.getIntent();
        cId = intent.getIntExtra("contact_id", 0);
        initView();
        datas = dbController.getAllCallLogs(cId);
        initRecyclerView();
    }

    private void initView(){
        back = findViewById(R.id.logs_back);
        choose = findViewById(R.id.logs_choose);
        logsrecycle = findViewById(R.id.logs_recycle);

        //设置监听器
        back.setOnClickListener(this);
        choose.setOnClickListener(this);
    }

    private void initRecyclerView() {
        LogsAdapter logsAdapter = new LogsAdapter(this, datas);
        logsrecycle.setAdapter(logsAdapter);
        logsrecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logs_back:
                finish();
                break;
            case R.id.logs_choose:
                break;
        }
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
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestory");
    }
}
