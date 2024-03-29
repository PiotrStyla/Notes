package pl.aplikacje.notes;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import pl.aplikacje.notes.models.Note;
import pl.aplikacje.notes.persistance.NoteRepository;
import pl.aplikacje.notes.util.Utility;

public class NoteActivity extends AppCompatActivity
        implements View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENEBLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //ui components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mBackArrow;

    //vars
    private boolean mIsNewNote;
    private Note mInitialNote;
    private GestureDetector mGestureDecector;
    private int mMode;
    private NoteRepository mNoteRepository;
    private Note mFinalNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mLinedEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);

        mNoteRepository = new NoteRepository(this);

        setListeners();

        if (getIncomingIntent()) {
            //this is a new note (EDIT MODE)
            setNewNoteProperties();
            enableEditMode();

        } else {
            //this is not a new note (VIEW MODE)
            setNoteProperties();
            disableContentInteraction();
        }

    }

    private void saveChanges(){
        if(mIsNewNote){
            saveNewNote();
        }
        else{
            updateNote();
        }

    }
    private void updateNote(){
        mNoteRepository.updateNote(mFinalNote);
    }



    private void saveNewNote(){
        mNoteRepository.insertNoteTask(mFinalNote);

    }

    private void setListeners() {
        mLinedEditText.setOnTouchListener(this);
        mGestureDecector = new GestureDetector(this, this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_note")) {
            mInitialNote = getIntent().getParcelableExtra("selected_note");

            mFinalNote = new Note();
            mFinalNote.setTitle(mInitialNote.getTitle());
            mFinalNote.setContent(mInitialNote.getContent());
            mFinalNote.setTimeStamp(mInitialNote.getTimeStamp());
            mFinalNote.setId(mInitialNote.getId());


            mMode = EDIT_MODE_ENEBLED;
            mIsNewNote = false;
            return false;
        }
        mMode = EDIT_MODE_ENEBLED;

        mIsNewNote = true;
        return true;
    }



    private void disableContentInteraction() {
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void enableContentInteraction() {
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }
    private void enableEditMode() {
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENEBLED;
        enableContentInteraction();

    }
    private void disableEditMode() {
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();


        String temp = Objects.requireNonNull(mLinedEditText.getText()).toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");

        if (temp.length() > 0) {

            mFinalNote.setTitle(mEditTitle.getText().toString());
            mFinalNote.setContent(mLinedEditText.getText().toString());
            String timestamp = Utility.getCurrentTimeStamp();
            mFinalNote.setTimeStamp(timestamp);

            if ((!mFinalNote.getContent().equals(mInitialNote.getContent()))
                    || (!mFinalNote.getTitle().equals(mInitialNote.getTitle())) ){
                updateNote();
                saveChanges();


            }
        }
    }












    private void hideSoftKeyboard() {

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void setNoteProperties() {
        mViewTitle.setText(mInitialNote.getTitle());
        mEditTitle.setText(mInitialNote.getTitle());
        mLinedEditText.setText(mInitialNote.getContent());
    }

    private void setNewNoteProperties() {
        mViewTitle.setText("Note Title");
        mEditTitle.setText("Note Title");

        mInitialNote = new Note();
        mFinalNote = new Note();
        mInitialNote.setTitle("Note Title");
        mFinalNote.setTitle("Note Title");
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        return mGestureDecector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        enableEditMode();
        Log.d(TAG, "Double Tap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
                case R.id.toolbar_check: {
                    hideSoftKeyboard();
                    disableEditMode();
                    break;
                }
                case R.id.note_text_title: {
                    enableEditMode();
                    mEditTitle.requestFocus();
                    mEditTitle.setSelection(mEditTitle.length());
                    break;
                }
                case R.id.toolbar_back_arrow: {
                    finish();
                    break;

                }

        }
    }
    @Override
    public void onBackPressed () {
        if (mMode == EDIT_MODE_ENEBLED) {
            onClick(mCheck);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if(mMode==EDIT_MODE_ENEBLED){
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

