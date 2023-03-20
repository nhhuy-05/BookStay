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
import vn.edu.fpt.projectprm392.activities.SignInActivity;
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
        //holder.img_room.setImageResource(lists.get(position).);
        holder.tv_name.setText(lists.get(position).getName());
        holder.tv_location.setText(nameOfLocation);
        holder.tv_price.setText(String.valueOf(lists.get(position).getPrice()));


        // set icon save
        DatabaseReference hotelRef = saveRef.child(String.valueOf(lists.get(holder.getAdapterPosition()).getId()));
        hotelRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && FirebaseAuth.getInstance().getCurrentUser() != null) {
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
                    hotelRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                hotelRef.removeValue();
                                holder.img_save.setImageResource(R.drawable.ic_favorite);
                            } else {
                                // get hotel by id and save to saveRef with startDate and endDate
                                SavedHotel savedHotel = new SavedHotel(mAuth.getCurrentUser().getUid(),lists.get(holder.getAdapterPosition()), startDate, endDate,nameOfLocation);
                                hotelRef.setValue(savedHotel);
                                // set to icon saved
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
            tv_name = itemView.findViewById(R.id.tv_nameRoom);
            tv_location = itemView.findViewById(R.id.tv_address);
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
}