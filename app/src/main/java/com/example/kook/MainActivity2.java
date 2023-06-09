package com.example.kook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {
    static double portion_number = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipie);

        Bundle b = getIntent().getExtras();
        String parametr = b.getString("Title");
        TextView poleTekstowe = (TextView) findViewById(R.id.editTitle);
        poleTekstowe.setText(parametr);


        update_recepie(parametr);

        Button button = findViewById(R.id.recalculate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView newamount = (TextView) findViewById(R.id.portions);
                portion_number = Integer.parseInt(newamount.getText().toString());
                update_recepie(parametr);
            }
        });


    }

    public static String replacePattern(String input, double doubleVariable) {
        Pattern pattern = Pattern.compile("\\$(\\d+)(\\S+)?\\$");
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            Double number = Double.parseDouble(matcher.group(1));
            String replacement = number * doubleVariable + " " + matcher.group(2);
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public void update_recepie(String dir) {
        Context context = getApplicationContext();
        File filePath = new File(context.getFilesDir(), "recepies/" + dir + "/recepie.txt");

        List<String> ingredients = new ArrayList<>();
        String text = "";
        int TimerMinutes = 0;
        int TimerSeconds = 0;


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            boolean isIngredients = false;
            boolean isText = false;
            boolean isPortion = false;
            boolean isTimer = false;
            LinearLayout LinearLayoutForTimers = findViewById(R.id.LinearLayoutForTimers);
            while ((line = br.readLine()) != null) {

                if (line.contains("<ingredients>")) {
                    isIngredients = true;
                    continue;
                }
                if (line.contains("</ingredients>")) {
                    isIngredients = false;
                    continue;
                }

                if (line.contains("<timers>")) {
                    isTimer = true;
                    continue;
                }
                if (line.contains("</timers>")) {
                    isTimer = false;
                    continue;
                }

                if (line.contains("<portion>")) {
                    isPortion = true;
                    continue;
                }
                if (line.contains("</portion>")) {
                    isPortion = false;
                    continue;
                }

                if (line.contains("<text>")) {
                    isText = true;
                    continue;
                }
                if (line.contains("</text>")) {
                    isText = false;
                    continue;
                }

                if (isIngredients) {
                    ingredients.add(replacePattern(line, portion_number));
                } else if (isText) {

                    text += replacePattern(line, portion_number);
                } else if (isPortion) {
                    if (portion_number == -1) {
                        portion_number = Double.parseDouble(line);
                    }
                } else if (isTimer) {

                    int minutesIndex = line.indexOf('m');
                    int secondsIndex = line.indexOf('s');
                    int minutes = 0;
                    int seconds = 0;
                    int totalTime = 0;
                    if (minutesIndex == -1) {
                        seconds = Integer.parseInt(line.substring(0, secondsIndex));
                        totalTime = seconds;
                    } else {
                        minutes = Integer.parseInt(line.substring(0, minutesIndex));
                        seconds = Integer.parseInt(line.substring(minutesIndex + 1, secondsIndex));
                        totalTime = (minutes * 60) + seconds;
                    }

                    Button yourButton = new Button(this);
                    if (minutesIndex != -1) {
                        yourButton.setText(minutes + " minutes & " + seconds + " seconds");
                    } else {
                        yourButton.setText(seconds + " seconds");
                    }

                    int finalTotalTime = totalTime;
                    yourButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                                    .putExtra(AlarmClock.EXTRA_LENGTH, finalTotalTime)
                                    .putExtra(AlarmClock.EXTRA_MESSAGE, "Timer for Kook <3")
                                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                            startActivity(intent);
                        }
                    });

                    LinearLayoutForTimers.addView(yourButton);


                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ingrid = "";

        for (int i = 0; i < ingredients.size(); i++) {
            ingrid += ingredients.get(i) + "\n";
        }

        TextView modyfiko = (TextView) findViewById(R.id.editIngredients);
        modyfiko.setText(ingrid);

        modyfiko = (TextView) findViewById(R.id.editRecipie);
        modyfiko.setText(text);

    }
}