package com.fawzy.cars;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public
class My_DB extends SQLiteAssetHelper {

    public static final String DB_NAME = "Car.db";
    public static final int DB_version = 1;
    public static final String car_TBname = "car";
    public static final String car_id = "id";
    public static final String car_model = "model";
    public static final String car_desc = "desc";
    public static final String car_image = "image";
    public static final String car_color = "color";
    public static final String car_distance = "distance";


    public My_DB(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, Environment.getExternalStorageState(), null,DB_version);
    }

    public My_DB(Context context) {
        super(context, DB_NAME, null, DB_version);
    }




}
