package com.example.firebasenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotesActivity extends AppCompatActivity {

    private EditText mNoteTitle;
    private EditText mNoteDetails;
    private TextView mNoteText;
    private int mNoteStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNoteTitle = findViewById(R.id.note_Title);
        mNoteDetails = findViewById(R.id.note_Details);
        mNoteText = findViewById(R.id.notes_textView);

        Intent intent = getIntent();
        mNoteStatus = intent.getIntExtra("NOTE_STATUS",-1);

        if(mNoteStatus == 1){
            NoteInfo theNoteToEdt = intent.getParcelableExtra("NOTE_TO_EDIT");
            mNoteText.setText("EDIT NOTE");
            mNoteTitle.setText(theNoteToEdt.getNoteTitle());
            mNoteDetails.setText(theNoteToEdt.getNoteDetails());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mNoteStatus == 0){
            createNewNote();
        }else if(mNoteStatus == 1){
            editNote();
        }
    }

    private void editNote() {

        Intent intent = getIntent();
        NoteInfo noteToEdit = intent.getParcelableExtra("NOTE_TO_EDIT");
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user_notes/"+ noteToEdit.getUID()).child(noteToEdit.getNoteId());
        noteToEdit.setNoteDetails(mNoteDetails.getText().toString());
        noteToEdit.setNoteTitle(mNoteTitle.getText().toString());
        dbRef.setValue(noteToEdit);

    }

    private void createNewNote() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String userUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference dbRef = db.getReference("user_notes/" + userUid).push();
        NoteInfo newNote = new NoteInfo();
        String noteTitle = mNoteTitle.getText().toString();
        String noteDetails = mNoteDetails.getText().toString();
        String noteId = dbRef.getKey();

        if(noteDetails.isEmpty() || noteTitle.isEmpty()){
            Toast.makeText(this, "Cannot store an empty Note", Toast.LENGTH_SHORT).show();
        }else{
            newNote.setNoteId(noteId);
            newNote.setNoteTitle(noteTitle);
            newNote.setNoteDetails(noteDetails);
            newNote.setUID(userUid);
            dbRef.setValue(newNote);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
    }
}