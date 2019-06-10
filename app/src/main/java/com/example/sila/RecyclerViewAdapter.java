package com.example.sila;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SwahiliWordsViewHolder> {
    // Variable to hold translations
    ArrayList<DataItem> translations = new ArrayList<>();

    public void setTranslations(ArrayList<DataItem> translations) {
        this.translations = translations;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.SwahiliWordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new SwahiliWordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.SwahiliWordsViewHolder holder, int position) {
        DataItem dataItem = translations.get(position);

        holder.textView.setText(dataItem.getSwaWord());

    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    class SwahiliWordsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        SwahiliWordsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

}

