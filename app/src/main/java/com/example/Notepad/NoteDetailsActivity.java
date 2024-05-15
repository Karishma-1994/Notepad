package com.example.Notepad;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class NoteDetailsActivity extends AppCompatActivity {
    EditText edtTitle, edtDetail;
    Toolbar toolbar;
    DataBaseHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        toolbar = findViewById(R.id.toolbar);
        edtTitle = findViewById(R.id.edtTitle);
        edtDetail = findViewById(R.id.edtDetail);
        db = new DataBaseHelper(this);


        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.create_note));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_save) {
            saveClicked();
            return true;
        } else if (id == R.id.menu_edit) {
            editClicked();
            return true;
        } else if (id == R.id.menu_delete) {
            deleteClicked();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void deleteClicked() {

    }

    private void editClicked() {

    }

    private void saveClicked() {
        String title = edtTitle.getText().toString().trim();
        String detail = edtDetail.getText().toString().trim();
        db.addNote(title,detail);
    }
}

