package declatoration.simulatorial.fight;


import static declatoration.simulatorial.enums.statusEffect.ACTION_REDUCE;
import static declatoration.simulatorial.enums.statusEffect.BANE;
import static declatoration.simulatorial.enums.statusEffect.BEDRAGGLE;
import static declatoration.simulatorial.enums.statusEffect.BLEED;
import static declatoration.simulatorial.enums.statusEffect.BURN;
import static declatoration.simulatorial.enums.statusEffect.CHARM;
import static declatoration.simulatorial.enums.statusEffect.DEFENDED;
import static declatoration.simulatorial.enums.statusEffect.DISADVANTAGE_DX;
import static declatoration.simulatorial.enums.statusEffect.ENERVATE;
import static declatoration.simulatorial.enums.statusEffect.ENGAGE;
import static declatoration.simulatorial.enums.statusEffect.EVADE;
import static declatoration.simulatorial.enums.statusEffect.EVADE_DX;
import static declatoration.simulatorial.enums.statusEffect.EX_DISADVANTAGE;
import static declatoration.simulatorial.enums.statusEffect.FREEZE;
import static declatoration.simulatorial.enums.statusEffect.GRAPPLE;
import static declatoration.simulatorial.enums.statusEffect.GRAPPLED;
import static declatoration.simulatorial.enums.statusEffect.INTIMIDATE;
import static declatoration.simulatorial.enums.statusEffect.NULL;
import static declatoration.simulatorial.enums.statusEffect.POISON;
import static declatoration.simulatorial.enums.statusEffect.RAGE;
import static declatoration.simulatorial.enums.statusEffect.RAMPART;
import static declatoration.simulatorial.enums.statusEffect.RATTLE;
import static declatoration.simulatorial.enums.statusEffect.SLEEP;
import static declatoration.simulatorial.enums.statusEffect.STUN;
import static declatoration.simulatorial.enums.statusEffect.SWALLOW;
import static declatoration.simulatorial.enums.statusEffect.SWALLOWED;
import static declatoration.simulatorial.enums.statusEffect.UNTARGETABLE;
import static declatoration.simulatorial.enums.statusEffect.TOPPLE;
import static declatoration.simulatorial.enums.statusEffect.WHIRLPOOL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import declatoration.simulatorial.enums.affinity;
import declatoration.simulatorial.enums.statusEffect;

public class statusEffectList {


    private affinity aff;
    private battleToken parentBT;

    private ArrayList<effect> statusList = new ArrayList<>();
    private boolean hasStatus = false;

    static List<statusEffect> negativeStatusEffects = new ArrayList<>();
    private static List<statusEffect> damagingStatusEffects = new ArrayList<>();
    private static List<statusEffect> immobilizingStatusEffects = new ArrayList<>();
    private static List<statusEffect> targetAffStatusEffects = new ArrayList<>();

    public statusEffectList(battleToken parentBT){
        negativeStatusEffects.addAll(Arrays.asList(STUN, SLEEP, POISON, BLEED, FREEZE, BURN, RATTLE,
                INTIMIDATE, BEDRAGGLE, RAGE, ENERVATE, ENGAGE, BANE, CHARM, WHIRLPOOL, TOPPLE, DISADVANTAGE_DX,
                EX_DISADVANTAGE));
        damagingStatusEffects.addAll(Arrays.asList(POISON, BLEED, FREEZE, BURN, BANE, WHIRLPOOL, SWALLOWED));
        immobilizingStatusEffects.addAll(Arrays.asList(STUN, SLEEP, FREEZE, CHARM));
        targetAffStatusEffects.addAll(Arrays.asList(INTIMIDATE, ENGAGE, RAMPART, RAGE, UNTARGETABLE));
        this.parentBT = parentBT;
        aff = parentBT.getAffinity();
    }

    public int size(){ return statusList.size(); }
    public ArrayList<effect> getStatusList(){ return this.statusList; }

    public void clear(){
        statusList = new ArrayList<>();
    }

    public void turnPass(boolean newCharTurn){

        for (int i = 0; i < statusList.size(); i++) {
            effect eff = statusList.get(i);
            if(eff != null){

                if(statusReducedByActionPass(eff.statusEffect) ||
                        (!statusReducedByActionPass(eff.statusEffect) && newCharTurn)){

                    eff.duration -= 1;

                    if(eff.duration == 0){
                        parentBT.statusConcluded(eff.statusEffect);
                        removeStatus(i);
                        i--;
                    }
                }


            }
        }
    }
    public boolean turnAvailable(){
        for (int i = 0; i < statusList.size(); i++) {
            if(immobilizingStatusEffects.contains(statusList.get(i).statusEffect)){
                if(!(statusList.get(i).statusEffect == CHARM && (Math.random() > 0.5))){
                    return false;
                }
            }
        }
        return true;
    }

