package com.google.croudintelligence.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.croudintelligence.Adapter.QuestionAdapter;
import com.google.croudintelligence.Models.Questions;
import com.google.croudintelligence.ViewModel.Question;
import com.google.croudintelligence.ViewModel.User_Quests;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.api.RetrofitClient;
import com.google.croudintelligence.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static final String TAG = "HomeFragment";
    QuestionAdapter adapter;
    FragmentHomeBinding fbinding;
    Api api;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fbinding = FragmentHomeBinding.inflate(inflater, container, false);


        api = RetrofitClient.getInstance().getMyApi();

        ArrayList<Questions> questions = new ArrayList<>();
        ArrayList<User_Quests> user_quests = new ArrayList<>();
        questions.add(new Questions());
        questions.add(new Questions());
        questions.add(new Questions());
        questions.add(new Questions());
        context = requireContext();
        //  adapter = new QuestionAdapter(questions, context);
        fbinding.recyclerViewHomeFragment.setAdapter(adapter);
        return fbinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        api.getAllQuestion().enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Question>> call, @NonNull Response<ArrayList<Question>> response) {
                if (response.isSuccessful()) {
                    try {

                        adapter = new QuestionAdapter(response.body(), context, getChildFragmentManager());
                        fbinding.recyclerViewHomeFragment.setAdapter(adapter);

                    } catch (Exception ex) {
                        Log.d(TAG, ex.toString());
                    }

                } else {
                    Toast.makeText(context, "UnSuc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Question>> call, @NonNull Throwable t) {

            }
        });
    }
}