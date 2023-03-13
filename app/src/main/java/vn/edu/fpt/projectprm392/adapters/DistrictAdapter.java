package vn.edu.fpt.projectprm392.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.models.District;
import vn.edu.fpt.projectprm392.models.Item;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder> {
    private List<District> mList;

    public DistrictAdapter(List<District> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_location, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
        District district = mList.get(position);
        if (district != null){
            holder.tv_description.setText(district.getName());
            holder.tv_districtName.setText(district.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DistrictViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_districtName;
        private ImageView img_district;
        private TextView tv_description;

        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_districtName = itemView.findViewById(R.id.tv_districtName);
            img_district = itemView.findViewById(R.id.img_district);
            tv_description = itemView.findViewById(R.id.tv_description);
            
        }
    }
}