    public statusEffect getTurnUnavailableStatus(){
        return returnStatusEffectWithPriority();
    }

    public boolean statusReducedByActionPass(statusEffect status){

        switch (status){
            case BURN:
            case POISON:
            case STUN:
            case UNTARGETABLE:
            case MOVE_NEXT:
            case ELECTROMAGNET:
            case NEUTRALIZE:
            case DISSIPATE:
            case CHARM:
            case COUNTER:
            case EVADE_DX:
            case WHIRLPOOL:
            case ADVANTAGE_DX:
            case DISADVANTAGE_DX:
            case REGEN:
            case THORNS:
            case RAMPART:
            case BRITTLE:
            case FLINCH:
            case ANNEAL:
            case RATTLE:
            case INTIMIDATE:
            case BANE:
            case BLEED:
            case EX_ADVANTAGE:
            case EX_AGGRO_MOD:
            case EX_STAT_SWAP:
            case EX_ATTACK_MOD:
            case IMMUNE:
            case ASSIST_UP:
            case SLEEP:
            case FREEZE:
            case RAGE:
            case BEDRAGGLE:
            case ENERVATE:
            case ENGAGE:
            case AMBIENCE_BLOOD_MOON:
            default:
                return false;

                // per action
            case TOPPLE:
            case OBEDIENT:
            case AWAKENED:
            case EXALTED:
            case EMPOWER:
            case CORROSION:
            case THAW:
            case DENT:
            case ABLAZE:
            case CURBED:
            case FRAZZLE:
                return true;

        }
    }

    public void cure(){
        for (int i = 0; i < statusList.size(); i++) {
            if(negativeStatusEffects.contains(statusList.get(i).getStatusEffect())){
                removeStatus(i);
            }
        }
    }


    public boolean hasActiveStatusEffect(){
        return hasStatus;
    }
    public void addStatusEffect(statusEffect sEffect, int duration){
        if(shouldAddStatusEffect(sEffect)) {
            statusList.add(new effect(sEffect, duration));
            hasStatus = true;
        }
    }
    public void addStatusEffect(statusEffect sEffect, float statMod){
        if(shouldAddStatusEffect(sEffect)) {
            statusList.add(new effect(sEffect, -1, statMod));
            hasStatus = true;
        }
    }
    public void addStatusEffect(statusEffect sEffect, int duration, int charId){
        if(shouldAddStatusEffect(sEffect)) {
            statusList.add(new effect(sEffect, duration, charId));
            hasStatus = true;
        }
    }
    public void addStatusEffect(statusEffect sEffect, int duration, String trigCond){
        if(shouldAddStatusEffect(sEffect)) {
            statusList.add(new effect(sEffect, duration, trigCond));
            hasStatus = true;
        }
    }
    public void addStatusEffect(statusEffect sEffect, affinity aff){
        if(shouldAddStatusEffect(sEffect)) {
            statusList.add(new effect(sEffect, -1, aff));
            hasStatus = true;
        }
    }
    private boolean shouldAddStatusEffect(statusEffect status){
        if(!contains(status)) {
            switch (status) {
                case BANE:
                case BLEED:
                    if (aff != affinity.VAMP) {
                        return true;
                    }
                    break;
                case BURN:
                    if (aff != affinity.INFERNO && aff != affinity.DRAGONSVALK) {
                        return true;
                    }
                    break;
                case POISON:
                    if (aff != affinity.VENIN) {
                        return true;
                    }
                    break;
                case SLEEP:
                    if (aff != affinity.DUSK) {
                        return true;
                    }
                    break;
                case STUN:
                    if (aff != affinity.THUNDER) {
                        return true;
                    }
                    break;
                case FREEZE:
                    if (aff != affinity.GLACIER) {
                        return true;
                    }
                    break;
                case INTIMIDATE:
                    if (aff != affinity.GHENJU) {
                        return true;
                    }
                    break;
                case BEDRAGGLE:
                    if (aff != affinity.TORRENT) {
                        return true;
                    }
                    break;
                case ENERVATE:
                    if (aff != affinity.FORCE) {
                        return true;
                    }
                    break;
                default:
                    return true;
            }

        }

        return false;
    }

