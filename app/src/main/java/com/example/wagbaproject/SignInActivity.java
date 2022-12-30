package com.example.wagbaproject;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagbaproject.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity
{
    private ActivitySignInBinding binding;
    private RestAvailActivity restAvailActivityObject;

    String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), RestAvailActivity.class));
        }


        binding.loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signInUser();
            }
        });

        binding.SignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

    }


    private void signInUser()
    {
        String email = binding.emailSignIn.getText().toString();
        String password = binding.passSignIn.getText().toString();


        try
        {
            if(!checkEmail(email))
            {
                binding.emailSignIn.setError("Please enter a valid email");
            }
            else if (!password.matches(passwordRegex))
            {
                binding.passSignIn.setError("your password should contain minimum eight characters, at least 1 letter and 1 number");
            }
            else
            {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignInActivity.this, "user signed in successfully", LENGTH_SHORT).show();

                            startActivity(new Intent(SignInActivity.this, RestAvailActivity.class));
                        }
                        else
                        {
                            Toast.makeText(SignInActivity.this, "sign in error " + task.getException().getMessage(), LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(SignInActivity.this, "signInUser error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean checkEmail(String email)  //college email
    {
        Pattern emailRegex = Pattern.compile("^[A-Z0-9_!#$%&'+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'+/=?`{|}~^-]+↵\n" +
                ")*@eng.asu.edu.eg$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegex.matcher(email);
        return matcher.matches();
    }
}