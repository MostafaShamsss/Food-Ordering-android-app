package com.example.wagbaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Query;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wagbaproject.databinding.ActivityPaymentBinding;
import com.example.wagbaproject.databinding.ActivityProfileBinding;
import com.example.wagbaproject.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.net.Authenticator;

public class ProfileActivity extends AppCompatActivity
{

    FirebaseAuth mAuth;
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();

        UserModel model = new UserModel();
        String currentUserEmail = mAuth.getCurrentUser().getEmail();
       // model = DatabaseClass.getDatabase(getApplicationContext()).getDao().findByEmail(currentUserEmail);
              DatabaseClass.getDatabase(getApplicationContext()).getDao().findByEmail(currentUserEmail).observe(this, userModel -> {
                  if(userModel!=null) {
                      Log.d("theuser", userModel.getEmail());

                      binding.nameProfile.setText(userModel.getName());
                      binding.emailProfile.setText(userModel.getEmail());
                      binding.addressProfile.setText(userModel.getAddress());
                      binding.phoneNoProfile.setText(userModel.getPhoneno());
                  }

                  else{
                      startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                  }

              } );

    }
}