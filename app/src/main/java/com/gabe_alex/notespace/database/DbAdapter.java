package com.gabe_alex.notespace.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gabe_alex.notespace.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DbAdapter {
    protected DbOpenHelper helper;

    public DbAdapter(Context context) {
        helper = new DbOpenHelper(context);
    }

    public Note addNote(Note note) {
        ContentValues cv = new ContentValues();
        cv.put(DbConstants.NOTE_TITLE, note.getTitle());
        cv.put(DbConstants.NOTE_CONTENT, note.getContent());
        cv.put(DbConstants.NOTE_DATE, new Date().getTime());

        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            int id = (int) db.insert(DbConstants.NOTE_TABLE_NAME, DbConstants.NOTE_ID, cv);
            return getNote(id, true);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public Note updateNote(Note note) {
        int id = note.getId();

        ContentValues cv = new ContentValues();
        cv.put(DbConstants.NOTE_TITLE, note.getTitle());
        cv.put(DbConstants.NOTE_CONTENT, note.getContent());
        cv.put(DbConstants.NOTE_DATE, new Date().getTime());

        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            db.update(DbConstants.NOTE_TABLE_NAME, cv, DbConstants.NOTE_ID + "=?", new String[]{String.valueOf(id)});
            return getNote(id, true);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            db.delete(DbConstants.NOTE_TABLE_NAME, DbConstants.NOTE_ID + "=?", new String[]{String.valueOf(id)});
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public int getNotesCount() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getWritableDatabase();
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + DbConstants.NOTE_TABLE_NAME, null);
            cursor.moveToFirst();
            return cursor.getInt(0);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
            if(db != null) {
                db.close();
            }
        }
    }

    public int getNoteIdFromPosition(int position) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getWritableDatabase();
            //for position, we get the n-th element from a list of notes sorted by date desc, so they appear in that order in the view
            String[] columns = {DbConstants.NOTE_ID};
            cursor = db.query(DbConstants.NOTE_TABLE_NAME, columns, null, null, null, null, DbConstants.NOTE_DATE + " DESC", position + ",1");
            cursor.moveToFirst();
            return cursor.getInt(0);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
            if(db != null) {
                db.close();
            }
        }
    }

    public Note getNote(int id, boolean withContent) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getWritableDatabase();
            List<String> columns = new ArrayList<>(Arrays.asList(new String[]{DbConstants.NOTE_ID, DbConstants.NOTE_TITLE, DbConstants.NOTE_DATE}));

            //content is optional, because it can be quite large, and we don't need it for a list of notes
            if (withContent) {
                columns.add(DbConstants.NOTE_CONTENT);
            }

            cursor = db.query(DbConstants.NOTE_TABLE_NAME, columns.toArray(new String[columns.size()]), DbConstants.NOTE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
            cursor.moveToFirst();

            Note note = new Note();
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setDate(new Date(cursor.getLong(2)));

            if (withContent) {
                note.setContent(cursor.getString(3));
            }

            return note;
        } finally {
            if(cursor != null) {
                cursor.close();
            }
            if(db != null) {
                db.close();
            }
        }
    }

    public Note getNoteFromPosition(int position, boolean withContent) {
        int id = getNoteIdFromPosition(position);
        return getNote(id, withContent);
    }
}
