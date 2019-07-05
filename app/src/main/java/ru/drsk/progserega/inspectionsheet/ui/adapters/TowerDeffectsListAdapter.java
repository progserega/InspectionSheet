package ru.drsk.progserega.inspectionsheet.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;

@Deprecated
public class TowerDeffectsListAdapter extends BaseAdapter {

    public interface AddPhotoBtnListener{
        void addPhotoBtnPressed(TowerDeffect deffect);
    }

    private Context context;
    private List<TowerDeffect> deffects;

    private ImageAdapter imageAdapter;

    private AddPhotoBtnListener addPhotoBtnListener;


    public TowerDeffectsListAdapter(Context context, List<TowerDeffect> deffects , AddPhotoBtnListener addPhotoBtnListener) {

        this.context = context;
        this.deffects = deffects;
        this.addPhotoBtnListener = addPhotoBtnListener;

    }

    public void setDeffects(List<TowerDeffect> deffects) {
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
//        final TowerDeffect deffect = (TowerDeffect) getItem(position);
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View rowView = inflater.inflate(R.layout.tower_deffect_item, parent, false);
//        TextView itemTextView = (TextView) rowView.findViewById(R.id.tower_deffect_item_title);
//        itemTextView.setText(deffect.getDeffectType().getName());
//
//        ImageButton deleteImageView = (ImageButton) rowView.findViewById(R.id.tower_deffect_add_photo_btn);
//        deleteImageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //Toast.makeText(context, "ADD PHOTO !!! "+ deffect.getDeffectType().getName(), Toast.LENGTH_LONG).show();
//                addPhotoBtnListener.addPhotoBtnPressed(deffect);
//            }
//        });
//
////        RecyclerView list = (RecyclerView) rowView.findViewById(R.id.tower_defect_photos);
////        list.setLayoutManager(new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false));
////        list.setAdapter(new HorizontalPhotoListAdapter(deffect.getPhotos()));
//
//
//        ExpandableHeightGridView gridview = (ExpandableHeightGridView) rowView.findViewById(R.id.tower_defect_photos);
//        gridview.setExpanded(true);
//        int imageWidth = MetricsUtils.dpToPx(80, this.context);
//        ImageAdapter imageAdapter = new ImageAdapter(this.context, deffect.getPhotos(), imageWidth);
//        gridview.setAdapter(imageAdapter);
//
//        ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.tower_deffect_add_photo_btn);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            imageButton.setImageResource(R.drawable.ic_baseline_photo_camera_24px);
//        } else {
//            /* старые версии не поддерживают векторные рисунки */
//            imageButton.setImageResource(R.drawable.ic_camera_png);
//        }
//        imageButton.invalidate();
//
//        return rowView;
        return null;
    }

}
