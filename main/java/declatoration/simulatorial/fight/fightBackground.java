package declatoration.simulatorial.fight;

import static declatoration.simulatorial.enums.stat.MAG;
import static declatoration.simulatorial.enums.stat.NULL;
import static declatoration.simulatorial.fight.fightForeground.clashReactionMode;
import static declatoration.simulatorial.fight.fightForeground.isCrit;
import static declatoration.simulatorial.fight.fightForeground.sonicJammer;
import static declatoration.simulatorial.fight.fightUpdater.textType.BALM;
import static declatoration.simulatorial.fight.fightUpdater.textType.GRAPPLE_RELEASE;
import static declatoration.simulatorial.fight.fightUpdater.textType.OUT_OF_AMMO;
import static declatoration.simulatorial.fight.fightUpdater.textType.RELOAD;
import static declatoration.simulatorial.fight.fightUpdater.textType.getTextType;
import static declatoration.simulatorial.fight.statusEffectList.negativeStatusEffects;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import declatoration.simulatorial.R;
import declatoration.simulatorial.affinityTable;
import declatoration.simulatorial.bonusEffects;
import declatoration.simulatorial.catalog;
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
import declatoration.simulatorial.getImageRes;
import declatoration.simulatorial.prep.enemySkirmishGenerator;
import declatoration.simulatorial.weaponTable;

public class fightBackground {


    static int currentTurnCharId;
    private int currentTargetBTId;

    fightForeground admin;
    fightMidgrounds midan;
    actionHandler actionhandler;
    skillmaster skillmaster;

    static battleTokenArray combatants;
    enemySkirmishGenerator skirmishGen;

    weaponTable weaponEffManager;
    static assistConductor assistManager;
    static grappleMediator grappleManager;

    int lastPlayerTurn, lastEnemyTurn;
    ArrayList<Integer> playerTurnOrder, enemyTurnOrder;
    ArrayList<Integer> turnHistory = new ArrayList<>(10);

    charAction currentAction;

    static affinityClashExtraEffects clashReactionManager;

    static StringBuilder actionTags = new StringBuilder();

    boolean firstDraw = true;
    boolean moveNextIsHappening;
    int moveNextTurnHolder = -1;

    //smaller stuff
    private int moveNextId = -1;

    ArrayList<duo> grapplerHpArrayList = new ArrayList<>();


    public fightBackground(Context context, fightForeground ff, fightMidgrounds midgrounds,
                           ArrayList<String> charNames, ArrayList<Integer> levelsArray,
                           ArrayList<String> pickedActions, int enemyAmount,
                           ArrayList<String> enemyNames, ArrayList<String> enemyPickedActions) {

        int playerSize = charNames.size();

        catalog allCharsMini = new catalog(context, charNames, pickedActions, levelsArray.get(0),
                enemyAmount, enemyNames, enemyPickedActions, levelsArray.get(1));

        admin = ff;

        combatants = new battleTokenArray(context, ff, allCharsMini, playerSize, admin);
        midan = midgrounds;


        for (int i = 0; i < 10; i++) {
            this.turnHistory.add(i, null);
        }

        skillmaster = new skillmaster();
        actionhandler = new actionHandler(this);
        clashReactionManager = new affinityClashExtraEffects();

        assistManager = new assistConductor();
        grappleManager = new grappleMediator();
        weaponEffManager = new weaponTable();

        playerTurnOrder = new ArrayList<>(playerSize);
        enemyTurnOrder = new ArrayList<>(enemyAmount);
        initializeTurnOrder();

    }

    public fightBackground(Context context, fightForeground ff, fightMidgrounds midgrounds, ArrayList<chars> megadeathCharsArray,
                           ArrayList<chars> enemyCharsArray) {

        // war room initialization

        int playerSize = megadeathCharsArray.size();
        int enemyAmount = enemyCharsArray.size();

        catalog allCharsMini = new catalog(context, megadeathCharsArray, enemyCharsArray);

        admin = ff;

        combatants = new battleTokenArray(context, ff, allCharsMini, playerSize, admin);
        midan = midgrounds;


        for (int i = 0; i < 10; i++) {
            this.turnHistory.add(i, null);
        }

        skillmaster = new skillmaster();
        actionhandler = new actionHandler(this);
        clashReactionManager = new affinityClashExtraEffects();

        assistManager = new assistConductor();
        grappleManager = new grappleMediator();
        weaponEffManager = new weaponTable();

        playerTurnOrder = new ArrayList<>(playerSize);
        enemyTurnOrder = new ArrayList<>(enemyAmount);
        initializeTurnOrder();

    }


//          turns

    //return value for printing new turn post
    public boolean pickTurn() {
        setGrapplerHpArrayList();

        if (moveNextId == -1) {

            //resetting stuff after move next instance
            if(moveNextIsHappening) {
                currentTurnCharId = moveNextTurnHolder;

                moveNextIsHappening = false;
                moveNextTurnHolder = -1;
            }

            if (getCurrentCharBT().turnComplete() || !getCurrentCharBT().getIsAlive()) {
                // new chars turn

                //is there anyone pent up?

                boolean playerJustWent = getIsPlayer(currentTurnCharId);

                //if team should go again, flip the script
                if (getBackToBackTurns(playerJustWent)) {
                        playerJustWent = !playerJustWent;
                    }


                if (playerJustWent) {
                        //shifts to enemy turn

                        if (enemyTurnOrder.size() > 1 && lastEnemyTurn != enemyTurnOrder.get(enemyTurnOrder.size() - 1)) {
                            currentTurnCharId = enemyTurnOrder.get(enemyTurnOrder.indexOf(lastEnemyTurn) + 1);
                        } else {
                            currentTurnCharId = enemyTurnOrder.get(0);
                        }

                        lastEnemyTurn = currentTurnCharId;
                    } else {
                        // shifts to player turn

                        if (playerTurnOrder.size() > 1 && lastPlayerTurn != playerTurnOrder.get(playerTurnOrder.size() - 1)) {
                            currentTurnCharId = playerTurnOrder.get(playerTurnOrder.indexOf(lastPlayerTurn) + 1);
                        } else {
                            currentTurnCharId = playerTurnOrder.get(0);
                        }

                        lastPlayerTurn = currentTurnCharId;
                    }
                getBT(currentTurnCharId).newTurn();




                return true;
            } else {
                getBT(currentTurnCharId).newAction();

                admin.setIsFinalActionTime(getCurrentCharBT().isFinaleActionTime());

                if(!firstDraw) {
                    return false;
                } else {
                    firstDraw = false;
                    return true;
                }
            }
        } else {
            moveNextIsHappening = true;
            moveNextTurnHolder = currentTurnCharId;
            currentTurnCharId = moveNextId;
            moveNextId = -1;

            return true;
        }
    }

