package declatoration.simulatorial.fight;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import declatoration.simulatorial.MainActivity;
import declatoration.simulatorial.R;
import declatoration.simulatorial.fight.sounds.soundmanDan2;
import declatoration.simulatorial.viewAnimationHelper;


public class gameEndScreen extends AppCompatActivity {


    TextView title;
    TextView turnNumTV;

    ImageButton mainButton;
    ImageView dancingGuys;
    ImageView sadBoi;

    ConstraintLayout groundBack;

    boolean shouldStop = true, initialized = false;
    boolean playerWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end_screen);

        title = findViewById(R.id.title);
        turnNumTV = findViewById(R.id.turn_num_tv);

        dancingGuys = findViewById(R.id.dancefest);
        sadBoi = findViewById(R.id.sad_boi);

        mainButton = findViewById(R.id.end_button);
        groundBack = findViewById(R.id.background_end_screen);

        playerWin = getIntent().getBooleanExtra("playerWin", true);
        int turnCount = getIntent().getIntExtra("turnCount", 0);

        String musicName;
        if(playerWin){
            musicName = "happy_win_yay";
        } else {
            musicName = "game_over";
        }
        sonicJammer = new soundmanDan2(getBaseContext(), musicName, false);




        turnNumTV.setText(String.valueOf(turnCount));
        if (playerWin) {
            title.setText(getResources().getString(R.string.player_win));
            groundBack.setBackgroundResource(R.drawable.crispy);
        } else {
            title.setText(getResources().getString(R.string.enemy_win));
//            title.setBackgroundColor(Color.TRANSPARENT);
            groundBack.setBackgroundResource(R.drawable.shinji_chair2);
        }


        mainButton.setOnClickListener(v -> {
            if(playerWin) {
                sonicJammer.playSound("big_select", getBaseContext());
            } else {
                sonicJammer.playSound("squish", getBaseContext());
            }
            soundmanDan2.fadeOutMusic fade = new soundmanDan2.fadeOutMusic(false, false);
            Thread thread = new Thread(fade);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }


            //it SHOULD stop, but not from onPause
            shouldStop = false;

            synchronized (this){
                try {
                    this.wait(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            Intent intent = new Intent(gameEndScreen.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            startActivity(intent);
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        sonicJammer.bonusMp.setOnCompletionListener(mp -> {
            if(shouldStop) {
                sonicJammer.resumeMusic(getBaseContext());
                if (playerWin) {
                    viewAnimationHelper.showViewWithAnimation(dancingGuys, 500);
                } else {
                    viewAnimationHelper.showViewWithAnimation(sadBoi, 500);
                }
                sonicJammer.endTask(sonicJammer.bonusMp);
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(initialized) {
            sonicJammer.resumeMusic(getBaseContext());
        }

        initialized = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(shouldStop) {
            sonicJammer.pauseMusic();
        }
    }

    @Override
    public void onBackPressed() {
        mainButton.performClick();
    }
}