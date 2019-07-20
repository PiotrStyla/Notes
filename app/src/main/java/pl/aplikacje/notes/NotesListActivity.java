package pl.aplikacje.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.aplikacje.notes.models.Note;

public class NotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Note note = new Note();
    }
}
