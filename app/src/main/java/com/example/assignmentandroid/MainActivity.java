package com.example.assignmentandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.assignmentandroid.Adpater.FactAdapter;
import com.example.assignmentandroid.Model.Country;
import com.example.assignmentandroid.Model.Fact;
import com.example.assignmentandroid.ViewModel.FactViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FactAdapter factAdapter;
    SwipeRefreshLayout swipeRefreshView;
    static ActionBar toolbar;
    private FactViewModel factViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializationViews();
        factViewModel = ViewModelProviders.of(this).get(FactViewModel.class);

        getFacts();
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFacts();
            }
        });
    }

    private void initializationViews() {
        recyclerView = findViewById(R.id.recyclerview);
        swipeRefreshView = findViewById(R.id.swiperefresh);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = getSupportActionBar();
        toolbar.setTitle("About Canada");
    }


    private void getFacts() {
        swipeRefreshView.setRefreshing(true);
        factViewModel.getFacts().observe(this, new Observer<List<Fact>>() {
            @Override
            public void onChanged(List<Fact> factList) {
                swipeRefreshView.setRefreshing(false);
                factAdapter = new FactAdapter(MainActivity.this, factList);
                recyclerView.setAdapter(factAdapter);
            }
        });
    }
}