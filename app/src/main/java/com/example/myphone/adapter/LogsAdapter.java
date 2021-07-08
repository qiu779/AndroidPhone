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

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.LogsViewHolder> {

    private Context context;
    private List<Calls> data;

    //监听器
    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;
    OnButMoreClickListener onButMoreClickListener;

    public LogsAdapter(Context context, List<Calls> data){
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
    public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_log, parent, false);
        return new LogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogsAdapter.LogsViewHolder holder, int position) {
        Instant now = Instant.now();
        Calls calls = data.get(position);
        //显示通话日期格式的设置
        Date callDate = new Date(calls.getDate());
        String callDateStr = Util.getAllDateStr(callDate);
        //
        //显示通话状态及通话时间的格式设置
        int type = calls.getType();
        String callDuration = calls.getCallDuration();
        if (callDuration == null || "".equals(callDuration)){
            callDuration = "0";
        }
        int duration = Integer.parseInt(callDuration);
        String typeStr = Util.getTypeStr(type, duration);
        //
        holder.logNumber.setText(calls.getPhoneNumber());
        holder.logType.setText(typeStr);
        holder.logTime.setText(callDateStr);
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
//        holder.phonemore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onButMoreClickListener.OnButMoreClick(view, position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class LogsViewHolder extends RecyclerView.ViewHolder{

        TextView logNumber, logTime, logType, logCard;

        public LogsViewHolder(@NonNull View itemView) {
            super(itemView);
            logNumber = itemView.findViewById(R.id.logs_calltime);
            logTime = itemView.findViewById(R.id.logs_calltime);
            logType = itemView.findViewById(R.id.logs_type);
            logCard = itemView.findViewById(R.id.logs_card);
        }
    }
}

