package com.example.firebasenotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<NoteInfo>  mNotes;

    public NotesRecyclerAdapter(Context context, List<NoteInfo> notes) {
        mContext = context;
        mNotes = notes;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.notes_item, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteInfo note = mNotes.get(position);
        holder.mNoteTitleText.setText(note.getNoteTitle());
        holder.mNoteDetailsText.setText(note.getNoteDetails());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView mNoteTitleText;
        public final TextView mNoteDetailsText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNoteTitleText = (TextView) itemView.findViewById(R.id.item_note_title);
            mNoteDetailsText = (TextView) itemView.findViewById(R.id.item_note_details);
        }
    }
}
