package com.example.myphone.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder> {
    @NonNull
    @Override
    public CallLogAdapter.CallLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogAdapter.CallLogViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CallLogViewHolder extends RecyclerView.ViewHolder {
        public CallLogViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
