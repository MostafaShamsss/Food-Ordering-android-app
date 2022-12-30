package com.example.wagbaproject;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wagbaproject.databinding.ActivityPaymentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    int total = 0;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUser = mAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //String orderID = getIntent().getStringExtra("ID");

        binding.restaurantsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RestAvailActivity.class));
            }
        });


        getTotalPriceOrder();
    }



    private void getTotalPriceOrder()
    {

        databaseReference.child("users").child(currentUser).child("orders").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                try
                {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        for(int i=0; i< postSnapshot.getChildrenCount(); i++)
                        {
                            if(!postSnapshot.child(String.valueOf(i)).child("status").getValue().toString().matches("Delivered"))
                            {
                                String totalPrice = postSnapshot.child(String.valueOf(i)).child("price").getValue().toString();
                                totalPrice = totalPrice.substring(0, totalPrice.length() - 3);     //to remove space LE for example "60 LE" to be "60"
                                total += Integer.parseInt(totalPrice);       //to set the total price of the elements of the cart here
                            }
                        }
                    }
                    binding.paymentTotal2.setText(String.valueOf(total) +" LE");

                }
                catch (Exception e)
                {
                    Toast.makeText(PaymentActivity.this, "Payment " + e.getMessage(), Toast.LENGTH_LONG).show();
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
}