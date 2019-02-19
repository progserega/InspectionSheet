package ru.drsk.progserega.inspectionsheet.activities.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;

public class HorizontalPhotoListAdapter extends RecyclerView.Adapter<HorizontalPhotoListAdapter.HorizontalViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(InspectionPhoto item, int position);
    }

    private List<InspectionPhoto> items;
    private OnItemClickListener onItemClickListener;


    public void setItems(List<InspectionPhoto> items) {
        this.items = items;
    }

    public HorizontalPhotoListAdapter(List<InspectionPhoto> photos, OnItemClickListener listener) {

        this.items = photos;
        this.onItemClickListener = listener;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recicler_horizontal, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        //holder.image.setImageBitmap(items.get(position).getThumbnail());
        holder.bind(items.get(position), position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.rec_image);
        }

        public void bind(final InspectionPhoto item, final int position, final OnItemClickListener listener) {

            image.setImageBitmap(item.getThumbnail());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //Toast.makeText(itemView.getContext(), "TAP ON PHOTO  "+ item.getPath(), Toast.LENGTH_LONG).show();
                    listener.onItemClick(item, position);
                }
            });
        }
    }
}