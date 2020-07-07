package com.example.firebasenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener {

    private FirebaseAuth mAuth;
    private FloatingActionButton mAddButton;
    public int mADD_NEW_NOTE = 0;
    public int mEDIT_NEW_NOTE = 1;
    private DatabaseReference mDbRef;
    private List<NoteInfo> mNotesList;
    private NotesRecyclerAdapter mNotesRecyclerAdapter;
    private RecyclerView mRecyclerNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        String userUid = mAuth.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        mDbRef = db.getReference("user_notes/" + userUid);

        mAddButton = findViewById(R.id.add_notes_button);
        mNotesList = new ArrayList<>();
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NotesActivity.class);
                intent.putExtra("NOTE_STATUS",mADD_NEW_NOTE);
                startActivity(intent);
            }
        });

    }

    private void initialiseDisplayContent() {
        mRecyclerNotes = (RecyclerView) findViewById(R.id.list_notes);
        final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this);
        mRecyclerNotes.setLayoutManager(notesLayoutManager);


        mNotesRecyclerAdapter = new NotesRecyclerAdapter(this,mNotesList, this);
        mRecyclerNotes.setAdapter(mNotesRecyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserNotes();
    }

    private List<NoteInfo> loadUserNotes() {
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mNotesList.clear();
                for(DataSnapshot notesSnapShot: snapshot.getChildren()){
                    NoteInfo note = notesSnapShot.getValue(NoteInfo.class);
                    mNotesList.add(note);
                }
                initialiseDisplayContent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                mAuth.signOut();
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClick(int position) {
        NoteInfo ourNote = mNotesRecyclerAdapter.getNoteAt(position);
        Intent intent = new Intent(this, NotesActivity.class);
        intent.putExtra("NOTE_STATUS", mEDIT_NEW_NOTE);
        intent.putExtra("NOTE_TO_EDIT", ourNote);

        Log.d("Main", ourNote.getNoteTitle());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        NoteInfo ourNote = mNotesRecyclerAdapter.getNoteAt(position);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user_notes/"+ ourNote.getUID()).child(ourNote.getNoteId());
        dbRef.removeValue();

        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
        loadUserNotes();
    }
}