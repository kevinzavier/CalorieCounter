package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.*;

/**
 * Created by kevinzavier on 3/28/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Food> foodArrayList = new ArrayList<>();

    public DatabaseHandler(Context context){
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = new String("CREATE TABLE " + Constants.TABLE_NAME + "(" +
            Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.FOOD_NAME + " TEXT, " +
            Constants.FOOD_CALORIES_NAME + " INT, " + Constants.DATE_NAME + " LONG);");

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //create a new one
        onCreate(db);
    }

    //get total items saved
    public int getTotalItems(){

        int totalItems = 0;

        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        totalItems = cursor.getCount();

        cursor.close();
        db.close();

        return totalItems;
    }

    //get total calories
    public int getTotalCalories(){
        int cals = 0;

        String query = "SELECT SUM( " + Constants.FOOD_CALORIES_NAME + " ) " +
                "FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cals = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return cals;
    }

    //delete food item
    public void deleteFood(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?", new String[]{ String.valueOf(id)});

        db.close();


    }

    //add an item to the database
    public void addFood(Food item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.FOOD_NAME, item.getFoodName());
        contentValues.put(Constants.FOOD_CALORIES_NAME, item.getCalories());
        contentValues.put(Constants.DATE_NAME, System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, contentValues);

        Log.v("Added an item", "nice!");

        db.close();
    }


    public ArrayList<Food> getFoods(){

        foodArrayList.clear();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.FOOD_NAME, Constants.FOOD_CALORIES_NAME,
                        Constants.DATE_NAME}, null, null, null, null, Constants.DATE_NAME + " DESC ");

        //loop through
        if(cursor.moveToFirst()){
            do {
                Food food = new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALORIES_NAME)));
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                food.setRecordDate(date);

                foodArrayList.add(food);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return foodArrayList;
    }


}
