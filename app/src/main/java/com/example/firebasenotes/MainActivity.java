package com.example.firebasenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FloatingActionButton mAddButton;
    public int mADD_NEW_NOTE = 0;
    public int mEDIT_NEW_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        String userUid = mAuth.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("user_notes/" + userUid);

        mAddButton = findViewById(R.id.add_notes_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NotesActivity.class);
                intent.putExtra("NOTE_STATUS",mADD_NEW_NOTE);
                startActivityForResult(intent, mADD_NEW_NOTE);
            }
        });
        initialiseDisplayContent();
    }

    private void initialiseDisplayContent() {
        final RecyclerView recyclerNotes = (RecyclerView) findViewById(R.id.list_notes);
        final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this);
        recyclerNotes.setLayoutManager(notesLayoutManager);

        List<NoteInfo> notes = loadUserNotes();
        final NotesRecyclerAdapter notesRecyclerAdapter = new NotesRecyclerAdapter(this,notes);
        recyclerNotes.setAdapter(notesRecyclerAdapter);

    }

    private List<NoteInfo> loadUserNotes() {
        
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
}