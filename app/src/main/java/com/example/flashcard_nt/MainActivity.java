package com.example.flashcard_nt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int indexFlashcardList = 0;
    final static int ADD_CARD_REQUEST_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView flashcardATXTView = (TextView) findViewById(R.id.flashcardA);
        TextView flashcardQTXTView = (TextView) findViewById(R.id.flashcardQ);
        /*ImageView flashcardNextButtonView = (ImageView) findViewById(R.id.nextButton);
        ImageView flashcardTrashButtonView = (ImageView) findViewById(R.id.trashButton);*/
        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();
        if(indexFlashcardList < allFlashcards.size()){
            Flashcard iterFlash = allFlashcards.get(indexFlashcardList);
            String questionToShow = iterFlash.getQuestion();
            String answerToShow = iterFlash.getAnswer();
            ((TextView) findViewById(R.id.flashcardQ)).setText(questionToShow);
            ((TextView) findViewById(R.id.flashcardA)).setText(answerToShow);
            indexFlashcardList++;
        }
        flashcardATXTView.setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcardQ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View answerSideView = findViewById(R.id.flashcardA);
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;
                float finalRadius = (float) Math.hypot(cx, cy);
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);
                flashcardQTXTView.setVisibility(View.INVISIBLE);
                flashcardATXTView.setVisibility(View.VISIBLE);
                anim.setDuration(3000);
                anim.start();
            }
        });
        findViewById(R.id.flashcardA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View questionSideView = findViewById(R.id.flashcardQ);
                int cx = questionSideView.getWidth() / 2;
                int cy = questionSideView.getHeight() / 2;
                float finalRadius = (float) Math.hypot(cx, cy);
                Animator anim = ViewAnimationUtils.createCircularReveal(questionSideView, cx, cy, 0f, finalRadius);
                flashcardQTXTView.setVisibility(View.VISIBLE);
                flashcardATXTView.setVisibility(View.INVISIBLE);
                anim.setDuration(3000);
                anim.start();
            }
        });

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
                System.out.println(indexFlashcardList);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        findViewById(R.id.flashcardQ).startAnimation(leftOutAnim);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(indexFlashcardList < allFlashcards.size()){
                            Flashcard iterFlash = allFlashcards.get(indexFlashcardList);
                            String questionToShow = iterFlash.getQuestion();
                            String answerToShow = iterFlash.getAnswer();
                            ((TextView) findViewById(R.id.flashcardQ)).setText(questionToShow);
                            ((TextView) findViewById(R.id.flashcardA)).setText(answerToShow);
                            indexFlashcardList++;
                        }
                        else{
                            indexFlashcardList = 0;
                            String reachedEndOfList = "Reached the end of your list of flashcards.";
                            ((TextView) findViewById(R.id.flashcardQ)).setText(reachedEndOfList);
                            ((TextView) findViewById(R.id.flashcardA)).setText(reachedEndOfList);
                        }
                        findViewById(R.id.flashcardQ).startAnimation(rightInAnim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                findViewById(R.id.flashcardQ).startAnimation(leftOutAnim);

            }
        });
        findViewById(R.id.trashButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indexFlashcardList < allFlashcards.size()) {
                    TextView questionFlash = (TextView) findViewById(R.id.flashcardQ);
                    String stringQuestionCard = questionFlash.getText().toString();
                    System.out.println("Size" + allFlashcards.size());
                    flashcardDatabase.deleteCard(stringQuestionCard);
                    allFlashcards = flashcardDatabase.getAllCards();
                    Flashcard iterFlash;
                    if(indexFlashcardList < allFlashcards.size()){
                        iterFlash = allFlashcards.get(indexFlashcardList);
                        ((TextView) findViewById(R.id.flashcardQ)).setText(iterFlash.getQuestion());
                        ((TextView) findViewById(R.id.flashcardA)).setText(iterFlash.getAnswer());
                    }
                    else if(indexFlashcardList - 1 < allFlashcards.size()){
                        iterFlash = allFlashcards.get(--indexFlashcardList);
                        ((TextView) findViewById(R.id.flashcardQ)).setText(iterFlash.getQuestion());
                        ((TextView) findViewById(R.id.flashcardA)).setText(iterFlash.getAnswer());
                    }
                    else{
                        String reachedEndOfList = "Reached the end of your list of flashcards.";
                        ((TextView) findViewById(R.id.flashcardQ)).setText(reachedEndOfList);
                        ((TextView) findViewById(R.id.flashcardA)).setText(reachedEndOfList);
                    }
                }


            }
        });

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(addCard, 1000);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CARD_REQUEST_CODE) {
            String newQuestion = data.getStringExtra("questionToAdd");
            String newAnswer = data.getStringExtra("answerToAdd");
            ((TextView) findViewById(R.id.flashcardQ)).setText(newQuestion);
            ((TextView) findViewById(R.id.flashcardA)).setText(newAnswer);
            flashcardDatabase.insertCard(new Flashcard(newQuestion, newAnswer));
            allFlashcards = flashcardDatabase.getAllCards();
        }
    }
}