package com.example.flashcard_nt;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard_activity);
        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddCardActivity.this, MainActivity.class);
                AddCardActivity.this.startActivity(back);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionToAdd = ((EditText)findViewById(R.id.questionEdit)).getText().toString();
                String answerToAdd = ((EditText)findViewById(R.id.answerEdit)).getText().toString();
                //Flashcard newFlashcardMade = new Flashcard(questionToAdd, answerToAdd);
                Intent savedCard = new Intent(AddCardActivity.this, MainActivity.class);
                savedCard.putExtra("questionToAdd", questionToAdd);
                savedCard.putExtra("answerToAdd", answerToAdd);
                setResult(1000, savedCard);
                finish();
            }
        });
    }
}
