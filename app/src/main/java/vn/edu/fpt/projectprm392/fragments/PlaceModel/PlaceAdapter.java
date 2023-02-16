package vn.edu.fpt.projectprm392.fragments.PlaceModel;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.fpt.projectprm392.R;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private Context mContext;
    private List<Place> mList;

    public PlaceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Place> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_savedplace, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = mList.get(position);

        if (place != null){
            holder.img_place.setImageResource(place.getResouceId());
            holder.tv_name_place.setText(place.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_place;
        private TextView tv_name_place;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            img_place = (ImageView) itemView.findViewById(R.id.img_place);
            tv_name_place = (TextView) itemView.findViewById(R.id.tv_name_place);
        }
    }
}
