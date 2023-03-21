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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.RoomDetailActivity;
import vn.edu.fpt.projectprm392.models.Hotel;
import vn.edu.fpt.projectprm392.models.SavedHotel;

public class SavedPlaceAdapter extends RecyclerView.Adapter<SavedPlaceAdapter.SavedPlaceViewHolder> {

    private List<Hotel> mList;
    private List<String> nameOfLocation;
    private List<Date> startDate, endDate;
    private DatabaseReference saveRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM");

    public SavedPlaceAdapter(List<Hotel> mList, List<String> nameOfLocation, List<Date> startDate, List<Date> endDate) {
        this.mList = mList;
        this.nameOfLocation = nameOfLocation;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @NonNull
    @Override
    public SavedPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_hotel, parent, false);
        return new SavedPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPlaceViewHolder holder, int position) {
        //holder.img_room.setImageResource(lists.get(position).);
        holder.tv_name.setText(mList.get(position).getName());
        holder.tv_location.setText(nameOfLocation.get(position) + " \n" + formatter.format(startDate.get(position)) + " - " + formatter.format(endDate.get(position)));
        holder.tv_price.setText(String.valueOf(mList.get(position).getPrice()));

        // set icon save
        DatabaseReference hotelRef = saveRef.child(mAuth.getCurrentUser().getUid());
        hotelRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    holder.img_save.setImageResource(R.drawable.ic_saved);
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
                if (mAuth.getCurrentUser() == null){
                    // go to sign in activity
                    Toast.makeText(v.getContext(), "Please sign in to save hotel", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    // check if the hotel is saved or not
                    hotelRef.child(String.valueOf(mList.get(holder.getAdapterPosition()).getId())).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists() ) {
                                // if the hotel is saved, remove it from the list
                                hotelRef.child(String.valueOf(mList.get(holder.getAdapterPosition()).getId())).removeValue();
                                holder.img_save.setImageResource(R.drawable.ic_favorite);
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
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class SavedPlaceViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_room, img_save;
        public TextView tv_name;
        public TextView tv_location, tv_price;

        public SavedPlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            img_room = itemView.findViewById(R.id.img_room);
            tv_name = itemView.findViewById(R.id.tv_totalPriceHotel);
            tv_location = itemView.findViewById(R.id.tv_numberOfPersons);
            tv_price = itemView.findViewById(R.id.tv_price);
            img_save = itemView.findViewById(R.id.img_save);

            // Database
            database = FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();
            saveRef = database.getReference("SavedHotels");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(itemView.getContext(), RoomDetailActivity.class);
                    intent.putExtra("hotelId", String.valueOf(mList.get(getAdapterPosition()).getId()));
                    intent.putExtra("name", tv_name.getText().toString());
                    intent.putExtra("location", tv_location.getText().toString());
                    intent.putExtra("price", tv_price.getText().toString());
                    intent.putExtra("startDate", startDate.get(getAdapterPosition()));
                    intent.putExtra("endDate", endDate.get(getAdapterPosition()));

                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
