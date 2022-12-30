package com.example.wagbaproject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestDetailsAdapter extends RecyclerView.Adapter<RestDetailsAdapter.ViewHolder>
{

    private Button button;
    private List<RestDetailsModel> userList;
    DatabaseReference databaseReference;
    String text1, text2,text3, text4, image;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUser = mAuth.getCurrentUser().getUid();


    public RestDetailsAdapter(List<RestDetailsModel> userList)
    {
        this.userList = userList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rest_details, parent, false);
        return new ViewHolder(v);
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        private TextView textView1, textView2, textView3, textView4;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewResDetails);
            textView1 = itemView.findViewById(R.id.textView1ResDetails);
            textView2 = itemView.findViewById(R.id.textView2ResDetails);
            textView3 = itemView.findViewById(R.id.textView3ResDetails);
            button = itemView.findViewById(R.id.dishToCartBtn);

        }



        public void setData(String image, String text1, String text2, String text3)
        {
            Picasso.get().load(image).into(imageView);
            textView1.setText(text1);
            textView2.setText(text2);
            textView3.setText(text3);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        text1 = userList.get(position).getDishName();
        text2 = userList.get(position).getComponents();
        text3 = userList.get(position).getPrice();
        text4 = userList.get(position).getAvailability();
        image = userList.get(position).getImage();
        holder.setData(image, text1, text2, text3);

        button.setOnClickListener(new View.OnClickListener()    //this is the +cart button in each item
        {
            @Override
            public void onClick(View view)
            {
                pushDataToCart(holder);
            }
        });

    }


    private void pushDataToCart(ViewHolder holder)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference postsRef = databaseReference.child("users").child(currentUser).child("cart");

        DatabaseReference newPostRef = postsRef.push();

        newPostRef.setValue(new CartModel(userList.get(holder.getAbsoluteAdapterPosition()).getImage(),
                userList.get(holder.getAbsoluteAdapterPosition()).getDishName()
                ,userList.get(holder.getAbsoluteAdapterPosition()).getPrice(), "Ordered"));
    }



    @Override
    public int getItemCount() {
        return userList.size();
    }

}
