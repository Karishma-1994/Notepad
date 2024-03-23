package com.example.Notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import CoutomAdaptor.CustomAdapter;


public class NoteListActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerview;
    String arr[] = {"item1", "item2", "item3", "item4", "item5", "item6","item7","item8","item9","item10"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        fab = findViewById(R.id.floatingActionButton);
        recyclerview = findViewById(R.id.recycleview);

        CustomAdapter c = new CustomAdapter(arr);
        recyclerview.setAdapter(c);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newnote = new Intent(NoteListActivity.this, NoteDetailsActivity.class);
                startActivity(newnote);
                Toast.makeText(NoteListActivity.this, "New Note Created", Toast.LENGTH_SHORT).show();
            }
        });

    }
}