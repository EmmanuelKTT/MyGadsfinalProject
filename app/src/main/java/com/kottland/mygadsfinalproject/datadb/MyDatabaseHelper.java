package com.kottland.mygadsfinalproject.datadb;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.kottland.mygadsfinalproject.model.product;

public class MyDatabaseHelper extends SQLiteOpenHelper {


    private Context context;
    private static final String DATABASE_NAME = "gadspaymentcx1.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "merchantPD";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "product_title";
    public static final String COLUMN_AMOUNT = "product_amount";
    public static final String COLUMN_PAGES = "desc_pages";


     private static final String TABLE_NAME1 = "transc";
     private static final String COLUMN_ID1 = "_id";
     private static final String COLUMN_TITLE1 = "product_title";
     private static final String COLUMN_AMOUNT1 = "product_amount";
     private static final String COLUMN_PAGES1 = "desc_pages";



     public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AMOUNT + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);

        String query1 = "CREATE TABLE " + TABLE_NAME1 +
                " (" + COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE1 + " TEXT, " +
                COLUMN_AMOUNT1 + " TEXT, " +
                COLUMN_PAGES1 + " INTEGER);";
        db.execSQL(query1);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public void addProduct(String title, String amount){
        String pages = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_PAGES, pages);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public void addTrans(String title, String amount){
        String pages = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE1, title);
        cv.put(COLUMN_AMOUNT1, amount);
        cv.put(COLUMN_PAGES1, pages);
        long result = db.insert(TABLE_NAME1,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }


     public  Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

     public product getSingleProductInfo( String idx){

         SQLiteDatabase database = this.getReadableDatabase();
         String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_ID + "=" + idx;
         Cursor cursor = null;

         if(database != null){
             cursor = database.rawQuery(query, null);
         }
         cursor.moveToFirst();

         //setting related user info in User Object
         product productdata = new product();
         productdata.setProductId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID) ));
         productdata.setProductName(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
         productdata.setProductAmount(cursor.getString(cursor.getColumnIndex(COLUMN_AMOUNT )));
         productdata.setProductCtd(cursor.getString(cursor.getColumnIndex(COLUMN_PAGES )));

         //close cursor & database
         cursor.close();
         database.close();

         return productdata;

     }


    public  Cursor readAllTrans(){
        String query = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

     public void updateData(String row_id, String title, String amount){
         String pages = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_PAGES, pages);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }


     public  void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

     public  void deleteOneTrans(String row_id){
         SQLiteDatabase db = this.getWritableDatabase();
         long result = db.delete(TABLE_NAME1, "_id=?", new String[]{row_id});
         if(result == -1){
             Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
         }else{
             Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
         }
     }


     public  void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

     public  void deleteAllTrans(){
         SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("DELETE FROM " + TABLE_NAME1);
     }



}
