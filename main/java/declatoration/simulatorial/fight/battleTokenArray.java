package declatoration.simulatorial.fight;

import static declatoration.simulatorial.fight.actionHandler.percentagize;
import static declatoration.simulatorial.fight.fightBackground.assistManager;
import static declatoration.simulatorial.fight.fightBackground.clashReactionManager;
import static declatoration.simulatorial.fight.fightBackground.currentTurnCharId;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import declatoration.simulatorial.bonusEffects;
import declatoration.simulatorial.catalog;
import declatoration.simulatorial.charAction;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.dmgMods;
import declatoration.simulatorial.enums.effectiveness;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.target;

public class battleTokenArray {

//    static int collectiveSpd;
    int playerSize;
    public int btSize;

    private ArrayList<battleToken> battleTokens;
    private ArrayList<battleToken> activeBattleTokens = new ArrayList<>();

    private boolean playerRowCollapse = false;
    private boolean enemyRowCollapse = false;

    static charClickedBridger bridger;
    fightForeground ff;

    ArrayList<Integer> playerRow1 = new ArrayList<>();
    ArrayList<Integer> playerRow2 = new ArrayList<>();
    ArrayList<Integer> enemyRow1 = new ArrayList<>();
    ArrayList<Integer> enemyRow2 = new ArrayList<>();

    ArrayList<Integer> enemiesLeftToSpawn = new ArrayList<>();

    public battleTokenArray(Context context, charClickedBridger charClickedBridger,
                            @NonNull catalog allChars, int megaDeathSize, fightForeground foreground) {

        ff = foreground;

        battleTokens = new ArrayList<>(allChars.getCharCount());
        playerSize = megaDeathSize;

        for (int i = 0; i < allChars.getCharCount(); i++) {
            if (allChars.charList.get(i) != null) {
                battleTokens.add(new battleToken(context,
                        charClickedBridger,
                        allChars.charList.get(i),
                        i));
                if (i < playerSize) {
                    battleTokens.get(i).setIsPlayer(true);
                }
            }
        }

        activeBattleTokens.addAll(battleTokens);
        bridger = charClickedBridger;

        List<battleToken> enemy = getActiveBattleTokens(false);
        for (int i = 6; i < enemy.size(); i++) {
            enemiesLeftToSpawn.add(enemy.get(i).getBattleSerialNum());

        }
    }


    public int length() {
        return this.battleTokens.size();
    }

    public battleToken getBattleToken(Integer id) {
        if(id != null && id > -1) {
            return battleTokens.get(id);
        } else return null;
    }

    public ArrayList<battleToken> getActiveBattleTokens() {
        return activeBattleTokens;
    }
    public ArrayList<battleToken> getActiveBattleTokens(boolean getPlayerBTs) {

        ArrayList<battleToken> temp = new ArrayList<>();
        for (int i = 0; i < battleTokens.size(); i++) {

            if ((battleTokens.get(i).getIsPlayer() == getPlayerBTs) &&
                    (battleTokens.get(i).getIsAlive())
                && !enemiesLeftToSpawn.contains(battleTokens.get(i).getBattleSerialNum())) {

                temp.add(battleTokens.get(i));
            }
        }

        return temp;
    }

    public boolean getTotalPlayerPartyKO() {

        return getActiveBattleTokens(true).size() == 0;
    }
    public boolean getTotalEnemyPartyKO() {
        ArrayList<battleToken> temp = this.getActiveBattleTokens(false);
        if (temp.size() == 0 && enemiesLeftToSpawn.size() == 0) {
            return true;
        }
        return false;
    }

    public void charKoed(battleToken BT) {

        activeBattleTokens.remove(BT);

        getRowArrayList(BT.getIsPlayer(), BT.getIsFrontLine()).remove(BT.getBattleSerialNum());

        // remove any lasting effects char has left behind
        // engage + intimidate
        int koedCharId = BT.getBattleSerialNum();
        ArrayList<battleToken> allBTs = getActiveBattleTokens();


        for (int i = 0; i < allBTs.size(); i++) {
            allBTs.get(i).removeStatusViaImposerId(koedCharId);
        }
    }

