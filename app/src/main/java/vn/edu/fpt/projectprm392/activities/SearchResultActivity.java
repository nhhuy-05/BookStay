package vn.edu.fpt.projectprm392.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.adapters.SearchAdapter;
import vn.edu.fpt.projectprm392.models.Room;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Room r1 = new Room(R.drawable.ic_launcher_background, "Hà Nội","Unknow");
        Room r2 = new Room(R.drawable.ic_launcher_background, "Thành Phố Hồ Chí Minh","Unknow");
        Room r3 = new Room(R.drawable.ic_launcher_background, "Hải Phòng","Unknow");
        Room r4 = new Room(R.drawable.ic_launcher_background, "Nha Trang","Unknow");
        Room r5 = new Room(R.drawable.ic_launcher_background, "Phú Quốc","Unknow");

        List<Room> rooms = new ArrayList<>();
        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        rooms.add(r4);
        rooms.add(r5);
        SearchAdapter adapter = new SearchAdapter(rooms);
        RecyclerView rv= findViewById(R.id.rc_search_result);
        rv.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        rv.setAdapter(adapter);
    }
}