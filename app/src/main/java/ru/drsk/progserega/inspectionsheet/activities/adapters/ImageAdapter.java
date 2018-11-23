package ru.drsk.progserega.inspectionsheet.activities.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectPhoto;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<DeffectPhoto> photos;
    private int imageWidthPx;

    public ImageAdapter(Context c, List<DeffectPhoto> photos, int width) {
        mContext = c;
        this.photos = photos;
        imageWidthPx = width;
    }

    public int getCount() {
        return photos.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
// <!--android:columnWidth="300dp"-->


            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidthPx, imageWidthPx));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(photos.get(position).getThumbnail());
        return imageView;
    }


}