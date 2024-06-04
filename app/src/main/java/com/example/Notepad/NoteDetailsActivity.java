package com.example.Notepad;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Notepad.databinding.ActivityNoteDetailsBinding;
import com.example.Notepad.databinding.ActivityNoteListBinding;
import com.example.Notepad.list.NoteListActivity;


public class NoteDetailsActivity extends AppCompatActivity {


    DataBaseHelper db;

    private int clickedId;

    public static String ID_KEY = "id";

    private ViewState viewState = ViewState.CREATE;

    private NoteModel noteModel;
    ActivityNoteDetailsBinding binding;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = new DataBaseHelper(this);

        clickedId = getIntent().getIntExtra(ID_KEY, 0);

        if (clickedId > 0) {
            noteModel = db.get(clickedId);
            setDataOnViewFromId();
        }

        setSupportActionBar(binding.toolbar.toolbar);

        getSupportActionBar().setTitle(getString(R.string.create_note));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (viewState) {
            case VIEW: {
                menu.findItem(R.id.menu_save).setVisible(false);
                menu.findItem(R.id.menu_delete).setVisible(false);
                menu.findItem(R.id.menu_edit).setVisible(true);
                break;
            }
            case EDIT: {
                menu.findItem(R.id.menu_save).setVisible(true);
                menu.findItem(R.id.menu_delete).setVisible(true);
                menu.findItem(R.id.menu_edit).setVisible(false);
                break;
            }
            case CREATE: {
                menu.findItem(R.id.menu_save).setVisible(true);
                menu.findItem(R.id.menu_delete).setVisible(false);
                menu.findItem(R.id.menu_edit).setVisible(false);
                break;
            }
        }
        return super.onPrepareOptionsMenu(menu);
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

    private void setDataOnViewFromId() {
        changeViewState(ViewState.VIEW);
       binding.clViewLayout.getRoot().setVisibility(View.VISIBLE);
        binding.clEditLayout.getRoot().setVisibility(View.GONE);
        binding.clViewLayout.viewTitle.setText(noteModel.getTitle());
        binding.clViewLayout.viewDetail.setText(noteModel.getContent());
    }

    private void deleteClicked() {
        db.deleteNote(String.valueOf(noteModel.getId()));
        Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();

        finish();
    }


    private void editClicked() {
        changeViewState(ViewState.EDIT);
        binding.clViewLayout.getRoot().setVisibility(View.GONE);
        binding.clEditLayout.getRoot().setVisibility(View.VISIBLE);
        binding.clEditLayout.edtTitle.setText(noteModel.getTitle());
        binding.clEditLayout.edtDetail.setText(noteModel.getContent());
    }

    private void changeViewState(ViewState viewState) {
        this.viewState = viewState;
        invalidateOptionsMenu();
    }

    private void saveClicked() {
        String title = binding.clEditLayout.edtTitle.getText().toString().trim();
        String detail = binding.clEditLayout.edtDetail.getText().toString().trim();
        if (title.isEmpty() || detail.isEmpty()) {
            Toast.makeText(this, "title or detail cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (viewState == ViewState.EDIT && noteModel != null) {
                db.updateNote(title, detail, String.valueOf(noteModel.getId()));
            } else {
                db.addNote(title, detail);
            }
            finish();
        }
    }


    private enum ViewState {
        CREATE,
        VIEW,
        EDIT
    }

}


