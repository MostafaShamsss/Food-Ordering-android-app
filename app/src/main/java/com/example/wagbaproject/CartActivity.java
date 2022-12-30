package com.example.wagbaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagbaproject.databinding.ActivityCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<CartModel> cartList;
    TextView textView1, textView2;
    ImageView imageView1;
    CartAdapter adapter;
    private ActivityCartBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUser = mAuth.getCurrentUser().getUid();
    //private boolean isPushed =false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        cartList = new ArrayList<>();


        textView1 = findViewById(R.id.textViewCart1);
        textView2 = findViewById(R.id.textViewCart3);
        imageView1 = findViewById(R.id.imageViewCart1);

        getCartFirebase();      //get cart info

        binding.makePaymentBtn.setOnClickListener(new View.OnClickListener()        //go to payment page
        {
            @Override
            public void onClick(View view)
            {
                pushDataToOrders(cartList);
                removeCartInfo();
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });
    }


    private void removeCartInfo()
    {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference cartRef = db.child("users").child(currentUser).child("cart");
        cartRef.setValue(null);
    }




    private void getCartFirebase()
    {
        databaseReference.child("users").child(currentUser).child("cart").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                try {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        cartList.add(postSnapshot.getValue(CartModel.class));
                    }
                    initRecyclerView();
                }

                catch (Exception e)
                {
                    Toast.makeText(CartActivity.this, "Cart getCartFirebase! " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Log.w("RT DB", "Failed to read value.", error.toException());
            }
        });
    }



    private void pushDataToOrders(List<CartModel> cartList)
    {
        DatabaseReference postsRef = databaseReference.child("users").child(currentUser).child("orders");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(cartList);

        /*isPushed = false;
        postsRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    for (int i = 0; i < postSnapshot.getChildrenCount(); i++)
                    {
                        if (!postSnapshot.child(String.valueOf(i)).child("status").getValue().toString().matches("Delivered")
                                ||postsRef==null)
                        {
                            isPushed = true;    //cart can be removed now
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Log.w("RT DB", "Failed to read value.", error.toException());
            }
        });
        if(isPushed)
        {
            DatabaseReference newPostRef = postsRef.push();
            newPostRef.setValue(cartList);
        }*/
    }


    private void initRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerViewCart);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(cartList);
        recyclerView.setAdapter(adapter);
    }
}