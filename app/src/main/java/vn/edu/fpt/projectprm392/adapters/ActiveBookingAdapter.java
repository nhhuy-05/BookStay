package vn.edu.fpt.projectprm392.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.models.Place;

public class ActiveBookingAdapter extends RecyclerView.Adapter<ActiveBookingAdapter.ActiveBookingViewHolder> {
    private List<Place> lists;

    public ActiveBookingAdapter(List<Place> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ActiveBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_place, parent, false);
        return new ActiveBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveBookingViewHolder holder, int position) {
        holder.img_place.setImageResource(lists.get(position).getResourceId());
        holder.tv_name_place.setText(lists.get(position).getName());
        holder.tv_address.setText(lists.get(position).getAddress());
        holder.tv_booking_date.setText("8 May - 12 May");
        holder.tv_roomType.setText(lists.get(position).getRoomType());
        holder.tag_status.setText("Check-in");
        holder.tag_status.setTextColor(holder.tag_status.getResources().getColor(R.color.text_status_checkin));
        holder.tag_status.setBackgroundColor(holder.tag_status.getResources().getColor(R.color.bg_status_checkin));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ActiveBookingViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_place;
        private TextView tv_name_place;
        private TextView tv_address;
        private TextView tv_booking_date;
        private TextView tv_roomType;
        private Button tag_status;

        public ActiveBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            img_place = (ImageView) itemView.findViewById(R.id.img_room);
            tv_name_place = (TextView) itemView.findViewById(R.id.tv_nameRoom);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_booking_date = (TextView) itemView.findViewById(R.id.tv_bookDate);
            tv_roomType = (TextView) itemView.findViewById(R.id.tv_roomType);
            tag_status = (Button) itemView.findViewById(R.id.tag_status);
        }
    }

}
