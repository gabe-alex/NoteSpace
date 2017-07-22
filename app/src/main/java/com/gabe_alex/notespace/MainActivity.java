package com.gabe_alex.notespace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(NoteActivity.class);
            }
        });

        noteAdapter = new NoteAdapter(this);
        RecyclerView notesList = (RecyclerView) findViewById(R.id.notesList);
        notesList.setLayoutManager(new LinearLayoutManager(this));
        notesList.setItemAnimator(new DefaultItemAnimator());
        notesList.setAdapter(noteAdapter);
    }

    public void launchActivity(Class classType) {
        Intent intent = new Intent(this, classType);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}
