package com.example.Notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NoteDetailsActivity extends AppCompatActivity {
Button but;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        but= findViewById(R.id.btnSave);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Save = new Intent(NoteDetailsActivity.this, NoteListActivity.class);
                startActivity(Save);
                Toast.makeText(NoteDetailsActivity.this, "Note Save", Toast.LENGTH_SHORT).show();
            }
        });

    }
}