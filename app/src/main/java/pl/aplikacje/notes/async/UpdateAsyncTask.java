package pl.aplikacje.notes.async;

import android.os.AsyncTask;

import pl.aplikacje.notes.models.Note;
import pl.aplikacje.notes.persistance.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {


    private NoteDao mNoteDao;
    public UpdateAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.updateNote(notes);
        return null;
    }
}
