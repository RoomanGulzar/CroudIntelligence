package com.google.croudintelligence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.croudintelligence.HelpingClass.UserDetails;
import com.google.croudintelligence.Models.Topic;
import com.google.croudintelligence.Models.User;
import com.google.croudintelligence.Models.staticClass;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.api.RetrofitClient;
import com.google.croudintelligence.databinding.ActivitySelectSubjectBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectSubjectActivity extends AppCompatActivity {
    ArrayList<CheckBox> checkBoxes;
    Api api;
    ActivitySelectSubjectBinding binding;
    private Context context;
    ArrayList<Integer> topicId;
    User user;
    ArrayList<String> subjects;
    private static final String TAG = "SelectSubjectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySelectSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        topicId = new ArrayList<>();
        api = RetrofitClient.getInstance().getMyApi();
        context = this;
        checkBoxes = new ArrayList<>();
        user = new UserDetails().getUser(getSharedPreferences("myPrefs", MODE_PRIVATE));
        subjects = new ArrayList<>();
        binding.btnDone.setEnabled(false);
        if (user == null)
            finish();
        getUserTopic();
        binding.btnDone.setOnClickListener(v -> {
            getTopic();
            api.InsertFavouriteTopics(sb.toString(), user.getUser_id()).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {
                        Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();

                        Intent i = new Intent(context, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } catch (Exception ex) {
                        Log.d(TAG, ex.toString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();

                }
            });

        });


    }

    private void getUserTopic() {
        api.getUserTopics(user.getUser_id()).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Topic>> call, @NonNull Response<ArrayList<Topic>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().size() > 0) {
                        try {
                            staticClass.userTopics = response.body();
                            Intent i = new Intent(context, HomeActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } catch (Exception ex) {
                            Log.d(TAG, ex.toString());
                        }
                    } else {
                        showTopic();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Topic>> call, @NonNull Throwable t) {

            }
        });
    }

    private void showTopic() {
        api.getAllTopics().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Topic>> call, @NonNull Response<ArrayList<Topic>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    try {
                        subjects.clear();
                        ArrayList<String> ids = new ArrayList<>();
                        for (Topic t : response.body()) {
                            subjects.add(t.t_name);
                            ids.add(t.t_id + "");
                        }
                        staticClass.updateBoxes(context, subjects, binding.layoutHomeLinear, checkBoxes, ids);
                        staticClass.setListenerLimitTo5(checkBoxes);
                        binding.btnDone.setEnabled(true);
                    } catch (Exception ex) {
                        Log.d(TAG, ex.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Topic>> call, @NonNull Throwable t) {
            }
        });

    }

    StringBuilder sb;

    private void getTopic() {
        try {
            sb = new StringBuilder();
            for (CheckBox cb : checkBoxes) {
                if (cb.isChecked()) {
                    sb.append(cb.getTag().toString());
                    sb.append(",");
                }
            }
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
    }

    static int n = 0;

    public void setListenerLimitTo5() {
        n = 0;
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    n++;
                    if (n >= 5) {
                        for (int j = 0; j < checkBoxes.size(); j++) {
                            if (!checkBoxes.get(j).isChecked()) {
                                checkBoxes.get(j).setEnabled(false);
                            }
                        }
                    }
                } else {
                    n--;
                    if (n < 5) {
                        for (int j = 0; j < checkBoxes.size(); j++) {
                            if (!checkBoxes.get(j).isChecked()) {
                                checkBoxes.get(j).setEnabled(true);
                            }
                        }
                    }
                }

            });
        }
    }

}