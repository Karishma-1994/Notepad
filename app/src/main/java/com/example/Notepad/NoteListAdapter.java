package com.example.Notepad;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Notepad.databinding.LayoutfileBinding;
import com.example.Notepad.list.NoteListActivity;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder> {

    private NoteClickListener noteClickListener;

    private LongNoteClickListener longNoteClickListener;
    private NoteListActivity.Mode mode = NoteListActivity.Mode.VIEW;

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

    public void setOnClickListener(NoteClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
    }

    public void setOnLongClickListener(LongNoteClickListener longNoteClickListener) {
        this.longNoteClickListener = longNoteClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMode(NoteListActivity.Mode mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeCheckedItems() {
        List<NoteModel> uncheckedNotes = new ArrayList<>();
        for (NoteModel noteModel : noteModelList) {
            if (!noteModel.isChecked()) {
                uncheckedNotes.add(noteModel);
            }
        }
        noteModelList = uncheckedNotes;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public List<String> getCheckedNoteIds() {
        List<String> checkedNoteIds = new ArrayList<>();
        for (NoteModel noteModel : noteModelList) {
            if (noteModel.isChecked()) {
                checkedNoteIds.add(String.valueOf(noteModel.getId()));
            }
        }
        return checkedNoteIds;
    }

    public void checkAllItems() {
        for (NoteModel noteModel : noteModelList) {
            noteModel.setChecked(true);
        }
        notifyDataSetChanged();
    }

    public void uncheckAllItems() {
        for (NoteModel noteModel : noteModelList) {
            noteModel.setChecked(false);
        }
        notifyDataSetChanged();
    }



        class NoteListViewHolder extends RecyclerView.ViewHolder {

            private LayoutfileBinding binding;

            public NoteListViewHolder(@NonNull LayoutfileBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
                setClickListener();
                setOnLongClickListener();
            }

            public void setClickListener() {
                binding.tvLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        NoteModel clickedNoteModel = noteModelList.get(position);
                        if (mode == NoteListActivity.Mode.SELECT) {
                            noteModelList.get(position).setChecked(!clickedNoteModel.isChecked());
                            notifyItemChanged(position);
                        } else {
                            noteClickListener.onClick(clickedNoteModel);
                        }
                    }
                });
            }

            public void setOnLongClickListener() {
                binding.tvLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = getAdapterPosition();
                        longNoteClickListener.onLongClick(noteModelList.get(position));
                        return true;
                    }
                });
            }

            public void bind(NoteModel noteModel) {
                binding.tvTitle.setText(noteModel.getTitle());
                binding.tvContent.setText(noteModel.getContent());
                if (mode == NoteListActivity.Mode.VIEW) {
                    binding.checkbox.setVisibility(View.GONE);
                } else if (mode == NoteListActivity.Mode.SELECT) {
                    binding.checkbox.setVisibility(View.VISIBLE);
                }
                binding.checkbox.setChecked(noteModel.isChecked());
            }
        }


        public interface NoteClickListener {
            void onClick(NoteModel noteModel);
        }

        public interface LongNoteClickListener {
            void onLongClick(NoteModel noteModel);


        }
    }