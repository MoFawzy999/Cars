package com.fawzy.cars;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataAccesses {


    private SQLiteOpenHelper sqLiteOpenHelper ;
    private SQLiteDatabase sqLiteDatabase ;
    private static DataAccesses Instance ;

    private DataAccesses(Context context){
        this.sqLiteOpenHelper = new My_DB(context);
    }

    public static  DataAccesses getInstance(Context context){
        if (Instance==null){
            Instance = new DataAccesses(context);
        }
        return Instance ;
    }

    public void  open(){
        this.sqLiteDatabase = this.sqLiteOpenHelper.getWritableDatabase();
        this.sqLiteDatabase = this.sqLiteOpenHelper.getReadableDatabase();
    }

    public void close (){
        if (this.sqLiteDatabase != null){
            this.sqLiteDatabase.close();
        }
    }

    public boolean insert_car(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(My_DB.car_color, car.getColor());
        contentValues.put(My_DB.car_model, car.getModel());
        contentValues.put(My_DB.car_distance, car.getDpl());
        contentValues.put(My_DB.car_desc,car.getDescription());
        contentValues.put(My_DB.car_image,car.getImage());

        long result = sqLiteDatabase.insert(My_DB.car_TBname, null, contentValues);
        return result != -1;
    }


    public boolean update_car(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(My_DB.car_color, car.getColor());
        contentValues.put(My_DB.car_model, car.getModel());
        contentValues.put(My_DB.car_distance, car.getDpl());
        contentValues.put(My_DB.car_desc,car.getDescription());
        contentValues.put(My_DB.car_image,car.getImage());
        contentValues.put(My_DB.car_id,car.getId());
        int result =  sqLiteDatabase.update(My_DB.car_TBname,contentValues,My_DB.car_id+"=?" , new String[]{String.valueOf(car.getId())});
        return result > 0;
    }

    public boolean delete_car(Car car) {
        String args[] = {String.valueOf(car.getId())};
        int result = sqLiteDatabase.delete(My_DB.car_TBname,My_DB.car_id+"=?" ,new String[]{String.valueOf(car.getId())});
        return result > 0;
    }

    public long getcarscount() {
        return DatabaseUtils.queryNumEntries(sqLiteDatabase,My_DB.car_TBname);
    }

    public ArrayList<Car> getAllcars() {
        ArrayList<Car> cars = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + My_DB.car_TBname, null);

        if (cursor != null &&cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(My_DB.car_id));
                String model = cursor.getString(cursor.getColumnIndex(My_DB.car_model));
                String color = cursor.getString(cursor.getColumnIndex(My_DB.car_color));
                double dpl = cursor.getDouble(cursor.getColumnIndex(My_DB.car_distance));
                String des = cursor.getString(cursor.getColumnIndex(My_DB.car_desc));
                String img = cursor.getString(cursor.getColumnIndex(My_DB.car_image));
                Car c = new Car(color,dpl,model,des,img,id);
                cars.add(c);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return cars;
    }

    public Car getcar (int carid){

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+My_DB.car_TBname+" WHERE "+My_DB.car_id +"=?"
                ,new String[]{String.valueOf(carid) });

        if (cursor != null &&cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(My_DB.car_id));
                String model = cursor.getString(cursor.getColumnIndex(My_DB.car_model));
                String color = cursor.getString(cursor.getColumnIndex(My_DB.car_color));
                double dpl = cursor.getDouble(cursor.getColumnIndex(My_DB.car_distance));
                String des = cursor.getString(cursor.getColumnIndex(My_DB.car_desc));
                String img = cursor.getString(cursor.getColumnIndex(My_DB.car_image));
                Car c = new Car(color,dpl,model,des,img,id);
            cursor.close();
            return  c ;
        }
        return  null ;
    }


    public ArrayList<Car> getcars (String modelsearch){
        ArrayList <Car> cars = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+My_DB.car_TBname+" WHERE "+My_DB.car_model +" LIKE ? "
                ,new String[]{modelsearch + "%"});

        if (cursor != null &&cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(My_DB.car_id));
                String model = cursor.getString(cursor.getColumnIndex(My_DB.car_model));
                String color = cursor.getString(cursor.getColumnIndex(My_DB.car_color));
                String Des = cursor.getString(cursor.getColumnIndex(My_DB.car_desc));
                double dpl = cursor.getDouble(cursor.getColumnIndex(My_DB.car_distance));
                Car c = new Car(color,dpl,model,Des,null,id);
                cars.add(c);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return  cars ;
    }


}
