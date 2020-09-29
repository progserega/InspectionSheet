package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;

public class LineSectionDeffectsListAdapter extends BaseAdapter {

    public interface IDeffectSelectionListener {
        void onDeffectSelectionChange(int position, boolean isSelect);
    }

    public interface OnItemAboutClickListener {
        void OnItemAboutClick(LineSectionDeffect sectionDeffect);
    }
    private IDeffectSelectionListener selectionListener;
    private OnItemAboutClickListener onItemAboutClickListener;

    private Context context;

    private List<LineSectionDeffect> deffects;

    public LineSectionDeffectsListAdapter(Context context) {
        this.context = context;
        this.deffects = new ArrayList<>();
    }

    public void setSelectionListener(IDeffectSelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public IDeffectSelectionListener getSelectionListener() {
        return selectionListener;
    }

    public void setOnItemAboutClickListener(OnItemAboutClickListener onItemAboutClickListener) {
        this.onItemAboutClickListener = onItemAboutClickListener;
    }

    public void setDeffects(List<LineSectionDeffect> deffects) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tower_deffect_item, parent, false);

        final LineSectionDeffect sectionDeffect = deffects.get(position);

        TextView headerTextView = (TextView) rowView.findViewById(R.id.line_tower_deffect_item_title);
        headerTextView.setText(sectionDeffect.getDeffectType().getName());

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.line_tower_deffect_item_checkbox);
        checkBox.setChecked(sectionDeffect.getValue() == 1);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selectionListener != null) {
                    selectionListener.onDeffectSelectionChange(position, isChecked);
                }
            }
        });



        ImageView aboutBtn = (ImageView)  rowView.findViewById(R.id.line_tower_deffect_item__about);

        if(sectionDeffect.getDeffectType().getDeffectDescription() == null){
            aboutBtn.setVisibility(View.GONE);
        }

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemAboutClickListener.OnItemAboutClick(sectionDeffect);
            }
        });

        return rowView;
    }
}
