package declatoration.simulatorial.fight;

import static declatoration.simulatorial.enums.moveType.BUFF;
import static declatoration.simulatorial.enums.moveType.DEBUFF;
import static declatoration.simulatorial.enums.moveType.HEAL;
import static declatoration.simulatorial.enums.moveType.STATUS;
import static declatoration.simulatorial.enums.stat.ALL;
import static declatoration.simulatorial.enums.stat.ATK;
import static declatoration.simulatorial.enums.stat.AVO;
import static declatoration.simulatorial.enums.stat.BLOCK;
import static declatoration.simulatorial.enums.stat.CRIT;
import static declatoration.simulatorial.enums.stat.DEF;
import static declatoration.simulatorial.enums.stat.HIGHEST;
import static declatoration.simulatorial.enums.stat.HIT;
import static declatoration.simulatorial.enums.stat.INT;
import static declatoration.simulatorial.enums.stat.LCK;
import static declatoration.simulatorial.enums.stat.MAG;
import static declatoration.simulatorial.enums.stat.MAGAVO;
import static declatoration.simulatorial.enums.stat.NULL;
import static declatoration.simulatorial.enums.stat.PHYSAVO;
import static declatoration.simulatorial.enums.stat.RANDOM;
import static declatoration.simulatorial.enums.stat.RES;
import static declatoration.simulatorial.enums.stat.SPD;
import static declatoration.simulatorial.enums.stat.SPREGEN;
import static declatoration.simulatorial.enums.statusEffect.AEGIS_SHIELD;
import static declatoration.simulatorial.enums.statusEffect.GRAPPLE;
import static declatoration.simulatorial.enums.statusEffect.GRAPPLED;
import static declatoration.simulatorial.enums.statusEffect.SLEEP;
import static declatoration.simulatorial.enums.statusEffect.SWALLOW;
import static declatoration.simulatorial.enums.statusEffect.SWALLOWED;
import static declatoration.simulatorial.fight.fightForeground.sonicJammer;
import static declatoration.simulatorial.fight.statusEffectList.negativeStatusEffects;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import declatoration.simulatorial.chars;
import declatoration.simulatorial.databaseExec;
import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.moveType;
import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.weaponType;
import declatoration.simulatorial.fight.sounds.charSoundBoard;


public class battleToken {

    // action figures representing
    // the face and forefront of each character on screen

    // nope they contain literally everything now

    private String name;
    private statusEffectList statusEffects;
    private boostBoard boosts;
    private charSoundBoard soundBoard;

    private int HP;
    private int HPMax;
    private float SP;
    private int SPMax;
    private final float SPRegen;
    private final int spd;
    private final float baseLCK;
    private int battleSerialNum;
    private affinity affinity;

    private boolean usesBullets = false;
    private Integer numBullets;
    private final Integer numBulletsMax;


    private int actionsPerTurn;
    private int actionsRemaining;
    private boolean hasFinaleActionRemaining = true;
    boolean shouldRemoveAdvantage = true;

    private final chars charSilhouette;


    cooldownManager coolMan;
    aegis aegisShield = null;


    private boolean isPlayer = true;
    private boolean isFrontLine;
    private boolean isKoed;

    private boolean focusAction = false;
    private boolean guardAction = false;

    private boolean isGrappling = false;
    private boolean isSwallowing = false;

    private boolean luckStatIsZero = false;


    private characterLayout charLayout;
    private ArrayList <statusEffect> statusEffectImmunities = new ArrayList<>();

    private static databaseExec dbExec;



    public battleToken(Context c, charClickedBridger charClickedBridger, chars ch, int serial){

        charSilhouette = ch;

        dbExec = databaseExec.getInstance(c);

        this.setName(ch.getName());

        this.setHPMax((int) ch.getStatFloat(stat.HP));
        this.setHP(HPMax);

        this.setSPMax((int) ch.getStatFloat(stat.SP));
        this.setSP(SPMax);
        this.SPRegen = ch.getStatFloat(stat.SPREGEN);

        affinity = ch.getAffinity();
        numBulletsMax = dbExec.getNumBullets(name);
        numBullets = numBulletsMax;
        usesBullets = !(numBulletsMax == null);

        this.baseLCK = dbExec.getCharBaseStat(name, LCK);
        this.spd = (int) ch.getStatFloat(stat.SPD);

        setBattleSerialNum(serial);

        isKoed = false;
        isFrontLine = true;
        setIsPlayer(false);

        statusEffects = new statusEffectList(this);

        this.actionsPerTurn = setBaseActionsPerTurn(dbExec.getCharBaseStat(this.name, SPD));
        actionsRemaining = 0;

        this.charLayout = new characterLayout(c, charClickedBridger, this.battleSerialNum, charSilhouette.getCharImageResId());
//        this.charLayout.init(charClickedBridger, battleSerialNum, backgroundResId);

        boosts = new boostBoard();
        coolMan = new cooldownManager();

        soundBoard = new charSoundBoard(sonicJammer.getSoundPool(), c, ch.getSFXCharBase());

//        // hoe gaan naar hier, de skillen?
//        statusEffectImmunities = statusImmunities;
    }