    public void removeStatus(int position){
        statusList.remove(position);

        if(statusList.size() == 0){
            hasStatus = false;
        }
    }
    public boolean removeStatusIfExists(statusEffect status){
        for (int i = 0; i < statusList.size(); i++) {
            if(statusList.get(i).isStatusEffect(status)) {
                statusList.remove(statusList.get(i));

                if (statusList.size() == 0) {
                    hasStatus = false;
                }

                return true;
            }
        }
        return false;
    }
    public void removeStatusViaImposerId(int imposerId){
        for (int i = 0; i < statusList.size(); i++) {

            effect eff = statusList.get(i);

            if((eff.statusEffect == ENGAGE || eff.statusEffect == INTIMIDATE || eff.statusEffect == DEFENDED ||
                    eff.statusEffect == GRAPPLE || eff.statusEffect == SWALLOW ||
                    eff.statusEffect == GRAPPLED || eff.statusEffect == SWALLOWED) &&
                    (int) eff.ex == imposerId) {

                statusList.remove(i);
                i--;

                if (statusList.size() == 0) {
                    hasStatus = false;
                }
            }
        }
    }


    public ArrayList<statusEffect> getDamagingStatusEffects(){
        ArrayList<statusEffect> stati = new ArrayList<>();

        for (int i = 0; i < statusList.size(); i++) {
            if(damagingStatusEffects.contains(statusList.get(i).statusEffect)){
                stati.add(statusList.get(i).statusEffect);
            }
        }

        return stati;
    }

    public ArrayList<effect> getTargetAffStatusEffects(){
        ArrayList<effect> statuses = new ArrayList<>();

        for (int i = 0; i < targetAffStatusEffects.size(); i++) {
            if(contains(targetAffStatusEffects.get(i))){
                statuses.add(getEffect(targetAffStatusEffects.get(i)));
            }
        }

        return statuses;
    }

    public boolean contains(statusEffect status){
        for (int i = 0; i < statusList.size(); i++) {
            if(statusList.get(i).isStatusEffect(status)){
                return true;
            }
        }
        return false;
    }
    private statusEffect checkContainsAndReturnStatus(statusEffect status1, statusEffect status2, statusEffect status3){
        statusEffect statusEff = statusEffect.NULL;
        if(contains(status1)){
            statusEff = status1;
        }
        if(contains(status2)){
            statusEff = status2;
        }
        if(contains(status3)){
            statusEff = status3;
        }
        return statusEff;
    }
    private statusEffect checkContainsAndReturnStatus(statusEffect status1, statusEffect status2){
        statusEffect statusEff = statusEffect.NULL;
        if(contains(status1)){
            statusEff = status1;
        }
        if(contains(status2)){
            statusEff = status2;
        }
        return statusEff;
    }

    public statusEffect returnStatusEffectWithPriority(){
        int priority = 0;
        statusEffect biggestStatMan = NULL;

        for (int i = 0; i < statusList.size(); i++) {
            statusEffect cur = statusList.get(i).statusEffect;
            if(cur.priority > priority){

                priority = cur.priority;
                biggestStatMan = cur;
            }
        }

        return biggestStatMan;
    }

    public effect getEffect(statusEffect status){
        for (int i = 0; i < statusList.size(); i++) {
            if(statusList.get(i).isStatusEffect(status)){
                return statusList.get(i);
            }
        }
        return null;
    }
    public Object getEffectExtra(statusEffect status){
        effect eff = getEffect(status);
        if(eff != null) {
            return eff.getExtra();
        } else{
            return null;
        }
    }

    public void grappleRelease(boolean releaseSwallowEffect, boolean releaseBeingGrappled) {
        ArrayList<statusEffect> effectsToRemove =  new ArrayList<>(Arrays.asList(GRAPPLE, GRAPPLED, SWALLOW, SWALLOWED));
        if(!releaseSwallowEffect) {
            effectsToRemove.remove(SWALLOW);
            effectsToRemove.remove(SWALLOWED);
        }
        if(!releaseBeingGrappled){
            effectsToRemove.remove(GRAPPLED);
        }

        for (int i = 0; i < statusList.size(); i++) {
            if(effectsToRemove.contains(statusList.get(i).statusEffect)){
                statusList.remove(i);
                i--;
            }
        }
    }

    public static class effect {

        int duration;
        statusEffect statusEffect;
        Object ex;


        public effect(statusEffect effect, int duration){
            this.duration = duration;
            this.statusEffect = effect;
        }

        public effect(statusEffect effect, int duration, Object extra){
            this.duration = duration;
            this.statusEffect = effect;
            ex = extra;
        }

        public int getDuration() { return duration; }
        public statusEffect getStatusEffect() { return statusEffect; }
        public Object getExtra(){ return ex; }
        public boolean isStatusEffect(statusEffect status){
            if(this.statusEffect == status){
                return true;
            }
            return false;
        }

    }
}