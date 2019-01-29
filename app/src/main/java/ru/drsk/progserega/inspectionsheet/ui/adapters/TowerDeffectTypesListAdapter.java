package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerDeffectType;

public class TowerDeffectTypesListAdapter extends BaseAdapter {


    private final Context context;

    private List<TowerDeffectType> deffectTypes;

    public TowerDeffectTypesListAdapter(Context context, List<TowerDeffectType> deffectTypes) {

        this.context = context;

        this.deffectTypes = deffectTypes;
    }
    @Override
    public int getCount() {
        return deffectTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return deffectTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return deffectTypes.get(position).getTypeId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tower_deffect_types_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.tower_deffect_type_name);
        textView.setText(deffectTypes.get(position).getName());


        return rowView;
    }
}
