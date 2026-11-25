package declatoration.simulatorial;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;
import static declatoration.simulatorial.prep.warRoom.warDatabase;
import static declatoration.simulatorial.settei.settingsDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import declatoration.simulatorial.fight.sounds.soundmanDan2;
import declatoration.simulatorial.overview.encyclopedia;
import declatoration.simulatorial.prep.onset;
import declatoration.simulatorial.prep.prepRoom;
import declatoration.simulatorial.prep.warRoom;


// RIP createGUI


// you will be missed :'(


// no




// not really
public class MainActivity extends AppCompatActivity {

    ImageButton start, charOverview, options;
    Button skirmish;

    TextView title;

    boolean shouldPlay = false;
    static boolean init = false;

    int titlePressCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Explode());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start_button);
        charOverview = findViewById(R.id.char_overview_button);
        options = findViewById(R.id.settings_button);
        skirmish = findViewById(R.id.war_room_button);
        title = findViewById(R.id.title);


        sonicJammer = new soundmanDan2();


        title.setOnClickListener(v -> {

            if(titlePressCount == 3){
                databaseExec dbExec = databaseExec.getInstance(getBaseContext());
                dbExec.resetActionsDatabase();

                titlePressCount = 0;
                sonicJammer.playSound("big_select", getBaseContext());
                Toast.makeText(getBaseContext(), "Actions DB has been reset!", Toast.LENGTH_SHORT).show();

            } else{
                titlePressCount++;
                sonicJammer.playSound("click", getBaseContext());
            }
        });


        start.setOnClickListener(v -> {

            sonicJammer.playSound("click", getBaseContext());
            shouldPlay = true;

            try {
                synchronized (this) {
                    this.wait(400);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Intent intent = new Intent(MainActivity.this, prepRoom.class);
            startActivity(intent);
        });

        charOverview.setOnClickListener(v -> {

            sonicJammer.playSound("click", getBaseContext());
            shouldPlay = true;

            try {
                synchronized (this) {
                    this.wait(400);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Intent intent = new Intent(MainActivity.this, encyclopedia.class);
            startActivity(intent);
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonicJammer.playSound("big_select", getBaseContext());

                try {
                    synchronized (this) {
                        this.wait(400);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                Intent intent = new Intent(MainActivity.this, settei.class);
                startActivity(intent);
            }
        });


        skirmish.setOnClickListener(v -> {

            sonicJammer.playSound("click", getBaseContext());
            shouldPlay = false;

            try {
                synchronized (this) {
                    this.wait(400);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            warDatabase = new warRoom.warRoomDatabase(getBaseContext());
            if(warDatabase.getIsMid()) {
                // is in the middle of a run, so go straight to war room with saved data
                Intent intent = new Intent(MainActivity.this, warRoom.class);
                startActivity(intent);
            } else {
                // start over
                Intent intent = new Intent(MainActivity.this, onset.class);
                startActivity(intent);

            }
        });

        skirmish.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                sonicJammer.playSound("big_select", getBaseContext());
                shouldPlay = true;

                warDatabase = new warRoom.warRoomDatabase(getBaseContext());
                warDatabase.startGameOver();

//                try {
//                    synchronized (this) {
//                        this.wait(400);
//                    }
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//                warDatabase = new warRoom.warRoomDatabase(getBaseContext());
//                    // start over
//                Intent intent = new Intent(MainActivity.this, onset.class);
//                startActivity(intent);


                return false;
            }
        });


        if(!soundmanDan2.musicIsPlaying) {

            settingsDatabase = new settingsDB(this);
            String musicName = "main" + settingsDatabase.getMainMusicId();

            sonicJammer = new soundmanDan2(getBaseContext(), musicName, false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!soundmanDan2.musicIsPlaying) {
            if (!init) {
                sonicJammer.resumeMusic(getBaseContext());
                init = true;
            } else {

                String musicName = "main" + settingsDatabase.getMainMusicId();
                soundmanDan2.playNewMusicTrack neww = new soundmanDan2.playNewMusicTrack(musicName, getBaseContext(), false);
                Thread thread = new Thread(neww);
                thread.start();
            }
        }
        shouldPlay = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!shouldPlay) {
            soundmanDan2.fadeOutMusic faderr = new soundmanDan2.fadeOutMusic(false, false);
            Thread thread = new Thread(faderr);
            thread.start();

            try {
                synchronized (this) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }


}
