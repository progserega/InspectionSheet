package ru.drsk.progserega.inspectionsheet.ui.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.ui.activities.TouchImageView;

public class FullScreenImageAdapter extends PagerAdapter {

    public interface DeletePhotoListener{
        void deletePhoto(int position);
    }
    private Activity _activity;
    private List<InspectionPhoto> inspectionPhotos;
    private LayoutInflater inflater;


    private DeletePhotoListener deletePhotoListener;

    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  List<InspectionPhoto> photos,
                                  DeletePhotoListener deletePhotoListener) {
        this._activity = activity;
        this.inspectionPhotos = photos;
        this.deletePhotoListener = deletePhotoListener;
    }

    @Override
    public int getCount() {
        return this.inspectionPhotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //ImageView imgDisplay;
        TouchImageView imgDisplay;
        Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        //imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(inspectionPhotos.get(position).getPath(), options);
        imgDisplay.setImageBitmap(bitmap);

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        ImageButton deleteBtn = (ImageButton)viewLayout.findViewById(R.id.delete_photo_ib);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_activity, "Удалить фотографию\n"+inspectionPhotos.get(position).getPath(), Toast.LENGTH_SHORT).show();

                deletePhotoListener.deletePhoto(position);
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }
}