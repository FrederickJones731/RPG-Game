package declatoration.simulatorial.prep;

import static declatoration.simulatorial.enums.stat.ATK;
import static declatoration.simulatorial.enums.stat.BLOCK;
import static declatoration.simulatorial.enums.stat.CRIT;
import static declatoration.simulatorial.enums.stat.DEF;
import static declatoration.simulatorial.enums.stat.HIT;
import static declatoration.simulatorial.enums.stat.INT;
import static declatoration.simulatorial.enums.stat.LCK;
import static declatoration.simulatorial.enums.stat.MAG;
import static declatoration.simulatorial.enums.stat.MAGAVO;
import static declatoration.simulatorial.enums.stat.PHYSAVO;
import static declatoration.simulatorial.enums.stat.RES;
import static declatoration.simulatorial.enums.stat.SPD;
import static declatoration.simulatorial.enums.stat.SPREGEN;
import static declatoration.simulatorial.enums.stat.statArray;
import static declatoration.simulatorial.fight.fightForeground.sonicJammer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import declatoration.simulatorial.MainActivity;
import declatoration.simulatorial.R;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.chars;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.fight.battleTokenParcelable;
import declatoration.simulatorial.fight.fightForeground;
import declatoration.simulatorial.fight.sounds.soundmanDan2;
import declatoration.simulatorial.viewAnimationHelper;
import pl.droidsonroids.gif.GifImageButton;

public class warRoom extends AppCompatActivity implements moveSelectLinker, AdapterView.OnItemSelectedListener {

    public static Integer roundNum;
    int score = 0, groupStrengthValue, playerNum = 3;

    ArrayList<chars> selectedChars = new ArrayList<>(4);

    charSelectionArrayAdapter adapter;

    public static warRoomDatabase warDatabase;
    moveSelectorDatabase selectorDatabase;

    Button randomizeChars;
    ImageButton startButton;
    GifImageButton backButton;

    SeekBar seekBarPlayer;
    LinearLayout ll_player2, ll_player3, ll_player4;
    SwitchCompat switchP1, switchP2, switchP3, switchP4;

    int spinnerCountdown = 4;
    Spinner spinner_1, spinner_2, spinner_3, spinner_4;
    Button actionSel1, actionSel2, actionSel3, actionSel4;
    TextView roundNumTV, playerNumTV, diffNumTV;

    boolean startStop = false;

    enemySkirmishGenerator skirmishGen;

    static ArrayList<Boolean> playerRandomizeArrayList = new ArrayList<>(Arrays.asList(false, false, false, false));

    // enemy types - atk, def, spd, supp, mag, mix

    //todo -
    // so, I want:
    // cooldown, statuses and stat boosts dont carry over, but hpsp does.
    // tells you what team you're up against - attack-y, speedy, def-y, or magic-y
    // allows you to pick your guys, except those that have been koed, while showing you hpsp
    // speed dmg not taken and efficiency gives you points
    // dif level increases the further in you get

    //unlock
    // game-overz
    // music

    // fun things to add
    // x1.3 points if you select a certain randomly generated character
    // boss round every 5 rounds - goat guy, lucida, solstice, hexahlia, later sumire, crescentio, brozigard... so on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war_room);

        warDatabase = new warRoomDatabase(getBaseContext());
        selectorDatabase = new moveSelectorDatabase(getBaseContext());

        backButton = findViewById(R.id.back_button);

        startButton = findViewById(R.id.start_button);

        switchP1 = findViewById(R.id.switch_p1);
        switchP2 = findViewById(R.id.switch_p2);
        switchP3 = findViewById(R.id.switch_p3);
        switchP4 = findViewById(R.id.switch_p4);

        roundNumTV = findViewById(R.id.title);
        diffNumTV = findViewById(R.id.diff_num_tv);
        playerNumTV = findViewById(R.id.player_num_tv);

        seekBarPlayer = findViewById(R.id.seekbar_player);
        ll_player2 = findViewById(R.id.ll_char_selection_2);
        ll_player3 = findViewById(R.id.ll_char_selection_3);
        ll_player4 = findViewById(R.id.ll_char_selection_4);

        actionSel1 = findViewById(R.id.move_selector1);
        actionSel2 = findViewById(R.id.move_selector2);
        actionSel3 = findViewById(R.id.move_selector3);
        actionSel4 = findViewById(R.id.move_selector4);

        spinner_1 = findViewById(R.id.spinner_1);
        spinner_2 = findViewById(R.id.spinner_2);
        spinner_3 = findViewById(R.id.spinner_3);
        spinner_4 = findViewById(R.id.spinner_4);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonicJammer.pauseMusic();

