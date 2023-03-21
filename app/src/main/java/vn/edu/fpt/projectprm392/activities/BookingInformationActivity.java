package vn.edu.fpt.projectprm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import vn.edu.fpt.projectprm392.R;

public class BookingInformationActivity extends AppCompatActivity {

    private DatabaseReference bookingRef, hotelRef, districtRef, paymentRef,bookingPaymentRef;
    private FirebaseDatabase database;
    private TextView tv_name_hotel, tv_hotel_location, tv_adult, tv_child, tv_date_using, tv_payment_method, tv_payment_status, tv_total_price_hotel;
    private Button btn_cancel_booking,btn_backToFragmentBooking;
    private ImageView img_Hotel;

    private SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_information);

        // Database
        database = FirebaseDatabase.getInstance();
        bookingRef = database.getReference("Bookings");
        hotelRef = database.getReference("Hotels");
        districtRef = database.getReference("Districts");
        paymentRef = database.getReference("PaymentMethods");
        bookingPaymentRef = database.getReference("BookingPayments");

        // get data from intent
        String codeId = getIntent().getStringExtra("bookingId");

        // Get view
        tv_name_hotel = findViewById(R.id.tv_name_hotel);
        tv_hotel_location = findViewById(R.id.tv_hotel_location);
        tv_adult = findViewById(R.id.tv_adult);
        tv_child = findViewById(R.id.tv_child);
        tv_date_using = findViewById(R.id.tv_date_using);
        tv_payment_method = findViewById(R.id.tv_payment_method);
        tv_payment_status = findViewById(R.id.tv_payment_status);
        tv_total_price_hotel = findViewById(R.id.tv_total_price_hotel);
        btn_cancel_booking = findViewById(R.id.btn_cancel_booking);
        btn_backToFragmentBooking = findViewById(R.id.btn_backToFragmentBooking);
        img_Hotel = findViewById(R.id.img_Hotel);


        bookingRef.child(codeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_adult.setText(snapshot.child("adult").getValue().toString());
                tv_child.setText(snapshot.child("child").getValue().toString());
                tv_date_using.setText(formatter.format(snapshot.child("in_date").getValue(Date.class)) + " - " + formatter.format(snapshot.child("out_date").getValue(Date.class)));
                tv_total_price_hotel.setText(snapshot.child("total_price").getValue().toString());
                hotelRef.child(snapshot.child("hotelId").getValue().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        img_Hotel.setImageResource(getImageHotel(snapshot.child("name").getValue().toString()));
                        tv_name_hotel.setText(snapshot.child("name").getValue().toString());
                        districtRef.child(snapshot.child("districtId").getValue().toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                tv_hotel_location.setText(snapshot.child("name").getValue().toString());
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
                paymentRef.child(snapshot.child("payment_method_id").getValue().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tv_payment_method.setText(snapshot.child("type").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                bookingPaymentRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.child("bookingId").getValue().toString().equals(codeId)) {
                                bookingPaymentRef.child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.child("status").getValue().toString().equals("Completed")) {
                                            tv_payment_status.setText(R.string.paid);
                                        } else {
                                            tv_payment_status.setText(R.string.not_paid);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
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

        // check if booking is canceled or checked-out, then disable cancel button
        bookingRef.child(codeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("status").getValue().toString().equals("Canceled")) {
                    btn_cancel_booking.setText(R.string.canceled);
                    btn_cancel_booking.setTextColor(getResources().getColor(R.color.text_status_canceled));
                    btn_cancel_booking.setBackgroundColor(getResources().getColor(R.color.bg_status_canceled));
                    btn_cancel_booking.setEnabled(false);
                }
                if (snapshot.child("status").getValue().toString().equals("Checked-out")) {
                    btn_cancel_booking.setText(R.string.checked_out);
                    btn_cancel_booking.setTextColor(getResources().getColor(R.color.text_status_checked_out));
                    btn_cancel_booking.setBackgroundColor(getResources().getColor(R.color.bg_status_checked_out));
                    btn_cancel_booking.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_cancel_booking.setOnClickListener(v -> {
            bookingRef.child(codeId).child("status").setValue("Canceled");
            finish();
        });

        btn_backToFragmentBooking.setOnClickListener(v -> {
            finish();
        });


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