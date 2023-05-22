package com.google.croudintelligence.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.croudintelligence.Models.ChatMessage;
import com.google.croudintelligence.databinding.ChatCellBinding;

import java.util.ArrayList;

public class FireBaseListAdapter extends RecyclerView.Adapter<FireBaseListAdapter.AnswerViewHolder> {

    private ArrayList<ChatMessage> AnswerList;
    private Context context;

    public FireBaseListAdapter(ArrayList<ChatMessage> messages, Context context) {
        this.AnswerList = AnswerList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatCellBinding b = ChatCellBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false
        );
        AnswerViewHolder vh = new AnswerViewHolder(b);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        holder.binding.messageParent.setBackgroundColor(12312);
    }

    @Override
    public int getItemCount() {
        return AnswerList.size();
    }

    class AnswerViewHolder extends RecyclerView.ViewHolder {
        ChatCellBinding binding;

        public AnswerViewHolder(@NonNull ChatCellBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

}
