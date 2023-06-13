package com.example.kook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String directoryPath = "/data/data/com.example.kook/files/recepies"; // Specify the path to the directory you want to read

        File directory = new File(directoryPath);
        File[] directories = directory.listFiles(File::isDirectory);


        if (directories != null) {
            for (File dir : directories) {

                //Creates the recepie widget as many times as there are directories
                ConstraintLayout activity_main = findViewById(R.id.mainConstraintLayout);
                LinearLayout categoryLinearLayout = findViewById(R.id.categoryLinearLayout);
                LayoutInflater inflater = LayoutInflater.from(this);
                View inflatedLayout = inflater.inflate(R.layout.widget, categoryLinearLayout, false);


                //Set title
                TextView titleTextView = inflatedLayout.findViewById(R.id.title);
                titleTextView.setText(dir.getName());

                //Set photo

                ImageView imageView = inflatedLayout.findViewById(R.id.imageView2);

                // Set the new image based on the file path
                String filePath = "/data/data/com.example.kook/files/recepies/" + dir.getName() + "/photo.jpg";
                File file = new File(filePath);
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    imageView.setImageBitmap(bitmap);
                }
                //Set button logic
                Button yourButton = inflatedLayout.findViewById(R.id.kook_button);
                yourButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve the modified title from the TextView
                        TextView titleTextView = inflatedLayout.findViewById(R.id.title);
                        String modifiedTitle = titleTextView.getText().toString();

                        // Create an Intent to start the new activity
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                        // Add the modified title as an extra to the Intent
                        intent.putExtra("Title", modifiedTitle);

                        // Start the new activity
                        startActivity(intent);
                    }
                });


                categoryLinearLayout.addView(inflatedLayout);
            }
        }

    }






}