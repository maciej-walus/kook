package com.example.kook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recepie);






    }

    public void save(View v)
    {

        String directoryPath = "/data/data/com.example.kook/files/recepies";
        EditText title = findViewById(R.id.editTitle);

        String directoryName = title.getText().toString();

        // Create a File object representing the parent directory
        File parentDirectory = new File(directoryPath);

        // Create a File object representing the new directory
        File newDirectory = new File(parentDirectory, directoryName);

        // Create the directory
        boolean created = newDirectory.mkdirs();

        if (created) {

            // Directory created
            //Create .txt
            EditText nextText= findViewById(R.id.editTextNumber3);
            String filePath = "/data/data/com.example.kook/files/recepies/"+ directoryName +"/recepie.txt";
            String finalText =
                    "<portion>\n"+
                    nextText.getText().toString()+
                    "\n</portion>\n";

            nextText= findViewById(R.id.editRequiredTimers);
            finalText +=
                    "<timers>\n"+
                    nextText.getText().toString()+
                    "\n</timers>\n";

            nextText= findViewById(R.id.editIngredients);
            finalText +=
                    "<ingredients>\n"+
                    nextText.getText().toString()+
                    "\n</ingredients>\n";

            nextText= findViewById(R.id.editRecipie);
            finalText +=
                    "<text>\n"+
                    nextText.getText().toString()+
                    "\n</text>\n";


            try {
                FileWriter fileWriter = new FileWriter(filePath);
                fileWriter.write(finalText);
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }





    }
}