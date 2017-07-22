package com.gabe_alex.notespace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gabe_alex.notespace.database.DbAdapter;
import com.gabe_alex.notespace.database.DbConstants;

public class NoteActivity extends AppCompatActivity {
    DbAdapter dbAdapter;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbAdapter = new DbAdapter(this);

        Intent i = getIntent();
        Bundle intentExtras = i.getExtras();
        if(intentExtras != null) {
            int id = i.getExtras().getInt(DbConstants.NOTE_ID);

            note = dbAdapter.getNote(id, true);

            EditText titleText = (EditText) findViewById(R.id.editorNoteTitle);
            EditText contentText = (EditText) findViewById(R.id.editorNoteContent);

            titleText.setText(note.getTitle());
            contentText.setText(note.getContent());
        } else {
            note = new Note();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            boolean noteExists = note.getId() != 0;
            if(noteExists) {
                dbAdapter.deleteNote(note.getId());
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        EditText titleText = (EditText) findViewById(R.id.editorNoteTitle);
        EditText contentText = (EditText) findViewById(R.id.editorNoteContent);

        boolean hasContent = !titleText.getText().toString().isEmpty() || !contentText.getText().toString().isEmpty();
        boolean contentChanged = !titleText.getText().toString().equals(note.getTitle())  || !contentText.getText().toString().equals(note.getContent());
        boolean noteExists = note.getId() != 0;

        if(hasContent) {
            note.setTitle(titleText.getText().toString());
            note.setContent(contentText.getText().toString());

            if (noteExists) {
                if(contentChanged) {
                    dbAdapter.updateNote(note);
                }
            } else {
                dbAdapter.addNote(note);
            }
        } else if(noteExists) {
            dbAdapter.deleteNote(note.getId());
        }
    }
}
