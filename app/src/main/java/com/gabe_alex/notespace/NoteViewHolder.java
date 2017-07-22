package com.gabe_alex.notespace;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView titleVTextIew;
    TextView dateTextIew;
    ItemClickListener itemClickListener;

    public NoteViewHolder(View itemView) {
        super(itemView);

        titleVTextIew = (TextView) itemView.findViewById(R.id.listNoteTitle);
        dateTextIew = (TextView) itemView.findViewById(R.id.listNoteDate);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
