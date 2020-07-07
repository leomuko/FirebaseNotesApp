package com.example.firebasenotes;

public class NoteInfo {
    private int mNoteId = 0;
    private static int nextNumber = 0;
    private String mUID;
    private String mNoteTitle;
    private String mNoteDetails;

    public NoteInfo(){

    }

    public NoteInfo( String UID, String NoteTitle, String NoteDetails){
        mNoteId = nextNumber;
        nextNumber++;
        mUID = UID;
        mNoteTitle = NoteTitle;
        mNoteDetails = NoteDetails;
    }

    public int getNoteId() {
        return mNoteId;
    }

    public String getNoteDetails() {
        return mNoteDetails;
    }

    public String getUID() {
        return mUID;
    }

    public String getNoteTitle() {
        return mNoteTitle;
    }

    public void setNoteDetails(String noteDetails) {
        mNoteDetails = noteDetails;
    }

    public void setNoteId(int noteId) {
        mNoteId = noteId;
    }

    public void setNoteTitle(String noteTitle) {
        mNoteTitle = noteTitle;
    }

    public void setUID(String UID) {
        mUID = UID;
    }
}
