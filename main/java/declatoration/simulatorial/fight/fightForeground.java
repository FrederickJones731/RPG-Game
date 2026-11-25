package declatoration.simulatorial.fight;


//import static declatoration.simulatorial.fight.attackPickerLayout.lockAtk;

import static declatoration.simulatorial.fight.actionHandler.percentagize;
import static declatoration.simulatorial.fight.fightBackground.combatants;
import static declatoration.simulatorial.fight.fightBackground.currentTurnCharId;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import declatoration.simulatorial.R;
import declatoration.simulatorial.affinityTable;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.chars;
import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.damageType;
import declatoration.simulatorial.enums.effectiveness;
import declatoration.simulatorial.enums.moveType;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.target;
import declatoration.simulatorial.enums.weaponType;
import declatoration.simulatorial.fight.sounds.soundmanDan2;
import declatoration.simulatorial.fight.statCard.statCardXX;
import declatoration.simulatorial.getImageRes;
import declatoration.simulatorial.prep.enemySkirmishGenerator;
import declatoration.simulatorial.prep.warRoom;
import declatoration.simulatorial.viewAnimationHelper;

public class fightForeground extends AppCompatActivity implements charClickedBridger, attackPickedBridger {


    private fightMidgrounds midlands;
    private fightBackground slagveld;

    charAction selectedAction;
    private ArrayList<Integer> targetCodes;
    private ArrayList<Integer> targetables;

    private int attackCode;
    private int turnCount;

    private boolean aiIsOn;
    private boolean isSkirmish;
    private boolean playTaunt = false;

    private boolean started = false;
    private boolean winningGroupIsPlayer;
    private boolean offensiveActionPicked;
    private boolean targetFragUp = false;
    private boolean targetResultReceived = false;
    private boolean backButtonPressed = false;
    private boolean isFinalActionTime = false;

    private attackPickerLayout attackView;
    private attackPickerFinaleAction finalAttackView;
    private targetPickerLayout targetView;

    private int initialPlayerNum;

    //visual things

    private largeBarsContainerLayout playerBars;
    private largeBarsContainerLayout enemyBars;
    private clashReactionForecast clashReactionLayout;

    private LinearLayout pr1, pr2, er1, er2;
    private ConstraintLayout root;
    private View clickBlocker;
    private TextView attackForecast;
    private ImageButton back;
//    pointerKore pointerManager;

    private LinearLayout resultPlayerll, resultEnemyll, forecastAffWeaponll;
    private TextView resultPlayerName, resultPlayer, resultPlayerKoTV, resultEnemyName, resultEnemy, resultEnemyKoTV;
    private ImageView forecastAffAtkrIcon, forecastAffDefIcon, forecastWeaponAtkrIcon, forecastWeaponDefIcon, forecastAffEffIcon, forecastWeaponEffIcon;


    public static soundmanDan2 sonicJammer;
    boolean gameOver = false;
    static int clashReactionMode;
    static int dmgTaken;
    static boolean isCrit;
    static boolean isBlock;

    static boolean charIsRaging = false;
    private boolean noRageEscape = false;

    private ArrayList<charAction> currentCharActions;


    //ToDo, immediates
    // baseLCK setting in battleToken is HORRIBLY optimized, can be easily slotted in with stat-base-setting
    // add code blocks for each action / skill IN THE DATABASE!!!!
    // charSkills + tags (ANOTHER class for tag checking)
    // make each action have a runnable, which will update modifier checking whatever needs to be checked

    //ToDo
    // make nice buttons w/ symbols for attackFrag
    // position bubble & pointers correctly - CONSTRAINT_SET
    // get RID of the frickin TOASTS! -> names pop up / pointers
    // use handlers for timings, its actually super good here


    //ToDo, but kinda later
    // does pointer work? (it should with the manager but idrk like what with multiples or if targets untargetables?)
    // attackFrag loop marker under chars or something of the sort
    // endScreen add like stats and stuff
    // smallBars
    // encyclopedia fix bases being thin fsr
    // encyclofrag actions symbols (make it look pretty too, move everything down somewhat)
    // encyclofrag skills
    // ok so get this ----->   critical heal?


    //ToDo, but later
    // move chars using motionLayout / pathMotion / objectAnimator
    // enemy AI / aggro?


    //ToDo, but ultra later
    // custom font!
    // hpbar damaged anim thing (not that easy, wait with this)
    // animations for random things in the app?
    // make a huge really cool cog-like arrangement for attack-picker. color changes with move type (big outdent for normal attack)

    // party size is 4, enemy 6, but for now we go with 3 and 5 respectively
    // fix rows and assign chars to them using .addChild(), max of 3 for each row
    // create ai for enemies when you feel like it but not now lets get this thing working first


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faito);

        root = findViewById(R.id.fight_layout);


        playerBars = findViewById(R.id.large_bar_player);
        enemyBars = findViewById(R.id.large_bar_enemy);

        pr1 = findViewById(R.id.player_row_1);
        pr2 = findViewById(R.id.player_row_2);
        er1 = findViewById(R.id.enemy_row_1);
        er2 = findViewById(R.id.enemy_row_2);


//        playerCenterView = findViewById(R.id.player_center_attack_view);

//        attackTextView = findViewById(R.id.attack_text_view);
//        nameTextView = findViewById(R.id.name_text_view);
//        targetTextView = findViewById(R.id.target_text_view);
        attackForecast = findViewById(R.id.fight_attack_forecast);

        resultPlayerll = findViewById(R.id.player_outcome_ll);
        resultEnemyll = findViewById(R.id.enemy_outcome_ll);

        resultPlayer = findViewById(R.id.fight_update_player);
        resultPlayerName = findViewById(R.id.fight_update_player_name);
        resultPlayerKoTV = findViewById(R.id.fight_update_player_ko);
        resultEnemy = findViewById(R.id.fight_update_enemy);
        resultEnemyName = findViewById(R.id.fight_update_enemy_name);
        resultEnemyKoTV = findViewById(R.id.fight_update_enemy_ko);

        forecastAffWeaponll = findViewById(R.id.effectiveness_forecast);
        forecastAffAtkrIcon = findViewById(R.id.aff_eff_icon_atkr);
        forecastAffDefIcon = findViewById(R.id.aff_eff_icon_def);
        forecastAffEffIcon = findViewById(R.id.aff_eff_icon);
        forecastWeaponAtkrIcon = findViewById(R.id.weapon_eff_icon_atkr);
        forecastWeaponDefIcon = findViewById(R.id.weapon_eff_icon_def);
        forecastWeaponEffIcon = findViewById(R.id.weapon_eff_icon);

        clashReactionLayout = findViewById(R.id.clash_reaction_forecast);

        back = findViewById(R.id.back_button);
        ImageButton settings = findViewById(R.id.settings_button);

        clickBlocker = findViewById(R.id.rage_click_blocker);

