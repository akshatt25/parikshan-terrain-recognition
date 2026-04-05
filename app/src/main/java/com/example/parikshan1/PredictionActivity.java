package com.example.parikshan1;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PredictionActivity extends AppCompatActivity {

    private ImageView imageView;  // ImageView to display the image
    private TextView textViewPrediction;
    private Uri imageUri;


    ProgressBar pb;

    RelativeLayout lay2;

    TextView msg;

    TextView slip;
    TextView tre;
    TextView stab;
    TextView veg;
    TextView hyd;

    LinearLayout lin1;
    LinearLayout lin2;
    LinearLayout lin3;
    LinearLayout lin4;
    LinearLayout lin5;
    LinearLayout lin6;

    Drawable snowy;
    Drawable marshy;
    Drawable rocky;
    Drawable sandy;
    Drawable grassy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        imageView = findViewById(R.id.imageView);  // Initialize the ImageView
        textViewPrediction = findViewById(R.id.textViewPrediction);
        pb = findViewById(R.id.pb);
        lay2= findViewById(R.id.lay2);
        msg= findViewById(R.id.msg);
        slip=findViewById(R.id.slip);
        tre=findViewById(R.id.tre);
        stab=findViewById(R.id.stab);
        veg=findViewById(R.id.veg);
        hyd=findViewById(R.id.hyd);
        lin1 = findViewById(R.id.lin1);
        lin2 = findViewById(R.id.lin2);
        lin3 = findViewById(R.id.lin3);
        lin4 = findViewById(R.id.lin4);
        lin5 = findViewById(R.id.lin5);
        lin6 = findViewById(R.id.lin6);
        snowy = getResources().getDrawable(R.drawable.snowy);
        sandy= getResources().getDrawable(R.drawable.sandy);
        grassy = getResources().getDrawable(R.drawable.grassy);
        rocky= getResources().getDrawable(R.drawable.rocky);
        marshy= getResources().getDrawable(R.drawable.marshy);

        String imageUriString = getIntent().getStringExtra("imageUri");
        imageUri = Uri.parse(imageUriString);
        Log.d("imageuri", imageUriString);

        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://parikshan.onrender.com/") // Replace with your local API URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the API interface
        ImagePropertiesService api = retrofit.create(ImagePropertiesService.class);

        // Make the API request
        Call<ImageProperties> call = api.predict_terrain(imageUri.toString());
        Log.d("mainthing",api.predict_terrain(imageUri.toString()).toString());
        call.enqueue(new Callback<ImageProperties>() {
            @Override
            public void onResponse(Call<ImageProperties> call, Response<ImageProperties> response) {
                if (response.isSuccessful()) {
                    Picasso.get().load(imageUri).into(imageView);

                    ImageProperties properties = response.body();
                    Log.d("Response", "Predicted Terrain: " + properties.getPredictedTerrain());
                    updateTextView(properties.getPredictedTerrain());
                    String terrainType = properties.getPredictedTerrain();
                    Log.d("akshat", terrainType);
                    if(terrainType.equals("snowy")) {
                        slip.setText("High");
                        tre.setText("Mid");
                        stab.setText("Low");
                        veg.setText("Low");
                        hyd.setText("Low");
                        lin1.setBackground(snowy);
                        lin2.setBackground(snowy);
                        lin3.setBackground(snowy);
                        lin4.setBackground(snowy);
                        lin5.setBackground(snowy);
                        lin6.setBackground(snowy);
                    }
                    if(terrainType.equals("sandy")) {
                        slip.setText("Mid");
                        tre.setText("Mid");
                        stab.setText("Low");
                        veg.setText("Low");
                        hyd.setText("Low");
                        lin1.setBackground(sandy);
                        lin2.setBackground(sandy);
                        lin3.setBackground(sandy);
                        lin4.setBackground(sandy);
                        lin5.setBackground(sandy);
                        lin6.setBackground(sandy);

                    }if(terrainType.equals("grassy")) {
                        slip.setText("Mid");
                        tre.setText("Low");
                        stab.setText("Mid");
                        veg.setText("High");
                        hyd.setText("Mid");
                        lin1.setBackground(grassy);
                        lin2.setBackground(grassy);
                        lin3.setBackground(grassy);
                        lin4.setBackground(grassy);
                        lin5.setBackground(grassy);
                        lin6.setBackground(grassy);

                    }if(terrainType.equals("rocky")) {
                        slip.setText("Low");
                        tre.setText("Mid");
                        stab.setText("Mid");
                        veg.setText("Low");
                        hyd.setText("Low");
                        lin1.setBackground(rocky);
                        lin2.setBackground(rocky);
                        lin3.setBackground(rocky);
                        lin4.setBackground(rocky);
                        lin5.setBackground(rocky);
                        lin6.setBackground(rocky);

                    }if(terrainType.equals("marshy")) {
                        slip.setText("High");
                        tre.setText("High");
                        stab.setText("Low");
                        veg.setText("Mid");
                        hyd.setText("High");
                        lin1.setBackground(marshy);
                        lin2.setBackground(marshy);
                        lin3.setBackground(marshy);
                        lin4.setBackground(marshy);
                        lin5.setBackground(marshy);
                        lin6.setBackground(marshy);

                    }
                    lay2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ImageProperties> call, Throwable t) {
                t.printStackTrace();
                Log.d("Requestapi", "API request failed: " + t.getMessage());
                pb.setVisibility(View.GONE);
                msg.setText("Try Again "+t.getMessage());


            }
        });
    }

    private void updateTextView(String predictedTerrain) {
        textViewPrediction.setText(Character.toUpperCase(predictedTerrain.charAt(0))+predictedTerrain.substring(1));
    }
}
