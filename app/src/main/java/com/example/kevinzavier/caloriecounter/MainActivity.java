package com.example.kevinzavier.caloriecounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {
    private EditText foodName;
    private EditText foodCal;
    private Button submitButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);

        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCal = (EditText) findViewById(R.id.caloriesEditText);
        submitButton = (Button) findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDB();
            }
        });







    }

    public void saveDataToDB(){

        String name = foodName.getText().toString().trim();
        String calsString = foodCal.getText().toString().trim();

        if(name.equals("") || calsString.equals("")){
            Toast.makeText(getApplicationContext(), "No nothing allowed", Toast.LENGTH_LONG).show();
        }
        else{
            Food food = new Food();
            int cals = Integer.parseInt(calsString);
            food.setFoodName(name);
            food.setCalories(cals);

            dba.addFood(food);
            dba.close();

            foodName.setText("");
            foodCal.setText("");

            //take user to the next screen
            startActivity(new Intent(MainActivity.this, DisplayFoodsActivity.class));

        }
    }
}
