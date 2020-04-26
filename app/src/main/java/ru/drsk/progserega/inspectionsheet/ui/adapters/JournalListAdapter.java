package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;

public class JournalListAdapter extends
        RecyclerView.Adapter< JournalListAdapter.ViewHolder > {



    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView dateTextView;
        public TextView timeTextView;
        public TextView messageTextView;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            dateTextView = (TextView) itemView.findViewById(R.id.journal_item_date);
            timeTextView = (TextView) itemView.findViewById(R.id.journal_item_time);
            messageTextView = (TextView) itemView.findViewById(R.id.journal_item_message);

        }
    }

    // Store a member variable for the contacts
    private List<LogModel> mLogRecords;

    private Context context;

    // Pass in the contact array into the constructor
    public JournalListAdapter(List< LogModel > logRecords) {
        mLogRecords = logRecords;
    }

    public void setLogRecords(List< LogModel > logRecords) {
        mLogRecords = logRecords;
        this.notifyDataSetChanged();
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public JournalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.journal_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(JournalListAdapter.ViewHolder viewHolder, int position) {

        // Get the data model based on position
        LogModel logRecord =   mLogRecords.get(position);

        Date messageDate = logRecord.getDate();
        String pattern = "dd.MM.YYYY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(messageDate);


        TextView dateTextView = viewHolder.dateTextView;
        dateTextView.setText(date);

        pattern = "HH:mm:ss";
        simpleDateFormat = new SimpleDateFormat(pattern);
        String time = simpleDateFormat.format(messageDate);
        TextView timeTextView = viewHolder.timeTextView;
        timeTextView.setText(time);

        TextView messageTextView = viewHolder.messageTextView;
        messageTextView.setText(logRecord.getMessage());


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mLogRecords.size();
    }

//    public List<Equipment> getEquipments(){
//        return this.mLogRecords;
//    }

    private String floatFmt(float n) {
        if (n % 1 == 0) {
            return String.format("%.0f", n);
        } else {
            return String.format("%.1f", n);
        }
    }
}
