package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;

public class EquipmentListAdapter extends ArrayAdapter<Equipment> {

    private final Context context;
    private  List<Equipment> equipments;

    public EquipmentListAdapter(Context context, List<Equipment> equipments){
        super(context, -1, equipments);
        this.context = context;
        this.equipments = equipments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.equipment_list_item, parent, false);
        Equipment equipment = equipments.get(position);

        TextView textView = (TextView) rowView.findViewById(R.id.equipment_list_name);
        textView.setText(equipment.getName());


        String percent = "";
        String date = "";

        if(equipment.getInspectionPercent() > 0.01f){
            percent = floatFmt(equipment.getInspectionPercent()) + " %";

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
            date = dateFormat.format(equipment.getInspectionDate());
        }

        TextView inspectionPercentTextView = (TextView) rowView.findViewById(R.id.equipment_list_inspection_percent);
        inspectionPercentTextView.setText(percent);

        TextView inspectionDateTextView = (TextView) rowView.findViewById(R.id.equipment_list_inspection_date);
        inspectionDateTextView.setText(date);

        // change the icon for Windows and iPhone
        return rowView;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    private String floatFmt(float n){
        if(n % 1 == 0) {
            return String.format("%.0f", n);
        } else {
            return String.format("%.1f", n);
        }
    }
}
