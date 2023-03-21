package vn.edu.fpt.projectprm392.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import vn.edu.fpt.projectprm392.R;

public class RoomDetailActivity extends AppCompatActivity {

    private String hotelId;
    private String nameOfHotel, locationOfHotel;
    private Date startDate,endDate;
    private int pricePerNight;
    private TextView tv_headerNameOfHotel,tv_nameOfHotel,tv_location,tv_pricePerNight,tv_totalPrice,tv_date, tv_descriptionOfHotel;
    private Button btn_bookNow, btnBackToSearchResult;
    private ImageView img_Hotel;

    private FirebaseDatabase database;
    private DatabaseReference hotelRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        // Database
        database = FirebaseDatabase.getInstance();
        hotelRef = database.getReference("Hotels");

        // Get View
        tv_headerNameOfHotel = findViewById(R.id.tv_headerNameOfHotel);
        tv_nameOfHotel = findViewById(R.id.tv_name_hotel);
        tv_location = findViewById(R.id.tv_hotel_location);
        tv_pricePerNight = findViewById(R.id.tv_total_price);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        tv_date = findViewById(R.id.tv_date);
        tv_descriptionOfHotel = findViewById(R.id.tv_descriptionOfHotel);
        btn_bookNow = findViewById(R.id.btn_bookNow);
        btnBackToSearchResult = findViewById(R.id.btn_backToSearchResult);
        img_Hotel = findViewById(R.id.img_Hotel);

        // Get data from intent
        hotelId = getIntent().getStringExtra("hotelId");
        nameOfHotel = getIntent().getStringExtra("name");
        locationOfHotel = getIntent().getStringExtra("location");
        startDate = (Date) getIntent().getSerializableExtra("startDate");
        endDate = (Date) getIntent().getSerializableExtra("endDate");
        pricePerNight = Integer.parseInt(getIntent().getStringExtra("price"));

        // Calculate total price
        int totalPrice = calculateTotalPrice(startDate, endDate, pricePerNight);

        // Show Data
        tv_headerNameOfHotel.setText(nameOfHotel + " Hotel");
        tv_nameOfHotel.setText(nameOfHotel);
        tv_location.setText(locationOfHotel);
        tv_pricePerNight.setText(String.valueOf(pricePerNight));
        tv_totalPrice.setText(String.valueOf(totalPrice));
        // Format date like Tue, 1 Jan
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM");
        String dateIn = formatter.format(this.startDate);
        String dateOut = formatter.format(this.endDate);
        tv_date.setText(dateIn + " - " + dateOut);
        // TODO: Set description
        hotelRef.child(hotelId).child("description").get().addOnSuccessListener(dataSnapshot -> {
            String description = dataSnapshot.getValue(String.class);
            tv_descriptionOfHotel.setText(description);
        });

        // TODO: Set image
        img_Hotel.setImageResource(getImageHotel(nameOfHotel));

        // Set onClick for btn_bookNow
        btn_bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Toast.makeText(RoomDetailActivity.this, "Please login to book a room", Toast.LENGTH_SHORT).show();
                    // TODO: Go to SignIn activity
                    Intent intent = new Intent(RoomDetailActivity.this, SignInActivity.class);
                    startActivity(intent);
                    return;
                }else{
                    // Transfer data to BookingDetailActivity\
                    Intent intent = new Intent(RoomDetailActivity.this, BookingDetailActivity.class);
                    intent.putExtra("hotelId", hotelId);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("totalPrice", String.valueOf(totalPrice));
                    startActivity(intent);
                }
            }
        });

        // Back to SearchResultActivity
        btnBackToSearchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private int calculateTotalPrice(Date startDate, Date endDate, int pricePerNight) {
        // Calculate total price
        long diff = endDate.getTime() - startDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return (int) (diffDays * pricePerNight);
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