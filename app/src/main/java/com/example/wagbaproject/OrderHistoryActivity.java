package com.example.wagbaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wagbaproject.databinding.ActivityOrderHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<OrderHistoryModel> orderHistoryList;
    OrderHistoryAdapter adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ActivityOrderHistoryBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUser = mAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getCartFirebase();
    }

    private void getCartFirebase()
    {
        orderHistoryList = new ArrayList<>();

        databaseReference.child("users").child(currentUser).child("orders").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                try {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        for(int i=0; i< postSnapshot.getChildrenCount(); i++)
                        {
                            //to place the orders whose status is delivered in the order history activity
                            //status of the order is by default preparing and it is controlled by the web application of the restaurants
                            if(postSnapshot.child(String.valueOf(i)).child("status").getValue().toString().matches("Delivered"))
                            {
                                orderHistoryList.add(postSnapshot.getValue(OrderHistoryModel.class));
                            }
                        }
                    }
                    initRecyclerView();
                }

                catch (Exception e)
                {
                    Toast.makeText(OrderHistoryActivity.this, "Order History " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    private void initRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerViewOrderHistory);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderHistoryAdapter(orderHistoryList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}