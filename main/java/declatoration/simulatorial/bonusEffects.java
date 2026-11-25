package declatoration.simulatorial;

import static declatoration.simulatorial.enums.stat.statArray;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

import declatoration.simulatorial.enums.stat;
import declatoration.simulatorial.enums.statusEffect;
import declatoration.simulatorial.enums.target;
import declatoration.simulatorial.fight.affinityClashExtraEffects;

public class bonusEffects {


    private statusEffect status;
    private target statusTarget;
    boolean targetsAlly = false;
    private Integer statusLength;

    public boolean hasTriggerPercent;
//    private stat triggerStat;
    private Integer triggerPercent;

    public boolean hasTriggerCondition = false;
    private String triggerCondition;

    //say which stat drops, by how many levels
    boolean hasStatChange;
    private stat changingStat;
    private Float statChangeDegree = null;



    public bonusEffects(){}
    public bonusEffects(statusEffect randomStatus){
        status = randomStatus;
        this.statusTarget = target.TARGET;

        hasTriggerCondition = false;
        hasTriggerPercent = false;

        triggerPercent = 100;

        statusLength = 2 + (int) (Math.random() * 7);

        switch (status){

            case STAT_DROP:
            case STAT_RAISE:
                statChangeDegree = (float) (-4 + (8 * Math.random()));
                changingStat = statArray[(int) (statArray.length * Math.random())];
                break;

            case BANE:
            case CHARM:
            case EVADE:
            case EVADE_DX:
            case COUNTER:
            case ASSIST_UP:
                statusLength = -1;
                break;
        }
    }

    public ArrayList<bonusEffects> getBonusEffectFromClashEffect(ArrayList<affinityClashExtraEffects.clashEffect> clashReactions) {

        ArrayList<bonusEffects> be = new ArrayList<>();
        ArrayList<statusEffect> statEffects = new ArrayList<>(Arrays.asList(statusEffect.ATK, statusEffect.DEF,
                statusEffect.MAG, statusEffect.INT, statusEffect.SPD, statusEffect.CRIT, statusEffect.SPREGEN));

        for (int i = 0; i < clashReactions.size(); i++) {
            bonusEffects cur = new bonusEffects();
            affinityClashExtraEffects.clashEffect ce = clashReactions.get(i);


            target target;
            if(ce.getAffectsUser()){
                target = declatoration.simulatorial.enums.target.SELF;
            } else{
                target = declatoration.simulatorial.enums.target.TARGET;
            }


            statusEffect reaction = ce.getReaction();
            stat changingStat = null;

            if(statEffects.contains(reaction)){

                changingStat = stat.getStatEnum(reaction.toString());

                if(ce.getDegree() > 0){
                    reaction = statusEffect.STAT_RAISE;
                } else{
                    reaction = statusEffect.STAT_DROP;
                }
            }

            cur.setStatus(reaction, target, getDegreeForStatus(reaction));
            if(ce.getDegree() != null){
                cur.setTargetStat(changingStat , ce.getDegree());
            }

            be.add(cur);

        }

        return be;
    }
    private int getDegreeForStatus(statusEffect status){

        int res = 0;

        switch (status) {

            case BANE:
            case BRITTLE:
            case DENT:
                res = -1;
                break;

            case BLEED:
                res = 4;
                break;

            case BURN:
            case POISON:
            case CURBED:
            case NEUTRALIZE:
            case DISSIPATE:
            case EXALTED:
            case ABLAZE:
            case ANNEAL:
                res = 3;
                break;

            case BEDRAGGLE:
            case RAGE:
            case OBEDIENT:
            case EMPOWER:
            case CORROSION:
                res = 2;
                break;

            case STUN:
            case FREEZE:
            case RATTLE:
            case ENERVATE:

            case ACTION_ADD:
            case ACTION_REDUCE:
            case ELECTROMAGNET:
            case AWAKENED:
            case FRAZZLE:
            case FLINCH:
            case THAW:
                res = 1;
                break;


            default:
                break;
        }

        return res;
    }

    public boolean triggeredViaPercent(){
        if(hasTriggerPercent) {
            float trigger = triggerPercent;

            return Math.random() * 100 <= trigger;
        } else {
            return true;
        }
    }

    public void setStatus(statusEffect status, target statusTarget, Integer statusLength){
        this.status = status;
        this.statusTarget = statusTarget;
        this.statusLength = statusLength;

        if(statusTarget.toString().contains("ALLY") || statusTarget.toString().contains("ALLIES") || statusTarget.toString().contains("SELF")){
            targetsAlly = true;
        }
    }
    public void setTriggers(@Nullable String triggerCondition, Integer triggerPer){
        this.triggerCondition = triggerCondition;
        if (triggerCondition != null){
            hasTriggerCondition = true;
        }
        this.triggerPercent = triggerPer;

        hasTriggerPercent = !(triggerPercent == 0);
    }
    public void setTargetStat(stat changingStat, float statChangeDegree){
        this.changingStat = changingStat;
        this.statChangeDegree = statChangeDegree;

        hasStatChange = !(changingStat == null || changingStat == stat.NULL);
    }


    public statusEffect getStatus() { return status; }

//    public stat getTriggerStat() { return triggerStat; }
    public int getTriggerPercent() { return triggerPercent; }

    public target getStatusTarget(){return statusTarget;}

    public stat getChangingStat() { return changingStat; }
    public Float getStatChangeDegree() { return statChangeDegree; }
    public Integer getStatusLength(){return statusLength;}

    public String getTriggerCondition() { return triggerCondition; }
    //    public boolean hasSecondStatChange(){return (this.changingStat2 != null && this.changingStat2 != stat.NULL); }
}
