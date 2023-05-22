package com.google.croudintelligence.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.croudintelligence.Edit_profile_activity;
import com.google.croudintelligence.HelpingClass.UserDetails;
import com.google.croudintelligence.MainActivity;
import com.google.croudintelligence.Models.User;
import com.google.croudintelligence.R;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentProfileBinding binding;
    User user;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = requireContext();
        sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        user = new UserDetails().getUser(sharedPreferences);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.btnEditProfile.setOnClickListener(v -> {
            Intent i = new Intent(context, Edit_profile_activity.class);
            startActivity(i);
        });
        binding.btnLogout.setOnClickListener(v -> {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        });
        Glide.with(context).load(Api.IMAGE_URL + user.getPic()).error(R.drawable.baseline_profile_24).into(binding.profileQuestionImage);
        binding.textViewUserName.setText(user.getUser_name());
        binding.textViewUserLevel.setText("Level " + user.getUser_level() + "");
        return binding.getRoot();
    }
}