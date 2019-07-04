package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineTowerInspectionItem;

public class LineTowerInspectionItemsAdapter extends BaseAdapter {

    private Context context;
    private List<LineTowerInspectionItem> deffects;

    public LineTowerInspectionItemsAdapter(Context context, List<LineTowerInspectionItem> deffects) {
        this.context = context;
        this.deffects = deffects;
    }

    public void setDeffects(List<LineTowerInspectionItem> deffects) {
        this.deffects = deffects;
    }

    @Override
    public int getCount() {
        return deffects.size();
    }

    @Override
    public Object getItem(int position) {
        return deffects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return deffects.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tower_deffect_item, parent, false);

        final LineTowerInspectionItem inspectionItem = deffects.get(position);

        TextView headerTextView = (TextView) rowView.findViewById(R.id.line_tower_deffect_item_title);
        headerTextView.setText(inspectionItem.getType().getName());

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.line_tower_deffect_item_checkbox);
        checkBox.setChecked(inspectionItem.isActive());
        return rowView;
    }
}
