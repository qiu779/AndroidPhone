package com.example.myphone.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphone.R;
import com.example.myphone.utils.Contacts;

import org.w3c.dom.Text;

import java.util.List;

public class            ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contacts> data;
    private Context context;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemCallClickListener onItemCallClickListener;

    public ContactAdapter(Context context, List<Contacts> data){
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
        Contacts contact = data.get(position);
        holder.contactname.setText(contact.getName());

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

        TextView contactname;
        ImageButton contactcall;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactname = itemView.findViewById(R.id.contact_name);
            contactcall = itemView.findViewById(R.id.contact_call);
        }
    }

}
