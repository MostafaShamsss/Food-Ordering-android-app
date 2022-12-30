package com.example.wagbaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagbaproject.databinding.ActivityCartBinding;
import com.example.wagbaproject.databinding.ActivityRestDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<RestDetailsModel> dishList;
    RestDetailsAdapter adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ActivityRestDetailsBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUser = mAuth.getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityRestDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dishList = new ArrayList<>();

        String restName = getIntent().getStringExtra("restName");
        String restImage = getIntent().getStringExtra("restImage");
        int resID = getIntent().getIntExtra("restID", 0);

        binding.TextView1ResDetails.setText(restName);
        Picasso.get().load(restImage).into(binding.ImageViewResDetails);


        binding.goToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        getRestFirebase(resID);
    }


    private void getRestFirebase(int resID) {
        databaseReference.child("restaurants").child(Integer.toString(resID)).child("dishList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        dishList.add(postSnapshot.getValue(RestDetailsModel.class));
                    }
                    initRecyclerView();
                } catch (Exception e) {
                    Toast.makeText(RestDetailsActivity.this, "Rest Details " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RT DB", "Failed to read value.", error.toException());
            }
        });
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewRestDetails);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RestDetailsAdapter(dishList);
        recyclerView.setAdapter(adapter);
    }


}