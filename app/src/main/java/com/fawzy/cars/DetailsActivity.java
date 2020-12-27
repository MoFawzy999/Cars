package com.fawzy.cars;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class DetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView;
    private TextInputEditText model, color, dpl, desc;
    private int car_id;
    private DataAccesses db;
    private Uri imageUri = null ;
    public static final int ADD_RES_CODE = 2 ;
    public static final int EDIT_RES_CODE = 3 ;
    private boolean res ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.img);
        model = findViewById(R.id.txt1);
        color = findViewById(R.id.txt2);
        dpl = findViewById(R.id.txt3);
        desc = findViewById(R.id.txt4);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        car_id = intent.getIntExtra(MainActivity.CAR_KEY, -1);

        db = DataAccesses.getInstance(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1,1);
            }
        });


        if (car_id == -1) {
            // htb2a add
            enablefield();
            clearfiels();
        } else {
            // htb2a edit aw view
            disaplefield();
            db.open();
            Car c = db.getcar(car_id);
            db.close();
            if (c != null){
                fillcartofield(c);
            }
        }


    }

    private void fillcartofield(Car c) {
        if (c.getImage() != null && c.getImage().equals("")){
            imageView.setImageURI(Uri.parse(c.getImage()));
        }
            model.setText(c.getModel());
            color.setText(c.getColor());
            dpl.setText(String.valueOf(c.getDpl()));
            desc.setText(c.getDescription());
    }

    private void disaplefield() {
        imageView.setEnabled(false);
        model.setEnabled(false);
        color.setEnabled(false);
        dpl.setEnabled(false);
        desc.setEnabled(false);
    }

    private void enablefield() {
        imageView.setEnabled(true);
        model.setEnabled(true);
        color.setEnabled(true);
        dpl.setEnabled(true);
        desc.setEnabled(true);
    }

    private void clearfiels() {
        imageView.setImageURI(null);
        model.setText("");
        color.setText("");
        dpl.setText("");
        desc.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem save = menu.findItem(R.id.save);
        MenuItem edit = menu.findItem(R.id.edit);
        MenuItem delete = menu.findItem(R.id.delete);

        if (car_id == -1) {
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        } else {
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String  Model , Color , Desc , Image = " " ;
        double Dpl ;
        db.open();
        switch (item.getItemId()) {
            case R.id.save:
                  Model = model.getText().toString();
                  Color = color.getText().toString();
                  Desc = desc.getText().toString();
                Dpl = Double.parseDouble(dpl.getText().toString());
                  if (imageUri != null){
                      Image = imageUri.toString();
                  }
                  Car c = new Car(Color,Dpl,Model,Desc,Image,car_id);

                  if (car_id == -1) {
                       res = db.insert_car(c);
                      if (res){
                          Toast.makeText(this, "car added successfully", Toast.LENGTH_SHORT).show();
                          setResult(ADD_RES_CODE,null);
                          finish();
                      }
                  }else{
                       res = db.update_car(c);
                      if (res){
                          Toast.makeText(this, "car edited successfully", Toast.LENGTH_SHORT).show();
                          setResult(EDIT_RES_CODE,null);
                          finish();
                      }
                  }

                return true;

            case R.id.edit:
                enablefield();
                MenuItem save = toolbar.getMenu().findItem(R.id.save);
                MenuItem edit = toolbar.getMenu().findItem(R.id.edit);
                MenuItem delete = toolbar.getMenu().findItem(R.id.delete);

                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);

                return true;

            case R.id.delete:
                 c = new Car(null,0,null,null,null,car_id);
                    res = db.delete_car(c);
                    if (res){
                        Toast.makeText(this, "car deleted successfully", Toast.LENGTH_SHORT).show();
                        setResult(EDIT_RES_CODE,null);
                        finish();
                    }
                return true;

        }
        db.close();
        return true ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            if (data != null){
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }








}