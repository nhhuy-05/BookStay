package vn.edu.fpt.projectprm392.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.RoomDetailActivity;
import vn.edu.fpt.projectprm392.models.Hotel;
import vn.edu.fpt.projectprm392.models.SavedHotel;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    public List<Hotel> lists;
    private String nameOfLocation;
    private Date startDate, endDate;
    private DatabaseReference saveRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

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
        holder.img_room.setImageResource(getImageHotel(lists.get(position).getName()));
        holder.tv_name.setText(lists.get(position).getName());
        holder.tv_location.setText(nameOfLocation);
        holder.tv_price.setText(String.valueOf(lists.get(position).getPrice()));

        // In the SavedHotel Reference, each user has a child node with the same name as the user's UID, in that child node,
        // there is a list of hotelId that the user saved
        if (mAuth.getCurrentUser() == null) {
            //Toast.makeText(holder.itemView.getContext(), "Please sign in to search", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference hotelRef = saveRef.child(mAuth.getCurrentUser().getUid());
        hotelRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.getKey().equals(mAuth.getCurrentUser().getUid())){
                        for (DataSnapshot child : snapshot.getChildren()) {
                            SavedHotel savedHotel = child.getValue(SavedHotel.class);
                            if (savedHotel.getHotel().getId() == lists.get(holder.getAdapterPosition()).getId()) {
                                holder.img_save.setImageResource(R.drawable.ic_saved);
                                break;
                            } else {
                                holder.img_save.setImageResource(R.drawable.ic_favorite);
                            }
                        }
                    }
                } else {
                    holder.img_save.setImageResource(R.drawable.ic_favorite);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
        holder.img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check exist current user, if not exist, go to login
                if (mAuth.getCurrentUser() == null) {
                    // go to sign in activity
                    Toast.makeText(v.getContext(), "Please sign in to save hotel", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // check if the hotel is saved or not
                    hotelRef.child(String.valueOf(lists.get(holder.getAdapterPosition()).getId())).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists() && snapshot.getValue(SavedHotel.class).getUserId().equals(mAuth.getCurrentUser().getUid())) {
                                // if the hotel is saved, remove it from the list
                                hotelRef.child(String.valueOf(lists.get(holder.getAdapterPosition()).getId())).removeValue();
                                holder.img_save.setImageResource(R.drawable.ic_favorite);
                            } else {
                                // if the hotel is not saved, add it to the list
                                hotelRef.child(String.valueOf(lists.get(holder.getAdapterPosition()).getId())).setValue(new SavedHotel(mAuth.getCurrentUser().getUid(), lists.get(holder.getAdapterPosition()), startDate, endDate, nameOfLocation));
                                holder.img_save.setImageResource(R.drawable.ic_saved);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder {

        public ImageView img_room, img_save;
        public TextView tv_name;
        public TextView tv_location, tv_price;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            img_room = itemView.findViewById(R.id.img_room);
            tv_name = itemView.findViewById(R.id.tv_totalPriceHotel);
            tv_location = itemView.findViewById(R.id.tv_numberOfPersons);
            tv_price = itemView.findViewById(R.id.tv_price);
            img_save = itemView.findViewById(R.id.img_save);

            // Database
            database = FirebaseDatabase.getInstance();
            saveRef = database.getReference("SavedHotels");
            mAuth = FirebaseAuth.getInstance();

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