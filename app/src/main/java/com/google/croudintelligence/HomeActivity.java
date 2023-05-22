package com.google.croudintelligence;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.croudintelligence.Fragments.HomeFragment;
import com.google.croudintelligence.Fragments.ProfileFragment;
import com.google.croudintelligence.Fragments.QuestionFragment;
import com.google.croudintelligence.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            try {
                switch (item.getItemId()) {
                    case R.id.action_home: {
                        loadFragment(new HomeFragment());
                        break;
                    }
                    case R.id.action_profile: {
                        loadFragment(new ProfileFragment());
                        break;
                    }
                    case R.id.action_ask: {
                        loadFragment(new QuestionFragment());
                        break;
                    }
                    default:
                        break;

                }
            } catch (Exception ex) {
                Log.d(TAG, ex.toString());
            }
            return true;
        });
    }

    private void loadFragment(Fragment f) {
        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(binding.frameContainer.getId(), f);
            ft.commit();
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
    }

    @Override
    public void onBackPressed() {
        try {
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to exit app?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Exit the app
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }

    }
}