package com.example.myphone.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myphone.R;
import com.example.myphone.dao.DBController;
import com.example.myphone.mode.Calls;
import com.example.myphone.mode.Contacts;
import com.example.myphone.myView.EditTextWithDel;
import com.example.myphone.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
        private String TAG = "DetailsActivity";

        private ImageButton back, newMore, call1, call2, send1 , send2, vedio, collect, edit, blackList, del;
        private ImageView picture;
        private Button moreLog, qrCode;
        private TextView Name, Number1, Number2, email, birthday, address, more, callTime1, callTime2,
                callTime3, callNumber1, callNumber2, callNumber3, callType1, callType2, callType3, callCard1, callCard2, callCard3, group, collectText, blackText;
        private LinearLayout top, phoneView1, phoneView2, emailView, birthdayView, addressView, moreView;
        private RelativeLayout logView1, logView2, logView3;

        private DBController dbController;
        private Contacts contacts;

        private int isBlackList;
        private int cId;
        private String number1;
        private String number2;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contactdetail);
            initView();
            dbController = new DBController(this);
            Intent intent = this.getIntent();
            cId = intent.getIntExtra("contact_id", 0);
            String number = intent.getStringExtra("number");
            //判断是否为本地联系人
            if (cId == 0) {
                //不是本地联系人
                List<Calls> cList = new ArrayList<>();
                //获取联系人最近的通话记录（三条）
                cList = dbController.getFCallLogs(number);
                int listsize = cList.size();
                Name.setText(number);
                Number1.setText(number);
                //判断需要显示多少条通话记录数据
                if (listsize == 0) {
                    logView1.setVisibility(View.GONE);
                    logView2.setVisibility(View.GONE);
                    logView3.setVisibility(View.GONE);
                }
                if (listsize > 0) {
                    logView1.setVisibility(View.VISIBLE);
                    Calls calls = cList.get(0);
                    callNumber1.setText(calls.getPhoneNumber());
                    Date date1 = new Date(calls.getDate());
                    callTime1.setText(Util.getAllDateStr(date1));
                    String duration1 = calls.getCallDuration();
                    if (duration1 == null || "".equals(duration1)) {
                        duration1 = "0";
                    }
                    callType1.setText(Util.getTypeStr(calls.getType(), Integer.parseInt(duration1)));
                    moreLog.setVisibility(View.VISIBLE);
                }
                if (listsize > 1) {
                    logView2.setVisibility(View.VISIBLE);
                    Calls calls = cList.get(1);
                    callNumber2.setText(calls.getPhoneNumber());
                    Date date2 = new Date(calls.getDate());
                    callTime2.setText(Util.getAllDateStr(date2));
                    String duration2 = calls.getCallDuration();
                    if (duration2 == null|| "".equals(duration2)) {
                        duration2 = "0";
                    }
                    callType2.setText(Util.getTypeStr(calls.getType(), Integer.parseInt(duration2)));
                }
                if (listsize > 2) {
                    logView3.setVisibility(View.VISIBLE);
                    Calls calls = cList.get(2);
                    callNumber3.setText(calls.getPhoneNumber());
                    Date date3 = new Date(calls.getDate());
                    callTime3.setText(Util.getAllDateStr(date3));
                    String duration3 = calls.getCallDuration();
                    if (duration3 == null|| "".equals(duration3)) {
                        duration3 = "0";
                    }
                    callType3.setText(Util.getTypeStr(calls.getType(), Integer.parseInt(duration3)));
                }
            } else {
                //是本地联系人
                List<Calls> cList = new ArrayList<>();
                //获取联系人的信息
                contacts = dbController.getContact(cId);
                //获取联系人最近的通话记录（三条）
                cList = dbController.getCallLogs(cId);
                //装配数据
                Name.setText(contacts.getName());
                number1 = contacts.getPhoneNumber();
                number2 = contacts.getPhoneNumber2();
                String emailtext = contacts.getEmail();
                String birthdaytext = contacts.getBirthDay();
                String addresstext = contacts.getAddress();
                String moretext = contacts.getMore();
                isBlackList = contacts.getBlacklist();
                int listsize = cList.size();
                //判断是否要显示电话、邮箱、生日、地址、备注
                if (number1 != null && !"".equals(number1)) {
                    phoneView1.setVisibility(View.VISIBLE);
                    Number1.setText(number1);
                }
                if (number2 != null && !"".equals(number2)) {
                    phoneView2.setVisibility(View.VISIBLE);
                    Number2.setText(number2);
                }
                if (emailtext != null && !"".equals(emailtext)) {
                    emailView.setVisibility(View.VISIBLE);
                    email.setText(emailtext);
                }
                if (birthdaytext != null && !"".equals(birthdaytext)) {
                    birthdayView.setVisibility(View.VISIBLE);
                    birthday.setText(birthdaytext);
                }
                if (addresstext != null && !"".equals(addresstext)) {
                    addressView.setVisibility(View.VISIBLE);
                    address.setText(addresstext);
                }
                if (moretext != null && !"".equals(moretext)) {
                    moreView.setVisibility(View.VISIBLE);
                    more.setText(moretext);
                }
//                //判断需要显示多少条通话记录数据
                if (listsize == 0) {
                    logView1.setVisibility(View.GONE);
                    logView2.setVisibility(View.GONE);
                    logView3.setVisibility(View.GONE);
                }
                if (listsize > 0) {
                    logView1.setVisibility(View.VISIBLE);
                    Calls calls = cList.get(0);
                    callNumber1.setText(calls.getPhoneNumber());
                    Date date1 = new Date(calls.getDate());
                    callTime1.setText(Util.getAllDateStr(date1));
                    String duration1 = calls.getCallDuration();
                    if (duration1 == null|| "".equals(duration1)) {
                        duration1 = "0";
                    }
                    callType1.setText(Util.getTypeStr(calls.getType(), Integer.parseInt(duration1)));
                    moreLog.setVisibility(View.VISIBLE);
                }
                if (listsize > 1) {
                    logView2.setVisibility(View.VISIBLE);
                    Calls calls = cList.get(1);
                    callNumber2.setText(calls.getPhoneNumber());
                    Date date2 = new Date(calls.getDate());
                    callTime2.setText(Util.getAllDateStr(date2));
                    String duration2 = calls.getCallDuration();
                    if (duration2 == null|| "".equals(duration2)) {
                        duration2 = "0";
                    }
                    callType2.setText(Util.getTypeStr(calls.getType(), Integer.parseInt(duration2)));
                }
                if (listsize > 2) {
                    logView3.setVisibility(View.VISIBLE);
                    Calls calls = cList.get(2);
                    callNumber3.setText(calls.getPhoneNumber());
                    Date date3 = new Date(calls.getDate());
                    callTime3.setText(Util.getAllDateStr(date3));
                    String duration3 = calls.getCallDuration();
                    if (duration3 == null|| "".equals(duration3)) {
                        duration3 = "0";
                    }
                    callType3.setText(Util.getTypeStr(calls.getType(), Integer.parseInt(duration3)));
                }

                //判断是否为收藏、黑名单
                if (isBlackList == -1) {
                    //黑名单人员
                    blackList.setImageDrawable(getResources().getDrawable(R.drawable.ic_yichuheimingdan));
                    blackText.setText("移出黑名单");
                } else if (isBlackList == 1) {
                    //收藏人员
                    collect.setImageDrawable(getResources().getDrawable(R.drawable.ic_shoucang_copy));
                    collectText.setText("取消收藏");

                }
                Log.d(TAG, "onCreate");
            }
        }

        private void initView(){
            back = findViewById(R.id.detail_back);
            newMore = findViewById(R.id.detail_new);
            call1 = findViewById(R.id.detail_call1);
            call2 = findViewById(R.id.detail_call2);
            send1 = findViewById(R.id.detail_send1);
            send2 = findViewById(R.id.detail_send2);
            collect = findViewById(R.id.detail_collect);
            edit = findViewById(R.id.detail_edit);
            blackList = findViewById(R.id.detail_blacklist);
            del = findViewById(R.id.detail_del);
            picture = findViewById(R.id.detail_picture);
            moreLog = findViewById(R.id.detail_morelog);
            qrCode = findViewById(R.id.detail_qrcode);
            Name = findViewById(R.id.detail_name);
            Number1 = findViewById(R.id.detail_number1);
            Number2 = findViewById(R.id.detail_number2);
            email = findViewById(R.id.detail_email);
            birthday = findViewById(R.id.detail_birthday);
            address = findViewById(R.id.detail_address);
            more = findViewById(R.id.detail_more);
            callTime1 = findViewById(R.id.detail_calltime1);
            callTime2 = findViewById(R.id.detail_calltime2);
            callTime3 = findViewById(R.id.detail_calltime3);
            callNumber1 = findViewById(R.id.detail_callnumber1);
            callNumber2 = findViewById(R.id.detail_callnumber2);
            callNumber3 = findViewById(R.id.detail_callnumber3);
            callType1 = findViewById(R.id.detail_calltype1);
            callType2 = findViewById(R.id.detail_calltype2);
            callType3 = findViewById(R.id.detail_calltype3);
            callCard1 = findViewById(R.id.detail_callcard1);
            callCard2 = findViewById(R.id.detail_callcard2);
            callCard3 = findViewById(R.id.detail_callcard3);
            group = findViewById(R.id.detail_group);
            collectText = findViewById(R.id.collect_text);
            blackText = findViewById(R.id.blacklist_text);
            top = findViewById(R.id.detail_top);
            phoneView1 = findViewById(R.id.detail_phone_view1);
            phoneView2 = findViewById(R.id.detail_phone_view2);
            emailView = findViewById(R.id.detail_email_view);
            addressView = findViewById(R.id.detail_address_view);
            birthdayView = findViewById(R.id.detail_birthday_view);
            moreView = findViewById(R.id.detail_more_view);
            logView1 = findViewById(R.id.detail_log_view1);
            logView2 = findViewById(R.id.detail_log_view2);
            logView3 = findViewById(R.id.detail_log_view3);

            //设置监听器
            back.setOnClickListener(this);
            newMore.setOnClickListener(this);
            call1.setOnClickListener(this);
            call2.setOnClickListener(this);
            send1.setOnClickListener(this);
            send2.setOnClickListener(this);
            collect.setOnClickListener(this);
            edit.setOnClickListener(this);
            blackList.setOnClickListener(this);
            del.setOnClickListener(this);
            picture.setOnClickListener(this);
            moreLog.setOnClickListener(this);
            qrCode.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.detail_back:
//                Intent intent = new Intent(AddActivity.this, MainActivity.class);
//                startActivity(intent);
                    finish();
                    break;
                case R.id.detail_new:
                    break;
                case R.id.detail_call1:
                    break;
                case R.id.detail_call2:
                    break;
                case R.id.detail_send1:
                    sendMessage(number1);
                    break;
                case R.id.detail_send2:
                    sendMessage(number2);
                    break;
//                case R.id.detail_vedio:
//                    break;
                case R.id.detail_collect:
                    if (isBlackList != 1){
                        Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                        collect.setImageDrawable(getResources().getDrawable(R.drawable.ic_shoucang_copy));
                        blackList.setImageDrawable(getResources().getDrawable(R.drawable.ic_heimingdan));
                        blackText.setText("加入黑名单");
                        collectText.setText("取消收藏");
                        isBlackList = 1;
                    } else{
                        Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
                        collect.setImageDrawable(getResources().getDrawable(R.drawable.ic_shoucang));
                        collectText.setText("收藏");
                        isBlackList = 0;
                    }
                    break;
                case R.id.detail_edit:
                    Intent intent1 = new Intent(this,AddActivity.class);
                    intent1.putExtra("id", cId);
                    startActivity(intent1);
                    break;
                case R.id.detail_blacklist:
                    if (isBlackList != -1){
                        Toast.makeText(this,"加入黑名单",Toast.LENGTH_SHORT).show();
                        collect.setImageDrawable(getResources().getDrawable(R.drawable.ic_shoucang));
                        blackList.setImageDrawable(getResources().getDrawable(R.drawable.ic_yichuheimingdan));
                        blackText.setText("移出黑名单");
                        collectText.setText("收藏");
                        isBlackList = -1;
                    } else{
                        Toast.makeText(this,"移出黑名单",Toast.LENGTH_SHORT).show();
                        blackList.setImageDrawable(getResources().getDrawable(R.drawable.ic_heimingdan));
                        blackText.setText("加入黑名单");
                        isBlackList = 0;
                    }
                    break;
                case R.id.detail_del:
                    List<Integer> idList = new ArrayList();
                    idList.add(cId);
                    dbController.Delete_contact(idList);
                    finish();
                    break;
                case R.id.detail_picture:
                    break;
                case R.id.detail_morelog:
                    Intent intentLogs = new Intent(this,AllLogsActivity.class);
                    intentLogs.putExtra("contact_id", cId);
                    startActivity(intentLogs);
                    break;
                case R.id.detail_qrcode:
                    break;
            }
        }

    private void sendMessage(String number) {
        Uri smsToUri = Uri.parse("smsto:" + number);
        Intent intentSMS = new Intent(Intent.ACTION_SENDTO,smsToUri);
        startActivity(intentSMS);
    }

    @Override
        protected void onStop() {
            super.onStop();
            dbController.Update_blackList(cId, isBlackList);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
