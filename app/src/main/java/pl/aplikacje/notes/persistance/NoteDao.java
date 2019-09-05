package pl.aplikacje.notes.persistance;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pl.aplikacje.notes.models.Note;

@Dao
public interface NoteDao {

    @Insert
    long[] insertNotes(Note...notes);



    @Query("SELECT*FROM notes")
    LiveData<List<Note>> getNotes();

    @Query("SELECT*FROM notes WHERE title LIKE :title")
    List<Note> getNoteWithCustomQuerry(String title);



    @Delete
    int delete(Note...notes);

    @Update
    int updateNote(Note...notes);





}
