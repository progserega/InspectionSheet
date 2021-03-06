package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public class TransformerSpinnerAdapter extends BaseAdapter {

    private final Context context;

    private List<StationEquipmentInspection> transformers;

    public TransformerSpinnerAdapter(Context context, List<StationEquipmentInspection> transformers) {

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
        return transformers.get(position).getEquipment().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.transf_spinner_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.transf_spinner_item_name);
        textView.setText(transformers.get(position).getEquipment().getName());

        TextView slotTextView = (TextView) rowView.findViewById(R.id.transf_spinner_item_slot);
        Transformer transformer =(Transformer)transformers.get(position).getEquipment();
        slotTextView.setText(transformer.getPlace());


        TextView percentText = (TextView) rowView.findViewById(R.id.transf_inspection_done_percent);
        int percent = transformers.get(position).calcInspectionPercent();
        if (percent > 0) {
            percentText.setText(percent + "%");
        } else {
            percentText.setText("");
        }

        ImageView imageView = (ImageView) rowView.findViewById(R.id.transf_inspection_done_img);
        //if(transformers.get(position).isDone()) {
        if (percent > 0) {
            imageView.setImageResource(R.drawable.ic_done_png);
        } else {
            imageView.setImageResource(0);

        }


        return rowView;
    }
}
