package com.example.foodie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.Model.FoodItems;
import com.example.foodie.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<FoodItems> list;
    private OnItemClickListener mListener;
    private final int N  =1 ;

    public interface OnItemClickListener {
        void onItemClickUP(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public ItemAdapter(Context context, ArrayList<FoodItems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        if (viewType==0){
            v = LayoutInflater.from(context).inflate(R.layout.empty_layout, parent, false);
        }else {
            v = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        }

        return new ItemViewHolder(v.getRootView());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (position<N){
            return;
        }else {
            FoodItems foodItems =list.get(position-N);
            String imageURL = foodItems.getImage();
            String title = foodItems.getLabel();

            Glide.with(context).load(imageURL).into(holder.food_image);
            holder.title.setText(title);
            double cal = foodItems.getCalories();
            int calories = (int) Math.round(cal);
            String ca = String.valueOf(calories);
            holder.calories.setText(ca + " kcal");
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + N;
    }

    @Override
    public int getItemViewType(int position) {
        return position <N ? 0 : 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView food_image;
        public TextView title, calories;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            food_image = itemView.findViewById(R.id.food_image);
            title = itemView.findViewById(R.id.title);
            calories = itemView.findViewById(R.id.calories);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            if (position+N != N){
                                mListener.onItemClickUP(position-N);
                            }
                        }
                    }
                }
            });
        }
    }
}
