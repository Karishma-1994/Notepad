package com.example.Notepad;


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

import com.example.Notepad.list.NoteListActivity;


public class NoteDetailsActivity extends AppCompatActivity {
    EditText edtTitle, edtDetail;
    Toolbar toolbar;
    DataBaseHelper db;

    TextView viewTitle, viewDetail;

    ConstraintLayout clViewLayout, clEditLayout;

    private int clickedId;

    public static String ID_KEY = "id";

    private ViewState viewState = ViewState.CREATE;

    private NoteModel noteModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        toolbar = findViewById(R.id.toolbar);

        edtTitle = findViewById(R.id.edtTitle);
        edtDetail = findViewById(R.id.edtDetail);

        viewTitle = findViewById(R.id.viewTitle);
        viewDetail = findViewById(R.id.viewDetail);

        clViewLayout = findViewById(R.id.cl_view_layout);
        clEditLayout = findViewById(R.id.cl_edit_layout);

        db = new DataBaseHelper(this);

        clickedId = getIntent().getIntExtra(ID_KEY, 0);

        if (clickedId > 0) {
            noteModel = db.get(clickedId);
            setDataOnViewFromId();
        }

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
                {
                }
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
        clViewLayout.setVisibility(View.VISIBLE);
        clEditLayout.setVisibility(View.GONE);
        viewTitle.setText(noteModel.getTitle());
        viewDetail.setText(noteModel.getContent());
    }

    private void deleteClicked() {
        changeViewState(ViewState.VIEW);
        Toast.makeText(NoteDetailsActivity.this, "Deleted the course", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(NoteDetailsActivity.this, NoteListActivity.class);
        startActivity(i);

    }

    private void editClicked() {
        changeViewState(ViewState.EDIT);
        clViewLayout.setVisibility(View.GONE);
        clEditLayout.setVisibility(View.VISIBLE);
        edtTitle.setText(noteModel.getTitle());
        edtDetail.setText(noteModel.getContent());
    }

    private void changeViewState(ViewState viewState) {
        this.viewState = viewState;
        invalidateOptionsMenu();
    }

    private void saveClicked() {
        String title = edtTitle.getText().toString().trim();
        String detail = edtDetail.getText().toString().trim();
        Intent i = new Intent(NoteDetailsActivity.this, NoteListActivity.class);
        startActivity(i);
        if (title.isEmpty() || detail.isEmpty()) {
            Toast.makeText(this, "title or detail cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (viewState == ViewState.EDIT && noteModel != null) {
                db.updateNote(title, detail, String.valueOf(noteModel.getId()));
            } else {
                db.addNote(title, detail);
            }
        }
    }

    private enum ViewState {
        CREATE,
        VIEW,
        EDIT
    }

}


