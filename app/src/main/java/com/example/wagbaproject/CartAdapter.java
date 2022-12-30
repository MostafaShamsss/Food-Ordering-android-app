package com.example.wagbaproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
{
    private List<CartModel> cartList;


    public CartAdapter(List<CartModel>userList)
    {
        this.cartList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart, parent, false);
        return new ViewHolder(v);
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        private TextView textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCart1);
            textView1 = itemView.findViewById(R.id.textViewCart1);
            textView2 = itemView.findViewById(R.id.textViewCart3);
        }

        public void setData(String image, String text1, String text2)
        {
            Picasso.get().load(image).into(imageView);
            textView1.setText(text1);
            textView2.setText(text2);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        String image = cartList.get(position).getImage();
        String text1 = cartList.get(position).getDishName();
        String text2 = cartList.get(position).getPrice();

        holder.setData(image, text1, text2);

    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }


}
