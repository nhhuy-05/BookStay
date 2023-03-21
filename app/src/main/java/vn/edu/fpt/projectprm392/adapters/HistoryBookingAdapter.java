package vn.edu.fpt.projectprm392.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.BookingInformationActivity;
import vn.edu.fpt.projectprm392.models.Booking;

public class HistoryBookingAdapter extends RecyclerView.Adapter<HistoryBookingAdapter.OngoingBookingViewHolder> {
    private List<Booking> lists;
    private SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM");
    private FirebaseDatabase database;
    private DatabaseReference bookingRef, hotelRef, districtRef;

    public HistoryBookingAdapter(List<Booking> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public OngoingBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_place, parent, false);
        return new OngoingBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingBookingViewHolder holder, int position) {
        // holder.img_place.setImageResource(lists.get(position).getResourceId());

        hotelRef.child(String.valueOf(lists.get(position).getHotelId())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.tv_HotelName.setText(snapshot.child("name").getValue().toString());
                districtRef.child(snapshot.child("districtId").getValue().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.tv_addressHotel.setText(snapshot.child("name").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.tv_totalPriceHotel.setText(String.valueOf(lists.get(position).getTotal_price()));
        holder.tv_dateUsingHotel.setText(formatter.format(lists.get(position).getIn_date()) + " - " + formatter.format(lists.get(position).getOut_date()));
        holder.tv_numberOfPersons.setText(lists.get(position).getAdult() + " adults, " + lists.get(position).getChild() + " children");
        holder.tag_status.setText(lists.get(position).getStatus());
        if (lists.get(position).getStatus().equals("Canceled")) {
            holder.tag_status.setTextColor(holder.tag_status.getResources().getColor(R.color.text_status_canceled));
            holder.tag_status.setBackgroundColor(holder.tag_status.getResources().getColor(R.color.bg_status_canceled));
        }
        if (lists.get(position).getStatus().equals("Checked-out")) {
            holder.tag_status.setTextColor(holder.tag_status.getResources().getColor(R.color.text_status_ongoing));
            holder.tag_status.setBackgroundColor(holder.tag_status.getResources().getColor(R.color.bg_status_ongoing));
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class OngoingBookingViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_place;
        private TextView tv_HotelName, tv_addressHotel, tv_totalPriceHotel, tv_dateUsingHotel, tv_numberOfPersons;
        private Button tag_status;


        public OngoingBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            database = FirebaseDatabase.getInstance();
            bookingRef = database.getReference("Bookings");
            hotelRef = database.getReference("Hotels");
            districtRef = database.getReference("Districts");
            img_place = (ImageView) itemView.findViewById(R.id.img_room);
            tv_HotelName = (TextView) itemView.findViewById(R.id.tv_HotelName);
            tv_addressHotel = (TextView) itemView.findViewById(R.id.tv_addressHotel);
            tv_totalPriceHotel = (TextView) itemView.findViewById(R.id.tv_totalPriceHotel);
            tv_dateUsingHotel = (TextView) itemView.findViewById(R.id.tv_dateUsingHotel);
            tv_numberOfPersons = (TextView) itemView.findViewById(R.id.tv_numberOfPersons);
            tag_status = (Button) itemView.findViewById(R.id.tag_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Transfer data to BookingInformationActivity
                    Intent intent = new Intent(itemView.getContext(), BookingInformationActivity.class);
                    intent.putExtra("bookingId", lists.get(getAdapterPosition()).getCodeId());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
