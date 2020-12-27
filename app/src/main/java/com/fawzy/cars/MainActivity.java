package com.fawzy.cars;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private FloatingActionButton floatingActionButton ;
    private Toolbar toolbar ;
    private DataAccesses dataAccesses ;
    private ArrayList<Car> carArrayList ;
    private static final int ADD_CAR_REQ_CODE = 2 ;
    private static final int EDIT_CAR_REQ_CODE = 3 ;
    public static  final String CAR_KEY = "car_key" ;
    public static final int PERMISSION_REQ_CODE = 5 ;
    private Adapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
            ,PERMISSION_REQ_CODE);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rec);
        floatingActionButton = findViewById(R.id.btn);

        dataAccesses = DataAccesses.getInstance(this);
        dataAccesses.open();
        carArrayList = dataAccesses.getAllcars();
        dataAccesses.close();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(this, carArrayList, new Listner() {
            @Override
            public void onItemClick(int car_id) {
                 Intent intent =  new Intent(getApplicationContext(),DetailsActivity.class);
                 intent.putExtra(CAR_KEY,car_id);
                 startActivityForResult(intent ,EDIT_CAR_REQ_CODE );
            }
        });
        recyclerView.setAdapter(adapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
               startActivityForResult(intent,ADD_CAR_REQ_CODE);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dataAccesses.open();
                ArrayList<Car> cars = dataAccesses.getcars(query);
                dataAccesses.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dataAccesses.open();
                ArrayList<Car> cars = dataAccesses.getcars(newText);
                dataAccesses.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                dataAccesses.open();
                ArrayList<Car> cars = dataAccesses.getAllcars();
                dataAccesses.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            dataAccesses.open();
            carArrayList = dataAccesses.getAllcars();
            dataAccesses.close();
            adapter.setCars(carArrayList);
            adapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }
        }
    }





}