//        visualThread = new HandlerThread("godsend");
//        visualThread.start();
//
//        uiHandler = new Handler(visualThread.getLooper());

        midlands = new fightMidgrounds(this, findViewById(R.id.fight_log));

        //initialize allChars and battleTokens
        ArrayList<String> charsNames = getIntent().getStringArrayListExtra("charsNameArray");
        if (charsNames != null) {
                // prepRoom initialization
                isSkirmish = false;

                initialPlayerNum = charsNames.size();
                ArrayList<String> enemyNames = getIntent().getStringArrayListExtra("enemyNameArray");
                this.slagveld = new fightBackground(this.getBaseContext(),
                        this, midlands, charsNames,
                        getIntent().getIntegerArrayListExtra("levelsArray"),
                        getIntent().getStringArrayListExtra("pickedActions"),
                        getIntent().getIntExtra("enemyNum", 4),
                        enemyNames,
                        getIntent().getStringArrayListExtra("enemyPickedActions"));


                setInitialRowPlacements(getIntent().getBooleanArrayExtra("frontLineArray"),
                        enemyNames, getIntent().getBooleanArrayExtra("enemyFrontLineArray"));

                aiIsOn = getIntent().getBooleanExtra("AiIsOn", true);
                clashReactionMode = getIntent().getIntExtra("clashMode", 1);

            } else {
                //war room initialization
                isSkirmish = true;
                aiIsOn = true;


                ArrayList<chars> megadeathCharsArray = enemySkirmishGenerator.getMegadeathArrayList();
                initialPlayerNum = megadeathCharsArray.size();
                ArrayList<chars> enemyCharsArray = enemySkirmishGenerator.getSkirmishArrayList();
                this.slagveld = new fightBackground(this.getBaseContext(),
                        this, midlands,
                        megadeathCharsArray, enemyCharsArray);


                setInitialRowPlacements(getIntent().getBooleanArrayExtra("frontLineArray"));

            }


        int roundNum = getIntent().getIntExtra("roundNum", -1);
        themeGenerator themeGenerator = new themeGenerator(isSkirmish, roundNum);
        Thread themeThread = new Thread(themeGenerator);
        themeThread.start();

        // ask if to main or just back to prep screen
            // also make an inflatable window thing
        back.setOnClickListener(v -> {
                if (!targetFragUp) {

                    //targetFrag isnt up

                    if (!backButtonPressed) {

                        sonicJammer.playSound("back_button", getBaseContext());
                        backButtonPressed = true;


                    } else {
                        sonicJammer.playSound("back_button_final", getBaseContext());
                        gameOver = true;

                        synchronized (this) {
                            try {
                                this.wait(250);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        finish();
                    }
                } else {

                    if (!noRageEscape) {
                        // exit and return to attackfrag
                        sonicJammer.playSound("back_button", getBaseContext());
                        targetFragUp = false;

                        forecastAffWeaponll.setVisibility(View.INVISIBLE);
                        attackForecast.setVisibility(View.INVISIBLE);
                        clashReactionLayout.setVisibility(View.INVISIBLE);

                        turnPlayerStart afterThread = new turnPlayerStart();
                        Thread thr = new Thread(afterThread);
                        thr.start();
                    } else {
                        sonicJammer.playSound("error", getBaseContext());

                        Toast.makeText(getBaseContext(), "lol nice try", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        settings.setOnClickListener(v -> {

            });


//        pointerManager = new pointerKore(getBaseContext());
//        movementManager = new movementManager(playerCenterView);
//        hideTextViews(true);
        forecastAffWeaponll.setVisibility(View.INVISIBLE);
        attackForecast.setVisibility(View.INVISIBLE);
        clashReactionLayout.setVisibility(View.INVISIBLE);

        playerBars.isPlayerBars(true);
        enemyBars.isPlayerBars(false);

        setCharBackgroundResIds();

        try {
                themeThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }

        String theme = themeGenerator.getTheme();
        if (roundNum % 5 == 0) {// get name of boss as music name
            theme = combatants.getActiveBattleTokens(false).get(0).getName().toLowerCase();
            playTaunt = true;
        }

        sonicJammer = new soundmanDan2(getBaseContext(), theme, true);

    }


    @Override
    protected void onStart() {
        super.onStart();


        // start up screen?

        if(!started) {

            turnCount = 0;

            try {
                // makes sure no one first row is empty
                Runnable r1 = checkRowCollapse(true);
                if (r1 != null) {
                    r1.run();
                }

                Runnable r2 = checkRowCollapse(false);
                if (r2 != null) {
                    r2.run();
                }


//            Thread toastHandler = new Thread(new turnToastGenerator());
//            toastHandler.start();


                turnStart start = new turnStart();
                Thread thread = new Thread(start);
                thread.start();

                // entire game runs, until one side is koed

            } catch (Exception e) {
                System.out.println("'onStart' error");
                Log.e("hi", "onStart: " + Arrays.toString(e.getStackTrace()));
            }

            started = true;
        }

    }

    public class turnStart implements Runnable {

        @Override
        public void run() {

            // returns true if start of new turn, and not mid-actions
            if(slagveld.pickTurn()){
                midlands.post(getCurrentBattleToken().getName(), "", "",
                        fightUpdater.textType.TURN_START, null, null);
            }
            turnCount++;

            if (slagveld.getIsPlayer(currentTurnCharId)) {
                enemyBars.setVisibility(View.INVISIBLE);
            } else {
                playerBars.setVisibility(View.INVISIBLE);
            }


            if (getCurrentBattleToken().hasTurn() && getCurrentBattleToken().getActionsRemaining() >= 0) {

                runOnUiThread(() -> Toast.makeText(getApplicationContext(), getCurrentBattleToken().getName()
                        + "'s turn!", Toast.LENGTH_SHORT).show());

                if(getCurrentBattleToken().hasStatus(statusEffect.RAGE)){
                    charIsRaging = true;


                    fightMidgrounds.post(getCurrentBattleToken().getName(), "", "",
                            fightUpdater.textType.RAGE_TURN, null, null);

                    rageTurnPlayer ragerrar = new rageTurnPlayer();
                    Thread thread = new Thread(ragerrar);
                    thread.start();

                } else {

                    charIsRaging = false;

                    try {
                        //always returns playerTurn, update code when you want to change that
                        Thread currentTurn = new Thread(getTurnThread());
                        currentTurn.start();

                        currentTurn.join();

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                //recactions after turn is complete
            } else {
                // if char has no turn, or expended all their actions (skip finale procedure)

                if(getCurrentBattleToken().getActionsRemaining() > -1) {

                    updateLargeBars(currentTurnCharId, slagveld.getIsPlayer(currentTurnCharId));

                    String turnUnavailableMsg;
                    statusEffect immStatusEff = getCurrentBattleToken().getTurnUnavailableStatus();
                    battleToken bt = getBT((Integer) getCurrentBattleToken().getBonusEffectExtra(statusEffect.CHARM));

                    //its null if there is no char imposing charm
                    boolean isCharmEffect = bt != null;
                    if (!isCharmEffect) {
                        turnUnavailableMsg = getTurnUnavailableMsg(getCurrentBattleToken().getName(), null, immStatusEff);
                    } else {
                        turnUnavailableMsg = getTurnUnavailableMsg(getCurrentBattleToken().getName(),
                                getBT((int) getCurrentBattleToken().getBonusEffectExtra(statusEffect.CHARM)).getName(), statusEffect.CHARM);
                    }


                    runOnUiThread(() -> Toast.makeText(getBaseContext(), turnUnavailableMsg, Toast.LENGTH_SHORT).show());
                    synchronized (this) {
                        try {
                            this.wait(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    midlands.post(getCurrentBattleToken().getName(), "", "",
                            fightUpdater.textType.getTextTypeTurnEff(immStatusEff), null, null);

                    if (!isCharmEffect) {
                        slagveld.terminateTurn();
                    }
                } else {
                    //adds 1, to replace finale action which they dont get
                    getCurrentBattleToken().altActionCount(1);
                    getCurrentBattleToken().terminateTurn();
                    isFinalActionTime = true;
                }

                startTurnEnd();
            }
        }


        private Runnable getTurnThread() {
            Runnable turn;
            if (true) {
                turn = new turnPlayerStart();
            } else {
                turn = new turnEnemy();
            }
            return turn;
        }
    }
    public class turnEnd implements Runnable {

        @Override
        public void run() {

            if(!isFinalActionTime) {
                getBT(currentTurnCharId).altActionCount(-selectedAction.getActionUse());
            } else{
                resetFocusFrames();
            }

//                hideSideBarName();
            slagveld.finalizeTurn(currentTurnCharId);
            playerBars.setVisibility(View.INVISIBLE);
            enemyBars.setVisibility(View.INVISIBLE);


            checkKOs();

        }
    }


    private class turnPlayerStart implements Runnable {

        @Override
        public void run() {

            runOnUiThread(() -> getCurrentBattleToken().getCharLayout().imageFrame.setBackgroundResource(R.drawable.groen));


            // sets current turn wielder's bars, player or enemy (fix this when complete)
//            if (!) {
//                enemyBars.updateBars(slagveld.getCharHpSp(currentTurnCharId));
//                runOnUiThread(() -> enemyBars.setVisibility(View.VISIBLE));
//
//            } else {
//                playerBars.updateBars(slagveld.getCharHpSp(currentTurnCharId));
//                runOnUiThread(() -> playerBars.setVisibility(View.VISIBLE));
//            }

            updateLargeBars(currentTurnCharId, getBT(currentTurnCharId).getIsPlayer());


            if (!isFinalActionTime) {

                //normal turn procedure


                currentCharActions = getBT(currentTurnCharId).getChars().getCharSelectedActions();
                ArrayList<String> actionBundle = new ArrayList<>(5);

                //0 is normal attack
                for (int i = 0; currentCharActions.size() > i; i++) {
                    actionBundle.add(i, currentCharActions.get(i).getName());
                }
//                movementManager.centralize((ViewGroup) findViewById(R.id.fight_layout).getRootView(),
//                        getCurrentBattleToken().getCharLayout());
//                centralizeCurrentTurnHolder();


//                startAttackProcedure

                attackView = new attackPickerLayout(getBaseContext(), fightForeground.this,
                        currentCharActions, getBT(currentTurnCharId).getActionsRemaining());
                runOnUiThread(() -> {
                    root.addView(attackView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    root.bringChildToFront(attackView);
                    attackView.setElevation(30);
                });
                attackView.setupAttackNames(actionBundle, checkSufficientSPAndBullets(getCurrentBattleToken().getCooldownArray(false), false),
                        combatants.rowCollapsed(slagveld.getIsPlayer(currentTurnCharId)), rowActionUnavailableCause());
                attackView.setClickable(true);


            } else {

                    finalAttackView = new attackPickerFinaleAction(getBaseContext(), fightForeground.this,
                            currentTurnCharId, rowActionUnavailableCause(),
                            combatants.rowCollapsed(slagveld.getIsPlayer(currentTurnCharId)),
                            combatants, checkSufficientSPAndBullets(getCurrentBattleToken().getCooldownArray(true), true));
                    runOnUiThread(() -> {
                        root.addView(finalAttackView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        root.bringChildToFront(finalAttackView);
                        finalAttackView.setElevation(30);
                    });
                    finalAttackView.setClickable(true);
                }


        }
    }
    private class turnPlayerPostAtkFrag implements Runnable {

        @Override
        public void run() {

            // hide frags
            if(!isFinalActionTime){
                runOnUiThread(() -> {
                    attackView.setVisibility(View.GONE);
                    attackView.setClickable(false);
                });
            } else {
                runOnUiThread(() -> {
                    finalAttackView.setVisibility(View.GONE);
                    finalAttackView.setClickable(false);
                });
            }

            backButtonPressed = false;


            // returns null for generic actions like row, guard, and focus
            if(charIsRaging && percentagize(20)){
                runOnUiThread(() -> Toast.makeText(getBaseContext(), getCurrentBattleToken().getName() + " isn't listening!", Toast.LENGTH_SHORT).show());


                midlands.post(getCurrentBattleToken().getName(), "rage", "",
                        fightUpdater.textType.RAGE_ATTACK_CHANGE, null, null);

                int rand = attackCode;
                while(rand == attackCode){
                    rand = (int) (currentCharActions.size() * Math.random());
                }
                attackCode = rand;
                selectedAction = currentCharActions.get(attackCode);

                noRageEscape = true;
            } else {
                selectedAction = getBT(currentTurnCharId).getChars().getCharAction(attackCode);
            }

            if (selectedAction != null) {

//                    startTargetProcedure

                offensiveActionPicked = !selectedAction.getTargetsAlly();
                slagveld.updateCurrentAction(selectedAction);

                setAttackTextView();

                targetables = combatants.findTargetableSerialNums(currentTurnCharId, selectedAction.getAffectedTarget());

                targetView = new targetPickerLayout(fightForeground.this, fightForeground.this,
                        targetables, combatants, selectedAction.getAffectedTarget(), offensiveActionPicked, slagveld.getIsPlayer(currentTurnCharId),
                        currentTurnCharId);

                targetResultReceived = false;
                targetFragUp = true;

                Thread fred = new Thread(targetView);
                fred.start();


            }

            // if picked a non-offensive, target-less action (except assist)
            // targetFrag is NOT NEEDED
            else {

    //                hideTextViews(false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        attackForecast.setVisibility(View.INVISIBLE);
                        forecastAffWeaponll.setVisibility(View.INVISIBLE);
                    }
                });

                offensiveActionPicked = false;
                switch (attackCode) {
                    case -1:
                        // guard action
                        slagveld.guardAction();
                        break;
                    case -2:
                        // focus action
                        slagveld.focusAction();
                        break;
                    case -3:
                        //hp potion
                        slagveld.hpPotion();
                        break;
                    case -4:
                        //sp potion
                        slagveld.spPotion();
                        break;
                    case -5:
                        //row action

                        setCharRow(currentTurnCharId, !slagveld.getIsFrontLine(currentTurnCharId));
                        break;

                    case -10:
                        //wait

                        getBT(currentTurnCharId).setSP(getBT(currentTurnCharId).getSP() + 5);
                        getBT(currentTurnCharId).removeStatusIfExists(statusEffect.INTIMIDATE);
                        break;
                }

                targetables = new ArrayList<>();
                selectedAction = new charAction();

                turnPlayerPostTargetFrag ptsd = new turnPlayerPostTargetFrag();
                Thread thread = new Thread(ptsd);
                thread.start();
            }

        }
    }
    private class turnPlayerPostTargetFrag implements Runnable {

        @Override
        public void run() {

            clashReactionLayout.setVisibility(View.INVISIBLE);
//            pointerManager.removeAllPointers();
            noRageEscape = false;

            for (int i = 0; i < targetables.size(); i++) {
                getBT(targetables.get(i)).getCharLayout().setIsTargetable(false);
            }

            if(charIsRaging && percentagize(30) && targetCodes.size() == 1 && targetables.size() > 1){
                runOnUiThread(() -> Toast.makeText(getBaseContext(), getCurrentBattleToken().getName() + " shifted targets!", Toast.LENGTH_SHORT).show());
                targetables.remove(targetCodes.get(0));
                targetCodes.set(0, targetables.get((int) (targetables.size() * Math.random())));

                midlands.post(getCurrentBattleToken().getName(), "rage", "",
                        fightUpdater.textType.RAGE_TARGET_CHANGE, null, null);
            }


                //normal procedure

            int newSP = getBT(currentTurnCharId).getSP() - selectedAction.getSPUsage();
            getBT(currentTurnCharId).setSP(newSP);

//            hideTextViews(true);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    forecastAffWeaponll.setVisibility(View.INVISIBLE);
                    attackForecast.setVisibility(View.INVISIBLE);
                }
            });


                // answers reached, now onto realizing actions!

            if (offensiveActionPicked &&
                    (selectedAction.getDamageType() == damageType.MAG ||
                    selectedAction.getDamageType() == damageType.PHYS)) {

                slagveld.doActuateDamageAndCheckKos(targetCodes);

            } else {
                // non-damaging actions
                if (selectedAction.getDamageType() == damageType.HEAL) {

                        slagveld.heal(targetCodes, selectedAction.getAffectedTarget(),
                                selectedAction.getDamageMod());

                } else {

                    //status attacks
                    if (selectedAction.getDamageType() == damageType.STATUS) {
//                                    // only status effect moves

                            slagveld.afterParty(selectedAction, targetCodes);

                        }
//                    else {
//                        // assist action
//                        if (attackCode == -6) {
//
//                                slagveld.assistManager.addAssistDuo(targetCodes.get(0), currentTurnCharId);
//
//                                String msg = getBT(currentTurnCharId).getName() + " is waiting to assist " +
//                                        getBT(targetCodes.get(0)).getName() + "!";
//
//                                runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());
//                        }
//                    }
                }

                startTurnEnd();
            }
        }
    }


    private class endGame implements Runnable {

        @Override
        public void run() {
            //game end

            if(playTaunt && !combatants.getTotalPlayerPartyKO()){
                playTauntProcedure();
            }

            try {
                synchronized (this) {
                    this.wait(300);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(isSkirmish){
                Intent intent = new Intent(fightForeground.this, warRoom.class);

                ArrayList<battleTokenParcelable> bts = new ArrayList<>();
                for (int i = 0; i < combatants.playerSize; i++) {
                    bts.add(new battleTokenParcelable(getBT(i)));
                }

                ArrayList<String> unlockedChars = combatants.getUnlockedCharsIfExist(getBaseContext());

                intent.putParcelableArrayListExtra("postCombatBTs", bts);
                intent.putStringArrayListExtra("unlockedChars", unlockedChars);
                intent.putExtra("playerWin", winningGroupIsPlayer);

                startActivity(intent);
                finish();

//                setResult(1, intent);
//                finish();
            } else {

                Intent intent = new Intent(fightForeground.this, gameEndScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                intent.putExtra("playerWin", winningGroupIsPlayer);
                intent.putExtra("turnCount", turnCount);

                startActivity(intent);
                finish();
            }

        }
    }

    //ToDo - this
    // it doesn't do anything yet make an AI or something guy
    private class turnEnemy implements Runnable {
        public void run() {
            try {

                //compute who should attack, and with what
                //temp inputs

                // BETTER WAYS TO HANDLE IDS
//                charAttackProcedure(slagveld.combatants[megaDeath.length()].getBattleSerialNum(),
//                        slagveld.combatants[0].getBattleSerialNum(), 0);


            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                System.out.println("Enemy 1 error");
            }
        }
    }

    private class rageTurnPlayer implements Runnable{

        @Override
        public void run() {
            getCurrentBattleToken().getCharLayout().imageFrame.setBackgroundResource(R.drawable.groen);

            // sets current turn wielder's bars, player or enemy (fix this when complete)
//            if (!getBT(currentTurnCharId).getIsPlayer()) {
//                enemyBars.updateBars(slagveld.getCharHpSp(currentTurnCharId));
//                runOnUiThread(() -> enemyBars.setVisibility(View.VISIBLE));
//
//            } else {
//                playerBars.updateBars(slagveld.getCharHpSp(currentTurnCharId));
//                runOnUiThread(() -> playerBars.setVisibility(View.VISIBLE));
//            }

            updateLargeBars(currentTurnCharId, getBT(currentTurnCharId).getIsPlayer());

            if(percentagize(70)) {
                // 70 percent chance to get your normal turn

                turnPlayerStart tps = new turnPlayerStart();
                Thread thr = new Thread(tps);
                thr.start();

            } else{

                runOnUiThread(() -> {
                    Toast.makeText(getBaseContext(), getCurrentBattleToken().getName() + " is acting out!", Toast.LENGTH_SHORT).show();
                    clickBlocker.setVisibility(View.VISIBLE);
                });


                // "attack frag"
                int selectedRageAttackId;

                if(!isFinalActionTime) {
                    currentCharActions = getBT(currentTurnCharId).getChars().getCharSelectedActions();
                    ArrayList<Integer> cooldownArray = getBT(currentTurnCharId).getCooldownArray(false);
                    ArrayList<Integer> possibleCharActions = new ArrayList<>();

                    String[] unavailableCause = checkSufficientSPAndBullets(getCurrentBattleToken().getCooldownArray(false), false);

                    for (int i = 0; i < currentCharActions.size() - 2; i++) {
                        if(!currentCharActions.get(i).getTargetsAlly()  &&
                                ((i == 0) || unavailableCause[i - 1] == null && cooldownArray.get(i) < 1)){
                            // aggressive attack,
                            // has enough sp & cooldown, or is normal attack

                            possibleCharActions.add(i);
                        }

                    }

                    selectedRageAttackId = possibleCharActions.get((int) (Math.random() * possibleCharActions.size()));

                } else{
                    // normal attack as the only aggressive other option

                    selectedRageAttackId = 0;
                }

                // "target frag"

                attackCode = selectedRageAttackId;
                selectedAction = getBT(currentTurnCharId).getChars().getCharAction(attackCode);

                setAttackTextView();
                backButtonPressed = false;

                //wait, so you get time to see what your char is doing
                try {
                    synchronized (this) {
                        this.wait(400);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                if (selectedAction != null) {

//                    startTargetProcedure

                    offensiveActionPicked = !selectedAction.getTargetsAlly();
                    slagveld.updateCurrentAction(selectedAction);


                    targetables = combatants.findTargetableSerialNums(currentTurnCharId, selectedAction.getAffectedTarget());

                    targetView = new targetPickerLayout(fightForeground.this, fightForeground.this,
                            targetables, combatants, selectedAction.getAffectedTarget(), offensiveActionPicked, slagveld.getIsPlayer(currentTurnCharId),
                            currentTurnCharId);

                    targetResultReceived = false;
                    targetFragUp = true;

                    targetView.setRaging();

                    Thread thread = new Thread(targetView);
                    thread.start();

                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                    runOnUiThread(() -> clickBlocker.setVisibility(View.GONE));

                    charIsRaging = false;

                } else{

                    Toast.makeText(getBaseContext(), "error action is null", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }



    void damageNums(int dmg, boolean koed, effectiveness affEff, String affToPrint, int targetId) {

        if(dmg == 0){
            dmg = 1;
        }

        String msg = dmg + " damage!";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(msg);

        if(Objects.equals(affToPrint, null)) {

            if (isCrit) {
                msg += "\nCRITICAL!";
                String critMsg = "\nCRITICAL!";

                int startIndex = msg.indexOf(critMsg);
                int endIndex = startIndex + critMsg.length() - 1;

//            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getTextColorForOutcome(false));
//            spannableStringBuilder.setSpan(foregroundColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            }
            if (isBlock) {
                msg += "\nBLOCK!";
                String blockMsg = "\nBLOCK!";

                int startIndex = msg.indexOf(blockMsg);
                int endIndex = startIndex + blockMsg.length() - 1;

//            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getTextColorForOutcome(true));
//            spannableStringBuilder.setSpan(foregroundColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            switch (affEff) {
                case IMMUNE:
                    msg += "\nImmune...";
                    break;
                case EFFECTIVE:
                    msg += "\nSUPER EFFECTIVE!";
                    break;
                case INEFFECTIVE:
                    msg += "\nNot very effective...";
            }

        } else{
            msg += "\n" + affToPrint.toUpperCase() + "-Aff!";

            if(!getCurrentTargetBattleToken().hasAegisShield()) {
                switch (affinityTable.getEffectiveness(affinity.getAffinity(affToPrint), slagveld.getCurrentTargetBTId().getAffinity())) {
                    case IMMUNE:
                        msg += "\nImmune...";
                        break;
                    case EFFECTIVE:
                        msg += "\nSUPER EFFECTIVE!";
                        break;
                    case INEFFECTIVE:
                        msg += "\nNot very effective...";
                }
            }
        }

        enactElicitOutcomeView(msg, koed, targetId);
    }

    private void updateSmallBars(characterLayout targetCL, float[] smallBarFilledPercentage) {

//        runOnUiThread(() -> {
//            targetCL.hpbar.bar.setLayoutParams(new RelativeLayout.LayoutParams(
//                    targetCL.hpbar.bar.getWidth(),
//                    (int) (targetCL.hpbar.length * smallBarFilledPercentage[0])));
//            targetCL.hpbar.bar.setBackgroundColor(targetCL.hpbar.getColorAndFullness(smallBarFilledPercentage[0]));
//
//            targetCL.spbar.bar.setLayoutParams(new RelativeLayout.LayoutParams(
//                    targetCL.spbar.bar.getWidth(),
//                    (int) (targetCL.spbar.length * smallBarFilledPercentage[1])));
//            targetCL.spbar.bar.setBackgroundColor(targetCL.spbar.getColorAndFullness(smallBarFilledPercentage[1]));
//        });

    }

    public Runnable checkRowCollapse(boolean isPlayer) {

        // if game over, or row is ALREADY collapsed, no repeat
        if (!(combatants.getTotalEnemyPartyKO() || combatants.getTotalPlayerPartyKO()) &&
                !combatants.rowCollapsed(isPlayer)) {
            if ((!isPlayer)) {
//                runOnUiThread(() -> er1.invalidate());

                if (!combatants.charExistsOnFirstRow(false) && combatants.enemiesLeftToSpawn.size() == 0) {
                    return () -> {
                        slagveld.setRowCollapse(false);
                        viewAnimationHelper.fightScreenCollapseRows(fightForeground.this,
                                er1, er2, false);
                    };
                }
            } else {
//                runOnUiThread(() -> pr1.invalidate());

                if (!combatants.charExistsOnFirstRow(true)) {

                    return () -> {
                        slagveld.setRowCollapse(true);
                        viewAnimationHelper.fightScreenCollapseRows(fightForeground.this,
                                pr1, pr2, true);
                    };
                }
            }
        }
        return null;
    }

    //checks the aftermath to see if any chars were koed, then exacts the consequences
    // sfx, and checks for game over
    public void checkKOs() {

        boolean koed = false;

        ArrayList<battleToken> activeBattleTokens = combatants.getActiveBattleTokens();

        // toasts for chars that have been koed + sfx
        //todo - something here makes the game freeze sometimes
        for (int i = 0; i < activeBattleTokens.size(); i++) {

            final battleToken curTargetBT = activeBattleTokens.get(i);

            if (curTargetBT.getHP() <= 0) {

                koed = true;
                curTargetBT.setIsAlive(false);

                if(curTargetBT.hasStatus(statusEffect.GRAPPLE)){

                    int target = (int) curTargetBT.getBonusEffectExtra(statusEffect.GRAPPLE);

                    getBT(target).grappleRelease(true, true);
                    curTargetBT.grappleRelease(true, true);

                }

                combatants.charKoed(curTargetBT);
                slagveld.charKoed(curTargetBT.getBattleSerialNum());

                fightBackground.assistManager.removeDuo(curTargetBT.getBattleSerialNum());

                fightMidgrounds.post(curTargetBT.getName(), curTargetBT.getName(),
                        selectedAction.getName(), fightUpdater.textType.KOED, dmgTaken, null);

                ViewGroup parent = (ViewGroup) curTargetBT.getCharLayout().getParent();
                runOnUiThread(() -> parent.removeView(curTargetBT.getCharLayout()));

                if(!curTargetBT.getIsPlayer()){
                    Integer charToSpawn = combatants.getEnemyToSpawn();
                    if(charToSpawn != null){

                        setCharRow(charToSpawn, curTargetBT.getIsFrontLine());
                        sonicJammer.playSound("step_in", getBaseContext());

                        slagveld.addToTurnOrder(false, charToSpawn);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), getBT(charToSpawn).getName() + " has jumped in as Reinforcements!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }


                Runnable runnable = () -> {
                    playCharSFX(curTargetBT.getBattleSerialNum(), moveType.KO);
                    if (!curTargetBT.getIsPlayer()) {
//                        sonicJammer.playSound("enemy_ko", getBaseContext());
                        sonicJammer.playSound(getBaseContext(), null);

                        Runnable run = checkRowCollapse(false);
                        if (run != null) {
                            run.run();
                        }

                    } else {
//                        sonicJammer.playSound("player_ko", getBaseContext());
                        sonicJammer.playSound(getBaseContext(), null);

                        Runnable run = checkRowCollapse(true);
                        if (run != null) {
                            run.run();
                        }

                        if ((initialPlayerNum == 3 || isSkirmish )&&
                                (combatants.getActiveBattleTokens(true).size() == 1)){
                            sonicJammer.init1CharSequence(getBaseContext());
                        }
                    }
                };
                Thread rowCollapse = new Thread(runnable);
                rowCollapse.start();

            }
        }

        // checks for any tpk, if so sends end results to main
        if (koed) {
            // party tpk
            if (combatants.getTotalPlayerPartyKO()) {
                winningGroupIsPlayer = false;
                gameOver = true;

                endGame endGameThread = new endGame();
                Thread threadEnd = new Thread(endGameThread);
                threadEnd.start();
            }

            // enemy tpk
            if (combatants.getTotalEnemyPartyKO()) {
                winningGroupIsPlayer = true;
                gameOver = true;

                endGame endGameThread = new endGame();
                Thread threadEnd = new Thread(endGameThread);
                threadEnd.start();
            }
        }


        if(!gameOver) {
            turnStart start = new turnStart();
            Thread thread = new Thread(start);
            thread.start();
        }
    }

    private void setCharBackgroundResIds() {

        ArrayList<Integer> resIds = combatants.getAllCharResIds();

        for (int i = 0; i < combatants.length(); i++) {
            getBT(i).getCharLayout().getBackgroundImageView().setBackgroundResource(resIds.get(i));
            getBT(i).getCharLayout().affIcon.setBackgroundResource(getImageRes.getAffinityImageResource(getBT(i).getAffinity()));
            getBT(i).getCharLayout().weaponIcon.setBackgroundResource(getImageRes.getWeaponIcon(getBT(i).getWeapon()));
        }
    }

    public void setStatusIcon(int id, statusEffect status) {
        ImageView statusIcon = getBT(id).getCharLayout().statusIcon;

        int resId = getImageRes.getStatusIcon(status);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusIcon.setBackgroundResource(resId);
                statusIcon.setVisibility(View.VISIBLE);

                getBT(id).getCharLayout().invalidate();
            }
        });
    }


//              side stuff

    public void enactElicitOutcomeView(String msg, boolean koed){
        elicitOutcomeView el = new elicitOutcomeView(msg, koed);

        Thread thread = new Thread(el);
        thread.start();
    }
    public void enactElicitOutcomeView(String msg, boolean koed, int targetId){
        elicitOutcomeView el = new elicitOutcomeView(msg, koed, targetId);

        Thread thread = new Thread(el);
        thread.start();
    }

    class elicitOutcomeView implements Runnable {

        String msg;
        boolean koed;
        Integer targetId;

        public elicitOutcomeView(String msg, boolean koed) {
            this.msg = msg;
            this.koed = koed;
        }
        public elicitOutcomeView(String msg, boolean koed, Integer targetId) {
            this.msg = msg;
            this.koed = koed;
            this.targetId = targetId;
        }


        @Override
        public void run() {
            if(targetId == null){
                targetId = getCurrentTargetBattleToken().getBattleSerialNum();
            }

            if (!getBT(targetId).getIsPlayer()) {
                AnimatorSet fef = viewAnimationHelper.showFightOutcome(fightForeground.this, resultEnemyll, false);

                String finalName;
                if(getBT(targetId).hasAegisShield()){
                    finalName = "Aegis Shield";
                } else {
                    finalName = getBT(targetId).getName();
                }
                String msgKO = getBT(targetId).getName() + " has\nbeen KOed!";


                runOnUiThread(() -> {
                    resultEnemyName.setText(finalName);
                    resultEnemy.setText(msg);
                    if (koed) {
                        resultEnemyKoTV.setText(msgKO);
                        resultEnemyKoTV.setVisibility(View.VISIBLE);
                        resultEnemyll.setAlpha(1);
                    } else {
                        resultEnemyKoTV.setVisibility(View.GONE);
                    }

                    fef.start();
                });

            } else {
                AnimatorSet fef = viewAnimationHelper.showFightOutcome(fightForeground.this, resultPlayerll, true);

                String finalName, msgKO;
                if(getBT(targetId).hasAegisShield()){
                    finalName = "Aegis Shield";
                    msgKO = "Aegis Shield has SHATTERED! (" + getBT(targetId).getName() +")";
                } else {
                    finalName = getBT(targetId).getName();
                    msgKO = getBT(targetId).getName() + " has\nbeen KOed!";
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        resultPlayerName.setText(finalName);
                        resultPlayer.setText(msg);

                        if (koed) {
                            resultPlayerKoTV.setText(msgKO);
                            resultPlayerKoTV.setVisibility(View.VISIBLE);
                        } else {
                            resultPlayerKoTV.setVisibility(View.GONE);
                        }

                        fef.start();
                    }
                });
            }
        }
    }


    private void showSideBarName() {
        AnimatorSet as;

        if (slagveld.getIsPlayer(currentTurnCharId)) {
            as = viewAnimationHelper.showSideBarName(resultPlayerName, true);
        } else {
            as = viewAnimationHelper.showSideBarName(resultEnemyName, false);
        }

        runOnUiThread(() -> as.start());
    }

    private void hideSideBarName() {
        AnimatorSet as;
        boolean isPlayer = slagveld.getIsPlayer(currentTurnCharId);

        if (isPlayer) {
            as = viewAnimationHelper.hideSideBarName(resultPlayerName, true);
        } else {
            as = viewAnimationHelper.hideSideBarName(resultEnemyName, false);
        }

        runOnUiThread(() -> {
            if (isPlayer) {
                resultPlayer.setVisibility(View.GONE);
                resultPlayerKoTV.setVisibility(View.GONE);

                resultPlayerll.setVisibility(View.VISIBLE);
            } else {
                resultEnemy.setVisibility(View.GONE);
                resultEnemyKoTV.setVisibility(View.GONE);

                resultEnemyll.setVisibility(View.VISIBLE);
            }


            as.start();
        });
    }


    //todo - does work? yeah looks great man
//    private class centralizeCurrentTurnHolder implements Runnable {
//
//        @Override
//        public void run() {
//
//
//        }
//
//    }

//    private void setTargetablesVisibility(boolean toggleEffectsOn) {
//
//        if (toggleEffectsOn) {
//
//            // attackCode doesn't exist yet! make a normal attack for all
//            ArrayList<Integer> targetables = slagveld.getTargetablesId(currentTurnCharId, attackCode);
//            screenShaderView.setVisibility(View.VISIBLE);
//            slagveld.jutOutTargetables(targetables);
//
//        } else {
//            screenShaderView.setVisibility(View.GONE);
//            slagveld.resetAllCharElevation();
//        }
//    }


    //////////////////////////////////////////////////////////

//              TextViews     /    targetFrag

    public int getTextColor(battleToken bt) {
        int color;
        if (!bt.getIsPlayer()) {
            color = Color.parseColor("#EBCC1D1D");
        } else {
            color = Color.parseColor("#EB3CA1CF");
        }

        return color;
    }

    public int getTextColorForOutcome(boolean isGoodISupposeIDontKnowWhatToNameThisVariable) {
        int color;
        if (isGoodISupposeIDontKnowWhatToNameThisVariable) {
            color = Color.parseColor("#0B4560");
        } else {
            color = Color.parseColor("#EB8C0B0B");
        }

        return color;
    }

    public void setAttackTextView() {

        String attackName;

        if (attackCode > 0) {
//            this.attackTextView.setText(getBT(currentTurnCharId).getChars()
//                    .getCharAction(this.attackCode).getName());
//            attackTextView.setText(selectedAction.getName());
            attackName = selectedAction.getName();
        } else {
            switch (attackCode) {
                case 0:
                default:
//                    this.attackTextView.setText(getResources().getString(R.string.attack));
                    attackName = getResources().getString(R.string.attack);
                    break;
                case -1:
//                    this.attackTextView.setText(getResources().getString(R.string.guard));
                    attackName = getResources().getString(R.string.wait_action);
                    break;
                case -2:
//                    this.attackTextView.setText(getResources().getString(R.string.focus));
                    attackName = getResources().getString(R.string.focus);
                    break;
                case -3:
//                    this.attackTextView.setText(getResources().getString(R.string.hp_potion));
                    attackName = getResources().getString(R.string.hp_potion);
                    break;
                case -4:
//                    this.attackTextView.setText(getResources().getString(R.string.sp_potion));
                    attackName = getResources().getString(R.string.sp_potion);
                    break;
                case -5:
//                    this.attackTextView.setText(getResources().getString(R.string.row));
                    attackName = getResources().getString(R.string.row);
                    break;
                case -6:
//                    this.attackTextView.setText(getResources().getString(R.string.assist));
                    attackName = getResources().getString(R.string.assist);
                    break;
                case -7:
//                    attackTextView.setText(getCurrentBattleToken().getChars().getCharAction(-7).getName());
                    attackName = getCurrentBattleToken().getChars().getCharAction(-7).getName();
                    break;
                case -8:
//                    attackTextView.setText(getCurrentBattleToken().getChars().getCharAction(-7).getName());
                    attackName = getCurrentBattleToken().getChars().getCharAction(-8).getName();
                    break;

            }

        }

        largeBarsContainerLayout cur;
        if(slagveld.getIsPlayer(currentTurnCharId)){
            cur = playerBars;
        } else{
            cur = enemyBars;
        }
        cur.updateAttackName(attackName);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewAnimationHelper.showViewWithAnimation(cur.attackName, 100);
            }
        });
    }


    public void updateTargetFragClickedTarget(int id, boolean first) {

        if (id != -1) {
            battleToken clickedTarget = getBT(id);
            if ((clickedTarget.getIsPlayer() != getCurrentBattleToken().getIsPlayer() && offensiveActionPicked) ||
                    (!clickedTarget.getIsPlayer() == !getCurrentBattleToken().getIsPlayer() && !offensiveActionPicked)) {
                updateCharLayoutFocusFrame(id, first);

                if (offensiveActionPicked) {

                    actionBoostParams bonuses = fightBackground.assistManager
                            .getAssistStatBonuses(currentTurnCharId, selectedAction.getIsMag());

                    attackValues vals = slagveld.getAttackValues(id);
                    int minDMG = vals.minDMG;
                    int hitP = vals.hitChance;
                    int critP = vals.critChance;
                    int blockP = vals.blockChance;

                    int attackNum = selectedAction.getNumOfAttacks();
                    String numOfAttacks = "";
                    if(attackNum > 1){
                        numOfAttacks = " x" + attackNum;
                    }

                    String initString = "Min. Damage: ";
                    if(vals.hasAegisShield){
                        initString = "Aegis DMG: ";
                    }


                    String forecastMsg;
                    if (!fightBackground.assistManager.isBeingAssisted(currentTurnCharId)) {
                        forecastMsg =  initString + minDMG + numOfAttacks +
                                "\nHit: " + hitP + "%\nCrit: " + critP + "%\nBlock: " + blockP + "%";
                    } else {

                        minDMG -= bonuses.attackBoost;
                        if(minDMG < 0){
                            minDMG = 0;
                        }
                        hitP -= bonuses.hitBoost;
                        if(hitP < 0){
                            hitP = 0;
                        }
                        critP -= bonuses.critBoost;
                        if(critP < 0){
                            critP = 0;
                        }
                        blockP -= bonuses.blockBoost;
                        if(blockP < 0){
                            blockP = 0;
                        }

                        forecastMsg = initString + minDMG +
                                " +" + (int) bonuses.attackBoost + numOfAttacks +
                                "\nHit: " + hitP + "% +" + (int) bonuses.hitBoost + "%\n" +
                                "Crit: " + critP + "% +" + (int) bonuses.critBoost + "%\n" +
                                "Block: " + blockP + "% -" + (int) bonuses.blockBoost + "%";
                    }

                    int[] forecastImgResIds = slagveld.getEffectivenessForecastImgRes();

                    boolean showClashForecast = false;
                    if(slagveld.clashReactionTriggered()){

//                        clashReactionLayout = new clashReactionForecast(getBaseContext(),
//                                combatants);

                        showClashForecast = true;
                    }
                    boolean finalShowClashForecast = showClashForecast;

                    runOnUiThread(() -> {
//                        targetTextView.setTextColor(getTextColor(clickedTarget));
//                        targetTextView.setText(clickedTarget.getName());
//                        viewAnimationHelper.showViewWithAnimation(targetTextView, 100);
//                        targetTextView.setVisibility(View.VISIBLE);

                        attackForecast.setText(forecastMsg);
                        viewAnimationHelper.showViewWithAnimation(attackForecast, 100);
                        attackForecast.setVisibility(View.VISIBLE);

                        if(finalShowClashForecast) {
                            clashReactionLayout.setInfo(selectedAction.getAffinity(), currentTurnCharId, id);
                            clashReactionLayout.setVisibility(View.VISIBLE);

                            ViewParent parent = fightMidgrounds.battleLog.getParent();
                            ((ViewGroup) parent).bringChildToFront(clashReactionLayout);
                        }

                        forecastAffAtkrIcon.setBackgroundResource(forecastImgResIds[0]);
                        forecastAffDefIcon.setBackgroundResource(forecastImgResIds[1]);
                        forecastAffEffIcon.setBackgroundResource(forecastImgResIds[2]);
                        forecastWeaponAtkrIcon.setBackgroundResource(forecastImgResIds[3]);
                        forecastWeaponDefIcon.setBackgroundResource(forecastImgResIds[4]);
                        forecastWeaponEffIcon.setBackgroundResource(forecastImgResIds[5]);

                        viewAnimationHelper.showViewWithAnimation(forecastAffWeaponll, 100);
                        forecastAffWeaponll.setVisibility(View.VISIBLE);
                    });
                }
            }
        }
    }

    @Override
    public void setAttackFragClickability(boolean clickability){
        runOnUiThread(() -> attackView.setClickable(clickability));
    }

    @Override
    public void post(String charName, String targetName, String attackName, fightUpdater.textType classification,
                     Integer value, stat changingStat){
        midlands.post(charName, targetName, attackName, classification, value, changingStat);
    }

    // statics


    public static void setDmgTaken(int i) {
        dmgTaken = i;
    }
    public static void setIsCrit(boolean b) {
        isCrit = b;
    }
    public static void setIsBlock(boolean b) {
        isBlock = b;
    }

    public void setAffEff(effectiveness eff) {
        midlands.setAffEff(eff);
    }
    public void setIsFinalActionTime(boolean b){ isFinalActionTime = b; }

    public int getAttackCode(){
        return attackCode;
    }


//              battleTokens

    public battleToken getCurrentBattleToken() {
        return this.slagveld.getCurrentCharBT();
    }

    public battleToken getCurrentTargetBattleToken() {
        return this.slagveld.getCurrentTargetBTId();
    }


//              largeBars

    public void updateLargeBarsLengthAndShow(boolean isPlayerBars, double hpBarPercent, double spBarPercent) {

        int barHeight = playerBars.hp.getBar().getHeight();

        runOnUiThread(() -> {
            if (isPlayerBars) {
//                playerBars.hp.getBar().setLayoutParams(new FrameLayout.LayoutParams((int) (barHeight * hpBarPercent), 18));
//                playerBars.sp.getBar().setLayoutParams(new FrameLayout.LayoutParams((int) (barHeight * spBarPercent), 18));
                viewAnimationHelper.showViewWithAnimation(playerBars, 100);
                playerBars.setVisibility(View.VISIBLE);

            } else {
//                enemyBars.hp.getBar().setLayoutParams(new FrameLayout.LayoutParams((int) (barHeight * hpBarPercent), 18));
//                enemyBars.sp.getBar().setLayoutParams(new FrameLayout.LayoutParams((int) (barHeight * spBarPercent), 18));
                viewAnimationHelper.showViewWithAnimation(enemyBars, 100);
                enemyBars.setVisibility(View.VISIBLE);

            }

        });
    }

    public void updateLargeBars(int id, boolean isPlayerSide) {

        largeBarsContainerLayout targetView;
        if (isPlayerSide) {
            targetView = playerBars;
        } else {
            targetView = enemyBars;
        }

        int[] hpsp = slagveld.getCharHpSp(id);


        battleToken curChar = getBT(id);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                targetView.updateChar(curChar.getName(), curChar.getAffinity(), curChar.getWeapon(),
                        curChar.getStatusEffectsWithPriority(), hpsp, curChar.getBulletCount());
                targetView.attackName.setVisibility(View.INVISIBLE);
            }
        });
        updateLargeBarsLengthAndShow(isPlayerSide, (double) hpsp[0] / hpsp[1], (double) hpsp[2] / hpsp[3]);
    }


//              focus frames

    public int setAllCharLayoutFocusFramesInitial(targetPickerLayout.targetEffectsList statusMap) {

        ArrayList<Integer>[] targetables = slagveld.getRangeSortedTargetables(selectedAction);
        int initialTargetId = -1;

        if (!statusMap.engageExists) {
            if (!targetables[0].isEmpty()) {
                for (int i = 0; i < targetables[0].size(); i++) {
                    if(getCurrentBattleToken().hasStatus(statusEffect.GRAPPLE)){
                        initialTargetId = (int) getBT(currentTurnCharId).getBonusEffectExtra(statusEffect.GRAPPLE);
                    } else {
                        if (statusMap.isAttackable(targetables[0].get(i))) {
                            initialTargetId = targetables[0].get(i);
                            break;
                        }
                    }
                }

            } else {
                if (!targetables[1].isEmpty()) {
                    for (int i = 0; i < targetables[1].size(); i++) {

                        if (statusMap.isAttackable(targetables[1].get(i))) {
                            initialTargetId = targetables[1].get(i);
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < targetables[2].size(); i++) {

                        if (statusMap.isAttackable(targetables[2].get(i))) {
                            initialTargetId = targetables[2].get(i);
                            break;
                        }
                    }
                }
            }
        } else {
            initialTargetId = statusMap.imposerId;
        }

        int finalInitialTargetId = initialTargetId;
        runOnUiThread(() -> {
            // 0 for within range, 1 for 1 out, 2 for 2 out
            for (int i = 0; i < targetables[0].size(); i++) {
                getBT(targetables[0].get(i)).getCharLayout().imageFrame.setBackgroundResource(R.drawable.dark_bloo);
            }

            for (int i = 0; i < targetables[1].size(); i++) {
                getBT(targetables[1].get(i)).getCharLayout().imageFrame.setBackgroundResource(R.drawable.dim_darkest_bloo);
            }

            //rest are NOT in targetables (KO'ed or otherwise), and stay with empty_bar

            updateTargetFragClickedTarget(finalInitialTargetId, true);
        });

        return initialTargetId;
    }

    public void updateCharLayoutFocusFrame(int clickedCharId, boolean first) {

        if (!first) {
            //for old target
            runOnUiThread(() -> {
                switch (slagveld.rangeify(selectedAction.getRange())) {
                    case 0:
                        slagveld.getCurrentTargetBTId().getCharLayout().imageFrame.setBackgroundResource(R.drawable.dark_bloo);
                        break;
                    case 1:
                        slagveld.getCurrentTargetBTId().getCharLayout().imageFrame.setBackgroundResource(R.drawable.dim_darkest_bloo);
                        break;
                    case 2:
                        slagveld.getCurrentTargetBTId().getCharLayout().imageFrame.setBackgroundResource(R.drawable.empty_bar);
                }
            });

        }

        slagveld.setCurrentTargetBT(clickedCharId);

        // for targeted individual
        switch (slagveld.rangeify(selectedAction.getRange())) {
            case 0:
                slagveld.getCurrentTargetBTId().getCharLayout().imageFrame.setBackgroundResource(R.drawable.bloo);
                break;
            case 1:
                slagveld.getCurrentTargetBTId().getCharLayout().imageFrame.setBackgroundResource(R.drawable.redoo);
                break;
            case 2:
                slagveld.getCurrentTargetBTId().getCharLayout().imageFrame.setBackgroundResource(R.drawable.dark_redoo);
        }

    }
    
    public void resetFocusFrames(){
        ArrayList<characterLayout> charLayouts = new ArrayList<>();
        ArrayList<battleToken> bts = combatants.getActiveBattleTokens();

        for (int i = 0; i < bts.size(); i++) {
            charLayouts.add(bts.get(i).getCharLayout());
        }

        runOnUiThread(() -> {
            for (int i = 0; i < charLayouts.size() ; i++) {
                charLayouts.get(i).imageFrame.setBackgroundResource(R.drawable.empty_bar);
            }
        });
    }

//              random

    battleToken getBT(Integer id) {
        return combatants.getBattleToken(id);
    }

    private LinearLayout getLinearLayout(boolean isPlayer, boolean isFrontRow){
        if(isPlayer){
            if(isFrontRow){
                return pr1;
            } else{
                return pr2;
            }
        } else{
            if(isFrontRow){
                return er1;
            } else{
                return er2;
            }
        }
    }
    public void setCharRow(int charId, boolean toFrontRow) {

        battleToken currentBattleToken = getBT(charId);
        boolean isPlayer = currentBattleToken.getIsPlayer();



        LinearLayout destinationLL = getLinearLayout(isPlayer, toFrontRow);

        if(combatants.executeRowSwapIfAvailable(charId, toFrontRow)){


            ViewGroup head = (ViewGroup) currentBattleToken.getCharLayout().getParent();

            //if mission complete
            runOnUiThread(() -> {

                if (head != null) {
                    head.removeView(currentBattleToken.getCharLayout());
                }
                destinationLL.addView(currentBattleToken.getCharLayout());
            });

        }

        Runnable runnable = checkRowCollapse(isPlayer);
        if (runnable != null) {
            runnable.run();

            if(currentBattleToken.getBattleSerialNum() == currentTurnCharId){
                playCharSFX(currentTurnCharId, moveType.ROW_SHIFT);
            } else{
                playCharSFX(currentBattleToken.getBattleSerialNum(), moveType.DEBUFF);
            }
        }

    }

    public void setInitialRowPlacements(boolean[] isFrontLineArray, ArrayList<String> enemyNames,
                                        boolean[] enemyFrontLineArray) {

        for (int i = 0; i < combatants.playerSize; i++) {
//            this.pr1.addView(this.slagveld.getCharLayout(i));
            setCharRow(i, isFrontLineArray[i]);
        }

        int enemyRow1Size = 0;
        int enemyRow2Size = 0;

        for (int i = 0; i < combatants.getActiveBattleTokens(false).size(); i++) {

            boolean charIsFrontRow;

            if(enemyFrontLineArray == null || Objects.equals(enemyNames.get(i), "-")) {
                charIsFrontRow = slagveld.enemyShouldBeFrontLine(i + combatants.playerSize);
            } else{
                charIsFrontRow = enemyFrontLineArray[i];
            }

            if (enemyRow1Size >= 3) {
                charIsFrontRow = false;
            } else {
                if (enemyRow2Size >= 3) {
                    charIsFrontRow = true;
                }
            }

            if (charIsFrontRow) {
                enemyRow1Size++;
            } else {
                enemyRow2Size++;
            }

            setCharRow(i + combatants.playerSize, charIsFrontRow);
        }
    }
    public void setInitialRowPlacements(boolean[] isFrontLineArray) {

        for (int i = 0; i < combatants.playerSize; i++) {
//            this.pr1.addView(this.slagveld.getCharLayout(i));
            setCharRow(i, isFrontLineArray[i]);
        }

        int enemyRow1Size = 0;
        int enemyRow2Size = 0;

        for (int i = 0; i < (combatants.length() - combatants.playerSize)
                && i < 6; i++) {

            boolean charIsFrontRow;

            charIsFrontRow = slagveld.enemyShouldBeFrontLine(i + combatants.playerSize);


            if (enemyRow1Size >= 3) {
                charIsFrontRow = false;
            } else {
                if (enemyRow2Size >= 3) {
                    charIsFrontRow = true;
                }
            }

            if (charIsFrontRow) {
                enemyRow1Size++;
            } else {
                enemyRow2Size++;
            }

            setCharRow(i + combatants.playerSize, charIsFrontRow);
        }
    }

    // either "lockdown", "full", or null
    public String rowActionUnavailableCause() {
        String res = null;

        boolean isPlayer = getBT(currentTurnCharId).getIsPlayer();
        boolean isFrontLine = getBT(currentTurnCharId).getIsFrontLine();

        if(!getCurrentBattleToken().hasStatus(statusEffect.ROW_LOCKDOWN)) {
            if (isPlayer) {
                //player
                if (isFrontLine) {
                    if (pr2.getChildCount() >= 3) {
                        res = "full";
                    }
                } else {
                    if (pr1.getChildCount() >= 3) {
                        res = "full";
                    }
                }
            } else {
                //enemy
                if (isFrontLine) {
                    if (er2.getChildCount() >= 3) {
                        res = "full";
                    }
                } else {
                    if (er1.getChildCount() >= 3) {
                        res = "full";
                    }
                }
            }
        } else{
            res = "lockdown";
        }

        return res;
    }
    public boolean rowActionAvailable(int id) {
        boolean res = true;

        boolean isPlayer = getBT(id).getIsPlayer();
        boolean isFrontLine = getBT(id).getIsFrontLine();

        if(!getBT(id).hasStatus(statusEffect.ROW_LOCKDOWN)) {
            if (isPlayer && !combatants.rowCollapsed(isPlayer)) {
                //player
                if (isFrontLine) {
                    if (pr2.getChildCount() >= 3) {
                        res = false;
                    }
                } else {
                    if (pr1.getChildCount() >= 3) {
                        res = false;
                    }
                }
            } else {
                if (!combatants.rowCollapsed(isPlayer)) {
                    //enemy
                    if (isFrontLine) {
                        if (er2.getChildCount() >= 3) {
                            res = false;
                        }
                    } else {
                        if (er1.getChildCount() >= 3) {
                            res = false;
                        }
                    }
                }
            }
        } else{
            res = false;
        }

        return res;
    }

    public String[] checkSufficientSPAndBullets(ArrayList<Integer> cooldownArray, boolean isFinaleMoves) {
        int currentSPValue = getBT(currentTurnCharId).getSP();

        ArrayList<Integer> destructableCooldownArray = new ArrayList<>(cooldownArray);

        if(!isFinaleMoves) {
            String[] attackUnavailableCause = new String[6];
            if(destructableCooldownArray.size() == 5) {
                destructableCooldownArray.add(0, 0);
            }

            for (int i = 0; i < currentCharActions.size() && i < 6; i++) {
                attackUnavailableCause[i] = getActionAvailable(i, currentSPValue, destructableCooldownArray.get(i));
            }

            return attackUnavailableCause;
        } else {
            String[] attackUnavailableCause = new String[6];


            attackUnavailableCause[0] = getFinalActionAvailable(-7, currentSPValue, destructableCooldownArray.get(0));
            attackUnavailableCause[1] = getFinalActionAvailable(-8, currentSPValue, destructableCooldownArray.get(1));
            attackUnavailableCause[2] = getFinalActionAvailable(-9, currentSPValue, destructableCooldownArray.get(2));
            attackUnavailableCause[3] = getFinalActionAvailable(-11, currentSPValue, destructableCooldownArray.get(3));


            return attackUnavailableCause;

        }
    }
    private String getActionAvailable(int id, int spval, int cooldownVal){

        String attackUnavailableCause = null;
        charAction curr = currentCharActions.get(id);

        if(!(curr.getActionUse() > getCurrentBattleToken().getActionsRemaining() + 1)) {
            // has enough turns to use action
            if (cooldownVal == 0) {
                if ((curr.getWeaponType() == weaponType.RIFLE || curr.getWeaponType() == weaponType.CANNON)
                        && getCurrentBattleToken().getBulletCount() == 0) {
                    attackUnavailableCause = "no_bullets";
                }

                if (spval < curr.getSPUsage()) {
                    attackUnavailableCause = "sp_deficit";
                }

                if((curr.getAffectedTarget() == target.GRAPPLED ||curr.getAffectedTarget() == target.SWALLOWED) &&
                    !getCurrentBattleToken().hasStatus(statusEffect.GRAPPLE)){
                    attackUnavailableCause = "no_grapple";
                }
            } else {
                attackUnavailableCause = "cooldown" + cooldownVal;
            }
        } else{
            //insufficient actions left
            attackUnavailableCause = "insuffActions";
        }

        return attackUnavailableCause;
    }
    private String getFinalActionAvailable(int id, int spval, int cooldownVal){

        String attackUnavailableCause = null;
        charAction curr = getCurrentBattleToken().getChars().getCharAction(id);

        if(curr != null) {
            if (cooldownVal == 0) {
                if (getCurrentBattleToken().usesBullets() && ((curr.getWeaponType() == weaponType.RIFLE || curr.getWeaponType() == weaponType.CANNON)
                        && getCurrentBattleToken().getBulletCount() == 0)) {
                    attackUnavailableCause = "no_bullets";
                }

                if (spval <= curr.getSPUsage()) {
                    attackUnavailableCause = "sp_deficit";
                }
            } else {
                attackUnavailableCause = "cooldown" + cooldownVal;
            }
        }



        return attackUnavailableCause;
    }

    public void healToast(String healee, int value, boolean isHP) {

        String hpsp;

        if (value > 0) {
            if (isHP) {
                hpsp = " HP!";
            } else {
                hpsp = " SP!";
            }
            final String msg = healee + " has been healed for " + value + hpsp;

            runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());
        }
    }

    public String getTurnUnavailableMsg(String charName, String inflictorName, statusEffect stat){

        String msg = charName + " has NULL status effect, fix me plzzzz";

            switch (stat){
                case STUN:
                    msg = charName + " is Stunned, and Cannot Move!";
                    break;
                case SLEEP:
                    msg = charName + " is Asleep, and Cannot Move!";
                    break;
                case FREEZE:
                    msg = charName + " is Frozen, and Cannot Move!";
                    break;
                case CHARM:
                    msg = charName + " is in love with " + inflictorName + ", and cannot move!";
                    break;
                default:
            }

            if(stat == statusEffect.SLEEP){
                playCharSFX(currentTurnCharId, moveType.SLEEP);
            } else{
                playCharSFX(currentTurnCharId, moveType.STATUS);
            }

        return msg;
    }


    public void playCharSFX(int charID, moveType moveType){
        getBT(charID).playSFX(moveType);
    }

    private void playTauntProcedure(){
        ArrayList<battleToken> activeBTs = combatants.getActiveBattleTokens(true);
        battleToken bt = activeBTs.get((int) (activeBTs.size() * Math.random()));

        Thread thr = bt.playSFX(moveType.TAUNT);

        try {
            thr.join();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startTurnEnd(){
        turnEnd teem = new turnEnd();
        Thread thread = new Thread(teem);
        thread.start();
    }


//              Override methods

    @Override
    public void attackPicked(int attackId) {
        attackCode = attackId;

        turnPlayerPostAtkFrag frish = new turnPlayerPostAtkFrag();
        Thread postAtkThread = new Thread(frish);
        postAtkThread.start();
    }

    @Override
    public void rowCollapsedToast() {

        String msg = "Front Row has Collapsed!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void rowFilledToast() {

        String msg = "Row is at full capacity!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void rampartToast(String rampName) {
        String msg = rampName + " is acting as a Rampart!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());

        sonicJammer.playSound("error", getBaseContext());
    }

    @Override
    public void attackUnavailable(String cause) {
        String msg;

        switch (cause){
            case "sp_deficit":
                msg = "Insufficient SP!";
                break;
            case "no_grapple":
                msg = "No Target!";
                break;
            case "no_bullets":
                msg = "Reload Required!";
                break;
            case "insuffActions":
                msg = "Not enough Actions Remaining!";
                break;
            default:
                msg = "There are ";

                if(cause.charAt(cause.length() - 2) == 'n'){
                    msg += cause.charAt(cause.length() - 1) + " turns of Cooldown Left!";
                } else {
                    msg += cause.substring(cause.length() - 2) + " turns of Cooldown Left!";
                }
        }

        String finalMsg = msg;
        runOnUiThread(() -> Toast.makeText(getBaseContext(), finalMsg, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void rowLockdownToast() {
        sonicJammer.playSound("error", getBaseContext());
        String msg = getBT(currentTurnCharId).getName() + " has the Row Lockdown Effect!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void intimidateToast(String charName) {
        String msg = getBT(currentTurnCharId).getName() + " is too Intimidated to attack " + charName + "!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());

        sonicJammer.playSound("error", getBaseContext());
    }

    @Override
    public void untargetableToast(String targetName) {

        String msg = targetName + " is defended!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());

        sonicJammer.playSound("error", getBaseContext());
    }

    @Override
    public void engageToast(String engageeName) {

        String msg = getBT(currentTurnCharId).getName() + " is currently Engaged with " + engageeName + "!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());

        sonicJammer.playSound("error", getBaseContext());
    }

    @Override
    public void grappledToast(boolean targetIsGrappled, String targetName) {
        String msg;
        if(targetIsGrappled){
            msg =  targetName + " is Grappled, and cannot be targeted!";
        } else{
            msg = targetName + " is not grappled! \n" + slagveld.currentAction.getName() + " can only target Grappled Chars!";
        }
        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());

        sonicJammer.playSound("error", getBaseContext());
    }

    @Override
    public void aegisToast() {
        String msg = "Defend is impossible because of AEGIS SHIELD!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());

        sonicJammer.playSound("error", getBaseContext());
    }

    public void defendToast(String defenderName, String defendeeName){

        String msg = defenderName + " took the hit for " + defendeeName + "!";

        runOnUiThread(() -> Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show());

        sonicJammer.playSound("row_shift", getBaseContext());
    }


    @Override
    public void conveyVisual(statusEffect effect, int targetId) {
        midlands.conveyVisual(effect, targetId);
    }


    // updates bars on target click from targetFrag (or when called through there)
    // characterLayout sends in -1 once its already focused yet still pressed again, hiding the layout
    @Override
    public boolean clicked(int id) {

        midlands.battleCharTap();

        if (id != -1) {
            //updates largeBars


            // only if current turn wielder and char pressed are NOT on the same team, the largeBars will change
            if (targetables.contains(id)) {

                if (((slagveld.getIsPlayer(id) == slagveld.getIsPlayer(currentTurnCharId)) && !offensiveActionPicked) ||
                        (slagveld.getIsPlayer(id) != slagveld.getIsPlayer(currentTurnCharId) && offensiveActionPicked)) {
                    updateLargeBars(id, !slagveld.getIsPlayer(currentTurnCharId));
                }
            }

        }


        // only while targetFrag is up - notify its interface
        // resReceived is set right before the frag is summoned, and updated right after

        if (!targetResultReceived) {
            // returns if attackable in statusEffect standards
            // also does the clickable stuff in targetfrag
            boolean attackable = false;

            if(targetView != null) {
                attackable = targetView.clicked(id);
            }

            if (id != -1 && attackable) {
                updateTargetFragClickedTarget(id, false);
            }
        }

        return true;
    }

    @Override
    public void finalTargetsAndProceed(ArrayList<Integer> finalTargets) {
//            pointerManager.removeAllPointers();
        targetResultReceived = true;
        targetFragUp = false;
        targetCodes = finalTargets;

        turnPlayerPostTargetFrag afterThread = new turnPlayerPostTargetFrag();
        Thread thr = new Thread(afterThread);
        thr.start();
    }


    @Override
    public void inflateStatCard(int serial) {

        statCardXX statCard = new statCardXX(fightForeground.this,
                getBT(serial).getChars(), getBT(serial));


        statCard.show();

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.96);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.96);

        statCard.getWindow().setLayout(width, height);
    }


//    public void inflateView(View view, int viewLeft, int viewTop) {
//        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.leftMargin = viewLeft;
//        params.topMargin = viewTop;
//        view.setLayoutParams(params);
//
//        root.addView(view);
//    }
//
//    public void removeView(View view) {
//        runOnUiThread(() -> {
//            viewAnimationHelper.hideViewWithAnimation(view, 300).start();
//            root.removeView(view);
//        });
//    }
//
//
//    public class pointerKore {
//
//        pointer[] pointers = new pointer[5];
//
//        public pointerKore(Context context) {
//            for (int i = 0; i < pointers.length; i++) {
//                pointers[i] = new pointer(context);
//                root.addView(pointers[i]);
//            }
//        }
//
//        public void removeAllPointers() {
//            for (pointer pointer : pointers) {
//                pointer.remove();
//            }
//        }
//
//        public void addPointerToTurnWielder() {
//            pointers[pointers.length - 1].moveTo(getBT(currentTurnCharId).getCharLayout());
//        }
//
//        public void addPointersToTargets(ArrayList<Integer> targetIds) {
//            for (int i = 0; i < pointers.length; i++) {
//                if (!(targetIds.get(i) == null)) {
//                    pointers[i].moveTo(getBT(targetIds.get(i)).getCharLayout());
//                } else {
//                    pointers[i].remove();
//                }
//            }
//        }
//
//        public class pointer extends GifImageView {
//
//            public pointer(Context c) {
//                super(c);
//
//                this.setImageResource(R.drawable.pointer);
//                this.setLayoutParams(new ViewGroup.LayoutParams(120, 120));
//                this.setVisibility(GONE);
//            }
//
//            public void moveTo(View view) {
//                runOnUiThread(() -> {
//                    setTop(view.getTop() - 65);
//                    setLeft(view.getLeft());
//                    setVisibility(View.VISIBLE);
//                });
//
//            }
//
//            public void remove() {
//                runOnUiThread(() -> setVisibility(GONE));
//
//            }
//        }
//
//    }


//              toast

//    private class turnToastGenerator implements Runnable {
//        @Override
//        public void run() {
//            while (!gameOver) {
//                while (!turnPicked) {
//                    synchronized (turnLock) {
//                        try {
//                            turnLock.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                turnPicked = false;
//
//                if (getCurrentBattleToken().hasTurn()) {
//                }
//
//            }
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        sonicJammer.resumeMusic(getBaseContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!gameOver) {
            sonicJammer.pauseMusic();
        } else {
            soundmanDan2.fadeOutMusic fade = new soundmanDan2.fadeOutMusic(false, false);
            Thread thread = new Thread(fade);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onBackPressed() {
        back.performClick();
    }


    private class themeGenerator implements Runnable{

        boolean isCampaignMode;
        int roundNum;

        HashMap<String, ArrayList<Integer>> themeRanges = new HashMap<>();
        ArrayList<String> availableThemes = new ArrayList<>();

        public themeGenerator(boolean isCampaignMode, int roundNumber){

            this.isCampaignMode = isCampaignMode;

            roundNum = roundNumber;
        }

        private void fillHashMap(){
            themeRanges.put("grassland", new ArrayList<>(Arrays.asList(1, 20)));
            themeRanges.put("fort", new ArrayList<>(Arrays.asList(1, 20)));
            themeRanges.put("mystic_arena", new ArrayList<>(Arrays.asList(15, 40)));
            themeRanges.put("frosty", new ArrayList<>(Arrays.asList(10, 25)));
            themeRanges.put("town", new ArrayList<>(Arrays.asList(1, 15)));
            themeRanges.put("wastes", new ArrayList<>(Arrays.asList(6, 22)));
            themeRanges.put("colliseum", new ArrayList<>(Arrays.asList(3, 15)));
            themeRanges.put("canyon", new ArrayList<>(Arrays.asList(10, 28)));
            themeRanges.put("spoopy", new ArrayList<>(Arrays.asList(15, 28)));
            themeRanges.put("space", new ArrayList<>(Arrays.asList(25, 40)));
            themeRanges.put("dark", new ArrayList<>(Arrays.asList(20, 40)));
            themeRanges.put("team_flare", new ArrayList<>(Arrays.asList(23, 40)));
            themeRanges.put("snowy", new ArrayList<>(Arrays.asList(17, 30)));
            themeRanges.put("rave", new ArrayList<>(Arrays.asList(10, 35)));
            themeRanges.put("manor_of_doom", new ArrayList<>(Arrays.asList(30, 40)));
            themeRanges.put("hands", new ArrayList<>(Arrays.asList(10, 25)));
            themeRanges.put("dragonlands", new ArrayList<>(Arrays.asList(25, 40)));
            themeRanges.put("castle", new ArrayList<>(Arrays.asList(8, 22)));
            themeRanges.put("hills", new ArrayList<>(Arrays.asList(1, 15)));
            themeRanges.put("profound", new ArrayList<>(Arrays.asList(10, 25)));
            themeRanges.put("river", new ArrayList<>(Arrays.asList(1, 15)));
            themeRanges.put("bright", new ArrayList<>(Arrays.asList(1, 15)));
            themeRanges.put("dusk", new ArrayList<>(Arrays.asList(5, 22)));
            themeRanges.put("desert", new ArrayList<>(Arrays.asList(1, 18)));
            themeRanges.put("ruin", new ArrayList<>(Arrays.asList(7, 25)));
            themeRanges.put("bazaar", new ArrayList<>(Arrays.asList(1, 10)));
            themeRanges.put("sf2", new ArrayList<>(Arrays.asList(10, 20)));
            themeRanges.put("savannah", new ArrayList<>(Arrays.asList(2, 16)));
            themeRanges.put("guild_town", new ArrayList<>(Arrays.asList(5, 20)));
            themeRanges.put("lunar_plains", new ArrayList<>(Arrays.asList(16, 35)));
        }

        public String getTheme(){
            String theme = availableThemes.get((int) (Math.random() * availableThemes.size()));

            switch (theme) {
                case "grassland":
                    root.setBackgroundResource(R.drawable.grasseses);
                    break;
                case "savannah":
                    root.setBackgroundResource(R.drawable.savannah);
                    break;
                case "sf2":
                    root.setBackgroundResource(R.drawable.sf2);
                    break;
                case "bazaar":
                    root.setBackgroundResource(R.drawable.bazaar);
                    break;
                case "ruin":
                    root.setBackgroundResource(R.drawable.castle_ruin);
                    break;
                case "desert":
                    root.setBackgroundResource(R.drawable.desert);
                    break;
                case "dusk":
                    root.setBackgroundResource(R.drawable.dusk_grasslands);
                    break;
                case "bright":
                    root.setBackgroundResource(R.drawable.bright_grasslands);
                    break;
                case "river":
                    root.setBackgroundResource(R.drawable.griver);
                    break;
                case "profound":
                    root.setBackgroundResource(R.drawable.waterfall);
                    break;
                case "hills":
                    root.setBackgroundResource(R.drawable.hills);
                    break;
                case "castle":
                    root.setBackgroundResource(R.drawable.castle_town);
                    break;
                case "dragonlands":
                    root.setBackgroundResource(R.drawable.dagronlands);
                    break;
                case "hands":
                    root.setBackgroundResource(R.drawable.hands);
                    break;
                case "manor_of_doom":
                    root.setBackgroundResource(R.drawable.evil_manor);
                    break;
                case "rave":
                    root.setBackgroundResource(R.drawable.rave_forest);
                    break;
                case "snowy":
                    root.setBackgroundResource(R.drawable.snow_dusk);
                    break;
                case "team_flare":
                    root.setBackgroundResource(R.drawable.dark_lab);
                    break;
                case "dark":
                    root.setBackgroundResource(R.drawable.dark_cave);
                    break;
                case "space":
                    root.setBackgroundResource(R.drawable.spacey_lepique);
                    break;
                case "spoopy":
                    root.setBackgroundResource(R.drawable.spoopy_trees);
                    break;
                case "canyon":
                    root.setBackgroundResource(R.drawable.canyon_of_redd);
                    break;
                case "colliseum":
                    root.setBackgroundResource(R.drawable.colliseum);
                    break;
                case "wastes":
                    root.setBackgroundResource(R.drawable.wastes);
                    break;
                case "fort":
                    root.setBackgroundResource(R.drawable.fort);
                    break;
                case "frosty":
                    root.setBackgroundResource(R.drawable.frosty);
                    break;
                case "mystic_arena":
                    root.setBackgroundResource(R.drawable.mystic_arena);
                    break;
                case "town":
                    root.setBackgroundResource(R.drawable.town);
                    break;
                case "guild_town":
                    root.setBackgroundResource(R.drawable.guild_town);
                    break;
                case "lunar_plains":
                    root.setBackgroundResource(R.drawable.lunar_plains);
                    break;
            }

            return theme;
        }

        @Override
        public void run() {

            fillHashMap();

            if(isCampaignMode){

                //adds any themes that are in range.
                Set<String> keySet = themeRanges.keySet();
                for (int i = 0; i < themeRanges.size(); i++) {
                    String curTheme = (String) keySet.toArray()[i];
                    if(themeInRangeOfRoundNum(curTheme)){
                        availableThemes.add(curTheme);
                    }
                }

            } else{
                // doesnt matter get random
                availableThemes.addAll(themeRanges.keySet());
            }

        }

        private boolean themeInRangeOfRoundNum(String themeName){
            ArrayList<Integer> range = themeRanges.get(themeName);
            return ((range.get(0) <= roundNum) && (roundNum <= range.get(1)));
        }
    }

}


