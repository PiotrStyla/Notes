package pl.aplikacje.notes.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.Month;
import java.util.ArrayList;

import pl.aplikacje.notes.R;
import pl.aplikacje.notes.models.Note;
import pl.aplikacje.notes.util.Utility;

//Adapter keeps vewHolder that transfers notes into recyclerView

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {

    private ArrayList<Note> mNote = new ArrayList<>();   //List of notes
    private OnNoteListener mOnNoteListener;              //Listener object

    //NotesRecyclerAdapter constructor

    public NotesRecyclerAdapter(ArrayList<Note> notes, OnNoteListener onNoteListener) {
        this.mNote = notes;
        this.mOnNoteListener = onNoteListener;
    }

    //onCreate METHOD 1 forced by extending class RecyclerView.Adapter...
    //This method inflates layout object of class View view - layout_note_list_item - returns ViewHolder object with view and onNoteListener
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_list_item,viewGroup,false);
        return new ViewHolder(view, mOnNoteListener);
    }
    //METHOD 2  - counts the number of viewHoder objects
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        try {
            String day = mNote.get(i).getTimeStamp();
            String month = mNote.get(i).getTimeStamp().substring(0,2);
            month = Utility.getMonthFromNumber(month);
            String year = mNote.get(i).getTimeStamp().substring(3);
            String timestamp = day;//+" "+month+" "+year;
            viewHolder.timestamp.setText(timestamp);
            viewHolder.title.setText(mNote.get(i).getTitle());



        }
        catch (NullPointerException e){

        }

    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, timestamp;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            timestamp = itemView.findViewById(R.id.note_timestamp);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }



}