    public boolean hasTurn(){return statusEffects.turnAvailable();}

    public stat applyStatChange(stat changingStat, int degree){
        if(degree > 0){
            playSFX(BUFF);
        } else{
            playSFX(DEBUFF);
        }
        return boosts.addBoost(changingStat, degree);
    }

    public void cureStatusEffects(){
        statusEffects.cure();
    }

    public void addStatusEffect(statusEffect statusEffect, int duration){
        // generic application
        if(negativeStatusEffects.contains(statusEffect)){
            if(statusEffect != SLEEP) {
                playSFX(moveType.STATUS);
            } else{
                playSFX(moveType.SLEEP);
            }
        } else{
            playSFX(moveType.HEAL);
        }

        if (!statusEffectImmunities.contains(statusEffect) && !statusEffects.contains(statusEffect)){
            statusEffects.addStatusEffect(statusEffect, duration);
        }
    }
    public void addStatusEffect(statusEffect statusEffect, float degree){
        // generic application
        if(negativeStatusEffects.contains(statusEffect)){
            playSFX(STATUS);
        } else{
            playSFX(HEAL);
        }
        if (!statusEffectImmunities.contains(statusEffect) && !statusEffects.contains(statusEffect)){
            statusEffects.addStatusEffect(statusEffect, degree);
        }
    }
    public void addStatusEffect(statusEffect statusEffect, Integer duration, int evokerId){
        if(negativeStatusEffects.contains(statusEffect)){
            playSFX(STATUS);
        } else{
            playSFX(HEAL);
        }
        // for engage and butter-up, for when you need char's id
        statusEffects.addStatusEffect(statusEffect, duration, evokerId);
    }
    public void addStatusEffect(statusEffect statusEffect, int duration, String triggerCond){
        // for IMMUNE status
        statusEffects.addStatusEffect(statusEffect, duration, triggerCond);
    }
    public void addStatusEffect(statusEffect clashEffect){

        charClickedBridger bridger = charLayout.clickedInterface;

        switch(clashEffect){

            case DENT:
                applyStatChange(SPD, -1);
                setSP(getSP() - 20);

                statusEffects.addStatusEffect(clashEffect, -1);

                bridger.conveyVisual(statusEffect.STAT_DROP, battleSerialNum);
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);
                break;

            case THAW:
            case CORROSION:
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);
                statusEffects.addStatusEffect(clashEffect, -1);
                break;
            case ABLAZE:
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);
                statusEffects.addStatusEffect(clashEffect, 4);
                break;
            case ANNEAL:
                applyStatChange(INT, -1);
                applyStatChange(DEF, -2);

                bridger.conveyVisual(statusEffect.STAT_DROP, battleSerialNum);
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);

                statusEffects.addStatusEffect(clashEffect, 4);
                break;

            case FLINCH:

            case BRITTLE:
                applyStatChange(DEF, -1);
                luckStatIsZero = true;

                bridger.conveyVisual(statusEffect.STAT_DROP, battleSerialNum);
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);

                statusEffects.addStatusEffect(clashEffect, 6);

                break;
            case EMPOWER:
                applyStatChange(ATK, 2);
                applyStatChange(MAG, 2);
                bridger.conveyVisual(statusEffect.STAT_RAISE, battleSerialNum);

                statusEffects.addStatusEffect(clashEffect, 3);
                break;
            case EXALTED:
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);
                statusEffects.addStatusEffect(clashEffect, 7);
                break;
            case FRAZZLE:
                // todo - range change?
                applyStatChange(SPD, -1);
                applyStatChange(HIT, -1);
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);
                bridger.conveyVisual(statusEffect.STAT_DROP, battleSerialNum);

                statusEffects.addStatusEffect(clashEffect, 5);
                break;
            case AWAKENED:
                applyStatChange(ATK, 1);
                applyStatChange(DEF, 1);
                applyStatChange(MAG, 1);
                applyStatChange(INT, 1);
                applyStatChange(SPD, 1);
                bridger.conveyVisual(statusEffect.STAT_RAISE, battleSerialNum);

                statusEffects.addStatusEffect(clashEffect, 7);
                break;
            case OBEDIENT:
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);
                statusEffects.addStatusEffect(clashEffect, 7);
                break;
            case DISSIPATE:
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);
                statusEffects.addStatusEffect(clashEffect, 4);
                break;
            case NEUTRALIZE:
                applyStatChange(ATK, -2);
                applyStatChange(MAG, -2);

                bridger.conveyVisual(statusEffect.STAT_DROP, battleSerialNum);
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);

                statusEffects.addStatusEffect(clashEffect, 4);
                break;
            case ELECTROMAGNET:
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);
                statusEffects.addStatusEffect(clashEffect, 3);
                break;
            case CURBED:
                applyStatChange(MAG, -1);

                bridger.conveyVisual(statusEffect.STAT_DROP, battleSerialNum);
                bridger.conveyVisual(statusEffect.CLASH_EFFECT, battleSerialNum);

                statusEffects.addStatusEffect(clashEffect, 5);
                break;

            default:


        }
    }
    public void metamorph(affinity aff){
        // for METAMORPH only

        if(affinity == aff){
            affinity = getChars().getAffinity();
        } else{ affinity = aff; }
    }


    public chars getChars(){return charSilhouette;}

    public String getName() { return this.name;}
    public int getHP() { return this.HP;}
    public int getHPMax() { return this.HPMax; }
    public int getSP() { return (int) this.SP;}
    public int getSPMax() { return this.SPMax; }
    public int getSpd(){ return this.spd;}
