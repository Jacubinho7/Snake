package com.jbalicki.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Scoreboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
    }
    public void Scoreboardclick(View V){
        TextInputLayout editText = (TextInputLayout)findViewById(R.id.textInputLayout2);

        String playername = editText.getEditText().getText().toString();
        try{
            setScore (playername,"100");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
    public void setScore(String playerName, String playerScore) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://hidden-waters-44323.herokuapp.com/save/" + playerName + "/" + playerScore);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.getInputStream();
    }
}
