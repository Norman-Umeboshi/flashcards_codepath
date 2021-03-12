package com.example.flashcard_nt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView flashcardATXTView = (TextView) findViewById(R.id.flashcardA);
        TextView flashcardQTXTView = (TextView) findViewById(R.id.flashcardQ);
        Intent intent = getIntent();
        if(intent != null){
            String newQuestion = intent.getStringExtra("questionToAdd");
            String newAnswer = intent.getStringExtra("answerToAdd");
            if(newQuestion == null || newAnswer == null){
                newQuestion = "How many people are in the world?";
                newAnswer = "7 Billion or something";
            }
            ((TextView) findViewById(R.id.flashcardQ)).setText(newQuestion);
            ((TextView) findViewById(R.id.flashcardA)).setText(newAnswer);
        }


        flashcardATXTView.setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcardQ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQTXTView.setVisibility(View.INVISIBLE);
                flashcardATXTView.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.flashcardA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQTXTView.setVisibility(View.VISIBLE);
                flashcardATXTView.setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivity(addCard);
            }
        });

    }
}