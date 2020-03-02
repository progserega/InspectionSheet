package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;

public class EquipmentRCListAdapter extends
        RecyclerView.Adapter< EquipmentRCListAdapter.ViewHolder > {

    /***** Creating OnItemClickListener *****/

    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private OnItemMapClickListener mapClickListener;
    // Define the listener interface
    public interface OnItemMapClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener

    public void setMapClickListener(OnItemMapClickListener mapClickListener) {
        this.mapClickListener = mapClickListener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView inspectionPercentTextView;
        public TextView inspectionDateTextView;
        public TextView showOnMaplabel;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.equipment_list_name);
            inspectionPercentTextView = (TextView) itemView.findViewById(R.id.equipment_list_inspection_percent);
            inspectionDateTextView = (TextView) itemView.findViewById(R.id.equipment_list_inspection_date);

            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });

            showOnMaplabel = (TextView)  itemView.findViewById(R.id.equipment_list_show_on_map);
            showOnMaplabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mapClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mapClickListener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }

    // Store a member variable for the contacts
    private List< Equipment > mEquipments;

    private Context context;

    // Pass in the contact array into the constructor
    public EquipmentRCListAdapter(List< Equipment > equipments) {
        mEquipments = equipments;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public EquipmentRCListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.equipment_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(EquipmentRCListAdapter.ViewHolder viewHolder, int position) {

        // Get the data model based on position
        Equipment equipment = mEquipments.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(equipment.getName());



        String percent = "";
        String date = "";

        if (equipment.getInspectionPercent() > 0.01f) {
            percent = floatFmt(equipment.getInspectionPercent()) + " %";

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
            date = dateFormat.format(equipment.getInspectionDate());
        }

        TextView inspectionPercentTextView = viewHolder.inspectionPercentTextView;
        inspectionPercentTextView.setText(percent);

        TextView inspectionDateTextView = viewHolder.inspectionDateTextView;
        inspectionDateTextView.setText(date);

//        if(equipment.getType() == EquipmentType.LINE){
//            viewHolder.showOnMaplabel.setVisibility(View.GONE);
//        }
//        else{
//            viewHolder.showOnMaplabel.setVisibility(View.VISIBLE);
//        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mEquipments.size();
    }

    public List<Equipment> getEquipments(){
        return this.mEquipments;
    }

    private String floatFmt(float n) {
        if (n % 1 == 0) {
            return String.format("%.0f", n);
        } else {
            return String.format("%.1f", n);
        }
    }
}
