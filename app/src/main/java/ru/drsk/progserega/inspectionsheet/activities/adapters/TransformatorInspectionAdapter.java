package ru.drsk.progserega.inspectionsheet.activities.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public class TransformatorInspectionAdapter extends BaseAdapter {

    private final Context context;
    private TransformerInspection inspection;
    private List<InspectionItem> inspectionItems;

    public void setInspection(TransformerInspection inspection) {
        this.inspection = inspection;
        if(inspection == null){
            inspectionItems = new ArrayList<>();
        }else {
            inspectionItems = inspection.getInspectionItems();
        }
    }

    public TransformatorInspectionAdapter(Context context, TransformerInspection inspection){
        this.context = context;
        this.inspection = inspection;
        this.inspectionItems = inspection.getInspectionItems();
    }

    @Override
    public int getItemViewType(int position) {
        return inspectionItems.get(position).getType().getValue();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return inspectionItems.size();
    }

    @Override
    public Object getItem(int position) {
        return inspectionItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return inspectionItems.get(position).getType() == InspectionItemType.ITEM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;
        InspectionItem inspectionItem = inspectionItems.get(position);
        switch (inspectionItem.getType()){
            case HEADER:
                rowView = inflater.inflate(R.layout.transf_inspection_header, parent, false);
                TextView headerTextView = (TextView) rowView.findViewById(R.id.transf_inspection_header_title);
                headerTextView.setText(inspectionItem.getName());
                TextView headerNumberTextView = (TextView) rowView.findViewById(R.id.transf_inspection_header_number);
                headerNumberTextView.setText(inspectionItem.getNumber());

                break;
            case ITEM:
                rowView = inflater.inflate(R.layout.transf_inspection_item, parent, false);
                TextView itemTextView = (TextView) rowView.findViewById(R.id.transf_inspection_item_title);
                itemTextView.setText(inspectionItem.getName());
                TextView itemNumberTextView = (TextView) rowView.findViewById(R.id.transf_inspection_item_number);
                itemNumberTextView.setText(inspectionItem.getNumber());

                String deffectText = "";
                TextView descriptionTextView = (TextView) rowView.findViewById(R.id.transf_inspection_item_description);

                if(inspectionItem.getDeffect() != null){
                    deffectText = inspectionItem.getDeffect().getDescription();
                    descriptionTextView.setText(deffectText);

                }
                descriptionTextView.setText(deffectText);

                RecyclerView list = (RecyclerView) rowView.findViewById(R.id.transf_inspection_photos);
                list.setLayoutManager(new LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false));
                list.setAdapter(new HorizontalAdapter(inspectionItem.getDeffect().getPhotos()));

//                GridView horizontalGridView = (GridView)rowView.findViewById(R.id.inspection_defect_photos);
//                horizontalGridView.setNumColumns(inspectionItem.getDeffect().getPhotos().size());
//
//                float scalefactor = this.context.getResources().getDisplayMetrics().density;
//                int imageWidth = (int) (48 * scalefactor);
//
//                //saveDeffectPhotos(deffect.getPhotos());
//
//                ImageAdapter imageAdapter = new ImageAdapter(this.context, inspectionItem.getDeffect().getPhotos(), imageWidth);
//                horizontalGridView.setAdapter(imageAdapter);




                break;

        }

        return rowView;
    }


}
