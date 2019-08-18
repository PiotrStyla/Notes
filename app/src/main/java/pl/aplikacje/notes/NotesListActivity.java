package pl.aplikacje.notes;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.aplikacje.notes.adapters.NotesRecyclerAdapter;
import pl.aplikacje.notes.models.Note;
import pl.aplikacje.notes.persistance.NoteRepository;
import pl.aplikacje.notes.util.VerticalSpacingItemDecorator;

public class NotesListActivity extends AppCompatActivity
        implements NotesRecyclerAdapter.OnNoteListener, View.OnClickListener{

    private static final String TAG = "NotesListActivity";


    //UI components section
    private RecyclerView mRecyclerView;


    //vars secton
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNotesRecyclerAdapter;
    private NoteRepository mNoteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.fab).setOnClickListener(this);

        mNoteRepository = new NoteRepository(this);

        initRecyclerView();
        retriveNotes();
       // insertFakeNotes();

        setSupportActionBar((Toolbar)findViewById(R.id.notes_toolbar));
        setTitle("Notesik Piotra");
    }

    private void retriveNotes(){

        mNoteRepository.retriveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if(mNotes.size()>0){
                    mNotes.clear();
                }
                if(notes != null){
                    mNotes.addAll(notes);
                }
                mNotesRecyclerAdapter.notifyDataSetChanged();

            }
        });
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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note note){
        mNotes.remove(note);
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));

        }
    };
}
