package com.gabe_alex.notespace;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabe_alex.notespace.database.DbAdapter;
import com.gabe_alex.notespace.database.DbConstants;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    protected Context context;
    protected DbAdapter dbAdapter;

    public NoteAdapter(Context context) {
        this.context = context;
        this.dbAdapter = new DbAdapter(context);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = dbAdapter.getNoteFromPosition(position, false);
        holder.titleVTextIew.setText(!note.getTitle().isEmpty() ? note.getTitle() : context.getString(R.string.missing_title_placeholder));
        holder.dateTextIew.setText(Utils.getRelativeTimespanString(context, note.getDate().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra(DbConstants.NOTE_ID, dbAdapter.getNoteIdFromPosition(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbAdapter.getNotesCount();
    }
}
