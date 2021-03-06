package com.example.myphone.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphone.dao.DBController;
import com.example.myphone.dao.MyDataBaseHelper;
import com.example.myphone.R;
import com.example.myphone.adapter.PhoneAdapter;
import com.example.myphone.myView.EditTextWithDel;
import com.example.myphone.mode.Calls;
import com.example.myphone.utils.Util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhoneTabFragment extends Fragment implements View.OnClickListener,View.OnLongClickListener {
    private static final String TAG = "PhoneTabFragment";
    private static final String TABLE_NAME = "Calls";

    private ImageButton phoneOne, phoneTwo, phoneThree, phoneFour, phoneFive, phoneSix, phoneSeven, phoneEight, phoneNine, phoneZero, phoneHash, phoneAsterisk, phoneCall1, phoneCall2, phoneHelp, phoneShow, phoneDelete, phonevanish;
    private EditText phoneCallNum;
    private EditTextWithDel phoneSearch;
    private LinearLayout phoneTable, phoneTableTop;

    private View root;
    private Context context;
    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;
    private PhoneAdapter phoneAdapter;
    private DBController dbController;
    private RecyclerView phoneRecycleView;
    private List<Calls> datas;

    //??????????????????????????????
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Long date;

    //??????????????????SQL??????
//    private static final String initAll = "select * from " + TABLE_NAME;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_phone, container, false);
        datas = new ArrayList<>();
        Date date = new Date();
        date.getTime();
        dbController = new DBController(context);
        Instant now = Instant.now();
        datas = dbController.getPhoneRvItem();
        if (datas.size() == 0){
            //?????????????????????????????????
            dbController.getLocalContacts();

            dbController.getLocalHistoryList();
            dbController.setContact_id();
            datas = dbController.getPhoneRvItem();
        }
        dbController.setContact_id();
        initRecycleView();
        Instant now2 = Instant.now();
        Log.d("??????11",String.valueOf(Duration.between(now, now2).toMillis()));
        Instant now3 = Instant.now();
        initView();
        Instant now4 = Instant.now();
        Log.d("??????11",String.valueOf(Duration.between(now3, now4).toMillis()));

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
//        int temp = dbController.getPhoneRvItemCount();
//        Log.d(TAG, "getCount =" + temp);
//        Log.d(TAG, "data.size =" + datas.size());
//        if (datas.size() != temp){
//            datas.clear();
//            datas.addAll(dbController.getPhoneRvItem());
//            phoneAdapter.notifyDataSetChanged();
//        }
        Log.d(TAG, "onStart");
    }


    private void initView() {
        phoneSearch = root.findViewById(R.id.phone_search);
        phoneCallNum = root.findViewById(R.id.phone_callNumber);
        phoneOne = root.findViewById(R.id.phone_one);
        phoneTwo = root.findViewById(R.id.phone_two);
        phoneThree = root.findViewById(R.id.phone_three);
        phoneFour = root.findViewById(R.id.phone_four);
        phoneFive = root.findViewById(R.id.phone_five);
        phoneSix = root.findViewById(R.id.phone_six);
        phoneSeven = root.findViewById(R.id.phone_seven);
        phoneEight = root.findViewById(R.id.phone_eight);
        phoneNine = root.findViewById(R.id.phone_nine);
        phoneZero = root.findViewById(R.id.phone_zero);
        phoneAsterisk = root.findViewById(R.id.phone_asterisk);
        phoneHash = root.findViewById(R.id.phone_hash);
        phoneHelp = root.findViewById(R.id.phone_help);
        phoneShow = root.findViewById(R.id.phone_show);
        phoneCall1 = root.findViewById(R.id.phone_call1);
        phoneCall2 = root.findViewById(R.id.phone_call2);
        phoneDelete = root.findViewById(R.id.phone_delete);
        phonevanish = root.findViewById(R.id.phone_vanish);
        phoneTable = root.findViewById(R.id.phone_table);
        phoneTableTop = root.findViewById(R.id.phone_tableTop);

        phoneOne.setOnClickListener(this);
        phoneTwo.setOnClickListener(this);
        phoneThree.setOnClickListener(this);
        phoneFour.setOnClickListener(this);
        phoneFive.setOnClickListener(this);
        phoneSix.setOnClickListener(this);
        phoneSeven.setOnClickListener(this);
        phoneEight.setOnClickListener(this);
        phoneNine.setOnClickListener(this);
        phoneZero.setOnClickListener(this);
        phoneAsterisk.setOnClickListener(this);
        phoneHash.setOnClickListener(this);
        phoneHelp.setOnClickListener(this);
        phoneShow.setOnClickListener(this);
        phoneCall1.setOnClickListener(this);
        phoneCall2.setOnClickListener(this);
        phoneDelete.setOnClickListener(this);
        phoneDelete.setOnLongClickListener(this);
        phoneSearch.setOnClickListener(this);
        phonevanish.setOnClickListener(this);

        phoneCallNum.setInputType(InputType.TYPE_NULL);
    }

    private void initRecycleView() {
        Instant now5 = Instant.now();
        phoneRecycleView = root.findViewById(R.id.phone_recycle);
        phoneAdapter = new PhoneAdapter(context, datas);
        phoneRecycleView.setAdapter(phoneAdapter);
        phoneRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setRvEventListener();
        Instant now6 = Instant.now();
        Log.d("??????99",String.valueOf(Duration.between(now5, now6).toMillis()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestory");
    }

    /*
    ?????????????????????????????????
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_one:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "1");
                break;
            case R.id.phone_two:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "2");
                break;
            case R.id.phone_three:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "3");
                break;
            case R.id.phone_four:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "4");
                break;
            case R.id.phone_five:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "5");
                break;
            case R.id.phone_six:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "6");
                break;
            case R.id.phone_seven:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "7");
                break;
            case R.id.phone_eight:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "8");
                break;
            case R.id.phone_nine:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "9");
                break;
            case R.id.phone_zero:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "0");
                break;
            case R.id.phone_asterisk:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "*");
                break;
            case R.id.phone_hash:
                callNumShow();
                phoneCallNum.setText(phoneCallNum.getText() + "#");
                break;
            case R.id.phone_help:

                break;
            case R.id.phone_show:
                phoneTable.setVisibility(View.VISIBLE);
                phoneShow.setVisibility(View.GONE);
                if (!phoneCallNum.getText().toString().isEmpty()) {
                    phoneTableTop.setVisibility(View.VISIBLE);
                    phoneTableTop.setBackgroundResource(R.drawable.radius);
                    phoneTable.setBackground(null);
                }
                break;
            case R.id.phone_vanish:
                phoneTable.setVisibility(View.GONE);
                phoneTableTop.setVisibility(View.GONE);
                phoneShow.setVisibility(View.VISIBLE);
                break;
            case R.id.phone_call1:
                String number = String.valueOf(phoneCallNum.getText());
//                Calls calls = new Calls();
//                calls.setContact_id(15);
//                dbController.InsertCall(calls);
                Util.callPhone(context, number);
                break;
            case R.id.phone_call2:

                break;
            case R.id.phone_delete:
                String callNum = phoneCallNum.getText().toString();
                phoneCallNum.setText(callNum.substring(0, callNum.length() - 1));
                if (callNum.substring(0, callNum.length() - 1).isEmpty()) {
                    phoneTableTop.setVisibility(View.GONE);
                    phoneTable.setBackgroundResource(R.drawable.radius);
                }
                break;
        }
    }

    /*
    ?????????????????????????????????
     */
    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.phone_delete:
                phoneCallNum.setText("");
                phoneTableTop.setVisibility(View.GONE);
                phoneTable.setBackgroundResource(R.drawable.radius);
                break;
        }
        return true;
    }


    public void callNumShow() {
        if (phoneCallNum.getText().toString().isEmpty()) {
            phoneTableTop.setVisibility(View.VISIBLE);
            phoneTable.setBackgroundResource(R.color.margin);
        }
    }

    /*
    ???????????????
     */
    private void setRvEventListener() {
        phoneAdapter.setOnItemClickListener(new PhoneAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Calls calls = datas.get(position);
                Util.callPhone(context, calls.getPhoneNumber());
            }
        });
        phoneAdapter.setOnItemLongClickListener(new PhoneAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position) {
                List<Integer> list =new ArrayList<>();
                list.add(datas.get(position).getId());
                dbController.Delete_Calls(list);
                phoneAdapter.notifyItemRemoved(position);
            }
        });
        phoneAdapter.setOnButMoreClickListener(new PhoneAdapter.OnButMoreClickListener() {
            @Override
            public void OnButMoreClick(View view, int position) {
                Toast.makeText(getActivity(), "?????????more", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("contact_id", datas.get(position).getContact_id());

                intent.putExtra("number", datas.get(position).getPhoneNumber());
                startActivity(intent);

            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
//        dbController.DeleteCall();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }



}
