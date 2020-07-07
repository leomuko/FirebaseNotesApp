package com.example.firebasenotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<NoteInfo>  mNotes;
    private OnNoteListener mOnNoteListener;

    public NotesRecyclerAdapter(Context context, List<NoteInfo> notes, OnNoteListener onNoteListener) {
        mContext = context;
        mNotes = notes;
        mLayoutInflater = LayoutInflater.from(mContext);
        mOnNoteListener = onNoteListener;
    }

    public NoteInfo getNoteAt(int position){
        return mNotes.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.notes_item, parent,false);
        return new ViewHolder(itemView, mOnNoteListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mNoteTitleText;
        public final TextView mNoteDetailsText;
        public  ImageView mDeleteButton;
        OnNoteListener mOnNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            mNoteTitleText = (TextView) itemView.findViewById(R.id.item_note_title);
            mNoteDetailsText = (TextView) itemView.findViewById(R.id.item_note_details);
            mOnNoteListener = onNoteListener;
            mDeleteButton = (ImageView) itemView.findViewById(R.id.item_delete_button);

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnNoteListener.onDeleteClick(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            mOnNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);

        void onDeleteClick(int position);
    }
}
