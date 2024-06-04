package com.example.Notepad.list;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.Notepad.DataBaseHelper;
import com.example.Notepad.NoteDetailsActivity;
import com.example.Notepad.NoteListAdapter2;
import com.example.Notepad.NoteModel;
import com.example.Notepad.R;
import com.example.Notepad.databinding.ActivityNoteListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class NoteListActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    ArrayAdapter arrayAdapter;

    ActivityNoteListBinding binding;
    NoteListAdapter2 noteListAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteListAdapter = new NoteListAdapter2();
        binding.rvNotes.setAdapter(noteListAdapter);
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));

        dataBaseHelper = new DataBaseHelper(this);
        updateNoteList();

        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, NoteDetailsActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(binding.toolbar.toolbar);
        getSupportActionBar().setTitle(getString(R.string.note_kar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void updateNoteList() {
        List<NoteModel> noteList = dataBaseHelper.getAll();
        noteListAdapter.setNoteList(noteList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
