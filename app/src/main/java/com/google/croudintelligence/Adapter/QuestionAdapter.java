package com.google.croudintelligence.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.croudintelligence.Fragments.AnswereFragment;
import com.google.croudintelligence.PostAnswerActivity;
import com.google.croudintelligence.R;
import com.google.croudintelligence.ViewModel.Question;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.databinding.QuestionCellBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private ArrayList<Question> QuestionList;
    private Context context;
    FragmentManager fragmentManager;

    private static final String TAG = "QuestionAdapter";

    public QuestionAdapter(ArrayList<Question> QuestionList, Context context, FragmentManager fragmentManager) {
        this.QuestionList = QuestionList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionCellBinding b = QuestionCellBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false
        );
        QuestionViewHolder vh = new QuestionViewHolder(b);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        try {
            Question obj = QuestionList.get(position);
            holder.binding.textViewAskedQuestion.setText(obj.getQuestion_text());
            holder.binding.userNameQuestion.setText(obj.getUser_name());
            holder.binding.dateQuestion.setText(obj.getDate_created());
            Glide.with(context).load(Api.IMAGE_URL + obj.getPic())
                    .error(AppCompatResources.getDrawable(context, R.drawable.baseline_profile_24))
                    .into(holder.binding.profileQuestionImage);

            final int[] n = {2};
            holder.binding.btnAnsQuestion.setOnClickListener(v -> {
                holder.openBottomSheetFragment(obj.getQuestion_id());
            });
            holder.binding.btnViewAns.setOnClickListener(v -> {
                Intent i = new Intent(context, PostAnswerActivity.class);
                Bundle bundle = new Bundle();
                String value = new Gson().toJson(obj, Question.class);
                bundle.putString("quest", value);
                i.putExtras(bundle);
                context.startActivity(i);
            });

        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
    }

    @Override
    public int getItemCount() {
        return QuestionList.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        QuestionCellBinding binding;

        public QuestionViewHolder(@NonNull QuestionCellBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void openBottomSheetFragment(int q_id) {
            try {
                AnswereFragment answerDialogue = new AnswereFragment(context, q_id);
                answerDialogue.showNow(fragmentManager, "alsas");
            } catch (Exception ex) {
                Log.d(TAG, ex.toString());
            }
        }
    }

}
