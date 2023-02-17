package vn.edu.fpt.projectprm392.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.models.Place;

public class SavedPlaceAdapter extends RecyclerView.Adapter<SavedPlaceAdapter.SavedPlaceViewHolder> {

    private Context mContext;
    private List<Place> mList;

    public SavedPlaceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Place> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_place, parent, false);
        return new SavedPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPlaceViewHolder holder, int position) {
        Place place = mList.get(position);

        if (place != null){
            holder.img_saved_place.setImageResource(place.getResourceId());
            holder.tv_name_saved_place.setText(place.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public class SavedPlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_saved_place;
        private TextView tv_name_saved_place;

        public SavedPlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            img_saved_place = (ImageView) itemView.findViewById(R.id.img_room);
            tv_name_saved_place = (TextView) itemView.findViewById(R.id.tv_nameRoom);
        }
    }
}
