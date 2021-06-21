package com.example.myphone.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphone.R;
import com.example.myphone.utils.Calls;
import com.example.myphone.utils.Contacts;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    private Context context;
    private List<Calls> data;
    int i = 0;
    int j = 0;

    //监听器
    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;
    OnButMoreClickListener onButMoreClickListener;

    public PhoneAdapter(Context context, List<Calls> data){
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnButMoreClickListener(OnButMoreClickListener onButMoreClickListener) {
        this.onButMoreClickListener = onButMoreClickListener;
    }

    /*
    * 对监听器接口的定义，单击和长按
    * */
    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        public void OnItemLongClick(View view, int position);
    }

    public interface OnButMoreClickListener{
        public void OnButMoreClick(View view, int position);
    }



    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_phone, parent, false);
        j++;
        System.out.println(j);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.PhoneViewHolder holder, int position) {
        Instant now = Instant.now();
        Calls calls = data.get(position);
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date_today = sdf.format(new Date(System.currentTimeMillis()));
        Date callDate = new Date(calls.getDate());
        String callDateStr = sdf.format(callDate);
        if (date_today.equals(callDateStr)){                           //如果为当天则显示时间
            sdf = new SimpleDateFormat("HH:mm");
            callDateStr = sdf.format(callDate);
        } else if (date_today.contains(callDateStr.substring(0, 4))){  //如果为当年则不显示年份
            sdf = new SimpleDateFormat("MM-dd");
            callDateStr = sdf.format(callDate);
        }
        //
        //
        int duration = Integer.parseInt(calls.getCallDuration());
        String durationStr = "";
        int hour = duration / 3600;
        int min = duration / 60;
        int sec = duration % 60;
        if (hour != 0){
            durationStr = hour + "时";
        }
        if (min != 0){
            durationStr = min + "分";
        }
        if (sec != 0){
            durationStr = sec + "秒";
        }
        //
        //
        int type = calls.getType();
        String typeStr = "";
        switch (type){
            case CallLog.Calls.INCOMING_TYPE:
                typeStr = "呼入"+durationStr;
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                if (duration != 0){
                    typeStr = "呼出" + durationStr;
                }else{
                    typeStr = "未接通";
                }
                break;
            case CallLog.Calls.MISSED_TYPE:
                typeStr = "未接，响铃..";
                break;
            case CallLog.Calls.BLOCKED_TYPE:
                typeStr = "阻止";
                break;
            case CallLog.Calls.REJECTED_TYPE:
                typeStr = "已挂断";
                break;
            case CallLog.Calls.ANSWERED_EXTERNALLY_TYPE:
                typeStr = "已接";
                break;
        }
        //
        holder.phonename.setText(calls.getName());
        holder.phonenumber.setText(calls.getPhoneNumber());
        holder.phonestate.setText(typeStr);
        holder.phonetime.setText(callDateStr);
        holder.phonecategory.setText(calls.getCategory());
        holder.phonearea.setText(calls.getArea());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.OnItemLongClick(v,position);
                return true;
            }
        });
        holder.phonemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Instant now2 = Instant.now();
        Log.d("时间55",String.valueOf(Duration.between(now, now2).toMillis()));
        i++;
        System.out.println(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder{
        ImageView phonepeople;
        TextView phonename, phonenumber, phonecategory, phonetime, phonestate, phonearea;
        ImageButton phonemore;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            phonename = itemView.findViewById(R.id.phone_name);
            phonepeople = itemView.findViewById(R.id.phone_people);
            phonenumber = itemView.findViewById(R.id.phone_number);
            phonecategory = itemView.findViewById(R.id.phone_category);
            phonetime = itemView.findViewById(R.id.phone_time);
            phonestate = itemView.findViewById(R.id.phone_state);
            phonearea = itemView.findViewById(R.id.phone_area);
            phonemore = itemView.findViewById(R.id.phone_more);
        }
    }
}
