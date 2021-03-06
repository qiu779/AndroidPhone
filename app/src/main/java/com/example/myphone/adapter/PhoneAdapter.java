package com.example.myphone.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphone.R;
import com.example.myphone.mode.Calls;
import com.example.myphone.utils.Util;

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
        //显示通话日期格式的设置
        Date callDate = new Date(calls.getDate());
        String callDateStr = Util.getDateStr(callDate);
        //
        //显示通话状态及通话时间的格式设置
        int type = calls.getType();
        int duration = 0;
        String temp2= calls.getCallDuration();
        if (temp2 == null){
            temp2 = "0";
        }
        String temp3= "0";
        if (temp2.length()>10){
           temp3 =temp2.substring(0,10);
            duration = Integer.parseInt(temp3);
        }else{
            duration =Integer.parseInt(temp2);
        }
        String typeStr = Util.getTypeStr(type, duration);
        //
        holder.phonename.setText(calls.getName());
        holder.phonenumber.setText(calls.getPhoneNumber());
        holder.phonestate.setText(typeStr);
        holder.phonetime.setText(callDateStr);
        holder.phonecategory.setText(calls.getNetName());
        holder.phonearea.setText(calls.getArea());
        holder.phonecarrier.setText(calls.getNetName());
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
                onButMoreClickListener.OnButMoreClick(view, position);
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
        TextView phonename, phonenumber, phonecategory, phonetime, phonestate, phonearea, phonecarrier;
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
            phonecarrier = itemView.findViewById(R.id.phone_carrier);
        }
    }
}