                onBackPressed();
            }
        });

        startButton.setOnClickListener(v -> {

            if (!checkDupes()) {

                if(checkNamesFilled()) {

//                startButton.setClickable(false);

                    startStop = true;
                    sonicJammer.playSound("big_select", getBaseContext());

                    soundmanDan2.fadeOutMusic faderr = new soundmanDan2.fadeOutMusic(false, false);
                    Thread thread = new Thread(faderr);
                    thread.start();

//                shouldContinue = false;

                    try {
                        synchronized (this) {
                            thread.join();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intent = new Intent(warRoom.this, fightForeground.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                    skirmishGen.createMegadeath(getBaseContext(),
                            adapter.getSelectedPlayerNames(playerNum),
                            getPickedActionsArrayList(),
                            25, selectedChars);

//                if(roundNum % 5 == 0){
//                    warDatabase.insertBossToDB(enemySkirmishGenerator.getSkirmishArrayList().get(0).getName(), roundNum);
//                }


                    adapter.setFrontlineArray(getFrontLineArray());

                    intent.putExtra("frontLineArray", getFrontLineArray());
                    intent.putExtra("roundNum", roundNum);

                    startActivity(intent);

                } else {
                    sonicJammer.playSound("error", getBaseContext());
                    Toast.makeText(getBaseContext(), "Please Fill In All Player Names!", Toast.LENGTH_SHORT).show();
                }
            } else {
                sonicJammer.playSound("error", getBaseContext());
                Toast.makeText(getBaseContext(), "Cannot Choose the Same Character Twice!", Toast.LENGTH_SHORT).show();
            }

        });

//        if (!soundmanDan2.musicIsPlaying) {
//            settingsDatabase = new settingsDB(this);
//            String musicName = "crusade" + settingsDatabase.getMainMusicId();
//            sonicJammer = new soundmanDan2(getBaseContext(), musicName, false);
//        }
        roundNum = warDatabase.getCurrentRound();
        score = warDatabase.getCurrentScore();

        ArrayList<String> initialCharsToAddToDB = getIntent().getStringArrayListExtra("initialCharNames");
        if (initialCharsToAddToDB != null) {
            addNewCharsToDB(initialCharsToAddToDB);
        }


        if(getIntent().getParcelableArrayListExtra("postCombatBTs") == null) {
            // if is not returning from battle
            generateSkirmish();
            diffNumTV.setText(String.valueOf(groupStrengthValue));

            setData();
        } else {

            Intent data = getIntent();

            boolean win = data.getBooleanExtra("playerWin", true);
            if(win && roundNum < 40) {
                // player win

                ArrayList<battleTokenParcelable> btps = data.getParcelableArrayListExtra("postCombatBTs");
                if (btps != null) {
                    addCharsToDB(btps);

                    // add round num after returning
                    roundNum++;
                    warDatabase.setRoundData(roundNum, score, false);
                }

                ArrayList<String> unlockedChars = data.getStringArrayListExtra("unlockedChars");
                if(unlockedChars != null && !unlockedChars.isEmpty()){
                        unlockChars(unlockedChars);
                }

                // update spinner adapter
                adapter = new charSelectionArrayAdapter(getBaseContext(), warDatabase.getSelectableCharArray(false));
                adapter.notifyDataSetChanged();

                roundNum = warDatabase.getCurrentRound();
                score = warDatabase.getCurrentScore() + data.getIntExtra("points", 100);

                generateSkirmish();
                diffNumTV.setText(String.valueOf(groupStrengthValue));
                setData();

            } else {
                // game over

                warDatabase.setRoundData(roundNum, score, true);

                String msg;

                if(win){
                    ArrayList<String> allCharNames = warDatabase.getSelectableCharArray(true);
                    ArrayList<String> allLiveCharNames = warDatabase.getSelectableCharArray(false);
                    msg = "You WIN! Round Reached - 40\nCharacter Acquired - " + allCharNames.size() + "\nCharacters Lost - " + (allCharNames.size() - allLiveCharNames.size());
                } else {
                    // lose
                    msg = "Game Over! You reached round " + roundNum + "!";

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });

                Intent backToMain = new Intent(warRoom.this, MainActivity.class);

                startActivity(backToMain);
                finish();
            }
        }

        if(roundNum == 25){
            sonicJammer.playSound("transform", getBaseContext());
            runOnUiThread(() -> Toast.makeText(getBaseContext(), "From now on, 4 characters will be pickable for each battle!", Toast.LENGTH_LONG).show());
        }

        if(roundNum >= 25){
            seekBarPlayer.setMax(399);

            seekBarPlayer.setProgress(399);

            ll_player4.setVisibility(View.VISIBLE);

            playerNumTV.setText(String.valueOf(4));
            playerNum = 4;

        } else{
            seekBarPlayer.setMax(299);
        }

        adapter = new charSelectionArrayAdapter(getBaseContext(), warDatabase.getSelectableCharArray(false));

        setupSpinnersAndStuff();

    }

    private boolean checkNamesFilled() {

        boolean res = true;

        switch (playerNum){
            case 4:
                if(Objects.equals(adapter.selectedPlayerNames.get(3), "-")){
                    res = false;
                }
            case 3:
                if(Objects.equals(adapter.selectedPlayerNames.get(2), "-")){
                    res = false;
                }
            case 2:
                if(Objects.equals(adapter.selectedPlayerNames.get(1), "-")){
                    res = false;
                }
            case 1:
                if(Objects.equals(adapter.selectedPlayerNames.get(0), "-")){
                    res = false;
                }
        }

        return res;
    }

    @Override
    protected void onResume() {

        super.onResume();
        startStop = false;

        if(!soundmanDan2.musicIsPlaying) {
            String musicName;
            if (roundNum <= 5) {
                musicName = "inter_1-5";
            } else if (roundNum <= 10) {
                musicName = "inter_6-10";
            } else if (roundNum <= 15) {
                musicName = "inter_11-15";
            } else if (roundNum <= 20) {
                musicName = "inter_16-20";
            } else if (roundNum <= 25) {
                musicName = "inter_21-25";
            } else if (roundNum <= 30){
                musicName = "inter_26-30";
            } else if(roundNum <= 35){
                musicName = "inter_31-35";
            } else{
                musicName = "inter_36-40";
            }


            soundmanDan2.playNewMusicTrack neww = new soundmanDan2.playNewMusicTrack(musicName, getBaseContext(), false);
            Thread thread = new Thread(neww);
            thread.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(!startStop) {
            soundmanDan2.fadeOutMusic faderr = new soundmanDan2.fadeOutMusic(false, false);
            Thread thread = new Thread(faderr);
            thread.start();
        }
    }

    private void generateSkirmish(){

        groupStrengthValue = (int) (4 + ((3/4f) * roundNum));
        skirmishGen = new enemySkirmishGenerator();

        ArrayList<String> bossNamesIfNeeded;
        if(roundNum % 5 == 0){
            bossNamesIfNeeded = warDatabase.getUsedBossesArrayList();
        } else{
            bossNamesIfNeeded = null;
        }

        groupStrengthValue = skirmishGen.createSkirmish(getBaseContext(), groupStrengthValue,
                warDatabase.getSelectableCharArray(true), roundNum, bossNamesIfNeeded);

    }

    private void setData(){

        String titleText = "Campaign Mode - Gauntlet " + roundNum;
        roundNumTV.setText(titleText);

        chars enemyBoss = enemySkirmishGenerator.getSkirmishArrayList().get(0);

        ImageView leaderIV = findViewById(R.id.group_leader_imageview);
        leaderIV.setBackgroundResource(enemyBoss.getCharImageResId());
        leaderIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> bossNamesIfNeeded;
                if(roundNum % 5 == 0){
                    bossNamesIfNeeded = warDatabase.getUsedBossesArrayList();
                } else{
                    bossNamesIfNeeded = null;
                }

                skirmishGen.createSkirmish(getBaseContext(), groupStrengthValue,
                        warDatabase.getSelectableCharArray(true), roundNum, bossNamesIfNeeded);
                sonicJammer.playSound("click", getBaseContext());

                setData();
            }
        });

        TextView groupTypeTV = findViewById(R.id.group_type_tv);
        groupTypeTV.setText(skirmishGen.getGroupType().toString());

        TextView header = findViewById(R.id.group_header_tv);
        String text = "Team " + enemyBoss.getName();
        header.setText(text);

    }


    private void setupSpinnersAndStuff() {
        spinner_1.setOnItemSelectedListener(this);
        spinner_2.setOnItemSelectedListener(this);
        spinner_3.setOnItemSelectedListener(this);
        spinner_4.setOnItemSelectedListener(this);


        spinner_1.setAdapter(adapter);
        spinner_2.setAdapter(adapter);
        spinner_3.setAdapter(adapter);
        spinner_4.setAdapter(adapter);


        ArrayList<String> playerNames = adapter.getSelectedPlayerNames(playerNum);
        if (playerNames.size() == 1) {
            playerNames.addAll(Arrays.asList("-", "-", "-"));
        } else {
            if (playerNames.size() == 2) {
                playerNames.addAll(Arrays.asList("-", "-"));
            } else{
                if (playerNames.size() == 3) {
                    playerNames.add("-");
                }
            }
        }

        spinner_1.setSelection(adapter.getPosition(playerNames.get(0)));
        spinner_2.setSelection(adapter.getPosition(playerNames.get(1)));
        spinner_3.setSelection(adapter.getPosition(playerNames.get(2)));
        spinner_4.setSelection(adapter.getPosition(playerNames.get(3)));

        viewAnimationHelper.setSpinnerAnimation(spinner_1);
        viewAnimationHelper.setSpinnerAnimation(spinner_2);
        viewAnimationHelper.setSpinnerAnimation(spinner_3);
        viewAnimationHelper.setSpinnerAnimation(spinner_4);

        ((BaseAdapter) spinner_1.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_2.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_3.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) spinner_4.getAdapter()).notifyDataSetChanged();


        actionSel1.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(0)));
        actionSel2.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(1)));
        actionSel3.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(2)));
        actionSel4.setOnClickListener(v -> inflateMoveSelector(adapter.getSelectedNameAt(3)));


        seekBarPlayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int playerAmount = 1 + (progress / 100);

                if (playerAmount != playerNum) {

                    playerNum = playerAmount;
                    playerNumTV.setText(String.format(Integer.toString(playerNum)));
                    sonicJammer.playSound("menu", getBaseContext());


                    switch (playerAmount) {
                        case 1:
                            viewAnimationHelper.hideViewWithAnimation(ll_player2, 200, spinner_2);
                            viewAnimationHelper.hideViewWithAnimation(ll_player3, 200, spinner_3);
                            viewAnimationHelper.hideViewWithAnimation(ll_player4, 200, spinner_4);
                            break;
                        case 2:
                            viewAnimationHelper.showViewWithAnimation(ll_player2, 200);
                            viewAnimationHelper.hideViewWithAnimation(ll_player3, 200, spinner_3);
                            viewAnimationHelper.hideViewWithAnimation(ll_player4, 200, spinner_4);
//                            adapter.updatePlayerSize(2);
                            break;
                        case 3:
                            viewAnimationHelper.showViewWithAnimation(ll_player2, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_player3, 200);
                            viewAnimationHelper.hideViewWithAnimation(ll_player4, 200, spinner_4);
//                            adapter.updatePlayerSize(3);
                            break;
                        case 4:
                            viewAnimationHelper.showViewWithAnimation(ll_player2, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_player3, 200);
                            viewAnimationHelper.showViewWithAnimation(ll_player4, 200);
                            break;

                        default:
                    }
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    private boolean checkDupes() {

        boolean res = false;

        if(playerNum > 1){
            if(selectedChars.get(0) == selectedChars.get(1)){
                res = true;
            } else if(playerNum == 3){
                if(selectedChars.get(0) == selectedChars.get(2) ||
                (selectedChars.get(1) == selectedChars.get(2))){
                    res = true;
                }
            } else if (playerNum == 4) {

                if(selectedChars.get(0) == selectedChars.get(3) ||
                        (selectedChars.get(1) == selectedChars.get(3))||
                        (selectedChars.get(2) == selectedChars.get(3))){
                    res = true;
                }
            }
        }

        return res;
    }

    protected boolean[] getFrontLineArray() {
        return new boolean[]{switchP1.isChecked(), switchP2.isChecked(), switchP3.isChecked(), switchP4.isChecked()};
    }

    protected ArrayList<String> getPickedActionsArrayList() {

        ArrayList<String> selectedActions = new ArrayList<>(15);

        if (!playerRandomizeArrayList.get(0)) {
            selectedActions.addAll(selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(0)));
        } else selectedActions.addAll(Arrays.asList(null, null, null, null, null));
        if (!playerRandomizeArrayList.get(1) && playerNum > 1) {
            selectedActions.addAll(selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(1)));
        } else selectedActions.addAll(Arrays.asList(null, null, null, null, null));
        if (!playerRandomizeArrayList.get(2) && playerNum == 3) {
            selectedActions.addAll(selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(2)));
        } else selectedActions.addAll(Arrays.asList(null, null, null, null, null));
        if (!playerRandomizeArrayList.get(3) && playerNum == 4) {
            selectedActions.addAll(selectorDatabase.getCharActionNames(adapter.getSelectedNameAt(3)));
        } else selectedActions.addAll(Arrays.asList(null, null, null, null, null));


        return selectedActions;
    }

    private int getPlayerCharIndex(String name) {
        for (int i = 0; i < selectedChars.size(); i++) {
            if (Objects.equals(selectedChars.get(i).getName(), name)) {
                return i;
            }
        }
        return -1;
    }


    //finds chars actions, and then inflates moveSelector using those
    public void inflateMoveSelector(String name) {

        if (name != null && !name.equals("-")) {
            ArrayList<charAction> charMoves;

            String charName;
            if (name.contains("(")) {
                charName = name.substring(0, name.lastIndexOf("(") - 1);
            } else {
                charName = name;
            }

            databaseExec dbExec = databaseExec.getInstance(getBaseContext());
            charMoves = dbExec.getAllCharUniqueActions(charName, 25);

            int charIndex = adapter.getSelectedPlayerNames(4).indexOf(name);
            boolean isChecked = playerRandomizeArrayList.get(charIndex);

            moveSelector selector = new moveSelector(this, true, isChecked);

            selector.show();

            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.85);

            selector.getWindow().setLayout(width, height);

            selector.setUpActionNames(getBaseContext(), charMoves, name, selectorDatabase.getCharActionNames(name));
        } else {
            Toast.makeText(getBaseContext(), "Please Pick a Char Before Selecting Moves!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewCharsToDB(ArrayList<String> charNames) {
        warDatabase.clearDBs();

        for (int i = 0; i < charNames.size(); i++) {
            warDatabase.inputCharData(new chars(getBaseContext(), charNames.get(i), 25));
        }
    }

    private void unlockChars(ArrayList<String> unlocked){
        for (int i = 0; i < unlocked.size(); i++) {
            chars cur = new chars(getBaseContext(), unlocked.get(i), 25);
            warDatabase.inputCharData(cur);

            String unlockString = cur.getName() + " has been Unlocked!";
            Toast.makeText(getBaseContext(), unlockString, Toast.LENGTH_SHORT).show();
        }
    }

    private void addCharsToDB(ArrayList<battleTokenParcelable> postCombatParcelables) {
        for (int i = 0; i < postCombatParcelables.size(); i++) {
            warDatabase.inputCharData(postCombatParcelables.get(i));
        }
    }


    @Override
    public void exportResult(ArrayList<String> selectedActions, String name, boolean isMainFrag, boolean checked) {
        selectorDatabase.updateActionIds(name, selectedActions);

        playerRandomizeArrayList.set(getPlayerCharIndex(name), checked);
        selectedChars.get(getPlayerCharIndex(name)).setSelectedActions(selectedActions);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int index = -1;

        if (parent.equals(spinner_1)) {
            index = 0;
        } else if (parent.equals(spinner_2)) {
            if (spinner_2.getVisibility() == View.VISIBLE) {
                index = 1;
            }
        } else if (parent.equals(spinner_3)) {
                if (spinner_3.getVisibility() == View.VISIBLE) {
                    index = 2;
                }
            } else if (parent.equals(spinner_4)) {
                if (spinner_4.getVisibility() == View.VISIBLE) {
                    index = 3;
                }
        }


        adapter.addToSelection(position, index);
        playerRandomizeArrayList.set(index, false);

        chars curChar = null;
        String charName = adapter.getNameAt(position);

        if (charName != null && charName.length() > 1) {
            curChar = warDatabase.getCharData(charName);
            if (curChar != null) {
                // this is what makes it so they dont keep hpsp
                curChar = new chars(getBaseContext(), charName, selectorDatabase.getCharActionNames(charName), 25);
            }
        }
        if (index >= selectedChars.size()) {
            selectedChars.add(index, curChar);
        } else {
            selectedChars.set(index, curChar);
        }


        if (spinnerCountdown == 0) {
            sonicJammer.playSound("spinner", getBaseContext());
        } else {
            spinnerCountdown--;
        }

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        sonicJammer.playSound("spinner", getBaseContext());
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        sonicJammer.playSound("spinner", getBaseContext());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(warRoom.this, MainActivity.class);
        startActivity(intent);
    }


    public static class warRoomDatabase extends SQLiteOpenHelper {

        public static final String WAR_DB_NAME = "warDB";


        public static final String RECORD_DB_TABLE = "recordDB";
        public static final String RECORD_COLUMN_HIGH_SCORE = "highScore";
        public static final String RECORD_COLUMN_HIGHEST_ROUND = "highestRound";
        public static final String RECORD_COLUMN_CURRENT_SCORE = "currentScore";
        public static final String RECORD_COLUMN_CURRENT_ROUND = "currentRound";
        public static final String RECORD_COLUMN_IS_MID = "isMid";


        public static final String WAR_DB_TABLE = "warDB";
        public static final String WAR_COLUMN_ID = "id";
        public static final String WAR_COLUMN_NAME = "name";

        public static final String WAR_COLUMN_HP = "hp";
        public static final String WAR_COLUMN_HP_MAX = "hpMax";
        public static final String WAR_COLUMN_SP = "sp";
        public static final String WAR_COLUMN_SP_MAX = "spMax";
        public static final String WAR_COLUMN_SPREGEN = "spregen";

        public static final String WAR_COLUMN_ATK = "atk";
        public static final String WAR_COLUMN_DEF = "def";
        public static final String WAR_COLUMN_MAG = "mag";
        public static final String WAR_COLUMN_INT = "int";
        public static final String WAR_COLUMN_SPD = "spd";
        public static final String WAR_COLUMN_LCK = "lck";
        public static final String WAR_COLUMN_HIT = "hit";
        public static final String WAR_COLUMN_CRIT = "crit";
        public static final String WAR_COLUMN_PHYSAVO = "physavo";
        public static final String WAR_COLUMN_MAGAVO = "magavo";
        public static final String WAR_COLUMN_BLOCK = "block";
        public static final String WAR_COLUMN_RES = "res";

        public static final String WAR_COLUMN_ACTION1 = "action1";
        public static final String WAR_COLUMN_ACTION2 = "action2";
        public static final String WAR_COLUMN_ACTION3 = "action3";
        public static final String WAR_COLUMN_ACTION4 = "action4";
        public static final String WAR_COLUMN_ACTION5 = "action5";


        public static final String UNLOCKED_CHARS_TABLE_NAME = "unlockedTable";

        public static final String UNLOCKED_COLUMN_ID = "id";
        public static final String UNLOCKED_COLUMN_CHAR_NAME = "charName";
        public static final String UNLOCKED_COLUMN_IS_ALIVE = "isAlive";
        public static final String UNLOCKED_COLUMN_WAS_USED = "used";


        public static final String BOSS_TABLE_NAME = "unlockedTable";

        public static final String BOSS_COLUMN_ID = "id";
        public static final String BOSS_COLUMN_NAME = "name";
        public static final String BOSS_COLUMN_LEVEL = "level";

        public warRoomDatabase(@Nullable Context context) {
            super(context, WAR_DB_NAME, null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(
                    "create table if not exists " + RECORD_DB_TABLE +
                            "(" + RECORD_COLUMN_HIGH_SCORE + " integer, " + RECORD_COLUMN_HIGHEST_ROUND + " integer, " +
                            RECORD_COLUMN_CURRENT_SCORE + " integer, " + RECORD_COLUMN_CURRENT_ROUND + " integer, " +
                            RECORD_COLUMN_IS_MID + " integer)"
            );
            db.execSQL(
                    "create table if not exists " + WAR_DB_TABLE +
                            "(" + WAR_COLUMN_ID + " integer primary key autoincrement, " + WAR_COLUMN_HP + " dec(5, 2), "
                            + WAR_COLUMN_ATK + " dec(5, 2), " + WAR_COLUMN_DEF + " dec(5, 2), " +
                            WAR_COLUMN_MAG + " dec(5, 2)," + WAR_COLUMN_INT + " dec(5, 2), " +
                            WAR_COLUMN_SPD + " dec(5, 2), " + WAR_COLUMN_LCK + " dec(5, 2), " +
                            WAR_COLUMN_HIT + " dec(5, 2), " + WAR_COLUMN_CRIT + " dec(5, 2), " +
                            WAR_COLUMN_PHYSAVO + " dec(5, 2)," + WAR_COLUMN_MAGAVO + " dec(5, 2), " + WAR_COLUMN_BLOCK + " dec(5, 2), " +
                            WAR_COLUMN_SP + " dec(5, 2), " + WAR_COLUMN_SPREGEN + " dec(5, 2),"
                            + WAR_COLUMN_RES + " dec(5, 2), " + WAR_COLUMN_NAME + " text unique, " +
                            WAR_COLUMN_HP_MAX + " dec(5, 2)," + WAR_COLUMN_SP_MAX + " dec(5, 2)," +

                            WAR_COLUMN_ACTION1 + " text," + WAR_COLUMN_ACTION2 + " text," +
                            WAR_COLUMN_ACTION3 + " text," + WAR_COLUMN_ACTION4 + " text," +
                            WAR_COLUMN_ACTION5 + " text)"
            );

            db.execSQL(
                    "create table if not exists " + UNLOCKED_CHARS_TABLE_NAME +
                            "(" + UNLOCKED_COLUMN_ID + " integer primary key autoincrement, " +
                            UNLOCKED_COLUMN_CHAR_NAME + " text, " +
                            UNLOCKED_COLUMN_IS_ALIVE + " integer)"
            );

            db.execSQL(
                    "create table if not exists " + BOSS_TABLE_NAME +
                            "(" + BOSS_COLUMN_ID + " integer primary key autoincrement, " +
                            BOSS_COLUMN_NAME + " text, " +
                            BOSS_COLUMN_LEVEL + " integer)"
            );


            //initial nums so it doesnt die
            db.execSQL("INSERT INTO " + RECORD_DB_TABLE + " (highScore, highestRound, currentScore," +
                    " currentRound, isMid) " +
                    "VALUES " +
                    "(0, 0, 0, 1, 0)"
            );
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

        private int numberOfRows(SQLiteDatabase lelmon) {
            return (int) DatabaseUtils.queryNumEntries(lelmon, warRoomDatabase.UNLOCKED_CHARS_TABLE_NAME);
        }

        public void clearDBs() {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("DELETE FROM " + WAR_DB_TABLE);
            db.execSQL("DELETE FROM " + BOSS_TABLE_NAME);
            db.execSQL("DELETE FROM " + UNLOCKED_CHARS_TABLE_NAME);
            setRoundData(getCurrentRound(), 0, true);
        }


        // returns char stats in char form. return null if char hasnt been used yet.
        public chars getCharData(String name) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + WAR_DB_TABLE + " WHERE name = ?", new String[]{String.valueOf(name)});

            c.moveToFirst();

            chars temp;

            if (!c.isAfterLast()) {
                temp = new chars();

                temp.setName(c.getString(16));

                for (int i = 1; i < 15 + 1; i++) {
                    temp.setData(statArray[i - 1], c.getFloat(i));
                }
                temp.setHPMax(c.getFloat(17));
                temp.setSPMax(c.getFloat(18));

//                ArrayList<String> actionNames = new ArrayList<>(Arrays.asList(c.getString(19), c.getString(20),
//                        c.getString(21), c.getString(22), c.getString(23)));
//                temp.setActions(actionNames);

            } else {
                temp = null;
            }

            c.close();

            return temp;
        }

        //new / unlocked chars
        public synchronized void inputCharData(chars c) {

            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("DELETE FROM " + WAR_DB_TABLE + " WHERE name = ?");

            String name = c.getName();

             if (name.contains("'")) {
                    String temp = name.substring(0, name.indexOf("'"));
                    temp += "'";
                    temp += name.substring(name.indexOf("'"));
                    name = temp;
                }

             db.execSQL("INSERT INTO " + WAR_DB_TABLE + " (name," +
                     "hp, hpMax, sp, spMax, spregen, " +
                     "atk, def, mag, int, spd, lck, hit, crit, physavo, magavo, block, res) " +
                     "VALUES " +
                     "('" + name + "', " + c.getHPMax() + ", " + c.getHPMax() + ", " + c.getSPMax() + ", " + c.getSPMax() +
                     ", " + c.getStatFloat(SPREGEN) + ", " + c.getStatFloat(ATK) + ", " + c.getStatFloat(DEF) +
                     ", " + c.getStatFloat(MAG) + ", " + c.getStatFloat(INT) + ", " + c.getStatFloat(SPD) + ", " + c.getStatFloat(LCK) +
                     ", " + c.getStatFloat(HIT) + ", " + c.getStatFloat(CRIT) + ", " + c.getStatFloat(PHYSAVO) +
                     ", " + c.getStatFloat(MAGAVO) + ", " + c.getStatFloat(BLOCK) + ", " + c.getStatFloat(RES) + ")");

             db.execSQL("INSERT INTO " + UNLOCKED_CHARS_TABLE_NAME + " (charName, isAlive) " +
                     "VALUES" +
                     "('" + name + "', 1)");

        }

        public void inputCharData(battleTokenParcelable btp) {

            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("DELETE FROM " + WAR_DB_TABLE + " WHERE name = ?", new String[]{btp.getName()});

            db.execSQL("INSERT INTO " + WAR_DB_TABLE + " (name," +
                    "hp, hpMax, sp, spMax, spregen, " +
                    "atk, def, mag, int, spd, lck, hit, crit, physavo, magavo, block, res) " +
                    "VALUES " + btp.getDBString());

            if (!btp.getIsAlive()) {

                String name = btp.getName();
                if (name.contains("'")) {
                    String temp = name.substring(0, name.indexOf("'"));
                    temp += "'";
                    temp += name.substring(name.indexOf("'"));
                    name = temp;
                }

                db.execSQL("DELETE FROM " + UNLOCKED_CHARS_TABLE_NAME + " WHERE charName = ?", new String[]{btp.getName()});

                db.execSQL("INSERT INTO " + UNLOCKED_CHARS_TABLE_NAME + "(charName, isAlive)" +
                        "VALUES" +
                        "('" + name + "', 0)"
                );
            }
        }

        public void insertBossToDB(String name, int roundNum){
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor c = db.rawQuery("SELECT * FROM " + BOSS_TABLE_NAME + " WHERE level = ?", new String[]{String.valueOf(roundNum)});
            c.moveToFirst();

            if(!c.isAfterLast()){
                db.execSQL("DELETE FROM " + BOSS_TABLE_NAME + " WHERE level = ?", new String[]{String.valueOf(roundNum)});
            }

            db.execSQL("INSERT INTO " + BOSS_TABLE_NAME + " (name, level)" +
                    "VALUES" +
                    "('" + name + "', " + roundNum+")");

            c.close();
        }
        public ArrayList<String> getUsedBossesArrayList(){

            SQLiteDatabase db = this.getReadableDatabase();

            ArrayList<String> usedBossArrayList = new ArrayList<>();

            Cursor c = db.rawQuery("SELECT * FROM " + BOSS_TABLE_NAME, new String[]{});
            c.moveToFirst();

            while (!c.isAfterLast()){
                usedBossArrayList.add(c.getString(1));
                c.moveToNext();
            }

            c.close();

            return usedBossArrayList;
        }


        // returns all unlocked chars, which are still alive
        // if isAlive column returns 1, theyre good
        public ArrayList<String> getSelectableCharArray(boolean includeKOed) {

            SQLiteDatabase db = this.getReadableDatabase();

            ArrayList<String> selectables = new ArrayList<>();

            Cursor c = db.rawQuery("SELECT * FROM " + UNLOCKED_CHARS_TABLE_NAME, null);
            c.moveToFirst();

            for (int i = 0; i < numberOfRows(db); i++) {
                if ((c.getInt(2) == 1) || includeKOed) {
                    selectables.add(c.getString(1));
                }
                c.moveToNext();
            }

            c.close();

            return selectables;
        }

        public void setRoundData(int currentRound, int currentScore, boolean gameOver) {

            SQLiteDatabase db = this.getReadableDatabase();

            ArrayList<String> selectables = new ArrayList<>();

            Cursor c = db.rawQuery("SELECT * FROM " + RECORD_DB_TABLE, null);
            c.moveToFirst();

            int highScore = c.getInt(0);
            if (currentScore > highScore) {
                highScore = currentScore;
            }

            int highestRound = c.getInt(1);
            if (currentRound > highestRound) {
                highestRound = currentRound;
            }

            int makeshiftBoolean;

            if (gameOver) {
                currentScore = 0;
                currentRound = 1;

                makeshiftBoolean = 0;
            } else {
                makeshiftBoolean = 1;
            }


            db.execSQL("DELETE FROM " + RECORD_DB_TABLE, new String[]{});
            db.execSQL("INSERT INTO " + RECORD_DB_TABLE + " (highScore, highestRound, currentScore," +
                    " currentRound, isMid) " +
                    "VALUES " +
                    "(" + highScore + ", " + highestRound + ", " + currentScore + ", " + currentRound + "," +
                    makeshiftBoolean + ")"
            );

            c.close();
        }

        public boolean getIsMid() {

            SQLiteDatabase db = this.getReadableDatabase();

            ArrayList<String> selectables = new ArrayList<>();

            Cursor c = db.rawQuery("SELECT * FROM " + RECORD_DB_TABLE, null);
            c.moveToFirst();

            boolean res = c.getInt(4) == 1;

            c.close();

            return res;
        }

        public int getCurrentScore() {

            SQLiteDatabase db = this.getReadableDatabase();

            ArrayList<String> selectables = new ArrayList<>();

            Cursor c = db.rawQuery("SELECT * FROM " + RECORD_DB_TABLE, null);
            c.moveToFirst();

            int score = c.getInt(2);

            c.close();

            return score;
        }

        public int getCurrentRound() {

            SQLiteDatabase db = this.getReadableDatabase();

            ArrayList<String> selectables = new ArrayList<>();

            Cursor c = db.rawQuery("SELECT * FROM " + RECORD_DB_TABLE, null);
            c.moveToFirst();

            int round = c.getInt(3);

            c.close();

            return round;
        }

        public void startGameOver(){
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor c = db.rawQuery("SELECT * FROM " + RECORD_DB_TABLE, null);
            c.moveToFirst();

            int highScore = c.getInt(0);
            int highestRound = c.getInt(1);

            c.close();

            db.execSQL("DELETE FROM " + RECORD_DB_TABLE);
            db.execSQL("INSERT INTO " + RECORD_DB_TABLE + "(highScore, highestRound, currentScore,"+
                    " currentRound, isMid) VALUES" +
                    "(" + highScore + ","+ highestRound +", 0, 1, 0)");

            enemySkirmishGenerator.reset();

        }




    }

}