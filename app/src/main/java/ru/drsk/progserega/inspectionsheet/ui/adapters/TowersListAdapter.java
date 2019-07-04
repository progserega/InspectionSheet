package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Tower;

public class TowersListAdapter extends BaseAdapter {


    private final Context context;

    private List<Tower> towers;

    public TowersListAdapter(Context context, List<Tower> towers) {

        this.context = context;

        this.towers = towers;
    }
    @Override
    public int getCount() {
        return towers.size();
    }

    @Override
    public Object getItem(int position) {
        return towers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return towers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.transformers_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.transf_list_item_name);
        textView.setText(towers.get(position).getName());


        return rowView;
    }
}
