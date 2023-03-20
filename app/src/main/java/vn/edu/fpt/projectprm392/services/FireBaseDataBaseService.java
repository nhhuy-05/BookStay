package vn.edu.fpt.projectprm392.services;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FireBaseDataBaseService {
    private static FirebaseDatabase database;

    private static DatabaseReference myRef;

    public static DatabaseReference getMyRef() {
        if (myRef == null) {
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference();
        }
        return myRef;
    }

    public static void setKeyValue(String key, Object value) {
        myRef = database.getReference(key);
        myRef.setValue(value);
    }

    public static void updateKeyValue(String key, Object value) {
        myRef = database.getReference(key);
        myRef.updateChildren((java.util.Map<String, Object>) value);
    }

    public static void deleteKeyValue(String key) {
        myRef = database.getReference(key);
        myRef.removeValue();
    }

    public static DatabaseReference getReference(String key) {
        myRef = database.getReference(key);
        return myRef;
    }

    // read data from firebase
    public static <T> void readDatabase(Context context, String references, Class<T> tClass, FirebaseDatabase db){
        myRef = db.getReference(references);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                T value = (T) dataSnapshot.getValue(tClass);
                if(value == null){
                    Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(context, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
