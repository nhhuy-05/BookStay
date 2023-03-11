package vn.edu.fpt.projectprm392.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vn.edu.fpt.projectprm392.R;

public class BookingDetailActivity extends AppCompatActivity {

    private RadioGroup paymentOptions;
    private RadioButton payAtHotel, cardPayment;
    private LinearLayout cardPaymentSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        // Display the card payment section only when the card payment option is selected
        paymentOptions = findViewById(R.id.payment_radioGroup);
        payAtHotel = findViewById(R.id.pay_at_hotel_radiobutton);
        cardPayment = findViewById(R.id.card_payment_radiobutton);
        cardPaymentSection = findViewById(R.id.card_payment_layout);

        paymentOptions.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.card_payment_radiobutton) {
                cardPaymentSection.setVisibility(View.VISIBLE);
            } else {
                cardPaymentSection.setVisibility(View.GONE);
            }
        });

        // Credit card name formatting
        EditText etCardPerson = findViewById(R.id.et_card_person);
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
        EditText etCardNumber = findViewById(R.id.et_card_number);
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
        EditText etCardExpiry = findViewById(R.id.et_expiry_date);
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

    }
}