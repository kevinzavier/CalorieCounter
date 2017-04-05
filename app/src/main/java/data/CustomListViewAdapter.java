package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kevinzavier.caloriecounter.FoodItemDetailsActivity;
import com.example.kevinzavier.caloriecounter.R;

import java.util.ArrayList;

import model.Food;

/**
 * Created by kevinzavier on 3/28/17.
 */

public class CustomListViewAdapter extends ArrayAdapter<Food> {

    private int layoutResource;
    private Activity activity;
    private ArrayList<Food> foodArrayList = new ArrayList<>();

    public CustomListViewAdapter(@NonNull Activity activity, @LayoutRes int resource, ArrayList<Food> data) {
        super(activity, resource, data);
        layoutResource = resource;
        this.activity = activity;
        foodArrayList = data;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return foodArrayList.size();
    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return foodArrayList.get(position);
    }

    //leave it like this
    @Override
    public int getPosition(@Nullable Food item) {
        return super.getPosition(item);
    }

    //leave it like this
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if(row == null || (row.getTag() == null)){
            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);

            holder = new ViewHolder();
            holder.foodName = (TextView) row.findViewById(R.id.name);
            holder.foodDate = (TextView) row.findViewById(R.id.dateText);
            holder.foodCalories = (TextView) row.findViewById(R.id.calories);

            row.setTag(holder);
        }
        else{
            holder = (ViewHolder)row.getTag();
        }

        holder.food = getItem(position);

        holder.foodName.setText(holder.food.getFoodName());
        holder.foodDate.setText(holder.food.getRecordDate());
        holder.foodCalories.setText(String.valueOf(holder.food.getCalories()));

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, FoodItemDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("foodObj", getItem(position));
                i.putExtras(bundle);

                activity.startActivity(i);
            }
        });

        return row;
    }

    public class ViewHolder{
        Food food;
        TextView foodName;
        TextView foodCalories;
        TextView foodDate;

    }
}
