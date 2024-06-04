package com.example.Notepad;

import static com.example.Notepad.NoteDetailsActivity.ID_KEY;

import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Notepad.databinding.LayoutfileBinding;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter2 extends RecyclerView.Adapter<NoteListAdapter2.NoteListViewHolder> {

    List<NoteModel> noteModelList = new ArrayList<>();

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutfileBinding binding = LayoutfileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder, int position) {
        holder.bind(noteModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

    public void setNoteList(List<NoteModel> noteList) {
        this.noteModelList = noteList;
        notifyDataSetChanged();
    }

    class NoteListViewHolder extends RecyclerView.ViewHolder {

        private LayoutfileBinding binding;

        public NoteListViewHolder(@NonNull LayoutfileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            setClickListener();
        }

        public void setClickListener() {
            binding.tvLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent in = new Intent(v.getContext(), NoteDetailsActivity.class);
                    in.putExtra(ID_KEY, noteModelList.get(position).getId());
                    v.getContext().startActivity(in);
                }
            });

        }


        public void bind(NoteModel noteModel) {
            binding.tvTitle.setText(noteModel.getTitle());
            binding.tvContent.setText(noteModel.getContent());
        }

    }
}