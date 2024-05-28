package com.example.Notepad;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public EditText editTitle;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        editTitle = itemView.findViewById(R.id.edtTitle);
    }
}
