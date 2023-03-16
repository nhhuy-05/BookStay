package vn.edu.fpt.projectprm392.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import vn.edu.fpt.projectprm392.R;

public class RoomDetailActivity extends AppCompatActivity {

    private String hotelId;
    private String nameOfHotel, locationOfHotel;
    private Date startDate,endDate;
    private int pricePerNight;

    private TextView tv_headerNameOfHotel,tv_nameOfHotel,tv_location,tv_pricePerNight,tv_totalPrice,tv_date, tv_descriptionOfHotel;

    private Button btn_bookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        // Get View
        tv_headerNameOfHotel = findViewById(R.id.tv_headerNameOfHotel);
        tv_nameOfHotel = findViewById(R.id.tv_nameOfHotel);
        tv_location = findViewById(R.id.tv_location);
        tv_pricePerNight = findViewById(R.id.tv_pricePerNight);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        tv_date = findViewById(R.id.tv_date);
        tv_descriptionOfHotel = findViewById(R.id.tv_descriptionOfHotel);
        btn_bookNow = findViewById(R.id.btn_bookNow);

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

        // TODO: Set image

        // Set onClick for btn_bookNow
        btn_bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Transfer data to BookingDetailActivity\
                Intent intent = new Intent(RoomDetailActivity.this, BookingDetailActivity.class);
                intent.putExtra("hotelId", hotelId);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                intent.putExtra("totalPrice", String.valueOf(totalPrice));
                startActivity(intent);
            }
        });

    }

    private int calculateTotalPrice(Date startDate, Date endDate, int pricePerNight) {
        // Calculate total price
        long diff = endDate.getTime() - startDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return (int) (diffDays * pricePerNight);
    }
}