package vn.edu.fpt.projectprm392.adapters;

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

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.SearchHolder>{

    public List<Place> lists;
    public SearchAdapter(List<Place> lists){
        this.lists=lists;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_place, parent, false);
        return new SearchHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        holder.img_room.setImageResource(lists.get(position).getResourceId());
        holder.tv_name.setText(lists.get(position).getName());
        holder.tv_location.setText(lists.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder {

        public ImageView img_room;
        public TextView tv_name;
        public TextView tv_location;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            img_room = itemView.findViewById(R.id.img_room);
            tv_name = itemView.findViewById(R.id.tv_nameRoom);
            tv_location = itemView.findViewById(R.id.tv_address);
        }
    }
}