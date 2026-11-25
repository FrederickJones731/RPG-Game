package declatoration.simulatorial.prep;


import static declatoration.simulatorial.fight.fightForeground.sonicJammer;
import static declatoration.simulatorial.settei.settingsDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import declatoration.simulatorial.MainActivity;
import declatoration.simulatorial.R;
import declatoration.simulatorial.fight.fightForeground;
import declatoration.simulatorial.fight.sounds.soundmanDan2;
import declatoration.simulatorial.numDatabase;
import declatoration.simulatorial.settingsDB;
import pl.droidsonroids.gif.GifImageButton;

public class prepRoom extends AppCompatActivity implements moveSelectLinker {


    charSelectionArrayAdapter adapterMain;
    enemySelectionArrayAdapter adapterEnemy;
    HashMap<Integer, String> allCharNamesHashMaps, allEnemyNames;

    GifImageButton backButton;

    ImageButton startButton;

    ViewPager2 viewPager;
    prepRoomMainFrag mainFrag;
    prepRoomEnemyFrag enemyFrag;

    prepadapter adapterOfFrags;

    boolean shouldContinue = false;

    static ArrayList<String> playerChars = new ArrayList<>(Arrays.asList("Jim", "Reika", "Dumwan")),
            enemyChars = new ArrayList<>(Arrays.asList("-", "-", "-", "-"));

    static ArrayList<Boolean> playerRandomize = new ArrayList<>(Arrays.asList(false, false, false)),
            enemyRandomize = new ArrayList<>(Arrays.asList(true, true, true, true, true));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_room);

        backButton = findViewById(R.id.back_button);

        startButton = findViewById(R.id.start_button);
        viewPager = findViewById(R.id.prep_view_pager2);


        try (final numDatabase database = new numDatabase(getBaseContext())) {

            allEnemyNames = database.getAllEnemyNames();
            allCharNamesHashMaps = database.getAllPlayerCharNames();

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> allPNames = new ArrayList<>();
        ArrayList<String> allNames = new ArrayList<>();


        for (int i = 0; i < allCharNamesHashMaps.size(); i++) {
            allPNames.add(allCharNamesHashMaps.get(i));
            allNames.add(allCharNamesHashMaps.get(i));
        }
        for (int i = 0; i < allEnemyNames.size(); i++) {
            allNames.add(allEnemyNames.get(i));
        }

        adapterMain = new charSelectionArrayAdapter(getBaseContext(), allPNames);
        adapterEnemy = new enemySelectionArrayAdapter(getBaseContext(), allNames);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        startButton.setOnClickListener(v -> {

            if (levelsFilled()) {

                if (!mainFrag.checkDupes(adapterMain.getSelectedPlayerNames(mainFrag.playerNum))) {

                    startButton.setClickable(false);

                    sonicJammer.playSound("big_select", getBaseContext());

                    soundmanDan2.fadeOutMusic faderr = new soundmanDan2.fadeOutMusic(false, false);
                    Thread thread = new Thread(faderr);
                    thread.start();

                    shouldContinue = false;

                    try {
                        synchronized (this) {
                            thread.join();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    adapterMain.setFrontlineArray(mainFrag.getFrontLineArray());

                    Intent intent = new Intent(prepRoom.this, fightForeground.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    intent.getBooleanExtra("AiIsOn", enemyFrag.getAiIsOn());
                    intent.putIntegerArrayListExtra("levelsArray", getUpdatedLevelArray());

                    intent.putStringArrayListExtra("charsNameArray", adapterMain.getSelectedPlayerNames(mainFrag.playerNum));
                    intent.putStringArrayListExtra("pickedActions", mainFrag.getPickedActionsArrayList());
                    intent.putExtra("frontLineArray", mainFrag.getFrontLineArray());
                    intent.putExtra("clashMode", mainFrag.getClashMode());

                    intent.putExtra("enemyNum", enemyFrag.enemyNum);
                    intent.putIntegerArrayListExtra("strValue", enemyFrag.getStrValue());
                    intent.putStringArrayListExtra("enemyNameArray", adapterEnemy.getSelectedEnemyNames(enemyFrag.enemyNum));
                    intent.putStringArrayListExtra("enemyPickedActions", enemyFrag.getPickedActionsArrayList());
                    intent.putExtra("enemyFrontLineArray", enemyFrag.getFrontLineArray());

                    startActivity(intent);


//                    skirmishGen.createMegadeath(getBaseContext(),
//                            adapter.getSelectedPlayerNames(playerNum),
//                            getPickedActionsArrayList(),
//                            25, selectedChars);
//
//                    intent.putExtra("frontLineArray", mainFrag.getFrontLineArray());

                    startActivity(intent);

                } else {
                    sonicJammer.playSound("error", getBaseContext());
                    Toast.makeText(getBaseContext(), "Cannot Choose the Same Character Twice!", Toast.LENGTH_SHORT).show();
                }
            } else {
                sonicJammer.playSound("error", getBaseContext());
                Toast.makeText(getBaseContext(),
                        "Please fill in all fields before advancing!", Toast.LENGTH_SHORT).show();
            }
        });

        if(!soundmanDan2.musicIsPlaying) {
            settingsDatabase = new settingsDB(this);
            String musicName = "main" + settingsDatabase.getMainMusicId();
            sonicJammer = new soundmanDan2(getBaseContext(), musicName, false);
        }
    }


    private void initializeFrags(){
        adapterOfFrags = new prepadapter(getSupportFragmentManager(), getLifecycle());

        mainFrag = new prepRoomMainFrag(this, adapterMain);
        enemyFrag = new prepRoomEnemyFrag(this, adapterEnemy);

        adapterOfFrags.addFragment(mainFrag);
        adapterOfFrags.addFragment(enemyFrag);

        viewPager.setAdapter(adapterOfFrags);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setPageTransformer(new MarginPageTransformer(300));

    }


    @Override
    protected void onStart() {
        super.onStart();

        initializeFrags();
        startButton.setClickable(true);
    }


    // mainFrag stuff
    private boolean levelsFilled() {
        return mainFrag.playerFieldsFilled();
    }
    private ArrayList<Integer> getUpdatedLevelArray() {
        return new ArrayList<>(Arrays.asList(mainFrag.getPlayerLevel(), enemyFrag.getEnemyLevel()));
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        sonicJammer.playSound("spinner", getBaseContext());
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    protected void onResume() {
        super.onResume();

        startButton.setClickable(true);
        shouldContinue = false;


        if(!soundmanDan2.musicIsPlaying) {
            String musicName = "main" + settingsDatabase.getMainMusicId();
            sonicJammer = new soundmanDan2(getBaseContext(), musicName, false);
        } else {
            if(soundmanDan2.interrupted) {
                sonicJammer.resumeMusic(getBaseContext());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        shouldContinue = true;

        Intent intent = new Intent(prepRoom.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void exportResult(ArrayList<String> selectedActions, String name, boolean isMainFrag, boolean checked) {
        if(isMainFrag) {
            mainFrag.practicizeResult(selectedActions, name);
            playerRandomize.set(playerChars.indexOf(name), checked);

        } else{
            enemyFrag.practicizeResult(selectedActions, name);
            enemyRandomize.set(enemyChars.indexOf(name), checked);
        }
    }

    public void updateArrayList(boolean isPlayer, ArrayList<String> names){
        if(isPlayer){
            playerChars = names;
        } else {
            enemyChars = names;
        }
    }

    public ArrayList<Boolean> getRandomizeArrayList(boolean isPlayer){
        if(isPlayer){
            return playerRandomize;
        } else{
            return enemyRandomize;
        }
    }
}