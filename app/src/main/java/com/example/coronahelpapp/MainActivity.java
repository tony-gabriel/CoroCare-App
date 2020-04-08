package com.example.coronahelpapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public String url="https://corona.lmao.ninja/countries/NGA";

    Button Test, Report, Symptoms, Precautions, News, Movement;
    ImageView profileImage;
    TextView profileName, status, Coronadata, CoronaActive, CoronaRecovered, CoronaCritical;
    DatabaseReference mDataBase;
    UserData userData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userData =new UserData();
        mDataBase = FirebaseDatabase.getInstance().getReference().child("users");




        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Coronadata=findViewById(R.id.CoronaData);
        CoronaActive=findViewById(R.id.CoronaActive);
        CoronaRecovered=findViewById(R.id.CoronaRecovered);
        CoronaCritical=findViewById(R.id.CoronaCritical);
        profileImage = findViewById(R.id.profile_image);

        profileName = findViewById(R.id.profile_name);
        status = findViewById(R.id.health_status);

        Test = findViewById(R.id.button_test);
        Report = findViewById(R.id.button_report);
        Symptoms = findViewById(R.id.button_symptoms);
        Precautions = findViewById(R.id.button_precautions);
        News = findViewById(R.id.button_news);
        Movement = findViewById(R.id.button_movement);

        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelfTest.class));
            }
        });

        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        Symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Precautions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Movement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocationHistory.class));

            }
        });

    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                final String myResponse = response.body().string();
//                Toast.makeText(MainActivity.this, "successful get", Toast.LENGTH_LONG).show();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            Coronadata.setText("Total Cases: "  +jsonObject.getString("cases"));
                            CoronaActive.setText("Active Cases: "+jsonObject.getString("active") );
                            CoronaCritical.setText("Critical Case: " + jsonObject.getString("critical"));
                            CoronaRecovered.setText(" Total Recovered  : " + jsonObject.getString("recovered"));




                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });

            }


        });
        mDataBase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              UserData user = dataSnapshot.getValue(UserData.class);
                profileName.setText("Hello " + user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
