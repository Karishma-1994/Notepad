package com.example.Notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String dbName = "notes_karishma.db";
    private static final int dbVersion = 1;

    private static final String NOTEKAR_TABLE = "notekar";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String newNote = "CREATE TABLE " + NOTEKAR_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                CONTENT + " TEXT)";
        db.execSQL(newNote);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTEKAR_TABLE);
        onCreate(db);
    }

    void addNote(String title, String content) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(CONTENT, content);

        long result = db.insert(NOTEKAR_TABLE, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
//        db.close();
    }

    public List<NoteModel> getAll() {
        List<NoteModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + NOTEKAR_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int noteID = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                NoteModel newNote = new NoteModel(noteID, title, content);
                returnList.add(newNote);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
//        db.close();
        return returnList;
    }
}