    public Integer getEnemyToSpawn(){
        Integer charToSpawn = null;
        if(enemiesLeftToSpawn.size() > 0){
            charToSpawn = enemiesLeftToSpawn.get(0);
            enemiesLeftToSpawn.remove(0);
        }

        return charToSpawn;
    }


    public charAction getCharAction(int charId, int actionId){

        charAction charAction = new charAction();

        return charAction;
    }


    //rows

    public int getRangeDiff(int charId, int targetId) {
        int range = 1;
        if (!getBattleToken(charId).getIsFrontLine()) {
            range++;
        }
        if (!getBattleToken(targetId).getIsFrontLine()) {
            range++;
        }
        return range;
    }

    public void setRowCollapsed(boolean isPlayer) {
        if (isPlayer) {
            playerRowCollapse = true;
        } else {
            enemyRowCollapse = true;
        }
    }
    public boolean rowCollapsed(boolean isPlayer) {
        if (isPlayer) {
            return playerRowCollapse;
        } else {
            return enemyRowCollapse;
        }
    }

    public boolean charExistsOnFirstRow(boolean isPlayer) {
        ArrayList<battleToken> bts = getActiveBattleTokens(isPlayer);
        if (isPlayer && !playerRowCollapse) {
            for (int i = 0; i < bts.size(); i++) {
                battleToken battleToken = bts.get(i);
                if (battleToken.getIsPlayer()) {
                    if (battleToken.getIsFrontLine()) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            for (int i = 0; i < bts.size(); i++) {
                battleToken battleToken = bts.get(i);
                if (!battleToken.getIsPlayer()) {
                    if (battleToken.getIsFrontLine()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }


    public boolean executeRowSwapIfAvailable(int charId, boolean toFrontRow){
        boolean isPlayer = getBattleToken(charId).getIsPlayer();
        ArrayList<Integer> rowToMoveTo = getRowArrayList(isPlayer, toFrontRow);
        ArrayList<Integer> existingRow = getRowArrayList(isPlayer, !toFrontRow);


        if(!rowCollapsed(isPlayer) && (rowToMoveTo.size() < 3)) {
            rowToMoveTo.add(charId);
            existingRow.remove((Integer) charId);

            getBattleToken(charId).setFrontLine(toFrontRow);
            return true;

        } else{
            rowToMoveTo = getRowArrayList(isPlayer, !toFrontRow);
            if(!rowToMoveTo.contains(charId)) {
                rowToMoveTo.add(charId);
            }

            getBattleToken(charId).setFrontLine(!toFrontRow);
            return false;
        }
    }
    private ArrayList<Integer> getRowArrayList(boolean isPlayer, boolean frontRow){
        ArrayList<Integer> curRowArrayList;

        if(isPlayer){
            if(frontRow) {
                curRowArrayList = playerRow1;
            } else{
                curRowArrayList = playerRow2;
            }
        } else{
            if(frontRow){
                curRowArrayList = enemyRow1;
            } else{
                curRowArrayList = enemyRow2;
            }
        }
        return curRowArrayList;
    }




    //targeting

    public ArrayList<Integer> findTargetableSerialNums(int curChar, target tar) {

        ArrayList<Integer> targetables = new ArrayList<>();
        ArrayList<battleToken> temp = new ArrayList<>();

        battleToken bt = getBattleToken(curChar);

        if(tar != target.RANDOM_FROM_ALL) {
            if (tar != target.SELF) {

                if (!bt.hasStatus(statusEffect.SWALLOWED)) {
                    boolean getPlayerBTs = !bt.getIsPlayer();

                    //should cover for random as well, as it gives enemy bts
                    if (tar.toString().contains("ALLY") || tar.toString().contains("ALLIES")) {
                        getPlayerBTs = !getPlayerBTs;
                    }

                    temp = getActiveBattleTokens(getPlayerBTs);

                    if (tar == target.SINGLE_ALLY_NO_USER) {
                        temp.remove(bt);
                    }
//            } else{
//                temp.add(getBattleToken((int)getBattleToken(curChar).getBonusEffectExtra(statusEffect.ENGAGE)));
//            }
                } else {
                    temp.add(getBattleToken((Integer) bt.getBonusEffectExtra(statusEffect.SWALLOWED)));
                }
            } else {
                temp.add(bt);
            }
        } else {
            temp = getActiveBattleTokens();
        }


        if (tar == target.SINGLE_ALLY_NO_USER) {
            temp.remove(bt);
        }

        for (int i = 0; i < temp.size(); i++) {
            targetables.add(temp.get(i).getBattleSerialNum());
            temp.get(i).getCharLayout().setIsTargetable(true);
        }
        return targetables;
    }

    public ArrayList<Integer> getUntargetablesIfExist(boolean playerSideBTs) {
        ArrayList<battleToken> BTs = getActiveBattleTokens(playerSideBTs);
        ArrayList<Integer> untargetables = new ArrayList<>();


        for (int i = 0; i < BTs.size(); i++) {
            if (BTs.get(i).hasStatus(statusEffect.UNTARGETABLE)) {
                untargetables.add(i);
            }
        }

        return untargetables;
    }

    public synchronized ArrayList<Integer> getRowColumnTargets(Integer initialTargetId){
        int verticalIndex = getVerticalIndex(initialTargetId);
        boolean isPlayer = getIsPlayer(initialTargetId);

        ArrayList<Integer> finalArrayList = new ArrayList<>();


        ArrayList<Integer> rowArrayList = getRowArrayList(isPlayer, getBattleToken(initialTargetId).getIsFrontLine());
        ArrayList<Integer> otherRowArrayList = getRowArrayList(isPlayer, !getBattleToken(initialTargetId).getIsFrontLine());

        finalArrayList.add(initialTargetId);

        switch (rowArrayList.size()){
            case 1:
                switch (otherRowArrayList.size()){
                    case 0:
                    default:
                        break;
                    case 1:
                        finalArrayList.add(otherRowArrayList.get(0));
                        break;
                    case 2:
                        if(percentagize(50)){
                            finalArrayList.add(otherRowArrayList.get(0));
                        } else {
                            finalArrayList.add(otherRowArrayList.get(1));
                        }
                        break;
                    case 3:
                        finalArrayList.add(otherRowArrayList.get(1));

                        if(percentagize(30)){
                            finalArrayList.add(otherRowArrayList.get(0));
                        } else if(percentagize(30)){
                            finalArrayList.add(otherRowArrayList.get(2));
                        }
                }
                break;

            case 2:
                switch (otherRowArrayList.size()){
                    case 0:
                    default:
                        break;

                    case 1:
                        if(percentagize(50)){
                            finalArrayList.add(otherRowArrayList.get(0));
                        }
                        break;

                    case 2:
                        finalArrayList.add(otherRowArrayList.get(verticalIndex));
                        break;

                    case 3:

                        if(percentagize(50)){
                            finalArrayList.add(otherRowArrayList.get(verticalIndex));

                            if(percentagize(50)){
                                finalArrayList.add(otherRowArrayList.get(verticalIndex + 1));
                            }
                        } else{
                            finalArrayList.add(otherRowArrayList.get(verticalIndex + 1));

                            if(percentagize(30)){
                                finalArrayList.add(otherRowArrayList.get(verticalIndex));
                            }
                        }
                }
                break;

            case 3:
                switch (otherRowArrayList.size()){
                    case 0:
                        break;

                    case 1:
                        if (verticalIndex == 1){
                            finalArrayList.add(otherRowArrayList.get(0));
                        } else{

                            if(percentagize(50)){
                                finalArrayList.add(otherRowArrayList.get(0));
                            }
                        }
                        break;

                    case 2:
                        switch (verticalIndex){
                            case 0:
                                if(percentagize(50)){
                                    finalArrayList.add(otherRowArrayList.get(0));
                                }
                                break;
                            case 1:

                                if(percentagize(50)){
                                    finalArrayList.add(otherRowArrayList.get(0));
                                } else {
                                    finalArrayList.add(otherRowArrayList.get(1));
                                }
                                break;
                            case 2:
                                if(percentagize(50)){
                                    finalArrayList.add(otherRowArrayList.get(1));
                                }
                                break;
                        }
                        if(percentagize(50)){
                            finalArrayList.add(otherRowArrayList.get(0));
                        }
                        break;

                    case 3:
                        finalArrayList.add(otherRowArrayList.get(verticalIndex));
                        break;
                }
                break;

            default:
                if(otherRowArrayList.size() >= verticalIndex) {
                    finalArrayList.add(otherRowArrayList.get(verticalIndex));
                }
                break;
        }

        return finalArrayList;

    }
    private Integer getVerticalIndex(int id){
        return getRowArrayList(getIsPlayer(id),
                getBattleToken(id).getIsFrontLine()).indexOf(id);
    }




    // effects

    public void applyEffect(bonusEffects cur, int curCharId, int targetId, String attackName) {
        int curTargetId;
        stat changingStat = null;

        if (cur.triggeredViaPercent()) {

            //calcs targets
            switch (cur.getStatusTarget()) {
                case SELF:
                    curTargetId = curCharId;
                    break;

                case TARGET:
                default:
                    curTargetId = targetId;
                    break;

                case RANDOM_TARGET:
                    ArrayList<battleToken> enemyBTs = getActiveBattleTokens(!getBattleToken(curCharId).getIsPlayer());
                    int rand = (int) (Math.random() * enemyBTs.size());

                    for (int i = 0; i < 5; i++) {
                        if (rand != targetId) {
                            break;
                        }
                        rand = (int) (Math.random() * enemyBTs.size());
                    }

                    curTargetId = enemyBTs.get(rand).getBattleSerialNum();
                    break;
            }

            // deals with respective statuses
            switch (cur.getStatus()) {
                case IMMUNITY:
                    getBattleToken(curTargetId).cureStatusEffects();

                case POISON:
                case BURN:
                case RATTLE:
                case SLEEP:
                case STUN:
                case FREEZE:
                case BANE:
                case BLEED:
                case RAGE:
                case ENERVATE:
                case BEDRAGGLE:
                case WHIRLPOOL:

                case REGEN:
                case THORNS:
                case COUNTER:
                case RAMPART:

                case ROW_LOCKDOWN:
                case EX_ADVANTAGE:
                case TURN_WEAPON_ADVANTAGE:
                    // just assigns a status effect normally, to target/s determined above
                    if(getBattleToken(curTargetId).getIsAlive()) {
                        getBattleToken(curTargetId).addStatusEffect(cur.getStatus(), cur.getStatusLength());
                        bridger.conveyVisual(cur.getStatus(), curTargetId);
                    }

                    break;

                case EX_ATTACK_MOD:
                case EX_AVO_MOD:
                case EX_AGGRO_MOD:
                    getBattleToken(curTargetId).addStatusEffect(cur.getStatus(), cur.getStatChangeDegree());
                    bridger.conveyVisual(cur.getStatus(), curTargetId);

                case TOPPLE:
                case UNTARGETABLE:
                    getBattleToken(curTargetId).addStatusEffect(cur.getStatus(), 1);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);

                    break;

                case ADVANTAGE_DX:
                case DISADVANTAGE_DX:
                    getBattleToken(curTargetId).shouldRemoveAdvantage = false;
                case EVADE:
                case EVADE_DX:
                case EX_CRIT:
                case INFILTRATE:
                case CHAOS_RIFT:
                    getBattleToken(curTargetId).addStatusEffect(cur.getStatus(), -1);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case AEGIS_SHIELD:
                    getBattleToken(curTargetId).createAegisShield();
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case INSTAKILL:
                    getBattleToken(curTargetId).setHP(0);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case NERD:
                    int newHpVal = getBattleToken(curTargetId).getHP() - getBattleToken(curTargetId).getHPMax() / 2;
                    getBattleToken(curTargetId).setHP(newHpVal);

                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case SET_HP:
                    int dmg = getBattleToken(curTargetId).getHP() - (int)((float) cur.getStatChangeDegree());
                    getBattleToken(curTargetId).setHP((int)((float) cur.getStatChangeDegree()));

                    ff.damageNums(dmg, false, effectiveness.NEUTRAL, null, curTargetId);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case STAT_RAISE:
                case STAT_DROP:
                    int changeDegree = Math.round(cur.getStatChangeDegree());
                    if (cur.getStatus() == statusEffect.STAT_DROP) {
                        changeDegree *= -1;
                    }
                    changingStat = getBattleToken(curTargetId).applyStatChange(cur.getChangingStat(), changeDegree);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);

                    break;


                case ROW_KNOCKBACK:
                case ROW_LURE:
                case ROW_SWAP:

                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case CURE_STATUS:
                    getBattleToken(curTargetId).cureStatusEffects();
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case APPLY_TABLE_EFFECT:
                    clashReactionManager.fluxAction(curCharId, targetId);
                    break;


                case SP_ABSORB:
                    int absorbAmount = 15;
                    if(cur.getStatChangeDegree() != null){
                        absorbAmount *= cur.getStatChangeDegree();
                    }
                    int changeAmount = getBattleToken(curTargetId).setSP(getBattleToken(curTargetId).getSP() - absorbAmount);
                    getBattleToken(curCharId).setSP(getBattleToken(curCharId).getSP() + changeAmount);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case SPREGEN:
                    getBattleToken(curTargetId).setSP(getBattleToken(curTargetId).getSP() + 15);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case ENGAGE:
                case INTIMIDATE:
                case CHARM:
                case ASSIST_UP:
                case DEFENDED:
                    if(curTargetId != currentTurnCharId) {
                        getBattleToken(curTargetId).addStatusEffect(cur.getStatus(), cur.getStatusLength(),
                                curCharId);
                    } else {
                        getBattleToken(curTargetId).addStatusEffect(cur.getStatus(), cur.getStatusLength(),
                                targetId);
                    }
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case COPY_STAT_CHANGES:
                    getBattleToken(curCharId).copyBoostBoard(getBattleToken(targetId).getBoostBoard());
                    bridger.conveyVisual(cur.getStatus(), targetId);
                    break;

                case STAT_STEAL_SINGLE:
                    changingStat = getBattleToken(curTargetId).statStealReduction(cur.getChangingStat(), 1, false);
                    changingStat = getBattleToken(curCharId).applyStatChange(changingStat, 1);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;
                case STAT_STEAL_ALL:
                    getBattleToken(curCharId).addStealBonuses(getBattleToken(targetId).stealAllStatChanges());
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case ACTION_ADD:
                    getBattleToken(curTargetId).altActionCount(1);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;
                case ACTION_REDUCE:
                    getBattleToken(curTargetId).altActionCount(-1);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;
                case END_TURN:
                    getBattleToken(curTargetId).terminateTurn();
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case RESET_STAT_CHANGES:
                    getBattleToken(curTargetId).resetStatTable();
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case ASSIST:
                    setAssistants(curTargetId, curCharId, true);
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case METAMORPH:
                    getBattleToken(curCharId).metamorph(ff.selectedAction.getAffinity());
                    bridger.conveyVisual(cur.getStatus(), curTargetId);
                    break;

                case RANDOM:
                    applyEffect(new bonusEffects(statusEffect.randomStatus()) , curCharId, targetId, attackName);
                    break;

                default:
            }

            if(cur.getStatus() != statusEffect.STAT_DROP && cur.getStatus() != statusEffect.STAT_RAISE) {
                bridger.post(getBattleToken(curCharId).getName(), getBattleToken(targetId).getName(),
                        attackName, fightUpdater.textType.getTextType(cur.getStatus(), true), null, changingStat);
            } else{
                bridger.post("", getBattleToken(curTargetId).getName(),
                        attackName, fightUpdater.textType.getTextType(cur.getStatus(), true), null, changingStat);
            }
        }
    }

    public void applyEffect(affinityClashExtraEffects.clashEffect cur, int curCharId, int targetId, Integer statusLength) {

        // for clashReactions
        statusEffect asStatusEffect = statusEffect.getStatusEnum(cur.reaction.toString());

        switch (cur.reaction) {
            case POISON:
            case BURN:
            case RATTLE:
            case SLEEP:
            case STUN:
            case FREEZE:
            case BANE:
            case BLEED:
            case RAGE:
            case ENERVATE:
            case BEDRAGGLE:

            case REGEN:
            case CLASH_THORNS:
//                case COUNTER:
//                case RAMPART:
//                case EVADE:
//                case EVADE_DX:
//
//                case EX_ATTACK_MOD:
//                case EX_AVO_MOD:
//                case EX_ADVANTAGE:
//                case EX_AGGRO_MOD:
                // just assigns a status effect normally, to target/s determined above
                getBattleToken(targetId).addStatusEffect(asStatusEffect, statusLength);
                bridger.conveyVisual(asStatusEffect, targetId);

                break;


            case INSTAKILL:
                getBattleToken(targetId).setHP(0);
                bridger.conveyVisual(asStatusEffect, targetId);
                break;

            case ATK:
            case DEF:
            case MAG:
            case INT:
            case SPD:
            case CRIT:
                getBattleToken(targetId).applyStatChange(
                        stat.getStatEnum(cur.reaction.toString()), cur.degree);

                if (cur.degree < 1) {
                    bridger.conveyVisual(statusEffect.STAT_DROP, targetId);
                } else {
                    bridger.conveyVisual(statusEffect.STAT_RAISE, targetId);
                }
                break;


            case ROW_KNOCKBACK:
                bridger.conveyVisual(asStatusEffect, targetId);
                break;

            case ENGAGE:
            case INTIMIDATE:
                getBattleToken(targetId).addStatusEffect(asStatusEffect, statusLength, curCharId);
                bridger.conveyVisual(asStatusEffect, targetId);
                break;

            default:
        }

    }




    //specific effects

    public String getRampName(boolean isPlayer) {
        ArrayList<battleToken> BTs = getActiveBattleTokens(isPlayer);

        for (int i = 0; i < BTs.size(); i++) {
            if (BTs.get(i).getIsFrontLine() && BTs.get(i).hasStatus(statusEffect.RAMPART)) {
                return BTs.get(i).getName();
            }
        }

        return "null";
    }

    public boolean hasRampart(boolean playerSideBTs) {
        ArrayList<battleToken> BTs = getActiveBattleTokens(playerSideBTs);

        boolean hasRampartEffect = false;

        for (int i = 0; i < BTs.size(); i++) {
            if (BTs.get(i).getIsFrontLine() && BTs.get(i).hasStatus(statusEffect.RAMPART)) {
                hasRampartEffect = true;
                break;
            }
        }

        return hasRampartEffect;
    }

    public void updateEngageStatus(int charId) {
        battleToken bt = getBattleToken(charId);

        if (bt.hasStatus(statusEffect.ENGAGE)) {
            ArrayList<Integer> charBaseRange = getBattleToken(charId).getChars().getCharAction(0).getRange();

            int engagee = (int) bt.getBonusEffectExtra(statusEffect.ENGAGE);

            // if the engaged unit is out of chars base range, kill the effect
            if (!charBaseRange.contains(getRangeDiff(charId, engagee)) ||
                    !getBattleToken(engagee).getIsAlive()) {
                getBattleToken(charId).removeStatusIfExists(statusEffect.ENGAGE);
            } else {
            }
        }

    }

    public float getScalingMod(dmgMods scalingEffect, float maxValue,
                               int invokerId, int targetId){
        scaling scaler = new scaling();
        return scaler.getScalingMod(scalingEffect, maxValue, invokerId, targetId);
    }


    //random

    public double getTurnSwitchSpdRatio(boolean isPlayer) {
        // gives percentage to calculate backToBack turns
        float denom = 0;

        ArrayList<battleToken> BTs = getActiveBattleTokens(isPlayer);
        for (int i = 0; i < BTs.size(); i++) {
            denom += BTs.get(i).getSpd() * battleTokens.get(i).getBoostModifier(stat.SPD);

        }

        float topGuy = 0;
        ArrayList<battleToken> BTs2 = getActiveBattleTokens(!isPlayer);
        for (int i = 0; i < BTs2.size(); i++) {
            topGuy += BTs2.get(i).getSpd() * battleTokens.get(i).getBoostModifier(stat.SPD);

        }
        return 1.2 * topGuy / denom;
    }

    private boolean getIsPlayer(int id){
        return getBattleToken(id).getIsPlayer();
    }

    public void setAssistants(int assistee, int assistor, boolean b) {
        if(b) {
            getBattleToken(assistee).addStatusEffect(statusEffect.ASSIST, -1, assistor);
            assistManager.addAssistDuo(assistee, assistor);
        } else{
            getBattleToken(assistee).removeStatusIfExists(statusEffect.ASSIST);
        }
    }

    public ArrayList<Integer> getAllCharResIds(){

        ArrayList<Integer> resIds = new ArrayList<>(battleTokens.size());

        for (int i = 0; i < battleTokens.size(); i++) {
            resIds.add(battleTokens.get(i).getChars().getCharImageResId());
        }

        return resIds;
    }

    public ArrayList<String> getUnlockedCharsIfExist(Context context){

        ArrayList<String> unlocked = new ArrayList<>();

        databaseExec dbExec = databaseExec.getInstance(context);
        HashMap<Integer, String> names = dbExec.getAllPlayerCharNames();

        ArrayList<battleToken> enemyBTs = new ArrayList<>(battleTokens.subList(playerSize, battleTokens.size()));

        for (int i = 0; i < enemyBTs.size(); i++) {
            if(names.containsValue(enemyBTs.get(i).getName())){
                unlocked.add(enemyBTs.get(i).getName());
            }
        }

        return unlocked;
    }



    private class scaling{

        public scaling(){}

        public float getScalingMod(dmgMods scalingType, float maxValue, int invokerID, int targetId){
            float finalMod = 1;

            battleToken invokerBT = getBattleToken(invokerID);
            battleToken targetBT = getBattleToken(targetId);
            switch (scalingType){
                case HP_SCALING:
                    finalMod = 1 + (maxValue - 1) * (float)(invokerBT.getHP() / invokerBT.getHPMax());
                    break;
                case HP_LACK_SCALING:
                    finalMod = 1 + (maxValue - 1) * (1 - (float)(invokerBT.getHP() / invokerBT.getHPMax()));
                    break;

                case BUFF_SCALING:
                    float statVal =  ((float)invokerBT.getTotalStatChangeAmount() / 12);
                    if(statVal > 1){
                        statVal = 1;
                    }
                    finalMod = 1 + (maxValue - 1) * statVal;
                    break;
                case DEBUFF_SCALING:
                    float statVal2 =  ((float)targetBT.getTotalStatChangeAmount() / -12);
                    if(statVal2 > 1){
                        statVal2 = 1;
                    }
                    finalMod = 1 + (maxValue - 1) * statVal2;
                    break;

                case ALLIES_KOED:
                    int aliveChars = getActiveBattleTokens(getIsPlayer(invokerID)).size();
                    finalMod = 1 + (maxValue - 1) * (1 - (float)(aliveChars - 1) / (playerSize - 1));
                    break;

                case AGGRO_SCALING:
                case DEAGGRO_SCALING:

                case IS_AGGROED_SCALING:
                    if(targetBT.getAggroedOn() == invokerID){
                        finalMod = maxValue;
                    } else finalMod = 1;

                    break;


            }

            if(finalMod < 1){
                finalMod = 1;
            }

            return finalMod;

        }

    }
}
