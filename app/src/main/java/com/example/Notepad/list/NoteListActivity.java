package com.example.Notepad.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.Notepad.DataBaseHelper;
import com.example.Notepad.NoteDetailsActivity;
import com.example.Notepad.NoteListAdapter2;
import com.example.Notepad.NoteModel;
import com.example.Notepad.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class NoteListActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerview;
    DataBaseHelper dataBaseHelper;
    ArrayAdapter arrayAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        fab = findViewById(R.id.fabAddNote);
        recyclerview = findViewById(R.id.rvNotes);
//        showNoteRV();
        NoteListAdapter2 noteListAdapter = new NoteListAdapter2();
        recyclerview.setAdapter(noteListAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBaseHelper = new DataBaseHelper(this);

        List<NoteModel> noteList = dataBaseHelper.getAll();
        noteListAdapter.setNoteList(noteList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newnote = new Intent(NoteListActivity.this, NoteDetailsActivity.class);
                startActivity(newnote);
            }
        });

    }

//    private void showNoteRV() {
//        List<NoteModel> everynote = dataBaseHelper.getAll();
//        NoteListAdapter noteListAdapter = new NoteListAdapter();
//        arrayAdapter = new ArrayAdapter<NoteModel>(NoteListActivity.this, R.layout.layoutfile, dataBaseHelper.getAll());
//        recyclerview.setAdapter(noteListAdapter);


//    }
}