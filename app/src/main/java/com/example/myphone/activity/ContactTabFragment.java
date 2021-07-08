package com.example.myphone.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphone.utils.MobileNumberUtils;
import com.example.myphone.dao.DBController;
import com.example.myphone.dao.MyDataBaseHelper;
import com.example.myphone.R;
import com.example.myphone.adapter.ContactAdapter;
import com.example.myphone.myView.EditTextWithDel;
import com.example.myphone.utils.Util;
import com.example.myphone.utils.Cn2Spell;
import com.example.myphone.utils.PinyinComparator;
import com.example.myphone.myView.SideBar;
import com.example.myphone.mode.NameSortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactTabFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "ContactTabFragment";
    private static final String TABLE_NAME = "Contacts";


    private View root;
    private Context context;
    private List<NameSortModel> datas;
    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;
    private DBController dbController;
    private ContactAdapter contactAdapter;

    private SideBar sideBar;
    private RecyclerView contactRecycleView;
    private Button contactAdd;
    private EditTextWithDel contactSearch;
    private ImageButton contactCall;

    private LinearLayout viewIndex;
    private TextView alphabetIndex;
    private TextView showIndexLetter;

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

        datas =  dbController.getContactRvItem();
//        updateCarrierAndArea();
        data2Spell();
        Collections.sort(datas, new PinyinComparator());
        initView();
        sideBar.setmTextView(showIndexLetter);
        sideBarListener();
        initRecycleView();
        contactSearch.setHint(new SpannableString("搜索" + datas.size() + "位联系人"));
        Log.d(TAG,"onCreateView");
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (datas.size() != dbController.getContactRvItemCount()){
            datas.clear();
            datas.addAll(dbController.getContactRvItem());
            data2Spell();
            Collections.sort(datas, new PinyinComparator());
            contactSearch.setHint(new SpannableString("搜索" + datas.size() + "位联系人"));
            contactAdapter.notifyDataSetChanged();
        }
        Log.d(TAG,"onStart");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
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
        showIndexLetter = root.findViewById(R.id.show_index_letter);

        contactAdd.setOnClickListener(this);
    }
    /*
    对组件单击事件的监听
    */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.contact_add:
                Intent intent =new Intent(context, AddActivity.class);
                startActivity(intent);

        }
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
                int temp = datas.get(position).getId();
                Log.d(TAG, "temp = " + String.valueOf(temp));
                dbController.getContact(temp);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("contact_id" , temp);
                startActivity(intent);
            }
        });
        contactAdapter.setOnItemLongClickListener(new ContactAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position) {
                List<Integer> list =new ArrayList<>();
                list.add(datas.get(position).getId());
                dbController.Delete_contact(list);
                contactAdapter.notifyItemRemoved(position);
            }
        });
        contactAdapter.setOnItemCallClickListener(new ContactAdapter.OnItemCallClickListener() {
            @Override
            public void OnItemCallClick(View view, int position) {
                Util.callPhone(context, datas.get(position).getPhonenumber());
            }
        });
    }

    ///////////////////////////////////////

    /*
    将名字转换成拼音并提出第一个字的大写存到数据中返回
     */
    private void data2Spell(){
        List<NameSortModel> nSortList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++){
            String name = datas.get(i).getName().replace(" ","");
            String sortString = Cn2Spell.getPinYinFirstLetter(name).toUpperCase();
            if (sortString.matches("[A-Z]")){
                datas.get(i).setFirstLetter(sortString);
            }else{
                datas.get(i).setFirstLetter("#");
            }
        }
    }

    /*
    sideBar监听器的实现
     */
    private void sideBarListener(){
        sideBar.setOnTouchingLetterListener(new SideBar.OnTouchingLetterListener() {
            @Override
            public void onTouchingLetterListener(String s) {
                int position = contactAdapter.getPositionForSection(s.charAt(0));
                if (position != -1){
                    contactRecycleView.scrollToPosition(position);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) contactRecycleView.getLayoutManager();
                    linearLayoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
    }

    /*
    更新运营商和归属地
     */

    private void updateCarrierAndArea(){
        for (int i = 0; i < datas.size(); i++){
            NameSortModel nameSortModel = datas.get(i);
            NameSortModel temp =new NameSortModel();
            int id = nameSortModel.getId();
            temp = dbController.getPhoneNumberAnd2(id);
            nameSortModel.setPhonenumber(temp.getPhonenumber());
            nameSortModel.setPhonenumber2(temp.getPhonenumber2());
            String number1 = nameSortModel.getPhonenumber();
            String number2 = nameSortModel.getPhonenumber2();
            //对号码 1 的运营商和归属地的获取并存入数据库
            if (!"".equals(number1)){
                String netName = MobileNumberUtils.getNetName(number1, 86);
                String area = MobileNumberUtils.getArea(number1);
                if (area == null) {
                    area = "";
                }else{
                    if (area.indexOf("省") > -1){
                        area = area.replace("省","");
                    }
                    if (area.indexOf("市") > -1){
                        area = area.replace("市","");
                    }
                }
                dbController.UpdateNum_netName(id, netName);
                dbController.UpdateNum_Area(id, area);
            }
            //对号码 1 的运营商和归属地的获取并存入数据库
            if (!"".equals(number2)){
                String netName = MobileNumberUtils.getNetName(number1, 86);
                String area = MobileNumberUtils.getArea(number1);
                if (area.indexOf("省") > -1){
                    area = area.replace("省","");
                }
                if (area.indexOf("市") > -1){
                    area = area.replace("市","");
                }
                dbController.UpdateNum_netName2(id, netName);
                dbController.UpdateNum_Area2(id, area);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
    }
}