    private void initializeTurnOrder() {
        ArrayList<battleToken> BTList = combatants.getActiveBattleTokens(true);
        int size = BTList.size();

        for (int i = 0; i < size; i++) {
            int rand = (int) (Math.random() * BTList.size());
            battleToken bt = BTList.get(rand);
            playerTurnOrder.add(bt.getBattleSerialNum());
            BTList.remove(bt);
        }
        BTList = combatants.getActiveBattleTokens(false);
        size = BTList.size();

        for (int i = 0; i < size; i++) {
            int rand = (int) (Math.random() * BTList.size()) ;
            battleToken bt = BTList.get(rand);
            enemyTurnOrder.add(bt.getBattleSerialNum());
            BTList.remove(bt);
        }

        lastPlayerTurn = playerTurnOrder.get(0);
        lastEnemyTurn = enemyTurnOrder.get(enemyTurnOrder.size() - 1);

        currentTurnCharId = playerTurnOrder.get(0);
        getCurrentCharBT().newTurn();
    }
    public void addToTurnOrder(boolean isPlayer, int charId){

        ArrayList<Integer> turnOrder;
        if(isPlayer){
            turnOrder = playerTurnOrder;
        } else{
            turnOrder = enemyTurnOrder;
        }

        turnOrder.add(charId);
    }
    private boolean getBackToBackTurns(boolean isPlayerTurn) {
        //returns true if selected team should go again
        int discrepencyPerc = (int) (100 * combatants.getTurnSwitchSpdRatio(isPlayerTurn));
        return percentagize(100 - discrepencyPerc);
    }

    //check if these things i stole from afterParty work here
    public void finalizeTurn(int id) {

        enactActionBonusEffects(actionhandler.getLastDmgTaken(), actionHandler.returningTasks);

        checkGrappleArrayList();

        if (getCurrentCharBT().hasStatusEffect()) {
            ArrayList<statusEffect> damagingStatusEffects = getCurrentCharBT().getDamagingStatusEffects();

            int dmgTaken = 0;

            for (int i = 0; i < damagingStatusEffects.size(); i++) {
                if (damagingStatusEffects.get(i) != statusEffect.BLEED && damagingStatusEffects.get(i) != statusEffect.BANE
                    && damagingStatusEffects.get(i) != statusEffect.WHIRLPOOL) {
                    dmgTaken += getCurrentCharBT().getHPMax() / 16;
                } else {
                    if (damagingStatusEffects.get(i) == statusEffect.BLEED) {
                        dmgTaken += getCurrentCharBT().getHPMax() / 12;
                    } else {
                        if(damagingStatusEffects.get(i) == statusEffect.BANE) {
                            dmgTaken += getCurrentCharBT().getHPMax() / 20;
                        } else {
                            dmgTaken += getCurrentCharBT().getHPMax() / 6;
                        }
                    }
                }
            }
            getCurrentCharBT().setHP(getCurrentCharBT().getHP() - dmgTaken);


            admin.setStatusIcon(currentTargetBTId, getCurrentTargetBTId().getStatusEffectsWithPriority());
        }

        int actionID = admin.getAttackCode();
        if (actionID > 0 && actionID < 6 || (actionID < -6 && actionID != -10)) {
            getCurrentCharBT().setActionCooldown(actionID);
        }

        admin.setIsFinalActionTime(false);

        //turn history
        turnHistory.add(id);
        if (this.turnHistory.size() > 10) {
            this.turnHistory.remove(0);
        }

    }


//          dmg calcs

    public void doActuateDamageAndCheckKos(ArrayList<Integer> targetCodes){
        actuateDamageAndCheckKOs thi = new actuateDamageAndCheckKOs(targetCodes);
        Thread thread = new Thread(thi);
        thread.start();
    }

    public class actuateDamageAndCheckKOs implements Runnable{

        ArrayList<Integer> targetCodes;

        public actuateDamageAndCheckKOs(ArrayList<Integer> targetCodes){
              this.targetCodes = targetCodes;
        }