//    public ArrayList<Integer> getRange() { return range; }
    public float getBaseLCK(){
        if(luckStatIsZero){
            return 0;
        } else {
            return this.baseLCK;
        }
    }
    public float getStatFloat(stat statification){
        if(!(statification.equals(LCK) && luckStatIsZero)){
            return charSilhouette.getStatFloat(statification);
        } else{
            return 0;
        }
    }
    public affinity getAffinity() { return this.affinity; }
    public weaponType getWeapon(){
        return charSilhouette.getWeapon();
    }

    public boolean turnComplete(){

        if((getActionsRemaining() <= 0) && !hasFinaleActionRemaining) {

            if(shouldRemoveAdvantage) {
                removeStatusIfExists(statusEffect.ADVANTAGE_DX);
            } else{
                shouldRemoveAdvantage = true;
            }

            removeStatusIfExists(statusEffect.DISADVANTAGE_DX);

            charSilhouette.releaseActions();

            return true;
        } else{
            return false;
        }
    }
    public boolean isFinaleActionTime(){

        if(getActionsRemaining() == 0){
            if(hasFinaleActionRemaining){
                hasFinaleActionRemaining = false;
                return true;
            }
        }
        return false;
    }
    public int getActionsPerTurn() { return actionsPerTurn; }
    public int getActionsRemaining(){
        return actionsRemaining;
    }

    public boolean getIsGuardActioning(){ return this.guardAction;}
