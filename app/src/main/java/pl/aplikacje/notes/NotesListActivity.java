package pl.aplikacje.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import pl.aplikacje.notes.adapters.NotesRecyclerAdapter;
import pl.aplikacje.notes.models.Note;
import pl.aplikacje.notes.util.VerticalSpacingItemDecorator;

public class NotesListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener{

    private static final String TAG = "NotesListActivity";


    //UI components section
    private RecyclerView mRecyclerView;


    //vars secton
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNotesRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();
        insertFakeNotes();

        setSupportActionBar((Toolbar)findViewById(R.id.notes_toolbar));
        setTitle("Notesik Piotra");
    }

    private void insertFakeNotes(){
        for (int i = 0; i <1000 ; i++) {
            Note note = new Note();
            note.setTitle("Notatka nr:  " + i);
            note.setContent("Content #" + i);
            note.setTimeStamp("Lipiec 2019");
            mNotes.add(note);
        }
        mNotesRecyclerAdapter.notifyDataSetChanged();


    }


    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mNotesRecyclerAdapter = new NotesRecyclerAdapter(mNotes,this);
        mRecyclerView.setAdapter(mNotesRecyclerAdapter);

    }


    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked"+position);
        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);

    }
}