        @Override
        public synchronized void run() {

            boolean koed = false;
            boolean curKoed = false;

            int currentTargetId = -1;
            ArrayList<Integer> targetables = combatants.findTargetableSerialNums(currentTurnCharId,
                    currentAction.getAffectedTarget());

            if (currentAction.getDamageType() != damageType.STATUS) {

                if(getCurrentCharBT().hasStatus(statusEffect.ASSIST)) {
                    assistManager.removeDuo(currentTurnCharId);
                }

                moveType atkCharSFX = getInitCharSFX();

                for (int j = 0; j < currentAction.getNumOfAttacks(); j++) {

                    boolean targetsRandom = currentAction.getAffectedTarget().toString().contains("RANDOM");

                    if (targetsRandom) {
                        targetCodes.clear();
                        targetCodes.add(targetables.get((int) (Math.random() * targetables.size())));
                    } else{
                        if(currentAction.getAffectedTarget() == target.SINGLE_ENEMY &&
                                !getBT(targetCodes.get(0)).getIsAlive()){
                            break;
                        }
                    }

                    if(getCurrentCharBT().usesBullets() &&
                            (currentAction.getWeaponType() == weaponType.RIFLE || currentAction.getWeaponType() == weaponType.CANNON)){
                        if(currentAction.getAffectedTarget() == target.ALL_ENEMIES ||
                                currentAction.getAffectedTarget() == target.ENEMY_ROW){
                            for (int i = 0; i < targetCodes.size(); i++) {
                                getCurrentCharBT().useBullet();
                            }
                        } else{
                            getCurrentCharBT().useBullet();
                        }
                    }


                    for (int i = 0; i < targetCodes.size(); i++) {

                        currentTargetId = targetCodes.get(i);
                        setCurrentTargetBT(currentTargetId);

                        int defenderID = -1;
                        if(getCurrentTargetBTId().hasStatus(statusEffect.DEFENDED)) {
                            int potentialDef = (int) getCurrentTargetBTId().getBonusEffectExtra(statusEffect.DEFENDED);
                            if (!targetCodes.contains(potentialDef)) {
                                // can only defend, if char himself isnt being attacked
                                defenderID = potentialDef;
                                currentTargetId = potentialDef;
                                admin.defendToast(getBT(defenderID).getName(), getCurrentTargetBTId().getName());

                                getCurrentTargetBTId().removeStatusIfExists(statusEffect.DEFENDED);
                                setCurrentTargetBT(potentialDef);

                                admin.playCharSFX(currentTargetId, moveType.HEAL);
                            }
                        }

                        boolean hasAegisShield = getCurrentTargetBTId().hasAegisShield();

                        effectiveness affEff = getAttackValues(currentTargetId).affinityEff;
                        admin.setAffEff(affEff);
                        if(affEff == effectiveness.EFFECTIVE){
                            atkCharSFX = moveType.POWER_ATTACK;
                        }

                        int dmgTaken = actionhandler.getFinalActionDmg(currentTargetId, 1, defenderID);

                        if(!hasAegisShield) {

                            curKoed = actuateDamage(dmgTaken);
                            koed = koed || curKoed;

                            Thread muzix = midan.enactSfxify(curKoed, dmgTaken, true, null, currentTargetId);

                            if(dmgTaken > 0) {
                                postAttackClashReactionTrigger();
                            }


                            try {
                                muzix.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(j == currentAction.getNumOfAttacks()-1) {
                                admin.playCharSFX(currentTargetId, getHitSFX((double) dmgTaken / getBT(currentTargetId).getHPMax()));
                            } else if(dmgTaken > 0) {
                                admin.playCharSFX(currentTargetId, moveType.LIGHT_HIT);
                            } else if(dmgTaken < 0){
                                admin.playCharSFX(currentTargetId, moveType.DODGE);
                            }

                            fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(),
                                    currentAction.getName(), fightUpdater.textType.DMG, dmgTaken, null);
                        }

                        int statusEffectCount = 0;
                        if (dmgTaken >= 0){


                            if(getCurrentTargetBTId().hasStatus(statusEffect.THORNS) &&
                                    (!currentAction.getRange().contains(2) && !currentAction.getRange().contains(2))){
                                // counter

                                int dmg = (getCurrentCharBT().getHPMax() / 12);
//                            actuateDamage(currentTurnCharId, dmg);

                                enactAdditionalDMGVisual(currentTurnCharId, dmg);
                                sonicJammer.playSound("thorns", admin.getBaseContext());

                                fightMidgrounds.post(getCurrentTargetBTId().getName(), getCurrentCharBT().getName() ,
                                        currentAction.getName(), fightUpdater.textType.THORNS_DMG, dmg, null);

                                currentTargetId = targetCodes.get(i);
                                setCurrentTargetBT(currentTargetId);

                                admin.playCharSFX(currentTurnCharId, moveType.LIGHT_HIT);

                            }

//                            postAttackClashReactionTrigger();

                            if(getCurrentTargetBTId().hasStatus(statusEffect.COUNTER) &&
                                    (!currentAction.getRange().contains(2) && !currentAction.getRange().contains(2))){
                                // counter

                                sonicJammer.playSound("counter", admin.getBaseContext());

                                actuateDamage(currentTurnCharId, dmgTaken);
                                enactAdditionalDMGVisual(currentTurnCharId, dmgTaken);

                                fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(),
                                        currentAction.getName(), fightUpdater.textType.COUNTER_DMG, dmgTaken, null);


                                currentTargetId = targetCodes.get(i);
                                setCurrentTargetBT(currentTargetId);

                                admin.playCharSFX(currentTurnCharId, getHitSFX(dmgTaken/getCurrentCharBT().getHPMax()));
                            }


                            if(currentAction.hasBonusEffects()) {
                                // if hasAegisShield, returns num of negative effects.
                                // otherwise, actuates effects & returns -1
                                statusEffectCount = afterParty(currentAction, currentTargetId, hasAegisShield);
                            }

                        }

                        if(hasAegisShield){

                            curKoed = false;

                            int shieldDamageTaken = actuateAegisShieldDamage(dmgTaken, statusEffectCount);
                            boolean aegisBreak = getCurrentTargetBTId().aegisShield.hpVal <= 0;

                            Thread muzix = midan.enactSfxify(aegisBreak, shieldDamageTaken, true, null, currentTargetId);


                            try {
                                muzix.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(),
                                    currentAction.getName(), fightUpdater.textType.AEGIS_SHIELD_TANK, dmgTaken, null);
                            if(aegisBreak){
                                //todo -minor dmg upon shield break
                                getCurrentTargetBTId().cancelAegisShield();
                                fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(),
                                        currentAction.getName(), fightUpdater.textType.AEGIS_SHIELD_BREAK, dmgTaken, null);

                                admin.playCharSFX(currentTargetId, moveType.DEBUFF);
                            } else{
                                admin.playCharSFX(currentTargetId, moveType.BUFF);
                            }
                        }

                    }

                    if(isCrit){
                        admin.playCharSFX(currentTurnCharId, moveType.CRIT);
                    }

                    if(j < currentAction.getNumOfAttacks() - 1 && j%2 == 0){
                        admin.playCharSFX(currentTurnCharId, moveType.NORMAL_ATTACK);
                    } else if (j == currentAction.getNumOfAttacks()-1){
                        admin.playCharSFX(currentTurnCharId, atkCharSFX);
                    }

                    if(curKoed){
                        targetCodes.remove((Integer) currentTargetId);

                        if((targetCodes.isEmpty() && !targetsRandom) || (targetCodes.isEmpty() && targetsRandom)){
                            break;
                        }
                    }

                    if(getCurrentCharBT().usesBullets() && getCurrentCharBT().getBulletCount() == 0
                            && currentAction.getWeaponType() == weaponType.RIFLE){
                        midan.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(), currentAction.getName(),
                                OUT_OF_AMMO, null, null);
                        break;
                    }

                }
            }

            admin.startTurnEnd();
        }
    }
    public class actuateDamageAndCheckKOsChainAttack implements Runnable{

        int targetId, dmgTaken;
        String currentActionName;
        boolean enactVisuals;
        affinity aff;

        public actuateDamageAndCheckKOsChainAttack(int targetId, String actionName, int dmgTaken,
                                                   boolean enactGenericVisuals, affinity caAff){
            this.targetId = targetId;
            currentActionName = actionName;
            this.dmgTaken = dmgTaken;
            enactVisuals = enactGenericVisuals;
            aff = caAff;
        }

        @Override
        public void run() {

            //for Chain-Attacks, has a x0.4 Modifier

            setCurrentTargetBT(targetId);

            admin.setAffEff(effectiveness.NEUTRAL);
            fightForeground.isBlock = false;
            fightForeground.isCrit = false;

            boolean hasAegisShield = getCurrentTargetBTId().hasAegisShield();

            if(!hasAegisShield) {
                boolean koed = actuateDamage(dmgTaken);

                String affin = null;
                if(currentActionName == null && aff != null){
                    affin = aff.toString();
                }

                midan.enactSfxify(koed, dmgTaken, true, affin, targetId);

                if (enactVisuals) {
                    midan.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(),
                            currentActionName, fightUpdater.textType.DMG, dmgTaken, null);
                }
            } else{

                int shieldDamageTaken = actuateAegisShieldDamage(dmgTaken, 0);
                boolean aegisBreak = getCurrentTargetBTId().aegisShield.hpVal <= 0;

                String affin = null;
                if(currentActionName == null){
                    affin = aff.toString();
                }
                midan.enactSfxify(aegisBreak, shieldDamageTaken, true, affin, targetId);
//
//
//                try {
//                    muzix.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(),
                        currentAction.getName(), fightUpdater.textType.AEGIS_SHIELD_TANK, dmgTaken, null);
                if(aegisBreak){
                    getCurrentTargetBTId().cancelAegisShield();

                    int shieldBreakDMG = getBT(targetId).getHPMax() / 8;
                    actuateDamage(targetId, shieldBreakDMG);

                    fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(),
                            currentAction.getName(), fightUpdater.textType.AEGIS_SHIELD_BREAK, shieldBreakDMG, null);
                }
            }

        }

    }

    public int doActuateChainAttackDamage(int statusTarget, charAction ca){

        int dmgTaken = actionhandler.getFinalActionDmg(statusTarget, ca);

        actuateDamageAndCheckKOsChainAttack atk = new actuateDamageAndCheckKOsChainAttack
                (statusTarget, ca.getName(), dmgTaken, ca.getName() != null, ca.getAffinity());
        Thread thr = new Thread(atk);
        thr.start();

        try{
            synchronized (this){
                thr.join();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return dmgTaken;
    }
    public void enactAdditionalDMGVisual(int targetId, int dmgTaken){


        actuateDamageAndCheckKOsChainAttack atk = new actuateDamageAndCheckKOsChainAttack
                (targetId, null, dmgTaken, false, null);
        Thread thr = new Thread(atk);
        thr.start();
    }


    // if char is koed, returns true
    // realizes damage for charAttackProcedure
    private boolean actuateDamage(int dmgTaken) {

        boolean isKoed = false;

        if(dmgTaken == 0){
            // make anything that hits detract 1 at LEAST
            dmgTaken = 1;
        } else if(dmgTaken < 0){
            // make miss detract 0 dmg
            dmgTaken = 0;
        }
         int newHp = getCurrentTargetBTId().getHP() - dmgTaken;

        if (newHp <= 0) {
            newHp = 0;


            isKoed = true;
            getCurrentTargetBTId().setIsAlive(false);
            actionTags.append("defKoed");
        } else {
            if (dmgTaken > 0) {
                // remove sleep if damage taken
                if (getCurrentTargetBTId().removeStatusIfExists(statusEffect.SLEEP)) {
                    String msg = getCurrentTargetBTId().getName() + " has been awakened!";
                    Runnable runnable = () -> Toast.makeText(admin.getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                    admin.runOnUiThread(runnable);
                }
            }
        }

        getCurrentTargetBTId().setHP(newHp);

        return isKoed;
    }
    public boolean actuateDamage(int targetId, int dmgTaken) {

        boolean isKoed = false;

        if(dmgTaken < 0){
            dmgTaken = 0;
        }

        int newHp = getBT(targetId).getHP() - dmgTaken;

        if (newHp <= 0) {
            newHp = 0;


            isKoed = true;
            getBT(targetId).setIsAlive(false);
            actionTags.append("defKoed");
        }

        getBT(targetId).setHP(newHp);

        return isKoed;
    }
    private int actuateAegisShieldDamage(int baseDmgTaken, int statusCount){
        return getCurrentTargetBTId().damageAegisShield(baseDmgTaken, statusCount);
    }


    //for strictly status moves
    public void afterParty(charAction action, ArrayList<Integer> charIds) {
        // statuses
        ArrayList<Integer> hitTargets = new ArrayList<>();

        for (int i = 0; i < charIds.size(); i++) {
            if (actionhandler.getStatusActionHit(charIds.get(i))) {
                //adds them as targets only if theyve been hit
                hitTargets.add(charIds.get(i));
            } else {
                sonicJammer.playSound( "miss", admin.getBaseContext());
            }
        }
        actionhandler.enactActionStatuses(currentTurnCharId, action, hitTargets);

    }

    // one at a time, after using a damaging attack (actuateDmgAndCheckKo())
    // returns amount of negative status effects
    public int afterParty(charAction action, int targetId, boolean onlyGetCount) {
        // statuses

        if(onlyGetCount) {
            int negativeStatusEffectCount = 0;

            ArrayList<bonusEffects> actionBonusEffects = action.getBonusEffects();
            for (int i = 0; i < actionBonusEffects.size(); i++) {
                if (negativeStatusEffects.contains(actionBonusEffects.get(i).getStatus())) {
                    negativeStatusEffectCount++;
                }
            }

            return negativeStatusEffectCount;

        } else{

            ArrayList<bonusEffects> actionBonusEffects = action.getBonusEffects();
            for (int i = 0; i < actionBonusEffects.size(); i++) {
                actionhandler.statusProcedure(actionBonusEffects.get(i), targetId);
            }

            return -1;
        }
    }


    public void postAttackClashReactionTrigger(){
        if(clashReactionTriggered()) {
            clashReactionManager.postAttackClashUpdate(currentAction.getAffinity(),
                    currentTurnCharId, currentTargetBTId);
        }
    }

    private boolean percentagize(int percentage) {
        return Math.random() * 100 <= percentage;
    }

    public int rangeify(ArrayList<Integer> attackRange, int targetId) {
        if (attackRange.size() < 3 && attackRange.size() != 0) {

            int tempMin = combatants.getRangeDiff(currentTurnCharId, targetId);
            int tempMax = tempMin;

            tempMin -= attackRange.get(0);
            tempMax -= attackRange.get(attackRange.size() - 1);

            int min = Math.min((Math.abs(tempMin)), (Math.abs(tempMax)));

            if (combatants.rowCollapsed(true) && min > 0) {
                min -= 1;
            }
            if (combatants.rowCollapsed(false) && min > 0) {
                min -= 1;
            }

            return min;

        } else return 0;
    }
    public int rangeify(ArrayList<Integer> attackRange) {

        if (attackRange.size() != 0) {
            int tempMin = combatants.getRangeDiff(currentTurnCharId, currentTargetBTId);
            int tempMax = tempMin;

            tempMax -= attackRange.get(0);
            tempMin -= attackRange.get(attackRange.size() - 1);

            int min = Math.min(Math.min((Math.abs(tempMin)), (Math.abs(tempMax))), Math.abs(tempMax + tempMin));

            if (combatants.rowCollapsed(true) && min > 0) {
                min -= 1;
            }
            if (combatants.rowCollapsed(false) && min > 0) {
                min -= 1;
            }

            return min;
        } else {
            return 0;
        }
    }


//              actions

    public void hpPotion() {
        replenishHP(currentTurnCharId, 60);
    }

    public void spPotion() {
        replenishSP(currentTurnCharId, 50);
    }

    public void focusAction() {
        getBT(currentTurnCharId).setIsFocusActioning(true);
    }

    public void guardAction() {
        getBT(currentTurnCharId).setIsGuardActioning(true);
    }


    public void heal(ArrayList<Integer> targetsSelectedId, target affectedTargets, double modifier) {

        // flat heal heals 0.7x - 0.8x mag stat
        int healValue = (int) (getStat(currentTurnCharId, MAG) * (0.7 + Math.random() * 0.1));
        healValue *= modifier;

        switch (affectedTargets) {
            case SELF:
                replenishHP(currentTurnCharId, healValue);

                sonicJammer.playSound( "heal", admin.getBaseContext());
                break;

            case SINGLE_ALLY_NO_USER:
            case SINGLE_ALLY:
                replenishHP(targetsSelectedId.get(0), healValue);

                sonicJammer.playSound( "heal", admin.getBaseContext());

                break;

            case ALL_ALLIES:
                ArrayList<battleToken> targets = combatants.getActiveBattleTokens(getIsPlayer(targetsSelectedId.get(0)));

                for (int i = 0; i < targets.size(); i++) {
                    replenishHP(targets.get(i).getBattleSerialNum(), healValue);
                    sonicJammer.playSound( "heal", admin.getBaseContext());

                    // so value changes each time
                    healValue = (int) (getStat(currentTurnCharId, MAG) * (0.7 + Math.random() * 0.1));
                    healValue *= modifier;
                }
        }
    }
    private void replenishHP(int id, int value) {
        battleToken healee = getBT(id);
        int newHP = healee.getHP() + value;

        //this is a lie, normal heals
        actionTags.append(id).append(statusEffect.HEAL);

        if (newHP > healee.getHPMax()) {
            newHP = healee.getHPMax();
        }

        healee.setHP(newHP);

        admin.healToast(healee.getName(), value, true);
        admin.playCharSFX(id, moveType.HEAL);
    }
    private void replenishSP(int id, int value) {
        battleToken healee = getBT(id);
        int newSP = healee.getSP() + value;
        actionTags.append(id).append(statusEffect.HEAL);

        if (newSP > healee.getSPMax()) {
            newSP = healee.getSPMax();
        }

        healee.setSP(newSP);
        admin.healToast(healee.getName(), value, false);
    }


    public void instakill(int targetId, charAction chosenAtk) {

        int dmg = getBT(targetId).getHPMax();
        actuateDamage(targetId, dmg);


        admin.conveyVisual(statusEffect.INSTAKILL, targetId);

        //thorns and counter and stuff

        //bonusEffects, ONLY if hit
        if (chosenAtk.getBonusEffects().size() > 1) {
            charAction tempAction = chosenAtk;
            ArrayList<bonusEffects> eff = new ArrayList<>(chosenAtk.getBonusEffects());
            eff.remove(chosenAtk.getBonusEffect(statusEffect.INSTAKILL));
            tempAction.setActionBonusEffects(eff);
            afterParty(tempAction, targetId, false);
        }

        try {
            synchronized (this) {
                this.wait(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminateTurn(){
        getBT(currentTurnCharId).terminateTurn();
    }


//              getters setters

    public void setCurrentTargetBT(int id) {
        this.currentTargetBTId = id;
    }

    private battleToken getBT(int id) {
        return combatants.getBattleToken(id);
    }
    public battleToken getCurrentCharBT() {
        return getBT(currentTurnCharId);
    }
    public battleToken getCurrentTargetBTId() {
        return this.getBT(currentTargetBTId);
    }

    public float getStat(int id, stat stat) {
        if (stat != null && stat != NULL) {
            return getBT(id).getStatFloat(stat);
        } else {
            return 0;
        }
    }

    public boolean getIsPlayer(int id) {
        return (getBT(id).getIsPlayer());
    }

    public boolean getIsFrontLine(int id) {
        return getBT(id).getIsFrontLine();
    }

    public actionBoostParams getAssistBonuses() {
        actionBoostParams bonuses = new actionBoostParams();

        // only want to calc bonus dmg during actual attack, else it should show up separately on board
        if (getBT(currentTurnCharId).hasStatus(statusEffect.ASSIST)) {
            bonuses = assistManager.getAssistStatBonuses(currentTurnCharId, currentAction.getIsMag());
            actionTags.append("atkrAssisted");
        }

        return bonuses;
    }

    public void updateCurrentAction(charAction selectedAction) {
        currentAction = selectedAction;

        offActionSelected(currentTurnCharId, getTargetables(), currentAction,
                currentAction.getDamageType().equals(damageType.STATUS));

    }

    public int[] getEffectivenessForecastImgRes() {
        int[] resIds = new int[6];

        affinity attackAff;
        if (currentAction.getAffinity().equals(affinity.USER_TYPE)) {
            attackAff = affinity.NULL;
        } else {
            attackAff = currentAction.getAffinity();
        }

        weaponType attackWeapon;
        if (currentAction.getWeaponType() == weaponType.USER_WEAPON) {
            attackWeapon = getBT(currentTurnCharId).getWeapon();
        } else {
            attackWeapon = currentAction.getWeaponType();
        }


        if(!getBT(currentTargetBTId).hasAegisShield()) {
            resIds[0] = getImageRes.getAffinityImageResource(attackAff);
            resIds[1] = getImageRes.getAffinityImageResource(getBT(currentTargetBTId).getAffinity());
            resIds[2] = getImageRes.getEffectivenessIcon(affinityTable.getEffectiveness(attackAff, getBT(currentTargetBTId).getAffinity()));

            resIds[3] = getImageRes.getWeaponIcon(attackWeapon);
            resIds[4] = getImageRes.getWeaponIcon(getBT(currentTargetBTId).getWeapon());
            if (getCurrentCharBT().hasStatus(statusEffect.TURN_WEAPON_ADVANTAGE)) {
                resIds[5] = getImageRes.getEffectivenessIcon(effectiveness.EFFECTIVE);

            } else if (getCurrentTargetBTId().hasStatus(statusEffect.TURN_WEAPON_ADVANTAGE)) {

                resIds[5] = getImageRes.getEffectivenessIcon(effectiveness.INEFFECTIVE);
            } else {
                // normal
                resIds[5] = getImageRes.getEffectivenessIcon(weaponEffManager.getWeaponEffectiveness(attackWeapon,
                        getBT(currentTargetBTId).getWeapon()));
            }
        } else{
            // aegis shield looks different

            resIds[0] = getImageRes.getAffinityImageResource(attackAff);
            resIds[1] = R.drawable.aegis_shield_icon;
            resIds[2] = getImageRes.getEffectivenessIcon(effectiveness.NEUTRAL);

            resIds[3] = getImageRes.getWeaponIcon(attackWeapon);
            resIds[4] = R.drawable.aegis_shield_icon;
            resIds[5] = getImageRes.getEffectivenessIcon(effectiveness.NEUTRAL);
        }

        return resIds;
    }


//          etc. things

    public moveType getHitSFX(double val){
        if(val < 0.16){
            return moveType.LIGHT_HIT;
        } else if (val < 0.4){
            return moveType.MED_HIT;
        } else{
            return moveType.HEAVY_HIT;
        }
    }

    private moveType getInitCharSFX(){

        if(admin.selectedAction.getDamageType() == damageType.PHYS &&
                admin.selectedAction.getDamageType() == damageType.MAG) {
            if (admin.selectedAction.getDamageMod() >= 1.2) {
                return moveType.FINALE_ATTACK;
            } else if (admin.selectedAction.getDamageMod() > 1) {
                return  moveType.FAST_ATTACK;
            } else {
                return moveType.NORMAL_ATTACK;
            }
        } else{
            return moveType.SELF_BUFF;
        }
    }

    public ArrayList<Integer> getTargetables() {
        ArrayList<battleToken> targetablesBTs;

        if (getBT(currentTurnCharId).getIsPlayer()) {
            //player
            if (currentAction.getTargetsAlly()) {
                targetablesBTs = combatants.getActiveBattleTokens(true);
            } else {
                targetablesBTs = combatants.getActiveBattleTokens(false);
            }
        } else {
            //enemy
            if (currentAction.getTargetsAlly()) {
                targetablesBTs = combatants.getActiveBattleTokens(false);
            } else {
                targetablesBTs = combatants.getActiveBattleTokens(true);
            }
        }

        ArrayList<Integer> targetables = new ArrayList<>();
        for (int i = 0; i < targetablesBTs.size(); i++) {
            targetables.add(targetablesBTs.get(i).getBattleSerialNum());
        }

        return targetables;
    }
    public ArrayList<Integer>[] getRangeSortedTargetables(charAction selectedAction) {

        ArrayList<battleToken> potentialTargetables = new ArrayList<>();

        if (selectedAction.getAffectedTarget() != target.SELF && selectedAction.getAffectedTarget() != target.SWALLOWED ) {
            if(selectedAction.getAffectedTarget() != target.GRAPPLED) {
                if (getBT(currentTurnCharId).getIsPlayer()) {
                    //player
                    if (currentAction.getTargetsAlly()) {
                        potentialTargetables = combatants.getActiveBattleTokens(true);
                    } else {
                        potentialTargetables = combatants.getActiveBattleTokens(false);
                    }
                } else {
                    //enemy
                    if (currentAction.getTargetsAlly()) {
                        potentialTargetables = combatants.getActiveBattleTokens(false);
                    } else {
                        potentialTargetables = combatants.getActiveBattleTokens(true);
                    }
                }
            } else{
                if(getCurrentCharBT().hasStatus(statusEffect.GRAPPLE)){
                    int grapplee = (int) getBT(currentTurnCharId).getBonusEffectExtra(statusEffect.GRAPPLE);
                    potentialTargetables.add(getBT(grapplee));
                }
            }
        } else {
            potentialTargetables.add(getBT(currentTurnCharId));
        }

        ArrayList<Integer> targetablesWithinRange = new ArrayList<>();
        ArrayList<Integer> targetablesOutOfRange = new ArrayList<>();
        ArrayList<Integer> targetablesSuperOutOfRange = new ArrayList<>();

        if (!selectedAction.getTargetsAlly()) {
            boolean targetIsFrontLine;
            int rangeDiscrepancy;

            for (int i = 0; i < potentialTargetables.size(); i++) {

                rangeDiscrepancy = rangeify(selectedAction.getRange(), potentialTargetables.get(i).getBattleSerialNum());

                switch (rangeDiscrepancy) {
                    case 0:
                        targetablesWithinRange.add(potentialTargetables.get(i).getBattleSerialNum());
                        break;
                    case 1:
                        targetablesOutOfRange.add(potentialTargetables.get(i).getBattleSerialNum());
                        break;
                    case 2:
                        targetablesSuperOutOfRange.add(potentialTargetables.get(i).getBattleSerialNum());
                        break;
                }
            }
        } else {
            for (int i = 0; i < potentialTargetables.size(); i++) {
                targetablesWithinRange.add(potentialTargetables.get(i).getBattleSerialNum());
            }
        }


        ArrayList[] res = new ArrayList[3];

        res[0] = targetablesWithinRange;
        res[1] = targetablesOutOfRange;
        res[2] = targetablesSuperOutOfRange;

        return res;
    }

    public void setRowCollapse(boolean isPlayer) {
        combatants.setRowCollapsed(isPlayer);
    }

    public boolean enemyShouldBeFrontLine(int id) {
        boolean res = getBT(id).getChars().shouldBeFrontLine();

        //for some small chance of wackness
        if (percentagize(8)) {
            return !res;
        }

        return res;
    }

    public void charKoed(Integer charId) {
        if (getIsPlayer(charId)) {
            playerTurnOrder.remove(charId);
        } else {
            enemyTurnOrder.remove(charId);
        }
    }

    // int [hp, hpmax, sp, spmax]
    public int[] getCharHpSp(int id) {
        return new int[]{
                getBT(id).getHP(),
                getBT(id).getHPMax(),
                getBT(id).getSP(),
                getBT(id).getSPMax(),
        };
    }

    private void setGrapplerHpArrayList(){

        ArrayList<Integer> grapCharIds = grappleManager.getGrapplingCharIDs();
        grapplerHpArrayList.clear();

        for (int i = 0; i < grapCharIds.size(); i++) {
            grapplerHpArrayList.add(new duo(getBT(grapCharIds.get(i)).getHP(), grapCharIds.get(i)));
        }
    }
    public void checkGrappleArrayList(){

        // targetId -> hpval
        // initiatorId -> charId

        for (int i = 0; i < grapplerHpArrayList.size(); i++) {

            int grapplerId = grapplerHpArrayList.get(i).initiatorId;
            if(getBT(grapplerId).hasStatus(statusEffect.GRAPPLE)) {

                int grappleeId = (int) getBT(grapplerId).getBonusEffectExtra(statusEffect.GRAPPLE);

                int prevHpVal = grapplerHpArrayList.get(i).targetId;
                int curHpVal = getBT(grapplerId).getHP();
                float maxHp = getBT(grapplerId).getHPMax();

                if ((prevHpVal / maxHp) - (curHpVal / maxHp) >= 0.5) {
                    // if took over 50% dmg


                    String releaseMsg;
                    if(getBT(grappleeId).hasStatus(statusEffect.SWALLOWED)){
                        releaseMsg = getBT(grappleeId).getName() + " was spit out!";
                    } else{
                        releaseMsg = getBT(grappleeId).getName() + " was released from the grapple!";
                    }

                    admin.runOnUiThread(() -> Toast.makeText(admin.getBaseContext(), releaseMsg, Toast.LENGTH_SHORT).show());

                    getBT(grapplerId).grappleRelease(true, true);
                    getBT(grappleeId).grappleRelease(true, true);
                    grappleManager.removeDuo(grapplerId);
                    admin.conveyVisual(statusEffect.GRAPPLE_RELEASE, 1);


                    fightMidgrounds.post(getBT(grapplerId).getName(), getBT(grappleeId).getName(),
                            currentAction.getName(), GRAPPLE_RELEASE, 50, null);
                }
            }
        }
    }

    public boolean clashReactionTriggered(){

        if(getIsPlayer(currentTargetBTId) == getIsPlayer(currentTurnCharId)){
            return false;
        } else {

            switch (clashReactionMode) {
                case 0:
                default:
                    return false;

                case 1:
                    return currentAction.getAffinity() == getCurrentCharBT().getAffinity();

                case 2:
                    return true;
            }
        }
    }

    public void offActionSelected(int charId1, ArrayList<Integer> targetIds,
                                  charAction selectedAction1, boolean isStatus) {

        actionhandler.offActionSelected(charId1, targetIds, selectedAction1,
                getAssistBonuses(), isStatus);
    }

    public attackValues getAttackValues(int targetId) {
        return actionhandler.getAttackValues(targetId, true);
    }

    public void enactActionBonusEffects(int dmg,
                                        ArrayList<actionHandler.backgroundTaskReport> tasks) {
        for (int i = 0; i < tasks.size(); i++) {

            actionHandler.backgroundTaskReport cur = tasks.get(i);
            int val = 0;

            boolean shouldPrint = true;

            switch (cur.statusEffect) {

                case CHAIN_ATTACK:
                case RECOIL:
                    // runs off of a chain attack version. has a x0.4 mod

                    val = doActuateChainAttackDamage(cur.statusTarget, currentAction);
                    break;

                case GRAPPLE:
                    // really just removes grappling effect from both.
                    // char releases any persisting hold, and targets grapple is released.

                    getCurrentCharBT().grappleRelease(false, false);
                    getCurrentTargetBTId().grappleRelease(false, false);

                    boolean success = grappleManager.addDuo(currentTurnCharId, cur.statusTarget,false);
                    if(success) {

                        getCurrentCharBT().addStatusEffect(statusEffect.GRAPPLE, -1, currentTargetBTId);
                        getCurrentTargetBTId().addStatusEffect(statusEffect.GRAPPLED, -1, currentTurnCharId);

                        admin.conveyVisual(cur.statusEffect, cur.statusTarget);
                    }

                    midan.post(getCurrentCharBT().getName(), getBT(cur.statusTarget).getName(),
                            currentAction.getName(), getTextType(statusEffect.GRAPPLE, success), null, null);

                    break;

                case SWALLOW:

                    // releases grapple / grappling effect from targets. swallow overpowers this.
                    getCurrentCharBT().grappleRelease(false, false);
                    getCurrentTargetBTId().grappleRelease(false, true);

                    boolean successful = grappleManager.addDuo(currentTurnCharId, cur.statusTarget,true);
                    if (successful) {

                        getCurrentCharBT().addStatusEffect(statusEffect.SWALLOW, -1, currentTargetBTId);
                        getCurrentTargetBTId().addStatusEffect(statusEffect.SWALLOWED, -1, currentTurnCharId);

                        admin.conveyVisual(cur.statusEffect, cur.statusTarget);
                    }

                    fightMidgrounds.post(getCurrentCharBT().getName(), getBT(cur.statusTarget).getName(),
                            currentAction.getName(), getTextType(statusEffect.SWALLOW, successful), null, null);

                    break;

                case GRAPPLE_RELEASE:
                    getCurrentCharBT().grappleRelease(true, true);
                    getBT(grappleManager.getPartner(currentTurnCharId)).grappleRelease(true, true);
                    grappleManager.removeDuo(currentTurnCharId);

                    // also makes visible
                    admin.conveyVisual(cur.statusEffect, cur.statusTarget);

                    break;

                case HEAL:
                    val = (int) (getBT(cur.statusTarget).getHPMax() * cur.mod);
                    replenishHP(cur.statusTarget, val);
                    break;

                case ATK_HEAL:
                    val = (int)(dmg * cur.mod);
                    replenishHP(currentTurnCharId, val);
                    break;

                case BALM:
                    boolean targetingSelf = cur.statusTarget == currentTurnCharId;
                    ArrayList<stat> stats = getBT(cur.statusTarget).balm(targetingSelf);
                    for (int j = 0; j < stats.size(); j++) {
                        if(stats.get(i) != null){
                            midan.post(getCurrentCharBT().getName(), getBT(cur.statusTarget).getName(),
                                    currentAction.getName(), BALM, null, stats.get(i));
                        }
                    }
                    admin.conveyVisual(cur.statusEffect, cur.statusTarget);

                    break;

                case RELOAD:
                    getBT(cur.statusTarget).reload();
                    midan.post(getCurrentCharBT().getName(), "", currentAction.getName(), RELOAD,
                            getCurrentCharBT().getBulletCount(), null);
                    admin.conveyVisual(statusEffect.RELOAD, cur.statusTarget);
                    break;

                case RUNE_BURST:
                case ELEMENTAL_BURST:
                case DIVINE_BURST:
                    charAction el = new charAction();
                    el.setDamageType("mag");
                    // dmg is nerfed so here it is buffed
                    el.setDamageMod(2.5);
                    el.setAffectedTarget(target.ENEMY_COLUMN);

                    if(cur.statusEffect == statusEffect.ELEMENTAL_BURST) {
                        el.setAffinity(codeBlockHolder.elementalBurst.getRandomAffinity());


                        val = doActuateChainAttackDamage(cur.statusTarget, el);

                        // im using attack name to store affinity cuz we dont need another variable kk
                        fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(), el.getAffinity().toString(),
                                fightUpdater.textType.getTextType(statusEffect.ELEMENTAL_BURST, true), val, null);
                    } else {
                        if(cur.statusEffect == statusEffect.RUNE_BURST) {
                            if (percentagize(50)) {
                                el.setAffinity(affinity.GHENJU);
                            } else {
                                el.setAffinity(affinity.DUSK);
                            }


                            val = doActuateChainAttackDamage(cur.statusTarget, el);

                            fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(), el.getAffinity().toString(),
                                    fightUpdater.textType.getTextType(statusEffect.RUNE_BURST, true), val, null);

                        } else {
                            el.setAffinity(affinity.AETHER);


                            val = doActuateChainAttackDamage(cur.statusTarget, el);

                            fightMidgrounds.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(), el.getAffinity().toString(),
                                    fightUpdater.textType.getTextType(statusEffect.DIVINE_BURST, true), val, null);
                        }
                    }

                    admin.conveyVisual(cur.statusEffect, cur.statusTarget);

                    shouldPrint = false;
                    break;

                case MOVE_NEXT:
                    moveNextId = cur.statusTarget;

                    getBT(cur.statusTarget).altActionCount(1);

                    Thread fetchActionThread = getBT(cur.statusTarget).getChars().exactFetchActions();

                    midan.post(getBT(moveNextId).getName(), "",
                            currentAction.getName(), fightUpdater.textType.getTextType(cur.statusEffect, true), val, null);

                    try {
                        fetchActionThread.join();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    shouldPrint = false;

                    break;

                case INSTAKILL:
                    instakill(cur.statusTarget, currentAction);
                    break;

                default:

            }

            if(shouldPrint) {
                midan.post(getCurrentCharBT().getName(), getCurrentTargetBTId().getName(),
                        currentAction.getName(), fightUpdater.textType.getTextType(cur.statusEffect, true), val, null);
            }
        }

        actionhandler.resetReturningTasks();

    }

    public static String getActionTags() {
        return actionTags.toString();
    }

}
