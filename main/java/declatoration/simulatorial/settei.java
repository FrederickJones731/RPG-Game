package declatoration.simulatorial;

import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import declatoration.simulatorial.fight.sounds.soundmanDan2;
import pl.droidsonroids.gif.GifImageButton;

public class settei extends AppCompatActivity {

    GifImageButton back;

    Button apply;

    ImageButton mainUp, mainDown, mainProj, setUp, setDown, setProj, backgroundUp, backgroundDown, backgroundProj;
    ImageButton clashUp, clashDown;

    TextView mainTV, settingsTV, backTV, clashTV, clashTitleTV, clashDescTV;

    int mainMusicId, settingsMusicId, backgroundId, clashModeId;
    int playingMain = 78;
    int playingSet = 78;

    ArrayList<String> clashModeTitles, clashModeDescs, clashModeLabels;

    boolean mainIsPlaying = false;
    boolean settingsIsPlaying = false;

    public static settingsDB settingsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        settingsDatabase = new settingsDB(getBaseContext());

        apply = findViewById(R.id.apply_changes_button);
        back = findViewById(R.id.back_button);

        mainUp = findViewById(R.id.music_id_up_button);
        mainDown = findViewById(R.id.music_id_down_button);
        mainProj = findViewById(R.id.main_theme_projector);
        mainTV = findViewById(R.id.music_num_tv);

        setUp = findViewById(R.id.settings_up_button);
        setDown = findViewById(R.id.settings_down_button);
        setProj = findViewById(R.id.settings_theme_projector);
        settingsTV = findViewById(R.id.settings_num_tv);

        backgroundUp = findViewById(R.id.background_up_button);
        backgroundDown = findViewById(R.id.background_down_button);
        backgroundProj = findViewById(R.id.background_projector);
        backTV = findViewById(R.id.background_num_tv);

        clashUp = findViewById(R.id.clash_up_button);
        clashDown = findViewById(R.id.clash_down_button);
        clashTV = findViewById(R.id.clash_mode_tv);
        clashTitleTV = findViewById(R.id.clash_mode_title_tv);
        clashDescTV = findViewById(R.id.clash_mode_desc_tv);


