package com.example.foodie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.foodie.Adapter.ItemAdapter;
import com.example.foodie.Model.FoodItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class Home extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    private BlurView blurView;
    private RecyclerView recyclerView;
    private ArrayList<FoodItems> itemsArrayList;
    private ItemAdapter itemAdapter;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        theme();
        recyclerView = findViewById(R.id.recyclerViewChicken);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        itemsArrayList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        showChickenResults();
    }
    private void showChickenResults() {
        String url = "https://api.edamam.com/api/recipes/v2?type=public&q=chicken&app_id=6d91f10b&app_key=21f493c9da380045887e5c8f74ff3bd1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject res = jsonArray.getJSONObject(i);
                        JSONObject recipe = res.getJSONObject("recipe");

                        String title = recipe.getString("label");
                        String image = recipe.getString("image");
                        String uri = recipe.getString("url");
                        int totalTime = recipe.getInt("totalTime");
                        double calories = recipe.getDouble("calories");

                        itemsArrayList.add(new FoodItems(title, image, uri,totalTime, calories));
                    }

                    itemAdapter =new ItemAdapter(Home.this, itemsArrayList);
                    recyclerView.setAdapter(itemAdapter);
                    itemAdapter.setOnItemClickListener(Home.this);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(request);
    }



    private void theme() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        blurView = findViewById(R.id.blurView);
        float radius = 6f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true); // Or false if it's in a scrolling container or might be animated

    }

    @Override
    public void onItemClickUP(int position) {
        FoodItems foodItems = itemsArrayList.get(position);

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(foodItems.getUrl()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.android.chrome");
        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Home.this, "unable to open chrome", Toast.LENGTH_SHORT).show();
            i.setPackage(null);
            startActivity(i);
        }
    }
}