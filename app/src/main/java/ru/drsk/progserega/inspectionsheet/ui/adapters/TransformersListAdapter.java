package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentModel;

public class TransformersListAdapter extends BaseAdapter {


    private final Context context;

    private List<EquipmentModel> transformerTypes;

    public TransformersListAdapter(Context context, List<EquipmentModel> transformerTypes) {

        this.context = context;

        this.transformerTypes = transformerTypes;
    }
    @Override
    public int getCount() {
        return transformerTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return transformerTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return transformerTypes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.transformers_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.transf_list_item_name);
        textView.setText(transformerTypes.get(position).getName());


        return rowView;
    }
}
