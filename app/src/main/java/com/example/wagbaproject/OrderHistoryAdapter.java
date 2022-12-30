package com.example.wagbaproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>
{

    private List<OrderHistoryModel> orderHistoryList;

    public OrderHistoryAdapter(List<OrderHistoryModel>userList)
    {
        this.orderHistoryList = userList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        String text1 = orderHistoryList.get(position).getDishName();
        String text2 = orderHistoryList.get(position).getPrice();
        String text3 = orderHistoryList.get(position).getStatus();

        holder.setData(text1, text2, text3);
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView1, textView2, textView3;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView1 = itemView.findViewById(R.id.dishNameHistory);
            textView2 = itemView.findViewById(R.id.priceHistory);
            textView3 = itemView.findViewById(R.id.statusHistory);
        }

        public void setData(String text1, String text2, String text3)
        {
            textView1.setText(text1);
            textView2.setText(text2);
            textView3.setText(text3);
        }
    }
}
