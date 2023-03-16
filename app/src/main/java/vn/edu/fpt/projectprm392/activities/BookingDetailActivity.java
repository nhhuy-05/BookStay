package vn.edu.fpt.projectprm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.models.Booking;
import vn.edu.fpt.projectprm392.models.User;
import vn.edu.fpt.projectprm392.network.JavaMailAPI;

public class BookingDetailActivity extends AppCompatActivity {

    private String hotelId, totalPrice, userId;
    private Date startDate, endDate;
    private EditText edt_nameOfPersonBooking, edt_phoneOfPerson, edt_emailPerson, edt_numPerson, edt_numChildren, etCardPerson, etCardNumber, etCardExpiry, etCardCvv;
    private String nameOfPersonBooking, phoneOfPerson, emailPerson, cardPerson, cardNumber, cardExpiry, cardCvv, numPerson, numChildren;
    private RadioGroup paymentOptions;
    private RadioButton payAtHotel, cardPayment;
    private Button btn_booking;
    private LinearLayout cardPaymentSection;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userRef, bookingRef, hotelRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
        bookingRef = database.getReference("Bookings");
        hotelRef = database.getReference("Hotels");


        // Get View
        edt_nameOfPersonBooking = findViewById(R.id.edt_nameOfPersonBooking);
        edt_phoneOfPerson = findViewById(R.id.edt_phoneOfPerson);
        edt_emailPerson = findViewById(R.id.edt_emailPerson);
        edt_numPerson = findViewById(R.id.edt_numPerson);
        edt_numChildren = findViewById(R.id.edt_numChildren);
        btn_booking = findViewById(R.id.btn_booking);
        etCardPerson = findViewById(R.id.et_card_person);
        etCardNumber = findViewById(R.id.et_card_number);
        etCardExpiry = findViewById(R.id.et_expiry_date);
        etCardCvv = findViewById(R.id.et_cvv);
        paymentOptions = findViewById(R.id.payment_radioGroup);
        payAtHotel = findViewById(R.id.pay_at_hotel_radiobutton);
        cardPayment = findViewById(R.id.card_payment_radiobutton);
        cardPaymentSection = findViewById(R.id.card_payment_layout);

        // Get data from intent
        hotelId = getIntent().getStringExtra("hotelId");
        Toast.makeText(this, hotelId, Toast.LENGTH_SHORT).show();
        startDate = (Date) getIntent().getSerializableExtra("startDate");
        endDate = (Date) getIntent().getSerializableExtra("endDate");
        totalPrice = getIntent().getStringExtra("totalPrice");

