package pl.aplikacje.notes.persistance;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import pl.aplikacje.notes.models.Note;

public class NoteRepository {

    private NoteDataBase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDataBase.getInstance(context);
    }

    public void insertNoteTask(Note note){

    }

    public void updateNote(Note note){

    }

    public LiveData<List<Note>> retriveNotesTask(){
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note){

    }

}
