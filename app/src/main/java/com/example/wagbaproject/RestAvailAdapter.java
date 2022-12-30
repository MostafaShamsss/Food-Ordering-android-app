package com.example.wagbaproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestAvailAdapter extends RecyclerView.Adapter<RestAvailAdapter.ViewHolder>
{

    private final RestAvailInterface restAvailInterface;

    private List<RestAvailModel> userList;

    public RestAvailAdapter(List<RestAvailModel>userList, RestAvailInterface restAvailInterface)
    {
        this.userList = userList;
        this.restAvailInterface = restAvailInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rest_avail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String image = userList.get(position).getImage();
        String restName = userList.get(position).getRestName();
        String kindOfFood = userList.get(position).getKindOfFood();
        ArrayList<RestDetailsModel> dishList = userList.get(position).getDishList();

        holder.setData(image, restName, kindOfFood, dishList);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView imageView;
        private TextView textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewResAvail);
            textView1 = itemView.findViewById(R.id.textView1ResAvail);
            textView2 = itemView.findViewById(R.id.textView2ResAvail);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(restAvailInterface != null)
                    {
                        int pos = getAbsoluteAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION)
                        {
                            restAvailInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

        public void setData(String image, String text1, String text2, ArrayList<RestDetailsModel> dishList)
        {
            Picasso.get().load(image).into(imageView);
            textView1.setText(text1);
            textView2.setText(text2);
        }
    }
}
