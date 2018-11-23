package ru.drsk.progserega.inspectionsheet.activities.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectPhoto;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private List<DeffectPhoto> items;

    public HorizontalAdapter(List<DeffectPhoto> photos) {

        this.items = photos;

    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recicler_horizontal, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        holder.image.setImageBitmap(items.get(position).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.rec_image);
        }
    }
}