//    public boolean getIsAssisting(){ return isAssisting;}
//
//    public boolean hasAssistant() { return hasAssistant; }

    public boolean getIsFrontLine() { return isFrontLine;}
    public boolean getIsAlive(){ return (!this.isKoed);}
    public boolean getIsPlayer(){ return this.isPlayer;}
    public Integer getBattleSerialNum(){ return this.battleSerialNum;}
    public characterLayout getCharLayout(){return this.charLayout;}
    public ArrayList<Integer> getCooldownArray(boolean isFinaleArray){return coolMan.getCooldownArray(isFinaleArray);}

    public boostBoard getBoostBoard(){ return boosts; }
    public void resetStatTable(){ boosts.resetMap(true);}
    public void copyBoostBoard(boostBoard bb){ boosts.copyBoosts(bb); }

    public Integer getBoostVal(stat statf){
        stat statoo;
        if(statf == PHYSAVO || statf == MAGAVO){
            statoo = AVO;
        } else {
            statoo = statf;
        }

        int boostSwitchVal = 0;
        switch (statf) {
            case ATK:
            case DEF:
                if (statusEffects.contains(statusEffect.BURN)) {
                    boostSwitchVal -= 1;
                }
                break;
            case MAG:
            case INT:
                if (statusEffects.contains(statusEffect.POISON)) {
                    boostSwitchVal -= 1;
                }
                break;
            case HIT:
                if (statusEffects.contains(statusEffect.RATTLE)) {
                    boostSwitchVal -= 2;
                }
                break;
            case SPD:
                if (statusEffects.contains(statusEffect.BEDRAGGLE)) {
                    boostSwitchVal -= 2;
                }
                if(statusEffects.contains(GRAPPLE) || statusEffects.contains(statusEffect.GRAPPLED)){
                    boostSwitchVal -= 1;
                }
                if(statusEffects.contains(statusEffect.SWALLOW) || statusEffects.contains(SWALLOWED)){
                    boostSwitchVal -= 3;
                }
                break;

            case AVO:
                if (statusEffects.contains(statusEffect.BEDRAGGLE)) {
                    boostSwitchVal -= 2;
                }
                if(statusEffects.contains(GRAPPLE) || statusEffects.contains(statusEffect.GRAPPLED)){
                    boostSwitchVal -= 1;
                }
                if(statusEffects.contains(statusEffect.SWALLOW) || statusEffects.contains(SWALLOWED)){
                    boostSwitchVal -= 3;
                }


                break;
            default:
        }

        return (this.boosts.get(statoo) + boostSwitchVal);
    }
    public float getBoostModifier(stat statGuy){
        float modifier = 1f;

        switch (statGuy){
            case ATK:
            case DEF:
                if(statusEffects.contains(statusEffect.BURN)){
                    modifier *= 0.75;
                }
                break;
            case MAG:
            case INT:
                if(statusEffects.contains(statusEffect.POISON)){
                    modifier *= 0.75;
                }
                break;
            case HIT:
                if(statusEffects.contains(statusEffect.RATTLE)){
                    modifier *= 9.0 /16;
                }
                break;
            case SPD:
                if(statusEffects.contains(statusEffect.BEDRAGGLE)){
                    modifier *= 9.0 / 16;
                }
                if(statusEffects.contains(statusEffect.SWALLOW) || statusEffects.contains(SWALLOWED)){
                    modifier *= 27.0 / 64;
                }
                if(statusEffects.contains(GRAPPLE) || statusEffects.contains(statusEffect.GRAPPLED)){
                    modifier *= 0.75;
                }
                break;
            case AVO:
            case MAGAVO:
            case PHYSAVO:
                if(statusEffects.contains(statusEffect.BEDRAGGLE)){
                    modifier *= 9.0 / 16;
                }
                if(hasStatus(statusEffect.SLEEP) || hasStatus(statusEffect.STUN)){
                    modifier *= 0.4;
                }
                if(statusEffects.contains(statusEffect.SWALLOW) || statusEffects.contains(SWALLOWED)){
                    modifier *= 27.0 / 64;
                }
                if(statusEffects.contains(GRAPPLE) || statusEffects.contains(statusEffect.GRAPPLED)){
                    modifier *= 0.75;
                }

                break;
            default:
        }

        modifier *= boosts.getModifier(statGuy);

        return modifier;
    }
    public stat getHighestStatVal(){
        stat highestStat = ATK;
        int highestStatVal = 0;

        ArrayList<stat> boostables = boosts.getBoostables();

        for (int i = 0; i < boostables.size(); i++) {
            final stat curStat = boostables.get(i);

            if(curStat == AVO){
                int valPhys = (int) (getStatFloat(PHYSAVO) * getBoostModifier(curStat));
                int valMag = (int) (getStatFloat(MAGAVO) * getBoostModifier(curStat));

                if(valPhys > highestStatVal){
                    highestStat = PHYSAVO;
                    highestStatVal = valPhys;
                }
                if(valMag > highestStatVal){
                    highestStat = MAGAVO;
                    highestStatVal = valMag;
                }
            } else {
                int curStatVal = (int) (getStatFloat(curStat) * getBoostModifier(curStat));

                if(curStat == HIT){
                    curStatVal *= 0.75;
                }

                if (curStatVal > highestStatVal) {
                    highestStatVal = curStatVal;
                    highestStat = curStat;
                }
            }
        }

        return highestStat;
    }
    public int getTotalStatChangeAmount(){

        int count = 0;
        ArrayList<stat> boostables = boosts.getBoostables();

        for (int i = 0; i < boostables.size(); i++) {
            count += boosts.get(boostables.get(i));
        }
        return count;
    }

    public stat statStealReduction(stat targetStat2, int value, boolean requireStatBoostExists){

        stat targetStat = targetStat2;

        if(targetStat == HIGHEST){
            targetStat = getHighestStatVal();
        }

        if (requireStatBoostExists) {
            if (targetStat == RANDOM) {
                ArrayList<stat> boosted = boosts.getBoostedStats();

                if (boosted.isEmpty()) {
                    return NULL;
                } else {
                    targetStat = boosted.get((int) (Math.random() * boosted.size()));
                }
            }
        }

        for (int i = 0; i < value; i++) {
            if(targetStat == null){
                targetStat = RANDOM;
            }
            targetStat = boosts.stealStat(targetStat);
        }

        return targetStat;
    }
    public void addStealBonuses(boostBoard board){
        boosts.combineBoostBoardVals(board);
    }
    public boostBoard stealAllStatChanges(){
        boostBoard newBoard = new boostBoard();

        ArrayList<stat> boostables = boosts.getBoostables();

        for (int i = 0; i < boostables.size(); i++) {
            newBoard.addBoost(boostables.get(i), boosts.get(boostables.get(i)));
        }

        boosts.clear();

        return newBoard;
    }

    public boolean hasStatusEffect(){ return statusEffects.hasActiveStatusEffect();}
    public statusEffect getTurnUnavailableStatus(){ return statusEffects.getTurnUnavailableStatus();}
    public ArrayList<statusEffect> getDamagingStatusEffects(){return statusEffects.getDamagingStatusEffects();}
    public ArrayList<statusEffectList.effect> getTargetAffStatusEffects(){ return statusEffects.getTargetAffStatusEffects();}
    public statusEffect getStatusEffectsWithPriority() {return statusEffects.returnStatusEffectWithPriority();}
    public ArrayList<statusEffectList.effect> getStatusEffects(){return statusEffects.getStatusList();}
    public boolean hasStatus(statusEffect statusEffect){
        if(statusEffect != GRAPPLE && statusEffect != GRAPPLED){
            return statusEffects.contains(statusEffect);
        } else{
            if(statusEffect == GRAPPLE){
                return statusEffects.contains(GRAPPLE) || statusEffects.contains(SWALLOW);
            } else{
                return statusEffects.contains(GRAPPLED) || statusEffects.contains(SWALLOWED);
            }

        }
    }
    public Object getBonusEffectExtra(statusEffect effect){
        if(effect != GRAPPLE && effect != GRAPPLED) {
            return statusEffects.getEffectExtra(effect);
        } else{
            Object o1, o2;
            if(effect == GRAPPLE){
                o1 = statusEffects.getEffectExtra(GRAPPLE);
                o2 = statusEffects.getEffectExtra(SWALLOW);

                if(o1 == null){
                    return o2;
                } else return o1;
            } else{

                o1 = statusEffects.getEffectExtra(GRAPPLED);
                o2 = statusEffects.getEffectExtra(SWALLOWED);

                if(o1 == null){
                    return o2;
                } else return o1;
            }
        }
    }
    public boolean removeStatusIfExists(statusEffect status){ return statusEffects.removeStatusIfExists(status);}
    public void removeStatusViaImposerId(int id){ statusEffects.removeStatusViaImposerId(id);}
    public void statusConcluded(statusEffect reaction){
        switch (reaction){
            case BRITTLE:
                applyStatChange(DEF, 1);
                luckStatIsZero = false;
                break;
            case ELECTROMAGNET:
                applyStatChange(SPD, 1);
                break;
            case NEUTRALIZE:
                // reinstate clashReactionAbility
                break;

            case EMPOWER:
                applyStatChange(ATK, -2);
                applyStatChange(MAG, -2);
                break;

            case AWAKENED:
                applyStatChange(ATK, -1);
                applyStatChange(DEF, -1);
                applyStatChange(MAG, -1);
                applyStatChange(INT, -1);
                applyStatChange(SPD, -1);
                break;

            case CURBED:
                // reinstate range :)
                applyStatChange(MAG, 1);
                break;

            case FLINCH:
                // range recalc
                break;

            case FRAZZLE:
                applyStatChange(SPD, 1);
                applyStatChange(HIT, 1);
                // recalc range

                break;
            case OBEDIENT:
            default:
        }
    }
    public void grappleRelease(boolean releaseSwallowEffect, boolean releaseBeingGrappled) {
        statusEffects.grappleRelease(releaseSwallowEffect, releaseBeingGrappled);
    }

    public int getAggroedOn(){
        return -1;
    }

    public void setName(String i){ this.name = i;}
    public void setHP(int g){
        HP = g;
        if(HP < 0){
            HP = 0;
        } else if(HP > HPMax){
            HP = HPMax;
        }
    }
    public void setHPMax(int g){
        this.HPMax = g;
        if(HPMax < 1){
            HPMax = 1;
        }
    }
    public int setSP(int f){
        int change = (int) SP;
        SP = f;
        if(SP < 0){
            SP = 0;
        } else if(SP > SPMax){
            SP = SPMax;
        }

        change -= (int) SP;

        return change;
    }
    public void setSPMax(int f){this.SPMax = f;}

    public void setIsGuardActioning(boolean b){
        this.guardAction = b;
        playSFX(moveType.FOCGAR); }
    public void setIsFocusActioning(boolean b){
        this.focusAction = b;
        playSFX(moveType.FOCGAR);
    }
