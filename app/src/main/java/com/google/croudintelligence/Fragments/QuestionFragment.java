package com.google.croudintelligence.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.croudintelligence.HelpingClass.UserDetails;
import com.google.croudintelligence.Models.Questions;
import com.google.croudintelligence.Models.Topic;
import com.google.croudintelligence.Models.User;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.api.RetrofitClient;
import com.google.croudintelligence.databinding.FragmentQuestionBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionFragment extends Fragment {
    private static final String TAG = "QuestionFragment";
    FragmentQuestionBinding binding;
    Api api;
    User user;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<Topic> topics;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        api = RetrofitClient.getInstance().getMyApi();
        context = requireContext();
        sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        topics = new ArrayList<>();
        user = new UserDetails().getUser(sharedPreferences);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserTopic();
        binding.btnPostYourQuestion.setOnClickListener(v -> {
            try {
                String q = binding.editTextAskQuestionHereQuestionFragment.getText().toString();
                if (q.equals("")) {
                    Toast.makeText(context, "Question must not empty!", Toast.LENGTH_SHORT).show();
                } else {
                    Questions qs = new Questions();
                    int top = topics.get(binding.spinnerSelectTopic.getSelectedItemPosition()).t_id;
                    qs.setQuestion_text(q);
                    qs.setT_id(top);
                    qs.setUser_id(user.getUser_id());
                    api.InsertQuestion(qs).enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        }
                    });
                }
            } catch (Exception ex) {
                Log.d(TAG, ex.toString());
            }
        });
    }

    private void getUserTopic() {
        api.getUserTopics(user.getUser_id()).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Topic>> call, @NonNull Response<ArrayList<Topic>> response) {
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                        topics.addAll(response.body());
                        ArrayList<String> names = new ArrayList<>();
                        for (Topic t : topics) {
                            names.add(t.t_name);
                        }
                        binding.spinnerSelectTopic.setAdapter(
                                new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, names)
                        );
                        binding.spinnerSelectTopic.setSelection(0);
                    }
                } catch (Exception ex) {
                    Log.d(TAG, ex.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Topic>> call, @NonNull Throwable t) {
            }
        });
    }
}