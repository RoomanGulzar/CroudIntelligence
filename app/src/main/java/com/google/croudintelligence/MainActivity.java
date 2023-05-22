package com.google.croudintelligence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.croudintelligence.HelpingClass.UserDetails;
import com.google.croudintelligence.Models.User;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.api.RetrofitClient;
import com.google.croudintelligence.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ///Login Activity
    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        binding.editTextEmial.setText("afaq@gmail.co.");
        binding.editTextPassword.setText("123");
        binding.textViewSignup.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });
        Api api = RetrofitClient.getInstance().getMyApi();
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.editTextEmial.getText().toString();
            String pass = binding.editTextPassword.getText().toString();
            v.setEnabled(false);
            if (email.equals("") || pass.equals("")) {
                Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            } else {
                api.Login(email, pass).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Intent i = new Intent(MainActivity.this, SelectSubjectActivity.class);
                            startActivity(i);
                            new UserDetails().setUser(editor, response.body());
                            Toast.makeText(MainActivity.this, "Logged In\nUser category is " + response.body().getRole(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                        v.setEnabled(true);
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        v.setEnabled(true);
                    }
                });
            }
        });
    }
}