package com.example.myphone.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.provider.ContactsContract;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphone.dao.DBController;
import com.example.myphone.dao.MyDataBaseHelper;
import com.example.myphone.R;
import com.example.myphone.adapter.ContactAdapter;
import com.example.myphone.pinyin.Cn2Spell;
import com.example.myphone.sideBar.SideBar;
import com.example.myphone.utils.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactTabFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "PhoneTabFragment";
    private static final String TABLE_NAME = "Contacts";


    private View root;
    private Context context;
    private List<Contacts> datas;
    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;
    private DBController dbController;
    private ContactAdapter contactAdapter;

    private SideBar sideBar;
    private RecyclerView contactRecycleView;
    private Button contactAdd;
    private SearchView contactSearch;
    private ImageButton contactCall;

    private LinearLayout viewIndex;
    private TextView alphabetIndex;

    /*
    获取Activity
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_contact,container,false);
        datas = new ArrayList<>();
        sideBar =new SideBar(context);
        dbController = new DBController(context);
        //获取本机原有的联系人
//        dbController.getLocalContacts();
        datas =  dbController.getContactRvItem();
        initView();
        initRecycleView();
        initIndex();
        return root;
    }

    private void initRecycleView() {
        contactRecycleView = root.findViewById(R.id.contact_recycle);
        contactAdapter = new ContactAdapter(context, datas);
        contactRecycleView.setAdapter(contactAdapter);
        contactRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setRvEventListener();
    }

    private void initView() {
        contactSearch = root.findViewById(R.id.contact_search);
        contactAdd = root.findViewById(R.id.contact_add);
        sideBar = root.findViewById(R.id.contact_sideBar);

    }
    /*
    对组件单击事件的监听
    */
    @Override
    public void onClick(View view) {

    }

    /*
    对组件长按事件的监听
     */
    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    /*
    设置RecycleViewItem的单击、长按监听，对其中的按钮进行监听
     */
    private void setRvEventListener(){
        contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

            }
        });
        contactAdapter.setOnItemLongClickListener(new ContactAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position) {

            }
        });
        contactAdapter.setOnItemCallClickListener(new ContactAdapter.OnItemCallClickListener() {
            @Override
            public void OnItemCallClick(View view, int position) {

            }
        });
    }

    ///////////////////////////////////////
    /*
    获取本地联系人数据库相关数据
     */
    public List<Contacts> initData(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//查询手机里所有的联系人

        //ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME 姓名
        //ContactsContract.CommonDataKinds.Phone.NUMBER 电话
        Cursor cursor = context.getContentResolver().query(uri,
                new String[] { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,"sort_key" }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String phone = cursor.getString(1);
                Contacts contact = new Contacts();
                contact.setName(name);
                contact.setPhoneNumber(phone);
                datas.add(contact);
            } while (cursor.moveToNext());
        }
        return datas;
    }

    public void initIndex(){
        viewIndex = root.findViewById(R.id.view_index);
        alphabetIndex = root.findViewById(R.id.alphabet_index);
        if (datas.size() > 0){
            Log.d("poyishi","poyishi");
            alphabetIndex.setText(Cn2Spell.getPinYinFirstLetter(datas.get(0).getName()));
            viewIndex.setVisibility(View.VISIBLE);
        }
    }

}
