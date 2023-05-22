package com.google.croudintelligence.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.croudintelligence.Models.Answer;
import com.google.croudintelligence.api.Api;
import com.google.croudintelligence.databinding.AnswerCellBinding;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private ArrayList<Answer> AnswerList;
    private Context context;

    public AnswerAdapter(ArrayList<Answer> AnswerList, Context context) {
        this.AnswerList = AnswerList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnswerCellBinding b = AnswerCellBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false
        );
        AnswerViewHolder vh = new AnswerViewHolder(b);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {

        Answer obj = AnswerList.get(position);

        holder.binding.textViewAnswer.setText(obj.getAnswer_text());
        holder.binding.userNameQuestion.setText(obj.user.getUser_name());
        Glide.with(context).load(Api.IMAGE_URL + obj.user.getPic()).into(holder.binding.profileQuestionImage);
        holder.binding.textViewLevel.setText("Level " + obj.user.getUser_level());
        holder.binding.dateQuestion.setText(obj.getAnswer_date());
        holder.binding.btnDownVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Vote Down", Toast.LENGTH_SHORT).show();
            }
        });
        holder.binding.btnUpVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Vote Up", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return AnswerList.size();
    }

    class AnswerViewHolder extends RecyclerView.ViewHolder {
        AnswerCellBinding binding;

        public AnswerViewHolder(@NonNull AnswerCellBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

}
