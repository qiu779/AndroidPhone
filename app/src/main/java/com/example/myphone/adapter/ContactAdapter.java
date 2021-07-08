package com.example.myphone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphone.R;
import com.example.myphone.mode.NameSortModel;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements SectionIndexer {

    private List<NameSortModel> data;
    private Context context;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemCallClickListener onItemCallClickListener;

    public ContactAdapter(Context context, List<NameSortModel> data){
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(ContactAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(ContactAdapter.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemCallClickListener(ContactAdapter.OnItemCallClickListener onItemCallClickListener) {
        this.onItemCallClickListener = onItemCallClickListener;
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

    public interface OnItemCallClickListener{
        public void OnItemCallClick(View view, int position);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.recycle_contact,parent,false);
        return new ContactViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        NameSortModel nameSortModel = data.get(position);
        String firstLetter = nameSortModel.getFirstLetter();
        if (position == 0) {
            holder.viewindex.setVisibility(View.VISIBLE);
            holder.alphabetindex.setText((firstLetter));
        }
        //若不是，需判断当前item首字母与上一个item首字母是否一致，再设置组view
        else {
            String preFirstLetter = data.get(position - 1).getFirstLetter();
            if (!preFirstLetter.equals(firstLetter)) {
                holder.viewindex.setVisibility(View.VISIBLE);
                holder.alphabetindex.setText((firstLetter));
            } else {
                //若与上一个item首字母一致则不需要重复设置组view
                holder.viewindex.setVisibility(View.GONE);
            }
        }

        holder.contactname.setText(nameSortModel.getName());

        //绑定点击， 点击后调用自己写的相关接口
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClick(view, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.OnItemLongClick(view, position);
                return true;
            }
        });
        holder.contactcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemCallClickListener.OnItemCallClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView contactname, alphabetindex;
        ImageButton contactcall;
        RelativeLayout viewindex;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactname = itemView.findViewById(R.id.contact_name);
            contactcall = itemView.findViewById(R.id.contact_call);
            viewindex = itemView.findViewById(R.id.view_index);
            alphabetindex = itemView.findViewById(R.id.alphabet_index);
        }
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++){
            String sortStr = data.get(i).getFirstLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return data.get(position).getFirstLetter().charAt(0);
    }

}
