package ru.drsk.progserega.inspectionsheet.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;

public class EquipmentListAdapter extends ArrayAdapter<Equipment> {

    private final Context context;
    private  List<Equipment> values;

    EquipmentListAdapter(Context context, List<Equipment> values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.equipment_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView);


        textView.setText(values.get(position).getName());
        // change the icon for Windows and iPhone
        return rowView;
    }

    public List<Equipment> getValues() {
        return values;
    }
}
