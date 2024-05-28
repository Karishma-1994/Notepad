package com.example.Notepad;

import static com.example.Notepad.NoteDetailsActivity.ID_KEY;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter2 extends RecyclerView.Adapter<NoteListAdapter2.NoteListViewHolder> {

    List<NoteModel> noteModelList = new ArrayList<>();

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutfile, parent, false);
        return new NoteListViewHolder(view);
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

        private TextView tvTitle;
        private TextView tvContent;
        private ConstraintLayout tvLayout;

        public NoteListViewHolder(@NonNull View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvLayout = view.findViewById(R.id.tvLayout);

            setClickListener();
        }

        public void setClickListener(){
            tvLayout.setOnClickListener(new View.OnClickListener() {
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
            tvTitle.setText(noteModel.getTitle());
            tvContent.setText(noteModel.getContent());
        }
    }
}