        // Display the card payment section only when the card payment option is selected
        paymentOptions.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.card_payment_radiobutton) {
                cardPaymentSection.setVisibility(View.VISIBLE);
            } else {
                cardPaymentSection.setVisibility(View.GONE);
            }
        });

        // Credit card name formatting
        etCardPerson.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                EditText editText = (EditText) v;
                String text = editText.getText().toString().toUpperCase();
                editText.setText(text);
                editText.setSelection(text.length()); // to set the cursor at the end
                return false;
            }
        });

        // Credit card number formatting
        etCardNumber.addTextChangedListener(new TextWatcher() {
            private boolean spaceDeleted = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String cleanString = s.toString().replaceAll("\\s", "");
                String formattedString = "";
                int length = cleanString.length();
                for (int i = 0; i < length; i++) {
                    if (i % 4 == 0 && i > 0) {
                        formattedString += " ";
                    }
                    formattedString += cleanString.charAt(i);
                }
                etCardNumber.removeTextChangedListener(this);
                etCardNumber.setText(formattedString);
                etCardNumber.setSelection(formattedString.length());
                etCardNumber.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && s.toString().substring(s.length() - 1).equals(" ")) {
                    spaceDeleted = true;
                } else {
                    spaceDeleted = false;
                }
            }
        });

        // Credit card expiry date formatting
        etCardExpiry.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2 && !s.toString().contains("/")) {
                    s.insert(2, "/");
                }
            }
        });

        // Binding Name, Phone, Email  of Current User to Fields of Booking Detail if User is Logged In
        if (mAuth.getCurrentUser() != null) {
            String emailOfCurrentUser = mAuth.getCurrentUser().getEmail();
            getUserFromDatabase(emailOfCurrentUser);
            // Set the field of Email to be uneditable
            edt_emailPerson.setEnabled(false);
        }

        // If User is not Logged In, then the fields of Booking Detail will be empty
        // User will have to enter the details manually

        // Booking Button
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from fields
                nameOfPersonBooking = edt_nameOfPersonBooking.getText().toString();
                phoneOfPerson = edt_phoneOfPerson.getText().toString();
                emailPerson = edt_emailPerson.getText().toString();
                numPerson = edt_numPerson.getText().toString();
                numChildren = edt_numChildren.getText().toString();
                cardPerson = etCardPerson.getText().toString();
                cardNumber = etCardNumber.getText().toString();
                cardExpiry = etCardExpiry.getText().toString();
                cardCvv = etCardCvv.getText().toString();

                //Check if the user has entered all the booking details
                if (nameOfPersonBooking.isEmpty()) {
                    edt_nameOfPersonBooking.setError("Please enter your name");
                    edt_nameOfPersonBooking.requestFocus();
                    return;
                }
                if (phoneOfPerson.isEmpty()) {
                    edt_phoneOfPerson.setError("Please enter your phone number");
                    edt_phoneOfPerson.requestFocus();
                    return;
                }
                if (emailPerson.isEmpty()) {
                    edt_emailPerson.setError("Please enter your email");
                    edt_emailPerson.requestFocus();
                    return;
                }
                if (numPerson.isEmpty()) {
                    edt_numPerson.setError("Please enter the number of people");
                    edt_numPerson.requestFocus();
                    return;
                }
                if (numChildren.isEmpty()) {
                    edt_numChildren.setError("Please enter the number of children");
                    edt_numChildren.requestFocus();
                    return;
                }

                // Check if the user has selected a payment option
                if (cardPayment.isChecked()) {
                    if (cardPerson.isEmpty()) {
                        etCardPerson.setError("Please enter the name on the card");
                        etCardPerson.requestFocus();
                        return;
                    }
                    if (cardNumber.isEmpty() || cardNumber.length() < 19) {
                        etCardNumber.setError("Please enter a valid card number");
                        etCardNumber.requestFocus();
                        return;
                    }
                    if (cardExpiry.isEmpty() || cardExpiry.length() < 5) {
                        etCardExpiry.setError("Please enter a valid expiry date");
                        etCardExpiry.requestFocus();
                        return;
                    }
                    if (cardCvv.isEmpty() || cardCvv.length() < 3) {
                        etCardCvv.setError("Please enter a valid CVV");
                        etCardCvv.requestFocus();
                        return;
                    }
                }
                if (mAuth.getCurrentUser() == null) {
                    // add user to database with type "guest"
                    User newUser = addGuestUserToDatabase(emailPerson, nameOfPersonBooking, phoneOfPerson, "guest");
                    userId = newUser.getId();
                } else {
                    // update user to database
                    updateUserToDatabase(mAuth.getCurrentUser().getUid(), nameOfPersonBooking, phoneOfPerson);
                    userId = mAuth.getCurrentUser().getUid();
                }
                addBookingToDatabase(userId, hotelId, startDate, endDate, numPerson, numChildren, totalPrice, cardPayment.isChecked());

                // TODO: fix Send Email to User when Booking is Confirmed
                //sendEmailToUser(emailPerson, nameOfPersonBooking, phoneOfPerson, numPerson, numChildren, startDate, endDate, totalPrice, cardPayment.isChecked());
                Intent intent = new Intent(BookingDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    ///////////////////////////// FIREBASE /////////////////////////////
    // Update User to Database
    private void updateUserToDatabase(String uid, String nameOfPersonBooking, String phoneOfPerson) {
        userRef.child(uid).child("name").setValue(nameOfPersonBooking);
        userRef.child(uid).child("phone").setValue(phoneOfPerson);
    }

    // Add Booking to Database
    private void addBookingToDatabase(String userId, String hotelId, Date startDate, Date endDate, String numPerson, String numChildren, String totalPrice, boolean cardPaymentChecked) {
        // generate booking code
        String bookingCode = generateRandomCode();
        // get booking date
        Date dateBooking = Calendar.getInstance().getTime();
        // set payment method id
        int paymentMethodId = 0;
        if (cardPaymentChecked) paymentMethodId = 2;
        else paymentMethodId = 1;
        // add booking to database
        Booking booking = new Booking(bookingCode, userId, Integer.parseInt(hotelId), dateBooking, startDate, endDate, Integer.parseInt(numPerson), Integer.parseInt(numChildren), Integer.parseInt(totalPrice), "On Going", paymentMethodId);
        bookingRef.child(bookingCode).setValue(booking);
    }

    // Get User from Database
    private User addGuestUserToDatabase(String emailPerson, String nameOfPersonBooking, String phoneOfPerson, String guest) {
        User user = new User(generateRandomID(), emailPerson, nameOfPersonBooking, phoneOfPerson, guest);
        userRef.child(user.getId()).setValue(user);
        return user;
    }

    // Get User from Database by Email
    private void getUserFromDatabase(String emailOfCurrentUser) {
        Query query = userRef.orderByChild("email").equalTo(emailOfCurrentUser);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    edt_nameOfPersonBooking.setText(user.getName());
                    edt_phoneOfPerson.setText(user.getPhone());
                    edt_emailPerson.setText(user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    ///////////////////////////// END FIREBASE /////////////////////////////

    // generate random code for booking with 12 characters including uppercase letters, lowercase letters and numbers
    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 12;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }

    // generate random id for user with 28 characters including numbers and uppercase letters and lowercase letters
    private String generateRandomID() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 28;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }

    // Send email to user
    private void sendEmailToUser(String emailPerson, String nameOfPersonBooking, String phoneOfPerson, String numPerson, String numChildren, Date startDate, Date endDate, String totalPrice, boolean checked) {
        String paymentMethod = "";
        if (checked) paymentMethod = "Card Payment";
        else paymentMethod = "Cash Payment";
        String subject = "Booking Confirmation";
        String message = "Dear " + nameOfPersonBooking + ",\n\n" +
                "Thank you for booking with us. Your booking details are as follows:\n\n" +
                "Name: " + nameOfPersonBooking + "\n" +
                "Phone: " + phoneOfPerson + "\n" +
                "Email: " + emailPerson + "\n" +
                "Number of People: " + numPerson + "\n" +
                "Number of Children: " + numChildren + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate + "\n" +
                "Total Price: " + totalPrice + "\n" +
                "Payment Method: " + paymentMethod + "\n\n" +
                "We look forward to welcoming you to our hotel.\n\n" +
                "Best regards,\n" +
                "Hotel Booking Team";
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, emailPerson, subject, message);
        javaMailAPI.execute();
    }
}