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

    }

    private void createNewNote() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String userUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference dbRef = db.getReference("user_notes/" + userUid).push();
        NoteInfo newNote = new NoteInfo();
        String noteTitle = mNoteTitle.getText().toString();
        String noteDetails = mNoteDetails.getText().toString();

        if(noteDetails.isEmpty() || noteTitle.isEmpty()){
            Toast.makeText(this, "Cannot store an empty Note", Toast.LENGTH_SHORT).show();
        }else{
            newNote.setNoteTitle(noteTitle);
            newNote.setNoteDetails(noteDetails);
            newNote.setUID(userUid);
            dbRef.setValue(newNote);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
    }
}