//    public void setIsAssisting(boolean b){ this.isAssisting = b; }
//    public void setHasAssistant(boolean b){ this.hasAssistant = b;}


    public void setIsAlive(boolean d){
        this.isKoed = !d;
        if(!d){
            statusEffects.clear();
        }
    }
    public void setFrontLine(boolean b){ this.isFrontLine = b; }
    public void setIsPlayer(boolean c){this.isPlayer = c;}
    public void setBattleSerialNum(int e){ this.battleSerialNum = e;}

    private int setBaseActionsPerTurn(float baseSpd){
        int turns = (int) (baseSpd / 3);

        switch (turns){
            case 0:
                turns = 1;
                break;
            case 1:
                turns = 2;
                break;
            case 4:
            case 5:
                turns = 3;
                break;
        }

        return turns;
    }
    public void setActionCooldown(int actionId){
        coolMan.setCooldown(actionId, charSilhouette.getCharAction(actionId).getCooldown() + 1);
    }
    public void altActionCount(int change){
        actionsRemaining += change;
    }
    public void terminateTurn(){
        if(actionsRemaining > 0) {
            actionsRemaining = 0;
        }
        hasFinaleActionRemaining = false;
    }

    public ArrayList<stat> balm(boolean targetingSelf){
        ArrayList<stat> restoredStats = new ArrayList<>();
        restoredStats.add(boosts.balm());
        restoredStats.add(boosts.balm());
        if(targetingSelf){
            restoredStats.add(boosts.balm());
        }
        return restoredStats;
    }

    public void reload(){
        numBullets = numBulletsMax;
    }
    public Integer getBulletCount(){
        if(usesBullets) {
            return numBullets;
        } else return null;
    }
    public void useBullet(){
        if(numBullets <= 0){
        } else {
            numBullets--;
        }
    }
    public boolean usesBullets(){
        return usesBullets;
    }

    public boolean hasAegisShield(){
        return aegisShield != null;
    }
    public void createAegisShield(){
        addStatusEffect(AEGIS_SHIELD, -1);
        aegisShield = new aegis((int) (getHPMax() * 1.5));
    }
    public int damageAegisShield(int dmg, int statusEffectNum){
        if(dmg >= 0) {
            return aegisShield.applyDamage(dmg, statusEffectNum);
        } else{
            return -1;
        }
    }
    public void cancelAegisShield(){
        aegisShield = null;
        removeStatusIfExists(AEGIS_SHIELD);
    }


    public float[] getSmallBarFilledPercentages(){
        float[] filledPercent = new float[2];

        filledPercent[0] = (float) (this.getHP() / this.getHPMax());
        filledPercent[1] = (float) (this.getSP() / this.getSPMax());

        return filledPercent;
    }

    public Thread playSFX(moveType moveType){
        return soundBoard.playSFX(moveType);
    }


    public void turnRegenSP(float regenMod){
        SP += SPRegen * regenMod * getBoostModifier(SPREGEN);
        if(SP > SPMax){ SP = SPMax;}
    }
    private int getSpdActionAlt(){

        int actionDif = 0;
        if(boosts.get(SPD) >= 3){
            actionDif++;
            fightMidgrounds.post(name, null,
                    null, fightUpdater.textType.getTextType(statusEffect.SPD, true), null, null);

        } else if (boosts.get(SPD) <= -3){
            actionDif--;
            if(actionsRemaining > 1) {
                fightMidgrounds.post(name, null,
                        null, fightUpdater.textType.getTextType(statusEffect.SPD, true), null, null);
            }

        }

        return actionDif;
    }

    //cooldown, half spregen, status turns --
    public void newAction(){

        statusEffects.turnPass(false);

        int cooldownReduce = 1;
        if(!hasStatus(statusEffect.ENERVATE)) {
            // only gets regen if not enervate
            turnRegenSP(0.5f);
        }

        coolMan.actionPass(cooldownReduce);

    }
    public void newTurn(){
        Thread fetchActionsThread = charSilhouette.exactFetchActions();

        actionsRemaining += actionsPerTurn;
        actionsRemaining += getSpdActionAlt();

        if(actionsRemaining <= 0){
            actionsRemaining = 1;
        }

        hasFinaleActionRemaining = true;

        statusEffects.turnPass(true);

        int cooldownReduce = 1;
        if(!hasStatus(statusEffect.ENERVATE)) {
            if (focusAction) {
                turnRegenSP(3);
                cooldownReduce++;
            } else {
                turnRegenSP(1);
            }
        } else{
            //
            actionsRemaining--;
            if(actionsRemaining <= 0){
                actionsRemaining = 1;
            }
        }

        coolMan.turnPass(cooldownReduce);

        guardAction = false;
        focusAction = false;

        if(statusEffects.contains(statusEffect.REGEN)){
            int newHpVal = getHP() + (getHPMax() / 10);
            if(newHpVal > getHPMax()){
                newHpVal = getHPMax();
            }
            setHP(newHpVal);
        }

        if(fetchActionsThread.isAlive()){
            try {
                fetchActionsThread.join();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public class aegis{

        int hpVal;

        public aegis(int hpval){
            this.hpVal = hpval;
        }

        // status effects also reduce durability
        public int applyDamage(int dmg, int statusEffectNum){

            int dmgTaken = dmg + ((hpVal / 8) * statusEffectNum);
            hpVal -= dmgTaken;

            return dmgTaken;
        }

    }

    public class cooldownManager{

        // move ids 1 - 5, not including normal attack
        ArrayList<Integer> moveCooldowns = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        // special 1, 2, 3 and affinity action
        ArrayList<Integer> finaleMoveCooldowns = new ArrayList<>(Arrays.asList(0, 0, 0, 0));


        public cooldownManager(){
            // set initial aff action cooldown
            finaleMoveCooldowns.set(3, getChars().getCharAction(-11).getCooldown() / 3);
        }


        public void actionPass(int cooldownVal){
            for (int i = 0; i < moveCooldowns.size(); i++) {
                if(moveCooldowns.get(i) > 0){
                    moveCooldowns.set(i, moveCooldowns.get(i) - cooldownVal);
                }
            }
        }
        public void turnPass(int cooldownVal){
            for (int i = 0; i < finaleMoveCooldowns.size(); i++) {
                if(finaleMoveCooldowns.get(i) > 0){
                    finaleMoveCooldowns.set(i, finaleMoveCooldowns.get(i) - cooldownVal);
                }
            }
        }

        public void setCooldown(int moveId, int cooldownVal){

            switch (moveId){
                case 0:
                default:
                    break;
                case 1:
                    moveCooldowns.set(0, cooldownVal);
                    break;
                case 2:
                    moveCooldowns.set(1, cooldownVal);
                    break;
                case 3:
                    moveCooldowns.set(2, cooldownVal);
                    break;
                case 4:
                    moveCooldowns.set(3, cooldownVal);
                    break;
                case 5:
                    moveCooldowns.set(4, cooldownVal);
                    break;
                case -7:
                    finaleMoveCooldowns.set(0, cooldownVal);
                    break;
                case -8:
                    finaleMoveCooldowns.set(1, cooldownVal);
                    break;
                case -9:
                    finaleMoveCooldowns.set(2, cooldownVal);
                    break;
                case -11:
                    finaleMoveCooldowns.set(3, cooldownVal);
                    break;
            }
        }

        public ArrayList<Integer> getCooldownArray(boolean isFinaleArray){
            ArrayList<Integer> usabilityArray;

            if(!isFinaleArray) {
                usabilityArray = moveCooldowns;
            } else{
                usabilityArray = finaleMoveCooldowns;
            }

            return usabilityArray;
        }
    }

    public static class boostBoard extends HashMap<stat, Integer>{

        public ArrayList<stat> boostables = new ArrayList<>();

        public boostBoard(){
            boostables.addAll(Arrays.asList(ATK, DEF, MAG, INT, SPD, LCK, HIT, CRIT, AVO, BLOCK, SPREGEN));

            resetMap(false);
        }

        public ArrayList<stat> getBoostables(){return boostables;}


        public void resetMap(boolean initialized){

            if(!initialized) {
                for (int i = 0; i < boostables.size(); i++) {
                    put(boostables.get(i), 0);
                }
                put(RES, 0);
                put(SPREGEN, 0);
            } else {
                for (int i = 0; i < boostables.size(); i++) {
                    replace(boostables.get(i), 0);
                }
                replace(RES, 0);
                replace(SPREGEN, 0);
            }
        }
        public void clear(){
            for (int i = 0; i < boostables.size(); i++) {
                this.replace(boostables.get(i), 0);
            }
        }


        public stat addBoost(stat targetStat2, int boostAmount){

            stat targetStat = targetStat2;

            if(targetStat != ALL) {
                if (targetStat.equals(RANDOM)) {
                    targetStat = boostables.get((int) (Math.random() * boostables.size()));
                }

                int statBoostAmount = get(targetStat) + boostAmount;
                if (statBoostAmount > 4) {
                    statBoostAmount = 4;
                }
                if (statBoostAmount < -4) {
                    statBoostAmount = -4;
                }

                replace(targetStat, statBoostAmount);
            } else{
                for (int i = 0; i < boostables.size(); i++) {
                    targetStat = boostables.get(i);

                    int statBoostAmount = get(targetStat) + boostAmount;
                    if (statBoostAmount > 4) {
                        statBoostAmount = 4;
                    }
                    if (statBoostAmount < -4) {
                        statBoostAmount = -4;
                    }

                    replace(targetStat, statBoostAmount);
                }
            }


            return targetStat;
        }
        public float getModifier(stat targetStat){
            int boostAmount;
            if(boostables.contains(targetStat)) {
                boostAmount = get(targetStat);
            } else if (targetStat == PHYSAVO || targetStat == MAGAVO) {
                boostAmount = get(AVO);
            } else{
                boostAmount = 0;
            }

            return (float) Math.pow((4/3f), boostAmount);
        }


        //returns a stat, which the other boostboard must add 1 to.
        // reduction happens here
        public stat balm(){
            ArrayList<stat> debuffedStats = new ArrayList<>();

            for (int i = 0; i < boostables.size(); i++) {
                if(get(boostables.get(i)) < 0){
                    debuffedStats.add(boostables.get(i));
                }
            }

            if(debuffedStats.size() > 0) {
                stat randomStatToBoost = debuffedStats.get((int) (debuffedStats.size() * Math.random()));
                replace(randomStatToBoost, get(randomStatToBoost) + 1);
                return randomStatToBoost;
            }
            return null;
        }
        public stat stealStat(stat denise){

            if(denise == RANDOM){
                denise = boostables.get((int) (Math.random() * boostables.size()));
            }

            addBoost(denise, -1);
            return denise;

        }


        public void copyBoosts(boostBoard bb){
            for (int i = 0; i < boostables.size(); i++) {
             replace(boostables.get(i), bb.get(boostables.get(i)));
            }
        }
        public void combineBoostBoardVals(boostBoard b){
            for (int i = 0; i < boostables.size(); i++) {
                stat cur = boostables.get(i);
                addBoost(cur, get(cur) + b.get(cur));
            }
        }


        private ArrayList<stat> getBoostedStats(){
            ArrayList<stat> boostedStats = new ArrayList<>();

            for (int i = 0; i < boostables.size(); i++) {
                if(get(boostables.get(i)) > 0){
                    boostedStats.add(boostables.get(i));
                }
            }

            return boostedStats;
        }
    }

}


