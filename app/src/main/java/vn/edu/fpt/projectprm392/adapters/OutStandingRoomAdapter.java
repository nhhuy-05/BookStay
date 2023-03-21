package vn.edu.fpt.projectprm392.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.BookingInformationActivity;
import vn.edu.fpt.projectprm392.models.Hotel;

public class OutStandingRoomAdapter extends RecyclerView.Adapter<OutStandingRoomAdapter.OutStandingRoomViewHolder> {
    private List<Hotel> lists;

    private List<Integer> listNumberOfBookings;

    public OutStandingRoomAdapter(List<Hotel> lists, List<Integer> listNumberOfBookings) {
        this.lists = lists;
        this.listNumberOfBookings = listNumberOfBookings;
    }

    private FirebaseDatabase database;
    private DatabaseReference hotelRef, districtRef;

    @NonNull
    @Override
    public OutStandingRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outstanding_hotel, parent, false);
        return new OutStandingRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutStandingRoomViewHolder holder, int position) {

        Hotel hotel = lists.get(position);
        holder.img_roomHotel.setImageResource(getImageHotel(hotel.getName()));
        holder.tv_nameHotel.setText(hotel.getName());
        districtRef.child(String.valueOf(hotel.getDistrictId())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.tv_locationHotel.setText(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.tv_numberOfBookings.setText(listNumberOfBookings.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class OutStandingRoomViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_roomHotel;
        private TextView tv_nameHotel, tv_locationHotel, tv_numberOfBookings;

        public OutStandingRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            // get view
            img_roomHotel = itemView.findViewById(R.id.img_roomHotel);
            tv_nameHotel = itemView.findViewById(R.id.tv_nameHotel);
            tv_locationHotel = itemView.findViewById(R.id.tv_locationHotel);
            tv_numberOfBookings = itemView.findViewById(R.id.tv_numberOfBookings);

            database = FirebaseDatabase.getInstance();
            hotelRef = database.getReference("Hotels");
            districtRef = database.getReference("Districts");
        }
    }
    public int getImageHotel(String nameHotel){
        if (nameHotel.equals("Hilton")){
            return R.drawable.img_hilton_hotel;
        }
        if (nameHotel.equals("Sheraton")){
            return R.drawable.img_sheraton_hotel;
        }
        if (nameHotel.equals("Marriott")){
            return R.drawable.img_marriott_hotel;
        }
        if (nameHotel.equals("Intercontinental")){
            return R.drawable.img_intercontinental_hotel;
        }
        if (nameHotel.equals("Novotel")){
            return R.drawable.img_novotel_hotel;
        }
        if (nameHotel.equals("Hyatt")){
            return R.drawable.img_hyatt_hotel;
        }
        if (nameHotel.equals("Ramada")){
            return R.drawable.img_ramada_hotel;
        }
        if (nameHotel.equals("Radisson")){
            return R.drawable.img_radisson_hotel;
        }
        if (nameHotel.equals("Renaissance")){
            return R.drawable.img_renaissance_hotel;
        }
        if (nameHotel.equals("Ritz Carlton")){
            return R.drawable.img_ritzcarlton_hotel;
        }
        return -1;
    }

}
