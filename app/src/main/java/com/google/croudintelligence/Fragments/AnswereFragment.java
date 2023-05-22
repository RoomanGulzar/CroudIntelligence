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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.croudintelligence.Adapter.AnswerAdapter;
import com.google.croudintelligence.Models.Answer;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.api.RetrofitClient;
import com.google.croudintelligence.databinding.FragmentAnswereBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnswereFragment extends BottomSheetDialogFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static final String TAG = "AnswereFragment";
    FragmentAnswereBinding fbinding;
    Api api;
    ArrayList<Answer> Answers;
    AnswerAdapter adapter;
    Context context;

    int q_id;

    public AnswereFragment(Context context, int q_id) {
        this.context = context;
        this.q_id = q_id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fbinding = FragmentAnswereBinding.inflate(inflater, container, false);
        context = requireContext();
        api = RetrofitClient.getInstance().getMyApi();
        return fbinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api.getAllAnswer(q_id).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Answer>> call, @NonNull Response<ArrayList<Answer>> response) {
                try {
                    String resp = "loading...";
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().size() > 0) {
                            Answers = response.body();
                            adapter = new AnswerAdapter(Answers, context);
                            fbinding.recyclerViewAnswerFragment.setAdapter(adapter);

                        } else {
                            resp = "No Answer yet";
                        }
                    } else {
                        resp = "Failed\n" + response.message();
                    }
                    Toast.makeText(context, resp, Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Log.d(TAG, ex.toString());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Answer>> call, @NonNull Throwable t) {

            }
        });
    }
}