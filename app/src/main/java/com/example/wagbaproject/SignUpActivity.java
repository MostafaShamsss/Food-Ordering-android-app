package com.example.wagbaproject;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wagbaproject.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity
{

    private ActivitySignUpBinding binding;

    String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();


        binding.SignIn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        binding.SignUp2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createUser();
            }

        });

    }


    private void saveDataRoom()
    {
        String name = binding.nameSignUp.getText().toString();
        String address = binding.addressSignUp.getText().toString();
        String phoneNo = binding.phoneNumberSignUp.getText().toString();
        String email = binding.emailSignUp.getText().toString();

        if(name!=null
                && address!=null
                && phoneNo!=null
                && checkEmail(email))
        {
            UserModel model = new UserModel();
            model.setName(name);
            model.setAddress(address);
            model.setPhoneno(phoneNo);
            model.setEmail(email);
            DatabaseClass.getDatabase(getApplicationContext()).getDao().insertAllData(model);

            binding.nameSignUp.setText("");
            binding.phoneNumberSignUp.setText("");
            binding.addressSignUp.setText("");
            binding.emailSignUp.setText("");

            Toast.makeText(this, "Data Successfully Saved", Toast.LENGTH_SHORT).show();
        }
    }



    public  void createUser()
    {
        String email = binding.emailSignUp.getText().toString();
        String password = binding.passSignUp.getText().toString();
        try
        {
            if(!checkEmail(email))
            {
                binding.emailSignUp.setError("Please enter a valid email");
            }
            else if (!password.matches(passwordRegex))
            {
                binding.passSignUp.setError("your password should contain minimum eight characters, at least 1 letter and 1 number");
            }
            else
            {

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "user registered successfully", LENGTH_SHORT).show();
                            saveDataRoom();
                            startActivity(new Intent(SignUpActivity.this,RestAvailActivity.class));
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "sign up error " + task.getException().getMessage(), LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "createUser error " + e.getMessage(), LENGTH_SHORT).show();
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