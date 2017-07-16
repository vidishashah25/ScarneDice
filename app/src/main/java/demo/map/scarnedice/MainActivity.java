package demo.map.scarnedice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    int userScore = 0;
    int computerScore = 0;
    int turnScore = 0;

    TextView scoreText;
    ImageView ivDice;
    Button btRoll;
    Button btHold;
    Button btReset;

    int currentDiceValue = 1;
    boolean isPlayerTurn = true;

    int images[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = (TextView) findViewById(R.id.userLable);
        ivDice = (ImageView) findViewById(R.id.ivDice);
        btRoll = (Button) findViewById(R.id.btRoll);
        btHold = (Button) findViewById(R.id.btHold);
        btReset = (Button) findViewById(R.id.btReset);

        updateUi();
    }

    public void computerTurn() {
        if (!isPlayerTurn) {
            if (turnScore < 20) {
                rolling();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        computerTurn();
                    }
                }, 1000);
            } else {
                currentHold();
            }
        }
    }

    public void rolling() {
        currentDiceValue = new Random().nextInt(6) + 1;

        if (currentDiceValue == 1) {
            turnScore = 0;
            currentHold();
        } else {
            turnScore = turnScore + currentDiceValue;
        }
        updateUi();

    }

    public void roll(View view) {
        if (isPlayerTurn) {
            rolling();
              }

    }

    public void currentHold() {
        if (isPlayerTurn) {
            userScore = userScore + turnScore;
        } else {
            computerScore = computerScore + turnScore;
        }
        turnScore = 0;
        currentDiceValue = 1;
        updateUi();
        isPlayerTurn = !isPlayerTurn;

        if (computerScore > 100 || userScore > 100) {
            Toast.makeText(this, (computerScore > 100 ? "computer" : "user") + " wins", Toast.LENGTH_SHORT).show();
            resetMethod();
        }
        if (!isPlayerTurn) {

            btRoll.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            }, 1000);
        }
        else {
            btRoll.setEnabled(true);
        }
    }

    public void resetMethod() {
        isPlayerTurn = true;
        currentDiceValue = 1;
        computerScore = turnScore = userScore = 0;
        updateUi();
        btRoll.setEnabled(true);
    }

    public void hold(View view) {

        if (isPlayerTurn) {
            currentHold();
        }
    }

    public void reset(View view) {
        resetMethod();
    }


    public void updateUi() {
        scoreText.setText("Player Score : " + userScore + "\n" + "Computer Score : " + computerScore + "\n" +
                "Current Score : " + turnScore);

        ivDice.setImageResource(images[currentDiceValue - 1]);
    }


}
