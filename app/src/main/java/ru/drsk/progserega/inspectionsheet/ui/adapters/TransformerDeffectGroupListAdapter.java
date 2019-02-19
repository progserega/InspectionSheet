package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.DeffectValuesView;
import ru.drsk.progserega.inspectionsheet.activities.ExpandableHeightGridView;
import ru.drsk.progserega.inspectionsheet.activities.adapters.ImageAdapter;
import ru.drsk.progserega.inspectionsheet.activities.utility.MetricsUtils;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;


public class TransformerDeffectGroupListAdapter extends BaseAdapter {

    public interface photoEventsListener {
        void addPhotoBtnPressed(int deffectPosition);
        void photoItemClick(int position, List<InspectionPhoto> photos);
    }

    private photoEventsListener photoEventsListener;
    private Context context;

    private List<InspectionItem> deffects;


    private Map<Integer, List<String>> values;
    private Map<Integer, List<String>> subValues;
    private Map<Integer, String> comments;
    private Map<Integer, List<InspectionPhoto>> photos;


    public TransformerDeffectGroupListAdapter(Context context, List<InspectionItem> deffects, photoEventsListener listener) {
        this.context = context;
        this.deffects = deffects;
        photoEventsListener = listener;

        saveValues(deffects);
    }

    public void saveValues(List<InspectionItem> deffects) {
        values = new HashMap<>();
        subValues = new HashMap<>();
        comments = new HashMap<>();
        photos = new HashMap<>();
        for (int i = 0; i < deffects.size(); i++) {

            InspectionItem deffect = deffects.get(i);

            values.put(i, new ArrayList<>(deffect.getResult().getValues()));
            subValues.put(i, new ArrayList<>(deffect.getResult().getSubValues()));
            comments.put(i, deffect.getResult().getComment());
            photos.put(i, new ArrayList<>(deffect.getResult().getPhotos()));
        }
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
        return deffects.get(position).getValueId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.transformer_group_deffect_item, parent, false);

        InspectionItem inspectionItem = deffects.get(position);
        InspectionItemResult deffect = inspectionItem.getResult();

        TextView deffectNameTextView = (TextView) rowView.findViewById(R.id.add_defect_inspection_name);
        deffectNameTextView.setText(inspectionItem.getName());


        TextView deffectDescription = (TextView) rowView.findViewById(R.id.group_add_defect_description);
        deffectDescription.setText(comments.get(position));
        deffectDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                comments.put(position, s.toString());
            }
        });


        if (deffect.getPossibleResult() != null) {
            DeffectValuesView valuesView = new DeffectValuesView(
                    (LinearLayout) rowView.findViewById(R.id.add_defect_result_layout),
                    deffect.getPossibleResult(),
                    values.get(position),
                    this.context,
                    new DeffectValuesView.OnValueChangeListener() {
                        @Override
                        public void valuesChange(List<String> changedValues) {
                            values.put(position, changedValues);
                        }
                    });
            valuesView.build();
        }

        if (deffect.getPossibleSubresult() != null) {
            DeffectValuesView subValuesView = new DeffectValuesView(
                    (LinearLayout) rowView.findViewById(R.id.add_defect_result_layout),
                    deffect.getPossibleSubresult(),
                    subValues.get(position),
                    this.context,
                    new DeffectValuesView.OnValueChangeListener() {
                        @Override
                        public void valuesChange(List<String> changedValues) {
                            subValues.put(position, changedValues);
                        }
                    });
            subValuesView.build();
        }

        ExpandableHeightGridView gridview = (ExpandableHeightGridView) rowView.findViewById(R.id.add_defect_photos);
        gridview.setExpanded(true);

        int imageWidth = MetricsUtils.dpToPx(115, this.context);

        ImageAdapter imageAdapter = new ImageAdapter(this.context, photos.get(position), imageWidth);
        gridview.setAdapter(imageAdapter);

        final int deffectIdx = position;
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                photoEventsListener.photoItemClick(position, photos.get(deffectIdx));
            }
        });

        ImageButton addPhotoBtn = (ImageButton) rowView.findViewById(R.id.add_defect_photo_btn);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(context, "ADD PHOTO !!! "+ deffect.getDeffectType().getName(), Toast.LENGTH_LONG).show();
                photoEventsListener.addPhotoBtnPressed(position);
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            addPhotoBtn.setImageResource(R.drawable.ic_baseline_photo_camera_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            addPhotoBtn.setImageResource(R.drawable.ic_camera_png);
        }
        addPhotoBtn.invalidate();
        return rowView;
    }

    public void addPhoto(int position, String photoPath){
            photos.get(position).add(new InspectionPhoto(0, photoPath, context));
    }

    public Map<Integer, List<String>> getValues() {
        return values;
    }

    public Map<Integer, List<String>> getSubValues() {
        return subValues;
    }

    public Map<Integer, String> getComments() {
        return comments;
    }

    public Map<Integer, List<InspectionPhoto>> getPhotos() {
        return photos;
    }
}
