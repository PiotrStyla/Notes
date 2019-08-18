package pl.aplikacje.notes.persistance;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pl.aplikacje.notes.models.Note;


@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {


    public static final String DATABASE_NAME = "notes_db";

    private static NoteDataBase instance;

    static NoteDataBase getInstance(final Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDataBase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }
    public abstract NoteDao getNoteDao();
}
