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

import com.example.myphone.mode.Contacts;
import com.example.myphone.utils.MobileNumberUtils;
import com.example.myphone.R;
import com.example.myphone.dao.DBController;
import com.example.myphone.myView.EditTextWithDel;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "AddActivity";

    private Button addCancel, addCommit, addQrCode, addBirthdayCancel, addBirthdayCommit;
    private ImageView addPicture;
    private EditTextWithDel addName, addNumber1, addNumber2, addEmail, addAddress, addMore, addBirthday;
    private DatePicker addBirthdayPick;
    private TextView addGroupName;
    private LinearLayout addGroupChoose;
    private RelativeLayout addBirthdayPickView;

    private DBController dbController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
        dbController = new DBController(this);
        initView();
    }

    private void initView(){
        addCancel = findViewById(R.id.add_cancel);
        addCommit = findViewById(R.id.add_commit);
        addQrCode = findViewById(R.id.add_qrcode);
        addPicture = findViewById(R.id.add_picture);
        addName = findViewById(R.id.add_name);
        addNumber1 = findViewById(R.id.add_number1);
        addNumber2 = findViewById(R.id.add_number2);
        addEmail = findViewById(R.id.add_email);
        addAddress = findViewById(R.id.add_address);
        addMore = findViewById(R.id.add_more);
        addBirthday = findViewById(R.id.add_birthday);
        addGroupChoose = findViewById(R.id.add_groupchoose);
        addGroupName =findViewById(R.id.add_groupname);
        addBirthdayPick = findViewById(R.id.add_birthday_pick);
        addBirthdayPickView =findViewById(R.id.add_birthday_pivkview);
        addBirthdayCancel =findViewById(R.id.add_birthday_cancel);
        addBirthdayCommit =findViewById(R.id.add_birthday_commit);

        //设置监听器
        addCancel.setOnClickListener(this);
        addCommit.setOnClickListener(this);
        addQrCode.setOnClickListener(this);
        addPicture.setOnClickListener(this);
        addBirthday.setOnClickListener(this);
        addGroupChoose.setOnClickListener(this);
        addBirthdayCancel.setOnClickListener(this);
        addBirthdayCommit.setOnClickListener(this);

        addBirthday.setInputType(InputType.TYPE_NULL);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_cancel:
//                Intent intent = new Intent(AddActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
                break;
            case R.id.add_commit:
//                Intent intent = new Intent(this, DetailsActivity.class);
                String name = addName.getText().toString();
                if (name == null){
                    name = "";
                }
                String Number1 = addNumber1.getText().toString();
                String netName1 = "";
                String area1 = "";
                if (Number1 == null){
                    Number1 = "";
                }else {
                    netName1 = MobileNumberUtils.getNetName(Number1, 86);
                    area1 = MobileNumberUtils.getArea(Number1);
                }
                if (netName1 == null){
                    netName1 = "";
                }
                if (area1 == null){
                    area1 = "";
                }
                String Number2 = addNumber2.getText().toString();
                String netName2 = "";
                String area2 = "";
                if (Number2 == null){
                    Number2 = "";
                }else {
                    netName2 = MobileNumberUtils.getNetName(Number2, 86);
                    area2 = MobileNumberUtils.getArea(Number2);
                }
                if (netName2 == null){
                    netName2 = "";
                }
                if (area2 == null){
                    area2 = "";
                }
                String Email = addEmail.getText().toString();
                if (Email == null){
                    Email = "";
                }
                String Birthday = addBirthday.getText().toString();
                if (Birthday == null){
                    Birthday = "";
                }
                String Address = addAddress.getText().toString();
                if (Address == null){
                    Address = "";
                }
                String More = addMore.getText().toString();
                if (More == null){
                    More = "";
                }
                String Group = addGroupName.getText().toString();
                if (Group == null || "群组名称".equals(Group)){
                    Group = "";
                }
                if ("".equals(name) && "".equals(Number1) && "".equals(Number2) && "".equals(Email) && "".equals(Birthday) && "".equals(Address) && "".equals(More)
                        && "".equals(Group)){
                    Toast.makeText(this, "无法保存空联系人" ,Toast.LENGTH_SHORT).show();
                    break;
                }
//                intent.putExtra("Name", name);
//                intent.putExtra("Number1", Number1);
//                intent.putExtra("Number2", Number2);
//                intent.putExtra("Email", Email);
//                intent.putExtra("Birthday", Birthday);
//                intent.putExtra("Address", Address);
//                intent.putExtra("More", More);
//                intent.putExtra("Group", Group);
//                intent.putExtra("netName1", netName1);
//                intent.putExtra("area1", area1);
//                intent.putExtra("netName2", netName2);
//                intent.putExtra("area2", area2);
                Toast.makeText(this ,"此联系人已保存",Toast.LENGTH_SHORT).show();
                Contacts contacts = new Contacts(0, Number1, Number2, netName1, area1, netName2, area2, name, Email, Birthday, Address, Group, More, 0);
                dbController.InsertContact(contacts);
                finish();
//                startActivity(intent);
                break;
            case R.id.add_picture:
                break;
            case R.id.add_qrcode:
                break;
            case R.id.add_birthday:
                addBirthdayPickView.setVisibility(View.VISIBLE);
                break;
            case R.id.add_groupchoose:
                break;
            case R.id.add_birthday_cancel:
                addBirthdayPickView.setVisibility(View.GONE);
                break;
            case R.id.add_birthday_commit:
                int year = addBirthdayPick.getYear();
                int month = addBirthdayPick.getMonth();
                int day = addBirthdayPick.getDayOfMonth();
                addBirthday.setText(year + "/" + month + "/" + day);
                addBirthdayPickView.setVisibility(View.GONE);
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
