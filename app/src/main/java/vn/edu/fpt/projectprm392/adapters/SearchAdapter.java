package vn.edu.fpt.projectprm392.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.RoomDetailActivity;
import vn.edu.fpt.projectprm392.models.Hotel;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    public List<Hotel> lists;
    private String nameOfLocation;

    private Date startDate, endDate;

    public SearchAdapter(List<Hotel> lists, String nameOfLocation, Date startDate, Date endDate) {
        this.lists = lists;
        this.nameOfLocation = nameOfLocation;
        this.startDate = startDate;
        this.endDate = endDate;
    }



    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_hotel, parent, false);
        return new SearchHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        //holder.img_room.setImageResource(lists.get(position).);
        holder.tv_name.setText(lists.get(position).getName());
        holder.tv_location.setText(nameOfLocation);
        holder.tv_price.setText(String.valueOf(lists.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder {

        public ImageView img_room;
        public TextView tv_name;
        public TextView tv_location, tv_price;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            img_room = itemView.findViewById(R.id.img_room);
            tv_name = itemView.findViewById(R.id.tv_nameRoom);
            tv_location = itemView.findViewById(R.id.tv_address);
            tv_price = itemView.findViewById(R.id.tv_price);

            // Pass tv_name, tv_location, tv_price, startDate, endDate to RoomDetailActivity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), RoomDetailActivity.class);
                    intent.putExtra("hotelId", String.valueOf(lists.get(getAdapterPosition()).getId()));
                    intent.putExtra("name", tv_name.getText().toString());
                    intent.putExtra("location", tv_location.getText().toString());
                    intent.putExtra("price", tv_price.getText().toString());
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);

                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }


}