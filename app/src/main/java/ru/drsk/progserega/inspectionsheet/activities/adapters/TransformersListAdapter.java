package ru.drsk.progserega.inspectionsheet.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public class TransformersListAdapter extends BaseAdapter {


    private final Context context;

    private List<Transformer> transformers;

    public TransformersListAdapter(Context context, List<Transformer> transformers) {

        this.context = context;

        this.transformers = transformers;
    }
    @Override
    public int getCount() {
        return transformers.size();
    }

    @Override
    public Object getItem(int position) {
        return transformers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return transformers.get(position).getTypeId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.transformers_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.transf_list_item_name);
        textView.setText(transformers.get(position).getName());


        return rowView;
    }
}
