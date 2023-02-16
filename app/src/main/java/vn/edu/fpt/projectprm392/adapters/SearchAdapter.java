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
import vn.edu.fpt.projectprm392.models.Room;

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.SearchHolder>{

    public List<Room> rooms;
    public SearchAdapter(List<Room> rooms){
        this.rooms=rooms;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chapters_search_result, parent, false);
        return new SearchHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        holder.imv_thumbnail.setImageResource(rooms.get(position).getThumbnail());
        holder.tv_name.setText(rooms.get(position).getName());
        holder.tv_location.setText(rooms.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder {

        public ImageView imv_thumbnail;
        public TextView tv_name;
        public TextView tv_location;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            imv_thumbnail = itemView.findViewById(R.id.imv_thumbnail);
            tv_name = itemView.findViewById(R.id.tv_name_search);
            tv_location = itemView.findViewById(R.id.tv_location);
        }
    }
}
