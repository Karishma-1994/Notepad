package com.example.Notepad.list;

import static com.example.Notepad.NoteDetailsActivity.ID_KEY;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.Notepad.DataBaseHelper;
import com.example.Notepad.NoteDetailsActivity;
import com.example.Notepad.NoteListAdapter;
import com.example.Notepad.NoteModel;
import com.example.Notepad.R;
import com.example.Notepad.databinding.ActivityNoteListBinding;

import java.util.List;


public class NoteListActivity extends AppCompatActivity {
    DataBaseHelper db;
    ActivityNoteListBinding binding;
    NoteListAdapter noteListAdapter;

    Mode mode = Mode.VIEW;

    ActivityResultLauncher<Intent> detailActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (result) -> {
                if (Activity.RESULT_OK == result.getResultCode()) {
                    updateNoteList();
                }
            }
    );
    OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (mode == Mode.SELECT) {
                noteListAdapter.uncheckAllItems();
                // TODO: Uncheck all items
                updateMode(Mode.VIEW);
            } else {
                finish();
            }
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        noteListAdapter = new NoteListAdapter();

        binding.rvNotes.setAdapter(noteListAdapter);
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));


        noteListAdapter.setOnClickListener(new NoteListAdapter.NoteClickListener() {
            @Override
            public void onClick(NoteModel noteModel) {
                Intent intent = new Intent(NoteListActivity.this, NoteDetailsActivity.class);
                intent.putExtra(ID_KEY, noteModel.getId());
                detailActivityResult.launch(intent);
            }
        });

        noteListAdapter.setOnLongClickListener(new NoteListAdapter.LongNoteClickListener() {
            @Override
            public void onLongClick(NoteModel noteModel) {
                updateMode(Mode.SELECT);
                Toast.makeText(NoteListActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        db = new DataBaseHelper(this);
        updateNoteList();

        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, NoteDetailsActivity.class);
                detailActivityResult.launch(intent);
            }
        });

        setSupportActionBar(binding.toolbar.toolbar);
        getSupportActionBar().setTitle(getString(R.string.note_kar));
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (mode) {
            case VIEW: {
                menu.findItem(R.id.menu_selectAll).setVisible(false);
                menu.findItem(R.id.menu_delete).setVisible(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            }
            case SELECT: {
                menu.findItem(R.id.menu_selectAll).setVisible(true);
                menu.findItem(R.id.menu_delete).setVisible(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                break;
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.menu_selectAll) {
            selectAllClicked();
            return true;
        } else if (id == R.id.menu_delete) {
            deleteClicked();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void updateMode(Mode mode) {
        this.mode = mode;
        noteListAdapter.setMode(mode);
        invalidateOptionsMenu();
    }

    private void updateNoteList() {
        List<NoteModel> noteList = db.getAll();
        noteListAdapter.setNoteList(noteList);
    }

    private void deleteClicked() {
        db.deleteNote(noteListAdapter.getCheckedNoteIds());
        noteListAdapter.removeCheckedItems();
        Toast.makeText(this, "Deleted Successfuly", Toast.LENGTH_SHORT).show();
    }

    private void selectAllClicked() {
        noteListAdapter.checkAllItems();
//        db.deleteNote(noteListAdapter.getCheckedNoteIds());

    }


    public enum Mode {
        SELECT,
        VIEW,
    }
}