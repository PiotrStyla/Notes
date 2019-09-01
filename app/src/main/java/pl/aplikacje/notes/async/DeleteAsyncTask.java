package pl.aplikacje.notes.async;

import android.os.AsyncTask;

import pl.aplikacje.notes.models.Note;
import pl.aplikacje.notes.persistance.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {


    private NoteDao mNoteDao;
    public DeleteAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.delete(notes);
        return null;
    }
}
