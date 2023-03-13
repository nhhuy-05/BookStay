package vn.edu.fpt.projectprm392.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.adapters.SearchAdapter;
import vn.edu.fpt.projectprm392.models.Place;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        SearchAdapter adapter = new SearchAdapter(getListSearchResults());
        RecyclerView rv= findViewById(R.id.rc_search_result);
        rv.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        rv.setAdapter(adapter);
    }

    private List<Place> getListSearchResults(){
        List<Place> list = new ArrayList<>();
        list.add(new Place(R.drawable.img_vietnam, "Vietnam", "Hanoi", "Hotel", "Check-Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "Indonesia", "Kuala Lumpur", "HomeStay", "Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "Canada", "Vancouver", "Hotel", "Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "New York", "Illinois", "Hotel", "Ongoing"));
        list.add(new Place(R.drawable.img_vietnam, "South Korea", "Seoul", "HomeStay", "Ongoing"));
        list.add(new Place(R.drawable.img_vietnam, "England", "London", "Hotel", "Ongoing"));
        list.add(new Place(R.drawable.img_vietnam, "Russia", "Moscow", "HomeStay", "Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "China", "Beijing", "Hotel", "Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "Thailand", "Bangkok", "Hotel", "Ongoing"));
        return list;
    }
}