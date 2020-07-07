package com.example.firebasenotes;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteInfo implements Parcelable {
    private String mNoteId ;
    private String mUID;
    private String mNoteTitle;
    private String mNoteDetails;

    public NoteInfo(){

    }

    public NoteInfo( String UID, String NoteTitle, String NoteDetails){
        mUID = UID;
        mNoteTitle = NoteTitle;
        mNoteDetails = NoteDetails;
    }

    protected NoteInfo(Parcel in) {
        mNoteId = in.readString();
        mUID = in.readString();
        mNoteTitle = in.readString();
        mNoteDetails = in.readString();
    }

    public static final Creator<NoteInfo> CREATOR = new Creator<NoteInfo>() {
        @Override
        public NoteInfo createFromParcel(Parcel in) {
            return new NoteInfo(in);
        }

        @Override
        public NoteInfo[] newArray(int size) {
            return new NoteInfo[size];
        }
    };

    public String getNoteId() {
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

    public void setNoteId(String noteId) {
        mNoteId = noteId;
    }

    public void setNoteTitle(String noteTitle) {
        mNoteTitle = noteTitle;
    }

    public void setUID(String UID) {
        mUID = UID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNoteId);
        dest.writeString(mUID);
        dest.writeString(mNoteTitle);
        dest.writeString(mNoteDetails);
    }
}