        mainMusicId = settingsDatabase.getMainMusicId();
        settingsMusicId = settingsDatabase.getSettingsMusicId();
        backgroundId = settingsDatabase.getBackgroundResId();
        clashModeId = settingsDatabase.getClashMode();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonicJammer.playSound("squish", getBaseContext());
                finish();
            }
        });

        mainUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainMusicId == 13){
                    mainMusicId = 1;
                } else {
                    mainMusicId++;
                }
                mainTV.setText(String.valueOf(mainMusicId));
                sonicJammer.playSound("click", getBaseContext());

                if(playingMain == mainMusicId){
                    mainIsPlaying = true;
                    mainProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.pause));
                } else {
                    mainIsPlaying = false;
                    mainProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.play));
                }

            }
        });
        mainDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainMusicId == 1){
                    mainMusicId = 13;
                } else {
                    mainMusicId--;
                }
                mainTV.setText(String.valueOf(mainMusicId));
                sonicJammer.playSound("click", getBaseContext());

                if(playingMain == mainMusicId){
                    mainIsPlaying = true;
                    mainProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.pause));
                } else {
                    mainIsPlaying = false;
                    mainProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.play));
                }
            }
        });
        mainProj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mainIsPlaying) {
                    playingMain = mainMusicId;

                    mainIsPlaying = true;
                    mainProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.pause));
                    sonicJammer.playSound("click", getBaseContext());

                    settingsIsPlaying = false;
                    setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.play));

                    String musicName = "main" + mainMusicId;
                    soundmanDan2.playNewMusicTrack newTrack = new soundmanDan2.playNewMusicTrack(musicName, getBaseContext(), false);
                    Thread thr = new Thread(newTrack);
                    thr.start();
                } else{
                    playingMain = 78;

                    mainIsPlaying = false;
                    mainProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.play));
                    sonicJammer.playSound("click", getBaseContext());

                    settingsMusicId = settingsDatabase.getSettingsMusicId();
                    if(playingSet == settingsMusicId){
                        setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.pause));
                    }

                    //whatever filler name its the boolean that matters
                    String musicName = "sf2";
                    soundmanDan2.playNewMusicTrack newTrack = new soundmanDan2.playNewMusicTrack(musicName, getBaseContext(), true);
                    Thread thr = new Thread(newTrack);
                    thr.start();
                }
            }
        });

        setUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingsMusicId == 3){
                    settingsMusicId = 1;
                } else {
                    settingsMusicId++;
                }
                settingsTV.setText(String.valueOf(settingsMusicId));
                sonicJammer.playSound("click", getBaseContext());

                if(playingSet == settingsMusicId){
                    settingsIsPlaying = true;
                    setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.pause));
                } else {
                    settingsIsPlaying = false;
                    setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.play));
                }

            }
        });
        setDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingsMusicId == 1){
                    settingsMusicId = 3;
                } else {
                    settingsMusicId--;
                }
                settingsTV.setText(String.valueOf(settingsMusicId));
                sonicJammer.playSound("click", getBaseContext());

                if(playingSet == settingsMusicId){
                    settingsIsPlaying = true;
                    setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.pause));
                } else {
                    settingsIsPlaying = false;
                    setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.play));
                }
            }
        });
        setProj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!settingsIsPlaying) {
                    settingsIsPlaying = true;
                    setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.pause));
                    sonicJammer.playSound("click", getBaseContext());

                    mainIsPlaying = false;
                    mainProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.play));

                    String musicName = "settings" + settingsMusicId;
                    soundmanDan2.playNewMusicTrack newTrack = new soundmanDan2.playNewMusicTrack(musicName, getBaseContext(), false);
                    Thread thr = new Thread(newTrack);
                    thr.start();
                } else{
                    settingsIsPlaying = false;
                    setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.play));
                    sonicJammer.playSound("click", getBaseContext());

                    String musicName = "sf2";
                    soundmanDan2.playNewMusicTrack newTrack = new soundmanDan2.playNewMusicTrack(musicName, getBaseContext(), true);
                    Thread thr = new Thread(newTrack);
                    thr.start();
                }
            }
        });

        backgroundUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundId++;
                backTV.setText(String.valueOf(backgroundId));
                sonicJammer.playSound("click", getBaseContext());
            }
        });
        backgroundDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundId--;
                backTV.setText(String.valueOf(backgroundId));
                sonicJammer.playSound("click", getBaseContext());
            }
        });
        backgroundProj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sonicJammer.playSound("click", getBaseContext());
            }
        });

        clashUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clashModeId == 2){
                    sonicJammer.playSound("error", getBaseContext());
                } else {
                    clashModeId++;
                    sonicJammer.playSound("click", getBaseContext());

                    updateClashTVs();
                }
            }
        });
        clashDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clashModeId == 0){
                    sonicJammer.playSound("error", getBaseContext());
                } else {
                    clashModeId--;
                    sonicJammer.playSound("click", getBaseContext());

                    updateClashTVs();
                }
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();

                Toast.makeText(getBaseContext(), "Updated!", Toast.LENGTH_SHORT).show();
                sonicJammer.playSound("big_select", getBaseContext());
            }
        });


        clashModeTitles = new ArrayList<>(Arrays.asList(getResources().getString(R.string.clash_mode0_title),
                getResources().getString(R.string.clash_mode1_title), getResources().getString(R.string.clash_mode2_title)));
        clashModeDescs = new ArrayList<>(Arrays.asList(getResources().getString(R.string.clash_mode0_desc),
                getResources().getString(R.string.clash_mode1_desc), getResources().getString(R.string.clash_mode2_desc)));
        clashModeLabels = new ArrayList<>(Arrays.asList(getResources().getString(R.string.clash_mode0),
                getResources().getString(R.string.clash_mode1), getResources().getString(R.string.clash_mode2)));

    }

    @Override
    protected void onStart() {
        super.onStart();

        updateClashTVs();

        String musicName = "settings" + settingsMusicId;
        sonicJammer = new soundmanDan2(getBaseContext(), musicName, false);

        setProj.setForeground(ContextCompat.getDrawable(getBaseContext(), R.drawable.pause));

        mainTV.setText(String.valueOf(mainMusicId));
        settingsTV.setText(String.valueOf(settingsMusicId));
        backTV.setText(String.valueOf(backgroundId));
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();

        soundmanDan2.fadeOutMusic fade = new soundmanDan2.fadeOutMusic(true, false);
        Thread thread = new Thread(fade);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateClashTVs(){
        clashTitleTV.setText(clashModeTitles.get(clashModeId));
        clashDescTV.setText(clashModeDescs.get(clashModeId));
        clashTV.setText(clashModeLabels.get(clashModeId));
    }

    public void applyChanges(){
        settingsDatabase.updateAll(mainMusicId, settingsMusicId, backgroundId, clashModeId);
    }

}